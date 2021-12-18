package am.jsl.listings.service.transaction;

import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Contains TransactionService tests.
 */
public class TransactionServiceTest extends BaseTest {

    /**
     * Executed before all TransactionServiceTest tests.
     *
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
        category = createCategory();
    }


    /**
     * Executed after all TransactionServiceTest tests.
     *
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
