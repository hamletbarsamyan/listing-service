package am.jsl.listings.dao.attribute;


import am.jsl.listings.dao.MultiLingualDao;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;

import java.util.List;

/**
 * The Dao interface for accessing {@link AttributeValue} domain object.
 * @author hamlet
 */
public interface AttributeValueDao extends MultiLingualDao<AttributeValue> {

    /**
     * Returns attribute values by attribute id and locale.
     * @param attributeId the attribute id
     * @param locale the locale
     * @return the attribute values
     */
    List<AttributeValue> getAttributeValues(long attributeId, String locale);

    /**
     * Returns attribute values by attribute id.
     * @param attributeId the attribute id
     * @return the attribute values
     */
    List<AttributeValue> getAttributeValues(long attributeId);

    /**
     * Deletes attribute values by attribute id.
     * @param attributeId the attribute id
     */
    void deleteAttributeValues(long attributeId);

    /**
     * Saves the given attribute values.
     * @param attributeId the attribute id
     * @param attributeValues the attribute values
     */
    void saveAttributeValues(long attributeId, List<AttributeValueManageDTO> attributeValues);

    /**
     * Returns attribute values by attribute id and locale.
     * @param attributeId the attribute id
     * @param locale the locale
     * @return the attribute values
     */
    List<AttributeValueLookupDTO> lookupAttributeValues(long attributeId, String locale);
}
