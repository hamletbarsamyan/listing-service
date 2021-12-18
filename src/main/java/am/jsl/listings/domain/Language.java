package am.jsl.listings.domain;

import java.io.Serializable;

/**
 * The language domain object.
 *
 * @author hamlet
 */
public class Language extends Descriptive implements Serializable {
	/**
	 * The language ISO code.
 	 */
	private String code;

	/**
	 * The locale of this language
	 */
	private String langLocale;

	/**
	 * The file name of language logo
	 */
	private String logo;

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets lang locale.
	 *
	 * @return the lang locale
	 */
	public String getLangLocale() {
		return langLocale;
	}

	/**
	 * Sets lang locale.
	 *
	 * @param langLocale the lang locale
	 */
	public void setLangLocale(String langLocale) {
		this.langLocale = langLocale;
	}

	/**
	 * Gets logo.
	 *
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Sets logo.
	 *
	 * @param logo the logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
}