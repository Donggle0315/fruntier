window.onload = () => {
    let add_comment_btn = document.getElementById("add-comment-btn");
    add_comment_btn.addEventListener('click', addComment);
}

async function fetchText(path, {method, headers, body}) {
    return fetch(path, {
        method: method,
        headers: headers,
        body: body
    })
    .then(response => {
            if(response.ok){
                return response.text();
            }
            else if(response.redirected){
                window.location.href = response.url + "?not_logged_in=true";
            }
            else{
                throw new Error("Not Found");
            }
        })
}

function addComment(){
    let comment_text = document.getElementById("comment-textarea");
    let send_object = {}
    send_object['content'] = comment_text.value;
    let send_json = JSON.stringify(send_object);
 
    let cur_uri = window.location.pathname;

    fetchText(`${cur_uri}/comment/new`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: send_json
    })
    .then(data => {
            window.location.reload();
        })
    .catch(err => {
            console.error(err);
        })
}

function deleteComment(comment_id){
    let cur_uri = window.location.pathname;
    fetchText(`${cur_uri}/comment/${comment_id}`, {
        method: 'DELETE',
    })
    .then(data => {
        window.location.reload();
    })
    .catch(err => {
        console.error(err);
    })
}