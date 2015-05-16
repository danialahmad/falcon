package com.twistlet.falcon.controller;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.UserAdminService;

@Controller
public class EditPatronProfileController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final PatronService patronService;
	
	private final UserAdminService userAdminService;
	
	
	@Autowired
	public EditPatronProfileController(PatronService patronService,
			UserAdminService userAdminService) {
		this.patronService = patronService;
		this.userAdminService = userAdminService;
	}

	@RequestMapping(value = "/patron/editprofile")
	public ModelAndView displayPatronsProfile(){
		ModelAndView mav = displayPatronProfile(false);
		mav.setViewName("patron/editprofile");
		return mav;
	}

	@RequestMapping(value = "/admin/editprofile")
	public ModelAndView displayAdminsProfile(){
		ModelAndView mav = displayPatronProfile(true);
		mav.setViewName("admin/editprofile");
		return mav;
	}
	
	public ModelAndView displayPatronProfile(boolean isAdmin){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUser = auth.getName();
		FalconUser thisUser = new FalconUser();
		thisUser.setUsername(loggedInUser);
		ModelAndView mav = new ModelAndView();
		int totalAdmin = 0;
		if(isAdmin){
			FalconUser falconUser = userAdminService.getFalconUser(loggedInUser);
			Set<FalconPatron> registeredPatrons = falconUser.getFalconPatronsForAdmin();
			if(CollectionUtils.isNotEmpty(registeredPatrons)){
				totalAdmin = falconUser.getFalconPatronsForAdmin().size();
			}
			mav.addObject("totalAdmin", totalAdmin);
			mav.addObject("patrons", registeredPatrons);
			mav.addObject("thisAdmin", falconUser);
		}else{
			List<FalconPatron> registeredAdmins = patronService.listAllPatronsAdmin(thisUser);
			FalconPatron falconPatron = patronService.findPatron(loggedInUser, isAdmin);
			if(CollectionUtils.isNotEmpty(registeredAdmins)){
				totalAdmin = registeredAdmins.size();
			}
			mav.addObject("patron", falconPatron);
			mav.addObject("admins", registeredAdmins);
			mav.addObject("totalAdmin", totalAdmin);
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/patron/update-patron", method = RequestMethod.POST)
	public ModelAndView updatePatron(@ModelAttribute("patron") FalconPatron patron) {
		patronService.savePatron(patron);
		return new ModelAndView("redirect:patron_landing");
	}
	
	@RequestMapping(value = "/admin/update-admin", method = RequestMethod.POST)
	public ModelAndView updateAdmin(@ModelAttribute("thisAdmin") FalconUser admin) {
		userAdminService.updateAdmin(admin);
		return new ModelAndView("redirect:admin_landing");
	}
	
}
