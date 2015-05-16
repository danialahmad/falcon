package com.twistlet.falcon.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconPatron;

public interface FalconAppointmentPatronRepository extends JpaRepository<FalconAppointmentPatron, Integer> {

	List<FalconAppointmentPatron> findByFalconAppointment(FalconAppointment appointment);
	
	List<FalconAppointmentPatron> findByFalconPatron(FalconPatron falconPatron);
	
}
