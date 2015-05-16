package com.twistlet.falcon.model.service;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailRegistrationConfirmationSenderServiceImpl implements MailRegistrationConfirmationSenderService {

	private final MailSender mailSender;
	private final String message;

	@Autowired
	public MailRegistrationConfirmationSenderServiceImpl(@Qualifier("signupMailSender") final MailSender mailSender,
			@Value("${mail.content.signup}") final String messageLocation) {
		this.mailSender = mailSender;
		try {
			message = StringUtils.join(FileUtils.readLines(new ClassPathResource(messageLocation).getFile()), "\n");
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void send(final String fullName, final String ic, final String hp, final String mail, final String patronOf) {
		final SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("signup@butter-bun.com");
		simpleMessage.setTo(MessageFormat.format("{0} <{1}>", new Object[] { fullName, mail }));
		simpleMessage.setSubject("You have successfully signed up with Butter-Bun");
		final Object[] arguments = { fullName, ic, hp, mail, patronOf };
		final String text = MessageFormat.format(message, arguments);
		simpleMessage.setText(text);
		mailSender.send(simpleMessage);

	}
}
