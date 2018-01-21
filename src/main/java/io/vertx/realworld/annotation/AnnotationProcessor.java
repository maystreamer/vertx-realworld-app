package io.vertx.realworld.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Route;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.handler.AuthHandler;
import io.vertx.realworld.handler.BaseHandler;
import io.vertx.realworld.helper.ConfigHelper;
import io.vertx.realworld.util.DateUtil;
import io.vertx.realworld.util.ResponseUtil;

public final class AnnotationProcessor {
	protected static Logger logger = LoggerFactory.getLogger(AnnotationProcessor.class);
	private static final String PACKAGE_NAME = ConfigHelper.getReflectionPackage();
	private static final String CONTEXT_PATH = ConfigHelper.getContextPath();
	// private static final Reflections reflections = new Reflections(PACKAGE_NAME);

	static Reflections reflections;

	static {
		reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(PACKAGE_NAME))
				.setScanners(new SubTypesScanner(false), new MethodAnnotationsScanner())
				.filterInputsBy(new FilterBuilder().includePackage(PACKAGE_NAME)));
	}

	public static void init(final Router router, final Vertx vertx) {
		final Set<Class<? extends BaseHandler>> clazzes = reflections.getSubTypesOf(BaseHandler.class);
		if (null != clazzes && !clazzes.isEmpty()) {
			AuthHandler authHandler = new AuthHandler(vertx);
			clazzes.forEach(baseHandler -> buildHandler(router, baseHandler, authHandler, vertx));
		} else {
			throw new RestException("There are no handlers to configure");
		}
	}

	private static void buildHandler(final Router router, final Class<? extends BaseHandler> clazz,
			final AuthHandler authHandler, final Vertx vertx) {
		RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
		// TODO: re-factor for the params
		if (null != mapping) {
			String[] baseConsumes = mapping.consumes();
			String[] baseProduces = mapping.produces();
			String basePath = mapping.path();
			Set<Method> methods = getMethodsAnnotatedWith(clazz, RequestMapping.class);
			methods.forEach(method -> {
				RequestMapping annotation = method.getAnnotation(RequestMapping.class);
				String[] methodConsumes = annotation.consumes();
				String[] methodProduces = annotation.produces();
				String methodPath = annotation.path();
				HttpMethod httpMethod = annotation.method();
				Route route = router.route(httpMethod, String.join("", CONTEXT_PATH, basePath, methodPath));
				setMediaType(route, methodConsumes == null ? baseConsumes : methodConsumes, false);
				setMediaType(route, methodProduces == null ? baseProduces : methodProduces, true);
				if (null != method.getAnnotation(Protected.class)) {
					route.handler(authHandler::handle);
				}
				System.out.println(route.getPath());
				createHandler(clazz, vertx, method, route);
			});
		}
	}

	private static void createHandler(final Class<? extends BaseHandler> clazz, final Vertx vertx, Method method,
			Route route) {
		route.handler(ctx -> {
			try {
				// TODO: Need to see a better way of doing this
				long timeInMillis = DateUtil.getTimeInMS();
				logger.info(String.join(" ", "Started executing method",
						method.getDeclaringClass().getName() + "." + method.getName()));
				ResponseUtil.setCookiesForLogging(ctx, method.getDeclaringClass() + "." + method.getName(),
						timeInMillis);
				method.invoke(clazz.getDeclaredConstructor(Vertx.class).newInstance(vertx), ctx);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		});
	}

	private static void setMediaType(final Route route, final String[] mediaTypes, final boolean isProduced) {
		if (null != mediaTypes && mediaTypes.length > 0) {
			Arrays.asList(mediaTypes).forEach(contentType -> {
				if (!isProduced) {
					route.consumes(contentType);
				} else {
					route.produces(contentType);
				}
			});
		}
	}

	private static Set<Method> getMethodsAnnotatedWith(final Class<?> type,
			final Class<? extends Annotation> annotation) {
		final Set<Method> methods = new HashSet<Method>();
		Class<?> klass = type;
		while (klass != Object.class) {
			final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
			for (final Method method : allMethods) {
				if (method.isAnnotationPresent(annotation)) {
					methods.add(method);
				}
			}
			klass = klass.getSuperclass();
		}
		return methods;
	}
}