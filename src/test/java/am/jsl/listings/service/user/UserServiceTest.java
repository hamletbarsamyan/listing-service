package am.jsl.listings.service.user;

import am.jsl.listings.domain.user.Role;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.domain.user.VerificationToken;
import am.jsl.listings.domain.user.VerificationTokenType;
import am.jsl.listings.dto.user.PasswordResetDTO;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;
import am.jsl.listings.service.BaseTest;
import am.jsl.listings.util.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains UserService tests.
 */
public class UserServiceTest extends BaseTest {

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Executed before all UserServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create User Test")
    public void testCreateUser() throws Exception {
        log.info("Starting test for create user");
        User user = new User();
        user.setLogin(RandomStringUtils.randomAlphabetic(8));
        user.setPassword("testpassword");
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
        user.setFullName("Full Name");
        user.setCreatedAt(new Date());
        user.setChangedBy(USER_ID);
        user.setChangedAt(new Date());
        Role role = createRole();
        user.setRoleId(role.getId());
        user.setEnabled(true);
        userService.create(user);

        assertTrue(user.getId() > 0);

        log.info("Finished test for create user");
    }

    @Test
    @DisplayName("Register User Test")
    public void testRegisterUser() throws Exception {
        log.info("Starting test for register user");
        User user = new User();
        user.setLogin(RandomStringUtils.randomAlphabetic(8));
        user.setPassword("testpassword");
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
        user.setFullName("Full Name");
        user.setChangedBy(USER_ID);
        userService.register(user, Locale.getDefault());

        // validate user
        user = userService.get(user.getId());
        assertTrue(user.getId() > 0);
        assertFalse(user.isEnabled());

        Role defaultRole = roleService.getDefaultRole();
        assertEquals(defaultRole.getId(), user.getRoleId());

        // confirm registration
        VerificationToken verificationToken = userService.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);
        assertFalse(verificationToken.isExpired());
        userService.confirmRegistration(user.getId(), verificationToken.getToken());

        // validate user
        user = userService.get(user.getId());
        assertTrue(user.isEnabled());

        verificationToken = userService.getToken(user.getId(), VerificationTokenType.NEW_ACCOUNT);
        assertTrue(verificationToken.isExpired());

        log.info("Finished test for register user");
    }

    @Test
    @DisplayName("Update User Test")
    public void testUpdateUser() throws Exception {
        log.info("Starting test for update user");
        String login = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
        String fullName = "Full updated";
        String zip = "zip updated";
        boolean status = false;

        User user = createUser();

        // update user
        user.setLogin(login);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setZip(zip);
        user.setEnabled(status);

        Role role = createRole();
        user.setRoleId(role.getId());
        userService.update(user);

        // validate user
        user = userService.getUser(login);
        assertEquals(fullName, user.getFullName());
        assertEquals(email, user.getEmail());
        assertEquals(zip, user.getZip());
        assertEquals(status, user.isEnabled());
        assertEquals(role.getId(), user.getRoleId());
        log.info("Finished test for update user");
    }

    @Test
    @DisplayName("Update User Profile Test")
    public void testUpdateUserProfile() throws Exception {
        log.info("Starting test for update user profile");
        String login = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
        String fullName = "fullName updated";
        String zip = "zip updated";

        User user = createUser();
        long roleId = user.getRoleId();

        // update user
        user.setLogin(login);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setZip(zip);
        userService.updateProfile(user);

        // validate user
        user = userService.getUser(login);
        assertEquals(fullName, user.getFullName());
        assertEquals(email, user.getEmail());
        assertEquals(zip, user.getZip());
        assertTrue(user.isEnabled());
        assertEquals(roleId, user.getRoleId());

        log.info("Finished test for update user profile");
    }

    @Test
    @DisplayName("Reset Password Test")
    public void testResetPassword() throws Exception {
        log.info("Starting test for reset password");
        User user = createUser();
        String email = user.getEmail();
        String newPassword = "newpassword";

        userService.sendPasswordResetMail(email, Locale.getDefault());

        VerificationToken verificationToken = userService.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);
        assertFalse(verificationToken.isExpired());

        // reset password
        PasswordResetDTO passwordResetDTO = new PasswordResetDTO();
        passwordResetDTO.setUserId(user.getId());
        passwordResetDTO.setLogin(user.getLogin());
        passwordResetDTO.setToken(verificationToken.getToken());
        passwordResetDTO.setNewPassword(newPassword);
        passwordResetDTO.setReNewPassword(newPassword);
        userService.resetPassword(passwordResetDTO);

        // validate user
        verificationToken = userService.getToken(user.getId(), VerificationTokenType.PASSWORD_RESET);
        assertTrue(verificationToken.isExpired());
        user = (User) userService.loadUserByUsername(user.getLogin());

        assertTrue( passwordEncoder.matches(newPassword, user.getPassword()));

        log.info("Finished test for reset password");
    }

    @Test
    @DisplayName("Delete User Test")
    public void testDeleteUser() throws Exception {
        log.info("Starting test for delete user");

        User user = createUser();
        long userId = user.getId();

        userService.delete(userId);

        // validate user
        try {
            user = userService.get(userId);
            assertNull(user);
        } catch (UserNotFoundException e) {
            // skip, user was deleted
        }

        log.info("Finished test for delete user");
    }

    @Test
    @DisplayName("Search Users Test")
    public void testSearchUsers() {
        log.info("Starting test for search users");

        UserSearchQuery query = new UserSearchQuery(1, Constants.PAGE_SIZE);
        ListPaginatedResult<UserListDTO> result = userService.search(query);

        assertTrue(result.getTotal() > 0);
        log.info("Finished test for search users");
    }

    /**
     * Executed after all UserServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}