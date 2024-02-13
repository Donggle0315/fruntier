window.onload = () => {
    let add_comment_btn = document.getElementById("add-comment-btn");
    add_comment_btn.addEventListener('click', addComment);

    // auto-resize textarea
    addAutoResize();
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

function editArticle(article_id) {

}

function deleteArticle(article_id) {
    if(!confirm("Are you sure you want to delete this article?")){
        return;
    }

    let cur_uri = window.location.pathname;
    fetchText(`${cur_uri}`, {
        method: 'DELETE'
    })
    .then(data => {
        window.location.href = "/community";
    })
    .catch(err => {
        console.error(err);
    })
}

function editComment(comment_id) {
    let cur_comment = document.getElementById(`comment-${comment_id}`);
    let textarea = cur_comment.querySelector(".comment-edit-content-text");
    cur_comment.querySelector(".comment-content").style.display = "none";
    cur_comment.querySelector(".comment-edit-content").style.display = "inline-flex";

    let cur_uri = window.location.pathname;
    cur_comment.querySelector("#comment-edit-submit-btn").addEventListener("click", () => {
        let send_object = {};
        send_object['content'] = textarea.value;
        let send_json = JSON.stringify(send_object);

        fetchText(`${cur_uri}/comment/${comment_id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': "application/json"
            },
            body: send_json
        })
        .then(data => {
            window.location.reload();
        })
        .catch(err => {
            console.error(err);
        })
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

function addAutoResize() {
  document.querySelectorAll('[data-autoresize]').forEach(function (element) {
    element.style.boxSizing = 'border-box';
    var offset = element.offsetHeight - element.clientHeight;
    element.style.height = 'auto';
    element.style.height = event.target.scrollHeight + offset + 'px';
    element.addEventListener('input', function (event) {
      event.target.style.height = 'auto';
      event.target.style.height = event.target.scrollHeight + offset + 'px';
    });
    element.removeAttribute('data-autoresize');
  });
}