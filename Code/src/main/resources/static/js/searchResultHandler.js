function searchForUsers() {
	var searchExpression = $('#searchFieldInput').val();
	if(searchExpression){
		var requestURL = 'http://localhost:8080/searchUser?expression=' + escape(searchExpression);
		console.log(requestURL);
		$.ajax({ 
	        type: 'GET', 
	        url: requestURL, 
	        data: { get_param: 'value' }, 
	        success: function (data) { 
	        	addResponseToTemplate(data);
	        }
	    });
	} else {
		console.warn("Value of Searchfield could not be read.");
	}
}

function addResponseToTemplate(response){
	var searchResultBody = $('#searchResultDialogBody');
	searchResultBody.html("");
	var template = '';
	for(var user of response){
		console.log(user);
		var name = user['name'];
		var tagname = user['tagName'];
		if(name && tagname){
			var templatePart = buildTemplateFrom(name, tagname);
			if(templatePart) {
				template += templatePart;
			}
		}
	}
	searchResultBody.html(template);
}

function buildTemplateFrom(name, tagname) {
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
