package de.hska.lkit.trumpet.application;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import de.hska.lkit.trumpet.application.services.Application;

@SpringBootApplication
public class TrumpetWebApplication {
	static ApplicationContext ctx;
	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(TrumpetWebApplication.class, args);
		ctx = SpringApplication.run(Application.class, args);
//		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class); 
//		CountDownLatch latch = ctx.getBean(CountDownLatch.class); 
//		template.convertAndSend("chat", "Hello from Redis!");
//		latch.await(); 
		//System.exit(0);
	}
	public static ApplicationContext getCtx() {
		return ctx;
	}
}
