package am.jsl.listings.service.item;

import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.dto.Pair;
import am.jsl.listings.dto.item.ItemAttributeManageDTO;
import am.jsl.listings.dto.item.ItemListDTO;
import am.jsl.listings.dto.item.ItemManageDTO;
import am.jsl.listings.dto.item.ItemViewDTO;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.item.ItemSearchQuery;
import am.jsl.listings.service.MultiLingualService;

import java.util.List;
import java.util.Map;

/**
 * Service interface which defines all the methods for working with {@link Item} domain object.
 *
 * @author hamlet
 */
public interface ItemService extends MultiLingualService<Item> {

    /**
     * Retrieves paginated result of items for the given search query.
     *
     * @param itemSearchQuery the {@link ItemSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} object containing paged result
     */
    ListPaginatedResult<ItemListDTO> search(ItemSearchQuery itemSearchQuery);

    /**
     * Returns ItemManageDTO by id
     *
     * @param userId the user id
     * @param itemId the user id
     * @param locale the locale
     * @return the ItemManageDTO
     */
    ItemManageDTO getManageDTO(long userId, long itemId, String locale);

    /**
     * Creates or updates the item based on ItemManageDTO data.
     *
     * @param itemManageDTO the ItemManageDTO
     */
    void save(ItemManageDTO itemManageDTO);

    /**
     * Returns ItemViewDTO by id and locale.
     * @param id the item id
     * @param locale the locale
     * @return the ItemViewDTO
     */
    ItemViewDTO getViewDTO(long id, String locale);

    /**
     * Returns item attributes for given locale.
     * @param itemId the item id
     * @param locale the locale
     * @return list of item attributes
     */
    List<ItemAttributeManageDTO> getItemAttributes(long itemId, String locale);

    /**
     * Saves item attributes.
     * @param itemId the item id
     * @param attributes the attributes
     */
    void saveItemAttributes(long itemId, List<ItemAttribute> attributes);

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
