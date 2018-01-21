package io.vertx.realworld.helper;

import io.vertx.reactivex.core.http.HttpClient;

public enum HttpClientHelper {
	INSTANCE;
	private HttpClient client;

	public void setHttpClient(final HttpClient client) {
		this.client = client;
	}

	public HttpClient getHttpClient() {
		return this.client;
	}
}