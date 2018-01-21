package io.vertx.realworld.verticle;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.realworld.config.AppConfig;
import io.vertx.realworld.constant.Constants;

public class BaseVerticle extends AbstractVerticle {
	public static String CONTEXT_PATH = AppConfig.INSTANCE.getConfig().getString(Constants.CONTEXT_PATH);
}