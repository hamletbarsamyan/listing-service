package am.jsl.listings.dao.language;

import am.jsl.listings.dao.MultiLingualDaoImpl;
import am.jsl.listings.domain.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * The implementation of Dao interface for accessing {@link Language} domain object.
 *
 * @author hamlet
 */
@Repository("languageDao")
@Lazy
public class LanguageDaoImpl extends MultiLingualDaoImpl<Language> implements LanguageDao {
    private LanguageMapper languageMapper = new LanguageMapper();

    @Autowired
    LanguageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean canDelete(long id) {
        return false;
    }

    private static final String listSql = "select * from language l "
            + "left join language_tr tr on tr.language_id = l.id and tr.locale = :locale "
            + "order by l.lang_locale";
    @Override
    public List<Language> list(String locale) {
        return list(locale, listSql, languageMapper);
    }
}
