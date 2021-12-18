package am.jsl.listings.service.item;

import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.dto.item.ItemImageDTO;
import am.jsl.listings.service.BaseService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link ItemImage} domain object.
 *
 * @author hamlet
 */
public interface ItemImageService extends BaseService<ItemImage> {

    /**
     * Returns item images.
     *
     * @param itemId the item id
     * @return the item images
     */
    List<ItemImageDTO> getItemImages(long itemId);
}
