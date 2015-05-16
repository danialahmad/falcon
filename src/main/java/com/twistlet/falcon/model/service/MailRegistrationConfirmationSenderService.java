package com.twistlet.falcon.model.service;

public interface MailRegistrationConfirmationSenderService {

	void send(String fullName, String ic, String hp, String mail, String patronOf);
}
