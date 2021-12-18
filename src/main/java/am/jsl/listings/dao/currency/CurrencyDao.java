package am.jsl.listings.dao.currency;

import am.jsl.listings.dao.BaseDao;
import am.jsl.listings.domain.Currency;

import java.util.List;

/**
 * The Dao interface for accessing {@link Currency} domain object.
 * @author hamlet
 */
public interface CurrencyDao extends BaseDao<Currency> {
    /**
     * Returns a currency by code.
     * @return the currency
     */
    Currency getByCode(String isoCode);

    /**
     * Returns all currencies.
     * @return list of currencies
     */
    List<Currency> list();

    /**
     * Deletes a currency with the given code.
     * @param code the currency code
     */
    void delete(String code);
}
