package de.hska.lkit.trumpet.application.services;

import java.util.Optional;

import de.hska.lkit.trumpet.application.model.User;

public interface IRedisService {

	// ==========================================================================================
    // 										Authentication
    // ==========================================================================================
	
	public Optional<Object> authenticate(String username, char[]pass);
	
	public Optional<Object> register(String username, char[]pass);
	
	// ==========================================================================================
    // 										TweetCollection
    // ==========================================================================================
    
    public Optional<Object> getSubscriptionTweets(User user);
    
    public Optional<Object> getPersonalTweets(User user);
    
    public Optional<Object> getGlobalTweets();
    
    // ==========================================================================================
    // 										  User-Search
    // ==========================================================================================
    
    public Optional<Object> getUserByUsername(String username);
    
    public Optional<Object> searchForUsersByExpression(String expression);
    
    public Optional<Object> getFollowersOfUsers(User user);

    // ==========================================================================================
    // 										  	Post
    // ==========================================================================================
    
	public Optional<Object> post(String username, String message);
	
}
