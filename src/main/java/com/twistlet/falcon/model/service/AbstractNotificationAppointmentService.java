package com.twistlet.falcon.model.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconAppointment;
import com.twistlet.falcon.model.entity.FalconAppointmentPatron;
import com.twistlet.falcon.model.entity.FalconLocation;
import com.twistlet.falcon.model.entity.FalconPatron;
import com.twistlet.falcon.model.entity.FalconService;
import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.repository.FalconAppointmentRepository;
import com.twistlet.falcon.model.repository.FalconUserRepository;

public abstract class AbstractNotificationAppointmentService implements NotificationAppointmentService {

	private final FalconAppointmentRepository falconAppointmentRepository;
	private final FalconUserRepository falconUserRepository;
	private final String template;
	private final JavaMailSender javaMailSender;
	private final SimpleMailMessage simpleMailMessage;
	private final String smsTemplate;
	private final SmsService smsService;
	private final String operation;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public AbstractNotificationAppointmentService(final FalconAppointmentRepository falconAppointmentRepository,
			final FalconUserRepository falconUserRepository, final String template, final JavaMailSender javaMailSender,
			final SimpleMailMessage simpleMailMessage, final String smsTemplate, final SmsService smsService, final String operation) {
		this.falconAppointmentRepository = falconAppointmentRepository;
		this.falconUserRepository = falconUserRepository;
		this.template = template;
		this.javaMailSender = javaMailSender;
		this.simpleMailMessage = simpleMailMessage;
		this.smsTemplate = smsTemplate;
		this.smsService = smsService;
		this.operation = operation;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MimeMessage> createMessages(final Integer appointmentId) {
		final List<MimeMessage> list = new ArrayList<>();
		final FalconAppointment falconAppointment = falconAppointmentRepository.findOne(appointmentId);
		if (falconAppointment != null) {
			final FalconStaff staff = falconAppointment.getFalconStaff();
			final FalconLocation falconLocation = falconAppointment.getFalconLocation();
			final FalconService falconService = falconAppointment.getFalconService();
			final Set<FalconAppointmentPatron> set = falconAppointment.getFalconAppointmentPatrons();
			final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
			final SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
			final Date appointmentDate = falconAppointment.getAppointmentDate();
			final String date = formatDate.format(appointmentDate);
			final String time = formatTime.format(appointmentDate);
			final String staffName = staff.getName();
			final String patronName = nameOrGroupSize(set);
			final String venue = falconLocation.getName();
			final String service = falconService.getName();
			if (Boolean.TRUE.equals(staff.getSendEmail())) {
				final MimeMessage mimeMessage = createMessage(staffName, staff.getEmail(), date, time, staffName, patronName,
						venue, service);
				list.add(mimeMessage);
			}

			for (final FalconAppointmentPatron item : set) {
				final FalconPatron patron = item.getFalconPatron();
				final FalconUser user = patron.getFalconUserByPatron();
				if (Boolean.TRUE.equals(user.getSendEmail())) {
					final String patronMailName = user.getName();
					final String patronMail = user.getEmail();
					final MimeMessage mimeMessage = createMessage(patronMailName, patronMail, date, time, staffName, patronName,
							venue, service);
					list.add(mimeMessage);
				}
			}

		} else {
			logger.warn("Unable to find appointment id # {} for " + operation + " messsage creation", appointmentId);
		}
		logger.info("{} mail messages created", list.size());
		return list;
	}

	private String nameOrGroupSize(final Set<FalconAppointmentPatron> set) {
		if (set.size() == 1) {
			final FalconAppointmentPatron item = set.toArray(new FalconAppointmentPatron[] {})[0];
			final FalconPatron patron = item.getFalconPatron();
			final FalconUser user = patron.getFalconUserByPatron();
			return user.getName();
		} else {
			return "(Group of " + set.size() + ")";
		}
	}

	private MimeMessage createMessage(final String name, final String email, final String date, final String time,
			final String staffName, final String patronName, final String venue, final String service) {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final SimpleToMimeConversionService conversionService = new SimpleToMimeConversionService();
		try {
			conversionService.toMime(simpleMailMessage, mimeMessage);
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			final Object[] arguments = { date, time, staffName, patronName, venue, service };
			final String content = MessageFormat.format(template, arguments);
			helper.setTo("\"" + name + "\" <" + email + ">");
			helper.setText(content, true);
		} catch (final MessagingException e) {
			e.printStackTrace();
		}
		return mimeMessage;
	}

	@Override
	public void send(final List<MimeMessage> list) {
		for (final MimeMessage mimeMessage : list) {
			try {
				javaMailSender.send(mimeMessage);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> createSmsMessages(final Integer appointmentId) {
		final Map<String, String> map = new LinkedHashMap<>();
		final FalconAppointment falconAppointment = falconAppointmentRepository.findOne(appointmentId);
		if (falconAppointment != null) {
			final FalconStaff staff = falconAppointment.getFalconStaff();
			final FalconLocation falconLocation = falconAppointment.getFalconLocation();
			final FalconService falconService = falconAppointment.getFalconService();
			final Set<FalconAppointmentPatron> set = falconAppointment.getFalconAppointmentPatrons();
			final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
			final SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
			final Date appointmentDate = falconAppointment.getAppointmentDate();
			final String date = formatDate.format(appointmentDate);
			final String time = formatTime.format(appointmentDate);
			final String staffName = staff.getName();
			final String patronName = nameOrGroupSize(set);
			final String venue = falconLocation.getName();
			final String service = falconService.getName();
			if (Boolean.TRUE.equals(staff.getSendSms())) {
				final String smsMessage = createSmsMessage(date, time, staffName, patronName, venue, service);
				final String phone = staff.getHpTel();
				map.put(phone, smsMessage);
			}

			for (final FalconAppointmentPatron item : set) {
				final FalconPatron patron = item.getFalconPatron();
				final FalconUser user = patron.getFalconUserByPatron();
				if (Boolean.TRUE.equals(user.getSendSms())) {
					final String smsMessage = createSmsMessage(date, time, staffName, patronName, venue, service);
					final String phone = user.getPhone();
					map.put(phone, smsMessage);
				}
			}

		} else {
			logger.warn("Unable to find appointment id # {} for " + operation + " messsage creation", appointmentId);
		}

		logger.info("{} text messages created", map.size());
		return map;
	}

	private String createSmsMessage(final String date, final String time, final String staffName, final String patronName,
			final String venue, final String service) {
		final Object[] arguments = { date, time, staffName, patronName, venue, service };
		return MessageFormat.format(smsTemplate, arguments);
	}

	@Override
	@Transactional
	public void sendSmsMessages(final String currentUser, final Map<String, String> messageMap) {
		final String username = currentUser;
		final FalconUser falconUser = falconUserRepository.findOne(currentUser);
		if (falconUser.getSmsRemaining() != null) {
			final Set<String> phoneSet = messageMap.keySet();
			for (final String phone : phoneSet) {
				final FalconUser admin = falconUserRepository.findOne(username);
				final Integer count = admin.getSmsRemaining();
				if (count != null && count > 0) {
					final String message = messageMap.get(phone);
					if (message != null) {
						smsService.send(falconUser.getUsername(), phone, message);
						admin.setSmsRemaining(count - 1);
						falconUserRepository.save(admin);
					}
				} else {
					logger.error("Unable to send sms from user {}, no sms remaining.", falconUser.getUsername());
				}
			}

		} else {
			logger.error("Unable to send sms from user {}, no valid sms remaining.", falconUser.getUsername());
		}

	}
}
