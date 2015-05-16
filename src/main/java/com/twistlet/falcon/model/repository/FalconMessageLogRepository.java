package com.twistlet.falcon.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconMessageLog;

public interface FalconMessageLogRepository extends
		JpaRepository<FalconMessageLog, Integer> {

}
