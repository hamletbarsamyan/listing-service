package am.jsl.listings.service;

import am.jsl.listings.dao.MultiLingualDao;
import am.jsl.listings.service.language.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * The service implementation of the {@link MultiLingualService}.
 * @author hamlet
 * @param <T> the parametrisation entity type.
 */
public abstract class MultiLingualServiceImpl<T> extends BaseServiceImpl<T> implements MultiLingualService<T> {

    /**
     * The multilingual dao.
     */
    private MultiLingualDao<T> multiLingualDao;

    /**
     * The languageService service
     */
    @Autowired
    @Lazy
    protected LanguageService languageService;

    @Override
    public List<T> list(String locale) {
        return multiLingualDao.list(locale);
    }

    @Override
    public T get(long id, String locale) {
        return multiLingualDao.get(id, locale);
    }

    @Override
    public List<T> lookup(String locale) {
        return multiLingualDao.lookup(locale);
    }

    @Override
    public List<T> lookup(long parentId, String locale) {
        return multiLingualDao.lookup(parentId, locale);
    }

    @Override
    public void createTranslation(T object) {
    }

    @Override
    public int updateTranslation(T object) {
        return 0;
    }

    /**
     * Setter for property 'multiLingualDao'.
     *
     * @param multiLingualDao Value to set for property 'multiLingualDao'.
     */
    public void setBaseDao(MultiLingualDao<T> multiLingualDao) {
        super.setBaseDao(multiLingualDao);
        this.multiLingualDao = multiLingualDao;
    }
}
