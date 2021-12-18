package am.jsl.listings.dao.language;

import am.jsl.listings.dao.DBUtils;
import am.jsl.listings.domain.Language;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Language instance.
 * @author hamlet
 */
public class LanguageMapper implements RowMapper<Language> {

	public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
		Language language = new Language();
		language.setId(rs.getLong(DBUtils.id));
		language.setCode(rs.getString(DBUtils.code));
		language.setLogo(rs.getString(DBUtils.logo));
		language.setLangLocale(rs.getString(DBUtils.lang_locale));
		language.setName(rs.getString(DBUtils.name));
		language.setDescription(rs.getString(DBUtils.description));
		return language;
	}
}
