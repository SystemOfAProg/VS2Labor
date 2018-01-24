package de.hska.lkit.trumpet.application.pages;

import de.hska.lkit.trumpet.application.services.*;

import java.util.List;
import java.util.Optional;

import de.hska.lkit.trumpet.application.model.*;
import de.hska.lkit.trumpet.application.security.SecurityUtils;

public class SubscriptionPage {

	ServiceBundle service;
	public List<Tweet> subscriptionTweets;
	public String username = SecurityUtils.getUserName();

	public SubscriptionPage() {
		this.service = new ServiceBundle();
		this.updateTweetList();
	}

	private void updateTweetList() {
		Optional<User> userOpt;
		try {
			userOpt = service.getUserByUsername(username);
		} catch (Exception e) {
			userOpt = Optional.ofNullable(null);
		}
		User user;
		if (userOpt.isPresent()) {
			user = userOpt.get();
			this.service.getSubscriptionTweets(user).ifPresent(tweets -> {
				log("There were found " + tweets.size() + " Tweets on the subscription list.");
				log("Current user: " + (SecurityUtils.getUserName() != null ? SecurityUtils.getUserName() : "-"));
				this.subscriptionTweets = tweets;
			});
		}
		else {
			this.subscriptionTweets = null;
		}
	}

	private void log(String debuginfo) {
		System.out.println("[Subscription-Page]: " + debuginfo);
	}
}
