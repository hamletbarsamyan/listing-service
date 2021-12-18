package am.jsl.listings.dao.account;


import am.jsl.listings.dao.BaseDaoImpl;
import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Account} domain object.
 * @author hamlet
 */
@Repository("accountDao")
@Lazy
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {
    private AccountMapper accountMapper = new AccountMapper();
    @Autowired
    public AccountDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from account where id = :id";
    @Override
    public void delete(long id) {
        delete(id, deleteSql);
    }

    private static final String createSql = "insert into account "
            + "(id, balance, currency, symbol, user_id) "
            + "values(:id, :balance, :currency, :symbol, :user_id)";
    @Override
    public void create(Account account) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "account");
        account.setId(id);
        Map<String, Object> params = new HashMap<>(5);
        params.put(DBUtils.id, account.getId());
        params.put(DBUtils.balance, account.getBalance());
        params.put(DBUtils.currency, account.getCurrency());
        params.put(DBUtils.symbol, account.getSymbol());
        params.put(DBUtils.user_id, account.getUserId());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String decreaseBalanceSql = "update account "
            + "set balance = (balance - :amount) where uid = :id";

    private static final String getUserAccountSql = "select * from account where user_id = :user_id";
    @Override
    public Account getUserAccount(long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put(DBUtils.user_id, userId);

        try {
            return parameterJdbcTemplate.queryForObject(getUserAccountSql, params, accountMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void decreaseBalance(long id, double amount) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, id);
        params.put(DBUtils.amount, amount);
        parameterJdbcTemplate.update(decreaseBalanceSql, params);
    }

    private static final String increaseBalanceSql = "update account "
            + "set balance = (balance + :amount) where id = :id";
    @Override
    public void increaseBalance(long id, double amount) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(DBUtils.id, id);
        params.put(DBUtils.amount, amount);
        parameterJdbcTemplate.update(increaseBalanceSql, params);
    }
}
