package io.vertx.realworld.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import io.vertx.realworld.annotation.EmailTemplate;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TemplateFactory {
	private static Map<String, Class<AbstractEmailTemplate>> emailTemplateMapping = new HashMap<>();
	static {
		final Reflections reflections = new Reflections("io.vertx.realworld.template");
		final Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(EmailTemplate.class);
		if (null != clazzes && !clazzes.isEmpty()) {
			clazzes.forEach(x -> {
				EmailTemplate emailTemplate = x.getAnnotation(EmailTemplate.class);
				if (null != emailTemplate && AbstractEmailTemplate.class.isAssignableFrom(x)) {
					emailTemplateMapping.put(emailTemplate.name(), (Class<AbstractEmailTemplate>) x);
				}
			});
		}
	}

	public static AbstractEmailTemplate getTemplate(final String name) throws ClassNotFoundException {
		Class<AbstractEmailTemplate> clazz = null;
		AbstractEmailTemplate abstractEmailTemplate = null;
		try {
			clazz = emailTemplateMapping.get(name);
			abstractEmailTemplate = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception ex) {

		}
		if (null == abstractEmailTemplate)
			throw new ClassNotFoundException(String.join(" ", "Class ", name, " not found"));
		return abstractEmailTemplate;
	}
}
