package am.jsl.listings.dto.item;

import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.dto.TranslatedDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * The ItemManageDTO is used for managing items and translation fields.
 *
 * @author hamlet
 */
public class ItemManageDTO extends TranslatedDTO<DescriptiveTranslation> implements Serializable {
    /**
     * The category id of this item
     */
    private long categoryId;

    /**
     * The listing type (sale, rent, exchange, wanted)
     */
    private byte listingType;

    /**
     * The price of this item
     */
    private double price;

    /**
     * The currency code
     */
    private String currency;

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
     * The Universal Product Code
     */
    private String upc;

    /**
     * The Stock Keeping Unit
     */
    private String sku;

    /**
     * Flag indicating the status of this item
     */
    private boolean active = true;

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
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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
     * Gets upc.
     *
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * Sets upc.
     *
     * @param upc the upc
     */
    public void setUpc(String upc) {
        this.upc = upc;
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

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
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
     * Constructs a new ItemManageDTO from the given Item domain object.
     *
     * @param item the Item
     * @return the ItemManageDTO
     */
    public static ItemManageDTO from(Item item) {
        ItemManageDTO itemManageDTO = new ItemManageDTO();
        itemManageDTO.setId(item.getId());
        itemManageDTO.setCategoryId(item.getCategoryId());
        itemManageDTO.setListingType(item.getListingType());
        itemManageDTO.setPrice(item.getPrice());
        itemManageDTO.setCurrency(item.getCurrency());
        itemManageDTO.setPurchasePrice(item.getPurchasePrice());
        itemManageDTO.setPurchaseCurrency(item.getPurchaseCurrency());
        itemManageDTO.setInvCount(item.getInvCount());
        itemManageDTO.setUpc(item.getUpc());
        itemManageDTO.setSku(item.getSku());
        itemManageDTO.setActive(item.isActive());
        itemManageDTO.setCreatedAt(item.getCreatedAt());
        itemManageDTO.setChangedAt(item.getChangedAt());

        return itemManageDTO;
    }

    /**
     * Converts this item dto to Item domain object.
     *
     * @return the Item
     */
    public Item toItem() {
        Item item = new Item();
        item.setId(getId());
        item.setCategoryId(getCategoryId());
        item.setListingType(getListingType());
        item.setPrice(getPrice());
        item.setCurrency(getCurrency());
        item.setPurchasePrice(getPurchasePrice());
        item.setPurchaseCurrency(getPurchaseCurrency());
        item.setInvCount(getInvCount());
        item.setUpc(getUpc());
        item.setSku(getSku());
        item.setActive(isActive());
        item.setUserId(getUserId());
        return item;
    }
}
