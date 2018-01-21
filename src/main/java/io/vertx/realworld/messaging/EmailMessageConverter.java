package io.vertx.realworld.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.mail.MailAttachment;
import io.vertx.ext.mail.MailMessage;
import io.vertx.reactivex.core.Vertx;
import io.vertx.realworld.util.MapUtil;

public class EmailMessageConverter {

	public static MailMessage toMailMessage(final EmailMessage message, final Vertx vertx) {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setBcc(message.getBcc());
		mailMessage.setCc(message.getCc());
		mailMessage.setFrom(message.getFrom());
		MultiMap headers = buildMultiMap(message.getHeaders());
		if (null != headers) {
			mailMessage.setHeaders(headers);
		}
		mailMessage.setTo(message.getTo());
		if (message.getIsContentHTML()) {
			mailMessage.setHtml(message.getContent());
		} else {
			mailMessage.setText(message.getContent());
		}
		mailMessage.setSubject(message.getSubject());
		buildAttachment(message.getAttachments(), mailMessage, vertx);
		return mailMessage;
	}

	private static void buildAttachment(final List<Attachment> attachments, MailMessage mailMessage,
			final Vertx vertx) {
		List<MailAttachment> inlineMailAttachments = new ArrayList<MailAttachment>();
		List<MailAttachment> mailAttachments = new ArrayList<MailAttachment>();
		if (null != attachments && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				MailAttachment mailAttachment = new MailAttachment();
				mailAttachment.setContentId(attachment.getContentId());
				mailAttachment.setContentType(attachment.getContentType());
				Buffer buffer = vertx.getDelegate().fileSystem().readFileBlocking(attachment.getFilePath());
				mailAttachment.setData(buffer);
				mailAttachment.setDescription(attachment.getDescription());
				mailAttachment.setDisposition(attachment.getDisposition());
				MultiMap headers = buildMultiMap(attachment.getHeaders());
				if (null != headers)
					mailAttachment.setHeaders(headers);
				mailAttachment.setName(attachment.getName());
				if (attachment.getIsInlineAttachment()) {
					inlineMailAttachments.add(mailAttachment);
				} else {
					mailAttachments.add(mailAttachment);
				}
			}
			if (!inlineMailAttachments.isEmpty()) {
				mailMessage.setInlineAttachment(inlineMailAttachments);
			}
			if (!mailAttachments.isEmpty()) {
				mailMessage.setAttachment(mailAttachments);
			}
		}
	}

	private static MultiMap buildMultiMap(Map<String, String> map) {
		MultiMap multiMap = null;
		if (!MapUtil.isNullOrEmpty(map)) {
			multiMap = MultiMap.caseInsensitiveMultiMap();
			for (Entry<String, String> entry : map.entrySet()) {
				multiMap.add(entry.getKey(), entry.getValue());
			}
		}
		return multiMap;
	}
}