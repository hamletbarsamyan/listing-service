package am.jsl.listings.dao.item;


import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.dto.item.ItemAttributeManageDTO;
import am.jsl.listings.dto.item.ItemAttributeViewDTO;

import java.util.List;

/**
 * The Dao interface for accessing {@link ItemAttribute} domain object.
 * @author hamlet
 */
public interface ItemAttributeDao extends BaseDao<ItemAttribute> {

    /**
     * Saves item attributes.
     * @param itemId long item id
     * @param attributes list of item attributes
     */
    void saveItemAttributes(long itemId, List<ItemAttribute> attributes);

    /**
     * Returns item attributes.
     * @param itemId the item id
     * @return list of item attributes
     */
    List<ItemAttributeManageDTO> getItemAttributes(long itemId, String locale);

    /**
     * Returns item attributes.
     * @param itemId the item id
     * @return list of item attributes
     */
    List<ItemAttributeViewDTO> getItemAttributeViewDTOs(long itemId, String locale);
}
