package io.vertx.realworld.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * @author saurabh Class to create the generic API responses to be send back to
 *         the consumers. All the rest API responses will be build using this.
 */
@JsonDeserialize(builder = ApiResponse.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	// HTTP status code to return
	private final int code;
	// Information to return
	private final String message;
	// For the consumers to know if there is any error occured
	private final boolean hasError;
	// To send the data - valid data or the error data to the consumers;
	private final T data;

	private ApiResponse(Builder<T> builder) {
		this.code = builder.code;
		this.message = builder.message;
		this.hasError = builder.hasError;
		this.data = (T) builder.data;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder<T> {
		private int code;
		private String message;
		private boolean hasError;
		private T data;

		public Builder() {
		}

		public Builder<T> setCode(int code) {
			this.code = code;
			return this;
		}

		public Builder<T> setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder<T> setHasError(boolean hasError) {
			this.hasError = hasError;
			return this;
		}

		public Builder<T> setData(T data) {
			this.data = data;
			return this;
		}

		public ApiResponse<T> build() {
			return new ApiResponse<T>(this);
		}
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isHasError() {
		return hasError;
	}

	public T getData() {
		return data;
	}
}