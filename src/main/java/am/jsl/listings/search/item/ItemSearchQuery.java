package am.jsl.listings.search.item;

import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.search.Query;

/**
 * Custom {@link Query} for searching of {@link UserListDTO} items.
 *
 * @author hamlet
 */
public class ItemSearchQuery extends Query<UserListDTO> {

    /**
     * The category yd
     */
    private long categoryId;

    /**
     * The item name
     */
    private String name;

    /**
     * The item upc
     */
    private String upc;

    /**
     * The item status(-1 - not set, 1 - active, 0 - inactive)
     */
    private int status = -1;
    /**
     * The user id
     */
    private long userId;

    /**
     * The locale
     */
    private String locale;

    /**
     * Instantiates a new Item search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public ItemSearchQuery(int page, int pageSize) {
        super(page, pageSize);
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
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
     * Gets locale.
     *
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
