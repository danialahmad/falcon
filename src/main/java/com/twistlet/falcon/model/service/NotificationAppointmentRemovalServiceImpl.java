package com.twistlet.falcon.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.twistlet.falcon.model.repository.FalconAppointmentRepository;
import com.twistlet.falcon.model.repository.FalconUserRepository;

public class NotificationAppointmentRemovalServiceImpl extends AbstractNotificationAppointmentService implements
		NotificationAppointmentRemovalService {

	@Autowired
	public NotificationAppointmentRemovalServiceImpl(final FalconAppointmentRepository falconAppointmentRepository,
			final FalconUserRepository falconUserRepository, final String template, final JavaMailSender javaMailSender,
			final SimpleMailMessage simpleMailMessage, final String smsTemplate, final SmsService smsService) {
		super(falconAppointmentRepository, falconUserRepository, template, javaMailSender, simpleMailMessage, smsTemplate,
				smsService, "remove");
	}

}
