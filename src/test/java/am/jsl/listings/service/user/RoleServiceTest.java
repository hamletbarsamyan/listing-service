package am.jsl.listings.service.user;

import am.jsl.listings.domain.user.Permission;
import am.jsl.listings.domain.user.Role;
import am.jsl.listings.service.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains RoleService tests.
 */
public class RoleServiceTest extends BaseTest {

    /**
     * Executed before all RoleServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Create Role Test")
    public void testCreateRole() throws Exception {
        log.info("Starting test for create role");

        List<Permission> permissions = permissionService.list();
        Permission defaultPermission = permissions.get(0);

        Role role = new Role();
        role.setName(RandomStringUtils.randomAlphabetic(8));
        role.setDescription("test description");
        role.setPermissions(Collections.singletonList(defaultPermission.getId()));

        roleService.create(role);
        assertTrue(role.getId() > 0);

        role = roleService.get(role.getId());
        List<Long> rolePermissions = role.getPermissions();

        assertEquals(1, rolePermissions.size());
        assertEquals(defaultPermission.getId(), rolePermissions.get(0).longValue());

        log.info("Finished test for create role");
    }

    @Test
    @DisplayName("Update Role Test")
    public void testUpdateRole() throws Exception {
        log.info("Starting test for update role");

        String name = "updated role";
        String description = "updated role description";
        List<Permission> permissions = permissionService.list();
        Permission defaultPermission = permissions.get(0);
        Permission updatedPermission = permissions.get(1);

        Role role = new Role();
        role.setName(RandomStringUtils.randomAlphabetic(8));
        role.setDescription("test description");
        role.setPermissions(Collections.singletonList(defaultPermission.getId()));

        roleService.create(role);
        assertTrue(role.getId() > 0);

        role = roleService.get(role.getId());
        List<Long> rolePermissions = role.getPermissions();

        assertEquals(1, rolePermissions.size());
        assertEquals(defaultPermission.getId(), rolePermissions.get(0).longValue());

        // update role
        role.setName(name);
        role.setDescription(description);
        role.setPermissions(Collections.singletonList(updatedPermission.getId()));

        roleService.update(role);

        // validate role
        role = roleService.get(role.getId());

        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
        rolePermissions = role.getPermissions();

        assertEquals(1, rolePermissions.size());
        assertEquals(updatedPermission.getId(), rolePermissions.get(0).longValue());

        log.info("Finished test for update role");
    }


    @Test
    @DisplayName("Delete Role Test")
    public void testDeleteUser() throws Exception {
        log.info("Starting test for delete role");

        List<Permission> permissions = permissionService.list();
        Permission defaultPermission = permissions.get(0);

        Role role = new Role();
        role.setName(RandomStringUtils.randomAlphabetic(8));
        role.setDescription("test description");
        role.setPermissions(Collections.singletonList(defaultPermission.getId()));

        roleService.create(role);
        long roleId = role.getId();
        assertTrue(roleId > 0);

        roleService.delete(roleId);

        // validate role
        role = roleService.get(roleId);
        assertNull(role);

        log.info("Finished test for delete role");
    }

    @Test
    @DisplayName("List Roles Test")
    public void testListRoles() throws Exception {
        log.info("Starting test for list roles");

        List<Permission> permissions = permissionService.list();
        Permission defaultPermission = permissions.get(0);

        Role role = new Role();
        role.setName(RandomStringUtils.randomAlphabetic(8));
        role.setDescription("test description");
        role.setPermissions(Collections.singletonList(defaultPermission.getId()));

        roleService.create(role);

        List<Role> roles = roleService.list();

        assertTrue(roles.size() > 0);
        log.info("Finished test for list roles");
    }

    /**
     * Executed after all UserServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}