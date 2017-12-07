package de.hska.lkit.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import de.hska.lkit.trumpet.application.TrumpetWebApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TrumpetWebApplication.class)
@WebAppConfiguration
public class SpringWebDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
