package com.twistlet.falcon.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.twistlet.falcon.model.entity.FalconService;
import com.twistlet.falcon.model.entity.QFalconService;
import com.twistlet.falcon.model.entity.QFalconUser;

@Repository
public class FalconServiceRepositoryImpl implements FalconServiceRepositoryCustom {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<FalconService> findByFalconUserLike(FalconService service) {
		final JPQLQuery query = new JPAQuery(entityManager);
		final QFalconService falconService = QFalconService.falconService;
		final QFalconUser falconUser = QFalconUser.falconUser;
		query.from(falconService);
		query.join(falconService.falconUser, falconUser).fetch();
		BooleanExpression conditionValid = falconService.valid.isTrue();
		BooleanExpression conditionAdmin = falconUser.username.eq(service.getFalconUser().getUsername());
		BooleanExpression conditionNameLike = falconService.name.containsIgnoreCase(service.getName());
		query.where(conditionValid.and(conditionAdmin.and(conditionNameLike)));
		return query.list(falconService);
	}

}
