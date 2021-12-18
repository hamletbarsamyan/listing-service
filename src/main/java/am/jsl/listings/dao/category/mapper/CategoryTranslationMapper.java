package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.DescriptiveTranslation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new DescriptiveTranslation instance.
 * @author hamlet
 */
public class CategoryTranslationMapper implements RowMapper<DescriptiveTranslation> {

	public DescriptiveTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
		DescriptiveTranslation translation = new DescriptiveTranslation();
		translation.setId(rs.getLong(DBUtils.category_id));
		translation.setName(rs.getString(DBUtils.name));
		translation.setDescription(rs.getString(DBUtils.description));
		translation.setLocale(rs.getString(DBUtils.locale));
		return translation;
	}
}
