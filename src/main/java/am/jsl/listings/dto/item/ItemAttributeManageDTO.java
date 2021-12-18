package am.jsl.listings.dto.item;

import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.item.ItemAttribute;

import java.io.Serializable;
import java.util.List;

/**
 * The ItemAttributeManageDTO is used for managing Item attribute.
 *
 * @author hamlet
 */
public class ItemAttributeManageDTO extends ItemAttribute implements Serializable {

    /**
     * The attribute values
     */
    private List<AttributeValue> attributeValues;

    /**
     * Gets attribute values.
     *
     * @return the attribute values
     */
    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    /**
     * Sets attribute values.
     *
     * @param attributeValues the attribute values
     */
    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
