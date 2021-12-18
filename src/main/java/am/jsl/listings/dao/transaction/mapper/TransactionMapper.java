package am.jsl.listings.dao.transaction.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.transaction.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Transaction instance.
 * @author hamlet
 */
public class TransactionMapper implements RowMapper<Transaction> {

	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setId(rs.getLong(DBUtils.id));
		transaction.setAccountId(rs.getLong(DBUtils.account_id));
		transaction.setAmount(rs.getDouble(DBUtils.amount));
		transaction.setStatus(rs.getByte(DBUtils.status));
		transaction.setTransactionType(rs.getByte(DBUtils.transaction_type));
		transaction.setTransactionDate(rs.getTimestamp(DBUtils.transaction_date));
		transaction.setDescription(rs.getString(DBUtils.description));
		return transaction;
	}
}
