package com.twistlet.falcon.model.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.controller.bean.Schedule;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.repository.FalconAppointmentPatronRepository;
import com.twistlet.falcon.model.repository.FalconAppointmentRepository;
import com.twistlet.falcon.model.repository.FalconLocationRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private final FalconAppointmentRepository falconAppointmentRepository;
	
	private final FalconAppointmentPatronRepository falconAppointmentPatronRepository;
	
	private final FalconLocationRepository falconLocationRepository;
	
	@Autowired
	public AppointmentServiceImpl(
			FalconAppointmentRepository falconAppointmentRepository,
			FalconAppointmentPatronRepository falconAppointmentPatronRepository,
			FalconLocationRepository falconLocationRepository) {
		this.falconAppointmentRepository = falconAppointmentRepository;
		this.falconAppointmentPatronRepository = falconAppointmentPatronRepository;
		this.falconLocationRepository = falconLocationRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createAppointment(FalconAppointment falconAppointment) {
		falconAppointmentRepository.save(falconAppointment);
		for(FalconAppointmentPatron falconAppointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
			falconAppointmentPatron.setFalconAppointment(falconAppointment);
			falconAppointmentPatronRepository.save(falconAppointmentPatron);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getMonthlySchedule(Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final Date start = DateUtils.truncate(date, Calendar.MONTH);
		final Date end = DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.MONTH), -1);
		List<FalconAppointment> appointments = falconAppointmentRepository.findByAppointmentDateBetween(start, end);
		HashMap<String, Integer> organiser = new LinkedHashMap<>();
		String key = StringUtils.EMPTY;
		Integer total = 0;
		for(FalconAppointment appointment : appointments){
			key = sdf.format(appointment.getAppointmentDate());
			total = organiser.get(key);
			if(total == null){
				total = 0;
			}
			organiser.put(key, total + 1);
		}
		List<Schedule> schedules = new ArrayList<>();
		Schedule schedule;
		for(String id : organiser.keySet()){
			schedule = new Schedule();
			schedule.setDay(Integer.parseInt(id.substring(6)));
			schedule.setTotalAppointment(organiser.get(id));
			schedules.add(schedule);
		}
		return schedules;	
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconAppointment> listMonthlyScheduleAdmin(Date date, String admin) {
		final Date start = DateUtils.truncate(date, Calendar.MONTH);
		final Date end = DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.MONTH), -1);
		List<FalconAppointment> appointments = falconAppointmentRepository.listFullByAppointmentDateBetween(start, end);
		List<FalconAppointment> adminsAppointments = new ArrayList<>();
		for(FalconAppointment appointment : appointments){
			String staffAdmin = appointment.getFalconStaff().getFalconUser().getUsername();
			if(StringUtils.equals(admin, staffAdmin)){
				appointment.getFalconStaff().getName();
				appointment.getFalconLocation().getName();
				appointment.getFalconService().getName();
				for(FalconAppointmentPatron falconAppointmentPatron : appointment.getFalconAppointmentPatrons()){
					falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName();
				}
				adminsAppointments.add(appointment);
			}
			
		}
		return adminsAppointments;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FalconAppointment> listMonthlySchedulePatron(Date date, String patron) {
		final Date start = DateUtils.truncate(date, Calendar.MONTH);
		final Date end = DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.MONTH), -1);
		List<FalconAppointment> appointments = falconAppointmentRepository.listFullByAppointmentDateBetween(start, end);
		List<FalconAppointment> patronsAppointment = new ArrayList<>();
		for(FalconAppointment appointment : appointments){
			appointment.getFalconStaff().getName();
			appointment.getFalconStaff().getFalconUser().getName();
			appointment.getFalconLocation().getName();
			appointment.getFalconService().getName();
			for(FalconAppointmentPatron falconAppointmentPatron : appointment.getFalconAppointmentPatrons()){
				if(StringUtils.equals(patron, falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getUsername())){
					falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName();
					patronsAppointment.add(appointment);
				}
			}
		}
		return patronsAppointment;
	}

	@Override
	@Transactional(readOnly = true)
	public FalconAppointment findAppointment(Integer id) {
		FalconAppointment falconAppointment = falconAppointmentRepository.findOne(id);
		falconAppointment.getFalconStaff().getId();
		for(FalconAppointmentPatron falconAppointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
			falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName();
			falconAppointmentPatron.getFalconAppointment().getFalconLocation().getName();
			falconAppointmentPatron.getFalconAppointment().getFalconService().getName();
			falconAppointmentPatron.getFalconAppointment().getFalconStaff().getName();
			falconAppointmentPatron.getFalconAppointment().getFalconStaff().getFalconUser().getEmail();
		}
		return falconAppointment;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAppointment(Integer id) {
		FalconAppointment appointment = new FalconAppointment();
		appointment.setId(id);
		List<FalconAppointmentPatron> patrons = falconAppointmentPatronRepository.findByFalconAppointment(appointment);
		for(FalconAppointmentPatron patron : patrons){
			falconAppointmentPatronRepository.delete(patron);
		}
		falconAppointmentRepository.delete(appointment);
	}

	@Override
	@Transactional(readOnly = true)
	public FalconAppointmentPatron findPatron(Integer id) {
		FalconAppointmentPatron falconAppointmentPatron = falconAppointmentPatronRepository.findOne(id);
		falconAppointmentPatron.getFalconAppointment().getFalconStaff().getName();
		falconAppointmentPatron.getFalconAppointment().getFalconLocation().getName();
		falconAppointmentPatron.getFalconPatron().getFalconUserByPatron().getName();
		return falconAppointmentPatron;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAppointmentPatron(Integer id) {
		FalconAppointmentPatron appointmentPatron = new FalconAppointmentPatron();
		appointmentPatron.setId(id);
		falconAppointmentPatronRepository.delete(appointmentPatron);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void rescheduleAppointment(Integer appointmentId, Date startDate,
			Date endDate, Integer locationId) {
		FalconAppointment appointment = falconAppointmentRepository.findOne(appointmentId);
		FalconLocation location = falconLocationRepository.findOne(locationId);
		appointment.setAppointmentDate(startDate);
		appointment.setAppointmentDateEnd(endDate);
		appointment.setFalconLocation(location);
		falconAppointmentRepository.save(appointment);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FalconAppointment> findAppointmentsByParameter(Integer staffId,
			String patronId, Integer serviceId, Integer locationId,
			Date appointmentDate) {
		List<FalconAppointment> appointments = falconAppointmentRepository.listAppointmentsByParam(staffId, patronId, serviceId, locationId, appointmentDate);
		if(CollectionUtils.isNotEmpty(appointments)){
			for(FalconAppointment falconAppointment : appointments){
				for(FalconAppointmentPatron appointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
					appointmentPatron.getFalconPatron().getFalconUserByPatron().getName();
				}
				falconAppointment.getFalconLocation().getName();
				falconAppointment.getFalconService().getName();
				falconAppointment.getFalconStaff().getName();
				falconAppointment.getFalconStaff().getFalconUser().getName();
			}
		}
		return appointments;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAppointmentPatrons(FalconAppointment falconAppointment) {
		final FalconAppointment appointment = falconAppointmentRepository.findOne(falconAppointment.getId());
		final Set<FalconAppointmentPatron> falconAppointmentPatrons = appointment.getFalconAppointmentPatrons();
		falconAppointmentPatronRepository.delete(falconAppointmentPatrons);
		for(FalconAppointmentPatron falconAppointmentPatron : falconAppointment.getFalconAppointmentPatrons()){
			falconAppointmentPatron.setFalconAppointment(appointment);
			falconAppointmentPatronRepository.save(falconAppointmentPatron);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> getMonthlySchedule(Date date, String admin) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final Date start = DateUtils.truncate(date, Calendar.MONTH);
		final Date end = DateUtils.addSeconds(DateUtils.ceiling(date, Calendar.MONTH), -1);
		List<FalconAppointment> appointments = falconAppointmentRepository.findByAppointmentDateBetween(start, end);
		HashMap<String, Integer> organiser = new LinkedHashMap<>();
		String key = StringUtils.EMPTY;
		Integer total = 0;
		for(FalconAppointment appointment : appointments){
			String staffAdmin = appointment.getFalconStaff().getFalconUser().getUsername();
			if(StringUtils.equals(staffAdmin, admin)){
				key = sdf.format(appointment.getAppointmentDate());
				total = organiser.get(key);
				if(total == null){
					total = 0;
				}
				organiser.put(key, total + 1);
			}
		}
		List<Schedule> schedules = new ArrayList<>();
		Schedule schedule;
		for(String id : organiser.keySet()){
			schedule = new Schedule();
			schedule.setDay(Integer.parseInt(id.substring(6)));
			schedule.setTotalAppointment(organiser.get(id));
			schedules.add(schedule);
		}
		return schedules;	
	}
	

}
