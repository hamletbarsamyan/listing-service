package am.jsl.listings.api.rest;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.log.AppLogger;
import am.jsl.listings.security.jwt.JwtTokenAuthenticationService;
import am.jsl.listings.service.user.UserService;
import am.jsl.listings.web.util.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * The base class for all controllers.
 * Contains common fields and methods.
 * @author hamlet
 */
public class BaseController implements Serializable {
    protected static final AppLogger log = new AppLogger(BaseController.class);

    /**
     * The user service
     */
    @Autowired
    protected transient UserService userService;

    @Autowired
    protected transient JwtTokenAuthenticationService jwtTokenAuthenticationService;

    /**
     * The internationalization message wrapper.
     * @see I18n
     */
    @Autowired
    protected transient I18n i18n;

    /**
     * Returns the current user from Spring Security Context.
     * @return the User
     */
    protected User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null && user.getId() == 0) {
            user = (User) userService.loadUserByUsername(user.getLogin());
        }
        return user;
    }

    /**
     * Returns the current locale from LocaleContextHolder.
     * @return the locale
     */
    protected String getLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale != null ? locale.toString() : Locale.getDefault().toString();
    }
}
