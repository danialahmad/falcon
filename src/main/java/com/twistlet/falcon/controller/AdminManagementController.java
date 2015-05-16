package com.twistlet.falcon.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconService;
import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.LocationService;
import com.twistlet.falcon.model.service.NotificationService;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.ServicesTypeService;
import com.twistlet.falcon.model.service.StaffService;
import com.twistlet.falcon.model.service.UserAdminService;

@Controller
public class AdminManagementController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final StaffService staffService;

	private final PatronService patronService;

	private final LocationService locationService;

	private final ServicesTypeService serviceTypeService;

	private final NotificationService notificationWelcomeService;

	private final UserAdminService userAdminService;

	@Autowired
	public AdminManagementController(final StaffService staffService, final PatronService patronService,
			final LocationService locationService, final ServicesTypeService serviceTypeService,
			final UserAdminService userAdminService,
			@Qualifier("notificationWelcomeService") final NotificationService notificationWelcomeService) {
		this.staffService = staffService;
		this.patronService = patronService;
		this.locationService = locationService;
		this.serviceTypeService = serviceTypeService;
		this.userAdminService = userAdminService;
		this.notificationWelcomeService = notificationWelcomeService;
	}

	@RequestMapping("/admin/manageusers")
	public ModelAndView view() {
		final FalconStaff falconStaff = new FalconStaff();
		falconStaff.setValid(true);
		final FalconPatron falconPatron = new FalconPatron();
		final FalconLocation falconLocation = new FalconLocation();
		final FalconService falconService = new FalconService();
		final ModelAndView mav = new ModelAndView("admin/admin_management");
		mav.addObject("staff", falconStaff);
		mav.addObject("patron", falconPatron);
		mav.addObject("venue", falconLocation);
		mav.addObject("service", falconService);
		return mav;
	}

	@RequestMapping(value = "/admin/create-staff", method = RequestMethod.POST)
	public ModelAndView createNewStaff(@ModelAttribute("staff") final FalconStaff staff) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String name = auth.getName();
		final FalconUser admin = new FalconUser();
		admin.setUsername(name);
		staff.setFalconUser(admin);
		staffService.saveStaff(staff);
		return new ModelAndView("redirect:manageusers?f=1");
	}

	@RequestMapping(value = "/admin/create-patron", method = RequestMethod.POST)
	public ModelAndView createNewPatron(@ModelAttribute("patron") final FalconPatron patron) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String name = auth.getName();
		final FalconUser admin = new FalconUser();
		admin.setUsername(name);
		patron.setFalconUserByAdmin(admin);
		patronService.savePatron(patron);
		sendWelcomeNotification(patron);
		return new ModelAndView("redirect:manageusers?f=2");
	}

	private void sendWelcomeNotification(final FalconPatron patron) {
		final FalconUser falconPatron = patron.getFalconUserByPatron();
		final FalconUser falconAdmin = userAdminService.getFalconUser(patron.getFalconUserByAdmin().getUsername());
		final String fullName = falconPatron.getName();
		final String ic = falconPatron.getNric();
		final String hp = falconPatron.getPhone();
		final String mail = falconPatron.getEmail();
		final String password = StringUtils.split(falconPatron.getName(), " ")[0];
		final String patronOf = falconAdmin.getName();
		notificationWelcomeService.send(fullName, ic, hp, mail, password, patronOf);

	}

	@RequestMapping(value = "/admin/create-venue", method = RequestMethod.POST)
	public ModelAndView createNewLocation(@ModelAttribute("venue") final FalconLocation location) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String name = auth.getName();
		final FalconUser admin = new FalconUser();
		admin.setUsername(name);
		location.setFalconUser(admin);
		locationService.saveLocation(location);
		return new ModelAndView("redirect:manageusers?f=3");
	}

	@RequestMapping(value = "/admin/create-service", method = RequestMethod.POST)
	public ModelAndView createNewService(@ModelAttribute("service") final FalconService service) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String name = auth.getName();
		final FalconUser admin = new FalconUser();
		admin.setUsername(name);
		service.setFalconUser(admin);
		serviceTypeService.saveService(service);
		return new ModelAndView("redirect:manageusers?f=4");
	}

	@RequestMapping(value = "/admin/delete-staff/{id}", method = RequestMethod.GET)
	public ModelAndView deleteStaff(@PathVariable("id") final Integer id) {
		final FalconStaff staff = new FalconStaff();
		staff.setId(id);
		staffService.deleteStaff(staff);
		return new ModelAndView("redirect:manageusers?f=1");
	}

	@RequestMapping(value = "/admin/delete-patron/{username}/{admin}", method = RequestMethod.GET)
	public ModelAndView deletePatron(@PathVariable("username") final String username, @PathVariable("admin") final String admin) {
		final FalconPatron patron = new FalconPatron();
		final FalconUser falconAdmin = new FalconUser();
		falconAdmin.setUsername(admin);
		final FalconUser falconPatron = new FalconUser();
		falconPatron.setUsername(username);
		patron.setFalconUserByAdmin(falconAdmin);
		patron.setFalconUserByPatron(falconPatron);
		patronService.deletePatron(patron);
		return new ModelAndView("redirect:manageusers?f=1");
	}

}
