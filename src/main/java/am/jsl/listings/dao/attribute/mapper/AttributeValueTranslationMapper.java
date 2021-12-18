package am.jsl.listings.dao.attribute.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.attribute.AttributeValueTranslation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new AttributeValueTranslation instance.
 * @author hamlet
 */
public class AttributeValueTranslationMapper implements RowMapper<AttributeValueTranslation> {

	public AttributeValueTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
		AttributeValueTranslation translation = new AttributeValueTranslation();
		translation.setId(rs.getLong(DBUtils.attribute_value_id));
		translation.setValueTr(rs.getString(DBUtils.value_tr));
		translation.setLocale(rs.getString(DBUtils.locale));
		return translation;
	}
}
