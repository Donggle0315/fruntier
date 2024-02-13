/* global variables */
var markerLatLng;
var vertexMap = [];
var result = [];
var response;

window.onload = function () {
    /* KaKao Map Container*/
    var mapContainer = document.getElementById('map');
    var mapOptions = {
        center : new kakao.maps.LatLng(37.5509442, 126.9410023), //at Sogang University
        level : 5 //map closing level
    };
    var map = new kakao.maps.Map(mapContainer, mapOptions);

    /* Marker Container */
    var markerOptions = {
        map : map
    };
    var marker = new kakao.maps.Marker(markerOptions);


    /* Mouse Click Event on the Map */
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        markerLatLng = mouseEvent.latLng;
        marker.setPosition(markerLatLng);
        var coordinateMessage = "Coordination => Lat : " + markerLatLng.getLat() + " Lng : " + markerLatLng.getLng();
        console.log(coordinateMessage);
    });

    /* Set Start Coordinate */
    document.getElementById("setStartVertex").addEventListener('click', setStartVertex);
    function setStartVertex() {
        setVertexMap("start");
    }

    /* Set End Coordinate */
    document.getElementById("setEndVertex").addEventListener('click', setEndVertex);
    function setEndVertex() {
        setVertexMap("end");
    }

    /* Update Start and End Vertex Map */
    function setVertexMap(title) {
        var coordinateIndex = vertexMap.findIndex(function (coordinate) {
            return coordinate.title === title;
        });

        if (coordinateIndex !== -1) vertexMap[coordinateIndex].latLng = markerLatLng;
        else vertexMap.push( {"title" : title, "latLng" : markerLatLng} );

        console.log(vertexMap);
    }

    /* Set Expected Distance */
    function getExpectedDistance() {
        var expectedDistance = document.getElementById("expectedDistance").value;
        if (!Number.isInteger(parseInt(expectedDistance))) {
            alert("Error: 뛰고자하는 거리를 입력해주세요.");
            return -1;
        }
        return expectedDistance;
    }

    /* Request the route to the Server */
    document.getElementById("generateRoute").addEventListener('click', requestServerGenerateRoute);
    function requestServerGenerateRoute() {
        var expectedDistance = getExpectedDistance();

        if (validateUserForm(expectedDistance) === false) {
            console.log(vertexMap);
            alert("Error: 모든 정보를 세팅해주세요.");
            return;
        }

        var sendingData = makeSendingDataJSON(expectedDistance);

        var serverContainer = openConnectionServer();

        communicateServer(serverContainer, sendingData, function (error, response) {
            if (error) {
                alert("Error: 서버 통신 중 오류가 발생했습니다.");
                return;
            }

            if (validateRecommendRoute(response) === false) {
                alert("Error: 추천 경로 생성 실패");
                return;
            }

            drawRouteOnMap(response);
        });
    }

    function validateUserForm(expectedDistance) {
        //not exist expected distance
        if (expectedDistance === -1) return false;

        const hasStartVertex = Array.from(vertexMap.values()).some(value => value.title === "start");
        const hasEndVertex = Array.from(vertexMap.values()).some(value => value.title === "end");

        if (!hasStartVertex || !hasEndVertex) return false;

        return true;
    }

    function makeSendingDataJSON(expectedDistance) {
        var data = JSON.stringify({
            expectedDistance: parseInt(expectedDistance),
            vertices: vertexMap.map(function (item) {
                return {
                    title : item.title,
                    coordinate : {latitude : item.latLng.getLat(), longitude : item.latLng.getLng() }
                };
            })
        })

        return data;
    }

    function openConnectionServer() {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/api/routes/recommendation', true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        return xhr;
    }

    function communicateServer(server, sendingData, callback) {

        server.onreadystatechange = function() {
            if (server.readyState === 4) {
                if (server.status === 200) {
                    response = JSON.parse(server.responseText);
                    callback(null, response);
                    console.log("Server Response : ", response);
                } else {
                    console.log("Error : ", server.status, server.statusText, server.responseText);
                    callback(new Error(`Server error: ${server.status}`), null);
                }
            }
        };

        server.send(sendingData);
    }

    function validateRecommendRoute(recommendRoute) {
        return true;
    }

    function drawRouteOnMap(recommendRoute) {
        //clear the markers
        result.forEach(function(m) {
            m.setMap(null);
        });
        result = [];

        for (var i = 0; i < recommendRoute.length - 1; i++) {
            var currentMarker = new kakao.maps.Marker({
                map : map,
                position : new kakao.maps.LatLng(recommendRoute[i].coordinate.latitude, recommendRoute[i].coordinate.longitude),
                title : recommendRoute[i].title,
            });

            var nextMarker = new kakao.maps.Marker({
                map : map,
                position : new kakao.maps.LatLng(recommendRoute[i+1].coordinate.latitude, recommendRoute[i+1].coordinate.longitude),
                title : recommendRoute[i+1].title,
            });

            var linePath = [currentMarker.getPosition(), nextMarker.getPosition()];
            var polyline = new kakao.maps.Polyline({
                path : linePath,
                strokeWeight : 3,
                strokeColor : '#FF0000',
                strokeOpacity : 0.7,
                strokeStyle : 'solid',
            });

            polyline.setMap(map);
            result.push(currentMarker);
            result.push(nextMarker);
            result.push(polyline);
        }
    }

    document.getElementById("recordRoute").addEventListener('click', recordBusiness);
    function recordBusiness() {
        const formData = {
                title: document.getElementById("routeTitle").value,
                routes: response
            };

            fetch('/record/save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData),
                });

    }

}