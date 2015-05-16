package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconService;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconServiceRepository extends JpaRepository<FalconService, Integer>, FalconServiceRepositoryCustom {

	List<FalconService> findByFalconUser(FalconUser admin);
}
