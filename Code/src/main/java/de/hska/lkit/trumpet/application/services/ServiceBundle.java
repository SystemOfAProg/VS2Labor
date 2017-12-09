package de.hska.lkit.trumpet.application.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import de.hska.lkit.trumpet.application.model.*;

/**
 * High-Level Utility to search for objects in Redis-Database.
 * Translates Redis-Answers in Java-Objects.
 */
public class ServiceBundle {

	private IRedisService redis;
	
    public ServiceBundle() {
    	// TODO: Change MockRedisService to functioning Class implementing IRedisService
    	this.redis = new MockRedisService();
    }
    
    // ==========================================================================================
    // 										Authentication
    // ==========================================================================================
    
    /**
     * Authenticates an user with an given password and username.
     * @param username Username
     * @param password Password
     * @return User found with the given authentication-tokens
     */
    public Optional<User> loginUser(String username, char[] password) {
    	Optional<User> user = this.redis.authenticate(username, password).map(response -> {
    		return this.transformResponseToUser(response);
    	});
    	return user;
    }
    
    /**
     * Registers an user with an given password and username.
     * @param username Username
     * @param password Password
     * @return User if no user with this name is not existing already
     */
    public Optional<User> registerUser(String username, char[] password) {
    	Optional<User> user = this.redis.register(username, password).map(response -> {
    		return this.transformResponseToUser(response);
    	});
    	return user;
    }
    
    // ==========================================================================================
    // 										TweetCollection
    // ==========================================================================================
    
    /**
     * Returns all tweets of users the given user has subscribed to.
     * The Collection contains all tweets since the given user has subscribed to the author of the
     * it.
     * @param user whose subscription's tweets are supposed to be collected.
     * @return A Optional of a List with Tweet-Objects
     */
    public Optional<List<Tweet>> getSubscriptionTweets(User user) {
    	Optional<List<Tweet>> tweets = this.redis.getSubscriptionTweets(user).map(response -> {
    		return this.transformResponseToTweetList(response);
    	});
    	return tweets;
    }
    
    /**
     * Returns all tweets the given user has ever posted.
     * @param user whose tweets are looked for.
     * @return A Optional of a List with Tweet-Objects
     */
    public Optional<List<Tweet>> getPersonalTweets(User user) {
    	Optional<List<Tweet>> tweets = this.redis.getPersonalTweets(user).map(response -> {
    		return this.transformResponseToTweetList(response);
    	});
    	return tweets;
    }
    
    /**
     * Returns all tweets that have ever been posted by all registered users.
     * @return A Optional of a List with Tweet-Objects
     */
    public Optional<List<Tweet>> getGlobalTweets() {
    	Optional<List<Tweet>> tweets = this.redis.getGlobalTweets().map(response -> {
    		return this.transformResponseToTweetList(response);
    	});
    	return tweets;
    }
    
    
    // ==========================================================================================
    // 										USER SEARCH
    // ==========================================================================================
    
    /**
     * Returns User-Object by searching for it with a given username. Search is Case-Sensitive!
     * @param username Case-Sensitive username to search for
     * @return User-Object representing the user with the given username.
     */
    public Optional<User> getUserByUsername(String username) throws IllegalArgumentException {
    	Optional<User> user = this.redis.getUserByUsername(username).map(response -> {
    		return this.transformResponseToUser(response);
    	});
    	return user;
    }
 
    /**
     * Searches for a sorted list of users by an regular expression of the searched for username.
     * @param expression Regular Expression of the username
     * @return A sorted List of Usernames
     */
    public Optional<List<User>> searchForUsersByExpression(String expression) {
    	Optional<List<User>> tweets = this.redis.searchForUsersByExpression(expression).map(response -> {
    		return this.transformResponseToUserList(response);
    	});
    	return tweets;
    }
    
    /**
     * Searches for a sorted list of users by an regular expression of the searched for username.
     * @param expression Regular Expression of the username
     * @return A sorted List of Usernames
     */
    public Optional<List<User>> getFollowersOfUsers(User user) {
    	Optional<List<User>> tweets = this.redis.getFollowersOfUsers(user).map(response -> {
    		return this.transformResponseToUserList(response);
    	});
    	return tweets;
    }
    
    /**
     * Searches for a sorted list of users by an regular expression of the searched for username.
     * @param expression Regular Expression of the username
     * @return A sorted List of Usernames
     */
    public Optional<List<User>> getSubscriptionsOfUsers(User user) {
    	Optional<List<User>> tweets = this.redis.getFollowersOfUsers(user).map(response -> {
    		return this.transformResponseToUserList(response);
    	});
    	return tweets;
    }
    
    // ==========================================================================================
    // 										Post
    // ==========================================================================================
    
    /**
     * Returns User-Object by searching for it with a given username. Search is Case-Sensitive!
     * @param username Case-Sensitive username to search for
     * @return User-Object representing the user with the given username.
     */
    public void post(String username, String message) {
    	this.redis.post(username, message);
    }
     
    // ==========================================================================================
    // 									Transform Utils
    // ==========================================================================================
    
    private User transformResponseToUser(Object user) {
    	// TODO: Handle implement transformResponseToUser()
    	System.out.println("Transform redis-response to User: " + user.toString());
    	String name = user.toString().replaceAll("getUserByUsername: ", "");
    	char[] pw = ("MansNotOrange").toCharArray();
    	return new User(name, pw);
    }
    
    private List<Tweet> transformResponseToTweetList(Object tweetList) {
    	// TODO: Handle implement transformResponseToTweetList()
    	System.out.println(tweetList.toString());
    	List<Tweet> tweets = new ArrayList<>();
    	for(int i=0; i<10; i++) {
    		String message = "Covfefe!";
    		Date date = new Date();
    		User user = new User("Donald Trump " + (i+1), ("MansNotOrange").toCharArray());
    		Tweet tweet = new Tweet(message, date, user);
    		tweets.add(tweet);
    	}
    	return tweets;
    }
    
    private List<User> transformResponseToUserList(Object userList) {
    	// TODO: Handle implement transformResponseToTweetList()
    	System.out.println(userList.toString());
    	List<User> users = new ArrayList<>();
    	for(int i=0; i<10; i++) {
    		User user = new User("Donald Trump " + (i+1) , ("MansNotOrange").toCharArray());
    		users.add(user);
    	}
    	return users;
    }
    
}
