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
		User user = new User("Iwanka Trump", ("MyHusbandIsACarrot").toCharArray());
		this.service.getSubscriptionTweets(user).ifPresent( tweets -> {
			System.out.println("[Subscription-Page-Info]: There were found " + tweets.size() + " Tweets on the global list.");
			this.subscriptionTweets = tweets;
		});
	}  
}
