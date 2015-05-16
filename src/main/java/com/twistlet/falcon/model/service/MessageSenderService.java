package com.twistlet.falcon.model.service;

public interface MessageSenderService {

	void send(String from, String sendTo, String message);
}
