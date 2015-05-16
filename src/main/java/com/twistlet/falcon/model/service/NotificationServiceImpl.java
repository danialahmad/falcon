package com.twistlet.falcon.model.service;

import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class NotificationServiceImpl implements NotificationService {

	private final SimpleMailMessage mailMessage;
	private final JavaMailSender javaMailSender;
	private final String contentTemplate;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public NotificationServiceImpl(final SimpleMailMessage mailMessage, final JavaMailSender javaMailSender,
			final String contentTemplate) {
		this.mailMessage = mailMessage;
		this.javaMailSender = javaMailSender;
		this.contentTemplate = contentTemplate;
	}

	@Override
	public void send(final String fullName, final String ic, final String hp, final String mail, final String password,
			final String patronOf) {
		final Object[] arguments = { fullName, ic, hp, mail, password, patronOf };
		logger.info("send parameters [{}], [{}], [{}], [{}], [{}], [{}]", arguments);
		final String contentOriginal = MessageFormat.format(contentTemplate, arguments);
		final String contentN = contentOriginal.replace("\r\n", "\n");
		final String content = contentN.replace("\n", "\r\n");
		logger.info("content\n{}", content);
		final String to = MessageFormat.format("\"{0}\" <{1}>", new Object[] { fullName, mail });
		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final SimpleToMimeConversionService conversion = new SimpleToMimeConversionService();
			conversion.toMime(mailMessage, mimeMessage);
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setText(content, true);
			javaMailSender.send(mimeMessage);
		} catch (final RuntimeException e) {
			e.printStackTrace();
		} catch (final MessagingException e) {
			e.printStackTrace();
		}
	}
}
