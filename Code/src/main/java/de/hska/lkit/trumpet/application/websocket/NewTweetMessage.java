package de.hska.lkit.trumpet.application.websocket;

public class NewTweetMessage {
	
	private String username;
	private String message;

	public NewTweetMessage () {
		this.username = "";
		this.message = "";
	}
	
	public NewTweetMessage (String username, String message) {
		this.username = username;
		this.message = message;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setName(String username) {
		this.username = username;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
