package am.jsl.listings.dto.category;

import am.jsl.listings.domain.category.CategoryAttribute;

import java.io.Serializable;

/**
 * The CategoryAttributeManageDTO is used for managing category attribute.
 *
 * @author hamlet
 */
public class CategoryAttributeManageDTO extends CategoryAttribute implements Serializable {

    /**
     * Flag indicating the edit mode
     */
    private boolean edit;

    /**
     * The parent attribute name
     */
    private String parent;

    /**
     * The parent attribute description
     */
    private String parentDescription;

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

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param parent the parent
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * Gets parent description.
     *
     * @return the parent description
     */
    public String getParentDescription() {
        return parentDescription;
    }

    /**
     * Sets parent description.
     *
     * @param parentDescription the parent description
     */
    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
    }
}
