var	stompClient	=	null;

// Connect to Websocket
function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/tweets', function (tweets) {
            showTweets(JSON.parse(tweets.body).content);
        });
    });
}

// Disconnect to Websocket
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function disconnect(){
	if	(stompClient	!=	null) {
		stompClient.disconnect();
	}
	setConnected(false);
}

function wsSendTweet(body)	{
	console.log("Tweet for Websocket:", body);
	stompClient.send("/app/post",{}, body);
}

function showTweets(message) {
	location.reload();
}
