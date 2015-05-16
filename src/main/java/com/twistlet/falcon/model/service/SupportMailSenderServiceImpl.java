package com.twistlet.falcon.model.service;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SupportMailSenderServiceImpl implements SupportMailSenderService {

	private final JavaMailSender javaMailSender;
	private final DatabaseLoggingService databaseLoggingService;
	private final String addressTo;
	private final String supportSubject;

	@Autowired
	public SupportMailSenderServiceImpl(final DatabaseLoggingService databaseLoggingService,
			@Qualifier("supportMailSender") final JavaMailSender javaMailSender,
			@Value("${smtp.support.address}") final String addressTo, @Value("${smtp.support.subject}") final String supportSubject) {
		this.javaMailSender = javaMailSender;
		this.databaseLoggingService = databaseLoggingService;
		this.addressTo = addressTo;
		this.supportSubject = supportSubject;
	}

	@Override
	public void send(final String messageType, final String cc, final String content) {
		final SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(addressTo);
		simpleMessage.setCc(cc);
		simpleMessage.setSubject(supportSubject + " - " + WordUtils.capitalize(messageType));
		simpleMessage.setText("From: " + cc + "\n\n\n" + content);
		try {
			javaMailSender.send(simpleMessage);
		} catch (final MailException e) {
			databaseLoggingService.logEmailSent(addressTo, content, e.toString());
		}
	}
}
