
function makeEditable() {
    document.querySelectorAll('#userInfoForm input, #userInfoForm textarea').forEach(function(input) {
        input.removeAttribute('readonly');
    });
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('submitButton').style.display = 'inline-block';
}

function makeReadOnly(){
    document.querySelectorAll('#userInfoForm input, #userInfoForm textarea').forEach(function(input) {
        input.setAttribute('readonly','true');
    });
    document.getElementById('editButton').style.display = 'inline-block';
    document.getElementById('submitButton').style.display = 'none';
}


function submitUserInfo(){
    let formData;
    formData = new FormData(document.getElementById("userInfoForm"));
    let obj = Object.fromEntries(formData);

    for(let key in obj){
        if(obj[key].trim() == ""){
            window.alert("빈 칸을 채워주세요.");
            return;
        }
    }

    let send_object = JSON.stringify(Object.fromEntries(formData));
    console.log(send_object);

    fetch('/user/submitUserInfo',{
        method : 'POST',
        headers : {
            'Content-Type': 'application/json',
        },
        body: send_object
    })
        .then(response=>{
            if(!response.ok){
                if(response.status == 401){//unauthorized access, need to logout.
                    throw new Error('Unauthorized access');
                }else{
                    throw new Error('Network Response was not Ok' + response.statusText);
                }
            }
        })
        .then(data=>{
            console.log('Update Successful');
            makeReadOnly();
        })
        .catch(error=>{
            if (error.message === 'Unauthorized access') {
                window.location.href = '/logout';

                // Prevent navigating back to the previous page
                window.history.pushState(null, '', '/login');
                window.addEventListener('popstate', function () {
                    window.history.pushState(null, '', '/login');
                });
            } else {
                // Handle other errors
                console.log(error.message);
                window.alert(error.message);
            }
        });


}