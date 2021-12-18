package am.jsl.listings.api.rest.user;

import am.jsl.listings.api.rest.Response;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.dto.user.PasswordChangeDTO;
import am.jsl.listings.dto.user.UserInfoDTO;
import am.jsl.listings.dto.user.UserProfileDTO;
import am.jsl.listings.ex.DuplicateEmailException;
import am.jsl.listings.ex.DuplicateUserException;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.util.*;
import am.jsl.listings.api.rest.BaseController;
import am.jsl.listings.web.util.I18n;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import static am.jsl.listings.util.WebUtils.USER;

/**
 * The ProfileController defines methods for managing user profile:
 * viewing / editing profile data, changing password, attaching / detaching profile images.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/profile")
public class ProfileController extends BaseController {

    /**
     * The user images server
     */
    @Value("${listings.user.img.server}")
    private String userImgServer;

    /**
     * The directory where user images are uploaded
     */
    @Value("${listings.user.img.dir}")
    private String userImgDir;

    /**
     * The password encoder
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;

    /**
     * Returns user information.
     *
     * @return the response
     */
    @GetMapping("/info")
    public Response<UserInfoDTO> info(@RequestParam String token) {
        String login = jwtTokenAuthenticationService.getUserLoginFromJwtToken(token);
        User user = (User) userService.loadUserByUsername(login);
        UserInfoDTO userInfoDTO = UserInfoDTO.from(user);
        userInfoDTO.setIconServer(userImgServer);
        userInfoDTO.initIconPath();
        return Response.ok(userInfoDTO);
    }

    /**
     * Returns user profile data.
     *
     * @return the response
     */
    @GetMapping()
    public Response<UserProfileDTO> getProfile(Principal principal) {
        String login = principal.getName();
        UserProfileDTO user = userService.getUserProfile(login);
        user.setIconServer(userImgServer);
        user.initIconPath();
        return Response.ok(user);
    }

    /**
     * Called when user clicks on save button from user profile page.
     *
     * @param principal the Principal
     * @param request   the HttpServletRequest
     * @param userDTO   the UserProfileDTO
     * @return the result message
     */
    @PostMapping()
    public Response<String> save(Principal principal, HttpServletRequest request,
                                 @RequestBody UserProfileDTO userDTO) {
        try {
            String login = principal.getName();
            User user = userService.getUser(login);
            user.setLogin(userDTO.getLogin());
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setZip(userDTO.getZip());
            user.setChangedAt(new Date());
            user.setChangedBy(user.getId());
            userService.updateProfile(user);

            String message = i18n.msg(request, I18n.KEY_MESSAGE_SUCCESS_UPDATE,
                    new Object[]{USER, userDTO.getLogin()});
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
     * Called when users clicks on change button from password change page.
     *
     * @param principal     the Principal
     * @param request       the HttpServletRequest
     * @return the response
     */
    @PostMapping(value = "/changePassword")
    public Response<String> changePassword(Principal principal, HttpServletRequest request,
                                 @RequestBody PasswordChangeDTO passwordChangeDTO) {
        String login = principal.getName();
        User user = userService.getUser(login);
        boolean error = false;
        String message = null;
        String oldPassword = passwordChangeDTO.getOldPassword();
        String newPassword = passwordChangeDTO.getNewPassword();
        String rePassword = passwordChangeDTO.getRePassword();

        if (!TextUtils.hasText(oldPassword)
                || !TextUtils.hasText(newPassword)
                || !TextUtils.hasText(rePassword)) {
            message = i18n.msg(request, "error.enter.required.fields");
            error = true;
        } else {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                message = i18n.msg(request, "user.old_password_incorrect");
                error = true;
            } else if (!rePassword.equals(newPassword)) {
                message = i18n.msg(request, "user.passwords_does_not_match");
                error = true;
            }
        }

        if (!error) {
            userService.changePassword(newPassword, user.getId(), login);
            message = i18n.msg(request, "user.password.change_success.msg");
            return Response.ok(message);
        } else {
            return Response.error(message);
        }
    }

    /**
     * Called from user profile page for attaching an image to the user profile.
     *
     * @param request      the HttpServletRequest
     * @param uploadedFile the MultipartFile
     * @return the response
     * @throws IOException if could not upload image
     */
    @PostMapping("/uploadImage")
    public Response<String> uploadImage(HttpServletRequest request, Principal principal,
                                        @RequestParam("avatar") MultipartFile uploadedFile) throws IOException {

        if (uploadedFile.isEmpty()) {
            return Response.error("");
        }

        try {
            String login = principal.getName();
            User user = userService.getUser(login);
            long userId = user.getId();
            String fileName = uploadedFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);

            if (!ImageFileFilter.isValidImageExtension(extension)) {
                String message = i18n.msg(request, "error.select.valid.image");
                return Response.error(message);
            }

            String uploadPath = userImgDir + userId;
            File imageUploadDir = new File(uploadPath);

            if (!imageUploadDir.isDirectory()) {
                imageUploadDir.mkdir();
            }

            // scale image if needs
            BufferedImage image = ImageUtils.toBufferedImage(uploadedFile.getBytes());
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            if (imgWidth > UserUtils.PROFILE_IMG_WIDTH
                    || imgHeight > UserUtils.PROFILE_IMG_HEIGHT) {
                image = ImageUtils.resizeImage(image,
                        UserUtils.PROFILE_IMG_WIDTH,
                        UserUtils.PROFILE_IMG_HEIGHT, true);
            }

            User dbUser = userService.get(user.getId());
            String icon = dbUser.getIcon();
            File imageFile = null;

            // remove old icon
            if (!TextUtils.isEmpty(icon)) {
                imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            icon = GenerateShortUUID.next() + "." + extension;

            imageFile = new File(imageUploadDir, icon);
            ImageIO.write(image, extension, imageFile);

            user.setIcon(icon);
            userService.updateIcon(user);
            String path = UserUtils.getIconPath(userImgServer, icon, userId);
            return Response.ok(path);
        } catch (UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
    }

    /**
     * Called when user clicks on remove image link.
     *
     * @return the response
     */
    @DeleteMapping(value = "/removeImage")
    public Response<String> removeImage(Principal principal) {
        try {
            String login = principal.getName();
            User user = userService.getUser(login);
            String icon = user.getIcon();

            if (TextUtils.isEmpty(icon)) {
                return Response.ok();
            }

            // remove icon
            String uploadPath = userImgDir + user.getId();
            File imageUploadDir = new File(uploadPath);

            if (imageUploadDir.isDirectory()) {
                File imageFile = new File(imageUploadDir, icon);
                imageFile.delete();
            }

            user.setIcon(null);
            userService.updateIcon(user);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        return Response.ok();
    }
}
