package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    private AccountDAO accountDAO;

    // Constructors
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    
    // Utilize the AccountDAO to add a user account
    public Account addAccount(Account account) {
        if(!account.getUsername().isBlank() && account.getPassword().length() > 4 && accountDAO.userLogin(account) == null) {
            return accountDAO.createAccount(account);
        }
        else {
            return null;
        }
    }

    // Allows the user to 'login' by search for the username and password
    public Account accountLogin(Account account) {
        if(accountDAO.userLogin(account) == null) {
            return null;
        }
        else {
            return accountDAO.userLogin(account);
        }
    }
}
