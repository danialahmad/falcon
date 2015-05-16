package com.twistlet.falcon.model.service;

public interface DatabaseLoggingService {
	void logEmailSent(String address, String message, String errorMessage);

	void logSmsSent(String phone, String message, String errorMessage);
}
