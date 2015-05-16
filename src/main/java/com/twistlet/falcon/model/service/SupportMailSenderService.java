package com.twistlet.falcon.model.service;

public interface SupportMailSenderService {

	void send(String messageType, String cc, String content);
}
