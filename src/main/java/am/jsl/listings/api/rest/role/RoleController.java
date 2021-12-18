package am.jsl.listings.api.rest.role;

import am.jsl.listings.api.rest.Response;
import am.jsl.listings.domain.Descriptive;
import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.dto.user.RoleDTO;
import am.jsl.listings.dto.user.RoleInfoDTO;
import am.jsl.listings.ex.CannotDeleteException;
import am.jsl.listings.service.user.PermissionService;
import am.jsl.listings.service.user.RoleService;
import am.jsl.listings.api.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The RoleController defines methods for managing roles.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/roles")
public class RoleController extends BaseController {

    /**
     * The role service
     */
    @Autowired
    private transient RoleService roleService;

    /**
     * The permission service
     */
    @Autowired
    private transient PermissionService permissionService;


    /**
     * Returns role list.
     *
     * @return the response
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('role.view')")
    public Response<?> list() {
        List<Role> roles = roleService.list();
        return Response.ok(roles);
    }

    /**
     * Returns available permissions.
     *
     * @return the response
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('role.view')")
    public Response<?> permissions() {
        return Response.ok(permissionService.list());
    }

    /**
     * Returns a role by id
     *
     * @return the response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role.view')")
    public Response<?> get(@PathVariable long id) {
        Role role = roleService.get(id);
        RoleDTO roleDTO = RoleDTO.from(role);
        return Response.ok(roleDTO);
    }

    /**
     * Returns a role information by id
     *
     * @return the response
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('role.view')")
    public Response<?> getInfo(@PathVariable long id) {
        Role role = roleService.get(id);
        RoleInfoDTO roleDTO = RoleInfoDTO.from(role);
        List<Permission> permissions = permissionService.getRolePermissions(id);
        List<String> permissionsNames = permissions.stream().map(Descriptive::getDescription).collect(Collectors.toList());
        roleDTO.setPermissions(permissionsNames);
        return Response.ok(roleDTO);
    }

    /**
     * Creates a role
     *
     * @return the response
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('role.manage')")
    public Response<?> create(@RequestBody RoleDTO roleDTO) {
        try {
            Role role = roleDTO.toRole();
            roleService.create(role);
            return Response.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Updates the role.
     *
     * @return the response
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role.manage')")
    public Response<?> save(@PathVariable long id, @RequestBody RoleDTO roleDTO) {
        try {
            Role role = roleDTO.toRole();
            roleService.update(role);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }

        return Response.ok(roleDTO);
    }

    /**
     * Deletes the role.
     *
     * @return the response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role.manage')")
    public Response<?> delete(HttpServletRequest req, @PathVariable long id) {
        try {
            roleService.delete(id);
            return Response.ok();
        } catch (CannotDeleteException e) {
            return Response.error(i18n.msg(req, "role.delete.error"));
        }
    }
}
