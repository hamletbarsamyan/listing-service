package am.jsl.listings.ex;

/**
 * Will be thrown if a category slug already exists.
 * @author hamlet
 */
public class DuplicateSlugException extends RuntimeException {

    public DuplicateSlugException() {
    }

    public DuplicateSlugException(String arg0) {
        super(arg0);
    }

    public DuplicateSlugException(Throwable arg0) {
        super(arg0);
    }

    public DuplicateSlugException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DuplicateSlugException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
