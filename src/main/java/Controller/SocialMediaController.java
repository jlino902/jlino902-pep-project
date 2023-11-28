package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();

    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::userRegHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::messagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void userRegHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.addAccount(account);
        if(createdAccount == null) {
            ctx.status(400);
        }
        else {
            ctx.json(mapper.writeValueAsString(createdAccount));
            ctx.status(200);
        }
    }
    
    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.accountLogin(account);
        if(verifiedAccount == null) {
            ctx.status(401);
        }
        else {
            ctx.json(mapper.writeValueAsString(verifiedAccount));
            ctx.status(200);
        }

    }
    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        System.out.println("First line in Controller for createMessage. ctx: " + ctx);
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        System.out.println("Now within the Controller. Message: " + message);
        Message newMessage = messageService.createMessage(message);
        System.out.println("Again within the Controller. newMessage: " + newMessage);
        if (newMessage == null) {
            ctx.status(400);
        }
        else {
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    private void messagesByIdHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message retrievedMessage = messageService.getMessagebyId(message_id);
        if (retrievedMessage == null) {
            ctx.status(200);
        }
        else {
            ctx.json(retrievedMessage);
            ctx.status(200);
        }

    }
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        System.out.println("First line in Controller. ctx: " + ctx);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        System.out.println("Within the Controller. Here is the int value: " + message_id);
        Message deletedMessage = messageService.deleteMessage(message_id);
        if (deletedMessage == null) {
            ctx.status(200);
        }
        else {
            ctx.json(deletedMessage);
            ctx.status(200);
        }

    }   
    private void updateMessagesByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message translatedMessage = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        System.out.println("Within the controller. This is the int assuming there's an id provided: " + message_id);
        System.out.println("This is the ctx translated into a message within the Controller: " + translatedMessage);
        Message message = messageService.updateMessage(message_id, translatedMessage);
        System.out.println("This is the processed message after putting it through the service: " + message);
        if(message == null) {
            ctx.status(400);
        }
        else {
            ctx.json(message);
        }
    }

    private void getMessagesByUser(Context ctx) throws JsonProcessingException{
        System.out.println("In the controller. Here's the ctx: " + ctx.pathParamMap());
        int posted_by = Integer.parseInt(ctx.pathParam("account_id"));
        System.out.println("In the controller. This is the posted_by id: " + posted_by);
        if (messageService.getAllMessagesByUser(posted_by) == null) {
            ctx.status(200);
        }
        else {
            ctx.json(messageService.getAllMessagesByUser(posted_by));
            ctx.status(200);
        }
    }


}