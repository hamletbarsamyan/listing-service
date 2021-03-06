package am.jsl.listings.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Contains constants and utility methods used in dao classes.
 * @author hamlet
 */
public class DBUtils {

	// common
	public static final String id = "id";
	public static final String name = "name";
	public static final String value = "value";
	public static final String value_tr = "value_tr";// value translation
	public static final String description = "description";
	public static final String created_by = "created_by";
	public static final String created_at = "created_at";
	public static final String changed_by = "changed_by";
	public static final String changed_at = "changed_at";
	public static final String enabled = "enabled";
	public static final String slug = "slug";
	public static final String locale = "locale";
	public static final String user_id = "user_id";
	public static final String parent_id = "parent_id";
	public static final String parent_name = "parent_name";
	public static final String balance = "balance";
	public static final String currency = "currency";
	public static final String active = "active";
	public static final String sort_order = "sort_order";
	public static final String account_id = "account_id";
	public static final String category_id = "category_id";
	public static final String parent_description = "parent_description";
	public static final String contact_id = "contact_id";
	public static final String contact = "contact";
	public static final String amount = "amount";
	public static final String paid = "paid";
	public static final String status = "status";
	public static final String transaction_type = "transaction_type";
	public static final String transaction_date = "transaction_date";
	public static final String transaction_source = "transaction_source";
	public static final String account = "account";
	public static final String category = "category";
	public static final String account_icon = "account_icon";
	public static final String account_color = "account_color";
	public static final String category_icon = "category_icon";
	public static final String category_color = "category_color";

	public static final String parent_category = "parent_category";
	public static final String parent_category_icon = "parent_category_icon";
	public static final String parent_category_color = "parent_category_color";

	public static final String attr_type = "attr_type";
	public static final String attribute_id = "attribute_id";
	public static final String parent_value_id = "parent_value_id";
	public static final String extra_info = "extra_info";
    public static final String attribute_value_id = "attribute_value_id";

	public static final String iso_code = "iso_code";
	public static final String symbol = "symbol";
	public static final String phone = "phone";
	public static final String email = "email";
	public static final String icon = "icon";
	public static final String thumbnail = "thumbnail";
	public static final String total = "total";
	public static final String offset = "offset";
	public static final String rows_per_page = "rowsPerPage";

	public static final String reminder_date = "reminder_date";
	public static final String due_date = "due_date";
	public static final String auto_charge = "auto_charge";
	public static final String reminder_repeat = "reminder_repeat";
	public static final String user_status = "user_status";
	public static final String reminder_id = "reminder_id";
	public static final String transaction_id = "transaction_id";

	// item
	public static final String item_id = "item_id";
	public static final String listing_type = "listing_type";
	public static final String price = "price";
	public static final String purchase_currency = "purchase_currency";
	public static final String purchase_price = "purchase_price";
	public static final String upc = "upc";
	public static final String inv_count = "inv_count";
	public static final String sku = "sku";
	public static final String country_id = "country_id";
	public static final String state_id = "state_id";
	public static final String city_id = "city_id";
	public static final String district_id = "district_id";
	public static final String zip = "zip";
	public static final String item_address_id = "item_address_id";
	public static final String address_line1 = "address_line1";
	public static final String address_line2 = "address_line2";
	public static final String file_name = "file_name";
	public static final String item_count = "item_count";

	// user, role, permission
	public static final String login = "login";
	public static final String password = "password";
	public static final String full_name = "full_name";
	public static final String last_login = "last_login";
	public static final String last_password_change = "last_password_change";
	public static final String role_id = "role_id";
	public static final String role = "role";
	public static final String token = "token";
	public static final String token_type = "token_type";
	public static final String expiry_date = "expiry_date";
	public static final String expired = "expired";
	public static final String default_role = "default_role";

	// language
	public static final String code = "code";
	public static final String lang_locale = "lang_locale";
	public static final String logo = "logo";

	private static final String SELECT_MAX_INDEX_PREFIX = "select max(id) from ";


    /**
	 * Returns max identifier plus 1 from the given database table.
	 * @param jdbcTemplate the JdbcTemplate
	 * @param tableName the table name
	 * @return the next identifier
	 */
    public static synchronized long getNextId(JdbcTemplate jdbcTemplate,
											  String tableName) {
        Long result = jdbcTemplate.queryForObject(SELECT_MAX_INDEX_PREFIX + tableName, Long.class);
		return ((result == null ? 0l : result) + 1);
	}
}
