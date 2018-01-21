package io.vertx.realworld.exception;

public class UserAlreadyExistsException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 505524577067688375L;
	/**
	 * 
	 */
	private static final String DEFAULT_MESSAGE = "User already exists.";
	private static final int DEFAULT_STATUS_CODE = 400;

	public UserAlreadyExistsException() {
		super(DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>UserAlreadyExistsException</code> with the specified
	 * message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public UserAlreadyExistsException(String msg) {
		super(msg != null ? msg : DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>UserAlreadyExistsException</code> with the specified
	 * message and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public UserAlreadyExistsException(String msg, Throwable t) {
		super(msg != null ? msg : DEFAULT_MESSAGE, t);
		statusCode = DEFAULT_STATUS_CODE;
	}

	public UserAlreadyExistsException(String msg, int code) {
		super(msg != null ? msg : DEFAULT_MESSAGE, code);
	}
}