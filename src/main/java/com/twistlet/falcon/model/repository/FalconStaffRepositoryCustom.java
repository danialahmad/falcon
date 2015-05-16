package com.twistlet.falcon.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconStaffRepositoryCustom {
	
	List<FalconStaff> findByFalconUserNameLike(FalconUser falconUser, String name);
	
	List<FalconStaff> findByFalconUserNricLike(FalconUser falconUser, String nric);
	
	List<FalconStaff> findByFalconUserEmailLike(FalconUser falconUser, String email);

	List<FalconStaff> findByFalconUserHpTelLike(FalconUser falconUser, String hpTel);
	
	List<FalconStaff> findByFalconUserStaffLike(FalconUser falconUser, FalconStaff staff);
	
	Set<FalconStaff> findStaffDateRange(FalconUser admin, Date start, Date end);
	
	Set<FalconStaff> findStaffDateRange(FalconUser admin, Date start, Date end, Integer appointmentId);

}
