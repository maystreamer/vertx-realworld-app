package io.vertx.realworld.exception;

public class UserNotFoundException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023354109026635110L;
	private static final String DEFAULT_MESSAGE = "User not found.";
	private static final int DEFAULT_STATUS_CODE = 404;

	public UserNotFoundException() {
		super(DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>UserNotFoundException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public UserNotFoundException(String msg) {
		super(msg != null ? msg : DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>UserNotFoundException</code> with the specified message
	 * and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public UserNotFoundException(String msg, Throwable t) {
		super(msg != null ? msg : DEFAULT_MESSAGE, t);
		statusCode = DEFAULT_STATUS_CODE;
	}

	public UserNotFoundException(String msg, int code) {
		super(msg != null ? msg : DEFAULT_MESSAGE, code);
	}
}