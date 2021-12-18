package am.jsl.listings.service.language;

import am.jsl.listings.dao.language.LanguageDao;
import am.jsl.listings.domain.Language;
import am.jsl.listings.service.MultiLingualServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The service implementation of the {@link LanguageService}.
 *
 * @author hamlet
 */
@Service("languageService")
public class LanguageServiceImpl extends MultiLingualServiceImpl<Language> implements LanguageService {

    /**
     * The language dao.
     */
    private LanguageDao languageDao;

    @Cacheable(value = "languages", key = "#locale")
    @Override
    public List<Language> list(String locale) {
        return languageDao.list(locale);
    }

    /**
     * Setter for property 'languageDao'.
     *
     * @param languageDao Value to set for property 'languageDao'.
     */
    @Autowired
    public void setLanguageDao(@Qualifier("languageDao") LanguageDao languageDao) {
        this.languageDao = languageDao;
        setBaseDao(languageDao);
    }

    @Cacheable(value = "languageNameMap", key = "#locale")
    @Override
    public Map<String, String> getLanguageNameMap(String locale) {
        List<Language> languages = languageDao.list(locale);
        LinkedHashMap<String, String> result = new LinkedHashMap<>(languages.size());

        for (Language language : languages) {
            result.put(language.getLangLocale(), language.getName());
        }

        return result;
    }
}
