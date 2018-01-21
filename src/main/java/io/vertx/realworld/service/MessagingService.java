package io.vertx.realworld.service;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;
import io.vertx.reactivex.ext.mail.MailClient;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@VertxGen
@ProxyGen
public interface MessagingService {
	String DEFAULT_ADDRESS = MessagingService.class.getName();

	/**
	 * Method called to create a proxy (to consume the service).
	 *
	 * @param vertx
	 *            vert.x
	 * @param address
	 *            the address on the event bus where the service is served.
	 * @return the proxy
	 */
	@GenIgnore
	static MessagingService createProxy(Vertx vertx, String address) {
		return new ServiceProxyBuilder(vertx).setAddress(address).build(MessagingService.class);
	}

	@GenIgnore
	static MessagingService create(final MailClient mailClient, Handler<AsyncResult<MessagingService>> readyHandler) {
		return new MessagingServiceImpl(mailClient, readyHandler);
	}

	@Fluent
	public MessagingService sendMail(final MailMessage mailMessage, Handler<AsyncResult<MailResult>> resultHandler);

	@ProxyClose
	public void close();
}
