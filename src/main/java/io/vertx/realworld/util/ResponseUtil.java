package io.vertx.realworld.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.Cookie;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.realworld.constant.Constants;
import io.vertx.realworld.model.ApiResponse;

public final class ResponseUtil {
	protected static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	public static <T> void toResponse(T result, final RoutingContext event, Date dt) {
		final ApiResponse<T> response;
		response = toApiResponse(200, Constants.RESPONSE_SUCCESS, (T) result, false);
		final String resp = JsonUtil.encode(response);
		String strDate = DateUtil.formatDate(new Date());
		logger.info(String.join(" ", "Session", ResponseUtil.getHeaderValue(event, Constants.CORRELATION_ID),
				"Successfully executed method", ResponseUtil.getCookieValue(event, Constants.COOKIE_METHOD), "and took",
				DateUtil.dateDiff(strDate,
						DateUtil.formatDate(
								new Date(Long.parseLong(ResponseUtil.getCookieValue(event, Constants.COOKIE_DATE)))))
						+ " MS \r\n"));
		event.response().setStatusCode(response.getCode()).end(resp);
	}

	public static <T> ApiResponse<T> toApiResponse(final int code, final String message, final T data,
			final boolean hasError) {
		ApiResponse<T> response = new ApiResponse.Builder<T>().setCode(code).setHasError(hasError).setData(data)
				.setMessage(message).build();
		return response;
	}

	public static String getHeaderValue(final RoutingContext event, final String headerName) {
		String headerVal = event.request().getHeader(headerName);
		if (null != headerVal)
			return headerVal;
		return event.request().response().headers().get(headerName);
	}

	public static String getCookieValue(final RoutingContext event, final String name) {
		Cookie cookie = event.getCookie(name);
		if (cookie == null)
			return null;
		return cookie.getValue();
	}

	public static void setCookiesForLogging(final RoutingContext event, final String value, final long timeInMillis) {
		Cookie cookie = Cookie.cookie(Constants.COOKIE_METHOD, value);
		event.addCookie(cookie);
		cookie = Cookie.cookie(Constants.COOKIE_DATE, timeInMillis + "");
		event.addCookie(cookie);
	}

	public static <T> List<T> toList(T object) {
		List<T> list = new ArrayList<T>();
		list.add(object);
		return list;
	}
}