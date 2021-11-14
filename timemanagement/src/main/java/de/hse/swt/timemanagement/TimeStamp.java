package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import javafx.concurrent.Task;

public class TimeStamp {

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

		
		String sh = (""+hours);	//Dieser block ist dazu da dass uhrzeiten in dem format angegeben werden können : 10:03:23
		if(hours < 10)			//Code, der die Variablen hours minute und seconds verändert über diesem block spätestens ausdefinieren!!!
			sh = ("0"+hours);
		String sm = (""+hours);
		if(hours < 10)
			sm = ("0"+hours);
		String ss = (""+hours);
		if(hours < 10)
			ss = ("0"+hours);
		
		return (sh + ":" + sm + ":" + ss);
	}

	public String getTimer() {
		return "Penis";
	}

	private void initComponents() {
		date = new Date();
	}

	//public void startTimer() {
	//	thread = new Thread(this);
	//	thread.start();
	//}

	//@SuppressWarnings("deprecation")
	//public void stopTimer() {
	//	thread.stop();
	//	thread = null;
	//}

/*	@Override
	protected Integer call() throws Exception {
		while (true) {
			String t = getTimestamp();
			System.out.println(t);
			mc.printCurrentTime(t);
			thread.sleep(1000);
		}
	}*/

}