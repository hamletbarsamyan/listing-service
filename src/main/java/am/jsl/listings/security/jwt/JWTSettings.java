package am.jsl.listings.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Contains jwt externalized settings.
 * @author hamlet
 */
@Component
@ConfigurationProperties(prefix = "listings.jwt")
public class JWTSettings {
    /**
     * The header key for storing tokens
     */
    private String header;

    /**
     * The token prefix
     */
    private String tokenPrefix;

    /**
     * The RSA private key
     */
    private String privateKey;

    /**
     * The RSA public key
     */
    private String publicKey;

    /**
     * The authorities key
     */
    private String authoritiesKey;

    /**
     * The expiration time in minutes
     */
    private long expirationTime;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAuthoritiesKey() {
        return authoritiesKey;
    }

    public void setAuthoritiesKey(String authoritiesKey) {
        this.authoritiesKey = authoritiesKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
