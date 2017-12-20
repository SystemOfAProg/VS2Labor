package de.hska.lkit.trumpet.application;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactory {
	public static JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);

	public static JedisPool getPool() {
		return pool;
	}
}
