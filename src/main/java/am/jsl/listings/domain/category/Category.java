package am.jsl.listings.domain.category;


import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;
import java.util.Objects;

/**
 * The category domain object.
 *
 * @author hamlet
 */
public class Category extends Descriptive implements Serializable {

    /**
     * The slug of this category
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
     * The parent id of this category
     */
    private long parentId;

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
}
