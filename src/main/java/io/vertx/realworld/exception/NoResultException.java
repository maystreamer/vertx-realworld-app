package io.vertx.realworld.exception;

public class NoResultException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417444327304066054L;

	private static final String DEFAULT_MESSAGE = "No records found.";
	private static final int DEFAULT_STATUS_CODE = 404;

	public NoResultException() {
		super(DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>NoResultException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public NoResultException(String msg) {
		super(msg != null ? msg : DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>NoResultException</code> with the specified message
	 * and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public NoResultException(String msg, Throwable t) {
		super(msg != null ? msg : DEFAULT_MESSAGE, t);
		statusCode = DEFAULT_STATUS_CODE;
	}

	public NoResultException(String msg, int code) {
		super(msg != null ? msg : DEFAULT_MESSAGE, code);
	}
}
