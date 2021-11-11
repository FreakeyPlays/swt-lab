package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;

public class TimeStamp {

	private Date date;
	private Timestamp ts;

	public TimeStamp() {
		initComponents();
	}

	public Timestamp getTimestamp() {
		ts = new Timestamp(date.getTime());
		System.out.println(ts);// Test Printout of the Timestamp
		return ts;// Returntype based on the sql Timestamp!!
	}

	private void initComponents() {
		date = new Date();
	}

}