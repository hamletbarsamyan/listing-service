package am.jsl.listings.service.account;

import am.jsl.listings.domain.account.Account;
import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains AccountService tests.
 */
public class AccountServiceTest extends BaseTest {

    /**
     * Executed before all AccountServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Account Test")
    public void testCreateAccount() throws Exception {
        log.info("Starting test for create account");
        Account account = new Account();
        account.setCurrency(CURRENCY_USD);
        account.setUserId(user.getId());
        accountService.create(account);

        assertTrue(account.getId() > 0);
        log.info("Finished test for create account");
    }

    @Test
    @DisplayName("Get User Account Test")
    public void testGetUserAccount() throws Exception {
        log.info("Starting test for get user account");
        Account account = new Account();
        account.setCurrency(CURRENCY_USD);
        account.setUserId(user.getId());
        accountService.create(account);

        Account userAccount = accountService.getUserAccount(user.getId());

        assertEquals(account.getId() , userAccount.getId());
        log.info("Finished test for get user account");
    }

    @Test
    @DisplayName("Delete Account Test")
    public void testDeleteAccount() throws Exception {
        log.info("Starting test for delete account");

        Account account = createAccount();
        long accountId = account.getId();

        accountService.delete(account.getId());

        // validate account
        account = accountService.get(accountId);
        assertNull(account);

        log.info("Finished test for delete account");
    }

    /**
     * Executed after all AccountServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
