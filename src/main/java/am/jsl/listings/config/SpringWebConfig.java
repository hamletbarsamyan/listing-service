package am.jsl.listings.config;

import am.jsl.listings.util.CategoryUtils;
import am.jsl.listings.util.Constants;
import am.jsl.listings.util.ItemUtils;
import am.jsl.listings.util.UserUtils;
import am.jsl.listings.web.converter.StringToLocalDateTimeConverter;
import am.jsl.listings.web.util.I18n;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Spring managed Spring MVC configuration.
 *
 * @author hamlet
 */
@Configuration
@EnableWebMvc
public class SpringWebConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    private static final List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("hy", "AM"));

    /**
     * Users images directory.
     */
    @Value("${listings.user.img.dir}")
    private String userImgDir;

    /**
     * User html directory.
     */
    @Value("${listings.html.dir}")
    private String userHtmlDir;

    /**
     * Categories images directory.
     */
    @Value("${listings.category.img.dir}")
    private String categoryImgDir;

    /**
     * Item images directory.
     */
    @Value("${listings.item.img.dir}")
    private String itemImgDir;


    /**
     * The default constructor.
     */
    public SpringWebConfig() {
        super();
    }

    /**
     * Creates the {@link StandardServletMultipartResolver}
     *
     * @return the StandardServletMultipartResolver
     */
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Enables forwarding to the "default" Servlet.
     *
     * @param configurer the DefaultServletHandlerConfigurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("listingsDefaultServlet");
    }


    /**
     * Creates {@link ResourceBundleMessageSource} for accessing resource bundles using specified base names.
     *
     * @return the ResourceBundleMessageSource
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setDefaultEncoding(Constants.UTF8);
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    /**
     * Creates the {@link I18n} instance wrapped with Spring MessagesSource.
     *
     * @return the I18n
     */
    @Bean
    public I18n i18n() {
        I18n i18n = new I18n();
        i18n.setMessageSource(messageSource());
        return i18n;
    }

    /**
     * Creates new {@link StringToLocalDateTimeConverter}.
     *
     * @return the StringToLocalDateTimeConverter
     */
    @Bean
    public StringToLocalDateTimeConverter stringToLocalDateTimeConverter() {
        return new StringToLocalDateTimeConverter();
    }

    /**
     * Dispatcher configuration for serving static resources.
     *
     * @param registry the ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(UserUtils.USER_IMG_PATHPATTERN).addResourceLocations("file:" + userImgDir);
        registry.addResourceHandler(UserUtils.USER_HTML_PATHPATTERN).addResourceLocations("file:" + userHtmlDir);
        registry.addResourceHandler(CategoryUtils.CATEGORY_IMG_PATHPATTERN).addResourceLocations("file:" + categoryImgDir);
        registry.addResourceHandler(ItemUtils.ITEM_IMG_PATHPATTERN).addResourceLocations("file:" + itemImgDir);
    }

    /**
     * Creates the {@link ResourceUrlEncodingFilter} instance.
     *
     * @return the ResourceUrlEncodingFilter
     */
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        ResourceUrlEncodingFilter filter = new ResourceUrlEncodingFilter();
        return filter;
    }

    /**
     * Creates the {@link StringHttpMessageConverter} instance.
     *
     * @return the StringHttpMessageConverter
     */
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName(Constants.UTF8));
    }
}
