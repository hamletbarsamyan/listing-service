package am.jsl.listings.service.attribute;

import am.jsl.listings.dao.attribute.AttributeDao;
import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.Translation;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.dto.attribute.AttributeManageDTO;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.service.MultiLingualServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The service implementation of the {@link AttributeService}.
 * @author hamlet
 */
@Service("attributeService")
public class AttributeServiceImpl extends MultiLingualServiceImpl<Attribute> implements AttributeService {

    /**
     * The Attribute dao.
     */
    private AttributeDao attributeDao;

    @Override
    public AttributeManageDTO getManageDTO(long attributeId, String locale) {
        AttributeManageDTO attributeManageDTO = null;

        if (attributeId != 0) {
            Attribute attribute = attributeDao.get(attributeId);
            attributeManageDTO = AttributeManageDTO.from(attribute);
            List<AttributeTranslation> translations = (List<AttributeTranslation>) attributeDao.getTranslations(attributeId);
            attributeManageDTO.setTranslations(translations);
        } else {
            attributeManageDTO = new AttributeManageDTO();
            attributeManageDTO.setAttributeType(AttributeType.TEXT.name());
            attributeManageDTO.setTranslations(new ArrayList<>());
        }
        List<AttributeTranslation> translations = attributeManageDTO.getTranslations();

        Set<String> existingTranslations = translations.stream().map(Translation::getLocale)
                .collect(Collectors.toCollection(() -> new HashSet<>(translations.size())));

        // init missing translations
        List<Language> languages = languageService.list(locale);

        for (Language language : languages) {
            String langLocale = language.getLangLocale();

            if (!existingTranslations.contains(langLocale)) {
                translations.add(new AttributeTranslation(langLocale));
            }
        }

        return attributeManageDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "attributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true)})
    @Override
    public void save(AttributeManageDTO attributeManageDTO) {
        Attribute attribute = attributeManageDTO.toAttribute();
        boolean isCreate = attributeManageDTO.isCreate();

        if (isCreate) {
            attributeDao.create(attribute);
        } else {
            attributeDao.update(attribute);
        }
        long attributeId = attribute.getId();
        attributeDao.saveTranslations(attributeId, attributeManageDTO.getTranslations());
        attributeManageDTO.setId(attributeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "attributes", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true)})
    public void delete(long id) throws CannotDeleteException {
        super.delete(id);
//        publish(userId);
    }

    @Cacheable(value = "attributes", key = "#locale")
    @Override
    public List<Attribute> list(String locale) {
        return super.list(locale);
    }

    /**
     * Generates a html representation of categories for the given user id.
     * @param userId the user id
     */
//    private void publish(long userId) {
//        if (!publishHtml) {
//            log.info("Publish html is disabled");
//            return;
//        }
//
//        List<CategoryDTO> categories = getCategories(userId);
//
//        StringBuilder html = new StringBuilder();
//        html.append("<option value='0'></option>");
//        long id;
//        String icon;
//        String color;
//
//        for (CategoryDTO category : categories) {
//            id = category.getId();
////            icon = category.getIcon();
////            icon = icon == null ? "" : icon;
////            color = category.getColor();
////            color = color == null ? "" : color;
//
//            html.append("<option value='").append(id).append("'>");
////            html.append("<span class=\"categoryCircle\"").append(" style=\"background-color:").append(color).append("\">");
////            html.append("<span>").append(icon).append("</span></span>").append("&nbsp;");
//            html.append("<span>").append(category.getName());
//            html.append("</span></option>");
//
//            List<CategoryDTO> childs = category.getChilds();
//
//            if (childs == null) {
//                continue;
//            }
//
//            for (CategoryDTO child : childs) {
//                id = child.getId();
////                icon = child.getIcon();
////                icon = icon == null ? "" : icon;
//
//                html.append("<option value='").append(id).append("'>").append("&nbsp;&nbsp;&nbsp;");
////                html.append("<span>").append(icon).append("</span>").append("&nbsp;");
//                html.append("<span>").append(child.getName());
//                html.append("</span></option>");
//            }
//        }
//
//        publish(userId, CATEGORY_LOOKUP_HTML, html.toString());
//    }

    /**
     * Setter for property 'attributeDao'.
     *
     * @param attributeDao Value to set for property 'attributeDao'.
     */
    @Autowired
    public void setAttributeDao(@Qualifier("attributeDao") AttributeDao attributeDao) {
        this.attributeDao = attributeDao;
        setBaseDao(attributeDao);
    }
}
