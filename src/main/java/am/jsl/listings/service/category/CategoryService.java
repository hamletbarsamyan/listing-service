package am.jsl.listings.service.category;

import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.category.*;
import am.jsl.listings.service.MultiLingualService;

import java.util.List;
import java.util.Map;

/**
 * Service interface which defines all the methods for working with {@link Category} domain object.
 *
 * @author hamlet
 */
public interface CategoryService extends MultiLingualService<Category> {
    /**
     * Crates or updates the category attribute.
     *
     * @param categoryAttribute the CategoryAttribute
     */
    void saveOrUpdateCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * Deletes category attribute.
     *
     * @param categoryAttribute the CategoryAttribute
     */
    void deleteCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * Returns category attributes for given locale.
     *
     * @param categoryId the category id
     * @param locale     the locale
     * @return list of category attributes
     */
    List<CategoryAttribute> getCategoryAttributes(long categoryId, String locale);

    /**
     * Returns category attributes for given locale.
     *
     * @param categoryId the category id
     * @param locale     the locale
     * @return list of category attributes
     */
    List<CategoryAttributeManageDTO> getCategoryAttributeManageDTOs(long categoryId, String locale);

    /**
     * Updates the icon for the given category.
     *
     * @param categoryId the category id
     * @param icon       the icon
     */
    void updateIcon(long categoryId, String icon);

    /**
     * Returns category tree for given locale.
     *
     * @param locale the locale
     * @return the category tree
     */
    List<CategoryTreeDTO> getCategoryTree(String locale);

    /**
     * Returns CategoryManageDTO by id
     *
     * @param categoryId the categoryId
     * @param locale     the locale
     * @return the CategoryManageDTO
     */
    CategoryManageDTO getManageDTO(long categoryId, String locale);

    /**
     * Creates or updates the category based on CategoryManageDTO data.
     *
     * @param categoryManageDTO the CategoryManageDTO
     */
    void save(CategoryManageDTO categoryManageDTO);

    /**
     * Returns category view dto by id and locale.
     * @param id the category id
     * @param locale the locale
     * @return the CategoryViewDTO
     */
    CategoryViewDTO getViewDTO(long id, String locale);

    /**
     * Saves category attributes.
     * @param categoryId the category id
     * @param attributes the attributes
     */
    void saveCategoryAttributes(long categoryId, List<CategoryAttributeManageDTO> attributes);

    /**
     * Lookups category attributes.
     * @param categoryId the category id
     * @param locale the locale
     * @return the category attributes
     */
    List<CategoryAttributeLookupDTO> lookupAttributes(long categoryId, String locale);

    /**
     * Returns map of attribute id/ attribute values by the given category.
     * @param categoryId the category id
     * @param locale the locale
     * @return the map of attribute id / values
     */
    Map<Long, List<CategoryAttributeValueLookupDTO>> lookupAttributeValues(long categoryId, String locale);

    /**
     * Returns total count of categories.
     *
     * @return the categories count
     */
    int getCategoriesCount();
}
