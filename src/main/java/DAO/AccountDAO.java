package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {
    // Process new user registerations

        public Account createAccount(Account account) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                while(rs.next()) {
                    int generated_account_id = (int) rs.getInt(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            }
                catch(SQLException e) {
                    System.out.println("Error in the createAccount method of DAO: " + e.getMessage());
                }
                return null;
        }

        public Account userLogin(Account account) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    Account existingAccount = new Account(rs.getInt("account_id"),
                                rs.getString("username"),
                                rs.getString("password"));
                    return existingAccount;
                }
            }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
                return null;
        }

        public Account verifyAccount(int account_id) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "SELECT username, password FROM Account WHERE account_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                preparedStatement.setInt(1, account_id);
                
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    Account existingAccount = new Account(rs.getInt("account_id"),
                                rs.getString("username"),
                                rs.getString("password"));
                    return existingAccount;
                }
            }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
                return null;
        }
}
