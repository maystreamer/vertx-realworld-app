package io.vertx.realworld.messaging;

public abstract class AbstractMessage {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}