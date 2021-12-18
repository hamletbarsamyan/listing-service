package am.jsl.listings.api.rest.category;

import am.jsl.listings.api.rest.Response;
import am.jsl.listings.api.rest.ResponseCode;
import am.jsl.listings.domain.attribute.AttributeValue;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.dto.attribute.AttributeValueLookupDTO;
import am.jsl.listings.dto.category.*;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.ex.DuplicateSlugException;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.service.category.CategoryService;
import am.jsl.listings.util.CategoryUtils;
import am.jsl.listings.util.ImageFileFilter;
import am.jsl.listings.util.ImageUtils;
import am.jsl.listings.util.TextUtils;
import am.jsl.listings.api.rest.BaseController;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The CategoryController defines methods for searching, viewing and managing categories and attributes.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/categories")
@Lazy
public class CategoryController extends BaseController {

    /**
     * The category images server
     */
    @Value("${listings.category.img.server}")
    private String categoryImgServer;

    /**
     * The directory where category images are uploaded
     */
    @Value("${listings.category.img.dir}")
    private String categoryImgDir;

    /**
     * The category service
     */
    @Autowired
    private transient CategoryService categoryService;

    /**
     * Lookup categories.
     *
     * @return the response
     */
    @GetMapping("/lookup")
    public Response<List<Category>> lookup() {
        List<Category> categories = categoryService.lookup(getLocale());
        return Response.ok(categories);
    }

    /**
     * Returns category tree.
     *
     * @return the response
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('category.view')")
    public Response<List<CategoryTreeDTO>> categoryTree() {
        List<CategoryTreeDTO> categoryTree = categoryService.getCategoryTree(getLocale());
        return Response.ok(categoryTree);
    }

    /**
     * Returns a category by id.
     *
     * @return the response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('category.view')")
    public Response<CategoryViewDTO> get(@PathVariable long id) {
        CategoryViewDTO category = categoryService.getViewDTO(id, getLocale());
        return Response.ok(category);
    }

    /**
     * Returns a CategoryManageDTO by id.
     *
     * @return the response
     */
    @GetMapping("/manage/{id}")
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<CategoryManageDTO> getManageDTO(@PathVariable long id) {
        CategoryManageDTO categoryManageDTO = categoryService.getManageDTO(id, getLocale());
        return Response.ok(categoryManageDTO);
    }

    /**
     * Creates or updates the category.
     *
     * @return the response
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<?> save(@RequestBody CategoryManageDTO categoryManageDTO) {
        try {
            categoryService.save(categoryManageDTO);
            return Response.ok();
        } catch (DuplicateSlugException e) {
            return Response.errorWithCode(ResponseCode.DUPLICATE_SLUG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Deletes the category.
     *
     * @return the response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<?> delete(@PathVariable long id) {
        try {
            categoryService.delete(id);
            return Response.ok();
        } catch (CannotDeleteException e) {
            return Response.errorWithCode(ResponseCode.CAN_NOT_DELETE);
        }
    }

    /**
     * Returns category attributes.
     *
     * @return the response
     */
    @GetMapping("/{categoryId}/attributes")
    @PreAuthorize("hasAuthority('category.view')")
    public Response<CategoryAttributesListDTO> attributes(@PathVariable long categoryId) {
        String locale = getLocale();
        Category category = categoryService.get(categoryId, locale);
        CategoryAttributesListDTO categoryAttributesDTO = new CategoryAttributesListDTO();
        categoryAttributesDTO.setName(category.getName());

        List<CategoryAttributeManageDTO> attributes = categoryService.getCategoryAttributeManageDTOs(categoryId, locale);
        categoryAttributesDTO.setAttributes(attributes);
        return Response.ok(categoryAttributesDTO);
    }

    /**
     * Lookups category attributes.
     *
     * @return the response
     */
    @GetMapping("/{categoryId}/attributes/lookup")
    public Response<List<CategoryAttributeLookupDTO>> lookupAttributes(@PathVariable long categoryId) {
        String locale = getLocale();

        List<CategoryAttributeLookupDTO> attributes = categoryService.lookupAttributes(categoryId, locale);
        return Response.ok(attributes);
    }

    /**
     * Lookups category attribute values.
     *
     * @return the response
     */
    @GetMapping("/{categoryId}/attributevalues/lookup")
    public Response<Map<Long, List<CategoryAttributeValueLookupDTO>>> lookupAttributeValues(@PathVariable long categoryId) {
        String locale = getLocale();

        Map<Long, List<CategoryAttributeValueLookupDTO>> attributeValuesMap = categoryService.lookupAttributeValues(categoryId, locale);
        return Response.ok(attributeValuesMap);
    }

    /**
     * Saves category attributes.
     *
     * @return the response
     */
    @PostMapping("/{categoryId}/attributes")
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<?> saveAttributes(@PathVariable long categoryId,
                                      @RequestBody List<CategoryAttributeManageDTO> attributes) {
        try {
            categoryService.saveCategoryAttributes(categoryId, attributes);
            List<CategoryAttributeManageDTO> response = categoryService.getCategoryAttributeManageDTOs(categoryId, getLocale());
            return Response.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Attaches an image to the category.
     *
     * @param request      the HttpServletRequest
     * @param uploadedFile the MultipartFile
     * @return the response
     * @throws IOException if could not upload image
     */
    @PostMapping("/{id}/uploadImage")
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<String> uploadImage(@PathVariable long id,
                                        HttpServletRequest request,
                                        @RequestParam("icon") MultipartFile uploadedFile) throws IOException {

        if (uploadedFile.isEmpty()) {
            return Response.error("");
        }

        try {
            String fileName = uploadedFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);

            if (!ImageFileFilter.isValidImageExtension(extension)) {
                String message = i18n.msg(request, "error.select.valid.image");
                return Response.error(message);
            }

            String uploadPath = categoryImgDir + id;
            File imageUploadDir = new File(uploadPath);

            if (!imageUploadDir.isDirectory()) {
                imageUploadDir.mkdir();
            }

            // scale image if needs
            BufferedImage image = ImageUtils.toBufferedImage(uploadedFile.getBytes());
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            if (imgWidth > CategoryUtils.CATEGORY_IMG_WIDTH
                    || imgHeight > CategoryUtils.CATEGORY_IMG_HEIGHT) {
                image = ImageUtils.resizeImage(image,
                        CategoryUtils.CATEGORY_IMG_WIDTH,
                        CategoryUtils.CATEGORY_IMG_HEIGHT, true);
            }

            Category category = categoryService.get(id);
            String icon = category.getIcon();
            File imageFile = null;

            // remove old icon
            if (!TextUtils.isEmpty(icon)) {
                imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            icon = fileName;

            imageFile = new File(imageUploadDir, icon);
            ImageIO.write(image, extension, imageFile);

            categoryService.updateIcon(id, icon);
            String path = CategoryUtils.getIconPath(categoryImgServer, icon, id);
            return Response.ok(path);
        } catch (UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
    }

    /**
     * Called for removing category image.
     *
     * @return the response
     */
    @DeleteMapping(value = "/{id}/removeImage")
    @PreAuthorize("hasAuthority('category.manage')")
    public Response<String> removeImage(@PathVariable long id) {
        try {
            Category category = categoryService.get(id);
            String icon = category.getIcon();

            if (TextUtils.isEmpty(icon)) {
                return Response.ok();
            }

            // remove icon
            String uploadPath = categoryImgDir + category.getId();
            File imageUploadDir = new File(uploadPath);

            if (imageUploadDir.isDirectory()) {
                File imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            categoryService.updateIcon(id, null);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return Response.ok();
    }
}