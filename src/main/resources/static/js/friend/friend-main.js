window.onload = () => {
    document.getElementById('search-form').addEventListener('submit', searchFriend)
    refreshFriendRequest();
}

function refreshFriendRequest() {
    refreshFriendRequestSent();
    refreshFriendRequestIncoming();
    searchFriend(null);
}

function searchFriend(event) {
    if (event != null){
        event.preventDefault();
    }

    let query = document.getElementById('search-input').value;
    if (query == "") {
        return;
    }
    fetch(`/user/friend/search?key=${query}`, {
        method: "GET"
    })
        .then(data => data.json())
        .then(data => {
            let table = document.getElementById("friend-search-list");
            table.innerHTML = "<thead><tr><th></th><th></th></tr></thead>";
            table.innerHTML += "<tbody>";
            for(let i=0; i<data.length; i++){
                table.innerHTML += `
                <tr>
                    <td>${data[i]['username']}</td>
                    <td>
                        <button class="btn btn-primary" onclick=sendFriendRequest(${data[i]['id']})>친구신청</button>
                    </td>
                </tr>`;
            }
            table.innerHTML += "</tbody>";
        })
        .catch(err => console.error(err));
}

function sendFriendRequest(toId){
    fetch(`/user/friend/request`, {
        method: "POST",
        header: {
            "Content-Type": "text/plain",
        },
        body: toId
    })
        .then(data => data.text())
        .then(data => {
            alert("요청을 보냈습니다!")
            refreshFriendRequest();
        })
        .catch(err => {
            alert("뭔가 잘못되어서 실패했습니다");
            console.error(err)
        });
}

function refreshFriendRequestSent() {
    fetch(`/user/friend/request/sent`, {
        method: "GET",
    })
        .then(data => data.json())
        .then(data => {
            let table = document.getElementById("sent-request-table");
            table.innerHTML = "<thead><tr><th>이름</th><th></th></tr></thead>";
            table.innerHTML += "<tbody>";
            for(let i=0; i<data.length; i++){
                table.innerHTML += `
                <tr>
                    <td>${data[i]['username']}</td>
                    <td>
                        <button class="btn btn-primary" onclick=sendCancelFriendRequest(${data[i]['id']})>취소</button>
                    </td>
                </tr>`;
            }
            table.innerHTML += "</tbody>";
        })
        .catch(err => {
            console.error(err)
        });
}

function refreshFriendRequestIncoming() {
    fetch(`/user/friend/request/incoming`, {
        method: "GET",
    })
        .then(data => data.json())
        .then(data => {
            let table = document.getElementById("incoming-request-table");
            table.innerHTML = "<thead><tr><th>이름</th><th></th></tr></thead>";
            table.innerHTML += "<tbody>";
            for(let i=0; i<data.length; i++){
                table.innerHTML += `
                <tr>
                    <td>${data[i]['username']}</td>
                    <td>
                        <button class="btn btn-primary" onclick=sendAcceptFriendRequest(${data[i]['id']})>수락</button>
                        <button class="btn btn-danger" onclick=sendDeclineFriendRequest(${data[i]['id']})>거절</button>
                    </td>
                </tr>`;
            }
            table.innerHTML += "</tbody>";
        })
        .catch(err => {
            console.error(err)
        });
}

function sendDeclineFriendRequest(id){
    fetch(`/user/friend/request/decline`, {
        method: "POST",
        body: id
    })
        .then(data => data.text())
        .then(data => {
            alert("거절했습니다!")
            window.location.reload();
        })
        .catch(err => {
            console.error(err)
        });
}

function sendAcceptFriendRequest(id){
    fetch(`/user/friend/request/accept`, {
        method: "POST",
        body: id
    })
        .then(data => data.text())
        .then(data => {
            alert("수락했습니다!")
            window.location.reload();
        })
        .catch(err => {
            console.error(err)
        });
}

function sendCancelFriendRequest(id) {

    fetch(`/user/friend/request/cancel`, {
        method: "POST",
        body: id
    })
        .then(data => data.text())
        .then(data => {
            alert("취소했습니다!")
            window.location.reload();
        })
        .catch(err => {
            console.error(err)
        });
}