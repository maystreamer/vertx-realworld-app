package io.vertx.realworld.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractEmailTemplate<T> {
	protected String templatePath;

	public AbstractEmailTemplate(String templatePath) {
		this.templatePath = templatePath;
	}

	protected String readTemplate() throws IOException {
		return new String(Files.readAllBytes(Paths.get(this.templatePath)));
	}

	public abstract String generateTemplate(T data) throws IOException;
}