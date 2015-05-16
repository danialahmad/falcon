package com.twistlet.falcon.model.scheduler;

import java.util.concurrent.TimeUnit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.twistlet.falcon.model.service.ReminderService;

public class ReminderJob extends QuartzJobBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ReminderService reminderService;

	public void setReminderService(final ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	@Override
	protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
		logger.info("Starting up...");
		final long seconds = TimeUnit.HOURS.toSeconds(2);
		reminderService.sendNotificationToAppointmentsInTheFuture(seconds);
		logger.info("Done...");
	}

}
