package de.hse.swt.timemanagement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;


public class TimeStamp {

	private Date date;
	private Timestamp ts;

	private Thread thread = null;

	private int hours = 0;
	private int minute = 0;
	private int seconds = 0;
	
	private int hours1 = 0;
	private int minute1 = 0;
	private int seconds1 = 0;
	
	private int hoursf = 0;
	private int minutef = 0;
	private int secondsf = 0;

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
		Calendar calendar2 = Calendar.getInstance();
		hours1 = calendar2.get(calendar2.HOUR_OF_DAY);
		minute1 = calendar2.get(calendar2.MINUTE);
		seconds1 = calendar2.get(calendar2.SECOND);
		
		hoursf = hours1 - hours;
		minutef = minute1 - minute;
		secondsf = seconds1 - seconds;
		
		//Idee Umsetzung mit minuten zusammen rechnen und jeweils wieder umrechnen um die zeit zu bekommen!
		
		return (hoursf + ":" + minutef + ":" + secondsf);
		}

	private void initComponents() {
		date = new Date();
	}

}