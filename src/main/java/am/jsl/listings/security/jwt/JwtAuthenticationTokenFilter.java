package am.jsl.listings.security.jwt;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.log.AppLogger;
import am.jsl.listings.service.ErrorTrackerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The subclass of <code>OncePerRequestFilter</code> that executed per request dispatch.
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    protected final AppLogger log = new AppLogger(this.getClass());

    @Autowired
    private JWTSettings jwtSettings;

    @Autowired
    private JwtTokenAuthenticationService jwtTokenAuthenticationService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * The ErrorTrackerService.
     */
    @Autowired
    private ErrorTrackerService errorTrackerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Cross domain settings
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", "*");

        if ("OPTIONS".equals(request.getMethod())) {
            return;
        }

        String jwtToken = getJwtToken(request);

        if (jwtToken != null) {
            if (jwtTokenAuthenticationService.validateJwtToken(jwtToken)) {
                String login = jwtTokenAuthenticationService.getUserLoginFromJwtToken(jwtToken);

                if (login != null) {
                    User user = (User) userDetailsService.loadUserByUsername(login);
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(user.getLogin(), null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request) {
        String token = request.getHeader(jwtSettings.getHeader());

        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(jwtSettings.getHeader());
        }

        return token;
    }
}
