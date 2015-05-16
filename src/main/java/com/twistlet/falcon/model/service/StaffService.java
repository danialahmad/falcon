package com.twistlet.falcon.model.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;

public interface StaffService {

	List<FalconUser> listPatients(String partialName);

	List<FalconStaff> listStaffByAdmin(String admin);

	Set<FalconStaff> listAvailableStaff(FalconUser admin, Date start, Date end);
	
	Set<FalconStaff> listAvailableStaff(FalconUser admin, Date start, Date end, Integer appointmentId);

	List<FalconStaff> listStaffByAdminNameLike(FalconUser admin, String name);

	List<FalconStaff> listStaffByAdminNricLike(FalconUser admin, String nric);

	List<FalconStaff> listStaffByAdminEmailLike(FalconUser admin, String nric);

	List<FalconStaff> listStaffByAdminMobileLike(FalconUser admin, String mobile);

	List<FalconStaff> listStaffByAdminStaffLike(FalconUser admin, FalconStaff staff);

	FalconUser getUser(String username);

	void saveStaff(FalconStaff staff);

	void deleteStaff(FalconStaff staff);

	List<FalconUser> listPatronByAdminId(String adminId);

	boolean sendEmail(String from, String name, String address, String message);

	boolean sendSms(String from, String phone, String message);
}
