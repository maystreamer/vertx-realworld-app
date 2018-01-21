package io.vertx.realworld.exception;

public class EmailException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3495230659920479398L;

	private static final String DEFAULT_MESSAGE = "Error while sending email.";
	private static final int DEFAULT_STATUS_CODE = 500;

	public EmailException() {
		super(DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>EmailException</code> with the specified message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public EmailException(String msg) {
		super(msg != null ? msg : DEFAULT_MESSAGE);
		statusCode = DEFAULT_STATUS_CODE;
	}

	/**
	 * Constructs a <code>EmailException</code> with the specified message
	 * and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public EmailException(String msg, Throwable t) {
		super(msg != null ? msg : DEFAULT_MESSAGE, t);
		statusCode = DEFAULT_STATUS_CODE;
	}

	public EmailException(String msg, int code) {
		super(msg != null ? msg : DEFAULT_MESSAGE, code);
	}
}
