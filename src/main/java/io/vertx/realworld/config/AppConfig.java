package io.vertx.realworld.config;

import io.vertx.core.json.JsonObject;

public enum AppConfig {
	INSTANCE;
	private JsonObject config;

	public JsonObject getConfig() {
		return config;
	}

	public void setConfig(final JsonObject config) {
		this.config = config;
	}
}