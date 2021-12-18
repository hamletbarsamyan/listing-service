package am.jsl.listings.dto.attribute;

import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.dto.TranslatedDTO;

import java.io.Serializable;

/**
 * The AttributeManageDTO is used for managing attribute and translation fields.
 *
 * @author hamlet
 */
public class AttributeManageDTO extends TranslatedDTO<AttributeTranslation> implements Serializable {
    /**
     * The attribute type
     */
    private String attributeType;

    /**
     * Constructs a new AttributeManageDTO from the given Attribute domain object.
     *
     * @param attribute the Attribute
     * @return the AttributeManageDTO
     */
    public static AttributeManageDTO from(Attribute attribute) {
        AttributeManageDTO attributeManageDTO = new AttributeManageDTO();
        attributeManageDTO.setId(attribute.getId());
        attributeManageDTO.setAttributeType(attribute.getAttrType());
        return attributeManageDTO;
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
     * Converts this attribute dto to attribute domain object.
     *
     * @return the Attribute
     */
    public Attribute toAttribute() {
        Attribute attribute = new Attribute();
        attribute.setId(getId());
        attribute.setAttrType(getAttributeType());
        return attribute;
    }
}
