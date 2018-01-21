package io.vertx.realworld.verticle;

import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mail.MailConfig;
import io.vertx.reactivex.ext.mail.MailClient;
import io.vertx.realworld.helper.ConfigHelper;
import io.vertx.realworld.messaging.EmailMessage;
import io.vertx.realworld.messaging.EmailMessageCodec;
import io.vertx.realworld.service.MessagingService;
import io.vertx.serviceproxy.ServiceBinder;

public class MessagingVerticle extends BaseVerticle {
	protected static Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);
	private MessagingService messagingService;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			MailClient mailClient = MailClient.createShared(vertx, new MailConfig(ConfigHelper.getMailconfig()));
			messagingService = MessagingService.create(mailClient, ready -> {
				if (ready.succeeded()) {
					EventBus eventBus = vertx.getDelegate().eventBus();
					eventBus.registerDefaultCodec(EmailMessage.class, new EmailMessageCodec());
					new ServiceBinder(vertx.getDelegate()).setAddress(MessagingService.DEFAULT_ADDRESS)
							.register(MessagingService.class, ready.result()).completionHandler(ar -> {
								if (ar.succeeded()) {
									startFuture.complete();
								} else {
									startFuture.fail(ar.cause());
								}
							});
				} else {
					startFuture.fail(ready.cause());
				}
			});
		} catch (Exception ex) {
			logger.error("Error while deploying MessagingVerticle ", ex);
			startFuture.fail(ex);
		}
	}

	@Override
	public void stop() throws Exception {
		messagingService.close();
	}
}