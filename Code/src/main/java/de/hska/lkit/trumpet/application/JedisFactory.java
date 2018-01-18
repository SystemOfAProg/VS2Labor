package de.hska.lkit.trumpet.application;

import java.time.Duration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactory {
	final static JedisPoolConfig poolConfig = buildPoolConfig();
	static JedisPool pool = new JedisPool(poolConfig, "localhost", 6379);

	public static JedisPool getPool() {
		return pool;
	}
	
	private static JedisPoolConfig buildPoolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(128);
		    poolConfig.setMaxIdle(128);
		    poolConfig.setMinIdle(16);
		    poolConfig.setTestOnBorrow(true);
		    poolConfig.setTestOnReturn(true);
		    poolConfig.setTestWhileIdle(true);
		    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		    poolConfig.setNumTestsPerEvictionRun(3);
		    poolConfig.setBlockWhenExhausted(true);		
		return poolConfig;
	}
}
