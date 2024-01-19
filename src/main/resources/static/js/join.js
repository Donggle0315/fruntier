window.onload = () => {
    let username_timeout;
    document.getElementById("username").addEventListener("input", () => {
        let username_warning_text = document.getElementById("username-warning-text");
        username_warning_text.innerHTML = "";
        clearTimeout(username_timeout);

        username_timeout = setTimeout(() => {

            check_username();
        }, 1000)
    })

    document.getElementById("joinSubmitButton").addEventListener("click", () => {
        let formData = new FormData(document.getElementById("joinForm"));
        let obj = Object.fromEntries(formData);
        for (let key in obj) {
            if (obj[key].trim() == "") {
                return;
            }
        }
        let send_object = JSON.stringify(Object.fromEntries(formData));
        console.log(send_object);
        fetch('/user/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: send_object
        })
            .then(response => response.text())
            .then((data) => {
                    if (data == "ok"){
                        console.log("OK?");
                        window.location.replace("/user/login");
                    }
                    else{
                        console.log("error");
                    }
                }
            )
            .catch((error) => console.error(error));
    })
}

function check_username() {
    let username = document.getElementById("username").value;
    let username_warning_text = document.getElementById("username-warning-text");
    let request = new Request(`/user/join/${username}/duplicate`, {
        method: "GET",
    })

    fetch(request)
        .then((response) => response.text())
        .then((data) => {
            console.log(data)
            if (data == "true") {
                username_warning_text.innerHTML = "This username has duplicate";
                username_warning_text.setAttribute("style", "color: red")
            }
            else {
                username_warning_text.innerHTML = "This username is appropriate";
                username_warning_text.setAttribute("style", "color: blue")
            }
        })
}