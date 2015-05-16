package com.twistlet.falcon.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconLocationRepositoryCustom {
	
	Set<FalconLocation> findLocationDateRange(FalconUser admin, Date start, Date end);

	List<FalconLocation> findByFalconUserLike(FalconLocation location);
	
	Set<FalconLocation> findLocationDateRange(FalconUser admin, Date start, Date end, Integer appointmentId);
	
}
