package Service;

import Model.Message;
import Model.Account;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

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
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(int message_id, String message_text) {
        if(messageDAO.getMessageById(message_id) != null && message_text.length() < 255 && message_text != null) {
            return messageDAO.updateMessageById(message_id, message_text);
        }
        else {
            return null;
        }
    }

    public List<Message> getAllMessagesByUser(Message message) {
        return messageDAO.getAllMessagesByUser(message);
    }
}
