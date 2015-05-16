package com.twistlet.falcon.model.service;

import java.util.List;

import com.twistlet.falcon.model.entity.FalconUser;

public interface UserAdminService {

	FalconUser getFalconUser(String username);
	
	void updateAdmin(FalconUser user);
	
	List<FalconUser> findAllAdmins();
}
