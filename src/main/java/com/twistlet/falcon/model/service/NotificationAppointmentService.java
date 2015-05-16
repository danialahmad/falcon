package com.twistlet.falcon.model.service;

import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

public interface NotificationAppointmentService {
	List<MimeMessage> createMessages(Integer appointmentId);

	Map<String, String> createSmsMessages(Integer appointmentId);

	void send(List<MimeMessage> list);

	void sendSmsMessages(String currentUser, Map<String, String> messages);

}
