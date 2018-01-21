package io.vertx.realworld.messaging;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.realworld.util.JsonUtil;

public class EmailMessageCodec implements MessageCodec<EmailMessage, EmailMessage> {

	@Override
	public void encodeToWire(Buffer buffer, EmailMessage message) {
		String _message = JsonUtil.encode(message);
		int length = _message.getBytes().length;
		buffer.appendInt(length);
		buffer.appendString(_message);
	}

	@Override
	public EmailMessage decodeFromWire(int pos, Buffer buffer) {
		int _pos = pos;
		int length = buffer.getInt(_pos);
		String data = buffer.getString(_pos += 4, _pos += length);
		EmailMessage message = JsonUtil.decode(data, EmailMessage.class);
		return message;
	}

	@Override
	public EmailMessage transform(EmailMessage message) {
		return message;
	}

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	@Override
	public byte systemCodecID() {
		return -1;
	}
}