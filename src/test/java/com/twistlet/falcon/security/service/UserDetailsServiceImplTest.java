package com.twistlet.falcon.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.twistlet.falcon.model.entity.FalconRole;
import com.twistlet.falcon.model.entity.FalconUser;
import com.twistlet.falcon.model.entity.FalconUserRole;
import com.twistlet.falcon.model.repository.FalconUserRepository;
import com.twistlet.falcon.model.repository.FalconUserRoleRepository;

public class UserDetailsServiceImplTest {

	private FalconUserRepository falconUserRepository;

	private FalconUserRoleRepository falconUserRoleRepository;

	private UserDetailsServiceImpl unit;

	@Before
	public void init() {
		falconUserRepository = EasyMock.createStrictMock(FalconUserRepository.class);
		falconUserRoleRepository = EasyMock.createStrictMock(FalconUserRoleRepository.class);
		unit = new UserDetailsServiceImpl(falconUserRepository, falconUserRoleRepository);
	}

	@After
	public void verify() {
		EasyMock.verify(falconUserRepository, falconUserRoleRepository);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNotFound() {
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(null);
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		unit.loadUserByUsername("invalid");
		fail("Not yet implemented");
	}

	@Test
	public void testLoadUserByUsernameNoRoles() {
		final FalconUser falconUser = new FalconUser("user100", "xxx", "Nobody", "nobody@nowhere.com", "60111111111",
				"831111123532", true, true, true, 0, 0, null, null, null, null, null, null);
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(falconUser);
		EasyMock.expect(falconUserRoleRepository.findByFalconUserUsername(EasyMock.anyObject(String.class))).andReturn(
				new ArrayList<FalconUserRole>());
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		final UserDetails userDetails = unit.loadUserByUsername("user100");
		final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		assertEquals(0, authorities.size());
	}

	@Test
	public void testLoadUserByUsernameRoleAdminCountOne() {
		final FalconUser falconUser = new FalconUser("user100", "xxx", "Nobody", "nobody@nowhere.com", "60111111111",
				"831111123532", true, true, true, 0, 0, null, null, null, null, null, null);
		final FalconRole falconRole = new FalconRole("ROLE_ADMIN");
		final List<FalconUserRole> falconUserRoles = new ArrayList<>();
		final FalconUserRole falconUserRole = new FalconUserRole();
		falconUserRole.setId(1);
		falconUserRole.setFalconUser(falconUser);
		falconUserRole.setFalconRole(falconRole);
		falconUserRoles.add(falconUserRole);
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(falconUser);
		EasyMock.expect(falconUserRoleRepository.findByFalconUserUsername(EasyMock.anyObject(String.class))).andReturn(
				falconUserRoles);
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		final UserDetails userDetails = unit.loadUserByUsername("user100");
		final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		assertEquals(1, authorities.size());
	}

	@Test
	public void testLoadUserByUsernameRoleAdminValue() {
		final FalconUser falconUser = new FalconUser("user100", "xxx", "Nobody", "nobody@nowhere.com", "60111111111",
				"831111123532", true, true, true, 0, 0, null, null, null, null, null, null);
		final FalconRole falconRole = new FalconRole("ROLE_ADMIN");
		final List<FalconUserRole> falconUserRoles = new ArrayList<>();
		final FalconUserRole falconUserRole = new FalconUserRole();
		falconUserRole.setId(1);
		falconUserRole.setFalconUser(falconUser);
		falconUserRole.setFalconRole(falconRole);
		falconUserRoles.add(falconUserRole);
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(falconUser);
		EasyMock.expect(falconUserRoleRepository.findByFalconUserUsername(EasyMock.anyObject(String.class))).andReturn(
				falconUserRoles);
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		final UserDetails userDetails = unit.loadUserByUsername("user100");
		final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		assertEquals("ROLE_ADMIN", new ArrayList<>(authorities).get(0).getAuthority());

	}

	@Test
	public void testLoadUserByUsernameRoleAdminCountTwo() {
		final FalconUser falconUser = new FalconUser("user100", "xxx", "Nobody", "nobody@nowhere.com", "60111111111",
				"831111123532", true, true, true, 0, 0, null, null, null, null, null, null);
		final List<FalconUserRole> falconUserRoles = new ArrayList<>();
		{
			final FalconRole falconRole = new FalconRole("ROLE_ADMIN");
			final FalconUserRole falconUserRole = new FalconUserRole();
			falconUserRole.setId(1);
			falconUserRole.setFalconUser(falconUser);
			falconUserRole.setFalconRole(falconRole);
			falconUserRoles.add(falconUserRole);
		}
		{
			final FalconRole falconRole = new FalconRole("ROLE_USER");
			final FalconUserRole falconUserRole = new FalconUserRole();
			falconUserRole.setId(2);
			falconUserRole.setFalconUser(falconUser);
			falconUserRole.setFalconRole(falconRole);
			falconUserRoles.add(falconUserRole);
		}
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(falconUser);
		EasyMock.expect(falconUserRoleRepository.findByFalconUserUsername(EasyMock.anyObject(String.class))).andReturn(
				falconUserRoles);
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		final UserDetails userDetails = unit.loadUserByUsername("user100");
		final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		assertEquals(2, authorities.size());
	}

	@Test
	public void testLoadUserByUsernameRoleAdminCountTwoValue() {
		final FalconUser falconUser = new FalconUser("user100", "xxx", "Nobody", "nobody@nowhere.com", "60111111111",
				"831111123532", true, true, true, 0, 0, null, null, null, null, null, null);
		final List<FalconUserRole> falconUserRoles = new ArrayList<>();
		{
			final FalconRole falconRole = new FalconRole("ROLE_ADMIN");
			final FalconUserRole falconUserRole = new FalconUserRole();
			falconUserRole.setId(1);
			falconUserRole.setFalconUser(falconUser);
			falconUserRole.setFalconRole(falconRole);
			falconUserRoles.add(falconUserRole);
		}
		{
			final FalconRole falconRole = new FalconRole("ROLE_USER");
			final FalconUserRole falconUserRole = new FalconUserRole();
			falconUserRole.setId(2);
			falconUserRole.setFalconUser(falconUser);
			falconUserRole.setFalconRole(falconRole);
			falconUserRoles.add(falconUserRole);
		}
		EasyMock.expect(falconUserRepository.findOne(EasyMock.anyObject(String.class))).andReturn(falconUser);
		EasyMock.expect(falconUserRoleRepository.findByFalconUserUsername(EasyMock.anyObject(String.class))).andReturn(
				falconUserRoles);
		EasyMock.replay(falconUserRepository, falconUserRoleRepository);
		final UserDetails userDetails = unit.loadUserByUsername("user100");
		final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		assertEquals("ROLE_USER", new ArrayList<>(authorities).get(1).getAuthority());
	}
}
