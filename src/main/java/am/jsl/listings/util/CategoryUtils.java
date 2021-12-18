package am.jsl.listings.util;

/**
 * Defines constants and methods used in category classes.
 * @author hamlet
 */
public class CategoryUtils {
    public static final int CATEGORY_IMG_WIDTH = 80;
    public static final int CATEGORY_IMG_HEIGHT = 80;
    public static final String CATEGORY_IMG_PATH = "/category_img/";
    public static final String CATEGORY_IMG_PATHPATTERN = "/category_img/**";

    public static String getIconPath(String iconServer, String icon, long categoryId) {
        if (TextUtils.isEmpty(icon)) {
            return null;
        }

        StringBuilder path = new StringBuilder();
        if (!TextUtils.isEmpty(iconServer)) {
            path.append(iconServer);
        }

        path.append(CATEGORY_IMG_PATH).append(categoryId);
        path.append(Constants.SLASH).append(icon);
        return  path.toString();
    }
}
