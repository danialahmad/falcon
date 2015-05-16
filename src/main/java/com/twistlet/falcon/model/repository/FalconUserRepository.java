package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconUserRepository extends
		JpaRepository<FalconUser, String>, FalconUserRepositoryCustom {

	List<FalconUser> findByNric(String nric);
	
	List<FalconUser> findByPhone(String phone);
}
