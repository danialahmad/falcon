package com.twistlet.falcon.security.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testSha256Inban() {
		final String username = "inban";
		final String password = "butterbun";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "5727c6363d91fcc80a98f22a748c95fba96cc9e39110a23b9a38e6ea510476cb";
		assertEquals(expected, actual);
	}

	@Test
	public void testSha256titiwangsa() {
		final String username = "titiwangsa";
		final String password = "butterbun";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "bb1338008e7180b5b8246884e8c37eec9ac8869fccd3340a31e9cf67b375fd6e";
		assertEquals(expected, actual);
	}

	@Test
	public void testSha256butterbun() {
		final String username = "butterbun";
		final String password = "inban";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "c42dad8cd3f474de4a21f50c80c04326938037cd74f0adfcfa51c39f302ba276";
		assertEquals(expected, actual);
	}

	@Test
	public void testSha256melissa() {
		final String username = "melissa";
		final String password = "mel";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "f092d4ac27c31e03eebd80b8634f9d62ca88dc2263b382861374d4992a3eec20";
		assertEquals(expected, actual);
	}

	@Test
	public void testSha256shankar() {
		final String username = "shankar";
		final String password = "shankar1234";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "cda98a529a2cdac1ec3b067c56ffc1ca17cd5724515b1e6f95cfeca383b4ba6b";
		assertEquals(expected, actual);
	}

	@Test
	public void testSha256bmw() {
		final String username = "bmw";
		final String password = "car";
		final PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		final String actual = passwordEncoder
				.encodePassword(password, username);
		final String expected = "b8b76e16b326bb31bec225d5c9a41eb4432d749fbc60b6b53ea2ecbca5e999b8";
		assertEquals(expected, actual);
	}
}
