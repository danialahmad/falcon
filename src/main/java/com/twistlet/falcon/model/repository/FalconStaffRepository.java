package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;

public interface FalconStaffRepository extends JpaRepository<FalconStaff, Integer>, FalconStaffRepositoryCustom  {
	
	List<FalconStaff> findByFalconUserAndValid(FalconUser falconUser, Boolean valid);

}
