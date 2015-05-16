package com.twistlet.falcon.model.service;

public interface NotificationService {

	void send(String fullName, String ic, String hp, String mail, String password, String patronOf);
}
