package am.jsl.listings.dto.category;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The CategoryAttributesListDTO is used for transferring category attributes.
 *
 * @author hamlet
 */
public class CategoryAttributesListDTO extends NamedDTO implements Serializable {

    /**
     * The category attributes
     */
    private List<CategoryAttributeManageDTO> attributes;

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public List<CategoryAttributeManageDTO> getAttributes() {
        return attributes;
    }

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<CategoryAttributeManageDTO> attributes) {
        this.attributes = attributes;
    }
}
