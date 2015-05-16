package com.twistlet.falcon.model.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconMessageLog;
import com.twistlet.falcon.model.repository.FalconMessageLogRepository;
import com.twistlet.falcon.security.service.SecurityContextService;

@Service
public class DatabaseLoggingServiceImpl implements DatabaseLoggingService {

	private final FalconMessageLogRepository falconMessageLogRepository;
	private final SecurityContextService securityContextService;

	@Autowired
	public DatabaseLoggingServiceImpl(final FalconMessageLogRepository falconMessageLogRepository,
			final SecurityContextService securityContextService) {
		this.falconMessageLogRepository = falconMessageLogRepository;
		this.securityContextService = securityContextService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void logEmailSent(final String address, final String message, final String errorMessage) {
		logMessageSent(address, message, errorMessage, "mail");

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void logSmsSent(final String phone, final String message, final String errorMessage) {
		logMessageSent(phone, message, errorMessage, "sms");

	}

	private void logMessageSent(final String address, final String message, final String errorMessage, final String messageType) {
		final Authentication auth = securityContextService.getAuthentication();
		final String name;
		if (auth != null) {
			name = auth.getName();
		} else {
			name = "scheduler";
		}
		final FalconMessageLog item = new FalconMessageLog();
		item.setDestination(address);
		item.setErrorMessage(errorMessage);
		item.setMessage(message);
		item.setMessageType(messageType);
		item.setSentTime(new Date());
		item.setSender(name);
		falconMessageLogRepository.save(item);
	}

}
