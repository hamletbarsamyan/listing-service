package am.jsl.listings.dao.item;


import am.jsl.listings.dao.MultiLingualDao;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.dto.Pair;
import am.jsl.listings.dto.item.ItemListDTO;
import am.jsl.listings.dto.item.ItemViewDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.item.ItemSearchQuery;

import java.util.List;
import java.util.Map;

/**
 * The Dao interface for accessing {@link Item} domain object.
 * @author hamlet
 */
public interface ItemDao extends MultiLingualDao<Item> {

    /**
     * Retrieves paginated result of items for the given search query.
     *
     * @param itemSearchQuery the {@link ItemSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} object containing paged result
     */
    ListPaginatedResult<ItemListDTO> search(ItemSearchQuery itemSearchQuery);

    /**
     * Returns item by user id and id
     * @param userId the user id
     * @param itemId the item id
     * @return the item
     */
    Item getUserItem(long userId, long itemId);

    /**
     * Returns ItemViewDTO by id and locale.
     * @param id the item id
     * @param locale the locale
     * @return the ItemViewDTO
     */
    ItemViewDTO getViewDTO(long id, String locale);

    /**
     * Returns total count of items.
     *
     * @return the items count
     */
    int getItemsCount();

    /**
     * Returns items count per category.
     *
     * @param locale the locale
     * @return items count per category
     */
    List<Pair<String, Integer>> categoryItemsCount(String locale);
}
