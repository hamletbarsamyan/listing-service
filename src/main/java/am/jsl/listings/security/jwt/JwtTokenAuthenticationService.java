package am.jsl.listings.security.jwt;

import am.jsl.listings.domain.user.User;
import am.jsl.listings.log.AppLogger;
import am.jsl.listings.security.SecurityUtils;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

/**
 * Defines methods for generating, parsing and validating jwt tokens.
 */
@Component
public class JwtTokenAuthenticationService {
    private static AppLogger logger = new AppLogger(JwtTokenAuthenticationService.class);

    @Value("${listings.jwt.secret}")
    private String jwtSecret;

    @Value("${listings.jwt.expiration-time}")
    private int jwtExpiration;

    public void addAuthentication(HttpServletResponse res, String login) throws IOException {
        String jwtToken = generateJwtToken(login);
        res.addHeader(SecurityUtils.HEADER_STRING, SecurityUtils.TOKEN_PREFIX + " " + jwtToken);
        res.getWriter().write("{\"token\":\"" + jwtToken + "\"}");
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityUtils.HEADER_STRING);

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token.replace(SecurityUtils.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        return generateJwtToken(userName);
    }

    public String generateJwtToken(String login) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(now)
                .setExpiration(DateUtils.addMinutes(now, jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserLoginFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e);
        }

        return false;
    }
}
