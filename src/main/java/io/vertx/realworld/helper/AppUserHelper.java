package io.vertx.realworld.helper;

import static org.apache.commons.lang3.StringUtils.isBlank;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.exception.UserNotFoundException;
import io.vertx.realworld.model.AppUser;
import io.vertx.realworld.reactive.DatabaseService;

public enum AppUserHelper {
	INSTANCE;

	private static final DatabaseService dbService = DatabaseServiceHelper.INSTANCE.getDbService();

	public Single<AppUser> doValidate(final AppUser appUser, boolean isSignUpUser) {
		String email = appUser.getEmail();
		String password = appUser.getPassword();
		String firstName = appUser.getFirstName();
		String lastName = appUser.getLastName();
		JsonObject errors = new JsonObject();
		boolean hasError = false;

		if (isBlank(firstName)) {
			hasError = true;
			errors.put("firstName", "Firstname is required.");
		}
		if (isBlank(lastName)) {
			hasError = true;
			errors.put("lastName", "Lastname is required.");
		}
		if (isBlank(email)) {
			hasError = true;
			errors.put("email", "Email is required.");
		}
		if (isBlank(password)) {
			hasError = true;
			errors.put("password", "Password is required.");
		}
		if (hasError) {
			JsonObject error = new JsonObject().put("errors", errors);
			throw new RestException(error, HttpResponseStatus.BAD_REQUEST.code());
		}
		return Single.just(appUser.toNewAppUser(isSignUpUser));
	}

	public Single<JsonObject> doCreate(final JsonObject appUser) {
		return dbService.rxInsertOne(AppUser.DB_TABLE, appUser).map(result -> {
			appUser.put("_id", new JsonObject().put("$oid", result));
			return appUser;
		});
	}

	public Single<JsonObject> updateAppUser(final JsonObject query, final JsonObject toUpdate) {
		return dbService.rxFindOneAndUpdate(AppUser.DB_TABLE, query, toUpdate).map(result -> {
			if (null == result)
				throw new UserNotFoundException();
			return result;
		});
	}
}