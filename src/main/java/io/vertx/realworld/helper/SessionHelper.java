package io.vertx.realworld.helper;

import org.bson.types.ObjectId;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.realworld.constant.Constants;
import io.vertx.realworld.dto.LoginDTO;
import io.vertx.realworld.exception.BadCredentialsException;
import io.vertx.realworld.exception.InvalidTokenException;
import io.vertx.realworld.exception.UserNotActivatedException;
import io.vertx.realworld.model.AppUser;
import io.vertx.realworld.model.Session;
import io.vertx.realworld.reactive.DatabaseService;
import io.vertx.realworld.util.PasswordAuthenticator;

public enum SessionHelper implements AuthProvider {
	INSTANCE;
	private static final DatabaseService dbService = DatabaseServiceHelper.INSTANCE.getDbService();

	public Single<AppUser> doAuthenticate(final LoginDTO creds) {
		JsonObject query = new JsonObject().put("email", creds.getEmail());
		return dbService.rxFindOne(AppUser.DB_TABLE, query, null).map(result -> {
			if (null == result)
				throw new BadCredentialsException();
			AppUser _user = new AppUser(result);
			if (_user.getIsActive().booleanValue() == false)
				throw new UserNotActivatedException();
			_user.set_id(new ObjectId(result.getString("_id")));
			boolean isPasswordOk = PasswordAuthenticator.isPasswordOk(creds.getPassword(), _user.getPassword(),
					_user.getSalt());
			if (!isPasswordOk) {
				throw new BadCredentialsException();
			}
			return _user;
		});
	}

	public Single<Session> createSession(final AppUser appUser) {
		final Session session = Session.buildSession(appUser);
		return dbService.rxInsertOne(Session.DB_TABLE, session.toJson()).map(result -> {
			session.set_id(new ObjectId(result));
			return session;
		});
	}

	public Single<Session> doValidateSession(final String authHeader) {
		String token = parseSessionToken(authHeader);
		JsonObject query = new JsonObject().put("token", token).put("isActive", true);
		return dbService.rxFindOne(Session.DB_TABLE, query, null).map(result -> {
			if (null == result)
				throw new InvalidTokenException();
			return new Session(result);
		});
	}

	public Single<JsonObject> doLogoutSession(final String authHeader) {
		String token = parseSessionToken(authHeader);
		JsonObject query = new JsonObject().put("token", token);
		JsonObject toUpdate = new JsonObject();
		toUpdate.put("$set", new JsonObject().put("isActive", false));
		return dbService.rxFindOneAndUpdate(Session.DB_TABLE, query, toUpdate).map(result -> {
			if (null == result)
				throw new InvalidTokenException();
			return result;
		});
	}

	private String parseSessionToken(final String authHeader) {
		String[] parts = authHeader.split(" ");
		String sscheme = parts[0];
		if (!Constants.AUTH_SCHEME.equals(sscheme)) {
			throw new InvalidTokenException("Invalid token scheme. Should be BEARER");
		}
		if (parts.length < 2) {
			throw new InvalidTokenException();
		}
		return parts[1];
	}

	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {

	}
}