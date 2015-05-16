package com.twistlet.falcon.model.service;


public interface ReminderService {

	void sendNotificationToAppointmentsInTheFuture(long seconds);
}
