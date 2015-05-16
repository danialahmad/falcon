package com.twistlet.falcon.model.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.repository.FalconUserRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService {

	private final FalconUserRepository falconUserRepository;

	
	@Autowired
	public UserAdminServiceImpl(FalconUserRepository falconUserRepository) {
		this.falconUserRepository = falconUserRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public FalconUser getFalconUser(String username) {
		FalconUser admin = falconUserRepository.findOne(username);
		Set<FalconPatron> patronForPatron = admin.getFalconPatronsForPatron();
		for(FalconPatron patron : patronForPatron){
			patron.getFalconUserByPatron().getUsername();
			patron.getFalconUserByPatron().getName();
			patron.getFalconUserByAdmin().getUsername();
			patron.getFalconUserByAdmin().getName();
		}
		Set<FalconPatron>  patronForAdmin = admin.getFalconPatronsForAdmin();
		for(FalconPatron patron : patronForAdmin){
			patron.getFalconUserByPatron().getUsername();
			patron.getFalconUserByPatron().getName();
			patron.getFalconUserByAdmin().getUsername();
			patron.getFalconUserByAdmin().getName();
		}
		return admin;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAdmin(FalconUser admin) {
		falconUserRepository.save(admin);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FalconUser> findAllAdmins() {
		List<FalconUser> organizations = falconUserRepository.findByRolename("ROLE_ADMIN");
		return organizations;
	}

}
