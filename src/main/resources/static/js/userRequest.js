var markers = []; //markers displayed on map
var vertexList = [];//vertices sending to java
var startVertex;//start vertex user selects
var endVertex;//end vertex user selects
var latlng;//lat and lng info
var expected_distance; //expected distance user input
window.onload = function () {
    var startCoordinatesElement = document.getElementById('startCoordinates');
    var endCoordinatesElement = document.getElementById('endCoordinates');
    var allCoordinatesElement = document.getElementById('allCoordinates');
    var expectedDistanceElement = document.getElementById('exDistance');
    var showReceivedRouteElement = document.getElementById('showReceivedRoute');
    var mapContainer = document.getElementById('map');
    var mapOptions = {
        center: new kakao.maps.LatLng(37.5509442, 126.9410023),//initially sogang university
        level: 5
    };
    //map container
    var map = new kakao.maps.Map(mapContainer, mapOptions);
    //marker container
    var marker = new kakao.maps.Marker({
        map: map,
    });

    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
        latlng = mouseEvent.latLng;
        marker.setPosition(latlng);
        var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';
        console.log(message);
    });

    //set start vertex
    document.getElementById("setStartVertex").addEventListener('click', setStartVertex);
    function setStartVertex() {
        startVertex = latlng;
        updateVertexList("start", startVertex);
        startCoordinatesElement.innerText = startVertex.getLat() + ", " + startVertex.getLng();
    }

    //set end vertex
    document.getElementById("setEndVertex").addEventListener('click', setEndVertex);
    function setEndVertex() {
        endVertex = latlng;
        updateVertexList("end", endVertex);
        endCoordinatesElement.innerText = endVertex.getLat() + ", " + endVertex.getLng();
    }

    //show selected start and end vertices on the map
    document.getElementById("vertexList").addEventListener('click', showVertex);
    function showVertex() {
        allCoordinatesElement.innerText = "";

        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }
        markers = [];

        var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
        for (var i = 0; i < vertexList.length; i++) {
            var marker = showMarker(vertexList[i].title, vertexList[i].latlng, imageSrc);
            markers.push(marker);
        }
    }

    //draw marker selected start and end vertices on the map
    function showMarker(title, latlng, imageSrc) {
        var imageSize = new kakao.maps.Size(24, 35);
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        var marker = new kakao.maps.Marker({
            map: map,
            position: latlng,
            title: title,
            image: markerImage
        });
        allCoordinatesElement.innerText += marker.getTitle() + " : " + marker.getPosition().getLat() + " , " + marker.getPosition().getLng() + "\n";

        return marker;
    }

    /* communicate with java spring. send start,end vertex information to vertexController */
    document.getElementById("sendVertices").addEventListener('click', sendVertexToServer);
    function sendVertexToServer() {
        var expectedDistance = document.getElementById("expectedDistance").value;

        // Ensure integerValue is a valid integer (you may want to add additional validation)
        if(!Number.isInteger(parseInt(expectedDistance))) {
            alert("error number");
            return;
        }


        var vertices = JSON.stringify({
            expectedDistance: parseInt(expectedDistance),
            vertices: vertexList.map(item => ({ title: item.title, coordinate: { latitude: item.latlng.getLat(), longitude: item.latlng.getLng() } }))
        });

        console.log('Vertices:', vertices);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/api/send-vertices-json', true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    console.log("response : ", response);
                    if (response != null) {
                        drawLineOnMap(response);
                    } else {
                        console.log('Server response:', response);
                    }
                } else {
                    console.error('Error:', xhr.status, xhr.statusText, xhr.responseText);
                }
            }
        };

        xhr.send(vertices);
    }

    //vertexList에 start, end vertex를 저장. 이미 있다면 replace
    function updateVertexList(title, latlng) {
        var existingItemIndex = vertexList.findIndex(item => item.title === title);
        if (existingItemIndex !== -1) {
            vertexList[existingItemIndex].latlng = latlng;
        } else {
            vertexList.push({ "title": title, "latlng": latlng });
        }
    }

//    document.getElementById("showRoute").addEventListener('click', showReceivedRoute);
//    function showReceivedRoute() {
//        //make an HTTP request to the backend
//        fetch('/api/get-data')
//            .then(response => {
//                if (!response.ok) {
//                    throw new Error('Network response was not ok');
//                }
//                return response.json();
//            })
//            .then(data => {
//                //Handle the JSON data
//                console.log('Received data:', data);
//
//                //console.log data
//                console.log('Key:', data.key);
//                console.log('Value:', data.value);
//
//                //drawMarkerOnMap(data);
//                drawLineOnMap(data);
//            })
//            .catch(error => console.error('Error:', error));
//    }

//    function drawMarkerOnMap(data) {
//        console.log("in draw:",data);
//
//        //draw markers on the Kakao Map using data
//        data.forEach(item => {
//            console.log("create marker :",item.latlng.lat, item.latlng.lng);
//                map: map,
//                position: new kakao.maps.LatLng(item.latlng.lat, item.latlng.lng),
//            });
//            markers.push(marker);
//        });
//    }

    function drawLineOnMap(data) {
        //clear existing markers and lines
        markers.forEach(marker => marker.setMap(null));
        markers = [];

        //draw markers and lines
        for (var i = 0; i < data.length - 1; i++) {
            var currentMarker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(data[i].coordinate.latitude, data[i].coordinate.longitude),
                title: data[i].title,
            });

            var nextMarker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(data[i + 1].coordinate.latitude, data[i + 1].coordinate.longitude),
                title: data[i + 1].title,
            });

            // Draw a line between consecutive markers
            var linePath = [currentMarker.getPosition(), nextMarker.getPosition()];
            var polyline = new kakao.maps.Polyline({
                path: linePath,
                strokeWeight: 3,
                strokeColor: '#FF0000',
                strokeOpacity: 0.7,
                strokeStyle: 'solid',
            });

            // Set the polyline on the map
            polyline.setMap(map);

            // Save markers and polyline to the markers array
            markers.push(currentMarker);
            markers.push(nextMarker);
            markers.push(polyline);
        }
    }
};