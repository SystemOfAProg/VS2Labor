function trumpIt(username) {
	event.preventDefault();
	var postContent = $('#postInputArea').val();
	if(postContent && (postContent.length > 0)){
		var requestURL = "http://localhost:8080/trumpIt";
		var requestBody = JSON.stringify({ "username": username, "message": postContent });
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: requestBody,
		    dataType: 'json',
	        success: function () {
	        	handlePost();
	        },
	        error: function(){
	        	// Lands in here even with status-code 200 !
	        	handlePost();
		    }
	    });
	} else {
		alert("The Post could not be posted. Please enter a valid message.");
	}
}

function handlePost() {
	location.reload();
}
