package io.vertx.realworld.reactive;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mail.MailClient;

@io.vertx.lang.reactivex.RxGen(io.vertx.realworld.service.MessagingService.class)
public class MessagingService {
	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MessagingService that = (MessagingService) o;
		return delegate.equals(that.delegate);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	public static final io.vertx.lang.reactivex.TypeArg<MessagingService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
			obj -> new MessagingService((io.vertx.realworld.service.MessagingService) obj),
			MessagingService::getDelegate);

	private final io.vertx.realworld.service.MessagingService delegate;

	public MessagingService(io.vertx.realworld.service.MessagingService delegate) {
		this.delegate = delegate;
	}

	public io.vertx.realworld.service.MessagingService getDelegate() {
		return delegate;
	}

	public void sendMail(final MailMessage mailMessage, Handler<AsyncResult<MailResult>> resultHandler) {
		delegate.sendMail(mailMessage, new Handler<AsyncResult<MailResult>>() {
			@Override
			public void handle(AsyncResult<MailResult> ar) {
				if (ar.succeeded()) {
					resultHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
				} else {
					resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
				}
			}
		});
	}

	public Single<MailResult> rxSendMail(final MailMessage mailMessage) {
		return new io.vertx.reactivex.core.impl.AsyncResultSingle<MailResult>(handler -> {
			sendMail(mailMessage, handler);
		});
	}

	public static MessagingService newInstance(io.vertx.realworld.service.MessagingService arg) {
		return arg != null ? new MessagingService(arg) : null;
	}

	public static MessagingService create(MailClient mailClient,
			Handler<AsyncResult<io.vertx.realworld.service.MessagingService>> readyHandler) {
		MessagingService ret = MessagingService
				.newInstance(io.vertx.realworld.service.MessagingService.create(mailClient, readyHandler));
		return ret;
	}

	public static MessagingService createProxy(Vertx vertx, String address) {
		MessagingService ret = MessagingService
				.newInstance(io.vertx.realworld.service.MessagingService.createProxy(vertx.getDelegate(), address));
		return ret;
	}
}