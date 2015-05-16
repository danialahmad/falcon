package com.twistlet.falcon.model.repository;

import java.util.List;

import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconUserRepositoryCustom {

	List<FalconUser> findByRolenameAndNameLike(String rolename, String partialName);

	List<FalconUser> findByCriteria(FalconUser falconUser);

	List<FalconUser> findByRolename(String roleName);

	List<FalconUser> findByAdmin(String adminLoginId);
}
