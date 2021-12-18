package am.jsl.listings.dto.attribute;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The AttributeValuesListDTO is used for transferring attribute values with translations.
 *
 * @author hamlet
 */
public class AttributeValuesListDTO extends NamedDTO implements Serializable {

    /**
     * The attribute values
     */
    private List<AttributeValueManageDTO> attributeValues;

    /**
     * Gets attribute values.
     *
     * @return the attribute values
     */
    public List<AttributeValueManageDTO> getAttributeValues() {
        return attributeValues;
    }

    /**
     * Sets attribute values.
     *
     * @param attributeValues the attribute values
     */
    public void setAttributeValues(List<AttributeValueManageDTO> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
