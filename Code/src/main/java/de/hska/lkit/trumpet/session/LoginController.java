package de.hska.lkit.trumpet.session;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.lkit.trumpet.application.model.User;

@Controller
public class LoginController {
	@Autowired private RedisRepository repository;
	private static final Duration TIMEOUT = Duration.ofMinutes(20);

	//@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") @Valid User user, HttpServletResponse response, Model model) { 
		if (repository.auth(user.getName(), user.getPassword().toString())) {
			String auth = repository.addAuth(user.getName(), TIMEOUT.getSeconds(), TimeUnit.SECONDS); 
			Cookie cookie = new Cookie("auth", auth); 
			response.addCookie(cookie);
			model.addAttribute("user", user.getName()); 
			//Auf welcher Seite landet man nach erfolgreichem Login
			return "redirect:/global";
		}
		model.addAttribute("user", new User(null, null)); 
		//Auf welcher Seite landet man nach unerfolgreichem Login
		return "redirect:/global";
	}

	@RequestMapping(value = "/blog/logout", method = RequestMethod.GET) 
	public String logout() {
		if (SimpleSecurity.isSignedIn()) {
			String name = SimpleSecurity.getName(); 
			repository.deleteAuth(name); 
		}
		return "redirect:/global";
	}
}
