package am.jsl.listings.api.rest.user;

import am.jsl.listings.api.rest.Response;
import am.jsl.listings.domain.event.EventType;
import am.jsl.listings.domain.user.User;
import am.jsl.listings.dto.user.ConfirmRegistrationDTO;
import am.jsl.listings.dto.user.PasswordResetDTO;
import am.jsl.listings.dto.user.UserDTO;
import am.jsl.listings.ex.DuplicateEmailException;
import am.jsl.listings.ex.DuplicateUserException;
import am.jsl.listings.ex.InvalidTokenException;
import am.jsl.listings.ex.UserNotFoundException;
import am.jsl.listings.service.event.EventLog;
import am.jsl.listings.util.TextUtils;
import am.jsl.listings.api.rest.BaseController;
import am.jsl.listings.web.form.LoginForm;
import am.jsl.listings.web.form.validator.EmailValidator;
import am.jsl.listings.web.util.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * The AuthenticationController defines methods for user public pages functionality
 * such as login, logout, register, password reset.
 *
 * @author hamlet
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    /**
     * The password encoder.
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;

    /**
     * The email validator
     */
    @Autowired
    @Qualifier("emailValidator")
    private transient EmailValidator emailValidator;

    @PostMapping("/login")
    public Response<String> login(HttpServletRequest request, @Valid @RequestBody LoginForm loginForm) {
        User user = null;
        try {
            user = (User) userService.loadUserByUsername(loginForm.getUsername());
        } catch (UsernameNotFoundException e) {
            // skip
        }

        if (user == null) {
            String message = i18n.msg(request, "user.invalid.login.password");
            return Response.error(message);
        }

        if (!user.isEnabled()) {
            String message = i18n.msg(request, "user.invalid.login.password");
            return Response.error(message);
        }

        if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            String message = i18n.msg(request, "user.invalid.login.password");
            return Response.error(message);
        }

        userService.login(user.getId());

        String jwt = jwtTokenAuthenticationService.generateJwtToken(loginForm.getUsername());
        return Response.ok(jwt);
    }

    @PostMapping("/logout")
    public Response<String> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            SecurityContextHolder.clearContext();
        }

        return Response.ok();
    }

    /**
     * Sends a password reset email to the given email with the password reset link.
     *
     * @param request       the HttpServletRequest
     * @param email         the email
     * @param locale        the Locale
     * @return the success response if success otherwise error with message
     */
    @RequestMapping(value = {"/sendPasswordResetMail"}, method = RequestMethod.POST)
    public Response<String> sendPasswordResetMail(HttpServletRequest request, @RequestParam String email,
                                                  Locale locale) {
        if (!emailValidator.valid(email)) {
            String message = i18n.msg(request, I18n.KEY_ERROR_INVALID_EMAIL);
            return Response.error(message);
        }

        try {
            userService.sendPasswordResetMail(email, locale);
            return Response.ok();
        } catch (UserNotFoundException e) {
            return Response.error(e.getMessage());
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * Called when user clicks on reset password link for submitting new password.
     *
     * @param request          the HttpServletRequest
     * @param passwordResetDTO the PasswordResetDTO
     * @return the login page if success
     */
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public Response<String> resetPassword(HttpServletRequest request,
                                @RequestBody PasswordResetDTO passwordResetDTO) {
        try {
            boolean error = false;
            String message = null;
            String newPassword = passwordResetDTO.getNewPassword();
            String rePassword = passwordResetDTO.getReNewPassword();

            if (!TextUtils.hasText(newPassword)
                    || !TextUtils.hasText(rePassword)) {
                message = i18n.msg(request, "error.enter.required.fields");
                error = true;
            } else if (!rePassword.equals(newPassword)) {
                message = i18n.msg(request, "user.passwords_does_not_match");
            }

            if (error) {
                return Response.error(message);
            }

            User user = userService.get(passwordResetDTO.getUserId());
            passwordResetDTO.setLogin(user.getLogin());
            userService.resetPassword(passwordResetDTO);
            message = i18n.msg(request, "user.password.change_success.msg");
            return Response.ok(message);
        } catch (InvalidTokenException e) {
            return Response.error(i18n.msg(request, e.getMessageCode()));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage(), e);
            return Response.error("User not found");
        }
    }

    /**
     * Called when user submits registration data. Sends a confirmation email to the user.
     *
     * @param request       the HttpServletRequest
     * @param user          the UserDTO
     * @param locale        the Locale
     * @return the message page for showing registration result
     * @throws Exception if exception occurs
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response<String> register(HttpServletRequest request,
                                     @Valid @RequestBody UserDTO user, Locale locale) throws Exception {
        String login = user.getLogin();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        String email = user.getEmail();
        boolean error = false;
        String message = null;

        if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(email)) {
            error = true;
            message = i18n.msg(request, I18n.KEY_ERROR_ENTER_REQUIRED_FIELDS);
        } else if (!password.equals(confirmPassword)) {
            error = true;
            message = i18n.msg(request, I18n.KEY_ERROR_PASSWORDS_DONT_MATCH);
        } else if (!emailValidator.valid(email)) {
            error = true;
            message = i18n.msg(request, I18n.KEY_ERROR_INVALID_EMAIL);
        }

        if (error) {
            return Response.error(message);
        }

        try {
            User dbUser = user.toUser();
            userService.register(dbUser, locale);

            EventLog.getInstance().write(EventType.CREATE_USER, dbUser.toString(), dbUser.getId());

            message = i18n.msg(request, "user.register.mail.sent");
            return Response.ok(message);
        } catch (DuplicateUserException e) {
            message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE_LOGIN,
                    new Object[]{user.getLogin()});
            return Response.error(message);
        } catch (DuplicateEmailException ex) {
            message = i18n.msg(request, I18n.KEY_ERROR_DUPLICATE_EMAIL,
                    new Object[]{user.getEmail()});
            return Response.error(message);
        }
    }

    /**
     * Called when user clicks on the confirm link from the registration confirm email.
     *
     * @param request       the HttpServletRequest
     * @param confirmRegistrationDTO            the ConfirmRegistrationDTO
     * @return the login page
     */
    @RequestMapping(value = {"/confirm-registration"}, method = RequestMethod.POST)
    public Response<String> confirmRegistration(HttpServletRequest request,
                                                @RequestBody ConfirmRegistrationDTO confirmRegistrationDTO) {
        try {
            userService.confirmRegistration(confirmRegistrationDTO.getUserId(), confirmRegistrationDTO.getToken());
            return Response.ok(i18n.msg(request, "user.register.success"));
        } catch (InvalidTokenException e) {
            return Response.error(i18n.msg(request, e.getMessageCode()));
        } catch (UserNotFoundException e) {
            String message = i18n.msg(request, I18n.KEY_ERROR_NOT_FOUND,
                    new Object[]{"user id", confirmRegistrationDTO.getUserId()});
            return Response.error(message);
        }
    }
}
