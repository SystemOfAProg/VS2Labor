package de.hska.lkit.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrumpetController {

	@RequestMapping(value = "/global")
	public String landingPageSubmit(@ModelAttribute Greeting greeting, Model model) {
		return "landingPage";
	}
	
	@RequestMapping(value = "/subscriptions")
	public String subscriptionPageSubmit(@ModelAttribute Greeting greeting, Model model) {
		return "subscriptionsPage";
	}
	
	@RequestMapping(value = "/profile")
	public String profilePageSubmit(@ModelAttribute Greeting greeting, Model model) {
		return "profilePage";
	}
}
