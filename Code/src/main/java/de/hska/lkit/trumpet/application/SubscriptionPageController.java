package de.hska.lkit.trumpet.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hska.lkit.trumpet.application.pages.*;

@Controller
public class SubscriptionPageController {

	@RequestMapping(value = "/subscriptions")
	public String subscriptionPageSubmit(@ModelAttribute SubscriptionPage page, Model model) {
		model.addAttribute("page", page != null ? page : new SubscriptionPage());
		return "subscriptionsPage";
	}

}
