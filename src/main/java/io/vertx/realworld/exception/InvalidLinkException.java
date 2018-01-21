package io.vertx.realworld.exception;

public class InvalidLinkException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023354109026635110L;
	private static final String DEFAULT_MESSAGE = "Link not found.";
	private static final int DEFAULT_STATUS_CODE = 404;

	public InvalidLinkException() {
		super(DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>InvalidLinkException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public InvalidLinkException(String msg) {
		super(msg != null ? msg : DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>InvalidLinkException</code> with the specified message
	 * and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public InvalidLinkException(String msg, Throwable t) {
		super(msg != null ? msg : DEFAULT_MESSAGE, t);
		statusCode = DEFAULT_STATUS_CODE;
	}

	public InvalidLinkException(String msg, int code) {
		super(msg != null ? msg : DEFAULT_MESSAGE, code);
	}
}