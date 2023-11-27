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
        app.get("/messages/{messag_id}", this::messagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/message/{message_id}", this::updateMessagesByIdHandler);
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
        if(createdAccount != null) {
            ctx.json(mapper.writeValueAsString(createdAccount));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }
    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.accountLogin(account);
        if(verifiedAccount != null) {
            ctx.json(mapper.writeValueAsString(verifiedAccount));
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }

    }
    private void newMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    private void messagesByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message retrievedMessage = messageService.getMessagebyId(message);
        ctx.json(mapper.writeValueAsString(retrievedMessage));
        ctx.status(200);

    }
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message deletedMessage = messageService.deleteMessage(message);
        ctx.json(mapper.writeValueAsString(deletedMessage));
        ctx.status(200);
    }   
    private void updateMessagesByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.updateMessage(message);
        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }
    private void getMessagesByUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        List<Message> retrievedMessage = messageService.getAllMessagesByUser(message);
        ctx.json(mapper.writeValueAsString(retrievedMessage));
        ctx.status(200);
    }


}