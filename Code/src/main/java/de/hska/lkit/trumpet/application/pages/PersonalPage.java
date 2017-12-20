package de.hska.lkit.trumpet.application.pages;

import de.hska.lkit.trumpet.application.services.*;
import groovyjarjarantlr.debug.DebuggingInputBuffer;

import java.util.List;

import de.hska.lkit.trumpet.application.model.*;

public class PersonalPage {

	ServiceBundle service;
	public List<Tweet> personalTweets;
	public List<User> searchResult;
	public User user;

	public PersonalPage(String name) {
		this.service = new ServiceBundle();
		this.service.getUserByUsername(name).ifPresent(user -> {
			this.user = user;
		});
		this.user.updateFollowerList();
		this.user.updateSubscriptionsList();
		this.updateTweetList();
	}

	private void updateTweetList() {
		this.service.getPersonalTweets(this.user).ifPresent(tweets -> {
			log("There were found " + tweets.size() + " Tweets on the personal list.");
			this.personalTweets = tweets;
		});
	}

	private void log(String debuginfo) {
		System.out.println("[Global-Page]: " + debuginfo);
	}

}
