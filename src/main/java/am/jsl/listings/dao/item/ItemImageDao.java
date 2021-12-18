package am.jsl.listings.dao.item;


import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.dao.MultiLingualDao;
import am.jsl.listings.domain.item.ItemAddress;
import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.dto.item.ItemImageDTO;

import java.util.List;

/**
 * The Dao interface for accessing {@link ItemImage} domain object.
 * @author hamlet
 */
public interface ItemImageDao extends BaseDao<ItemImage> {

    /**
     * Returns item images.
     * @param itemId the item id
     * @return the item images
     */
    List<ItemImageDTO> getItemImages(long itemId);
}
