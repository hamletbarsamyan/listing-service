package am.jsl.listings.api.rest;

import am.jsl.listings.dto.Pair;
import am.jsl.listings.service.category.CategoryService;
import am.jsl.listings.service.item.ItemService;
import am.jsl.listings.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The DashboardController defines methods for viewing dashboard.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/dashboard")
@Lazy
public class DashboardController extends BaseController {

    /**
     * The UserService service
     */
    @Autowired
    private transient UserService userService;

    /**
     * The CategoryService service
     */
    @Autowired
    private transient CategoryService categoryService;

    /**
     * The ItemService service
     */
    @Autowired
    private transient ItemService itemService;

    /**
     * Returns total count of users.
     *
     * @return the response
     */
    @GetMapping("/usersCount")
    public Response<Integer> usersCount() {
        Integer count = userService.getUsersCount();
        return Response.ok(count);
    }

    /**
     * Returns total count of categories.
     *
     * @return the response
     */
    @GetMapping("/categoriesCount")
    public Response<Integer> categoriesCount() {
        Integer count = categoryService.getCategoriesCount();
        return Response.ok(count);
    }

    /**
     * Returns total count of items.
     *
     * @return the response
     */
    @GetMapping("/itemsCount")
    public Response<Integer> itemsCount() {
        Integer count = itemService.getItemsCount();
        return Response.ok(count);
    }

    /**
     * Returns total count of items by category.
     *
     * @return the response
     */
    @GetMapping("/categoryItemsCount")
    public Response<List<Pair<String, Integer>>> categoryItemsCount() {
        List<Pair<String, Integer>> categoryItemsCount = itemService.categoryItemsCount(getLocale());
        return Response.ok(categoryItemsCount);
    }
}
