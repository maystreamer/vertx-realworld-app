package io.vertx.realworld.handler;

import static io.vertx.realworld.constant.Constants.RESPONSE_FAILED;
import static io.vertx.realworld.exception.ExceptionMapper.build;
import static io.vertx.realworld.util.JsonUtil.encode;
import static io.vertx.realworld.util.ResponseUtil.toApiResponse;

import java.util.Date;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.realworld.constant.Constants;
import io.vertx.realworld.constant.MediaType;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.model.ApiError;
import io.vertx.realworld.model.ApiResponse;
import io.vertx.realworld.util.DateUtil;
import io.vertx.realworld.util.ResponseUtil;
import io.vertx.serviceproxy.ServiceException;

public class ErrorHandler extends BaseHandler {
	private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	public ErrorHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	public void handle(RoutingContext event) {
		String strDate = DateUtil.formatDate(new Date());
		final ApiResponse<ApiError<JsonObject>> response;
		final RestException exception = getRestException(event.failure());
		response = toApiResponse(exception.getStatusCode(), RESPONSE_FAILED, build(exception), true);
		event.response().setStatusCode(exception.getStatusCode());
		event.response().putHeader(HttpHeaders.CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON_VALUE);
		logger.error(String.join(" ", "Session", ResponseUtil.getHeaderValue(event, Constants.CORRELATION_ID),
				"Failed to execute method", ResponseUtil.getCookieValue(event, Constants.COOKIE_METHOD), "and took",
				DateUtil.dateDiff(strDate,
						DateUtil.formatDate(
								new Date(Long.parseLong(ResponseUtil.getCookieValue(event, Constants.COOKIE_DATE)))))
						+ " MS \r\n"),
				exception);
		event.response().end(encode(response));
	}

	private RestException getRestException(Throwable t) {
		if (t instanceof RestException) {
			return (RestException) t;
		} else if (t instanceof ServiceException) {
			return new RestException(t.getMessage(), ((ServiceException) t).failureCode());
		} else {
			return new RestException(t.getMessage(), t, 500);
		}
	}

	public static ErrorHandler create(final Vertx vertx) {
		return new ErrorHandler(vertx);
	}
}