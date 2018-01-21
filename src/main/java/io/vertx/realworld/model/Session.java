package io.vertx.realworld.model;

import java.util.Date;

import org.bson.types.ObjectId;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.realworld.util.DateUtil;
import io.vertx.realworld.util.TokenUtil;

@DataObject(generateConverter = true)
public class Session extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7369431750943267578L;
	public static final String DB_TABLE = "sessions";

	private ObjectId _id;
	private String token;
	private ObjectId appUserId;

	// Mandatory for data objects
	public Session(JsonObject jsonObject) {
		SessionConverter.fromJson(jsonObject, this);
		fromBaseJson(jsonObject, this);
		if (jsonObject.getJsonObject("appUserId") instanceof JsonObject) {
			this.setAppUserId(new ObjectId(jsonObject.getJsonObject("appUserId").getValue("$oid").toString()));
		}
	}

	public Session() {
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ObjectId getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(ObjectId appUserId) {
		this.appUserId = appUserId;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		SessionConverter.toJson(this, json);
		if (this.getAppUserId() != null) {
			json.put("appUserId", new JsonObject().put("$oid", this.getAppUserId().toHexString()));
		}
		toBaseJson(this, json);
		return json;
	}

	public static Session buildSession(final AppUser appUser) {
		Session session = new Session();
		session.setToken(TokenUtil.getUUID());
		session.setAppUserId(appUser.get_id());
		session.setCreatedBy(appUser.get_id());
		session.setCreatedDate(DateUtil.toISO8601UTC(new Date()));
		session.setIsActive(true);
		session.setUpdatedBy(appUser.get_id());
		session.setUpdatedDate(DateUtil.toISO8601UTC(new Date()));
		return session;
	}
}