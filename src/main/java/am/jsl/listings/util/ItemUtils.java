package am.jsl.listings.util;

/**
 * Defines constants and methods used in item classes.
 * @author hamlet
 */
public class ItemUtils {
    public static final String THUMBS = "thumbs";

    public static final int THUMBNAIL_WIDTH = 150;
    public static final int THUMBNAIL_HEIGHT = 150;

    public static final String ITEM_IMG_PATH = "/item_img/";
    public static final String ITEM_IMG_PATHPATTERN = "/item_img/**";

    public static String getIconPath(String iconServer, String icon, long itemId) {
        if (TextUtils.isEmpty(icon)) {
            return null;
        }

        StringBuilder path = new StringBuilder();
        if (!TextUtils.isEmpty(iconServer)) {
            path.append(iconServer);
        }

        path.append(ITEM_IMG_PATH).append(itemId);
        path.append(Constants.SLASH).append(icon);
        return  path.toString();
    }
}
