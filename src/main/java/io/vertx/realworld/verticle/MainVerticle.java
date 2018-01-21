package io.vertx.realworld.verticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.CompositeFuture;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.helper.ConfigHelper;

public class MainVerticle extends AbstractVerticle {
	protected static Logger logger = LoggerFactory.getLogger(MainVerticle.class);

	@Override
	@SuppressWarnings("rawtypes")
	public void start(Future<Void> startFuture) throws Exception {
		final Reflections reflections = new Reflections(ConfigHelper.getVerticlePackage());
		final Set<Class<? extends BaseVerticle>> clazzes = reflections.getSubTypesOf(BaseVerticle.class);
		List<io.vertx.reactivex.core.Future> futures = new ArrayList<io.vertx.reactivex.core.Future>();
		if (null != clazzes && !clazzes.isEmpty()) {
			for (Class<? extends BaseVerticle> verticle : clazzes) {
				final String clazzFullName = verticle.getCanonicalName();
				final String clazzName = verticle.getSimpleName();
				futures.add(deploy(clazzFullName, clazzName));
			}
			CompositeFuture.all(futures).setHandler(ar -> {
				if (ar.succeeded()) {
					logger.info("All verticles deployed successfully");
					System.out.println("All verticles deployed successfully");
					startFuture.complete();
				} else {
					logger.error("An error occured while deploying verticles ", ar.cause());
					startFuture.fail(ar.cause());
				}

			});
		} else {
			startFuture.fail("No verticles to deploy");
		}
	}

	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
	}

	private io.vertx.reactivex.core.Future<Void> deploy(final String clazzFullName, final String clazzName) {
		io.vertx.reactivex.core.Future<Void> future = io.vertx.reactivex.core.Future.future();
		vertx.deployVerticle(clazzFullName, getDeploymentOptions(clazzName), ar -> {
			if (ar.succeeded()) {
				logger.info(String.join(" ", clazzName, "deployed successfully with deployment id: ", ar.result()));
				future.complete();
			} else {
				logger.error(String.join(" ", "Failed to deploy verticle ", clazzName));
				future.fail(ar.cause());
			}
		});
		return future;
	}

	private DeploymentOptions getDeploymentOptions(final String clazzName) {
		try {
			final String key = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
			final JsonObject json = ConfigHelper.getValueByEnvironment(key);
			DeploymentOptions options = new DeploymentOptions(json);
			return options;
		} catch (Exception ex) {
			throw new RestException(
					"Failed to load DeploymentOptions from configuration file for verticle: " + clazzName);
		}
	}
}
