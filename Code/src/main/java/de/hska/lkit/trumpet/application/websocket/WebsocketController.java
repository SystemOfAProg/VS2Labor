package de.hska.lkit.trumpet.application.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import de.hska.lkit.trumpet.application.model.Tweet;

@Controller
public class WebsocketController {
	
	@MessageMapping("/post")
    @SendTo("/topic/tweets")
    public NewTweetMessage greeting(NewTweetMessage message) throws Exception {
        return new NewTweetMessage(message.getUsername(), message.getMessage());
    }
	
	private void log(String debuginfo) {
		System.out.println("[Websocket-Controller]: " + debuginfo);
	}
	
}
