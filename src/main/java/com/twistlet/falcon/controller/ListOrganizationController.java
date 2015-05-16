package com.twistlet.falcon.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.UserAdminService;

@Controller
public class ListOrganizationController {
	
	private final PatronService patronService;
	
	private final UserAdminService userAdminService;
	
	@Autowired
	public ListOrganizationController(PatronService patronService,
			UserAdminService userAdminService) {
		this.patronService = patronService;
		this.userAdminService = userAdminService;
	}

	@RequestMapping("/list-organizations")
	@ResponseBody
	public List<FalconUser> listOrganisations(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUser = auth.getName();
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(loggedInUser);
		List<FalconPatron> registeredOrganizations = patronService.listAllPatronsAdmin(falconUser);
		List<FalconUser> registered = new ArrayList<>();
		for(FalconPatron patron : registeredOrganizations){
			registered.add(patron.getFalconUserByAdmin());
		}
		List<FalconUser> admins = userAdminService.findAllAdmins();
		List<FalconUser> toDisplay = new ArrayList<>();
		for(FalconUser admin : admins){
			boolean isRegistered = false;
			for(FalconUser register : registered){
				if(StringUtils.equals(admin.getUsername(), register.getUsername())){
					isRegistered = true;
					break;
				}
			}
			if(!isRegistered){
				FalconUser display = new FalconUser();
				display.setUsername(admin.getUsername());
				display.setName(admin.getName());
				toDisplay.add(display);
			}
		}
		return toDisplay;
	}
	
	@RequestMapping("/registration/list-organizations")
	@ResponseBody
	public List<FalconUser> listRegistrationOrganisations(){
		List<FalconUser> admins = userAdminService.findAllAdmins();
		List<FalconUser> toDisplay = new ArrayList<>();
		for(FalconUser admin : admins){
			FalconUser display = new FalconUser();
			display.setUsername(admin.getUsername());
			display.setName(admin.getName());
			toDisplay.add(display);
		}
		return toDisplay;
	}
	
}
