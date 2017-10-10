var loginDataWebSocket;

function login(login, password) {
    loginDataWebSocket = new WebSocket("ws://" + document.location.host + "/userlogin");

    loginDataWebSocket.onopen = function (event) {
        var msg = {};
        msg.command = "LOGIN";
        msg.login = login;
        msg.password = password;
        loginDataWebSocket.send(JSON.stringify(msg));
    };

    loginDataWebSocket.onmessage = function(event) {
        var result = JSON.parse(event.data);
        if (result) {
            window.location = "http://" + document.location.host + "/";
        } else {
            document.getElementById('loginError').style.visibility='visible';
        }
        loginDataWebSocket.close();
    };
}