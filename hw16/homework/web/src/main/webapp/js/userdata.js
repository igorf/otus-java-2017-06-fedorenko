var userDataWebSocket;

function showUser(userId) {

    userDataWebSocket = new WebSocket("ws://" + document.location.host + "/userdata");

    userDataWebSocket.onopen = function (event) {
        var msg = {};
        msg.command = "SHOW_USER";
        msg.userId = userId;
        userDataWebSocket.send(JSON.stringify(msg));
    };

    userDataWebSocket.onmessage = function(event) {
        var user = JSON.parse(event.data);

        document.getElementById("userPlaceholder").style.visibility='visible';
        document.getElementById("userPlaceholder").innerText = "";
        if (user !== null) {
            document.getElementById("userPlaceholder").innerText += "UID:........ " + user.id + "\n";
            document.getElementById("userPlaceholder").innerText += "Age:........ " + user.age + "\n";
            document.getElementById("userPlaceholder").innerText += "Name:....... " + user.name + "\n";
        } else {
            document.getElementById("userPlaceholder").innerText += "User not found";
        }
        userDataWebSocket.close();
    };
}