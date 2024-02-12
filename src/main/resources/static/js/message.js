
/**
 * 1. select a user to chat with
 */

var currentOpponentName = null;
var currentOpponentUsername = null;

function loadChat(element){
    var loginUsername = element.getAttribute('data-login-username');
    var opponentUsername = element.getAttribute('data-opponent-username');

    currentOpponentUsername = element.getAttribute('data-opponent-username');
    currentOpponentName = element.getAttribute('data-opponent-name');

    makeSendBlockVisible();
    loadChatWith(loginUsername,opponentUsername);
    setButtonInfo(opponentUsername);

    console.log('loadChat username  :' + loginUsername)
    console.log('loadChat opponentusername  :' + opponentUsername)
}

function loadChatWith(loginUsername, opponentUsername) {
    // Adjusted to include both loginUserId and opponentId
    let send_object = JSON.stringify({
        loginUsername: loginUsername,
        opponentUsername: opponentUsername
    });

    fetch('/message/loadChatWith', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: send_object
    }).then(response => response.json()) // Correctly return the promise here
        .then(data => {
            console.log('Messages:', data);
            displayMessages(data);
        }).catch(error => {
        // Handle any errors here
        console.error('Error:', error);
    });
}

function displayMessages(messages) {
    const messageList = document.getElementById('message-list');
    messageList.innerHTML = ''; // Clear existing messages
    document.getElementById("messageBox").value = ""; //clear textbox

    if (messages.length == 0) {
        messageList.innerHTML = '<li class="list-group-item">No messages found.</li>';
    } else {
        messages.forEach((msg) => {
            const li = document.createElement('li');
            li.classList.add('list-group-item');
            const time = getTimeFromMessage(msg);
            li.textContent = msg.sender.name +'('+msg.sender.username +')' + ': '+ msg.content + '(' + time + ')' ;



            messageList.appendChild(li);
        });
    }
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

    let send_object = JSON.stringify({
        opponentUsername : opponentUsername,
        content : content
    });

    fetch('/message/sendMessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: send_object
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

