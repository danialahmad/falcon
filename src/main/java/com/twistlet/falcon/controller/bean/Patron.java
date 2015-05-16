package com.twistlet.falcon.controller.bean;

public class Patron {
	
	private String key;
	
	private String name;
	
	private Integer patronAppointmentId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPatronAppointmentId() {
		return patronAppointmentId;
	}

	public void setPatronAppointmentId(Integer patronAppointmentId) {
		this.patronAppointmentId = patronAppointmentId;
	}

}
