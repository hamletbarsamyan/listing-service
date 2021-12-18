package am.jsl.listings.dto.attribute;

import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.attribute.AttributeValueTranslation;
import am.jsl.listings.dto.TranslatedDTO;

import java.io.Serializable;

/**
 * The AttributeValueManageDTO is used for managing attribute value and translation fields.
 *
 * @author hamlet
 */
public class AttributeValueManageDTO extends TranslatedDTO<AttributeValueTranslation> implements Serializable {

    /**
     * Flag indicating the edit mode
     */
    private boolean edit;

    /**
     * The attribute id
     */
    private long attributeId;

    /**
     * The parent attribute value id
     */
    private long parentValueId;

    /**
     * The attribute value (default value)
     */
    private String value;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * Constructs a new AttributeValueManageDTO from the given AttributeValue domain object.
     *
     * @param attributeValue the AttributeValue
     * @return the AttributeValueManageDTO
     */
    public static AttributeValueManageDTO from(AttributeValue attributeValue) {
        AttributeValueManageDTO attributeManageDTO = new AttributeValueManageDTO();
        attributeManageDTO.setId(attributeValue.getId());
        attributeManageDTO.setAttributeId(attributeValue.getAttributeId());
        attributeManageDTO.setParentValueId(attributeValue.getParentValueId());
        attributeManageDTO.setValue(attributeValue.getValue());
        attributeManageDTO.setSortOrder(attributeValue.getSortOrder());
        return attributeManageDTO;
    }

    /**
     * Converts this attribute value dto to attribute value domain object.
     *
     * @return the AttributeValue
     */
    public AttributeValue toAttributeValue() {
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setId(getId());
        attributeValue.setAttributeId(getAttributeId());
        attributeValue.setParentValueId(getParentValueId());
        attributeValue.setValue(getValue());
        attributeValue.setSortOrder(getSortOrder());
        return attributeValue;
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
     * Gets parent value id.
     *
     * @return the parent value id
     */
    public long getParentValueId() {
        return parentValueId;
    }

    /**
     * Sets parent value id.
     *
     * @param parentValueId the parent value id
     */
    public void setParentValueId(long parentValueId) {
        this.parentValueId = parentValueId;
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

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Is edit boolean.
     *
     * @return the boolean
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * Sets edit.
     *
     * @param edit the edit
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
