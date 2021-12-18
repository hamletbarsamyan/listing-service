package am.jsl.listings.dao.transaction.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.transaction.TransactionListTotalDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new TransactionListTotalDTO instance.
 * @author hamlet
 */
public class TransactionListTotalMapper implements RowMapper<TransactionListTotalDTO> {
    @Override
    public TransactionListTotalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionListTotalDTO total = new TransactionListTotalDTO();
        total.setTransactionType(rs.getByte(DBUtils.transaction_type));
        total.setTotal(rs.getDouble(DBUtils.total));
        total.setSymbol(rs.getString(DBUtils.symbol));
        return total;
    }
}
