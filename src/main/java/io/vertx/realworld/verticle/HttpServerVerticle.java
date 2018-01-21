package io.vertx.realworld.verticle;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import io.vertx.reactivex.ext.web.handler.ResponseContentTypeHandler;
import io.vertx.realworld.annotation.AnnotationProcessor;
import io.vertx.realworld.handler.ErrorHandler;
import io.vertx.realworld.helper.ConfigHelper;
import io.vertx.realworld.helper.DatabaseServiceHelper;
import io.vertx.realworld.helper.HttpClientHelper;
import io.vertx.realworld.helper.MessageServiceHelper;
import io.vertx.realworld.reactive.DatabaseService;
import io.vertx.realworld.reactive.MessagingService;

public class HttpServerVerticle extends BaseVerticle {
	protected static Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
	private Single<HttpServer> server;
	private Router mainRouter;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			DatabaseServiceHelper.INSTANCE.setDbService(
					DatabaseService.createProxy(vertx, io.vertx.realworld.service.DatabaseService.DEFAULT_ADDRESS));
			MessageServiceHelper.INSTANCE.setMessageService(
					MessagingService.createProxy(vertx, io.vertx.realworld.service.MessagingService.DEFAULT_ADDRESS));
			this.server = createHttpServer(createOptions(ConfigHelper.isHTTP2Enabled()), buildRouter());
			server.subscribe((result -> {
				HttpClientHelper.INSTANCE.setHttpClient(buildHttpClient());
				startFuture.complete();
				logger.info("HTTP server running on port {}", result.actualPort());
			}), ex -> {
				startFuture.fail(ex);
			});
		} catch (Exception ex) {
			logger.error("Failed to start HTTP Server ", ex);
			startFuture.fail(ex);
		}
	}

	private Single<HttpServer> createHttpServer(final HttpServerOptions httpOptions, final Router router) {
		return vertx.createHttpServer(httpOptions).requestHandler(router::accept).rxListen(ConfigHelper.getPort(),
				ConfigHelper.getHost());
	}

	private HttpServerOptions createOptions(boolean http2) {
		HttpServerOptions serverOptions = new HttpServerOptions(ConfigHelper.getHTTPServerOptions());
		if (http2) {
			serverOptions.setSsl(true)
					.setKeyCertOptions(
							new PemKeyCertOptions().setCertPath("server-cert.pem").setKeyPath("server-key.pem"))
					.setUseAlpn(true);
		}
		return serverOptions;
	}

	private Router buildRouter() {
		this.mainRouter = Router.router(vertx).exceptionHandler((error -> {
			logger.error("Routers not injected", error);
		}));
		mainRouter.route(CONTEXT_PATH + "/*").handler(BodyHandler.create());
		mainRouter.route().handler(ResponseContentTypeHandler.create());
		mainRouter.route(CONTEXT_PATH + "/*").handler(
				CorsHandler.create("*").allowedHeaders(getAllowedHeaders()).exposedHeaders(getAllowedHeaders()));
		AnnotationProcessor.init(this.mainRouter, vertx);
		mainRouter.route(CONTEXT_PATH + "/*").last().failureHandler(ErrorHandler.create(vertx));
		return this.mainRouter;
	}

	public HttpClient buildHttpClient() {
		HttpClientOptions options = new HttpClientOptions();
		if (ConfigHelper.isSSLEnabled()) {
			options.setSsl(true);
			options.setTrustAll(true);
			options.setVerifyHost(false);
		}
		options.setTryUseCompression(true);
		options.setKeepAlive(true);
		options.setMaxPoolSize(50);
		return vertx.createHttpClient(options);
	}

	private Set<String> getAllowedHeaders() {
		Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("X-Requested-With");
		allowHeaders.add("Access-Control-Allow-Origin");
		allowHeaders.add("Origin");
		allowHeaders.add("Content-Type");
		allowHeaders.add("Accept");
		allowHeaders.add(HttpHeaders.AUTHORIZATION.toString());
		return allowHeaders;
	}
}