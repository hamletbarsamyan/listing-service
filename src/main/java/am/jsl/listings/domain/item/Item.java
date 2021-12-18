package am.jsl.listings.domain.item;

import am.jsl.listings.domain.Descriptive;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The item domain object.
 *
 * @author hamlet
 */
public class Item extends Descriptive implements Serializable {

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
     * The user id
     */
    private long userId;

    /**
     * The item address
     */
    private ItemAddress itemAddress;

    /**
     * The item attributes
     */
    private List<ItemAttribute> attributes;

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
     * Gets item address.
     *
     * @return the item address
     */
    public ItemAddress getItemAddress() {
        return itemAddress;
    }

    /**
     * Sets item address.
     *
     * @param itemAddress the item address
     */
    public void setItemAddress(ItemAddress itemAddress) {
        this.itemAddress = itemAddress;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public List<ItemAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Sets attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(List<ItemAttribute> attributes) {
        this.attributes = attributes;
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
}
