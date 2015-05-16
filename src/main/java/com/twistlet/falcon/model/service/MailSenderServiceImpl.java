package com.twistlet.falcon.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	private final String senderName;
	private final String senderAddress;
	private final String subject;
	private final JavaMailSender javaMailSender;
	private final DatabaseLoggingService databaseLoggingService;

	@Autowired
	public MailSenderServiceImpl(@Value("${smtp.notifications.name}") final String senderName,
			@Value("${smtp.notifications.address}") final String senderAddress,
			@Value("${smtp.notifications.subject}") final String subject,
			@Qualifier("notificationsMailSender") final JavaMailSender javaMailSender,
			final DatabaseLoggingService databaseLoggingService) {
		this.senderName = senderName;
		this.senderAddress = senderAddress;
		this.subject = subject;
		this.javaMailSender = javaMailSender;
		this.databaseLoggingService = databaseLoggingService;
	}

	@Override
	public void send(final String fromUserId, final String sendTo, final String message) {
		send(fromUserId, sendTo, message, this.subject);
	}

	@Override
	public void send(final String fromUserId, final String sendTo, final String message, final String subject) {
		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		final String from = "\"" + senderName + "\" <" + senderAddress + ">";
		mailMessage.setFrom(from);
		mailMessage.setTo(sendTo);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		String errorMessage = null;
		try {
			javaMailSender.send(mailMessage);
		} catch (final MailException e) {
			errorMessage = e.toString();
			throw e;
		} finally {
			databaseLoggingService.logEmailSent(sendTo, message, errorMessage);
		}
	}

	@Override
	public void sendHtml(final String fromUserId, final String sendTo, final String message, final String subject) {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		final String from = "\"" + senderName + "\" <" + senderAddress + ">";
		String errorMessage = null;
		try {
			helper.setFrom(from);
			helper.setTo(sendTo);
			helper.setSubject(subject);
			helper.setText(message, true);
			javaMailSender.send(mimeMessage);
		} catch (final MailException e) {
			errorMessage = e.toString();
		} catch (final MessagingException e) {
			errorMessage = e.toString();
		} finally {
			databaseLoggingService.logEmailSent(sendTo, message, errorMessage);
		}
	}

}
