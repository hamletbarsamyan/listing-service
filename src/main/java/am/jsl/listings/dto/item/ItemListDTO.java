package am.jsl.listings.dto.item;

import am.jsl.listings.util.ItemUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * The ItemListDTO used for transferring item list data.
 *
 * @author hamlet
 */
public class ItemListDTO extends BaseItemDTO implements Serializable {

    /**
     * The category name of this item
     */
    private String category;

    /**
     * The thumbnail of this item
     */
    private String thumbnail;

    /**
     * The thumbnail path
     */
    private String thumbnailPath;

    /**
     * Initializes the icon path from server and icon.
     */
    public void initThumbnailPathPath(String imageServer, String thumbnail) {
        if (StringUtils.hasText(thumbnail)) {
            String path = ItemUtils.getIconPath(imageServer, thumbnail, getId());
            setThumbnailPath(path);
        }
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets thumbnail.
     *
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets thumbnail.
     *
     * @param thumbnail the thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Gets thumbnail path.
     *
     * @return the thumbnail path
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    /**
     * Sets thumbnail path.
     *
     * @param thumbnailPath the thumbnail path
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
