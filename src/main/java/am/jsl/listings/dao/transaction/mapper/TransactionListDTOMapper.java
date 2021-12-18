package am.jsl.listings.dao.transaction.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.dto.transaction.TransactionListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new TransactionListDTO instance.
 * @author hamlet
 */
public class TransactionListDTOMapper implements RowMapper<TransactionListDTO> {

	public TransactionListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TransactionListDTO listDTO = new TransactionListDTO();
		listDTO.setId(rs.getLong(DBUtils.id));

		listDTO.setSymbol(rs.getString(DBUtils.symbol));
		listDTO.setCategory(rs.getString(DBUtils.category));
		listDTO.setCategoryIcon(rs.getString(DBUtils.category_icon));
		listDTO.setCategoryColor(rs.getString(DBUtils.category_color));

		listDTO.setParentCategory(rs.getString(DBUtils.parent_category));
		listDTO.setParentCategoryIcon(rs.getString(DBUtils.parent_category_icon));
		listDTO.setParentCategoryColor(rs.getString(DBUtils.parent_category_color));

		listDTO.setAmount(rs.getDouble(DBUtils.amount));
		listDTO.setTransactionType(rs.getByte(DBUtils.transaction_type));
		listDTO.setTransactionDate(rs.getTimestamp(DBUtils.transaction_date));
		return listDTO;
	}
}
