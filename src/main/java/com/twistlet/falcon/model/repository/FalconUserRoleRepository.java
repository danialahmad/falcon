package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconUserRole;

public interface FalconUserRoleRepository extends
		JpaRepository<FalconUserRole, Integer> {

	List<FalconUserRole> findByFalconUserUsername(String username);

}
