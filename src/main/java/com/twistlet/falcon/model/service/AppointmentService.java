package com.twistlet.falcon.model.service;

import java.util.Date;
import java.util.List;

import com.twistlet.falcon.controller.bean.Schedule;
import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;

public interface AppointmentService {

	void createAppointment(FalconAppointment falconAppointment);
	
	void updateAppointmentPatrons(FalconAppointment falconAppointment);
	
	List<Schedule> getMonthlySchedule(Date date);
	
	List<Schedule> getMonthlySchedule(Date date, String admin);
	
	List<FalconAppointment> listMonthlyScheduleAdmin(Date date, String admin);
	
	List<FalconAppointment> listMonthlySchedulePatron(Date date, String patron);
	
	FalconAppointment findAppointment(Integer id);
	
	void deleteAppointment(Integer id);
	
	FalconAppointmentPatron findPatron(Integer id);
	
	void deleteAppointmentPatron(Integer id);
	
	void rescheduleAppointment(Integer appointmentId, Date startDate, Date endDate, Integer locationId);
	
	List<FalconAppointment> findAppointmentsByParameter(Integer staffId, String patronId, Integer serviceId, Integer locationId, Date appointmentDate);
}
