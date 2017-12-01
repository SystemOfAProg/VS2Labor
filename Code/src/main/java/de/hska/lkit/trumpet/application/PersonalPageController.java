package de.hska.lkit.trumpet.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hska.lkit.trumpet.application.pages.*;

@Controller
public class PersonalPageController {

	@RequestMapping(value = "/profile/{userName}")
	public String profilePageSubmit(@ModelAttribute("personalPage") PersonalPage page, Model model) {
		model.addAttribute("page", page);
		return "profilePage";
	}
	
	@ModelAttribute("personalPage")
	public PersonalPage prepareModel(@PathVariable("userName") String name) {
	    return new PersonalPage(name);
	}
	
}
