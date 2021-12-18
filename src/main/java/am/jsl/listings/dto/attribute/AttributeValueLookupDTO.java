package am.jsl.listings.dto.attribute;

import am.jsl.listings.dto.BaseDTO;

import java.io.Serializable;

/**
 * The AttributeLookupValueDTO is used for transferring attribute value.
 *
 * @author hamlet
 */
public class AttributeValueLookupDTO extends BaseDTO implements Serializable {

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
     * The attribute value translation
     */
    private String valueTr;

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
     * Gets value tr.
     *
     * @return the value tr
     */
    public String getValueTr() {
        return valueTr;
    }

    /**
     * Sets value tr.
     *
     * @param valueTr the value tr
     */
    public void setValueTr(String valueTr) {
        this.valueTr = valueTr;
    }
}
