package com.twistlet.falcon.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconFeedback;
import com.twistlet.falcon.model.repository.FalconFeedbackRepository;

@Service
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

	private final FalconFeedbackRepository falconFeedbackRepository;
	private final SupportMailSenderService supportMailSenderService;

	@Autowired
	public CustomerFeedbackServiceImpl(final FalconFeedbackRepository falconFeedbackRepository,
			final SupportMailSenderService supportMailSenderService) {
		this.falconFeedbackRepository = falconFeedbackRepository;
		this.supportMailSenderService = supportMailSenderService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void sendFeedback(final FalconFeedback feedback) {
		try {
			final String feedbackType = feedback.getFeedbackType();
			final String content = feedback.getContent();
			final String cc = feedback.getEmailFrom();
			supportMailSenderService.send(feedbackType, cc, content);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		falconFeedbackRepository.save(feedback);
	}

}
