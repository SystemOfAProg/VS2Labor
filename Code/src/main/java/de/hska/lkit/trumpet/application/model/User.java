package de.hska.lkit.trumpet.application.model;

import java.util.List;
import de.hska.lkit.trumpet.application.services.*;

public class User {

	public ServiceBundle service;
	public String name;
	public String tagName;
	public char[] password;
	public List<User> follower;
	public List<User> subscriptions;
	public int followerCount;
	public int subscriptionsCount;
	public List<Tweet> subscriptionsTweetList;
	public List<Tweet> personalTweetList;

	public User(String name, char[] password) {
		this.name = name;
		this.tagName = this.getTagName();
		this.password = password;
		this.service = new ServiceBundle();
	}

	// ==============================================================
	// Getter
	// ==============================================================

	public String getName() {
		return name;
	}

	public String getTagName() {
		String tagName = "@real" + name.toLowerCase().replace(" ", "");
		return tagName;
	}

	public char[] getPassword() {
		return password;
	}

	public List<User> getSubscriptionsList() {
		return this.subscriptions;
	}

	public List<User> getFollowerList() {
		return this.follower;
	}

	public List<Tweet> getSubscriptionsTweetList() {
		return this.subscriptionsTweetList;
	}

	public List<Tweet> getPersonalTweetList() {
		return this.personalTweetList;
	}

	// ==============================================================
	// Setter
	// ==============================================================

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public void updateFollowerList() {
		this.service.getFollowersOfUsers(this).ifPresent(follower -> {
			this.follower = follower;
			this.followerCount = this.follower.size();
		});
	}

	public void updateSubscriptionsList() {
		this.service.getSubscriptionsOfUsers(this).ifPresent(subscriptions -> {
			this.subscriptions = subscriptions;
			this.subscriptionsCount = this.subscriptions.size();
		});
	}

}
