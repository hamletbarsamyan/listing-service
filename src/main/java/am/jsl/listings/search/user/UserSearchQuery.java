package am.jsl.listings.search.user;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.search.Query;

/**
 * Custom {@link Query} for searching of {@link User} items.
 *
 * @author hamlet
 */
public class UserSearchQuery extends Query<User> {

    /**
     * Instantiates a new User search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public UserSearchQuery(int page, int pageSize) {
        super(page, pageSize);
    }
}
