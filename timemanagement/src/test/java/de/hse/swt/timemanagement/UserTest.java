package de.hse.swt.timemanagement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	User user;

	@Before
	public void setUp() {
		user = new User(1, "Max", "Mustermann", "max@muster.de", "Employee", 1, 30);
	}

	@Test
	public void testGetID() {
		assertEquals(1, user.getID());
	}

	@Test
	public void testGetFirstName() {
		assertEquals("Max", user.getFirstName());
	}

	@Test
	public void testGetLastName() {
		assertEquals("Mustermann", user.getLastName());
	}

	@Test
	public void testGetEMail() {
		assertEquals("max@muster.de", user.getEMail());
	}

	@Test
	public void testGetHierarchy() {
		assertEquals("Employee", user.getHierarchy());
	}

	@Test
	public void testGetGroupID() {
		assertEquals(1, user.getGroupID());
	}

	@Test
	public void testGetVacDays() {
		assertEquals(30, user.getVacDays());
	}

	@Test
	public void testClearData() {
		user.clearData();
		assertEquals(-1, user.getID());
		assertEquals(null, user.getFirstName());
		assertEquals(null, user.getLastName());
		assertEquals(null, user.getEMail());
		assertEquals(null, user.getHierarchy());
		assertEquals(-1, user.getGroupID());
		assertEquals(-1, user.getVacDays());
	}

}
