package de.hse.swt.timemanagement;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeStampTest {
	@Test
	public void testGetTime(){
		System.out.println(TimeStamp.getTimestamp());
	}
	
	@Test
	public void testCalcTime() {
		assertEquals("03:30:00", TimeStamp.calcTime("10:30:00", "-" , "07:00:00"));
		assertEquals("05:30:00", TimeStamp.calcTime("02:30:00", "+", "03:00:00"));
	}
	
	@Test
	public void testGetTimer() {
		assertEquals("03:30:00", TimeStamp.getTimer("07:00:00", "10:30:00"));
	}
}
