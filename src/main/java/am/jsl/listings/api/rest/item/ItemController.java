package am.jsl.listings.api.rest.item;

import am.jsl.listings.api.rest.BaseController;
import am.jsl.listings.api.rest.Response;
import am.jsl.listings.api.rest.ResponseCode;
import am.jsl.listings.domain.category.Category;
import am.jsl.listings.domain.item.Item;
import am.jsl.listings.domain.item.ItemAttribute;
import am.jsl.listings.domain.item.ItemImage;
import am.jsl.listings.domain.item.ListingType;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.dto.item.*;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.ex.DuplicateSlugException;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.item.ItemSearchQuery;
import am.jsl.listings.service.attribute.AttributeService;
import am.jsl.listings.service.attribute.AttributeValueService;
import am.jsl.listings.service.category.CategoryService;
import am.jsl.listings.service.item.ItemImageService;
import am.jsl.listings.service.item.ItemService;
import am.jsl.listings.util.CategoryUtils;
import am.jsl.listings.util.ImageFileFilter;
import am.jsl.listings.util.ImageUtils;
import am.jsl.listings.util.TextUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ItemController defines methods for searching, viewing and managing items and attributes.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/items")
@Lazy
public class ItemController extends BaseController {

    /**
     * The item image url
     */
    @Value("${listings.item.image.url}")
    private String itemImgUrl;

    /**
     * The directory where item images are uploaded
     */
    @Value("${listings.item.image.files.path}")
    private String itemImgFilesPath;

    /**
     * The item image service
     */
    @Autowired
    private transient ItemImageService itemImageService;

    /**
     * The item service
     */
    @Autowired
    private transient CategoryService categoryService;

    /**
     * The item service
     */
    @Autowired
    private transient ItemService itemService;


    /**
     * Returns user items.
     *
     * @return the response
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('item.view')")
    public Response<ListPaginatedResult<ItemListDTO>> list(Principal principal,
                                                           @RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam String categoryId,
                                                           @RequestParam String name,
                                                           @RequestParam String upc,
                                                           @RequestParam String status,
                                                           @RequestParam String sortBy,
                                                           @RequestParam boolean asc) {
        ItemSearchQuery itemSearchQuery = new ItemSearchQuery(page, size);

        if (StringUtils.hasText(categoryId)) {
            itemSearchQuery.setCategoryId(Long.valueOf(categoryId));
        }
        itemSearchQuery.setName(name);
        itemSearchQuery.setUpc(upc);
        if (StringUtils.hasText(status)) {
            itemSearchQuery.setStatus(Integer.valueOf(status));
        }
        itemSearchQuery.setUserId(getUserId(principal));
        itemSearchQuery.setLocale(getLocale());
        itemSearchQuery.setSortBy(sortBy);
        itemSearchQuery.setAsc(asc);
        ListPaginatedResult<ItemListDTO> result = itemService.search(itemSearchQuery);
        return Response.ok(result);
    }

    /**
     * Returns a item by id.
     *
     * @return the response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('item.view')")
    public Response<ItemViewDTO> get(Principal principal, @PathVariable long id) {
        ItemViewDTO item = itemService.getViewDTO(id, getLocale());
        return Response.ok(item);
    }

    /**
     * Returns item attributes.
     *
     * @return the response
     */
    @GetMapping("/listingTypes")
    public Response<List<Byte>> listingTypes() {
        List<Byte> result = Arrays.stream(ListingType.values()).map(ListingType::getValue).collect(Collectors.toList());
        return Response.ok(result);
    }

    /**
     * Returns a ItemManageDTO by id.
     *
     * @return the response
     */
    @GetMapping("/manage/{id}")
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<ItemManageDTO> getManageDTO(Principal principal, @PathVariable long id) {
        ItemManageDTO itemManageDTO = itemService.getManageDTO(getUserId(principal), id , getLocale());
        return Response.ok(itemManageDTO);
    }

    /**
     * Creates or updates the item.
     *
     * @return the response
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<?> save(Principal principal, @RequestBody ItemManageDTO itemManageDTO) {
        try {
            itemManageDTO.setUserId(getUserId(principal));
            itemService.save(itemManageDTO);
            return Response.ok();
        } catch (DuplicateSlugException e) {
            return Response.errorWithCode(ResponseCode.DUPLICATE_SLUG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Deletes the item.
     *
     * @return the response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<?> delete(@PathVariable long id) {
        try {
            itemService.delete(id);
            return Response.ok();
        } catch (CannotDeleteException e) {
            return Response.errorWithCode(ResponseCode.CAN_NOT_DELETE);
        }
    }

    /**
     * Returns item attributes.
     *
     * @return the response
     */
    @GetMapping("/{itemId}/attributes")
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<ItemAttributesListDTO> attributes(@PathVariable long itemId) {
        String locale = getLocale();
        Item item = itemService.get(itemId, locale);
        ItemAttributesListDTO itemAttributesManageDTO = new ItemAttributesListDTO();
        itemAttributesManageDTO.setName(item.getName());
        itemAttributesManageDTO.setCategoryId(item.getCategoryId());

        List<ItemAttributeManageDTO> attributes = itemService.getItemAttributes(itemId, locale);
        itemAttributesManageDTO.setAttributes(attributes);

        return Response.ok(itemAttributesManageDTO);
    }

    /**
     * Saves item attributes.
     *
     * @return the response
     */
    @PostMapping("/{itemId}/attributes")
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<?> saveAttributes(@PathVariable long itemId,
                                      @RequestBody List<ItemAttribute> attributes) {
        try {
            itemService.saveItemAttributes(itemId, attributes);
            List<ItemAttributeManageDTO> response = itemService.getItemAttributes(itemId, getLocale());
            return Response.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Returns item images.
     *
     * @return the response
     */
    @GetMapping("/{itemId}/images")
    public Response<List<ItemImageDTO>> images(@PathVariable long itemId) {
        List<ItemImageDTO> itemImages = itemImageService.getItemImages(itemId);
        return Response.ok(itemImages);
    }

    /**
     * Attaches an image to the item.
     *
     * @param request      the HttpServletRequest
     * @param uploadedFile the MultipartFile
     * @return the response
     * @throws IOException if could not upload image
     */
    @PostMapping("/{id}/uploadImage")
    @PreAuthorize("hasAuthority('item.manage')")
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

            String uploadPath = itemImgFilesPath + id;
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

            Category item = categoryService.get(id);
            String icon = item.getIcon();
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
            String path = CategoryUtils.getIconPath(itemImgUrl, icon, id);
            return Response.ok(path);
        } catch (UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
    }

    /**
     * Called for removing item image.
     *
     * @return the response
     */
    @DeleteMapping(value = "/{itemId}/images/{id}")
    @PreAuthorize("hasAuthority('item.manage')")
    public Response<String> removeImage(@PathVariable long itemId,
                                        @PathVariable long id) {
        try {
            ItemImage itemImage = itemImageService.get(id);

            if (itemImage == null || itemImage.getItemId() != itemId) {
                return Response.ok();
            }

            itemImageService.delete(id);

            String fileName = itemImage.getFileName();

            // remove image
            String uploadPath = itemImgFilesPath + itemId;
            File imageUploadDir = new File(uploadPath);

            if (imageUploadDir.isDirectory()) {
                File imageFile = new File(imageUploadDir, fileName);
                imageFile.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Response.ok();
    }

    private long getUserId(Principal principal) {
        String login = principal.getName();
        User user = (User) userService.loadUserByUsername(login);
        return user.getId();
    }
}