package com.twistlet.falcon.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconPatronRepositoryCustom {
	
	Set<FalconPatron> findPatronsDateRange(FalconUser admin, Date start, Date end);
	
	Set<FalconPatron> findPatronsDateRange(FalconUser admin, Date start, Date end, Integer appointmentId);
	
	List<FalconPatron> findByFalconUserNameLike(FalconUser falconUser, String name);
	
	List<FalconPatron> findByFalconUserNricLike(FalconUser falconUser, String nric);
	
	List<FalconPatron> findByFalconUserEmailLike(FalconUser falconUser, String email);

	List<FalconPatron> findByFalconUserHpTelLike(FalconUser falconUser, String hpTel);
	
	List<FalconPatron> findByFalconUserPatronLike(FalconUser falconUser, FalconUser patron);

}
