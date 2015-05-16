package com.twistlet.falcon.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.twistlet.falcon.model.repository.FalconAppointmentRepository;
import com.twistlet.falcon.model.repository.FalconUserRepository;

public class NotificationAppointmentRescheduleServiceImpl extends AbstractNotificationAppointmentService implements
		NotificationAppointmentRescheduleService {

	@Autowired
	public NotificationAppointmentRescheduleServiceImpl(final FalconAppointmentRepository falconAppointmentRepository,
			final FalconUserRepository falconUserRepository, final String template, final JavaMailSender javaMailSender,
			final SimpleMailMessage simpleMailMessage, final String smsTemplate, final SmsService smsService) {
		super(falconAppointmentRepository, falconUserRepository, template, javaMailSender, simpleMailMessage, smsTemplate,
				smsService, "reschedule");
	}

}
