package de.hska.lkit.trumpet.application.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.hska.lkit.trumpet.application.JedisFactory;
import redis.clients.jedis.Jedis;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "de.hska.lkit.trumpet.application", "de.hska.lkit.trumpet.application.config",
		"de.hska.lkit.trumpet.application.model", "de.hska.lkit.trumpet.application.pages",
		"de.hska.lkit.trumpet.application.sercices", })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


		Jedis jedis = JedisFactory.getPool().getResource();
		Set<String> keys = jedis.zrange("all_users", 0, jedis.zcard("all_users"));

		if (!keys.isEmpty()) {
			for (String key : keys) {
				System.out.println(jedis.hget(key, "name") + " - " + jedis.hget(key, "pw"));

				auth.inMemoryAuthentication().withUser(jedis.hget(key, "name")).password(jedis.hget(key, "pw"))
						.roles("USER");
			}
		}

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/profile/*" /*,"/subscriptions"*/).hasRole("USER").and().formLogin().defaultSuccessUrl("/global", true).and().logout().deleteCookies("remove").invalidateHttpSession(false)
			.logoutUrl("/custom-logout").logoutSuccessUrl("/global");
		
		
	}

}
