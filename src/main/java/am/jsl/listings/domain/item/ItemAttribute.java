package am.jsl.listings.domain.item;

import am.jsl.listings.domain.attribute.Attribute;

import java.io.Serializable;

/**
 * The item attribute domain object.
 *
 * @author hamlet
 */
public class ItemAttribute extends Attribute implements Serializable {

    /**
     * The item id
     */
    private long itemId;

    /**
     * The attribute id
     */
    private long attributeId;

    /**
     * The value
     */
    private String value;

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets attribute id.
     *
     * @return the attribute id
     */
    public long getAttributeId() {
        return attributeId;
    }

    /**
     * Sets attribute id.
     *
     * @param attributeId the attribute id
     */
    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
