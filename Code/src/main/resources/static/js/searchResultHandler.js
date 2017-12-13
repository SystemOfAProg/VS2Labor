// =======================================================================================
// For Profile-Page Use
// =======================================================================================

function searchForUsersProfile() {
	var searchExpression = $('#searchFieldInput').val();
	if(searchExpression && (searchExpression.length != 0)){
		var requestURL = 'http://localhost:8080/searchUser';
		var requestBody = JSON.stringify({ "expression": searchExpression });
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: requestBody,
		    dataType: 'json',
	        success: function (data) {
	        	addResponseToTemplateProfile(data);
	        },
	        error: function(){
		        console.error("An error happened on the server-site");
		    }
	    });
	} else {
		// Don't show any results when the Search-Expression is empty.
		var searchResultBody = $('#searchResultDialogBody');
		searchResultBody.html("");
	}
}

function addResponseToTemplateProfile(response){
	var searchResultBody = $('#searchResultDialogBody');
	searchResultBody.html("");
	var template = '';
	for(var user of response){
		console.log(user);
		var name = user['name'];
		var tagname = user['tagName'];
		if(name && tagname){
			var templatePart = buildTemplateFromProfile(name, tagname);
			if(templatePart) {
				template += templatePart;
			}
		}
	}
	searchResultBody.html(template);
}

function buildTemplateFromProfile(name, tagname) {
    var template = '';
    template += '<a href="'+ escape(name.trim()) +'">';
    template += '	<div class="userListElement">';
    template += '		<div class="listProfileImage">';
    template += '			<img src="/images/profileImage.jpg" class="listProfileImage"/>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<p class="listFullName">' + name + '</p>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<p class="listTagName">' + tagname + '</p>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<i class="material-icons">keyboard_arrow_right</i>';
    template += '		</div>';
    template += '	</div>';
    template += '</a>';
	return template;
}

// =======================================================================================
// For non Profile-Page use -> Redirect is different and i am too lazy to parameterize it
// =======================================================================================

function searchForUsersNonProfile() {
	var searchExpression = $('#searchFieldInput').val();
	if(searchExpression && (searchExpression.length != 0)){
		var requestURL = 'http://localhost:8080/searchUser';
		var requestBody = JSON.stringify({ "expression": searchExpression });
		$.ajax({
	        type: 'POST',
	        url: requestURL,
	        contentType: 'application/json',
	        data: requestBody,
		    dataType: 'json',
	        success: function (data) {
	        	addResponseToTemplateNonProfile(data);
	        },
	        error: function(){
		        console.error("An error happened on the server-site");
		    }
	    });
	} else {
		// Don't show any results when the Search-Expression is empty.
		var searchResultBody = $('#searchResultDialogBody');
		searchResultBody.html("");
	}
}

function addResponseToTemplateNonProfile(response){
	var searchResultBody = $('#searchResultDialogBody');
	searchResultBody.html("");
	var template = '';
	for(var user of response){
		console.log(user);
		var name = user['name'];
		var tagname = user['tagName'];
		if(name && tagname){
			var templatePart = buildTemplateFromNonProfile(name, tagname);
			if(templatePart) {
				template += templatePart;
			}
		}
	}
	searchResultBody.html(template);
}

function buildTemplateFromNonProfile(name, tagname) {
    var template = '';
    template += '<a href="profile/'+ escape(name.trim()) +'">';
    template += '	<div class="userListElement">';
    template += '		<div class="listProfileImage">';
    template += '			<img src="/images/profileImage.jpg" class="listProfileImage"/>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<p class="listFullName">' + name + '</p>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<p class="listTagName">' + tagname + '</p>';
    template += '		</div>';
    template += '		<div class="listTextWrapper">';
    template += '			<i class="material-icons">keyboard_arrow_right</i>';
    template += '		</div>';
    template += '	</div>';
    template += '</a>';
	return template;
}
