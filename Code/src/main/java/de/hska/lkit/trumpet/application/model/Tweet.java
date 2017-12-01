package de.hska.lkit.trumpet.application.model;

import java.util.Date;

import org.springframework.format.datetime.joda.LocalDateParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import de.hska.lkit.trumpet.application.services.*;

public class Tweet {

    public String message;
    public Date date;
    public String timeStamp;
    public User user;
    
    public Tweet(String message, Date date, User user) {
    	this.message = message;
    	this.date = date;
    	this.timeStamp = this.getDateDifference();
    	this.user = user;
    }
    
    public String getMessage() {
    	return this.message;
    }
    
    public Date getDate() {
    	return this.date;
    }
    
    public User getUser() {
    	return this.user;
    }
    
    public String getDateDifference() {
    	return "vor 20 Std.";
    }

}
