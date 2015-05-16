package com.twistlet.falcon.model.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconUser;

public interface LocationService {
	
	List<FalconLocation> listAdminLocations(FalconUser admin);
	
	List<FalconLocation> listAdminLocationLike(FalconLocation location);
	
	Set<FalconLocation> listAvailableLocations(FalconUser admin, Date start, Date end);
	
	Set<FalconLocation> listAvailableLocations(FalconUser admin, Date start, Date end, Integer appointmentId);
	
	void saveLocation(FalconLocation falcLocation);
	
	void deleteLocation(FalconLocation falconLocation);

}
