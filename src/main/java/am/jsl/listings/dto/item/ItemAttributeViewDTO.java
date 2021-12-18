package am.jsl.listings.dto.item;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;

/**
 * The ItemAttributeViewDTO is used for transferring item attributes.
 *
 * @author hamlet
 */
public class ItemAttributeViewDTO extends NamedDTO implements Serializable {
    /**
     * The attribute type
     */
    private String attributeType;

    /**
     * The value
     */
    private String value;

    /**
     * The extra info
     */
    private String extraInfo;

    /**
     * Gets attribute type.
     *
     * @return the attribute type
     */
    public String getAttributeType() {
        return attributeType;
    }

    /**
     * Sets attribute type.
     *
     * @param attributeType the attribute type
     */
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
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
     * Gets extra info.
     *
     * @return the extra info
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * Sets extra info.
     *
     * @param extraInfo the extra info
     */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
