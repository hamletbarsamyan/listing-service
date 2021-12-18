package am.jsl.listings.service.item;

import am.jsl.listings.dao.category.CategoryDao;
import am.jsl.listings.dao.item.ItemAddressDao;
import am.jsl.listings.dao.item.ItemAttributeDao;
import am.jsl.listings.dao.item.ItemDao;
import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.dto.Pair;
import am.jsl.listings.dto.item.*;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.ex.DuplicateSlugException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.item.ItemSearchQuery;
import am.jsl.listings.service.MultiLingualServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The service implementation of the {@link ItemService}.
 * @author hamlet
 */
@Service("itemService")
public class ItemServiceImpl extends MultiLingualServiceImpl<Item> implements ItemService {

    /**
     * The item dao.
     */
    private ItemDao itemDao;

    /**
     * The item attribute dao.
     */
    private ItemAttributeDao itemAttributeDao;

    /**
     * The item address dao.
     */
    private ItemAddressDao itemAddressDao;

    /**
     * The category dao.
     */
    private CategoryDao categoryDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(long id) throws CannotDeleteException {
        super.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Item item) throws Exception {
        item.setCreatedAt(new Date());
        item.setChangedAt(new Date());
        itemDao.create(item);
//        publish(category.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Item item) throws Exception {
        item.setChangedAt(new Date());
        itemDao.update(item);
//        publish(category.getUserId());
    }

    @Override
    public ListPaginatedResult<ItemListDTO> search(ItemSearchQuery itemSearchQuery) {
        try {
            return itemDao.search(itemSearchQuery);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ListPaginatedResult<>();
        }
    }

    @Override
    public ItemManageDTO getManageDTO(long userId, long itemId, String locale) {
        ItemManageDTO itemManageDTO = null;

        if (itemId != 0) {
            Item item = itemDao.getUserItem(userId, itemId);
            itemManageDTO = ItemManageDTO.from(item);
            List<DescriptiveTranslation> translations = (List<DescriptiveTranslation>) itemDao.getTranslations(itemId);
            itemManageDTO.setTranslations(translations);
        } else {
            itemManageDTO = new ItemManageDTO();
            itemManageDTO.setTranslations(new ArrayList<>());
        }
        List<DescriptiveTranslation> translations = itemManageDTO.getTranslations();

        Set<String> existingTranslations = translations.stream().map(Translation::getLocale)
                .collect(Collectors.toCollection(() -> new HashSet<>(translations.size())));

        // init missing translations
        List<Language> languages = languageService.list(locale);

        for (Language language : languages) {
            String langLocale = language.getLangLocale();

            if (!existingTranslations.contains(langLocale)) {
                translations.add(new AttributeTranslation(langLocale));
            }
        }

        return itemManageDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(ItemManageDTO itemManageDTO) {
        Item item = itemManageDTO.toItem();
        boolean isCreate = itemManageDTO.isCreate();
        long itemId = item.getId();
        Date current = new Date();
        item.setChangedAt(current);

        try {
            if (isCreate) {
                item.setCreatedAt(current);
                itemDao.create(item);
                itemId = item.getId();
            } else {
                itemDao.update(item);
            }

        } catch (DuplicateKeyException exception) {
            throw new DuplicateSlugException();
        }
        itemDao.saveTranslations(itemId, itemManageDTO.getTranslations());
        itemManageDTO.setId(itemId);
    }

    @Override
    public ItemViewDTO getViewDTO(long id, String locale) {
        ItemViewDTO itemViewDTO = itemDao.getViewDTO(id, locale);

        List<String> parentCategories = categoryDao.getCategoryTreeNames(itemViewDTO.getCategoryId(), locale);
        itemViewDTO.setParentCategories(parentCategories);

        List<ItemAttributeViewDTO> attributes = itemAttributeDao.getItemAttributeViewDTOs(id, locale);
        itemViewDTO.setAttributes(attributes);
        return itemViewDTO;
    }

    @Override
    public List<ItemAttributeManageDTO> getItemAttributes(long itemId, String locale) {
        return itemAttributeDao.getItemAttributes(itemId, locale);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveItemAttributes(long itemId, List<ItemAttribute> attributes) {
        itemAttributeDao.saveItemAttributes(itemId, attributes);
    }

    @Override
    public int getItemsCount() {
        return itemDao.getItemsCount();
    }

    @Override
    public List<Pair<String, Integer>> categoryItemsCount(String locale) {
        return itemDao.categoryItemsCount(locale);
    }

    /**
     * Setter for dao classes.
     *
     */
    @Autowired
    public void setDaos(@Qualifier("itemDao") ItemDao itemDao,
                        @Qualifier("itemAttributeDao") ItemAttributeDao itemAttributeDao,
                        @Qualifier("itemAddressDao") ItemAddressDao itemAddressDao,
                        @Qualifier("categoryDao") CategoryDao categoryDao) {
        this.itemDao = itemDao;
        this.itemAttributeDao = itemAttributeDao;
        this.itemAddressDao = itemAddressDao;
        this.categoryDao = categoryDao;
        setBaseDao(itemDao);
    }
}
