package am.jsl.listings.service.user;

import am.jsl.listings.dao.user.RoleDao;
import am.jsl.listings.dao.user.UserDao;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.domain.user.VerificationToken;
import am.jsl.listings.domain.user.VerificationTokenType;
import am.jsl.listings.dto.user.PasswordResetDTO;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.dto.user.UserProfileDTO;
import am.jsl.listings.dto.user.UserViewDTO;
import am.jsl.listings.ex.*;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;
import am.jsl.listings.service.BaseServiceImpl;
import am.jsl.listings.service.EmailService;
import am.jsl.listings.util.GenerateShortUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Locale;

/**
 * The service implementation of the {@link UserService}.
 *
 * @author hamlet
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Value("${listings.password.reset.url}")
    private String passwordResetUrl;

    @Value("${listings.user.activation.url}")
    private String activationUrl;

    /**
     * The user dao.
     */
    private UserDao userDao;

    /**
     * The user dao.
     */
    private RoleDao roleDao;

    /**
     * The password encoder.
     */
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    /**
     * The email service.
     */
    @Autowired
    private EmailService emailService;

    @Override
    public User getUser(String name) throws UserNotFoundException {
        return userDao.getUser(name);
    }

    @Override
    public ListPaginatedResult<UserListDTO> search(UserSearchQuery userSearchQuery) {
        try {
            return userDao.search(userSearchQuery);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ListPaginatedResult<>();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void login(long userId) {
        userDao.login(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", allEntries = true)})
    public void delete(long userId) throws CannotDeleteException {
        super.delete(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendPasswordResetMail(String email, Locale locale) throws MessagingException {
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            VerificationToken verificationToken = userDao.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);

            if (verificationToken == null) {
                verificationToken = new VerificationToken();
            }
            verificationToken.setUserId(user.getId());

            String token = GenerateShortUUID.next();
            verificationToken.updateToken(token);
            verificationToken.setTokenType(VerificationTokenType.PASSWORD_RESET.getValue());

            if (verificationToken.getId() == 0) {
                userDao.createVerificationToken(verificationToken);
            } else {
                userDao.updateVerificationToken(verificationToken);
            }

            final String resetPasswordLink = passwordResetUrl + "?id=" + user.getId() + "&token=" + token;
            emailService.sendPasswordResetMail(email, resetPasswordLink, locale);
        } else {
            log.debug("Email not found {}", email);
        }
    }

    @Override
    public VerificationToken checkToken(long userId, String token, VerificationTokenType tokenType) throws InvalidTokenException {
        VerificationToken verificationToken = userDao.getToken(userId, tokenType);

        if (verificationToken == null
                || !verificationToken.getToken().equals(token)
                || verificationToken.isExpired()) {
            throw new InvalidTokenException();
        }

        return verificationToken;
    }

    @Override
    public VerificationToken getToken(long userId, VerificationTokenType tokenType) {
        return userDao.getToken(userId, tokenType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#passwordResetDTO.login")})
    @Override
    public void resetPassword(PasswordResetDTO passwordResetDTO) throws InvalidTokenException {
        String token = passwordResetDTO.getToken();
        long userId = passwordResetDTO.getUserId();

        // check, change token to expired
        VerificationToken verificationToken = checkToken(userId, token, VerificationTokenType.PASSWORD_RESET);
        verificationToken.setExpired(true);
        userDao.updateVerificationToken(verificationToken);

        // change password
        String encryptedPassword = passwordEncoder.encode(passwordResetDTO.getNewPassword());
        userDao.changePassword(encryptedPassword, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void register(User user, Locale locale) throws Exception {

        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        String email = user.getEmail();

        if (userDao.emailExists(email, user.getId())) {
            throw new DuplicateEmailException();
        }
        Role defaultRole = roleDao.getDefaultRole();

        if (defaultRole == null) {
            throw new AppException("The default role not found for registering users");
        }

        // create user
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRoleId(defaultRole.getId());
        user.setCreatedAt(new Date());
        user.setChangedAt(new Date());
        user.setEnabled(false);
        userDao.create(user);

        // create verification token
        VerificationToken verificationToken = userDao.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);

        if (verificationToken == null) {
            verificationToken = new VerificationToken();
        }
        verificationToken.setUserId(user.getId());

        String token = GenerateShortUUID.next();
        verificationToken.updateToken(token);
        verificationToken.setTokenType(VerificationTokenType.NEW_ACCOUNT.getValue());

        if (verificationToken.getId() == 0) {
            userDao.createVerificationToken(verificationToken);
        } else {
            userDao.updateVerificationToken(verificationToken);
        }

        final String registrationConfirmLink = activationUrl + "?id=" + user.getId() + "&token=" + token;
        emailService.sendRegistrationMail(email, registrationConfirmLink, locale);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void confirmRegistration(Long userId, String token) throws InvalidTokenException, UserNotFoundException {
        VerificationToken verificationToken = userDao.getToken(userId, VerificationTokenType.NEW_ACCOUNT);

        if (verificationToken == null
                || !verificationToken.getToken().equals(token)
                || verificationToken.isExpired()) {
            throw new InvalidTokenException();
        }

        // enable user
        User user = userDao.get(userId);
        user.setEnabled(true);
        user.setChangedBy(user.getId());
        user.setChangedAt(new Date());
        userDao.update(user);

        // update verification
        verificationToken.setExpired(true);
        userDao.updateVerificationToken(verificationToken);
    }

    @Override
    public UserViewDTO getViewDTO(long userId) {
        return userDao.getViewDTO(userId);
    }

    @Override
    public UserProfileDTO getUserProfile(String username) {
        return userDao.getUserProfile(username);
    }

    @Override
    public int getUsersCount() {
        return userDao.getUsersCount();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void create(User user) throws Exception {
        if (user.getId() == 0) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.create(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void update(User user) throws Exception {
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    public void updateProfile(User user) throws Exception {
        if (userDao.loginExists(user.getLogin(), user.getId())) {
            throw new DuplicateUserException();
        }

        if (userDao.emailExists(user.getEmail(), user.getId())) {
            throw new DuplicateEmailException();
        }
        userDao.updateProfile(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#login")})
    @Override
    public void changePassword(String newPassword, long userId, String login) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        userDao.changePassword(encryptedPassword, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(value = "userByName", key = "#user.login")})
    @Override
    public void updateIcon(User user) {
        userDao.updateIcon(user);
    }

    @Override
    @Cacheable(value = "userByName", key = "#username")
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userDao.getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * Sets the daos.
     */
    @Autowired
    public void setDao(@Qualifier("userDao") UserDao userDao,
                       @Qualifier("roleDao") RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        setBaseDao(userDao);
    }
}
