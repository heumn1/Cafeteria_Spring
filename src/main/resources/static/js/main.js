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
    //TODO СДЕЛАТЬ НОРМ ДИНАМИКУ ЛОЛ
    location.reload();
}

