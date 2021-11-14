package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

public class TimeStamp implements Runnable{

	private Date date;
	private Timestamp ts;
	
	private Thread thread = null;
	
	private int hours = 0;
	private int minute = 0;
	private int seconds = 0;
	

	public TimeStamp() {
		initComponents();
	}

	public String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		hours = calendar.get(calendar.HOUR_OF_DAY);
		minute = calendar.get(calendar.MINUTE);
		seconds = calendar.get(calendar.SECOND);
		 
		return (hours + ":" + minute + ":" + seconds);// Returntype based on the sql Timestamp!!
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
		try {
		while(true) {

			System.out.println(getTimestamp());
			thread.sleep(1000);
		}
		}
		catch (Exception e) {
			
		}
	}

}