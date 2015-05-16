package com.twistlet.falcon.model.service;

public interface MailSenderService extends MessageSenderService {
	void send(String fromUserId, String sendTo, String message, String subject);

	void sendHtml(String fromUserId, String sendTo, String message, String subject);
}
