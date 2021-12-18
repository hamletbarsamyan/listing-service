package am.jsl.listings.dto.category;

import am.jsl.listings.dto.NamedDTO;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The CategoryAttributeLookupDTO is used for transferring category attributes.
 *
 * @author hamlet
 */
public class CategoryAttributeLookupDTO extends NamedDTO implements Serializable {

    /**
     * The category id
     */
    private long attributeId;

    /**
     * The parent attribute id
     */
    private long parentId;

    /**
     * The attribute type
     */
    private String attrType;

    /**
     * Attribute extra information
     */
    private String extraInfo;

    /**
     * Attribute values
     */
    private List<AttributeValueLookupDTO> attributeValues;

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
     * Gets parent id.
     *
     * @return the parent id
     */
    public long getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets attr type.
     *
     * @return the attr type
     */
    public String getAttrType() {
        return attrType;
    }

    /**
     * Sets attr type.
     *
     * @param attrType the attr type
     */
    public void setAttrType(String attrType) {
        this.attrType = attrType;
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

    /**
     * Gets attribute values.
     *
     * @return the attribute values
     */
    public List<AttributeValueLookupDTO> getAttributeValues() {
        return attributeValues;
    }

    /**
     * Sets attribute values.
     *
     * @param attributeValues the attribute values
     */
    public void setAttributeValues(List<AttributeValueLookupDTO> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
