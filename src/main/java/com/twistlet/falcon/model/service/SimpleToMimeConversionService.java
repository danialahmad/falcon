package com.twistlet.falcon.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SimpleToMimeConversionService {

	public MimeMessage toMime(final SimpleMailMessage mailMessage, final MimeMessage mimeMessage) throws MessagingException {
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		if (mailMessage.getFrom() != null) {
			helper.setFrom(mailMessage.getFrom());
		}
		if (mailMessage.getReplyTo() != null) {
			helper.setReplyTo(mailMessage.getReplyTo());
		}
		if (mailMessage.getCc() != null) {
			helper.setCc(mailMessage.getCc());
		}
		if (mailMessage.getBcc() != null) {
			helper.setBcc(mailMessage.getBcc());
		}
		if (mailMessage.getSentDate() != null) {
			helper.setSentDate(mailMessage.getSentDate());
		}
		if (mailMessage.getTo() != null) {
			helper.setTo(mailMessage.getTo());
		}
		if (mailMessage.getSubject() != null) {
			helper.setSubject(mailMessage.getSubject());
		}
		return mimeMessage;
	}
}
