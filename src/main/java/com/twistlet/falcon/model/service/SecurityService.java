package com.twistlet.falcon.model.service;

public interface SecurityService {
	String getCurrentUserId();

	boolean isCurrentUserInRole(String role);
}
