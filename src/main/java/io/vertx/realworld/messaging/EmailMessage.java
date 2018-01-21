package io.vertx.realworld.messaging;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = EmailMessage.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailMessage {
	private String from;
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	private String subject;
	private String content;
	private Boolean isContentHTML;
	private List<Attachment> attachments;
	private Map<String, String> headers;

	private EmailMessage(Builder builder) {
		this.from = builder.from;
		this.to = builder.to;
		this.cc = builder.cc;
		this.bcc = builder.bcc;
		this.subject = builder.subject;
		this.content = builder.content;
		this.isContentHTML = builder.isContentHTML;
		this.attachments = builder.attachments;
		this.headers = builder.headers;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {
		private String from;
		private List<String> to;
		private List<String> cc;
		private List<String> bcc;
		private String subject;
		private String content;
		private Boolean isContentHTML;
		private List<Attachment> attachments;
		private Map<String, String> headers;

		public Builder() {
		}

		public Builder setFrom(String from) {
			this.from = from;
			return this;
		}

		public Builder setTo(List<String> to) {
			this.to = to;
			return this;
		}

		public Builder setCc(List<String> cc) {
			this.cc = cc;
			return this;
		}

		public Builder setBcc(List<String> bcc) {
			this.bcc = bcc;
			return this;
		}

		public Builder setSubject(String subject) {
			this.subject = subject;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setIsContentHTML(Boolean isContentHTML) {
			this.isContentHTML = isContentHTML;
			return this;
		}

		public Builder setAttachments(List<Attachment> attachments) {
			this.attachments = attachments;
			return this;
		}

		public Builder setHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		public EmailMessage build() {
			return new EmailMessage(this);
		}
	}

	public String getFrom() {
		return from;
	}

	public List<String> getTo() {
		return to;
	}

	public List<String> getCc() {
		return cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public Boolean getIsContentHTML() {
		return isContentHTML;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}