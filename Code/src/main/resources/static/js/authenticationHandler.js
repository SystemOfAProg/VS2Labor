function login(username, password) {
	event.preventDefault();

	var username = $('#login-username-field').val();
	var password = $('#login-password-field').val();
	if(username && password){
//		var requestURL = "http://localhost:8080/api/login?username=" + username +"&password=" + password;
		var requestURL = "http://localhost:8080/api/login";
		var requestBody = JSON.stringify({ "username": username, "password": password });
		
//		$.post(requestURL,requestBody, function(){
//			handleLogin();
//		})
		
		
		
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: requestBody,
		    dataType: 'json',
	        success: function () {
	        	handleLogin(data);
	        },
	        error: function(){
		        console.error("Login with credentials",username,",",password,"failed.");
		    }
	    });
	} else {
		console.error("Login failed. Please enter valid credentials.");
	}
}

function register(username, password) {
	event.preventDefault();

	var username = $('#register-username-field').val();
	var password = $('#register-password-field').val();
	if(username && password){
//		var requestURL = "http://localhost:8080/register?username=" + username +"&password=" + password;
		var requestURL = "http://localhost:8080/register";
		var requestBody = JSON.stringify({ "username": username, "password": password });
		console.log(requestURL);
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: requestBody,
		    dataType: 'json',
	        success: function (data) {
	        	handleLogin(data);
	        },
	        error: function(){
		        console.error("Registration with credentials",username,",",password,"failed.");
		    }
	    });
	} else {
		console.warn("Registration failed. Please enter valid credentials.");
	}
}

function handleLogin(data) {
	location.reload();
	console.log(data);
}
