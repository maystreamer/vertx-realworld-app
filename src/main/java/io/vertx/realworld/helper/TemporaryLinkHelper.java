package io.vertx.realworld.helper;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.realworld.exception.InvalidLinkException;
import io.vertx.realworld.model.TemporaryLink;
import io.vertx.realworld.reactive.DatabaseService;

public enum TemporaryLinkHelper {
	INSTANCE;
	private static final DatabaseService dbService = DatabaseServiceHelper.INSTANCE.getDbService();

	public Single<Boolean> doValidate(final String link) {
		JsonObject query = new JsonObject().put("link", link).put("isActive", true);
		return dbService.rxFindOne(TemporaryLink.DB_TABLE, query, null).map(result -> {
			if (null == result)
				throw new InvalidLinkException();
			return null;
		});
	}

	public Single<JsonObject> doCreate(final JsonObject link) {
		return dbService.rxInsertOne(TemporaryLink.DB_TABLE, link).map(result -> {
			link.put("_id", new JsonObject().put("$oid", result));
			return link;
		});
	}

	public Single<JsonObject> updateTemporaryLink(final JsonObject query, final JsonObject toUpdate) {
		return dbService.rxFindOneAndUpdate(TemporaryLink.DB_TABLE, query, toUpdate).map(result -> {
			if (null == result)
				throw new InvalidLinkException();
			return result;
		});
	}
}