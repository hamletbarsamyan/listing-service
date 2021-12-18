package am.jsl.listings.dto.item;

import am.jsl.listings.domain.item.ItemAttribute;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The ItemViewDTO is used for viewing items and attributes.
 *
 * @author hamlet
 */
public class ItemViewDTO extends BaseItemDTO implements Serializable {
    /**
     * The names fo parent categories
     */
    private List<String> parentCategories;

    /**
     * The category of this item
     */
    private String category;

    /**
     * The category id of this item
     */
    private long categoryId;

    /**
     * The listing type (sale, rent, exchange, wanted)
     */
    private byte listingType;

    /**
     * The purchase price of this item
     */
    private double purchasePrice;

    /**
     * The purchase currency code
     */
    private String purchaseCurrency;

    /**
     * The inventory count
     */
    private double invCount;

    /**
     * The Stock Keeping Unit
     */
    private String sku;

    /**
     * The creation date
     */
    private Date createdAt;

    /**
     * The last changed date
     */
    private Date changedAt;

    /**
     * The the user id
     */
    private long userId;

    /**
     * The description
     */
    private String description;

    /**
     * The item attributes
     */
    private List<ItemAttributeViewDTO> attributes;

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets listing type.
     *
     * @return the listing type
     */
    public byte getListingType() {
        return listingType;
    }

    /**
     * Sets listing type.
     *
     * @param listingType the listing type
     */
    public void setListingType(byte listingType) {
        this.listingType = listingType;
    }

    /**
     * Gets purchase price.
     *
     * @return the purchase price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets purchase price.
     *
     * @param purchasePrice the purchase price
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Gets purchase currency.
     *
     * @return the purchase currency
     */
    public String getPurchaseCurrency() {
        return purchaseCurrency;
    }

    /**
     * Sets purchase currency.
     *
     * @param purchaseCurrency the purchase currency
     */
    public void setPurchaseCurrency(String purchaseCurrency) {
        this.purchaseCurrency = purchaseCurrency;
    }

    /**
     * Gets inv count.
     *
     * @return the inv count
     */
    public double getInvCount() {
        return invCount;
    }

    /**
     * Sets inv count.
     *
     * @param invCount the inv count
     */
    public void setInvCount(double invCount) {
        this.invCount = invCount;
    }

    /**
     * Gets sku.
     *
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * Sets sku.
     *
     * @param sku the sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets changed at.
     *
     * @return the changed at
     */
    public Date getChangedAt() {
        return changedAt;
    }

    /**
     * Sets changed at.
     *
     * @param changedAt the changed at
     */
    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets parent categories.
     *
     * @return the parent categories
     */
    public List<String> getParentCategories() {
        return parentCategories;
    }

    /**
     * Sets parent categories.
     *
     * @param parentCategories the parent categories
     */
    public void setParentCategories(List<String> parentCategories) {
        this.parentCategories = parentCategories;
    }

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public List<ItemAttributeViewDTO> getAttributes() {
        return attributes;
    }

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<ItemAttributeViewDTO> attributes) {
        this.attributes = attributes;
    }
}
