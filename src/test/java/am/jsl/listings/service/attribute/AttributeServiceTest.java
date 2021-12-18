package am.jsl.listings.service.attribute;

import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeTranslation;
import am.jsl.listings.domain.attribute.AttributeType;
import am.jsl.listings.dto.attribute.AttributeManageDTO;
import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains AttributeService tests.
 */
public class AttributeServiceTest extends BaseTest {

    /**
     * Executed before all AttributeServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Save Attribute Test")
    public void testSaveAttribute() throws Exception {
        log.info("Starting test for save attribute");

        // create attribute
        Attribute attribute = new Attribute();
        attribute.setAttrType(AttributeType.TEXT.name());
        attribute.setLocale(locale);

        AttributeManageDTO attributeManageDTO = AttributeManageDTO.from(attribute);
        List<AttributeTranslation> translations = new ArrayList<>();
        List<Language> languages = languageService.list(locale);

        // create translations
        for (Language language : languages) {
            String langLocale = language.getLangLocale();
            AttributeTranslation translation = new AttributeTranslation();
            translation.setName("Weight " + langLocale);
            translation.setDescription("Weight " + langLocale);
            translation.setExtraInfo("kg " + langLocale);
            translation.setLocale(langLocale);
            translations.add(translation);
        }
        attributeManageDTO.setTranslations(translations);

        attributeService.save(attributeManageDTO);

        assertTrue(attributeManageDTO.getId() > 0);

        AttributeManageDTO newAttribute = attributeService.getManageDTO(attribute.getId(), locale);
        assertNotNull(newAttribute);
        assertEquals(languages.size(), newAttribute.getTranslations().size());

        log.info("Finished test for save attribute");
    }

    @Test
    @DisplayName("Delete Attribute Test")
    public void testDeleteAttribute() throws Exception {
        log.info("Starting test for delete attribute");

        Attribute attribute = createAttribute();
        long attributeId = attribute.getId();

        attributeService.delete(attributeId);

        // validate attribute
        attribute = attributeService.get(attributeId, locale);
        assertNull(attribute);

        log.info("Finished test for delete attribute");
    }

    @Test
    @DisplayName("List Attributes Test")
    public void testListAttributes() throws Exception {
        log.info("Starting test list attributes");

        createAttribute();

        List<Attribute> attributes = attributeService.list(locale);

        assertTrue(attributes.size() > 0);

        log.info("Finished test for list attributes");
    }

    /**
     * Executed after all AttributeServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
