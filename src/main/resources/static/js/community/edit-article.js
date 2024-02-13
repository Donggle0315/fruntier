window.onload = () => {
    let editArticleBtn = document.getElementById("article-submit-btn");
    editArticleBtn.addEventListener("click", editArticle);
}

async function fetchText(path, {method, headers, body}) {
    let response = await fetch(path, {
        method: method,
        headers: headers,
        body: body
    });

    if(response.redirected){
        if(response.url.includes("/user/login")){
            window.location.replace(`${response.url}?not_logged_in=true`);
        }
        else {
            console.log(response.url);
            window.location.replace(`${response.url}`);
        }
    }
    else if(response.ok){
        return response.text();
    }
    else{
        throw new Error("Not Found");
    }
}

function editArticle(){
    let newArticleForm = document.getElementById("article-form");

    let newArticleFormData = new FormData(newArticleForm);

    let sendObject = Object.fromEntries(newArticleFormData);
    sendObject['status'] = 'normal';

    let sendJson = JSON.stringify(sendObject);

    let cur_uri = window.location.pathname;

    fetchText(`${cur_uri}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: sendJson
    })
        .then(data => {
        })
        .catch(error => {
            console.error(error);
        });
}
