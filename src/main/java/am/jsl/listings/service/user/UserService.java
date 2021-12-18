package am.jsl.listings.service.user;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.domain.user.VerificationToken;
import am.jsl.listings.domain.user.VerificationTokenType;
import am.jsl.listings.dto.user.PasswordResetDTO;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.dto.user.UserProfileDTO;
import am.jsl.listings.dto.user.UserViewDTO;
import am.jsl.listings.ex.InvalidTokenException;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;
import am.jsl.listings.service.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.util.Locale;

/**
 * Service interface extends Spring's UserDetailsService and defines all the methods for working with {@link User} domain object.
 *
 * @author hamlet
 */
public interface UserService extends UserDetailsService, BaseService<User> {

    /**
     * Retrieves paginated result of users for the given search query.
     *
     * @param userSearchQuery the {@link UserSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} object containing paged result
     */
    ListPaginatedResult<UserListDTO> search(UserSearchQuery userSearchQuery);

    /**
     * Sets last_login field with current date for the given user id.
     *
     * @param userId the user id
     */
    void login(long userId);

    /**
     * Updates the profile of the given user.
     *
     * @param user the user
     * @throws Exception will throw if exception occurs
     */
    void updateProfile(User user) throws Exception;

    /**
     * Returns an user with the given user name.
     * Will throw {@link UserNotFoundException} if user not found.
     *
     * @param name the user name
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    User getUser(String name) throws UserNotFoundException;

    /**
     * Encrypts the given password and updates in user.
     * Changes user password with the given encryptedPassword.
     *
     * @param newPassword the new password
     * @param id          the user id
     * @param login          the user login
     */
    void changePassword(String newPassword, long id, String login);

    /**
     * Updates the icon for the given user.
     *
     * @param user the user
     */
    void updateIcon(User user);

    /**
     * Generates and sends password reset email to the given email and locale.
     *
     * @param email  email address
     * @param locale the locale
     * @throws MessagingException if error occurs
     */
    void sendPasswordResetMail(String email, Locale locale)
            throws MessagingException;

    /**
     * Check whether the given token is valid for the given user
     * and returns VerificationToken object.
     * Will throw InvalidTokenException if token is invalid.
     *
     * @param userId    the user id
     * @param token     the token
     * @param tokenType token type
     * @return the VerificationToken object
     * @throws InvalidTokenException if token is invalid.
     */
    VerificationToken checkToken(long userId, String token, VerificationTokenType tokenType)
            throws InvalidTokenException;

    /**
     * Returns the VerificationToken with the user id and tokenType.
     *
     * @param userId    the user id
     * @param tokenType the token type
     * @return the VerificationToken
     */
    VerificationToken getToken(long userId, VerificationTokenType tokenType);

    /**
     * Resets the user password based on {@link PasswordResetDTO} object.
     *
     * @param passwordResetDTO the PasswordResetDTO
     * @throws InvalidTokenException if token is invalid
     */
    void resetPassword(PasswordResetDTO passwordResetDTO) throws InvalidTokenException;

    /**
     * Creates an user with inactive status and send confirm registration with activation link.
     *
     * @param user   the user
     * @param locale the Locale
     * @throws Exception if error occurs
     */
    void register(User user, Locale locale) throws Exception;

    /**
     * Confirms the user registration. If registration token is valid then sets the given user status to active
     * and expires verification token.
     * Will throw {@link InvalidTokenException} if token is invalid or expired
     * and {@link UserNotFoundException} if user not found.
     *
     * @param userId the user id
     * @param token  the token
     * @throws InvalidTokenException if token is invalid or expired
     * @throws UserNotFoundException if user not found
     */
    void confirmRegistration(Long userId, String token) throws InvalidTokenException, UserNotFoundException;

    /**
     * Returns user view dto by id.
     *
     * @param userId the user id
     * @return the UserViewDTO
     */
    UserViewDTO getViewDTO(long userId);

    /**
     * Returns an user with the given user name.
     * Will throw {@link UserNotFoundException} if user not found.
     *
     * @param username the user name
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    UserProfileDTO getUserProfile(String username) throws UserNotFoundException;

    /**
     * Returns total count of users.
     *
     * @return the users count
     */
    int getUsersCount();
}
