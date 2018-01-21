package io.vertx.realworld.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;
import io.vertx.reactivex.ext.mail.MailClient;
import io.vertx.realworld.exception.EmailException;

public class MessagingServiceImpl implements MessagingService {
	private MailClient mailClient;

	public MessagingServiceImpl(MailClient mailClient, Handler<AsyncResult<MessagingService>> readyHandler) {
		this.mailClient = mailClient;
		readyHandler.handle(Future.succeededFuture(this));
	}

	@Override
	public MessagingService sendMail(MailMessage mailMessage, Handler<AsyncResult<MailResult>> resultHandler) {
		try {
			this.mailClient.rxSendMail(mailMessage).subscribe(resp -> {
				if (null != resp) {
					resultHandler.handle(Future.succeededFuture(resp));
				} else {
					resultHandler.handle(Future.failedFuture(new EmailException("Not able to send any emails.")));
				}
			}, cause -> {
				resultHandler.handle(Future.failedFuture(cause));
			});
		} catch (Exception ex) {
			resultHandler.handle(Future.failedFuture(ex));
		}
		return this;
	}

	@Override
	public void close() {
		mailClient.close();
	}
}