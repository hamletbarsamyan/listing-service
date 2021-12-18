package am.jsl.listings.dto.item;

import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The ItemAttributesListDTO is used for transferring item attributes.
 *
 * @author hamlet
 */
public class ItemAttributesListDTO extends NamedDTO implements Serializable {

    /**
     * The item's category id
     */
    private long categoryId;

    /**
     * The item attributes
     */
    private List<ItemAttributeManageDTO> attributes;

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public List<ItemAttributeManageDTO> getAttributes() {
        return attributes;
    }

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<ItemAttributeManageDTO> attributes) {
        this.attributes = attributes;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
