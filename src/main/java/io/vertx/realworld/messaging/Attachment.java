package io.vertx.realworld.messaging;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Attachment.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment {
	private String filePath;
	private String name;
	private String contentType;
	private String disposition;
	private String description;
	private String contentId;
	private Map<String, String> headers;
	private Boolean isInlineAttachment;

	private Attachment(Builder builder) {
		this.filePath = builder.filePath;
		this.name = builder.name;
		this.contentType = builder.contentType;
		this.disposition = builder.disposition;
		this.description = builder.description;
		this.contentId = builder.contentId;
		this.headers = builder.headers;
		this.isInlineAttachment = builder.isInlineAttachment;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {
		private String filePath;
		private String name;
		private String contentType;
		private String disposition;
		private String description;
		private String contentId;
		private Map<String, String> headers;
		private Boolean isInlineAttachment;

		public Builder() {
		}

		public Builder setFilePath(String filePath) {
			this.filePath = filePath;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder setDisposition(String disposition) {
			this.disposition = disposition;
			return this;
		}

		public Builder setContentId(String contentId) {
			this.contentId = contentId;
			return this;
		}

		public Builder setHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		public Builder setIsInlineAttachment(Boolean isInlineAttachment) {
			this.isInlineAttachment = isInlineAttachment;
			return this;
		}

		public Attachment build() {
			return new Attachment(this);
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public String getDisposition() {
		return disposition;
	}

	public String getDescription() {
		return description;
	}

	public String getContentId() {
		return contentId;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Boolean getIsInlineAttachment() {
		return isInlineAttachment;
	}
}