package io.vertx.realworld.exception;

import io.vertx.realworld.model.ApiError;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExceptionMapper {

	public static <T> ApiError<T> build(Exception ex) {
		if (ex instanceof RestException) {
			RestException rx = (RestException) ex;
			final String message = rx.getCause() != null ? rx.getCause().getMessage() : rx.getMessage();
			if (null != rx.getErrorJson()) {
				return new ApiError.Builder().setCode(rx.getStatusCode()).setMessage(message)
						.setError(rx.getErrorJson()).build();
			}
			return new ApiError.Builder().setCode(rx.getStatusCode()).setMessage(message).build();
		} else {
			return new ApiError.Builder().setCode(500).setMessage(ex.getMessage()).build();
		}
	}

	public static <T> T build(Throwable cause) {
		return (T) new ApiError.Builder().setCode(500).setMessage(cause.getMessage()).build();
	}
}