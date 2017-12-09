package de.hska.lkit.trumpet.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.lkit.trumpet.application.model.User;
import de.hska.lkit.trumpet.application.services.ServiceBundle;

@RestController
public class FunctionsController {
	
	/**
	 * Searches for a list of users with an regular expression.
	 * Returns an empty list when no user matches the expression
	 * @param expression Regular Expression
	 * @return List of Users
	 */
	@RequestMapping(value = "/searchUser" , method = RequestMethod.GET)
    public List<User> searchForUsers(@RequestParam(value="expression", defaultValue="*") String expression) {
    	ServiceBundle service = new ServiceBundle();
    	List<User> users = new ArrayList<>();
    	service.searchForUsersByExpression(expression).ifPresent(list -> {
    		users.addAll(list);
    	});
        return users;
    }
	
	/**
	 * Authenticates an user with an given user and password.
	 * Returns Unauthorized, when username or password is not correct.
	 * @param username Username
	 * @param password Password
	 * @return User and Password or Unauthorized
	 */
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
    public Object login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
    	ServiceBundle service = new ServiceBundle();
    	Optional<User> userOpt= service.loginUser(username, password.toCharArray());
    	if(userOpt.isPresent()) {
    		return userOpt.get();
    	} else {
    		return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    	}
    }
	
	/**
	 * Authenticates an user with an given user and password.
	 * Returns Unauthorized, when username or password is not correct.
	 * @param username Unique Username that is supposed to be set
	 * @param password Password that is supposed to be set
	 * @return User and Password or Unauthorized
	 */
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
    public Object register(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
    	ServiceBundle service = new ServiceBundle();
    	Optional<User> userOpt= service.registerUser(username, password.toCharArray());
    	if(userOpt.isPresent()) {
    		return userOpt.get();
    	} else {
    		return new ResponseEntity<String>("Conflict", HttpStatus.CONFLICT);
    	}
    }
	
	/**
	 * Authenticates an user with an given user and password.
	 * Returns Unauthorized, when username or password is not correct.
	 * @param username Unique Username that is supposed to be set
	 * @param password Password that is supposed to be set
	 * @return User and Password or Unauthorized
	 */
	@RequestMapping(value = "/trumpIt" , method = RequestMethod.POST)
    public Object post(@RequestParam(value="username") String username, @RequestParam(value="message") String message) {
		System.out.println("New Post has been added: Content= " + message + ", user = " + username);
    	ServiceBundle service = new ServiceBundle();
    	try {
    		service.post(username, message);    		
    	} catch(Exception e) {
    		return new ResponseEntity<String>("Conflict", HttpStatus.CONFLICT);
    	}
    	return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
	
}
