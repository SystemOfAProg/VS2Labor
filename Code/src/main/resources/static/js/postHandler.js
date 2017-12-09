function trumpIt(username) {
	event.preventDefault();
	console.log("Post-Button pressed");
	var postContent = $('#postInputArea').val();
	if(postContent){
		var requestURL = "http://localhost:8080/trumpIt?username=" + escape(username) + "&message=" + escape(postContent);
		console.log(requestURL);
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        data: { get_param: 'value' },
	        success: function (data) {
	        	handlePost(data);
	        }
	    });
	} else {
		console.warn("Value of Searchfield could not be read.");
	}
}

function handlePost(data) {
	console.log(data);
	location.reload();
}
