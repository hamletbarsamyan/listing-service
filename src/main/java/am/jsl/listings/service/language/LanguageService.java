package am.jsl.listings.service.language;

import am.jsl.listings.domain.Language;
import am.jsl.listings.service.MultiLingualService;

import java.util.Map;

/**
 * Service interface which defines all the methods for working with {@link Language} domain object.
 *
 * @author hamlet
 */
public interface LanguageService extends MultiLingualService<Language> {

    /**
     * Returns locale / language name map by locale.
     *
     * @param locale the locale
     * @return the language name map
     */
    Map<String, String> getLanguageNameMap(String locale);
}
