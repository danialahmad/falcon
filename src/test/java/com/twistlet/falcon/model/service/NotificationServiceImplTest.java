package com.twistlet.falcon.model.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

	NotificationService unit;
	SimpleMailMessage mailMessage;
	@Mock
	JavaMailSender javaMailSender;

	@Before
	public void init() throws IOException {

		mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("Butter-Bun Sign-Up <signup@butter-bun.com>");
		mailMessage.setSubject("Welcome to Butter-Bun");
		final Charset encoding = Charset.forName("US-ASCII");
		final File contentFile = new ClassPathResource("com/twistlet/falcon/mail/welcome.txt").getFile();
		final String contentTemplate = FileUtils.readFileToString(contentFile, encoding);
		unit = new NotificationServiceImpl(mailMessage, javaMailSender, contentTemplate);
	}

	@Test
	public void testSend() throws IOException, MessagingException {
		final MimeMessage mimeMessage = new MimeMessage((Session) null);
		final ArgumentCaptor<MimeMessage> argument = ArgumentCaptor.forClass(MimeMessage.class);
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		unit.send("Name", "100", "200", "me@me.com", "abc123", "Shankar's Dental Clinic");
		verify(javaMailSender).send(argument.capture());
		final MimeMessage mailMessage = argument.getValue();
		final String content = mailMessage.getContent().toString();
		assertTrue(!content.contains("{0}"));

	}

	public static void main(final String[] args) throws IOException {
		new NotificationServiceImplTest().execute();
	}

	public void execute() throws IOException {
		mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("Butter-Bun Sign-Up <signup@butter-bun.com>");
		mailMessage.setSubject("Welcome to Butter-Bun");
		final Charset encoding = Charset.forName("US-ASCII");
		final File contentFile = new ClassPathResource("com/twistlet/falcon/mail/welcome.txt").getFile();
		final String contentTemplate = FileUtils.readFileToString(contentFile, encoding);
		final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("localhost");
		javaMailSender.setPort(13306);
		javaMailSender.setUsername("mailer@tabuk-tech.com");
		javaMailSender.setPassword("8ym4X6CgOWx5hsQM7NCY");
		final Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(properties);
		final NotificationServiceImpl service = new NotificationServiceImpl(mailMessage, javaMailSender, contentTemplate);
		service.send("Titi Wangsa", "100", "200", "tt@twistlet.com", "password", "Orga");

	}
}
