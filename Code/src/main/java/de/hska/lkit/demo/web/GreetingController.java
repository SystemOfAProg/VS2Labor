package de.hska.lkit.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

	@RequestMapping(value = "/start")
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
		return "landingPage";
	}
}
