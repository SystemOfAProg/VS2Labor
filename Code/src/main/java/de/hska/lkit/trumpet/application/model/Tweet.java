package de.hska.lkit.trumpet.application.model;

import java.util.Date;
import java.util.Locale;

import org.springframework.format.datetime.joda.LocalDateParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import de.hska.lkit.trumpet.application.services.*;

public class Tweet {

	public String message;
	public Date date;
	public String timeStamp;
	public User user;
	public String id;

	public Tweet(String message, Date date, User user, String id) {
		this.message = message;
		this.date = date;
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
		return "vor 20 Std.";
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
		} catch (ParseException ex) {
			// kommt noch was rein
		}
	}

	public void setUser(User user) {
		this.user = user;
	}
}
