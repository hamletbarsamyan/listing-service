package am.jsl.listings.dao.account;


import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.account.Account;

/**
 * The Dao interface for accessing {@link Account} domain object.
 * @author hamlet
 */
public interface AccountDao extends BaseDao<Account> {

    /**
     * Returns an account by user id.
     * @param userId the user id
     * @return the account
     */
    Account getUserAccount(long userId);

    /**
     * Decreases the balance of the given account.
     * @param id the account id
     * @param amount the amount to subtract from balance
     */
    void decreaseBalance(long id,  double amount);

    /**
     * Increases the balance of the given account.
     * @param id  the account id
     * @param amount the amount to add to balance
     */
    void increaseBalance(long id, double amount);
}
