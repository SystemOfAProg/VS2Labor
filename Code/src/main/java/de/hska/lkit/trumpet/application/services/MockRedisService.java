package de.hska.lkit.trumpet.application.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.hska.lkit.trumpet.application.JedisFactory;
import de.hska.lkit.trumpet.application.model.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import ch.qos.logback.core.net.SyslogOutputStream;

/* 	=============================================
 * 	TODO: Implement interface for redis-databse
 *  TODO: Take a look at Jedis-Framework
 * 	=============================================
 */

public class MockRedisService implements IRedisService {

	@Override
	public Optional<String> id() {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			return Optional.of(jedis.set("id", "1"));
		} catch (Exception e) {
			System.out.println("Mock id CatchBlock");
			e.printStackTrace();
			return null;
		}
	}

	// ==========================================================================================
	// Authentication
	// ==========================================================================================

	@Override
	public Optional<User> authenticate(String username, char[] pass) {
		// JedisPool jedisPool = JedisFactory.getPool();
		// try (Jedis jedis = jedisPool.getResource()) {
		//
		// String key = username;
		// // if (jedis.hget(key, "name").equals(username) && (jedis.hget(key,
		// // "passwort").toCharArray().equals(pass))) {
		// String name = jedis.hget(key, "name");
		// char[] pw = jedis.hget(key, "pw").toCharArray();
		// User user = new User(name, pw);
		// return Optional.of(user);
		//
		// } catch (Exception e) {
		// System.out.println("Mock authenticate CatchBlock");
		// e.printStackTrace();
		System.out.println("authenticate schlug fehl");
		return null;
		// }
		// return Optional.ofNullable("authenticate: Redis-Answer-Expression");
	}

	@Override
	public Optional<User> register(String username, char[] pass) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = username;
			jedis.hset(key, "name", username);
			jedis.hset(key, "pw", String.valueOf(pass));

			jedis.zadd("all_users", 1, key);

			System.out.println("Test: " + jedis.hget(key, "name") + jedis.hget(key, "pw"));
			
			User user = new User(username, pass);
			return Optional.of(user);
		} catch (Exception e) {
			System.out.println("Mock register CatchBlock");
			e.printStackTrace();
			return null;
		}
	}

	// ==========================================================================================
	// TweetCollection
	// ==========================================================================================

	@Override
	public Optional<Tweet> getTweets(User user, String id) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = user.getName() + ":tweet:" + id;
			Tweet tweet = new Tweet(null, null, user, id);
			tweet.setMessage(jedis.hget(key, "message"));
			tweet.setDate(jedis.hget(key, "date"));
			return Optional.of(tweet);
		} catch (Exception e) {
			System.out.println("Mock getTweets");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<List<String>> getSubscriptionTweets(User user) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = user.getName() + ":follower:tweet";
			System.out.println(key);
			return Optional.of(jedis.lrange(key, 0, -1));
		} catch (Exception e) {
			System.out.println("Mock getSubscriptionTweets");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<List<String>> getPersonalTweets(User user) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = user.getName() + ":personal";
			return Optional.of(jedis.lrange(key, 0, -1));

		} catch (Exception e) {
			System.out.println("Mock getPersonalTweets");
			e.printStackTrace();
			return null;
		}
		// return Optional.ofNullable("getPersonalTweets: Redis-Answer-Expression");
	}

	@Override
	public Optional<List<String>> getGlobalTweets() {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			return Optional.of(jedis.lrange("global", 0, -1));
		} catch (Exception e) {
			System.out.println("Mock getGlobalTweets");
			e.printStackTrace();
			return null;
		}
		// return Optional.ofNullable("getGlobalTweets: Redis-Answer-Expression");
	}

	// currentUser folgt userToFollow
	//
	@Override
	public void follow(String currentUser, String userToFollow) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String ichFolgeKey = currentUser + ":following";
			jedis.sadd(ichFolgeKey, userToFollow);

			String mirWirdGefolgtKey = userToFollow + ":follower";
			jedis.sadd(mirWirdGefolgtKey, currentUser);
		} catch (Exception e) {
			System.out.println("Mock follower");
			e.printStackTrace();
		}
	}

	@Override
	public void unfollow(String currentUser, String userToFollow) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String ichFolgeKey = currentUser + ":following";
			jedis.srem(ichFolgeKey, userToFollow);

			String mirWirdGefolgtKey = userToFollow + ":follower";
			jedis.srem(mirWirdGefolgtKey, currentUser);
		} catch (Exception e) {
			System.out.println("Mock unfollower");
			e.printStackTrace();
		}
	}

	// ==========================================================================================
	// User-Search
	// ==========================================================================================

	@Override
	public Optional<User> getUserByUsername(String username) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = username;
			User user = new User(jedis.hget(key, "name"), null);
			return Optional.of(user);
		} catch (Exception e) {
			System.out.println("Mock getUserByUsername");
			e.printStackTrace();
			return null;
		}
		// return Optional.ofNullable("getUserByUsername: " + username);
	}

	@Override
	public Optional<List<String>> searchForUsersByExpression(String expression) {
		System.out.println("expression: " + expression);
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			if (jedis.exists("0")) {
				jedis.del("0");
			}
			long lenght = jedis.zcard("all_users");
			Set<String> sresult = jedis.zrange("all_users", 0, lenght);

			for (Iterator<String> iterator = sresult.iterator(); iterator.hasNext();) {
				String iter = iterator.next();
				System.out.println("Iterator: " + iter);
				if (iter.length() >= expression.length() && iter.substring(0, expression.length()).equals(expression)) {
					jedis.lpush("0", iter);
					System.out.println("Suche hinzugefügt: " + iter);
				}
			}
			return Optional.of(jedis.lrange("0", 0, 1));
		} catch (Exception e) {
			System.out.println("Mock SearchUserByExpression: " + expression);
			e.printStackTrace();
			return Optional.of(new ArrayList<String>());
		}
		// return Optional.ofNullable("searchForUsersByExpression:
		// Redis-Answer-Expression");
	}

	@Override
	public Optional<Set<String>> getFollowersOfUsers(User user) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = user.getName() + ":follower";

			return Optional.of(jedis.smembers(key));
		} catch (Exception e) {
			System.out.println("Mock getFollowersOfUsers");
			e.printStackTrace();
			return null;
		}
		// return Optional.ofNullable("getFollowersOfUsers: Redis-Answer-Expression");
	}

	@Override
	public Optional<Set<String>> getFollowingOfUsers(User user) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			String key = user.getName() + ":following";
			return Optional.of(jedis.smembers(key));
		} catch (Exception e) {
			System.out.println("Mock getFollowersOfUsers");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void post(String username, String message) {
		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			long tweetId = jedis.incr("id");

			String key = username + ":tweet:" + tweetId;
			System.out.println("key der nachricht:" + key);

			jedis.hset(key, "message", message);
			Calendar cal = Calendar.getInstance();
			java.util.Date time = cal.getTime();
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

			jedis.hset(key, "date", formatter.format(time));

			jedis.lpush("global", key);
			jedis.lpush(username + ":personal", key);

			Set<String> werEinemFolgt = jedis.smembers(username + ":follower");
			for (Iterator<String> iterator = werEinemFolgt.iterator(); iterator.hasNext();) {
				String iter = iterator.next();
				System.out.println("Iterator: " + iter);
				jedis.lpush(iter + ":follower:tweet", key);
				System.out.println("name von dem Typen auf wesen liste man schreibt: " + iter);
			}
		} catch (Exception e) {
			System.out.println("Mock catch block post");
			e.printStackTrace();
		}
	}
}
