package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconLocationRepository extends JpaRepository<FalconLocation, Integer>, FalconLocationRepositoryCustom {
	
	List<FalconLocation> findByFalconUser(FalconUser admin);

}
