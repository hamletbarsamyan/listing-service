package am.jsl.listings.domain.item;


import am.jsl.listings.domain.BaseEntity;

import java.io.Serializable;

/**
 * The ItemImage domain object.
 *
 * @author hamlet
 */
public class ItemImage extends BaseEntity implements Serializable {

    /**
     * The item id
     */
    private long itemId;

    /**
     * The image file name
     */
    private String fileName;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
