package com.twistlet.falcon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.service.AppointmentService;
import com.twistlet.falcon.model.service.PatronService;

@Controller
@RequestMapping("/admin")
public class AdminLandingController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final AppointmentService appointmentService;

	private final PatronService patronService;

	@Autowired
	public AdminLandingController(final AppointmentService appointmentService, final PatronService patronService) {
		this.appointmentService = appointmentService;
		this.patronService = patronService;
	}

	@InitBinder
	public void initDateBinder(final WebDataBinder dataBinder, final Locale locale) {
		final String dateformat = "dd-MM-yyyy hh:mm aaa";
		final SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		sdf.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}

	@InitBinder
	public void initFalconPatronsBinder(final WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Set.class, "falconAppointmentPatrons", new CustomCollectionEditor(Set.class) {
			@Override
			protected Object convertElement(final Object element) {
				final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				final String adminUsername = auth.getName();
				String username = StringUtils.EMPTY;
				if (element instanceof String && !((String) element).equals("")) {
					username = (String) element;
				} else {
					return null;
				}
				final FalconAppointmentPatron falconAppointmentPatron = new FalconAppointmentPatron();
				final FalconPatron falconPatron = patronService.findPatron(username, adminUsername);
				falconAppointmentPatron.setFalconPatron(falconPatron);
				return falconAppointmentPatron;
			}
		});
	}

	@RequestMapping("/admin_landing")
	public ModelAndView listAllPatient() {
		final ModelAndView mav = new ModelAndView("admin/admin_landing");
		final FalconAppointment appointment = new FalconAppointment();
		final Date now = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa - EEEE, MMMMM d, yyyy");
		final String currentTime = sdf.format(now);
		appointment.setAppointmentDate(now);
		mav.addObject("appointment", appointment);
		mav.addObject("currentTime", currentTime);
		return mav;
	}

	@RequestMapping(value = "/create-appointment", method = RequestMethod.POST)
	public ModelAndView createNewAppointment(@ModelAttribute("appointment") final FalconAppointment appointment) {
		appointment.setNotified('N');
		appointmentService.createAppointment(appointment);
		return new ModelAndView("redirect:admin_landing");
	}

}
