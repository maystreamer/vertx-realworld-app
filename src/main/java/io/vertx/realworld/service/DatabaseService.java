package io.vertx.realworld.service;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@VertxGen
@ProxyGen
public interface DatabaseService {
	String DEFAULT_ADDRESS = DatabaseService.class.getName();

	/**
	 * Method called to create a proxy (to consume the service).
	 *
	 * @param vertx
	 *            vert.x
	 * @param address
	 *            the address on the event bus where the service is served.
	 * @return the proxy
	 */
	@GenIgnore
	static DatabaseService createProxy(Vertx vertx, String address) {
		return new ServiceProxyBuilder(vertx).setAddress(address).build(DatabaseService.class);
	}

	@GenIgnore
	static DatabaseService create(final MongoClient mongoClient, Handler<AsyncResult<DatabaseService>> readyHandler) {
		return new DatabaseServiceImpl(mongoClient, readyHandler);
	}

	@Fluent
	public DatabaseService findOne(String collection, JsonObject query, JsonObject fields,
			Handler<AsyncResult<JsonObject>> resultHandler);

	@Fluent
	public DatabaseService insertOne(String collection, JsonObject document,
			Handler<AsyncResult<String>> resultHandler);

	@Fluent
	public DatabaseService findOneAndUpdate(String collection, JsonObject query, JsonObject toUpdate,
			Handler<AsyncResult<JsonObject>> resultHandler);

	@ProxyClose
	public void close();
}