package com.twistlet.falcon.model.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.controller.bean.User;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;

public interface PatronService {

	List<User> listRegisteredPatrons(FalconUser admin);

	Set<User> listAvailablePatrons(FalconUser admin, Date start, Date end);
	
	Set<User> listAvailablePatrons(FalconUser admin, Date start, Date end, Integer appointmentId);

	void savePatron(FalconPatron patron);
	
	void saveNewPatron(FalconPatron patron);

	FalconPatron findPatron(String patron, String admin);
	
	FalconPatron findPatron(String patron, boolean isAdmin);

	List<FalconPatron> listPatronByAdminNameLike(FalconUser admin, String name);

	List<FalconPatron> listPatronByAdminNricLike(FalconUser admin, String nric);

	List<FalconPatron> listPatronByAdminEmailLike(FalconUser admin, String nric);

	List<FalconPatron> listPatronByAdminMobileLike(FalconUser admin, String mobile);

	List<FalconPatron> listPatronByAdminPatronLike(FalconUser admin, FalconUser patron);
	
	List<FalconPatron> listAllPatronsAdmin(FalconUser patron);
	
	List<FalconUser> listUserByCriteria(FalconUser user);
	
	List<FalconUser> listUserByNric(FalconUser user);
	
	List<FalconUser> listUserByPhone(FalconUser user);

	void deletePatron(FalconPatron patron);

}
