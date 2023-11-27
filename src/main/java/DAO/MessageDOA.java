package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDOA {

    // Create a new message

    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
            catch(SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
    }

    // Retrieve all messages

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Retrieve a message by its ID

    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            return message;
        }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        

        return null;
    }

    //Delete a message by its ID

    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            return message;
        }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        

        return null;
    }

    //Update a message identified by its ID

    public Message updateMessageById(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, message.message_text);
        preparedStatement.setInt(1, message.message_id);

        preparedStatement.executeUpdate();
        return message;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Retrieve all messages by a particular user

    public List<Message> getAllMessagesByUser(int user_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
