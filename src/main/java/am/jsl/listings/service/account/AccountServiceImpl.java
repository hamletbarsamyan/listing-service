package am.jsl.listings.service.account;

import am.jsl.listings.dao.account.AccountDao;
import am.jsl.listings.domain.account.Account;
import am.jsl.listings.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The service implementation of the {@link AccountService}.
 * @author hamlet
 */
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
    private AccountDao accountDao;

    @Override
    public Account getUserAccount(long userId) {
        return accountDao.getUserAccount(userId);
    }

    /**
     * Setter for property 'accountDao'.
     *
     * @param accountDao Value to set for property 'accountDao'.
     */
    @Autowired
    public void setAccountDao(@Qualifier("accountDao") AccountDao accountDao) {
        setBaseDao(accountDao);
        this.accountDao = accountDao;
    }
}
