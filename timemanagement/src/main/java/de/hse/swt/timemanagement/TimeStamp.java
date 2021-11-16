package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

public class TimeStamp {

	private Date date;
	private Timestamp ts;

	private Thread thread = null;

	// Variablen für getTimestamp()
	private static int hours = 0;
	private static int minute = 0;
	private static int seconds = 0;

	private static int startHours = 0;
	private static int startMinutes = 0;
	private static int startSeconds = 0;
	
	private static int endHours = 0;
	private static int endMinutes = 0;
	private static int endSeconds = 0;
	
	private static int finalStart = 0;
	private static int finalEnd = 0;
	private static int finalTime = 0;
	
	private static int finalHours = 0;
	private static int finalMinutes = 0;
	private static int finalSeconds = 0;

	public TimeStamp() {
		initComponents();
	}

	
	/**  
	 * @param usrEMail
     * @return String of current time in format: hh:mm:ss
	 * */
	public static String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		hours = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);
		seconds = calendar.get(Calendar.SECOND);

		String sh = ("" + hours);
		if (hours < 10) 
			sh = ("0" + hours);
		String sm = ("" + hours);
		if (hours < 10)
			sm = ("0" + hours);
		String ss = ("" + hours);
		if (hours < 10)
			ss = ("0" + hours);

		return (sh + ":" + sm + ":" + ss);
	}

	public static String getTimer(String start, String end) {
		startHours = Integer.parseInt(start.substring(0, 2));
		startMinutes = Integer.parseInt(start.substring(3, 5));
		startSeconds = Integer.parseInt(start.substring(6, 8));
		
		endHours = Integer.parseInt(end.substring(0, 2));
		endMinutes = Integer.parseInt(end.substring(3, 5));
		endSeconds = Integer.parseInt(end.substring(6, 8));

		finalStart += ((startHours *60)*60);
		finalStart += (startMinutes*60);
		finalStart += startSeconds;
		
		finalEnd += ((endHours *60)*60);
		finalEnd += (endMinutes*60);
		finalEnd += endSeconds;
		
		finalTime = finalEnd - finalStart;
		
		finalHours = (finalTime / 60 ) / 60;
		finalMinutes = (finalTime - ((finalHours * 60)*60 )) / 60;
		finalSeconds = finalTime % 60;
		
		return(finalHours + ":" + finalMinutes + ":" + finalSeconds);
	}
	private void initComponents() {
		date = new Date();
	}

}