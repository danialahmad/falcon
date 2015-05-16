package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconPasswordChange;

public interface FalconPasswordChangeRepository extends JpaRepository<FalconPasswordChange, Integer> {
	
	List<FalconPasswordChange> findByRandomStringAndExecuted(String randomString, boolean executed);

}
