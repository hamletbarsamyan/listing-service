package am.jsl.listings.dao.category;


import am.jsl.listings.dao.MultiLingualDao;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.category.*;

import java.util.List;
import java.util.Map;

/**
 * The Dao interface for accessing {@link Category} domain object.
 *
 * @author hamlet
 */
public interface CategoryDao extends MultiLingualDao<Category> {
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
     * Returns category tree names for the given category.
     *
     * @param id     the category id
     * @param locale the locale
     * @return returns category tree names
     */
    List<String> getCategoryTreeNames(long id, String locale);

    /**
     * Returns category parent tree ids for the given category.
     *
     * @param id the category id
     * @return returns category tree
     */
    List<Long> getCategoryTree(long id);

    /**
     * Saves the category tree for the given category id.
     *
     * @param id the category id
     * @param parentIds the parent ids
     */
    void saveCategoryTree(long id, List<Long> parentIds);

    /**
     * Returns category attributes for given locale.
     *
     * @param categoryId the category id
     * @param locale     the locale
     * @return list of category attributes
     */
    List<CategoryAttributeManageDTO> getCategoryAttributeManageDTOs(long categoryId, String locale);

    /**
     * Saves category attributes
     * @param categoryId the category di
     * @param attributes the attributes
     */
    void saveCategoryAttributes(long categoryId, List<CategoryAttributeManageDTO> attributes);

    /**
     * Looks up category attributes.
     *
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
