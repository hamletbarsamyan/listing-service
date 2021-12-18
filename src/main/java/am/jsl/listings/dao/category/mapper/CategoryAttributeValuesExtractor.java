package am.jsl.listings.dao.category.mapper;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.dto.category.CategoryAttributeValueLookupDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The {@link ResultSetExtractor} implementation for extracting
 * results from a {@link ResultSet} to AttributeValues.
 *
 * @author hamlet
 */
public class CategoryAttributeValuesExtractor implements ResultSetExtractor<Map<Long, List<CategoryAttributeValueLookupDTO>>> {

	@Override
	public Map<Long, List<CategoryAttributeValueLookupDTO>> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Long, List<CategoryAttributeValueLookupDTO>> result = new HashMap<>();

		while (rs.next()) {
			long attributeId = rs.getLong(DBUtils.attribute_id);
			CategoryAttributeValueLookupDTO attributeValue = new CategoryAttributeValueLookupDTO();
			attributeValue.setId(String.valueOf(rs.getLong(DBUtils.id)));
			attributeValue.setAttributeId(attributeId);
			attributeValue.setValue(rs.getString(DBUtils.value));

			List<CategoryAttributeValueLookupDTO> attributeValues = result.computeIfAbsent(attributeId, k -> new ArrayList<>());
			attributeValues.add(attributeValue);
		}

		return result;
	}
}
