package am.jsl.listings.dao.item.mapper;


import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.item.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new Item instance.
 *
 * @author hamlet
 */
public class ItemByLocaleMapper implements RowMapper<Item> {

	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item item = new Item();
		item.setId(rs.getLong(DBUtils.id));
		item.setCategoryId(rs.getLong(DBUtils.category_id));
		item.setName(rs.getString(DBUtils.name));
		return item;
	}
}
