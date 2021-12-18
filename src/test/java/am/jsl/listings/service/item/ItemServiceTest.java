package am.jsl.listings.service.item;

import am.jsl.listings.domain.attribute.Attribute;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.category.CategoryAttribute;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ListingType;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains ItemService tests.
 */
public class ItemServiceTest extends BaseTest {

    /**
     * Executed before all ItemServiceTest tests.
     *
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Create Item Test")
    public void testCreateItem() throws Exception {
        log.info("Starting test for create item");

        Category category = createCategory();
        User user = createUser();

        Item item = new Item();
        item.setCategoryId(category.getId());
        item.setName("test item");
        item.setDescription("description");
        item.setPrice(10);
        item.setListingType(ListingType.SALE.getValue());
        item.setCurrency(CURRENCY_USD);
        item.setActive(true);
        item.setLocale(locale);
        item.setUserId(user.getId());

        itemService.create(item);

        assertTrue(item.getId() > 0);
        log.info("Finished test for create item");
    }

    @Test
    @DisplayName("Update Category Test")
    public void testUpdateCategory() throws Exception {
        log.info("Starting test for update category");
        String categoryName = "name updated";
        String slug = "test updated";
        String description = "description updated";
        long parentId = 1;

        Category category = createCategory();

        // update category
        category.setName(categoryName);
        category.setSlug(slug);
        category.setParentId(parentId);
        category.setDescription(description);

        categoryService.update(category);

        // validate category
        category = categoryService.get(category.getId(), locale);

        assertEquals(categoryName, category.getName());
        assertEquals(slug, category.getSlug());
        assertEquals(parentId, category.getParentId());
        assertEquals(description, category.getDescription());

        log.info("Finished test for update category");
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
    @DisplayName("Delete Category Attribute Test")
    public void testDeleteCategoryAttribute() throws Exception {
        log.info("Starting test for delete category attribute");

        Category category = createCategory();
        Attribute attribute = createAttribute();

        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setCategoryId(category.getId());
        categoryAttribute.setAttributeId(attribute.getId());
        categoryAttribute.setSortOrder(0);

        categoryService.saveOrUpdateCategoryAttribute(categoryAttribute);

        List<CategoryAttribute> categoryAttributes = categoryService.getCategoryAttributes(category.getId(), locale);
        assertEquals(1, categoryAttributes.size());

        categoryService.deleteCategoryAttribute(categoryAttribute);

        categoryAttributes = categoryService.getCategoryAttributes(category.getId(), locale);
        assertEquals(0, categoryAttributes.size());

        log.info("Finished test for delete category delete attribute");
    }

    /**
     * Executed after all ItemServiceTest tests.
     *
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
