package de.hska.lkit.trumpet.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.lkit.trumpet.application.model.User;
import de.hska.lkit.trumpet.application.services.ServiceBundle;

@RestController
public class UserSearchController {

	 @RequestMapping("/searchUser")
    public List<User> greeting(@RequestParam(value="expression", defaultValue="*") String expression) {
    	ServiceBundle service = new ServiceBundle();
    	List<User> users = new ArrayList<>();
    	service.searchForUsersByExpression(expression).ifPresent(list -> {
    		users.addAll(list);
    	});
        return users;
    }
	
}
