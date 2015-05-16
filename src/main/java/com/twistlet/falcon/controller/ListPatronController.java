package com.twistlet.falcon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.falcon.controller.bean.User;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.AppointmentService;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.StaffService;

@Controller
public class ListPatronController {

	private final StaffService staffService;

	private final PatronService patronService;
	
	private final AppointmentService appointmentService;

	@Autowired
	public ListPatronController(StaffService staffService,
			PatronService patronService, AppointmentService appointmentService) {
		this.staffService = staffService;
		this.patronService = patronService;
		this.appointmentService = appointmentService;
	}
	
	@RequestMapping("/check-patron-availability/{appointmentId}/{admin}/{date}/{startTime}/{endTime}")
	@ResponseBody
	public String velidateStaffAvailability(@PathVariable Integer appointmentId, @PathVariable String admin, @PathVariable(value = "date") String date,
			@PathVariable("startTime") String start, @PathVariable("endTime") String end) {
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		final FalconAppointment appointment = appointmentService.findAppointment(appointmentId);
		String status = StringUtils.EMPTY;
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			FalconUser falconUser = new FalconUser();
			falconUser.setUsername(admin);
			Set<User> patients = patronService.listAvailablePatrons(falconUser, startDate, endDate, appointmentId);
			Set<FalconAppointmentPatron> appointmentPatrons = appointment.getFalconAppointmentPatrons();
			for(FalconAppointmentPatron falconAppointmentPatron : appointmentPatrons){
				boolean available = false;
				for(User user : patients){
					if(user.getUsername().equals(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername())){
						available = true;
						break;
					}
				}
				if(!available){
					if(StringUtils.isBlank(status)){
						status = falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName() + " is not available between the time selected";
					}else{
						status = falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName() + ", " + status;
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return status;
	}
	

	@RequestMapping("/list-patient")
	@ResponseBody
	public List<Map<String, String>> listPatient(
			@RequestParam("term") final String partialName) {
		final List<FalconUser> users = staffService.listPatients(partialName);
		final List<Map<String, String>> list = new ArrayList<>();
		for (final FalconUser falconUser : users) {
			final Map<String, String> map = new LinkedHashMap<>();
			map.put("name", falconUser.getName());
			map.put("phone", StringUtils.trimToEmpty(falconUser.getPhone()));
			map.put("mail", StringUtils.trimToEmpty(falconUser.getEmail()));
			list.add(map);
		}
		return list;
	}

	@RequestMapping("/list-patient/{admin}/{date}")
	@ResponseBody
	public List<User> listAllPatrons(@PathVariable("admin") String admin, @PathVariable(value="date") String date) {
		FalconUser falconUser = new FalconUser();
		falconUser.setUsername(admin);
		List<User> patients = patronService.listRegisteredPatrons(falconUser);
		return patients;
	}
	
	@RequestMapping("/list-patient/{admin}/{date}/{startTime}/{endTime}")
	@ResponseBody
	public Set<User> listAvailablePatrons(@PathVariable("admin") String admin,
			@PathVariable(value="date") String date,
			@PathVariable("startTime") String start,
			@PathVariable("endTime") String end) {
		FalconUser falconUser = new FalconUser();
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		Set<User> patients = new HashSet<>();
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			falconUser.setUsername(admin);
			patients = patronService.listAvailablePatrons(falconUser, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return patients;
	}
	
	@RequestMapping("/list-patient/{admin}/{date}/{startTime}/{endTime}/{appointmentId}")
	@ResponseBody
	public Set<User> listAvailablePatronsAdd(@PathVariable("admin") String admin,
			@PathVariable(value="date") String date,
			@PathVariable("startTime") String start,
			@PathVariable("endTime") String end,
			@PathVariable("appointmentId") Integer appointmentId) {
		FalconUser falconUser = new FalconUser();
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		Set<User> patients = new HashSet<>();
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			falconUser.setUsername(admin);
			patients = patronService.listAvailablePatrons(falconUser, startDate, endDate);
			FalconAppointment falconAppointment = appointmentService.findAppointment(appointmentId);
			User user = null;
			for(FalconAppointmentPatron patron : falconAppointment.getFalconAppointmentPatrons()){
				user = new User();
				user.setUsername(patron.getFalconPatron().getFalconUserByPatron().getUsername());
				user.setName(patron.getFalconPatron().getFalconUserByPatron().getName());
				patients.add(user);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return patients;
	}
	
	@RequestMapping("/list-patron-name/{admin}/{date}")
	@ResponseBody
	public List<String> listPatronNames(@PathVariable("admin") String username, @PathVariable(value="date") String date,
			@RequestParam("term") String name) {
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		List<FalconPatron> patrons = patronService.listPatronByAdminNameLike(admin, name);
		List<String> names = new ArrayList<>();
		for (FalconPatron patron : patrons) {
			names.add(patron.getFalconUserByPatron().getName() + " (" + patron.getFalconUserByPatron().getNric() + ")");
		}
		return names;
	}
	
	@RequestMapping("/list-patron-nric/{admin}/{date}")
	@ResponseBody
	public List<String> listPatronNric(@PathVariable("admin") String username, @PathVariable(value="date") String date, 
			@RequestParam("term") String name) {
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		List<FalconPatron> patrons = patronService.listPatronByAdminNricLike(admin, name);
		List<String> names = new ArrayList<>();
		for (FalconPatron patron : patrons) {
			names.add(patron.getFalconUserByPatron().getNric());
		}
		return names;
	}
	
	@RequestMapping("/list-patron-phone/{admin}/{date}")
	@ResponseBody
	public List<String> listPatronPhone(@PathVariable("admin") String username, @PathVariable(value="date") String date,
			@RequestParam("term") String name) {
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		List<FalconPatron> patrons = patronService.listPatronByAdminMobileLike(admin, name);
		List<String> names = new ArrayList<>();
		for (FalconPatron patron : patrons) {
			names.add(patron.getFalconUserByPatron().getPhone());
		}
		return names;
	}
	
	@RequestMapping("/list-patron-email/{admin}/{date}")
	@ResponseBody
	public List<String> listPatronEmail(@PathVariable("admin") String username, @PathVariable(value="date") String date,
			@RequestParam("term") String name) {
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		List<FalconPatron> patrons = patronService.listPatronByAdminEmailLike(admin, name);
		List<String> names = new ArrayList<>();
		for (FalconPatron patron : patrons) {
			names.add(patron.getFalconUserByPatron().getEmail());
		}
		return names;
	}
	
	
	@RequestMapping("/search-patron/{admin}/{date}")
	@ResponseBody
	public FalconUser searchPatron(@PathVariable("admin") String username, @PathVariable(value="date") String date,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "nric", required = false) String nric,
			@RequestParam(value = "email", required = false) String email) {
		FalconUser admin = new FalconUser();
		admin.setUsername(username);
		FalconUser patron = new FalconUser();
		patron.setEmail(email);
		patron.setName(name);
		patron.setPhone(mobile);
		patron.setNric(nric);
		List<FalconPatron> patrons = patronService.listPatronByAdminPatronLike(admin, patron); 
		FalconUser matchingUser = null;
		if (CollectionUtils.size(patrons) == 1) {
			matchingUser = patrons.get(0).getFalconUserByPatron();
			matchingUser.setFalconLocations(null);
			matchingUser.setFalconPatronsForAdmin(null);
			matchingUser.setFalconPatronsForPatron(null);
			matchingUser.setFalconPatronsForPatron(null);
			matchingUser.setFalconServices(null);
			matchingUser.setFalconStaffs(null);
			matchingUser.setFalconUserRoles(null);
		}
		return matchingUser;
	}
	
	@RequestMapping("/validate-patron")
	@ResponseBody
	public String validatePatron(final HttpServletRequest request) {
		final String stringId = request.getParameter("fieldId");
		final String value = request.getParameter("fieldValue");
		final String username = request.getParameter("username-patron");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
		FalconUser user = new FalconUser();
		if("identificationnum-patron".equals(stringId)){
			user.setNric(value);
		}else if("mobilenum-patron".equals(stringId)){
			user.setPhone(value);
		}else if("email-patron".equals(stringId)){
			user.setEmail(value);
		}
		boolean isValid = true;
		List<FalconUser> users = patronService.listUserByCriteria(user);
		if(CollectionUtils.isNotEmpty(users)){
			//check if current id passed is equal to retrieved id. Valid is id is equal
			for(FalconUser theUser : users){
				if(StringUtils.isNotBlank(username)){
					/**
					 * updating user
					 */
					if(username.equals(theUser.getUsername())){
						break;
					}else{
						isValid = false;
						break;
					}
				}else{
					/**
					 * check if different admin is trying to add same user
					 */
					Set<FalconPatron> registeredAdmins = theUser.getFalconPatronsForPatron();
					for(FalconPatron patron : registeredAdmins){
						if(name.equals(patron.getFalconUserByAdmin().getUsername())){
							isValid = false;
							break;
						}
					}
				}
			}
		}
		return "[\""+ stringId + "\", " + isValid +"]";
	}
	
	@RequestMapping("/validatenricphone")
	@ResponseBody
	public List<List<Object>> validateNricAndPhone(final HttpServletRequest request){
		List<Object> item = new ArrayList<>();
		List<List<Object>> response = new ArrayList<>();
		String responseString = "";
		String username = request.getParameter("falconUserByPatron.username");
		String theNric = request.getParameter("falconUserByPatron.nric");
		String thePhone = request.getParameter("falconUserByPatron.phone");;
		FalconUser user = new FalconUser();
		user.setNric(theNric);
		user.setPhone(thePhone);
		String registeredEmail = StringUtils.EMPTY;
		List<FalconUser> users = patronService.listUserByNric(user);
		if(CollectionUtils.isNotEmpty(users)){
			FalconUser registeredUser = users.get(0);
			registeredEmail = registeredUser.getEmail();
			item.add("falconUserByPatron.nric");
			if(!registeredUser.getUsername().equals(username)){
				item.add(false);
			}else{
				item.add(true);
			}
			//item.add("A duplicate nric is already registered by " + registeredEmail);
			//responseString = "['" + item.get(0) + "'," + item.get(1) + ",'" + item.get(2) + "']";
			responseString = "['" + item.get(0) + "'," + item.get(1) + "]";
			response.add(item);
		}else{
			item.add("falconUserByPatron.nric");
			item.add(true);
			//item.add("valid");
			responseString = "['" + item.get(0) + "'," + item.get(1) + "]";
			response.add(item);
		}
		users = patronService.listUserByPhone(user);
		item = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(users)){
			FalconUser registeredUser = users.get(0);
			registeredEmail = registeredUser.getEmail();
			item.add("falconUserByPatron.phone");
			if(!registeredUser.getUsername().equals(username)){
				item.add(false);
				item.add("A duplicate phone is already registered by " + registeredEmail);
			}else{
				item.add(true);
			}
			//responseString = responseString + ",['" + item.get(0) + "'," + item.get(1) + ",'" + item.get(2) + "']";
			responseString = responseString + ",['" + item.get(0) + "'," + item.get(1) + "]";
			response.add(item);
		}else{
			item.add("falconUserByPatron.phone");
			item.add(true);	
			//item.add("valid");
			response.add(item);
			//responseString = responseString + ",['" + item.get(0) + "'," + item.get(1) + ",'" + item.get(2) + "']";
			responseString = responseString + ",['" + item.get(0) + "'," + item.get(1) + "]";
		}
		responseString = "[" + responseString + "]";
		return response;
	}
	
	@RequestMapping("/registration/validate-patron")
	@ResponseBody
	public String validateNewUser(final HttpServletRequest request) {
		final String stringId = request.getParameter("fieldId");
		final String value = request.getParameter("fieldValue");
		FalconUser user = new FalconUser();
		if("identificationnum-patron".equals(stringId)){
			user.setNric(value);
		}else if("mobilenum-patron".equals(stringId)){
			user.setPhone(value);
		}else if("email-patron".equals(stringId)){
			user.setEmail(value);
		}
		boolean isValid = true;
		List<FalconUser> users = patronService.listUserByCriteria(user);
		if(CollectionUtils.isNotEmpty(users)){
			isValid = false;
		}
		return "[\""+ stringId + "\", " + isValid +"]";
	}
}
