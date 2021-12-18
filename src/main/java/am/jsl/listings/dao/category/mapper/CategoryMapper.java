package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.category.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Category instance.
 * @author hamlet
 */
public class CategoryMapper implements RowMapper<Category> {

	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		category.setId(rs.getLong(DBUtils.id));
		category.setSlug(rs.getString(DBUtils.slug));
		category.setIcon(rs.getString(DBUtils.icon));
		category.setSortOrder(rs.getInt(DBUtils.sort_order));
		category.setParentId(rs.getLong(DBUtils.parent_id));
		return category;
	}
}
