package de.hska.lkit.trumpet.application.pages;

import de.hska.lkit.trumpet.application.services.*;

import java.util.List;

import de.hska.lkit.trumpet.application.model.*;

public class SubscriptionPage {

	ServiceBundle service;
	public List<Tweet> subscriptionTweets;

	public SubscriptionPage() {
		this.service = new ServiceBundle();
		this.updateTweetList();
	}

	private void updateTweetList() {
		// TODO: get user information from session
		User user = new User("Donald Trump", null);
		this.service.getSubscriptionTweets(user).ifPresent(tweets -> {
			log("There were found " + tweets.size() + " Tweets on the subscription list.");
			this.subscriptionTweets = tweets;
		});
	}

	private void log(String debuginfo) {
		System.out.println("[Subscription-Page]: " + debuginfo);
	}
}
