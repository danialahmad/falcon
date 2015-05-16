package com.twistlet.falcon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.falcon.controller.bean.Appointment;
import com.twistlet.falcon.controller.bean.AppointmentPatron;
import com.twistlet.falcon.controller.bean.Patron;
import com.twistlet.falcon.controller.bean.Schedule;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.service.AppointmentService;
import com.twistlet.falcon.model.service.PatronService;

@Controller
public class ListAppointmentController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final AppointmentService appointmentService;
	
	private final PatronService patronService;
	
	@Autowired
	public ListAppointmentController(AppointmentService appointmentService,
			PatronService patronService) {
		this.appointmentService = appointmentService;
		this.patronService = patronService;
	}

	@RequestMapping("/list-appointment/{userId}/{date}")
	@ResponseBody
	public List<Schedule> listAppointments(@PathVariable String userId, @PathVariable String date) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<Schedule> schedules = null;
		try {
			schedules = appointmentService.getMonthlySchedule(sdf.parse(date), userId);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return schedules;
	}
	
	@RequestMapping("/apppointment_fetch/{id}")
	@ResponseBody
	public Appointment getAppointment(@PathVariable Integer id) {
		FalconAppointment falconAppointment = appointmentService.findAppointment(id);
		final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		final SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aaa");
		Appointment appointment = new Appointment();
		appointment.setId(falconAppointment.getId());
		appointment.setStaff(falconAppointment.getFalconStaff().getName());
		appointment.setLocation(falconAppointment.getFalconLocation().getName());
		appointment.setLocationId(falconAppointment.getFalconLocation().getId());
		appointment.setAppointmentDate(sdfDate.format(falconAppointment.getAppointmentDate()));
		appointment.setAppointmentTime(sdfTime.format(falconAppointment.getAppointmentDate()));
		appointment.setAppointmentTimeEnd(sdfTime.format(falconAppointment.getAppointmentDateEnd()));
		appointment.setPatrons(new ArrayList<Patron>());
		Patron patron = null;
		for(FalconAppointmentPatron falconAppointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
			patron = new Patron();
			patron.setKey(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername());
			patron.setName(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName());
			appointment.getPatrons().add(patron);
		}
		return appointment;
	}
	
	@RequestMapping("/apppointment_patron/{id}")
	@ResponseBody
	public Appointment getPatronAppointment(@PathVariable Integer id) {
		FalconAppointment falconAppointment = appointmentService.findAppointment(id);
		final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		final SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aaa");
		Appointment appointment = new Appointment();
		appointment.setId(falconAppointment.getId());
		appointment.setStaff(falconAppointment.getFalconStaff().getName());
		appointment.setLocation(falconAppointment.getFalconLocation().getName());
		appointment.setLocationId(falconAppointment.getFalconLocation().getId());
		appointment.setAppointmentDate(sdfDate.format(falconAppointment.getAppointmentDate()));
		appointment.setAppointmentTime(sdfTime.format(falconAppointment.getAppointmentDate()));
		appointment.setAppointmentTimeEnd(sdfTime.format(falconAppointment.getAppointmentDateEnd()));
		appointment.setPatrons(new ArrayList<Patron>());
		Patron patron = null;
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String thePatron = auth.getName();
		for(FalconAppointmentPatron falconAppointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
			patron = new Patron();
			if(StringUtils.equals(thePatron, falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername())){
				patron.setKey(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername());
				patron.setName(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName());
				patron.setPatronAppointmentId(falconAppointmentPatron.getId());
				appointment.getPatrons().add(patron);
			}
		}
		return appointment;
	}
	
	@RequestMapping("/apppointment_patron_fetch/{id}")
	@ResponseBody
	public AppointmentPatron getAppointmentPatron(@PathVariable Integer id) {
		FalconAppointmentPatron falconAppointmentPatron = appointmentService.findPatron(id);
		AppointmentPatron appointmentPatron = new AppointmentPatron();
		final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aaa");
		appointmentPatron.setId(falconAppointmentPatron.getId());
		appointmentPatron.setAppointmentDate(sdfDate.format(falconAppointmentPatron.getFalconAppointment().getAppointmentDate()));
		appointmentPatron.setAppointmentTime(sdfTime.format(falconAppointmentPatron.getFalconAppointment().getAppointmentDate()));
		appointmentPatron.setLocation(falconAppointmentPatron.getFalconAppointment().getFalconLocation().getName());
		appointmentPatron.setStaff(falconAppointmentPatron.getFalconAppointment().getFalconStaff().getName());
		appointmentPatron.setPatron(falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName());
		return appointmentPatron;
	}

}
