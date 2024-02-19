
/**
 * 1. select a user to chat with
 */

var currentOpponentName = null;
var currentOpponentUsername = null;
var currentLoginUserName;

function loadChat(element){
    var loginUsername = element.getAttribute('data-login-username');
    var opponentUsername = element.getAttribute('data-opponent-username');

    currentOpponentUsername = element.getAttribute('data-opponent-username');
    currentOpponentName = element.getAttribute('data-opponent-name');

    makeSendBlockVisible();
    makeChatBlockActive();
    makeSelectedPersonRed(element);
    loadChatWith(loginUsername,opponentUsername);
    setButtonInfo(opponentUsername);

    console.log('loadChat username  :' + loginUsername)
    console.log('loadChat opponentusername  :' + opponentUsername)
}

function loadChatWith(loginUsername, opponentUsername) {
    // Adjusted to include both loginUserId and opponentId
    let loadChatUserInfo = JSON.stringify({
        loginUsername: loginUsername,
        opponentUsername: opponentUsername
    });

    fetch('/message/loadChatWith', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: loadChatUserInfo
    }).then(response => response.json()) // Correctly return the promise here
        .then(data => {
            console.log('Messages:', data);
            displayMessages(data);
        }).catch(error => {
        // Handle any errors here
        console.error('Error:', error);
    });
}

// function displayMessages(messages) {
//     const messageList = document.getElementById('message-list');
//     messageList.innerHTML = ''; // Clear existing messages
//     document.getElementById("messageBox").value = ""; //clear textbox
//
//     if (messages.length == 0) {
//         messageList.innerHTML = '<li class="list-group-item">No messages found.</li>';
//     } else {
//         messages.forEach((msg) => {
//             const li = document.createElement('li');
//             li.classList.add('list-group-item');
//             const time = getTimeFromMessage(msg);
//             li.textContent = msg.sender.name +'('+msg.sender.username +')' + ': '+ msg.content + '(' + time + ')' ;
//
//
//
//             messageList.appendChild(li);
//         });
//     }
// }
function displayMessages(messages) {
    const messageList = document.getElementById('message-list');
    messageList.innerHTML = ''; // Clear existing messages
    document.getElementById("messageBox").value = ""; //clear textbox

    if (messages.length === 0) {
        const noMessagesDiv = document.createElement('div');
        noMessagesDiv.classList.add('chat-message', 'message-incoming'); // Style as needed
        noMessagesDiv.textContent = 'No messages found.';
        messageList.appendChild(noMessagesDiv);
    } else {
        messages.forEach((msg) => {
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('chat-message');
            // Determine if the message is incoming or outgoing
            if (msg.sender.username === currentOpponentUsername) {
                messageDiv.classList.add('message-incoming');
            } else {
                messageDiv.classList.add('message-outgoing');
            }
            const time = getTimeFromMessage(msg); // Assuming this function returns a time string
            messageDiv.textContent = `${msg.sender.name} (${msg.sender.username}): ${msg.content} (${time})`;

            messageList.appendChild(messageDiv);
        });
    }
    scrollToBottom(messageList);
}


function sendMessage(element){
    var loginUsername = element.getAttribute('data-login-username');
    var opponentUsername = element.getAttribute('data-opponent-username');

    let content = document.getElementById("messageBox").value

    console.log('content : '+content);
    console.log('opponentUsername: '+opponentUsername);

    //clear textbox
    document.getElementById("messageBox").value = "";
    console.log('content after emptying : '+ document.getElementById("messageBox").value);

    let sentMessage = JSON.stringify({
        opponentUsername : opponentUsername,
        content : content
    });

    fetch('/message/sendMessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: sentMessage
    }).then(response => {
        response.json()
    }).then(data=>{
        console.log('Messages:',data)
        loadChatWith(loginUsername,opponentUsername);
    }).catch(error => {
        // Handle any errors here
        console.error('Error:', error);
    });
}


function checkResponseThrowError(response){

}

function setButtonInfo(opponentUsername){
    var submitBtn = document.getElementById('sendButton');
    submitBtn.setAttribute('data-opponent-username', opponentUsername);

}
function makeSendBlockVisible() {
    document.getElementById('divMessageBox').style.display = 'block';
}

function getTimeFromMessage(msg){
    const msgTime = new Date(msg.time);
    const hours = msgTime.getHours().toString().padStart(2,'0')
    const minutes = msgTime.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
}
function makeChatBlockActive(){
    document.getElementById("chatLogo").innerText = "Chat with (" + currentOpponentName + ")"
}

function scrollToBottom(element) {
    element.scrollTop = element.scrollHeight;
}

function makeSelectedPersonRed(element){
    var ulElement = document.getElementById("userList");

    // Iterate through each li element
    ulElement.querySelectorAll('li').forEach(function(liElement) {
        liElement.classList.remove('selected');
    });

    element.classList.add('selected');
}


// send message when hit enter.
document.getElementById('messageBox').addEventListener('keydown', function(event) {
    if (event.key === 'Enter' && !event.shiftKey) { // Checks if Enter is pressed without the Shift key
        event.preventDefault(); // Prevents the default action (form submission)
        sendMessage(document.getElementById('sendButton')); // Calls the sendMessage function
    }
});
