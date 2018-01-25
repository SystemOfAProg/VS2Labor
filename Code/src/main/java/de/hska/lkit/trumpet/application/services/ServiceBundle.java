package de.hska.lkit.trumpet.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hska.lkit.trumpet.application.JedisFactory;
import de.hska.lkit.trumpet.application.model.Tweet;
import de.hska.lkit.trumpet.application.model.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * High-Level Utility to search for objects in Redis-Database. Translates
 * Redis-Answers in Java-Objects.
 */
public class ServiceBundle {

	private IRedisService redis;

	public ServiceBundle() {
		this.redis = new MockRedisService();
	}

	// ==========================================================================================
	// Authentication
	// ==========================================================================================

	/**
	 * Authenticates an user with an given password and username.
	 * 
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @return User found with the given authentication-tokens
	 */
	public Optional<User> loginUser(String username, char[] password) {
		Optional<User> user = this.redis.authenticate(username, password).map(response -> {
			System.out.println("was kommt hier an: loginUser" + response.toString());
			return this.transformResponseToUser(response);
		});
		return user;
	}

	/**
	 * Registers an user with an given password and username.
	 * 
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @return User if no user with this name is not existing already
	 */
	public Optional<User> registerUser(String username, char[] password) {
		Optional<User> user = this.redis.register(username, password).map(response -> {
			return this.transformResponseToUser(response);
		});
		
		return user;
	}

	// ==========================================================================================
	// TweetCollection
	// ==========================================================================================

	/**
	 * Returns all tweets of users the given user has subscribed to. The Collection
	 * contains all tweets since the given user has subscribed to the author of the
	 * it.
	 * 
	 * @param user
	 *            whose subscription's tweets are supposed to be collected.
	 * @return A Optional of a List with Tweet-Objects
	 */
	public Optional<List<Tweet>> getSubscriptionTweets(User user) {
		Optional<List<Tweet>> tweets = this.redis.getSubscriptionTweets(user).map(response -> {
			System.out.println("getSubscriptionOfTweets: response -> " + response);
			return this.transformResponseToTweetList(response);
		});
		return tweets;
	}

	/**
	 * @param user
	 *            whose tweets are looked for. Returns all tweets the given user has
	 *            ever posted.
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
	 * 
	 * @return A Optional of a List with Tweet-Objects
	 */
	public Optional<List<Tweet>> getGlobalTweets() {
		Optional<List<Tweet>> tweets = this.redis.getGlobalTweets().map(response -> {
			return this.transformResponseToTweetList(response);
		});
		return tweets;
	}

	// ==========================================================================================
	// USER SEARCH
	// ==========================================================================================

	/**
	 * Returns User-Object by searching for it with a given username. Search is
	 * Case-Sensitive!
	 * 
	 * @param username
	 *            Case-Sensitive username to search for
	 * @return User-Object representing the user with the given username.
	 */
	public Optional<User> getUserByUsername(String username) throws IllegalArgumentException {
		if(username != null) {
			Optional<User> user = this.redis.getUserByUsername(username).map(response -> {
				return this.transformResponseToUser(response);
			});	
			return user;
		} else {
			return Optional.ofNullable(null);
		}
	}

	/**
	 * Searches for a sorted list of users by an regular expression of the searched
	 * for username.
	 * 
	 * @param expression
	 *            Regular Expression of the username
	 * @return A sorted List of Usernames
	 */
	public Optional<List<User>> searchForUsersByExpression(String expression) {
		Optional<List<User>> tweets = this.redis.searchForUsersByExpression(expression).map(response -> {
			System.out.println("searchForUsersByExpression: response -> " + response);
			return this.transformResponseToUserList(response);
		});
		return tweets;
	}

	/**
	 * Searches for a sorted list of users by an regular expression of the searched
	 * for username.
	 * 
	 * @param expression
	 *            Regular Expression of the username
	 * @return A sorted List of Usernames
	 */
	public Optional<List<User>> getFollowersOfUsers(User user) {
		Optional<List<User>> tweets = this.redis.getFollowersOfUsers(user).map(response -> {
			return this.transformResponseToUserList2(response);
		});
		return tweets;
	}

	/**
	 * Searches for a sorted list of users by an regular expression of the searched
	 * for username.
	 * 
	 * @param expression
	 *            Regular Expression of the username
	 * @return A sorted List of Usernames
	 */
	public Optional<List<User>> getSubscriptionsOfUsers(User user) {
		Optional<List<User>> tweets = this.redis.getFollowingOfUsers(user).map(response -> {
			System.out.println("getSubscriptionsOfUsers: response -> " + response);
			return this.transformResponseToUserList2(response);
		});
		return tweets;
	}

	/**
	 * Unfollow an User.
	 * 
	 * @param currentUser
	 *            User that follows.
	 * @param userToFollow
	 *            User that gets followed by <b> currentUser </b>
	 */
	public void follow(String currentUser, String userToFollow) {
		this.redis.follow(currentUser, userToFollow);
	}

	public void unfollow(String currentUser, String userToFollow) {
		this.redis.unfollow(currentUser, userToFollow);
	}

	// ==========================================================================================
	// Post
	// ==========================================================================================

	/**
	 * Returns User-Object by searching for it with a given username. Search is
	 * Case-Sensitive!
	 * 
	 * @param username
	 *            Case-Sensitive username to search for
	 * @return User-Object representing the user with the given username.
	 */
	public void post(String username, String message) {
		this.redis.post(username, message);
	}

	// ==========================================================================================
	// Transform Utils
	// ==========================================================================================

	private User transformResponseToUser(User user) {
		System.out.println("Transform redis-response to User: " + user.toString());
		String name = user.getName();
		System.out.println(name);
		// char[] pw = user.getPassword();
		// System.out.println(pw.toString());
		return new User(name, null);
		// return user;
	}

	private List<Tweet> transformResponseToTweetList(List<String> tweetList) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			System.out.println("transformResponseToTweetList: die übergeben tweetliste: " + tweetList.toString());

			List<Tweet> tweets = new ArrayList<>();
			for (int i = 0; i < tweetList.size(); i++) {
				Tweet tweet = new Tweet(null, null, null, null);
				String value = tweetList.get(i); // tagName + ":tweet:" + id
				System.out.println("transform tweetlist value: " + value);

				int doppelpunkt = value.indexOf(":");
				String keyUser = value.substring(0, doppelpunkt);
				System.out.println("key vom User:" + keyUser);

				int letzterDoppelpunkt = value.lastIndexOf(":") + 1;
				String id = value.substring(letzterDoppelpunkt, value.length());
				System.out.println("id:" + id);
				String keyMessage = keyUser + ":tweet:" + id;
				System.out.println("key von der nachricht: " + keyMessage);

				tweet.setDate(jedis.hget(keyMessage, "date"));
				tweet.setMessage(jedis.hget(keyMessage, "message"));
				// die nächsten 3 Zeilen kann man löschen, vorerst drin gelassen.
				String wertÜberprüfen = jedis.hget(keyMessage, "message");
				System.out.println("was in der nachricht drin steht: " + wertÜberprüfen);
				System.out.println("was in der nachricht drin steht: " + tweet.getMessage());

				User user = new User(jedis.hget(keyUser, "name"), null);
				tweet.setUser(user);

				tweets.add(tweet);
				System.out.println("größe der Liste: " + tweets.size());
			}
			return tweets;
		} catch (Exception e) {
			System.out.println("transformResponseToTweetList");
			e.printStackTrace();
		}
		return null;
	}

	private List<User> transformResponseToUserList(List<String> userList) {

		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			System.out.println("Die Übergebene userList" + userList.toString());
			List<User> users = new ArrayList<>();
			for (int i = 0; i < userList.size(); i++) {
				String keyUser = userList.get(i);
				System.out.println("keyUser: " + keyUser);

				String pw = jedis.hget(keyUser, "pw");
				User user = new User(jedis.hget(keyUser, "name"), null);

				users.add(user);
			}
			System.out.println("users:" + users);
			return users;
		} catch (Exception e) {
			System.out.println("transformResponseToUserList");
			e.printStackTrace();
			return null;
		}
	}

	private List<User> transformResponseToUserList2(Set<String> userList) {

		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			ArrayList<String> arrayList = new ArrayList<String>(userList);

			System.out.println(arrayList.toString());
			List<User> users = new ArrayList<>();
			for (int i = 0; i < arrayList.size(); i++) {
				String keyUser = arrayList.get(i);
				User user = new User(jedis.hget(keyUser, "name"), null);

				users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println("transformResponseToUserList2");
			e.printStackTrace();
			return null;
		}

	}
}