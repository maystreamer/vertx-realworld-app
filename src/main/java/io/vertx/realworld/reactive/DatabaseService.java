package io.vertx.realworld.reactive;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

@io.vertx.lang.reactivex.RxGen(io.vertx.realworld.service.DatabaseService.class)
public class DatabaseService {
	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DatabaseService that = (DatabaseService) o;
		return delegate.equals(that.delegate);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	public static final io.vertx.lang.reactivex.TypeArg<DatabaseService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
			obj -> new DatabaseService((io.vertx.realworld.service.DatabaseService) obj), DatabaseService::getDelegate);

	private final io.vertx.realworld.service.DatabaseService delegate;

	public DatabaseService(io.vertx.realworld.service.DatabaseService delegate) {
		this.delegate = delegate;
	}

	public io.vertx.realworld.service.DatabaseService getDelegate() {
		return delegate;
	}

	public void findOne(String collection, JsonObject query, JsonObject fields,
			Handler<AsyncResult<JsonObject>> resultHandler) {
		delegate.findOne(collection, query, fields, new Handler<AsyncResult<JsonObject>>() {
			@Override
			public void handle(AsyncResult<JsonObject> ar) {
				if (ar.succeeded()) {
					resultHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
				} else {
					resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
				}
			}
		});
	}

	public Single<JsonObject> rxFindOne(String collection, JsonObject query, JsonObject fields) {
		return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
			findOne(collection, query, fields, handler);
		});
	}

	public void insertOne(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
		delegate.insertOne(collection, document, new Handler<AsyncResult<String>>() {
			@Override
			public void handle(AsyncResult<String> ar) {
				if (ar.succeeded()) {
					resultHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
				} else {
					resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
				}
			}
		});
	}

	public Single<String> rxInsertOne(String collection, JsonObject document) {
		return new io.vertx.reactivex.core.impl.AsyncResultSingle<String>(handler -> {
			insertOne(collection, document, handler);
		});
	}

	public void findOneAndUpdate(String collection, JsonObject query, JsonObject toUpdate,
			Handler<AsyncResult<JsonObject>> resultHandler) {
		delegate.findOneAndUpdate(collection, query, toUpdate, new Handler<AsyncResult<JsonObject>>() {
			@Override
			public void handle(AsyncResult<JsonObject> ar) {
				if (ar.succeeded()) {
					resultHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
				} else {
					resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
				}
			}
		});
	}

	public Single<JsonObject> rxFindOneAndUpdate(String collection, JsonObject query, JsonObject toUpdate) {
		return new io.vertx.reactivex.core.impl.AsyncResultSingle<JsonObject>(handler -> {
			findOneAndUpdate(collection, query, toUpdate, handler);
		});
	}

	public static DatabaseService newInstance(io.vertx.realworld.service.DatabaseService arg) {
		return arg != null ? new DatabaseService(arg) : null;
	}

	public static DatabaseService create(MongoClient mongoClient,
			Handler<AsyncResult<io.vertx.realworld.service.DatabaseService>> readyHandler) {
		DatabaseService ret = DatabaseService
				.newInstance(io.vertx.realworld.service.DatabaseService.create(mongoClient, readyHandler));
		return ret;
	}

	public static DatabaseService createProxy(Vertx vertx, String address) {
		DatabaseService ret = DatabaseService
				.newInstance(io.vertx.realworld.service.DatabaseService.createProxy(vertx.getDelegate(), address));
		return ret;
	}
}