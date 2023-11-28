package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    //Constructors
    public MessageService() {
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if(!message.message_text.isBlank() && message.message_text.length() < 255 /*&& accountDAO.verifyAccount(message.posted_by) != null*/) {
            return messageDAO.createMessage(message);
        }
        else {
            return null;
        }
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessagebyId(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessage(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message updateMessage(int message_id, Message message) {
        if(messageDAO.getMessageById(message_id) != null && message.getMessage_text().length() < 255 && !message.getMessage_text().isBlank()) {
            return messageDAO.updateMessageById(message_id, message);
        }
        else {
            return null;
        }
    }

    public List<Message> getAllMessagesByUser(int account_id) {
        System.out.println("In the service layer. This is the return from getAllMessagesByUser: " + messageDAO.getAllMessagesByUser(account_id));
        return messageDAO.getAllMessagesByUser(account_id);
    }
}
