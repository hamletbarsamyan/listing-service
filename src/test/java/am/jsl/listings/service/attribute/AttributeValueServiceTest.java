package am.jsl.listings.service.attribute;

import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.attribute.AttributeValueTranslation;
import am.jsl.listings.dto.attribute.AttributeValueManageDTO;
import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains AttributeValueService tests.
 */
public class AttributeValueServiceTest extends BaseTest {

    /**
     * Executed before all AttributeValueServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Save Attribute Values Test")
    public void testSaveAttributeValues() throws Exception {
        log.info("Starting test for save attribute values");

        // create attribute
        Attribute attribute = createAttribute();
        long attributeId = attribute.getId();

        // create attribute value
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttributeId(attributeId);
        attributeValue.setValue("value1");
        attributeValue.setSortOrder(0);
        AttributeValueManageDTO valueManageDTO = AttributeValueManageDTO.from(attributeValue);

        // create attribute value translation
        AttributeValueTranslation translation = new AttributeValueTranslation();
        translation.setValueTr("value1 translation");
        translation.setLocale(locale);
        List<AttributeValueTranslation> translations = new ArrayList<>(1);
        translations.add(translation);
        valueManageDTO.setTranslations(translations);

        List<AttributeValueManageDTO> attributeValues = new ArrayList<>(1);
        attributeValues.add(valueManageDTO);

        attributeValueService.saveAttributeValues(attributeId, attributeValues);

        List<AttributeValueManageDTO> savedAttValues = attributeValueService.getAttributeManageValues(attributeId, locale);
        assertEquals(1, savedAttValues.size());
        AttributeValueManageDTO savedAttValue = savedAttValues.get(0);

        translations = savedAttValue.getTranslations();
        assertEquals(4, translations.size());

        log.info("Finished test for save attribute values");
    }

    @Test
    @DisplayName("Delete Attribute Value Test")
    public void testDeleteAttributeValue() throws Exception {
        log.info("Starting test for delete attribute value");

        AttributeValue attributeValue = createAttributeValue();
        long attributeValueId = attributeValue.getId();

        attributeValueService.delete(attributeValueId);

        // validate attribute value
        attributeValue = attributeValueService.get(attributeValueId, locale);
        assertNull(attributeValue);

        log.info("Finished test for delete attribute value");
    }

    @Test
    @DisplayName("Lookup Attribute Values Test")
    public void testLookupAttributeValues() throws Exception {
        log.info("Starting test lookup attribute values");

        AttributeValue attributeValue = createAttributeValue();
        long attributeId = attributeValue.getAttributeId();

        attributeValue = new AttributeValue();
        attributeValue.setAttributeId(attributeId);
        attributeValue.setValue("value2");
        attributeValue.setValueTr("value2 translation");
        attributeValue.setSortOrder(1);
        attributeValue.setLocale(locale);
        attributeValueService.create(attributeValue);

        List<AttributeValue> attributeValues = attributeValueService.lookup(attributeId, locale);

        assertEquals(2, attributeValues.size());

        log.info("Finished test for lookup attribute values");
    }

    @Test
    @DisplayName("Get Attribute Values Test")
    public void testGetAttributeValues() throws Exception {
        log.info("Starting test get attribute values");

        AttributeValue attributeValue = createAttributeValue();
        long attributeId = attributeValue.getAttributeId();

        attributeValue = new AttributeValue();
        attributeValue.setAttributeId(attributeId);
        attributeValue.setValue("value2");
        attributeValue.setValueTr("value2 translation");
        attributeValue.setSortOrder(1);
        attributeValue.setLocale(locale);
        attributeValueService.create(attributeValue);

        List<AttributeValue> attributeValues = attributeValueService.getAttributeValues(attributeId, locale);

        assertEquals(2, attributeValues.size());

        log.info("Finished test for get attribute values");
    }
    
    /**
     * Executed after all AttributeValueServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
