package io.vertx.realworld.verticle;

import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.realworld.helper.ConfigHelper;
import io.vertx.realworld.service.DatabaseService;
import io.vertx.serviceproxy.ServiceBinder;

public class DatabaseVerticle extends BaseVerticle {
	protected static Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);
	private DatabaseService dbService;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			MongoClient mongoClient = MongoClient.createShared(vertx, ConfigHelper.getMongoConfig());
			dbService = DatabaseService.create(mongoClient, ready -> {
				if (ready.succeeded()) {
					new ServiceBinder(vertx.getDelegate()).setAddress(DatabaseService.DEFAULT_ADDRESS)
							.register(DatabaseService.class, ready.result()).completionHandler(ar -> {
								if (ar.succeeded()) {
									startFuture.complete();
								} else {
									startFuture.fail(ar.cause());
								}
							});
				} else {
					startFuture.fail(ready.cause());
				}
			});
		} catch (Exception ex) {
			logger.error("Error while deploying DatabaseVerticle ", ex);
			startFuture.fail(ex);
		}
	}

	@Override
	public void stop() throws Exception {
		dbService.close();
	}
}