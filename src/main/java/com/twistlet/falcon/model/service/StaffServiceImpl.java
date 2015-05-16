package com.twistlet.falcon.model.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.twistlet.falcon.model.entity.FalconStaff;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.repository.FalconStaffRepository;
import com.twistlet.falcon.model.repository.FalconUserRepository;

@Service
public class StaffServiceImpl implements StaffService {

	private final FalconUserRepository falconUserRepository;
	private final FalconStaffRepository falconStaffRepository;
	private final MailSenderService mailSenderService;
	private final SmsService smsService;

	@Autowired
	public StaffServiceImpl(final FalconUserRepository falconUserRepository, final FalconStaffRepository falconStaffRepository,
			final MailSenderService mailSenderService, final SmsService smsService) {
		this.falconUserRepository = falconUserRepository;
		this.falconStaffRepository = falconStaffRepository;
		this.mailSenderService = mailSenderService;
		this.smsService = smsService;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconUser> listPatients(final String partialName) {
		return falconUserRepository.findByRolenameAndNameLike("ROLE_PATRON", partialName);
	}

	@Override
	public boolean sendEmail(final String from, final String name, final String address, final String message) {
		final String sendTo = "\"" + name + "\"" + " <" + address + ">";
		try {
			mailSenderService.send(from, sendTo, message);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean sendSms(final String from, final String phone, final String message) {
		try {
			final FalconUser user = falconUserRepository.findOne(from);
			final int remaining = user.getSmsRemaining();
			if (remaining > 0) {
				smsService.send(from, phone, message);
				user.setSmsRemaining(remaining - 1);
				user.setSmsSentLifetime(user.getSmsSentLifetime() + 1);
				falconUserRepository.save(user);
				return true;
			} else {
				return false;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public FalconUser getUser(final String username) {
		return falconUserRepository.findOne(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdmin(final String admin) {
		final FalconUser user = new FalconUser();
		user.setUsername(admin);
		final List<FalconStaff> staffs = falconStaffRepository.findByFalconUserAndValid(user, true);
		return staffs;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveStaff(final FalconStaff staff) {
		falconStaffRepository.save(staff);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdminNameLike(final FalconUser admin, final String username) {
		return falconStaffRepository.findByFalconUserNameLike(admin, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdminNricLike(final FalconUser admin, final String nric) {
		return falconStaffRepository.findByFalconUserNricLike(admin, nric);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdminEmailLike(final FalconUser admin, final String email) {
		return falconStaffRepository.findByFalconUserEmailLike(admin, email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdminStaffLike(final FalconUser admin, final FalconStaff staff) {
		return falconStaffRepository.findByFalconUserStaffLike(admin, staff);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconStaff> listStaffByAdminMobileLike(final FalconUser admin, final String mobile) {
		return falconStaffRepository.findByFalconUserHpTelLike(admin, mobile);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteStaff(final FalconStaff staff) {
		falconStaffRepository.delete(staff);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<FalconStaff> listAvailableStaff(final FalconUser admin, final Date start, final Date end) {
		final List<FalconStaff> staffs = falconStaffRepository.findByFalconUserAndValid(admin, true);
		final Set<FalconStaff> busyStaffs = falconStaffRepository.findStaffDateRange(admin, start, end);
		final Set<FalconStaff> availableStaffs = new HashSet<>();
		for (final FalconStaff staff : staffs) {
			boolean found = false;
			for (final FalconStaff busyStaff : busyStaffs) {
				if (staff.getId().equals(busyStaff.getId())) {
					found = true;
					break;
				}
			}
			if (!found) {
				availableStaffs.add(staff);
			}
		}
		return availableStaffs;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<FalconStaff> listAvailableStaff(FalconUser admin, Date start,
			Date end, Integer appointmentId) {
		final List<FalconStaff> staffs = falconStaffRepository.findByFalconUserAndValid(admin, true);
		final Set<FalconStaff> busyStaffs = falconStaffRepository.findStaffDateRange(admin, start, end, appointmentId);
		final Set<FalconStaff> availableStaffs = new HashSet<>();
		for (final FalconStaff staff : staffs) {
			boolean found = false;
			for (final FalconStaff busyStaff : busyStaffs) {
				if (staff.getId().equals(busyStaff.getId())) {
					found = true;
					break;
				}
			}
			if (!found) {
				availableStaffs.add(staff);
			}
		}
		return availableStaffs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FalconUser> listPatronByAdminId(final String adminId) {
		return falconUserRepository.findByAdmin(adminId);
	}

}
