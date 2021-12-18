package am.jsl.listings.domain.category;

import am.jsl.listings.domain.attribute.Attribute;

import java.io.Serializable;

/**
 * The category attribute domain object.
 *
 * @author hamlet
 */
public class CategoryAttribute extends Attribute implements Serializable {

    /**
     * The category id
     */
    private long categoryId;

    /**
     * The category id
     */
    private long attributeId;

    /**
     * The parent attribute id
     */
    private long parentId;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

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
}
