package am.jsl.listings.api.rest;

import am.jsl.listings.domain.Language;
import am.jsl.listings.service.language.LanguageService;
import am.jsl.listings.api.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * The LanguageController defines methods for viewing languages.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/languages")
@Lazy
public class LanguageController extends BaseController {
    /**
     * The languageService service
     */
    @Autowired
    private transient LanguageService languageService;

    /**
     * Returns languages.
     *
     * @return the response
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('login')")
    public Response<List<Language>> list() {
        List<Language> languages = languageService.list(getLocale());
        return Response.ok(languages);
    }

    /**
     * Returns locale / language name map.
     *
     * @return the response
     */
    @GetMapping("/nameMap")
    @PreAuthorize("hasAuthority('login')")
    public Response<Map<String, String>> nameMap() {
        Map<String, String> languageNameMap = languageService.getLanguageNameMap(getLocale());
        return Response.ok(languageNameMap);
    }
}
