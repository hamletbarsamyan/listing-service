package am.jsl.listings.dao.attribute.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.attribute.Attribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Attribute instance.
 * @author hamlet
 */
public class AttributeByLocaleMapper implements RowMapper<Attribute> {

	public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attribute attribute = new Attribute();
		attribute.setId(rs.getLong(DBUtils.id));
		attribute.setAttrType(rs.getString(DBUtils.attr_type));
		attribute.setName(rs.getString(DBUtils.name));
		attribute.setDescription(rs.getString(DBUtils.description));
		attribute.setExtraInfo(rs.getString(DBUtils.extra_info));
		attribute.setLocale(rs.getString(DBUtils.locale));
		return attribute;
	}
}
