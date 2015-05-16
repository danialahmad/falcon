package com.twistlet.falcon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.twistlet.falcon.controller.bean.SearchAppointment;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.service.AppointmentService;
import com.twistlet.falcon.model.service.NotificationAppointmentRemovalService;
import com.twistlet.falcon.model.service.NotificationAppointmentRescheduleService;
import com.twistlet.falcon.model.service.PatronService;
import com.twistlet.falcon.model.service.SecurityService;

@Controller
public class AppointmentManagementController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final AppointmentService appointmentService;

	private final PatronService patronService;

	private final NotificationAppointmentRemovalService appointmentRemovalService;

	private final NotificationAppointmentRescheduleService notificationAppointmentRescheduleService;

	private final SecurityService securityService;

	@Autowired
	public AppointmentManagementController(final AppointmentService appointmentService, final PatronService patronService,
			final NotificationAppointmentRemovalService appointmentRemovalService,
			final NotificationAppointmentRescheduleService notificationAppointmentRescheduleService,
			final SecurityService securityService) {
		this.appointmentService = appointmentService;
		this.patronService = patronService;
		this.appointmentRemovalService = appointmentRemovalService;
		this.notificationAppointmentRescheduleService = notificationAppointmentRescheduleService;
		this.securityService = securityService;
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

	@RequestMapping("/admin/manage-appointments")
	public ModelAndView viewAppointments() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String adminUsername = auth.getName();
		final Date now = new Date();
		final List<FalconAppointment> falconAppointments = appointmentService.listMonthlyScheduleAdmin(now, adminUsername);
		final SearchAppointment search = new SearchAppointment();
		final ModelAndView mav = new ModelAndView("admin/manage_appointments");
		final FalconAppointment appointment = new FalconAppointment();
		mav.addObject("appointment", appointment);
		mav.addObject("appointments", falconAppointments);
		mav.addObject("search", search);
		return mav;
	}

	@RequestMapping(value = "/admin/search-appointments", method = RequestMethod.GET)
	public ModelAndView searchAppointmentsGet() {
		return viewAppointments();
	}

	@RequestMapping(value = "/admin/search-appointments", method = RequestMethod.POST)
	public ModelAndView searchAppointments(@ModelAttribute("search") final SearchAppointment searchAppointment) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
		Date searchDate = null;
		if (StringUtils.isNotBlank(searchAppointment.getSearchDate())) {
			final String date = searchAppointment.getSearchDate();
			String time = "00:00 am";
			if (StringUtils.isNotBlank(searchAppointment.getSearchTime())) {
				time = searchAppointment.getSearchTime();
			}
			try {
				searchDate = sdf.parse(date + " " + time);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
		}
		String patronId = null;
		if (StringUtils.isNotBlank(searchAppointment.getPatronId())) {
			patronId = searchAppointment.getPatronId();
		}
		final List<FalconAppointment> falconAppointments = appointmentService.findAppointmentsByParameter(
				searchAppointment.getStaffId(), patronId, searchAppointment.getServiceId(), searchAppointment.getLocationId(),
				searchDate);
		final ModelAndView mav = new ModelAndView("admin/manage_appointments");
		final FalconAppointment appointment = new FalconAppointment();
		mav.addObject("appointment", appointment);
		mav.addObject("appointments", falconAppointments);
		mav.addObject("search", searchAppointment);
		return mav;
	}

	@RequestMapping("/admin/patron_group_list")
	public ModelAndView viewPatrons(@RequestParam("id") final Integer id) {
		final FalconAppointment appointment = appointmentService.findAppointment(id);
		final ModelAndView mav = new ModelAndView("admin/patron_group_list");
		mav.addObject("appointmentPatrons", appointment.getFalconAppointmentPatrons());
		return mav;
	}

	@RequestMapping("/admin/delete_appointment/{id}")
	public ModelAndView deleteAppointment(@PathVariable final Integer id) {
		final String currentUser = securityService.getCurrentUserId();
		try {
			logger.info("Delete Appointment {}", id);
			final List<MimeMessage> mailMessages = appointmentRemovalService.createMessages(id);
			logger.info("MIME message created");
			final Map<String, String> smsMessages = appointmentRemovalService.createSmsMessages(id);
			logger.info("Text message created");
			appointmentService.deleteAppointment(id);
			logger.info("Deleted Appointment {}", id);
			appointmentRemovalService.send(mailMessages);
			logger.info("Mail sent");
			appointmentRemovalService.sendSmsMessages(currentUser, smsMessages);
			logger.info("SMS sent");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/admin/manage-appointments");
	}

	@RequestMapping("/admin/delete_patron_appointment/{id}")
	public ModelAndView deletePatronAppointment(@PathVariable final Integer id) {
		appointmentService.deleteAppointmentPatron(id);
		return new ModelAndView("redirect:/admin/manage-appointments");
	}

	@RequestMapping("/admin/reschedule_appointment/{id}/{date}/{start}/{end}/{location}")
	public ModelAndView rescheduleAppointment(@PathVariable final Integer id, @PathVariable final String date,
			@PathVariable final String start, @PathVariable final String end, @PathVariable final Integer location) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
		final String currentUser = securityService.getCurrentUserId();
		try {
			final Date startDate = sdf.parse(date + " " + start);
			final Date endDate = sdf.parse(date + " " + end);
			logger.info("Reschedule Appointment {}", id);
			appointmentService.rescheduleAppointment(id, startDate, endDate, location);
			final List<MimeMessage> mailMessages = notificationAppointmentRescheduleService.createMessages(id);
			logger.info("Mail message for reschdeule created");
			final Map<String, String> textMessages = notificationAppointmentRescheduleService.createSmsMessages(id);
			logger.info("Text message for reschdeule created");
			notificationAppointmentRescheduleService.send(mailMessages);
			logger.info("Mail message for reschdeule sent");
			notificationAppointmentRescheduleService.sendSmsMessages(currentUser, textMessages);
			logger.info("Text message for reschdeule sent");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/admin/manage-appointments");
	}

	@RequestMapping(value = "/admin/update-appointment", method = RequestMethod.POST)
	public ModelAndView updateAppointmentPatron(@ModelAttribute("appointment") final FalconAppointment appointment) {
		appointmentService.updateAppointmentPatrons(appointment);
		return new ModelAndView("redirect:/admin/manage-appointments");
	}

}
