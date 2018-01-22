package de.hska.lkit.trumpet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import de.hska.lkit.trumpet.application.JedisFactory;
import de.hska.lkit.trumpet.application.TrumpetWebApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TrumpetWebApplication.class)
@WebAppConfiguration
public class TrumpetApplicationTests {

	@Test
	public void contextLoads() {

		JedisPool jedisPool = JedisFactory.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.flushAll();
		} catch (Exception e) {
			System.out.println("transformResponseToUserList");
			e.printStackTrace();
		}
	}

}
