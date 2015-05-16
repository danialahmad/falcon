package com.twistlet.falcon.model.repository;

import java.util.List;

import com.twistlet.falcon.model.entity.FalconService;

public interface FalconServiceRepositoryCustom {
	
	List<FalconService> findByFalconUserLike(FalconService service);

}
