package am.jsl.listings.dto.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains category tree data.
 *
 * @author hamlet
 */
public class CategoryTreeDTO extends BaseCategoryDTO implements Serializable {

    /**
     * The child categories.
     */
    private List<CategoryTreeDTO> children;


    /**
     * Gets children.
     *
     * @return the children
     */
    public List<CategoryTreeDTO> getChildren() {
        return children;
    }

    /**
     * Sets children.
     *
     * @param children the children
     */
    public void setChildren(List<CategoryTreeDTO> children) {
        this.children = children;
    }

    /**
     * Add child.
     *
     * @param category the category
     */
    public void addChild(CategoryTreeDTO category) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(category);
    }
}
