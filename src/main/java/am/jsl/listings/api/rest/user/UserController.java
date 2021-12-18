package am.jsl.listings.api.rest.user;

import am.jsl.listings.api.rest.BaseController;
import am.jsl.listings.api.rest.Response;
import am.jsl.listings.domain.event.EventType;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.dto.user.UserDTO;
import am.jsl.listings.dto.user.UserListDTO;
import am.jsl.listings.dto.user.UserViewDTO;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.ex.DuplicateEmailException;
import am.jsl.listings.ex.DuplicateUserException;
import am.jsl.listings.search.ListPaginatedResult;
import am.jsl.listings.search.user.UserSearchQuery;
import am.jsl.listings.service.event.EventLog;
import am.jsl.listings.web.util.I18n;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.Objects;

import static am.jsl.listings.util.WebUtils.USER;

/**
 * The UserController defines methods for user pages functionality from admin part:
 * such as search, view, add, edit users.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/users")
@Lazy
public class UserController extends BaseController {

    /**
     * The user images server
     */
    @Value("${listings.user.img.server}")
    private String userImgServer;

    /**
     * Returns user list.
     *
     * @return the response
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('user.view')")
    public Response<ListPaginatedResult<UserListDTO>> list(@RequestParam int page,
                                                           @RequestParam int size) {
        UserSearchQuery query = new UserSearchQuery(page, size);
        ListPaginatedResult<UserListDTO> result = userService.search(query);
        return Response.ok(result);
    }

    /**
     * Returns an user information by id.
     *
     * @return the response
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('user.view')")
    public Response<UserViewDTO> view(@PathVariable long id) {
        UserViewDTO userViewDTO = userService.getViewDTO(id);
        userViewDTO.setIconServer(userImgServer);
        userViewDTO.initIconPath();
        return Response.ok(userViewDTO);
    }

    /**
     * Returns an user by id.
     *
     * @return the response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user.view')")
    public Response<UserDTO> get(@PathVariable long id) {
        User user = userService.get(id);
        UserDTO userDTO = UserDTO.from(user);
        userDTO.setIconServer(userImgServer);
        userDTO.initIconPath();
        return Response.ok(userDTO);
    }

    /**
     * Creates or updates the user.
     *
     * @return the response
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('user.manage')")
    public Response<?> save(HttpServletRequest request, Principal principal, @RequestBody UserDTO userDTO) {
        try {
            boolean create = userDTO.isCreate();
            if (create) {
                if (!Objects.equals(userDTO.getPassword(), userDTO.getConfirmPassword())) {
                    String message = i18n.msg(request, I18n.KEY_ERROR_PASSWORDS_DONT_MATCH);
                    return Response.error(message);
                }
            }
            String userDetails = principal.getName();
            User loggedInUser = userService.getUser(userDetails);
            User user = userDTO.toUser();
            user.setChangedAt(new Date());
            user.setChangedBy(loggedInUser.getId());

            String successMsgKey = null;
            EventType eventType = null;

            if (create) { // create
                user.setCreatedAt(new Date());
                userService.create(user);
                successMsgKey = I18n.KEY_MESSAGE_SUCCESS_ADD;
                eventType = EventType.CREATE_USER;
            } else { // update
                userService.update(user);
                successMsgKey = I18n.KEY_MESSAGE_SUCCESS_UPDATE;
                eventType = EventType.UPDATE_USER;
            }

            EventLog.getInstance().write(eventType, user.toString(), loggedInUser);

            String message = i18n.msg(request, successMsgKey, new Object[]{USER, userDTO.getLogin()});
            return Response.ok(message);
        } catch (DuplicateUserException unf) {
            String message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE,
                    new Object[]{USER, userDTO.getLogin()});
            return Response.error(message);
        } catch (DuplicateEmailException ex) {
            String message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE,
                    new Object[]{USER, userDTO.getEmail()});
            return Response.error(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Updates the user.
     *
     * @return the response
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user.manage')")
    public Response<?> save(@PathVariable long id, @RequestBody UserDTO userDTO) {
        try {
            User user = userDTO.toUser();
            userService.create(user);
            return Response.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Deletes the user.
     *
     * @return the response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user.manage')")
    public Response<?> delete(HttpServletRequest req, @PathVariable long id) {
        try {
            userService.delete(id);
            return Response.ok();
        } catch (CannotDeleteException e) {
            return Response.error(i18n.msg(req, "user.delete.error"));
        }
    }
}