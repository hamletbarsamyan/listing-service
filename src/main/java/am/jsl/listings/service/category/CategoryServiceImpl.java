package am.jsl.listings.service.category;

import am.jsl.listings.dao.attribute.AttributeValueDao;
import am.jsl.listings.dao.category.CategoryDao;
import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.category.*;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.ex.DuplicateSlugException;
import am.jsl.listings.service.MultiLingualServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The service implementation of the {@link CategoryService}.
 * @author hamlet
 */
@Service("categoryService")
public class CategoryServiceImpl extends MultiLingualServiceImpl<Category> implements CategoryService {

    /**
     * The category images server
     */
    @Value("${listings.category.img.server}")
    private String categoryImgServer;

    /**
     * The category dao.
     */
    private CategoryDao categoryDao;

    /**
     * The attribute value dao.
     */
    private AttributeValueDao attributeValueDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributeValues", allEntries = true)})
    public void delete(long id) throws CannotDeleteException {
        super.delete(id);
//        publish(userId);
    }

    @Cacheable(value = "categories", key = "#locale")
    @Override
    public List<Category> lookup(String locale) {
        return categoryDao.lookup(locale);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributeValues", allEntries = true)})
    @Override
    public void saveOrUpdateCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryDao.saveOrUpdateCategoryAttribute(categoryAttribute);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributeValues", allEntries = true)})
    @Override
    public void deleteCategoryAttribute(CategoryAttribute categoryAttribute) {
        categoryDao.deleteCategoryAttribute(categoryAttribute);
    }

    @Cacheable(value = "categoryAttributes", key = "#categoryId + '_' + #locale")
    @Override
    public List<CategoryAttribute> getCategoryAttributes(long categoryId, String locale) {
        return categoryDao.getCategoryAttributes(categoryId, locale);
    }

    @Override
    public List<CategoryAttributeManageDTO> getCategoryAttributeManageDTOs(long categoryId, String locale) {
        return categoryDao.getCategoryAttributeManageDTOs(categoryId, locale);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true)})
    @Override
    public void updateIcon(long categoryId, String icon) {
        categoryDao.updateIcon(categoryId, icon);
    }

    @Override
    public List<CategoryTreeDTO> getCategoryTree(String locale) {
        return categoryDao.getCategoryTree(locale);
    }

    @Override
    public CategoryManageDTO getManageDTO(long categoryId, String locale) {
        CategoryManageDTO categoryManageDTO = null;

        if (categoryId != 0) {
            Category attribute = categoryDao.get(categoryId);
            categoryManageDTO = CategoryManageDTO.from(attribute);
            List<DescriptiveTranslation> translations = (List<DescriptiveTranslation>) categoryDao.getTranslations(categoryId);
            categoryManageDTO.setTranslations(translations);
        } else {
            categoryManageDTO = new CategoryManageDTO();
            categoryManageDTO.setTranslations(new ArrayList<>());
        }
        List<DescriptiveTranslation> translations = categoryManageDTO.getTranslations();

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

        categoryManageDTO.setIconServer(categoryImgServer);
        categoryManageDTO.initIconPath();

        return categoryManageDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributeValues", allEntries = true)})
    @Override
    public void save(CategoryManageDTO categoryManageDTO) {
        Category category = categoryManageDTO.toCategory();
        boolean isCreate = categoryManageDTO.isCreate();
        long categoryId = category.getId();
        long parentId = category.getParentId();

        try {
            if (isCreate) {
                categoryDao.create(category);
                categoryId = category.getId();
            } else {
                if (parentId == categoryId) { // reset parent
                    parentId = 0;
                }
                category.setParentId(parentId);
                categoryDao.update(category);
            }

            List<Long> parentIds = new ArrayList<>();

            if (parentId != 0) {
                List<Long> parentTree = categoryDao.getCategoryTree(parentId);
                parentIds.addAll(parentTree);
                parentIds.add(parentId);
            }

            categoryDao.saveCategoryTree(categoryId, parentIds);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateSlugException();
        }
        categoryDao.saveTranslations(categoryId, categoryManageDTO.getTranslations());
        categoryManageDTO.setId(categoryId);
    }

    @Override
    public CategoryViewDTO getViewDTO(long id, String locale) {
        Category category = categoryDao.get(id, locale);
        CategoryViewDTO categoryViewDTO = CategoryViewDTO.from(category);
        categoryViewDTO.setIconServer(categoryImgServer);
        categoryViewDTO.initIconPath();

        List<String> parents;
        if (category.getParentId() != 0) {
            parents = categoryDao.getCategoryTreeNames(category.getId(), locale);
            categoryViewDTO.setParents(parents);
        } else {
            categoryViewDTO.setParents(Collections.emptyList());
        }

        List<CategoryAttribute> attributes = categoryDao.getCategoryAttributes(id, locale);
        categoryViewDTO.setAttributes(attributes);
        return categoryViewDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "categories", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributeValues", allEntries = true)})
    @Override
    public void saveCategoryAttributes(long categoryId, List<CategoryAttributeManageDTO> attributes) {
        categoryDao.saveCategoryAttributes(categoryId, attributes);
    }

    @Cacheable(value = "lookupCategoryAttributes", key = "#categoryId + '_' + #locale")
    @Override
    public List<CategoryAttributeLookupDTO> lookupAttributes(long categoryId, String locale) {
        List<CategoryAttributeLookupDTO> categoryAttributes = categoryDao.lookupAttributes(categoryId, locale);

        for (CategoryAttributeLookupDTO categoryAttribute : categoryAttributes) {
            if (AttributeType.LIST.name().equals(categoryAttribute.getAttrType())) {
                List<AttributeValueLookupDTO> attributeValues = attributeValueDao.lookupAttributeValues(categoryAttribute.getAttributeId(), locale);
                categoryAttribute.setAttributeValues(attributeValues);
            }
        }
        return categoryAttributes;
    }

    @Cacheable(value = "lookupCategoryAttributeValues", key = "#categoryId + '_' + #locale")
    @Override
    public Map<Long, List<CategoryAttributeValueLookupDTO>> lookupAttributeValues(long categoryId, String locale) {
        return categoryDao.lookupAttributeValues(categoryId, locale);
    }

    @Override
    public int getCategoriesCount() {
        return categoryDao.getCategoriesCount();
    }

    /**
     * Generates a html representation of categories for the given user id.
     * @param userId the user id
     */
//    private void publish(long userId) {
//        if (!publishHtml) {
//            log.info("Publish html is disabled");
//            return;
//        }
//
//        List<CategoryDTO> categories = getCategories(userId);
//
//        StringBuilder html = new StringBuilder();
//        html.append("<option value='0'></option>");
//        long id;
//        String icon;
//        String color;
//
//        for (CategoryDTO category : categories) {
//            id = category.getId();
////            icon = category.getIcon();
////            icon = icon == null ? "" : icon;
////            color = category.getColor();
////            color = color == null ? "" : color;
//
//            html.append("<option value='").append(id).append("'>");
////            html.append("<span class=\"categoryCircle\"").append(" style=\"background-color:").append(color).append("\">");
////            html.append("<span>").append(icon).append("</span></span>").append("&nbsp;");
//            html.append("<span>").append(category.getName());
//            html.append("</span></option>");
//
//            List<CategoryDTO> childs = category.getChilds();
//
//            if (childs == null) {
//                continue;
//            }
//
//            for (CategoryDTO child : childs) {
//                id = child.getId();
////                icon = child.getIcon();
////                icon = icon == null ? "" : icon;
//
//                html.append("<option value='").append(id).append("'>").append("&nbsp;&nbsp;&nbsp;");
////                html.append("<span>").append(icon).append("</span>").append("&nbsp;");
//                html.append("<span>").append(child.getName());
//                html.append("</span></option>");
//            }
//        }
//
//        publish(userId, CATEGORY_LOOKUP_HTML, html.toString());
//    }


    /**
     * Injects the given dao classes.
     * @param categoryDao the CategoryDao
     * @param attributeValueDao the AttributeValueDao
     */
    @Autowired
    public void setDaos(@Qualifier("categoryDao") CategoryDao categoryDao,
                        @Qualifier("attributeValueDao") AttributeValueDao attributeValueDao) {
        this.categoryDao = categoryDao;
        this.attributeValueDao = attributeValueDao;
        setBaseDao(categoryDao);
    }
}
