var cacheNotifierWebSocket;

function cacheNotifierConnect() {
    cacheNotifierWebSocket = new WebSocket("ws://" + document.location.host + "/cachechanged");

    cacheNotifierWebSocket.onopen = function() {
        cacheNotifierRefresh();
    };

    cacheNotifierWebSocket.onmessage = function(event) {
        var caches = JSON.parse(event.data);
        for (var i = 0; i < caches.length; i++) {
            document.getElementById("cachesData_" + caches[i].name).innerText = "";
            document.getElementById("cachesData_" + caches[i].name).innerText += "Global TTL:... " + caches[i].globalTTL+ "\n";
            document.getElementById("cachesData_" + caches[i].name).innerText += "Idle TTL:..... " + caches[i].idleTTL + "\n";
            document.getElementById("cachesData_" + caches[i].name).innerText += "Size:......... " + caches[i].size + "\n";
            document.getElementById("cachesData_" + caches[i].name).innerText += "Hits:......... " + caches[i].hits + "\n";
            document.getElementById("cachesData_" + caches[i].name).innerText += "Misses:....... " + caches[i].misses + "\n";
            document.getElementById("cachesData_" + caches[i].name).innerText += "\n\n";
        }
    };
}

function cacheNotifierRefresh() {
    var msg = {};
    msg.command = "GET";
    msg.cacheID = "";
    cacheNotifierWebSocket.send(JSON.stringify(msg));
}

function cleanCache(cacheID) {
    var msg = {};
    msg.command = "CLEAN";
    msg.cacheID = cacheID;
    cacheNotifierWebSocket.send(JSON.stringify(msg));
}