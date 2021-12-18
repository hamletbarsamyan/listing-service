package am.jsl.listings.dto.item;

import am.jsl.listings.dto.BaseDTO;
import am.jsl.listings.dto.NamedDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Contains item image data.
 *
 * @author hamlet
 */
public class ItemImageDTO extends BaseDTO implements Serializable {

    /**
     * The image file name
     */
    private String fileName;

    /**
     * The image file url
     */
    private String fileUrl;

    /**
     * The sort order
     */
    private int sortOrder;

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
     * Gets file url.
     *
     * @return the file url
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Sets file url.
     *
     * @param fileUrl the file url
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
