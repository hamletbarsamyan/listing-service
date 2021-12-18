package am.jsl.listings.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The TranslatedDTO is used for transferring object with the list of translation fields.
 *
 * @param <T> the type parameter
 * @author hamlet
 */
public class TranslatedDTO<T> extends BaseDTO implements Serializable {
    /**
     * The list of translations
     */
    protected List<T> translations;

    /**
     * Gets translations.
     *
     * @return the translations
     */
    public List<T> getTranslations() {
        return translations;
    }

    /**
     * Sets translations.
     *
     * @param translations the translations
     */
    public void setTranslations(List<T> translations) {
        this.translations = translations;
    }
}
