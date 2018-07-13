function toggleFollowButton(currentUser, userToFollow) {
	hidePost(currentUser, userToFollow);
	if (userAlreadyFollowing(currentUser, userToFollow)) {
		this.showUnfollow();
	} else {
		this.showFollow();
	}
}

function hidePost(currentUser, userToFollow) {
	if(currentUser.trim() !== userToFollow.trim()) {
		$('.profilePagePostsWrapper').hide();
	}
}

function userAlreadyFollowing(currentUser, userToFollow) {
	currentUser = currentUser.trim();
	userToFollow = userToFollow.trim();
	var requestURL = "http://localhost:8080/isFollowing";
	var requestBody = JSON.stringify({ "currentUser": currentUser, "userToFollow": userToFollow });
	$.ajax({
        type: 'POST',
        url: requestURL,
        contentType: 'application/json',
        data: requestBody,
	    dataType: 'json',
        success: function (body) {
        	handleButton(body);
        },
        error: function(body) {
        	// Lands in here even with status-code 200 !
        	handleButton(body);
	    }
    });
}

function handleButton(body) {
	if(body) {
		showUnfollow();
	} else {
		showFollow();
	}
}

function showFollow() {
	$('#followArea').slideDown(200);
	$('#unfollowArea').slideUp(200);
}

function showUnfollow() {
	$('#followArea').slideUp(200);
	$('#unfollowArea').slideDown(200);
}

function hideFollowUnfollow() {
	$('#followArea').hide();
	$('#unfollowArea').hide();
}

function follow(currentUser, userToFollow) {
	currentUser = currentUser.trim();
	userToFollow = userToFollow.trim();
	var requestURL = "http://localhost:8080/follow";
	var requestBody = JSON.stringify({ "currentUser": currentUser, "userToFollow": userToFollow });
	$.ajax({
        type: 'POST',
        url: requestURL,
        contentType: 'application/json',
        data: requestBody,
	    dataType: 'json',
        success: function () {
        	handleFollow();
        },
        error: function() {
        	// Lands in here even with status-code 200 !
        	handleFollow();
	    }
    });
}

function handleFollow() {
	showUnfollow();
}

function unfollow(currentUser, userToFollow) {
	var requestURL = "http://localhost:8080/unfollow";
	var requestBody = JSON.stringify({ "currentUser": currentUser, "userToFollow": userToFollow });
	$.ajax({
        type: 'POST',
        url: requestURL,
        contentType: 'application/json',
        data: requestBody,
	    dataType: 'json',
        success: function () {
        	handleUnfollow();
        },
        error: function(){
        	// Lands in here even with status-code '200' !
        	handleUnfollow();
	    }
    });
}

function handleUnfollow() {
	showFollow();
}