var newPostBody = "";

function trumpIt(username) {
	event.preventDefault();
	var postContent = $('#postInputArea').val();
	if(postContent && (postContent.length > 0)){
		var requestURL = "http://localhost:8080/trumpIt";
		newPostBody = JSON.stringify({ "username": username, "message": postContent });
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: newPostBody,
		    dataType: 'json',
	        success: function () {
	        	handlePost(newPostBody);
	        },
	        error: function(){
	        	// -> Lands in here even with status-code 200 !
	        	handlePost(newPostBody);
		    }
	    });
	} else {
		alert("The Post could not be posted. Please enter a valid message.");
	}
}

// Notify Websocket about new Tweet
function handlePost(body) {
	wsSendTweet(body);
}
