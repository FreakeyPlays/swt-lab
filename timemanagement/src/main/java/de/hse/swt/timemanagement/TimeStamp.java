package de.hse.swt.timemanagement;

import java.util.Calendar;

public class TimeStamp {
	
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
	
	
	
	public static String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		hours = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);
		seconds = calendar.get(Calendar.SECOND);

		
		String sh = (""+hours);	//Dieser block ist dazu da dass uhrzeiten in dem format angegeben werden können : 10:03:23
		if(hours < 10)			//Code, der die Variablen hours minute und seconds verändert über diesem block spätestens ausdefinieren!!!
			sh = ("0"+hours);
		String sm = (""+minute);
		if(minute < 10)
			sm = ("0"+minute);
		String ss = (""+seconds);
		if(seconds < 10)
			ss = ("0"+seconds);
		
		return (sh + ":" + sm + ":" + ss);
	}
	
	public static String getTimer(String start, String end) {
		startHours = Integer.parseInt(start.substring(0, 2));
		startMinutes = Integer.parseInt(start.substring(3, 5));
		startSeconds = Integer.parseInt(start.substring(6, 8));

		endHours = Integer.parseInt(end.substring(0, 2));
		endMinutes = Integer.parseInt(end.substring(3, 5));
		endSeconds = Integer.parseInt(end.substring(6, 8));

		finalStart += ((startHours * 60) * 60);
		finalStart += (startMinutes * 60);
		finalStart += startSeconds;

		finalEnd += ((endHours * 60) * 60);
		finalEnd += (endMinutes * 60);
		finalEnd += endSeconds;

		finalTime = finalEnd - finalStart;

		finalHours = (finalTime / 60) / 60;
		finalMinutes = (finalTime - ((finalHours * 60) * 60)) / 60;
		finalSeconds = finalTime % 60;
		
		String sh = (""+finalHours);	//Dieser block ist dazu da dass uhrzeiten in dem format angegeben werden können : 10:03:23
		if(finalHours < 10)			//Code, der die Variablen hours minute und seconds verändert über diesem block spätestens ausdefinieren!!!
			sh = ("0"+finalHours);
		String sm = (""+finalMinutes);
		if(finalMinutes < 10)
			sm = ("0"+finalMinutes);
		String ss = (""+finalSeconds);
		if(finalSeconds < 10)
			ss = ("0"+finalSeconds);

		return (sh + ":" + sm + ":" + ss);
	}

}