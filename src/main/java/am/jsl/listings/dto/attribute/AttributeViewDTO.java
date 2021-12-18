package am.jsl.listings.dto.attribute;

import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.dto.DescriptiveDTO;
import am.jsl.listings.dto.TranslatedDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The AttributeViewDTO is used for viewing attribute and attribute values.
 *
 * @author hamlet
 */
public class AttributeViewDTO extends DescriptiveDTO implements Serializable {
    /**
     * The attribute type
     */
    private String attributeType;

    /**
     * Attribute extra information
     */
    private String extraInfo;

    /**
     * The attribute values
     */
    private List<AttributeValue> attributeValues;

    /**
     * Constructs a new AttributeViewDTO from the given Attribute domain object.
     *
     * @param attribute the Attribute
     * @return the AttributeViewDTO
     */
    public static AttributeViewDTO from(Attribute attribute) {
        AttributeViewDTO attributeViewDTO = new AttributeViewDTO();
        attributeViewDTO.setId(attribute.getId());
        attributeViewDTO.setAttributeType(attribute.getAttrType());
        attributeViewDTO.setName(attribute.getName());
        attributeViewDTO.setExtraInfo(attribute.getExtraInfo());
        attributeViewDTO.setDescription(attribute.getDescription());
        return attributeViewDTO;
    }

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
