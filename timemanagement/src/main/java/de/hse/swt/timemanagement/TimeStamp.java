package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;

public class TimeStamp implements Runnable{

	private Date date;
	private Timestamp ts;
	
	private Thread thread = null;
	private String timeString = "";
	

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
	
	public void startTimer() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		
		while(true) {
			timeString = getTimestamp().toString();
			System.out.println(timeString);
		}
		
	}

}