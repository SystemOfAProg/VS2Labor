package de.hska.lkit.trumpet.application.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Tweet {

	public String message;
	public Date date;
	public String timeStamp;
	public User user;
	public String id;

	public Tweet(String message, String date, User user, String id) {
		this.message = message;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			System.out.println("date parse tweet");
			e.printStackTrace();
		}
		this.timeStamp = this.getDateDifference();
		this.user = user;
		this.id = id;
	}

	// ==============================================================
	// Getter
	// ==============================================================

	public String getMessage() {
		return this.message;
	}

	public Date getDate() {
		return this.date;
	}

	public User getUser() {
		return this.user;
	}

	public String getId() {
		return this.id;
	}

	public String getDateDifference() {
		Calendar cal = Calendar.getInstance();
		java.util.Date time = cal.getTime();
		long dif = time.getTime() - date.getTime();
		System.out.println("Differenz in Sekunden: " + dif / 1000);
		if (dif / 1000 / 60 < 1) {
			return "vor wenigen Sekunden.";
		} else if (dif / 1000 / 60 < 2) {
			return "vor 1 Minute.";
		} else if (dif / 1000 / 60 < 60) {
			return "vor " + dif / 1000 / 60 + " Minuten.";
		} else if (dif / 1000 / 60 / 60 < 2) {
			return "vor 1 Stunde.";
		} else if (dif / 1000 / 60 / 60 < 24) {
			return "vor " + dif / 1000 / 60 / 60 + " Stunden.";
		} else if (dif / 1000 / 60 / 60 < 48) {
			return "vor 1 Tag.";
		} else {
			return "vor " + dif / 1000 / 60 / 60 / 24 + " Tagen";
		}
	}

	// ==============================================================
	// Setter
	// ==============================================================

	public void setId(String id) {
		this.id = id;
	}

	public void setMessage(String massage) {
		this.message = massage;
	}

	public void setDate(String date) throws ParseException {
		try {
			DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);

			Date date1 = format.parse(date);

			this.date = date1;
		} catch (ParseException e) {
			System.out.println("date2");
			e.printStackTrace();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}
}
