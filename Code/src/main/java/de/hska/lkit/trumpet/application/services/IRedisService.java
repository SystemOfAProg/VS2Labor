package de.hska.lkit.trumpet.application.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hska.lkit.trumpet.application.model.*;

public interface IRedisService {

	// ==========================================================================================
	// Authentication
	// ==========================================================================================
	public Optional<String> id();

	public Optional<User> authenticate(String username, char[] pass);

	public Optional<User> register(String username, char[] pass);

	// ==========================================================================================
	// TweetCollection
	// ==========================================================================================

	public Optional<Tweet> getTweets(User user, String id);

	public Optional<List<String>> getSubscriptionTweets(User user);

	public Optional<List<String>> getPersonalTweets(User user);

	public Optional<List<String>> getGlobalTweets();

	// ==========================================================================================
	// CreateThings
	// ==========================================================================================

	public void follow(String currentUser, String userToFollow);

	public void unfollow(String currentUser, String userToFollow);

	// ==========================================================================================
	// User-Search
	// ==========================================================================================

	public Optional<User> getUserByUsername(String username);

	public Optional<List<String>> searchForUsersByExpression(String expression);

	public Optional<Set<String>> getFollowersOfUsers(User user);

	public Optional<Set<String>> getFollowingOfUsers(User user);

	// ==========================================================================================
	// Post
	// ==========================================================================================

	public void post(String username, String message);

}
