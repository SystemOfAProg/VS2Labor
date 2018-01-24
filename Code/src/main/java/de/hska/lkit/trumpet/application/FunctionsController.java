package de.hska.lkit.trumpet.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.hska.lkit.trumpet.application.model.AuthenticationRequestBody;
import de.hska.lkit.trumpet.application.model.FollowRequestBody;
import de.hska.lkit.trumpet.application.model.NewPostRequestBody;
import de.hska.lkit.trumpet.application.model.User;
import de.hska.lkit.trumpet.application.model.UserSearchRequestBody;
import de.hska.lkit.trumpet.application.security.SecurityConfig;
import de.hska.lkit.trumpet.application.services.ServiceBundle;

@RestController
public class FunctionsController {

	/**
	 * Searches for a list of users with an regular expression. Returns an empty
	 * list when no user matches the expression
	 * 
	 * @param expression
	 *            Regular Expression
	 * @return List of Users
	 */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public List<User> searchForUsers(@RequestBody UserSearchRequestBody body) {
		String expression = body.expression;
		log("Search over REST-Controller for User with expression '" + expression + "'.");
		ServiceBundle service = new ServiceBundle();
		List<User> users = new ArrayList<>();
		service.searchForUsersByExpression(expression).ifPresent(list -> {
			users.addAll(list);
		});
		return users;
	}

	/**
	 * Authenticates an user with an given user and password. Returns Unauthorized,
	 * when username or password is not correct.
	 * 
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @return User and Password or Unauthorized
	 */
//	@RequestMapping(value = "/api/login?username={username:\\w+}&password={username:\\w+}", method = RequestMethod.POST)
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public Object login(@RequestBody AuthenticationRequestBody body ) {
		String username = body.username;
		char[] password = body.password.toCharArray();

//		SecurityConfig config = new SecurityConfig();
//		config.setSessionUser(username, String.valueOf(password));
//		System.out.println("SessionUser: " + config.getUsername());
		
		log("Login (username:'" + username + "', password:'" + String.valueOf(password) + "')");
		ServiceBundle service = new ServiceBundle();
		Optional<User> userOpt = service.loginUser(username, password);
		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			return new ResponseEntity<String>("Your credentials were not correct. Please try it again.",
					HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Authenticates an user with an given user and password. Returns Unauthorized,
	 * when username or password is not correct.
	 * 
	 * @param username
	 *            Unique Username that is supposed to be set
	 * @param password
	 *            Password that is supposed to be set
	 * @return User and Password or Unauthorized
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object register(@RequestBody AuthenticationRequestBody body) {
		String username = body.username;
		char[] password = body.password.toCharArray();
		log("New User registration ( username:'" + username + "' , password: '" + String.valueOf(password) + "' )");
		ServiceBundle service = new ServiceBundle();
		Optional<User> userOpt = service.registerUser(username, password);
		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			return new ResponseEntity<String>(
					"The registration failed. Does there maybe exist an user with the same Name?", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Adds a new post to the posting list of the user with the given username.
	 * 
	 * @return 200 when OK, 409 when post could not be added
	 */
	@RequestMapping(value = "/trumpIt", method = RequestMethod.POST)
	public Object post(@RequestBody NewPostRequestBody body) {
		String message = body.message;
		String username = body.username;
		log("New Post has been added (content:'" + message + "', user:'" + username + "' )");
		ServiceBundle service = new ServiceBundle();
		try {
			service.post(username, message);
		} catch (Exception e) {
			return new ResponseEntity<String>("The Post could not be posted.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("The Post has succesfully been added.", HttpStatus.OK);
	}

	/**
	 * Follow an User.
	 * 
	 * @return 200 when OK, 409 when post could not be added
	 */
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public Object follow(@RequestBody FollowRequestBody body) {
		String currentUser = body.currentUser;
		String userToFollow = body.userToFollow;
		log("User '" + userToFollow + "' will be added to List of Followers of User '" + currentUser + "'.");
		ServiceBundle service = new ServiceBundle();
		try {
			service.follow(currentUser, userToFollow);
		} catch (Exception e) {
			String message = "The user " + currentUser + " could not follow " + userToFollow + ".";
			return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
		}
		String message = "The user " + currentUser + " successfully follows " + userToFollow + ".";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	/**
	 * Is an user already follwing an other.
	 * 
	 * @return 200 when OK, 409 when post could not be added
	 */
	@RequestMapping(value = "/isFollowing", method = RequestMethod.POST)
	public Object isFollowing(@RequestBody FollowRequestBody body) {
		String followingUserName = body.currentUser.trim();
		String userToFollowName = body.userToFollow.trim();
		boolean userAlreadyFollowing = false;
		log("Is User '" + userToFollowName + "' already following '" + followingUserName + "'.");
		ServiceBundle service = new ServiceBundle();
		try {
			User followingUser = null;
			User userToFollow;
			Optional<User> followingUserOpt = service.getUserByUsername(followingUserName);
			Optional<User> userToFollowOpt = service.getUserByUsername(userToFollowName);
			if(followingUserOpt.isPresent()){
				followingUser = followingUserOpt.get();
			}
			if(userToFollowOpt.isPresent()){
				userToFollow = userToFollowOpt.get();
			}
			Optional<List<User>> followingsOpt = service.getFollowersOfUsers(followingUser);
			if (followingsOpt.isPresent() ) {
				List<User> followings = followingsOpt.get();
				for (User user:followings) {
					if(user.getName().equals(userToFollowName)) {
						userAlreadyFollowing = true;
					}
				}
			} else {
				throw new IllegalStateException();
			}
		} catch (Exception e) {
			String message = "An Error happened while looking up if user " + userToFollowName + " already follows " + followingUserName + ".";
			return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
		};
		return new ResponseEntity<String>(String.valueOf(userAlreadyFollowing), HttpStatus.OK);
	}

	/**
	 * Unfollow an User.
	 * 
	 * @return User and Password or Unauthorized
	 */
	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	public Object unfollow(@RequestBody FollowRequestBody body) {
		String currentUser = body.currentUser;
		String userToFollow = body.userToFollow;
		log("User '" + userToFollow + "' will be removed from List of Followers of User '" + currentUser + "'.");
		ServiceBundle service = new ServiceBundle();
		try {
			service.unfollow(currentUser, userToFollow);
		} catch (Exception e) {
			String message = "The user " + currentUser + " could not unfollow " + userToFollow + ".";
			return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
		}
		String message = "The user " + currentUser + " does not follow " + userToFollow + " anymore.";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	private void log(String debuginfo) {
		System.out.println("[Rest-Controller]: " + debuginfo);
	}

}