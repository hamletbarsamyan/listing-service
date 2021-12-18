package am.jsl.listings.service.category;

import am.jsl.listings.domain.DescriptiveTranslation;
import am.jsl.listings.domain.Language;
import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.dto.category.CategoryAttributeManageDTO;
import am.jsl.listings.dto.category.CategoryManageDTO;
import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains CategoryService tests.
 */
public class CategoryServiceTest extends BaseTest {

    /**
     * Executed before all CategoryServiceTest tests.
     *
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Save Category Test")
    public void testSaveCategory() throws Exception {
        log.info("Starting test for save category");
        Category category = new Category();
        category.setSlug("test");
        category.setParentId(0);
        category.setLocale(locale);

        CategoryManageDTO categoryManageDTO = CategoryManageDTO.from(category);
        List<DescriptiveTranslation> translations = new ArrayList<>();
        List<Language> languages = languageService.list(locale);

        // create translations
        for (Language language : languages) {
            String langLocale = language.getLangLocale();
            DescriptiveTranslation translation = new DescriptiveTranslation();
            translation.setName("test category " + langLocale);
            translation.setDescription("test category description " + langLocale);
            translation.setLocale(langLocale);
            translations.add(translation);
        }
        categoryManageDTO.setTranslations(translations);

        categoryService.save(categoryManageDTO);

        assertTrue(categoryManageDTO.getId() > 0);

        CategoryManageDTO newCategory = categoryService.getManageDTO(category.getId(), locale);
        assertNotNull(newCategory);
        assertEquals(languages.size(), newCategory.getTranslations().size());

        log.info("Finished test for save category");
    }

    @Test
    @DisplayName("Delete Category Test")
    public void testDeleteCategory() throws Exception {
        log.info("Starting test for delete category");

        Category category = createCategory();
        long categoryId = category.getId();

        categoryService.delete(categoryId);

        // validate category
        category = categoryService.get(categoryId, locale);
        assertNull(category);

        log.info("Finished test for delete category");
    }

    @Test
    @DisplayName("Get Categories Test")
    public void testListCategories() throws Exception {
        log.info("Starting test for getCategories");

        Category category = new Category();
        category.setName("test category");
        category.setSlug("test");
        category.setDescription("description");
        category.setParentId(0);
        category.setLocale(locale);

        categoryService.create(category);

        List<Category> categories = categoryService.list(locale);

        assertTrue(categories.size() > 0);
        log.info("Finished test for getCategories");
    }

    @Test
    @DisplayName("Lookup Categories Test")
    public void testLookupCategories() throws Exception {
        log.info("Starting test for lookup categories");

        Category category = new Category();
        category.setName("test category");
        category.setSlug("test");
        category.setDescription("description");
        category.setParentId(0);
        category.setLocale(locale);

        categoryService.create(category);

        Category child = new Category();
        child.setName("test child category");
        child.setSlug("child");
        child.setDescription("child description");
        child.setParentId(category.getId());
        child.setLocale(locale);
        categoryService.create(child);

        List<Category> categories = categoryService.lookup(category.getId(), locale);

        assertEquals(1, categories.size());
        assertEquals(child.getId(), categories.get(0).getId());
        log.info("Finished test for lookup categories");
    }

    @Test
    @DisplayName("SaveOrUpdate Category Attribute Test")
    public void testSaveOrUpdateCategoryAttribute() throws Exception {
        log.info("Starting test for SaveOrUpdate category attribute");

        Category category = createCategory();
        Attribute attribute1 = createAttribute();

        CategoryAttribute categoryAttribute1 = new CategoryAttribute();
        categoryAttribute1.setCategoryId(category.getId());
        categoryAttribute1.setAttributeId(attribute1.getId());
        categoryAttribute1.setSortOrder(0);

        categoryService.saveOrUpdateCategoryAttribute(categoryAttribute1);

        List<CategoryAttribute> categoryAttributes = categoryService.getCategoryAttributes(category.getId(), locale);
        assertEquals(1, categoryAttributes.size());

        Attribute attribute = createAttribute();

        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setCategoryId(category.getId());
        categoryAttribute.setAttributeId(attribute.getId());
        categoryAttribute.setSortOrder(0);

        categoryService.saveOrUpdateCategoryAttribute(categoryAttribute);

        categoryAttribute1 = categoryAttributes.get(0);
        categoryAttribute1.setParentId(attribute.getId());
        categoryAttribute1.setSortOrder(1);
        categoryService.saveOrUpdateCategoryAttribute(categoryAttribute1);

        categoryAttributes = categoryService.getCategoryAttributes(category.getId(), locale);
        assertEquals(2, categoryAttributes.size());

        categoryAttribute = categoryAttributes.get(0);
        categoryAttribute1 = categoryAttributes.get(1);

        assertEquals(0, categoryAttribute.getSortOrder());
        assertEquals(1, categoryAttribute1.getSortOrder());

        assertEquals(categoryAttribute.getAttributeId(), categoryAttribute1.getParentId());

        log.info("Finished test for SaveOrUpdate category attribute");
    }

    @Test
    @DisplayName("Save Category Attributes Test")
    public void testSaveCategoryAttributes() throws Exception {
        log.info("Starting test for Save category attributes");

        Category category = createCategory();
        long categoryId = category.getId();

        List<CategoryAttributeManageDTO> categoryAttributeManageDTOS = new ArrayList<>();
        List<Attribute> attributes = attributeService.lookup(locale);
        CategoryAttributeManageDTO manageDTO = new CategoryAttributeManageDTO();
        manageDTO.setAttributeId(attributes.get(0).getId());
        categoryAttributeManageDTOS.add(manageDTO);

        categoryService.saveCategoryAttributes(categoryId, categoryAttributeManageDTOS);

        categoryAttributeManageDTOS = categoryService.getCategoryAttributeManageDTOs(categoryId, locale);
        assertEquals(1, categoryAttributeManageDTOS.size());

        log.info("Finished test for Save category attributes");
    }

    /**
     * Executed after all CategoryServiceTest tests.
     *
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
