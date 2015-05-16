package com.twistlet.falcon.model.service;

public interface ResetPasswordService {
	
	boolean validateUserIdentity(String nric);
	
	boolean validateUrl(String random);
	
	boolean resetPassword(String nric, String random, String password);

}
