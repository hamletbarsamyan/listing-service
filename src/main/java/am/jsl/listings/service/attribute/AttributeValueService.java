package am.jsl.listings.service.attribute;

import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;
import am.jsl.listings.service.MultiLingualService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link AttributeValue} domain object.
 *
 * @author hamlet
 */
public interface AttributeValueService extends MultiLingualService<AttributeValue> {

    /**
     * Returns attribute values by attribute id and locale.
     *
     * @param attributeId the attribute id
     * @param locale      the locale
     * @return the attribute values
     */
    List<AttributeValue> getAttributeValues(long attributeId, String locale);

    /**
     * Returns attribute values with all translations by attribute id.
     *
     * @param attributeId the attribute id
     * @param locale      the locale
     * @return the attribute values
     */
    List<AttributeValueManageDTO> getAttributeManageValues(long attributeId, String locale);

    /**
     * Saves attribute values with all translations.
     *
     * @param attributeId the attribute id
     * @param attributeValues      the attributeValues
     */
    void saveAttributeValues(long attributeId, List<AttributeValueManageDTO> attributeValues);
}
