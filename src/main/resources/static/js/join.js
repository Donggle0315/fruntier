window.onload = () => {
    document.getElementById("joinSubmitButton").addEventListener("click", () => {
        let formData = new FormData(document.getElementById("joinForm"));
        let send_object = JSON.stringify(Object.fromEntries(formData));
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