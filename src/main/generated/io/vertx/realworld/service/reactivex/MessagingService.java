/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.realworld.service.reactivex;

import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;


@io.vertx.lang.reactivex.RxGen(io.vertx.realworld.service.MessagingService.class)
public class MessagingService {

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MessagingService that = (MessagingService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public static final io.vertx.lang.reactivex.TypeArg<MessagingService> __TYPE_ARG = new io.vertx.lang.reactivex.TypeArg<>(
    obj -> new MessagingService((io.vertx.realworld.service.MessagingService) obj),
    MessagingService::getDelegate
  );

  private final io.vertx.realworld.service.MessagingService delegate;
  
  public MessagingService(io.vertx.realworld.service.MessagingService delegate) {
    this.delegate = delegate;
  }

  public io.vertx.realworld.service.MessagingService getDelegate() {
    return delegate;
  }

  public MessagingService sendMail(MailMessage mailMessage, Handler<AsyncResult<MailResult>> resultHandler) { 
    delegate.sendMail(mailMessage, resultHandler);
    return this;
  }

  public Single<MailResult> rxSendMail(MailMessage mailMessage) { 
    return new io.vertx.reactivex.core.impl.AsyncResultSingle<MailResult>(handler -> {
      sendMail(mailMessage, handler);
    });
  }

  public void close() { 
    delegate.close();
  }


  public static  MessagingService newInstance(io.vertx.realworld.service.MessagingService arg) {
    return arg != null ? new MessagingService(arg) : null;
  }
}
