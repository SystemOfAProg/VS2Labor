package de.hska.lkit.trumpet.session;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
	@Autowired 
	private StringRedisTemplate template;

	public boolean auth(String uname, String pass) {
		String uid = template.opsForValue().get("uname:" + uname + ":uid");
		BoundHashOperations<String, String, String> userOps = template.boundHashOps("uid:" + uid + ":user");
		return userOps.get("pass").equals(pass);
	}

	public String addAuth(String uname, long timeout, TimeUnit tUnit) {
		String uid = template.opsForValue().get("uname:" + uname + ":uid");
		String auth = UUID.randomUUID().toString();
		template.boundHashOps("uid:" + uid + ":auth").put("auth", auth);
		template.expire("uid:" + uid + ":auth", timeout, tUnit);
		template.opsForValue().set("auth:" + auth + ":uid", uid, timeout, tUnit);
		return auth;
	}

	public void deleteAuth(String uname) {
		String uid = template.opsForValue().get("uname:" + uname + ":uid");
		String authKey = "uid:" + uid + ":auth";
		String auth = (String) template.boundHashOps(authKey).get("auth");
		List<String> keysToDelete = Arrays.asList(authKey, "auth:" + auth + ":uid");
		template.delete(keysToDelete);
	}
}


