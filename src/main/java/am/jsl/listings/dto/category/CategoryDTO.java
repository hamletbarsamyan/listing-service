package am.jsl.listings.dto.category;

import am.jsl.listings.domain.category.Category;
import am.jsl.listings.dto.DescriptiveDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains category information.
 *
 * @author hamlet
 */
public class CategoryDTO extends DescriptiveDTO implements Serializable {
    /**
     * The icon of category.
     */
    private String slug;

    /**
     * The icon of this category
     */
    private String icon;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * The parent id of category.
     */
    private long parentId;

    /**
     * The child categories.
     */
    private List<CategoryDTO> childs;

    /**
     * Gets slug.
     *
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Sets slug.
     *
     * @param slug the slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Getter for property 'parentId'.
     *
     * @return Value for property 'parentId'.
     */
    public long getParentId() {
        return parentId;
    }

    /**
     * Setter for property 'parentId'.
     *
     * @param parentId Value to set for property 'parentId'.
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Getter for property 'childs'.
     *
     * @return Value for property 'childs'.
     */
    public List<CategoryDTO> getChilds() {
        return childs;
    }

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof Category) {
            final Category other = (Category) o;
            return Objects.equals(getId(), other.getId())
                    && Objects.equals(getSlug(), other.getSlug());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSlug());
    }

    /**
     * Setter for property 'childs'.
     *
     * @param childs Value to set for property 'childs'.
     */
    public void setChilds(List<CategoryDTO> childs) {
        this.childs = childs;
    }

    /**
     * Add child.
     *
     * @param category the category
     */
    public void addChild(CategoryDTO category) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(category);
    }
}
