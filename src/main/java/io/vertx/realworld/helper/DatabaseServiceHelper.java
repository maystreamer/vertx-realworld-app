package io.vertx.realworld.helper;

import io.vertx.realworld.reactive.DatabaseService;

public enum DatabaseServiceHelper {
	INSTANCE;
	private DatabaseService dbService;

	public DatabaseService getDbService() {
		return dbService;
	}

	public void setDbService(DatabaseService dbService) {
		this.dbService = dbService;
	}
}