package com.twistlet.falcon.model.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconPasswordChange;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.repository.FalconPasswordChangeRepository;
import com.twistlet.falcon.model.repository.FalconUserRepository;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final FalconUserRepository falconUserRepository;
	private final FalconPasswordChangeRepository passwordChangeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender javaMailSender;
	private final String message;
	
	@Autowired
	public ResetPasswordServiceImpl(FalconUserRepository falconUserRepository,
			FalconPasswordChangeRepository passwordChangeRepository,
			PasswordEncoder passwordEncoder,
			@Value("${mail.content.reset}") final String messageLocation,
			@Qualifier("resetMailSender") final JavaMailSender javaMailSender) {
		this.falconUserRepository = falconUserRepository;
		this.passwordChangeRepository = passwordChangeRepository;
		this.passwordEncoder = passwordEncoder;
		try {
			message = StringUtils.join(FileUtils.readLines(new ClassPathResource(messageLocation).getFile()), "\n");
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		this.javaMailSender = javaMailSender;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean validateUserIdentity(String nric) {
		boolean success = false;
		List<FalconUser> users = falconUserRepository.findByNric(nric);
		try {
			FalconUser user = DataAccessUtils.singleResult(users);
			if(user != null){
				
					FalconPasswordChange passwordChangeRequest = new FalconPasswordChange();
					final Date now = new Date();
					final String randomNum = new Integer(SecureRandom.getInstance("SHA1PRNG").nextInt()).toString();
					final MessageDigest sha = MessageDigest.getInstance("SHA-256");
					final byte[] result =  sha.digest(randomNum.getBytes());
					final String random = Base64.encodeBase64URLSafeString(result);
					passwordChangeRequest.setNric(user.getNric());
					passwordChangeRequest.setRandomString(random);
					passwordChangeRequest.setDateRequest(now);
					passwordChangeRequest.setExecuted(false);
					passwordChangeRepository.save(passwordChangeRequest);
					sendToUser(user, random);
					success = true;	
			}
		} catch (NoSuchAlgorithmException e) {
			success = false;
			e.printStackTrace();
		} catch (IncorrectResultSizeDataAccessException e){
			success = false;
			logger.info("{}, trying to reset password with an invalid nric.", nric);
		}
		return success;
	}

	@Transactional
	private void sendToUser(final FalconUser user, final String random){
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		final String sendTo = "\"" + user.getName() + "\" <" + user.getEmail() + ">";
		try {
			helper.setFrom("signup@butter-bun.com");
			helper.setTo(sendTo);
			helper.setSubject("Your Butter-Bun password has been reset. Please use the link to key in your new password");
			final Object[] arguments = { user.getName(), random };
			final String text = MessageFormat.format(message, arguments);
			helper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (final MailException e) {
			logger.error(e.toString(), e);
		} catch (final MessagingException e) {
			logger.error(e.toString(), e);
		}
	}

	@Override
	public boolean validateUrl(String random) {
		boolean isValidRandom = false;
		List<FalconPasswordChange> passwordChangeRequests = passwordChangeRepository.findByRandomStringAndExecuted(random, false);
		try {
			FalconPasswordChange falconPasswordChange = DataAccessUtils.singleResult(passwordChangeRequests);
			if(falconPasswordChange != null){
				isValidRandom = true;
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.info("{}, trying to reset reset password with an invalid random.", random);
		}
		return isValidRandom;
	}

	@Override
	@Transactional
	public boolean resetPassword(String nric, String random, String password) {
		boolean success = false;
		List<FalconUser> users = falconUserRepository.findByNric(nric);
		FalconUser user = DataAccessUtils.singleResult(users);
		List<FalconPasswordChange> requests = passwordChangeRepository.findByRandomStringAndExecuted(random, false);
		FalconPasswordChange falconPasswordChange = DataAccessUtils.singleResult(requests);
		if(StringUtils.equalsIgnoreCase(nric, falconPasswordChange.getNric())){
			final String encodedPassword = passwordEncoder.encodePassword(password, user.getUsername());
			user.setPassword(encodedPassword);
			falconUserRepository.save(user);
			falconPasswordChange.setExecuted(true);
			passwordChangeRepository.save(falconPasswordChange);
			success = true;
		}
		return success;
	}
}
