package am.jsl.listings.dto.category;

import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.dto.TranslatedDTO;
import am.jsl.listings.util.CategoryUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * The CategoryManageDTO is used for managing categories and translation fields.
 *
 * @author hamlet
 */
public class CategoryManageDTO extends TranslatedDTO<DescriptiveTranslation> implements Serializable {
    /**
     * The slug
     */
    private String slug;

    /**
     * The user server
     */
    private String iconServer;

    /**
     * The icon of this category
     */
    private String icon;

    /**
     * The icon path
     */
    private String iconPath;

    /**
     * The sort order
     */
    private int sortOrder;

    /**
     * The parent id of category.
     */
    private long parentId;

    /**
     * Gets slug.
     *
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Sets slug.
     *
     * @param slug the slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Getter for property 'parentId'.
     *
     * @return Value for property 'parentId'.
     */
    public long getParentId() {
        return parentId;
    }

    /**
     * Setter for property 'parentId'.
     *
     * @param parentId Value to set for property 'parentId'.
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Gets sort order.
     *
     * @return the sort order
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets sort order.
     *
     * @param sortOrder the sort order
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Initializes the icon path from server and icon.
     */
    public void initIconPath() {
        String path = CategoryUtils.getIconPath(iconServer, icon, getId());
        setIconPath(path);
    }

    /**
     * Gets icon path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets icon path.
     *
     * @param iconPath the icon path
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * Gets icon server.
     *
     * @return the icon server
     */
    public String getIconServer() {
        return iconServer;
    }

    /**
     * Sets icon server.
     *
     * @param iconServer the icon server
     */
    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }

    /**
     * Constructs a new CategoryManageDTO from the given Category domain object.
     *
     * @param category the Category
     * @return the CategoryManageDTO
     */
    public static CategoryManageDTO from(Category category) {
        CategoryManageDTO categoryManageDTO = new CategoryManageDTO();
        categoryManageDTO.setId(category.getId());
        categoryManageDTO.setSlug(category.getSlug());
        categoryManageDTO.setIcon(category.getIcon());
        categoryManageDTO.setSortOrder(category.getSortOrder());
        categoryManageDTO.setParentId(category.getParentId());
        return categoryManageDTO;
    }

    /**
     * Converts this category dto to Category domain object.
     *
     * @return the Category
     */
    public Category toCategory() {
        Category category = new Category();
        category.setId(getId());
        category.setSlug(getSlug());
        category.setIcon(getIcon());
        category.setSortOrder(getSortOrder());
        category.setParentId(getParentId());
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof Category) {
            final Category other = (Category) o;
            return Objects.equals(getId(), other.getId())
                    && Objects.equals(getSlug(), other.getSlug());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSlug());
    }
}
