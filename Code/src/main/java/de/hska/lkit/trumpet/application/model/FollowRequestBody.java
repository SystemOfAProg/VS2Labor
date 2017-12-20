package de.hska.lkit.trumpet.application.model;

/**
 * Model for Request-Body for Following and Unfollowing and User.
 * <b>currentUser</b> follows <b>userToFollow</b> .
 */
public class FollowRequestBody {

	public String currentUser;
	public String userToFollow;

}
