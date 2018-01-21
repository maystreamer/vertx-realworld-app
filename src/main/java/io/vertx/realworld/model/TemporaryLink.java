package io.vertx.realworld.model;

import java.util.Date;

import org.bson.types.ObjectId;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.realworld.util.DateUtil;
import io.vertx.realworld.util.TokenUtil;

@DataObject(generateConverter = true)
public class TemporaryLink extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5371205617965641100L;
	public static final String DB_TABLE = "temporary_links";

	private String link;
	private Boolean isActive;
	private ObjectId appUserId;

	// Mandatory for data objects
	public TemporaryLink(JsonObject jsonObject) {
		TemporaryLinkConverter.fromJson(jsonObject, this);
		fromBaseJson(jsonObject, this);
		if (jsonObject.getJsonObject("appUserId") instanceof JsonObject) {
			this.setAppUserId(new ObjectId(jsonObject.getJsonObject("appUserId").getValue("$oid").toString()));
		}
	}

	public TemporaryLink(String link, boolean isActive) {
		this.link = link;
		this.isActive = isActive;
	}

	public String getLink() {
		if (null == this.link)
			return TokenUtil.getUUID();
		return link;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ObjectId getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(ObjectId appUserId) {
		this.appUserId = appUserId;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		TemporaryLinkConverter.toJson(this, json);
		if (this.getAppUserId() != null) {
			json.put("appUserId", new JsonObject().put("$oid", this.getAppUserId().toHexString()));
		}
		toBaseJson(this, json);
		return json;
	}

	public static TemporaryLink toNewTemporaryLink(final AppUser appUser) {
		TemporaryLink link = new TemporaryLink(TokenUtil.getUUID(), true);
		link.setAppUserId(appUser.get_id());
		link.setCreatedBy(appUser.get_id());
		link.setCreatedDate(DateUtil.toISO8601UTC(new Date()));
		link.setUpdatedBy(appUser.get_id());
		link.setUpdatedDate(DateUtil.toISO8601UTC(new Date()));
		return link;
	}
}