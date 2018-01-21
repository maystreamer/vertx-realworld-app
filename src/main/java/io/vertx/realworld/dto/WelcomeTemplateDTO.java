package io.vertx.realworld.dto;

public class WelcomeTemplateDTO {
	private final String fullName;
	private final String activationLink;

	public WelcomeTemplateDTO(String fullName, String activationLink) {
		this.fullName = fullName;
		this.activationLink = activationLink;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getActivationLink() {
		return this.activationLink;
	}
}