package io.vertx.realworld.helper;

import static io.vertx.realworld.constant.Constants.REFLECTION_PACKAGE_NAME;
import static io.vertx.realworld.constant.Constants.VERTICLE_PACKAGE_NAME;
import static org.apache.commons.lang3.StringUtils.isBlank;

import io.vertx.core.json.JsonObject;
import io.vertx.realworld.config.AppConfig;
import io.vertx.realworld.constant.Constants;

public class ConfigHelper {

	private static final JsonObject config = AppConfig.INSTANCE.getConfig();

	public static JsonObject loadEnvironment() {
		String environment = System.getenv(Constants.ENVIRONMENT);
		environment = !isBlank(environment) ? environment.trim().toLowerCase()
				: Constants.ENVIRONMENT_DEVELOPMENT.toLowerCase();
		return config.getJsonObject(environment);
	}

	public static JsonObject getValueByEnvironment(final String key) {
		return loadEnvironment().getJsonObject(key);
	}

	public static String getVerticlePackage() {
		return config.getString(VERTICLE_PACKAGE_NAME);
	}

	public static String getReflectionPackage() {
		return config.getString(REFLECTION_PACKAGE_NAME);
	}

	public static String getProtocol() {
		return config.getString(Constants.CONFIG_HTTP_PROTOCOL, "http://");
	}

	public static String getHost() {
		return config.getString(Constants.CONFIG_HTTP_SERVER_HOST, "localhost");
	}

	public static int getPort() {
		return config.getInteger(Constants.CONFIG_HTTP_SERVER_PORT, 8080);
	}

	public static String getContextPath() {
		return config.getString(Constants.CONTEXT_PATH);
	}

	public static String getServerContextPath() {
		return new StringBuilder(getProtocol()).append(getHost()).append(":").append(getPort()).append(getContextPath())
				.toString();
	}

	public static boolean isHTTP2Enabled() {
		return config.getBoolean(Constants.CONFIG_IS_HTTP2_ENABLED, false);
	}

	public static JsonObject getHTTPServerOptions() {
		return config.getJsonObject(Constants.CONFIG_HTTP_SERVER_OPTIONS, new JsonObject());
	}

	public static boolean isSSLEnabled() {
		return config.getBoolean(Constants.IS_SSL_ENABLED, false);
	}

	public static JsonObject getMongoConfig() {
		return getValueByEnvironment(Constants.MONGO_CONFIG);
	}

	public static JsonObject getMailconfig() {
		return getValueByEnvironment(Constants.CONFIG_MAIL_NAME);
	}

	public static String getFromAddress() {
		return getMailconfig().getString(Constants.CONFIG_MAIL_FROM_ADDRESS);
	}
}