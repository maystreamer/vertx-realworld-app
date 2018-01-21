package io.vertx.realworld.handler;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.realworld.annotation.RequestMapping;
import io.vertx.realworld.constant.Constants;
import io.vertx.realworld.constant.MediaType;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.model.ApiResponse;
import io.vertx.realworld.util.JsonUtil;
import io.vertx.realworld.util.ResponseUtil;

@RequestMapping(path = "/ping")
public class PingHandler extends BaseHandler {

	public PingHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	@RequestMapping(method = HttpMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void handle(RoutingContext ctx) {
		ApiResponse<JsonObject> response;
		JsonObject result;
		try {
			result = new JsonObject().put("status", "OK");
			response = ResponseUtil.toApiResponse(200, Constants.RESPONSE_SUCCESS, result, false);
			ctx.response().end(JsonUtil.encode(response));
		} catch (Exception ex) {
			ctx.fail(new RestException(Constants.RESPONSE_FAILED, ex, 500));
		}
	}
}