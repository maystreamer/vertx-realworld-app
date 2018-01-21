package io.vertx.realworld.exception;

import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceException;

public class DuplicateEntryException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7737541716180724179L;
	private static final String DEFAULT_MESSAGE = "Entry already exists.";
	private static final int DEFAULT_STATUS_CODE = 400;

	public DuplicateEntryException(int failureCode, String message, JsonObject debugInfo) {
		super(failureCode, message, debugInfo);
	}

	public DuplicateEntryException(int failureCode, String message) {
		super(failureCode, message);
	}

	public DuplicateEntryException() {
		super(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
	}
}