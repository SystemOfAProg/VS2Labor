package de.hska.lkit.trumpet.application.pages;

import java.util.ArrayList;
import java.util.List;
import de.hska.lkit.trumpet.application.services.*;
import de.hska.lkit.trumpet.application.model.*;

public class GlobalPage {
	
	ServiceBundle service;
	public List<Tweet> globalTweets;

	public GlobalPage() {
		this.service = new ServiceBundle();
		this.updateTweetList();
	}

	private void updateTweetList() {
		this.service.getGlobalTweets().ifPresent( tweets -> {
			System.out.println("[Global-Page-Info]: There were found " + tweets.size() + " Tweets on the global list.");
			this.globalTweets = tweets;
		});
	}

}
