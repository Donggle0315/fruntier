window.onload = () => {
    document.getElementById('search-form').addEventListener('submit', searchFriend)
    refreshFromFriendRequest();
    refreshToFriendRequest();
}

function searchFriend(event) {
    event.preventDefault();

    let query = document.getElementById('search-input').value;
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
        .then(data => () => {
            alert("요청을 보냈습니다!")
        })
        .catch(err => {
            alert("뭔가 잘못되어서 실패했습니다");
            console.error(err)
        });
}

function refreshFromFriendRequest() {
    fetch(`/user/friend/request/from`, {
        method: "GET",
    })
        .then(data => data.json())
        .then(data => {
            let table = document.getElementById("from-request-table");
            table.innerHTML = "<thead><tr><th>이름</th><th></th></tr></thead>";
            table.innerHTML += "<tbody>";
            for(let i=0; i<data.length; i++){
                table.innerHTML += `
                <tr>
                    <td>${data[i]['username']}</td>
                    <td>
                        <button class="btn btn-primary" onclick=sendAcceptFriendRequest(${data[i]['id']})>수락</button>
                    </td>
                </tr>`;
            }
            table.innerHTML += "</tbody>";
        })
        .catch(err => {
            console.error(err)
        });
}

function refreshToFriendRequest() {
    fetch(`/user/friend/request/to`, {
        method: "GET",
    })
        .then(data => data.json())
        .then(data => {
            let table = document.getElementById("to-request-table");
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
