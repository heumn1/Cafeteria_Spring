'use strict';

var stompClient = null;

connect();


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}


function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
}


function onError(error) {
}

function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);

    if(location.pathname === "/cook/orders" && message.type === "Cook")
    {
        location.reload();
    }
    if(location.pathname === "/delivery" && message.type === "Delivery")
    {
        location.reload();
    }
}

