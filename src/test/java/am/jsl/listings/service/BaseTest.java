package am.jsl.listings.service;

import am.jsl.listings.domain.Currency;
import am.jsl.listings.domain.account.Account;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.log.AppLogger;
import am.jsl.listings.service.account.AccountService;
import am.jsl.listings.service.attribute.AttributeService;
import am.jsl.listings.service.attribute.AttributeValueService;
import am.jsl.listings.service.category.CategoryService;
import am.jsl.listings.service.currency.CurrencyService;
import am.jsl.listings.service.item.ItemService;
import am.jsl.listings.service.language.LanguageService;
import am.jsl.listings.service.transaction.TransactionService;
import am.jsl.listings.service.user.PermissionService;
import am.jsl.listings.service.user.RoleService;
import am.jsl.listings.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Base test with common fields and methods.
 * 
 * @author hamlet
 */
@SpringBootTest(classes = { TestApplicationContext.class })
@ActiveProfiles("test")
@Rollback
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
	protected final AppLogger log = new AppLogger(this.getClass());

	/** Default user id for testing */
	protected static final long USER_ID = 1L;

	/** Default locale id for testing */
	protected static final String locale = "en_US";// EN

	protected static final String CURRENCY_USD = "USD";
	protected static final String CURRENCY_EUR = "EUR";

	protected User user;
	protected Account account;
	protected Category category;
	protected Currency currency;

	@Autowired
	protected PermissionService permissionService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	protected AttributeService attributeService;

	@Autowired
	protected AttributeValueService attributeValueService;

	@Autowired
	protected CurrencyService currencyService;

	@Autowired
	protected TransactionService transactionService;

	@Autowired
	protected ItemService itemService;

	@Autowired
	protected LanguageService languageService;

	/**
	 * Creates a new role.
	 *
	 * @return the Role.
	 */
	protected Role createRole() throws Exception {
		List<Permission> permissions = permissionService.list();
		Permission defaultPermission = permissions.get(0);

		Role role = new Role();
		role.setName(RandomStringUtils.randomAlphabetic(8));
		role.setDescription("test description");
		role.setPermissions(Collections.singletonList(defaultPermission.getId()));

		roleService.create(role);

		return role;
	}

	/**
	 * Creates a new user.
	 * 
	 * @return the User.
	 */
	protected User createUser() throws Exception {
		Role role = createRole();
		User user = new User();
		user.setLogin(RandomStringUtils.randomAlphabetic(8));
		user.setPassword("testpassword");
		user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@gmail.com");
		user.setFullName("Full Name");
		user.setCreatedAt(new Date());
		user.setChangedBy(USER_ID);
		user.setChangedAt(new Date());
		user.setRoleId(role.getId());
		user.setZip("zip");
		user.setEnabled(true);
		userService.create(user);

		return user;
	}

	/**
	 * Creates a new account.
	 * @return the Account
	 */
	protected Account createAccount() throws Exception {
		Account account = new Account();
		account.setBalance(100);
		account.setCurrency(CURRENCY_USD);
		account.setUserId(user.getId());
		accountService.create(account);

		return account;
	}

	/**
	 * Creates a new Category.
	 * @return the Category
	 */
	protected Category createCategory() throws Exception {
		Category category = new Category();
		category.setName("test category");
		category.setSlug("slug");
		category.setDescription("description");
		category.setParentId(0);
		category.setLocale(locale);
		categoryService.create(category);

		return category;
	}

    /**
     * Creates a new Attribute.
     * @return the Attribute
     */
    protected Attribute createAttribute() throws Exception {
        Attribute attribute = new Attribute();
        attribute.setAttrType(AttributeType.TEXT.name());
        attribute.setName("Test Attribute");
        attribute.setDescription("Test Attribute Description");
        attribute.setExtraInfo("test extra info");
        attribute.setLocale(locale);

        attributeService.create(attribute);

        return attribute;
    }

	/**
	 * Creates a new AttributeValue.
	 * @return the AttributeValue
	 */
	protected AttributeValue createAttributeValue() throws Exception {
		Attribute attribute = createAttribute();
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttributeId(attribute.getId());
		attributeValue.setValue("value1");
		attributeValue.setValueTr("value1 translation");
		attributeValue.setSortOrder(0);
		attributeValue.setLocale(locale);
		attributeValueService.create(attributeValue);

		return attributeValue;
	}

	/**
	 * Creates a new Currency.
	 * @return the Currency
	 */
	protected Currency createCurrency() throws Exception {
		Currency currency = new Currency();
		currency.setCode("TST");
		currency.setName("Test Currency");
		currency.setSymbol("t");
		currencyService.create(currency);

		return currency;
	}

	/**
	 * Deletes all temporary objects.
	 * @throws Exception if failed
	 */
	public void cleanUp() throws Exception {
		if (account != null) {
			accountService.delete(account.getId());
		}

		if (category != null) {
			categoryService.delete(category.getId());
		}

		if (currency != null) {
			currencyService.delete(currency.getCode());
		}

		if (user != null) {
			userService.delete(user.getId());
			roleService.delete(user.getRoleId());

		}
	}
}
