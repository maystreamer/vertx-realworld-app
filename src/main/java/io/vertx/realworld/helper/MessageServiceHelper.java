package io.vertx.realworld.helper;

import io.vertx.realworld.reactive.MessagingService;

public enum MessageServiceHelper {
	INSTANCE;
	private MessagingService messagingService;

	public MessagingService getMessageService() {
		return messagingService;
	}

	public void setMessageService(MessagingService messagingService) {
		this.messagingService = messagingService;
	}
}