'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var receiver = null;

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    receiver = document.querySelector('#receiver').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

// on connect
function onConnected() {
    console.log(receiver);
//    // Construct the full URL
//    const baseUrl = '/chats/history';
//    const params = new URLSearchParams({ receiverName: receiver, senderName: username });
//    const fullUrl = `${baseUrl}?${params.toString()}`;
//    // Fetch chat history from the server
//    fetch(fullUrl)
//        .then(response => response.json())
//        .then(data => {
//            // Iterate through chat history and display each message
//            data.forEach(message => {
//                displayMessage(message);
//            });
//        })
//        .catch(error => {
//            console.error('Error fetching chat history:', error);
//        });



    // Subscribe to the Public Topic
//    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);

    // Tell the server about the new user
//    stompClient.send("/app/chat.addUser",
//        {},
//        JSON.stringify({sender: username, type: 'JOIN'})
//    );

    connectingElement.classList.add('hidden');
}


// on connect


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            senderId: username,
            content: messageInput.value,
            receiverId: receiver,
            type: 'CHAT'
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}
// herer

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log(message);
    displayMessage(message);
}

function displayMessage(message) {
    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


// here


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)