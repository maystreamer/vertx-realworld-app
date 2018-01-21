package io.vertx.realworld.handler;

import static io.vertx.realworld.util.JsonUtil.decode;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Date;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.realworld.annotation.RequestMapping;
import io.vertx.realworld.constant.Constants;
import io.vertx.realworld.constant.MediaType;
import io.vertx.realworld.dto.LoginDTO;
import io.vertx.realworld.dto.UserSessionDTO;
import io.vertx.realworld.exception.BadCredentialsException;
import io.vertx.realworld.exception.InvalidTokenException;
import io.vertx.realworld.exception.RestException;
import io.vertx.realworld.helper.SessionHelper;
import io.vertx.realworld.util.MapperUtil;
import io.vertx.realworld.util.ResponseUtil;

@RequestMapping(path = "/session")
public class SessionHandler extends BaseHandler {
	private static final SessionHelper sessionHelper = SessionHelper.INSTANCE;
	private static Logger logger = LoggerFactory.getLogger(SessionHandler.class);

	public SessionHandler(Vertx vertx) {
		super(vertx);
	}

	@Override
	@RequestMapping(path = "/authenticate", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void handle(final RoutingContext ctx) {
		try {
			Date date = new Date();
			LoginDTO loginDTO = decode(ctx.getBodyAsString(), LoginDTO.class);
			final String password = loginDTO.getPassword();
			final String userName = loginDTO.getEmail();
			if (!isBlank(password.toString()) && !isBlank(userName)) {
				sessionHelper.doAuthenticate(loginDTO)
						.flatMap(appUser -> sessionHelper.createSession(appUser).flatMap(session -> {
							UserSessionDTO userSession = MapperUtil.map(appUser, UserSessionDTO.class);
							userSession.setSessionToken(session.getToken());
							userSession.setCorrelationId(session.get_id().toString());
							return Single.just(userSession);
						})).doOnSuccess(userSession -> {
							String sessionHeader = String.join(" ", Constants.AUTH_SCHEME,
									userSession.getSessionToken());
							ctx.response().putHeader(HttpHeaders.AUTHORIZATION.toString(), sessionHeader);
							ctx.response().putHeader(Constants.CORRELATION_ID, userSession.getCorrelationId());
							ResponseUtil.toResponse(userSession, ctx, date);
						}).doOnError(cause -> {
							ctx.fail(cause);
						}).subscribe();
			} else {
				ctx.fail(new BadCredentialsException());
			}
		} catch (DecodeException dx) {
			logger.error("Error while decoding ", dx);
			ctx.fail(new RestException("Bad Request", HttpResponseStatus.BAD_REQUEST.code()));
		} catch (Exception ex) {
			logger.error("Error while processing authentication", ex);
			ctx.fail(new BadCredentialsException());
		}
	}

	@RequestMapping(path = "/logout", method = HttpMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void logout(final RoutingContext ctx) {
		try {
			Date date = new Date();
			String authHeader = ctx.request().headers().get(HttpHeaders.AUTHORIZATION.toString());
			if (isBlank(authHeader))
				throw new InvalidTokenException();
			sessionHelper.doLogoutSession(authHeader).subscribe(result -> {
				ResponseUtil.toResponse(new JsonObject().put("message", Constants.RESPONSE_SUCCESS), ctx, date);
			}, cause -> {
				ctx.fail(cause);
			});
		} catch (Exception ex) {
			ctx.fail(ex);
		}
	}
}