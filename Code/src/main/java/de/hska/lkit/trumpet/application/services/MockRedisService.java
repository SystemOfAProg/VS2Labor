package de.hska.lkit.trumpet.application.services;

import java.util.List;
import java.util.Optional;
import de.hska.lkit.trumpet.application.model.*;

/* 	=============================================
 * 	TODO: Implement interface for redis-databse
 *  TODO: Take a look at Jedis-Framework
 * 	=============================================
 */

public class MockRedisService implements IRedisService{
	
	// ==========================================================================================
    // 										Authentication
    // ==========================================================================================
	
	@Override
	public Optional<Object> authenticate(String username, char[]pass) {
		return Optional.ofNullable("authenticate: Redis-Answer-Expression");
	}
	
	// ==========================================================================================
    // 										TweetCollection
    // ==========================================================================================
    
	@Override
    public Optional<Object> getSubscriptionTweets(User user) {
    	return Optional.ofNullable("getSubscriptionTweets: Redis-Answer-Expression");
    }
    
    @Override
    public Optional<Object> getPersonalTweets(User user) {
    	return Optional.ofNullable("getPersonalTweets: Redis-Answer-Expression");
    }
    
    @Override
    public Optional<Object> getGlobalTweets() {
    	return Optional.ofNullable("getGlobalTweets: Redis-Answer-Expression");
    }
    
    // ==========================================================================================
    // 										  User-Search
    // ==========================================================================================
    
    @Override
    public Optional<Object> getUserByUsername(String username) {
		return Optional.ofNullable("getUserByUsername: " + username);
	}
    
    @Override
    public Optional<Object> searchForUsersByExpression(String expression) {
    	return Optional.ofNullable("searchForUsersByExpression: Redis-Answer-Expression");
    }
    
    @Override
    public Optional<Object> getFollowersOfUsers(User user) {
    	return Optional.ofNullable("getFollowersOfUsers: Redis-Answer-Expression");
    }

	@Override
	public Optional<Object> register(String username, char[] pass) {
		return Optional.ofNullable("register: Redis-Answer-Expression");
	}

	@Override
	public Optional<Object> post(String username, String message) {
		return Optional.ofNullable("post: Redis-Answer-Expression");
	}
    
}
