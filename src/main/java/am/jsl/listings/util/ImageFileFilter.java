package am.jsl.listings.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Extended FileFilter for filtering image files.
 * @author hamlet
 */
public class ImageFileFilter implements FileFilter {

	private static final String[] extensions;

	static {
		extensions = new String[] {"jpeg", "jpg", "png", "gif"};
	}
	private static final String description = "Image files";

	public boolean accept(File f) {
		String name = f.getName().toLowerCase();
		for (int i = extensions.length - 1; i >= 0; i--) {
			if (name.endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidImageExtension(String extension) {
		if (!TextUtils.hasText(extension)) {
			return false;
		}
		String name = extension.toLowerCase();

		for (String ext : extensions) {
			if (name.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return description;
	}
}
