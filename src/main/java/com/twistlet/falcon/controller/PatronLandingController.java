package com.twistlet.falcon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.falcon.controller.bean.SearchAppointment;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.service.AppointmentService;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.UserAdminService;

@Controller
@RequestMapping("/patron")
public class PatronLandingController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final AppointmentService appointmentService;
	
	private final PatronService patronService;
	
	private final UserAdminService userAdminService;
	
	@Autowired
	public PatronLandingController(AppointmentService appointmentService,
			PatronService patronService, UserAdminService userAdminService) {
		this.appointmentService = appointmentService;
		this.patronService = patronService;
		this.userAdminService = userAdminService;
	}


	@RequestMapping(value = "/create-patron", method = RequestMethod.POST)
	public ModelAndView createPatron(
			@ModelAttribute("patron") FalconPatron patron) {
		FalconUser user = userAdminService.getFalconUser(patron.getFalconUserByPatron().getUsername());
		patron.setFalconUserByPatron(user);
		patronService.savePatron(patron);
		return new ModelAndView("redirect:patron_landing");
	}
	
	
	@RequestMapping("/patron_landing")
	public ModelAndView listAllPatient() {
		ModelAndView mav = new ModelAndView("patron/patron_landing");
		FalconAppointment newAppointment = new FalconAppointment();
		newAppointment.setAppointmentDate(new Date());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUser = auth.getName();
		FalconPatron patron = new FalconPatron();
		FalconUser theUser = new FalconUser();
		theUser.setUsername(loggedInUser);
		patron.setFalconUserByPatron(theUser);
		SearchAppointment search = new SearchAppointment();
		List<FalconAppointment> falconAppointments = appointmentService.listMonthlySchedulePatron(new Date(), loggedInUser);
		Set<FalconAppointmentPatron> appointmentPatrons = null;
		for(FalconAppointment appointment : falconAppointments){
			appointmentPatrons = new HashSet<>();
			for(FalconAppointmentPatron appointmentPatron : appointment.getFalconAppointmentPatrons()){
				if(StringUtils.equals(appointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername(), loggedInUser)){
					appointmentPatrons.add(appointmentPatron);
				}
			}
			appointment.setFalconAppointmentPatrons(appointmentPatrons);
		}
		mav.addObject("search", search);
		mav.addObject("patron", patron);
		mav.addObject("appointments", falconAppointments);
		mav.addObject("appointment", newAppointment);
		return mav;
	}

	@RequestMapping(value = "/create-appointment", method = RequestMethod.POST)
	public ModelAndView createNewAppointment(
			@ModelAttribute("appointment") FalconAppointment appointment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUser = auth.getName();
		FalconPatron falconPatron = patronService.findPatron(loggedInUser, false);
		FalconAppointmentPatron falconAppointmentPatron = new FalconAppointmentPatron();
		Set<FalconAppointmentPatron> patrons = new HashSet<>();
		falconAppointmentPatron.setFalconPatron(falconPatron);
		patrons.add(falconAppointmentPatron);
		appointment.setFalconAppointmentPatrons(patrons);
		appointmentService.createAppointment(appointment);
		return new ModelAndView("redirect:patron_landing");
	}
	
	@RequestMapping("/delete_appointment/{id}")
	public ModelAndView deleteAppointment(@PathVariable Integer id){
		appointmentService.deleteAppointmentPatron(id);
		return new ModelAndView("redirect:/patron/patron_landing");
	}
	
	@RequestMapping(value="/search-appointments", method =  RequestMethod.GET)
	public ModelAndView searchAppointmentsGet(@ModelAttribute("search") SearchAppointment searchAppointment){
		return listAllPatient();
	}
	
	@RequestMapping(value="/search-appointments", method =  RequestMethod.POST)
	public ModelAndView searchAppointments(@ModelAttribute("search") SearchAppointment searchAppointment){
		ModelAndView mav = new ModelAndView("patron/patron_landing");
		final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
		Date searchDate = null;
		if(StringUtils.isNotBlank(searchAppointment.getSearchDate())){
			String date = searchAppointment.getSearchDate();
			String time = "00:00 am";
			if(StringUtils.isNotBlank(searchAppointment.getSearchTime())){
				time = searchAppointment.getSearchTime();
			}
			try {
				searchDate = sdf.parse(date + " " + time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String patronId = auth.getName();
		List<FalconAppointment> falconAppointments = appointmentService.findAppointmentsByParameter(searchAppointment.getStaffId(), patronId, searchAppointment.getServiceId(), searchAppointment.getLocationId(), searchDate);
		mav.addObject("appointments", falconAppointments);
		/**
		 * 
		 */
		FalconAppointment newAppointment = new FalconAppointment();
		newAppointment.setAppointmentDate(new Date());
		String loggedInUser = auth.getName();
		FalconPatron patron = new FalconPatron();
		FalconUser theUser = new FalconUser();
		theUser.setUsername(loggedInUser);
		patron.setFalconUserByPatron(theUser);
		SearchAppointment search = searchAppointment;
		mav.addObject("search", search);
		mav.addObject("patron", patron);
		mav.addObject("appointment", newAppointment);
		return mav; 
	}
	
	@InitBinder
	public void initDateBinder(final WebDataBinder dataBinder, final Locale locale) {
		final String dateformat = "dd-MM-yyyy hh:mm aaa";
		final SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		sdf.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}
	
	@InitBinder
	public void initFalconPatronsBinder(final WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Set.class, "falconAppointmentPatrons", new CustomCollectionEditor(Set.class){
			 @Override
	         protected Object convertElement(Object element){
				 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			     String adminUsername = auth.getName();
				 String username = StringUtils.EMPTY;
				 if(element instanceof String && !((String)element).equals("")){
					 username = (String) element;
				 }
				 else{
					 return null;
				 }
				 FalconAppointmentPatron falconAppointmentPatron = new FalconAppointmentPatron();
				 FalconPatron falconPatron = patronService.findPatron(username, adminUsername);
				 falconAppointmentPatron.setFalconPatron(falconPatron);
				 return falconAppointmentPatron;
			 }
		});
	}

}
