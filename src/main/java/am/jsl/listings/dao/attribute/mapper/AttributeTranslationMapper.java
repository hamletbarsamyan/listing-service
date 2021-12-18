package am.jsl.listings.dao.attribute.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new AttributeTranslation instance.
 * @author hamlet
 */
public class AttributeTranslationMapper implements RowMapper<AttributeTranslation> {

	public AttributeTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttributeTranslation translation = new AttributeTranslation();
		translation.setId(rs.getLong(DBUtils.attribute_id));
		translation.setName(rs.getString(DBUtils.name));
		translation.setDescription(rs.getString(DBUtils.description));
		translation.setExtraInfo(rs.getString(DBUtils.extra_info));
		translation.setLocale(rs.getString(DBUtils.locale));
		return translation;
	}
}
