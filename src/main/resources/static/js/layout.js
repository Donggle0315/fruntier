document.addEventListener('DOMContentLoaded', function() {
    console.log("onload logout function");
    // Check if "authToken" cookie exists
    if (getCookie('authToken')) {
        // If the cookie exists, make the logout button visible
        document.getElementById('logoutButton').style.display = 'block';
    }else{
        document.getElementById('logoutButton').style.display = 'block';

    }
});

function getCookie(name) {
    const cookies = document.cookie.split(';'); // Split the cookie string into an array of individual cookies
    for (let i = 0; i < cookies.length; i++) {
        const cookiePair = cookies[i].trim().split('='); // Trim any leading or trailing whitespace
        // Check if this cookie starts with the name we're looking for
        console.log("cookiePair = " + cookiePair)
        if (cookiePair[0]===name) {
            // If it does, return true to indicate the cookie exists
            return true;
        }
    }
    // If the cookie was not found, return false
    return false;
}