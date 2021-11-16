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

	// Variablen für getTimer()
	private int hours1 = 0;
	private int minute1 = 0;
	private int hoursf = 0;
	private int minutef = 0;
	private int minuteges = 0;
	private int minutestart = 0;
	private int hoursfinal = 0;
	private int minutesfinal = 0;
	private int speicher = 0;

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

	public String getTimer() {
		// Inhalt der klasse sollte ohne probleme in run() vom Kernel einbaubar sein!
		// Zum testen der Klasse muss vor dem Timer auf jeden fall einmal getTimestamp
		// ausgeführt werden da dies als Start initialisierung gilt

		// Holen der aktuellen Zeit für den Vergleich der Startzeit
		Calendar calendar2 = Calendar.getInstance();
		hours1 = calendar2.get(calendar2.HOUR);
		minute1 = calendar2.get(calendar2.MINUTE);

		// Berechnung Minuten der Startzeit
		minutestart += (hours * 60);
		minutestart += minute;

		// Bestimmung der aktuellen Minuten der aktuellen Zeit
		hoursf = hours1 - hours;
		minutef = minute1 - minute;

		minuteges += minutef;
		minuteges += (60 * hoursf);

		// Vergleich Startminuten mit aktuellen Minuten
		speicher = minuteges - minutestart;

		// aufteilung übrige zeit und dann rückgabe
		hoursfinal = speicher / 60;
		minutesfinal = speicher % 60;

		return (hoursf + ":" + minutef);
	}

	private void initComponents() {
		date = new Date();
	}

}