package am.jsl.listings.dao.user;

import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.domain.user.VerificationToken;
import am.jsl.listings.domain.user.VerificationTokenType;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.dto.user.UserProfileDTO;
import am.jsl.listings.dto.user.UserViewDTO;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;

/**
 * The Dao interface for accessing {@link User} domain object.
 * @author hamlet
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Retrieves paginated result of users for the given search query.
     * @param userSearchQuery the {@link UserSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} object containing paged result
     */
    ListPaginatedResult<UserListDTO> search(UserSearchQuery userSearchQuery);

    /**
     * Sets last_login field with current date for the given user id.
     * @param userId the user id
     */
    void login(long userId);

    /**
     * Checks whether an user with the given login and id exists.
     * @param login the login
     * @param id user id
     * @return true if user login exists
     */
    boolean loginExists(String login, long id);

    /**
     * Checks whether an user with the given email and id exists.
     * @param email the emails
     * @param id the user id
     * @return true if user email exists
     */
    boolean emailExists(String email, long id);

    /**
     * Changes user password with the given encryptedPassword.
     * @param encryptedPassword the encrypted password
     * @param userId the user id
     */
    void changePassword(String encryptedPassword, long userId);

    /**
     * Returns an user with the given user name.
     * Will throw {@link UserNotFoundException} if user not found.
     * @param username the user name
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    User getUser(String username);

    /**
     * Returns an user with the given user name.
     * Will throw {@link UserNotFoundException} if user not found.
     * @param username the user name
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    UserProfileDTO getUserProfile(String username);

    /**
     * Updates the icon for the given user.
     * @param user the user
     */
    void updateIcon(User user);

    /**
     * Updates the profile of the given user.
     * @param user the user
     */
    void updateProfile(User user) throws Exception;

    /**
     * Returns an user with the given email.
     * @param email the emails
     * @return the user
     */
    User getUserByEmail(String email);

    /**
     * Creates the given {@link VerificationToken}
     * @param verificationToken the VerificationToken
     */
    void createVerificationToken(VerificationToken verificationToken);

    /**
     * Updates the given {@link VerificationToken}
     * @param verificationToken the VerificationToken
     */
    void updateVerificationToken(VerificationToken verificationToken);

    /**
     * Returns the VerificationToken with the user id and tokenType.
     * @param userId the user id
     * @param tokenType the token type
     * @return the VerificationToken
     */
    VerificationToken getToken(long userId, VerificationTokenType tokenType);

    /**
     * Returns user view dto by id.
     * @param userId the user id
     * @return the UserViewDTO
     */
    UserViewDTO getViewDTO(long userId);

    /**
     * Returns total count of users.
     *
     * @return the users count
     */
    int getUsersCount();

}
