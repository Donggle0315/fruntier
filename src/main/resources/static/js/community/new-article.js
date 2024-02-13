window.onload = () => {
    let newArticleBtn = document.getElementById("article-submit-btn");
    newArticleBtn.addEventListener("click", submitNewArticle);
}

async function fetchJson(path, {method, headers, body}) {
    return fetch(path, {
        method: method,
        headers: headers,
        body: body
    })
    .then(response => {
            if(response.ok){
                return response.json();
            }
            else if(response.redirected){
                window.location.href = `${response.url}?not_logged_in=true`;
            }
            else{
                throw new Error("Not Found");
            }
        })
}

function submitNewArticle() {
    let newArticleForm = document.getElementById("article-form");

    let newArticleFormData = new FormData(newArticleForm);

    let sendObject = Object.fromEntries(newArticleFormData);
    sendObject['status'] = 'normal';

    let sendJson = JSON.stringify(sendObject);

    fetchJson('/community/article/new', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: sendJson
    })
        .then(data => {
            console.log(data);
            window.location.replace("/community");
        })
        .catch(error => {
            console.error(error);
            window.location.replace("/user/login");
        });
}

function editArticle(){
    let newArticleForm = document.getElementById("article-form");

    let newArticleFormData = new FormData(newArticleForm);

    let sendObject = Object.fromEntries(newArticleFormData);
    sendObject['status'] = 'normal';

    let sendJson = JSON.stringify(sendObject);

    let cur_uri = window.location.pathname;
    fetchJson(cur_uri, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: sendJson
    })
        .then(data => {
            console.log(data);
            window.location.replace("/community");
        })
        .catch(error => {
            console.error(error);
            window.location.replace("/user/login");
        });
}
