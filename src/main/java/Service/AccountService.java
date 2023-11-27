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

    public Account addAccount(Account account) {
        if(!account.getUsername().isEmpty() && account.getPassword().length() >= 4 && accountDAO.userLogin(account) == null) {
            return accountDAO.createAccount(account);
        }
        else {
            return null;
        }
    }

    public Account accountLogin(Account account) {
        if(accountDAO.userLogin(account) != null) {
            return accountDAO.userLogin(account);
        }
        return null;
    }
}
