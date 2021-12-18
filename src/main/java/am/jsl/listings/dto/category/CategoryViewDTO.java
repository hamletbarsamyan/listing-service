package am.jsl.listings.dto.category;

import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;

import java.io.Serializable;
import java.util.List;

/**
 * The CategoryViewDTO is used for viewing categories and attributes.
 *
 * @author hamlet
 */
public class CategoryViewDTO extends BaseCategoryDTO implements Serializable {
    /**
     * The names fo parent categories
     */
    private List<String> parents;

    /**
     * The category attributes
     */
    private List<CategoryAttribute> attributes;

    /**
     * Constructs a new CategoryViewDTO from the given Category domain object.
     *
     * @param category the Category
     * @return the CategoryViewDTO
     */
    public static CategoryViewDTO from(Category category) {
        CategoryViewDTO viewDTO = new CategoryViewDTO();
        viewDTO.setId(category.getId());
        viewDTO.setSlug(category.getSlug());
        viewDTO.setName(category.getName());
        viewDTO.setSortOrder(category.getSortOrder());
        viewDTO.setParentId(category.getParentId());
        viewDTO.setIcon(category.getIcon());
        viewDTO.setDescription(category.getDescription());
        return viewDTO;
    }

    /**
     * Gets parents.
     *
     * @return the parents
     */
    public List<String> getParents() {
        return parents;
    }

    /**
     * Sets parents.
     *
     * @param parents the parents
     */
    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public List<CategoryAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<CategoryAttribute> attributes) {
        this.attributes = attributes;
    }
}
