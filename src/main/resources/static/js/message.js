/**
 * 1. select a user to chat with
 */
function loadChatWith(username){

    let send_object = JSON.stringify({username : username});

    fetch('/message/loadChatWith',{
        method : 'POST',
        headers : {
            'Content-Type': 'application/json',
        },
        body: send_object
    }).then(response=>{
        checkResponseThrowError(response);
    }).then(data=>{

    })

}

function checkResponseThrowError(response){

}