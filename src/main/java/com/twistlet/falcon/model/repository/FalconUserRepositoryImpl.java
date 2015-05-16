package com.twistlet.falcon.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.entity.QFalconPatron;
import com.twistlet.falcon.model.entity.QFalconRole;
import com.twistlet.falcon.model.entity.QFalconUser;
import com.twistlet.falcon.model.entity.QFalconUserRole;

@Repository
public class FalconUserRepositoryImpl implements FalconUserRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<FalconUser> findByRolenameAndNameLike(final String rolename, final String partialName) {
		final JPQLQuery query = new JPAQuery(entityManager);
		final QFalconUser falconUser = QFalconUser.falconUser;
		final QFalconRole falconRole = QFalconRole.falconRole;
		final QFalconUserRole falconUserRole = QFalconUserRole.falconUserRole;
		query.from(falconUser);
		query.join(falconUser.falconUserRoles, falconUserRole);
		query.join(falconUserRole.falconRole, falconRole);
		final BooleanExpression conditionRolename = falconRole.roleName.eq(rolename);
		final BooleanExpression conditionNameLike = falconUser.name.containsIgnoreCase(partialName);
		query.orderBy(falconUser.name.asc());
		query.where(conditionRolename.and(conditionNameLike));
		return query.list(falconUser);
	}

	@Override
	public List<FalconUser> findByCriteria(FalconUser user) {
		final JPQLQuery query = new JPAQuery(entityManager);
		final QFalconUser falconUser = QFalconUser.falconUser;
		query.from(falconUser);
		BooleanExpression conditionPatronLike = null;
		if (StringUtils.isNotBlank(user.getPhone())) {
			conditionPatronLike = falconUser.phone.eq(user.getPhone());
		} else if (StringUtils.isNotBlank(user.getEmail())) {
			conditionPatronLike = falconUser.email.eq(user.getEmail());
		} else if (StringUtils.isNotBlank(user.getNric())) {
			conditionPatronLike = falconUser.nric.eq(user.getNric());
		} else if (StringUtils.isNotBlank(user.getUsername())) {
			conditionPatronLike = falconUser.username.eq(user.getUsername());
		}
		final BooleanExpression conditionValidPatron = falconUser.valid.eq(true);
		query.where(conditionPatronLike.and(conditionValidPatron));
		query.orderBy(falconUser.name.asc());
		return query.list(falconUser);
	}

	@Override
	public List<FalconUser> findByRolename(String roleName) {
		final JPQLQuery query = new JPAQuery(entityManager);
		final QFalconUser falconUser = QFalconUser.falconUser;
		final QFalconRole falconRole = QFalconRole.falconRole;
		final QFalconUserRole falconUserRole = QFalconUserRole.falconUserRole;
		query.from(falconUser);
		query.join(falconUser.falconUserRoles, falconUserRole);
		query.join(falconUserRole.falconRole, falconRole);
		final BooleanExpression conditionRolename = falconRole.roleName.eq(roleName);
		query.orderBy(falconUser.name.asc());
		query.where(conditionRolename);
		return query.list(falconUser);
	}

	@Override
	public List<FalconUser> findByAdmin(String adminLoginId) {
		final JPQLQuery query = new JPAQuery(entityManager);
		final QFalconUser falconUser = QFalconUser.falconUser;
		QFalconPatron falconPatronsForPatron = QFalconPatron.falconPatron;
		query.from(falconUser);
		query.join(falconUser.falconPatronsForPatron, falconPatronsForPatron);
		final BooleanExpression conditionRolename = falconPatronsForPatron.falconUserByAdmin.username.eq(adminLoginId);
		query.where(conditionRolename);
		return query.list(falconUser);
	}
}
