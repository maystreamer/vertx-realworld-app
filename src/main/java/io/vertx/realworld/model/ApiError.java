package io.vertx.realworld.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * @author saurabh Generic error object to be thrown for any occurence of error
 *         while accessing the APIs.
 */

@JsonDeserialize(builder = ApiError.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError<T> {
	// Status code - HTTP or any custom
	private final Integer code;
	// Error message to be thrown
	private final String message;
	private T error;

	private ApiError(Builder<T> builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.error = builder.error;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder<T> {
		private Integer code;
		private String message;
		private T error;

		public Builder() {
		}

		public Builder<T> setCode(Integer code) {
			this.code = code;
			return this;
		}

		public Builder<T> setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder<T> setError(T error) {
			this.error = error;
			return this;
		}

		public ApiError<T> build() {
			return new ApiError<T>(this);
		}
	}

	public Integer getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public T getError() {
		return this.error;
	}
}