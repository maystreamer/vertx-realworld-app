package io.vertx.realworld.handler;

import static io.vertx.realworld.util.ResponseUtil.toResponse;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Date;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.realworld.annotation.RequestMapping;
import io.vertx.realworld.constant.MediaType;
import io.vertx.realworld.constant.MessageConstants;
import io.vertx.realworld.exception.InvalidLinkException;
import io.vertx.realworld.helper.AppUserHelper;
import io.vertx.realworld.helper.TemporaryLinkHelper;
import io.vertx.realworld.model.TemporaryLink;

@RequestMapping(path = "/link")
public class TemporaryLinkHandler extends BaseHandler {
	private static final TemporaryLinkHelper temporaryLinkHelper = TemporaryLinkHelper.INSTANCE;
	private static final AppUserHelper appUserhelper = AppUserHelper.INSTANCE;

	public TemporaryLinkHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	@RequestMapping(path = "/:linkId", method = HttpMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void handle(final RoutingContext ctx) {
		try {
			final Date date = new Date();
			final String linkId = ctx.request().getParam("linkId");
			if (!isBlank(linkId)) {
				JsonObject query = new TemporaryLink(linkId, true).toJson();
				JsonObject toUpdate = new JsonObject();
				toUpdate.put("$set", new JsonObject().put("isActive", false));
				temporaryLinkHelper.updateTemporaryLink(query, toUpdate).flatMap(jsonObject -> {
					JsonObject userQuery = new JsonObject().put("_id", new JsonObject().put("$oid",
							jsonObject.getJsonObject("appUserId").getValue("$oid").toString()));
					JsonObject toUpdateUser = new JsonObject();
					toUpdateUser.put("$set", new JsonObject().put("isActive", true));
					return appUserhelper.updateAppUser(userQuery, toUpdateUser);
				}).doOnSuccess(jsonObject -> {
					toResponse(new JsonObject().put("message", MessageConstants.TEMPORARY_LINK_SUCCESSFUL), ctx, date);
				}).doOnError(cause -> {
					ctx.fail(cause);
				}).subscribe();
			} else {
				ctx.fail(new InvalidLinkException());
			}
		} catch (Exception ex) {
			ctx.fail(new InvalidLinkException());
		}
	}
}