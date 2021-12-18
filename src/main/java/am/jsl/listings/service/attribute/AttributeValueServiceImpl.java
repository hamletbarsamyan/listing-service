package am.jsl.listings.service.attribute;

import am.jsl.listings.dao.attribute.AttributeValueDao;
import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.attribute.AttributeValueTranslation;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The service implementation of the {@link AttributeValueService}.
 *
 * @author hamlet
 */
@Service("attributeValueService")
public class AttributeValueServiceImpl extends MultiLingualServiceImpl<AttributeValue>
        implements AttributeValueService {

    /**
     * The AttributeValue dao.
     */
    private AttributeValueDao attributeValueDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {
            @CacheEvict(cacheNames = "attributeValues", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true)})
    public void delete(long id) throws CannotDeleteException {
        super.delete(id);
//        publish(userId);
    }

    @Cacheable(value = "attributeValues", key = "#attributeId + '_' + #locale")
    @Override
    public List<AttributeValue> getAttributeValues(long attributeId, String locale) {
        return attributeValueDao.getAttributeValues(attributeId, locale);
    }

    @Override
    public List<AttributeValueManageDTO> getAttributeManageValues(long attributeId, String locale) {
        List<AttributeValue> attributeValues = attributeValueDao.getAttributeValues(attributeId);
        List<AttributeValueManageDTO> result = new ArrayList<>(attributeValues.size());
        List<Language> languages = languageService.list(locale);

        for (AttributeValue attributeValue : attributeValues) {
            AttributeValueManageDTO valueManageDTO = AttributeValueManageDTO.from(attributeValue);
            List<AttributeValueTranslation> valueTranslations = new ArrayList<>(languages.size());
            List<AttributeValueTranslation> existingTranslations =
                    (List<AttributeValueTranslation>) attributeValueDao.getTranslations(attributeValue.getId());

            // init missing translations
            for (Language language : languages) {
                AttributeValueTranslation translation = null;
                String langLocale = language.getLangLocale();
                Iterator<AttributeValueTranslation> translationIterator = existingTranslations.iterator();

                while (translationIterator.hasNext()) {
                    AttributeValueTranslation tempTranslation = translationIterator.next();

                    if (langLocale.equals(tempTranslation.getLocale())) {
                        translation = tempTranslation;
                        translationIterator.remove();
                        break;
                    }
                }

                if (translation != null) {
                    valueTranslations.add(translation);
                } else {
                    valueTranslations.add(new AttributeValueTranslation(langLocale));
                }
            }

            valueManageDTO.setTranslations(valueTranslations);
            result.add(valueManageDTO);
        }

        return result;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "attributeValues", allEntries = true),
            @CacheEvict(cacheNames = "lookupCategoryAttributes", allEntries = true)})
    @Override
    public void saveAttributeValues(long attributeId, List<AttributeValueManageDTO> attributeValues) {
        attributeValueDao.deleteAttributeValues(attributeId);
        attributeValueDao.saveAttributeValues(attributeId, attributeValues);
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
     * Setter for property 'attributeValueDao'.
     *
     * @param attributeValueDao Value to set for property 'attributeValueDao'.
     */
    @Autowired
    public void setAttributeValueDao(@Qualifier("attributeValueDao") AttributeValueDao attributeValueDao) {
        this.attributeValueDao = attributeValueDao;
        setBaseDao(attributeValueDao);
    }
}
