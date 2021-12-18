package am.jsl.listings.service.account;

import am.jsl.listings.domain.account.Account;
import am.jsl.listings.service.BaseService;

/**
 * Service interface which defines all the methods for working with {@link Account} domain object.
 * @author hamlet
 */
public interface AccountService extends BaseService<Account> {

    /**
     * Returns an account by user id.
     * @param userId the user id
     * @return the account
     */
    Account getUserAccount(long userId);
}
