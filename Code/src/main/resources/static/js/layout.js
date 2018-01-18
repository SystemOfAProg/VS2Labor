function layoutMain() {
	$('#searchbar').hide();
	$('#logoSmall').hide();
	window.onscroll = handleTitleBarPosition;
	// Connect to Websocket
	connect();
}

function openSearchBar() {
	$('#searchbar').slideDown(200);
	$('#navigationbar').slideUp(200);
}

function closeSearchBar() {
	$('#searchbar').slideUp(200);
	$('#navigationbar').slideDown(200);
}

function handleTitleBarPosition() {
	var offset = $('#titlecontainer').outerHeight();
    if (window.pageYOffset >= offset){
        $('#topbar').css({'position': 'fixed', 'right': '0px', 'top': '0px', 'box-shadow': '0px 0px 10px rgba(0,0,0,0.2)'});
        $('#contentwrapper').css({'paddingTop': 'calc(50px + 0.5em)'});
        $('#logoSmall').slideDown(200);
    }
    else {
        $('#topbar').css({'position': '', 'right': '', 'top': '', 'box-shadow': ''});
        $('#contentwrapper').css({'paddingTop': '0.5em'});
        $('#logoSmall').slideUp(200);
    }
}
