package com.twistlet.falcon.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconAppointment;

public interface FalconAppointmentRepository extends JpaRepository<FalconAppointment, Integer>, FalconAppointmentRepositoryCustom {

	List<FalconAppointment> findByAppointmentDateBetween(Date start, Date end);

	List<FalconAppointment> findByAppointmentDateBetweenAndNotified(Date start, Date end, Character notified);

}
