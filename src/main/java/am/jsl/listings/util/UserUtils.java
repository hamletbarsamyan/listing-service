package am.jsl.listings.util;

/**
 * Defines constants and methods used in user classes.
 * @author hamlet
 */
public class UserUtils {
    public static final String USER_PROFILE_DEFAULT_IMG = "/static/img/profile.jpg";
    /**
     * User generated static resource paths.
     * For each user will be created a separate folder with name consisting user id.
     */
    public static final String USER_IMG_PATH = "/userimg/";
    public static final String USER_IMG_PATHPATTERN = "/userimg/**";
    public static final String USER_HTML_PATH = "/userhtml/";
    public static final String USER_HTML_PATHPATTERN = "/userhtml/**";

    public static final int PROFILE_IMG_WIDTH = 225;
    public static final int PROFILE_IMG_HEIGHT = 225;

    public static String getIconPath(String iconServer, String icon, long userId) {
        if (TextUtils.isEmpty(icon)) {
            return null;
        }

        StringBuilder path = new StringBuilder();
        if (!TextUtils.isEmpty(iconServer)) {
            path.append(iconServer);
        }

        path.append(UserUtils.USER_IMG_PATH).append(userId);
        path.append(Constants.SLASH).append(icon);
        return  path.toString();
    }
}
