package am.jsl.listings.ex;

/**
 * Will be thrown when something went wrong during internal operation.
 * @author hamlet
 */
public class AppException extends RuntimeException {

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

}
