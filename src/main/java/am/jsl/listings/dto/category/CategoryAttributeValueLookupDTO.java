package am.jsl.listings.dto.category;

import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The CategoryAttributeLookupDTO is used for transferring category attribute values.
 *
 * @author hamlet
 */
public class CategoryAttributeValueLookupDTO implements Serializable {

    /**
     * The internal identifier
     */
    private String id;

    /**
     * The attribute id
     */
    private long attributeId;

    /**
     * The parent value id
     */
    private long parentValueId;

    /**
     * The value
     */
    private String value;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
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
}
