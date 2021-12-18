package am.jsl.listings.dao.account;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.account.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new Account instance.
 *
 * @author hamlet
 */
public class AccountMapper implements RowMapper<Account> {

	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setId(rs.getLong(DBUtils.id));
		account.setBalance(rs.getDouble(DBUtils.balance));
		account.setCurrency(rs.getString(DBUtils.currency));
		account.setSymbol(rs.getString(DBUtils.symbol));
		account.setUserId(rs.getLong(DBUtils.user_id));
		return account;
	}
}
