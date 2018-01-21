package io.vertx.realworld.model;

import java.util.Date;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.util.DateUtil;
import io.vertx.realworld.util.PasswordStorageUtil;

@DataObject(generateConverter = true)
public class AppUser extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1569695920143846592L;
	public static final String DB_TABLE = "app_users";

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String salt;

	// Mandatory for data objects
	public AppUser(JsonObject jsonObject) {
		AppUserConverter.fromJson(jsonObject, this);
		fromBaseJson(jsonObject, this);
	}

	// for api handers
	public AppUser() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		AppUserConverter.toJson(this, json);
		toBaseJson(this, json);
		return json;
	}

	public AppUser toNewAppUser(boolean isSignUpUser) {
		String[] passwordAndSalt = PasswordStorageUtil.encrypt(this.getPassword());
		if (null != passwordAndSalt && passwordAndSalt.length == 2) {
			this.setSalt(passwordAndSalt[1]);
			this.setCreatedDate(DateUtil.toISO8601UTC(new Date()));
			this.setIsActive(!isSignUpUser);
			this.setUpdatedDate(DateUtil.toISO8601UTC(new Date()));
			this.setPassword(passwordAndSalt[0]);
			return this;
		}
		throw new RestException("Error while building AppUser");
	}
}