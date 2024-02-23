function sendGetRequest(routeId) {
    // routeId를 사용하여 동적 URL 생성
    var url = 'route/find?routeId=' + routeId;

    // GET 요청 보내기
    fetch(url, {
        method: 'GET'
    })
    .then(response => {
        // 응답 처리
        if (response.ok) {
            console.log('GET request successful');
            // 여기에서 응답을 처리하거나 필요한 작업을 수행할 수 있습니다.
        } else {
            console.error('GET request failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    // 버튼 클릭 시 record/update 요청을 보내는 함수
    var updateRecordButton = document.getElementById("updateRecordButton");
    if (updateRecordButton) {
        updateRecordButton.addEventListener("click", function() {
            // routeId 값을 가져옴
            var routeId = parseInt(document.querySelector("#routeDetails span").innerText);
            if (!isNaN(routeId)) {
                // record/update 요청 보내기
                window.location.href = "/record/update?routeId=" + routeId;
            } else {
                alert("Route ID not available.");
            }
        });
    }
});
