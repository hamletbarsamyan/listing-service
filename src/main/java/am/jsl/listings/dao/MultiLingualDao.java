package am.jsl.listings.dao;

import am.jsl.listings.domain.Translation;

import java.util.List;

/**
 * DAO interface for interacting with the Entity objects with multilingual fields
 * such as name, description which are stored in tables with _tr postfix.
 *
 * @param <T> the parametrisation entity type.
 * @author hamlet
 */
public interface MultiLingualDao<T> extends BaseDao<T> {

    /**
     * Returns all instances of the type for the given locale.
     *
     * @param locale the locale
     * @return the results
     */
    List<T> list(String locale);

    /**
     * Retrieves an entity by id and locale.
     *
     * @param id     the entity id
     * @param locale the locale
     * @return the entity
     */
    T get(long id, String locale);


    /**
     * Returns all instances of the type for the given locale.
     * Will be selected instance id and name.
     *
     * @param locale the locale
     * @return the result
     */
    List<T> lookup(String locale);

    /**
     * Returns all instances of the type by parent id for the given locale.
     * Will be selected instance id and name.
     *
     * @param parentId the parent id of entity
     * @param locale   the locale
     * @return the result
     */
    List<T> lookup(long parentId, String locale);

    /**
     * Returns all translations for the given entity id.
     *
     * @param entityId the entity id
     * @return all translations
     */
    List<? extends Translation> getTranslations(long entityId);

    /**
     * Saves the translations with the given entity id.
     *
     * @param entityId the entity id
     * @param translations the translations
     */
    void saveTranslations(long entityId, List<? extends Translation> translations);
}
