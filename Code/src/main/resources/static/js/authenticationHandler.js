function login(username, password) {
	event.preventDefault();

	var username = $('#login-username-field').val();
	var password = $('#login-password-field').val();
	if(username && password){
	var requestURL = "http://localhost:8080/login?username=" + escape(username) + "&password=" + escape(password);
		console.log(requestURL);
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        data: { get_param: 'value' },
	        success: function (data) {
	        	handleLogin(data);
	        }
	    });
	} else {
		console.warn("Login failed");
	}
}

function register(username, password) {
	event.preventDefault();

	var username = $('#register-username-field').val();
	var password = $('#register-password-field').val();
	if(username && password){
	var requestURL = "http://localhost:8080/register?username=" + escape(username) + "&password=" + escape(password);
		console.log(requestURL);
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        data: { get_param: 'value' },
	        success: function (data) {
	        	handleLogin(data);
	        }
	    });
	} else {
		console.warn("Registration failed");
	}
}

function handleLogin(data) {
	console.log(data);
}
