
var SocketHandler = (function() {
    function socketHandler() {
        var socket = null
        var graph = new Graph('graph');
        var readings = [];

        this.connect = function() {
            if(!socket) {
                socket = new WebSocket("ws://localhost:8080/websockets/randomData");

                socket.onopen = function(event) {
                    console.log('Connection opened', event);
                }

                socket.onmessage = function(event) {
                    if(readings.length == 100) {
                        readings = readings.splice(1,100);
                    }
                    readings.push(parseInt(event.data));

                    graph.draw(readings);
                }
            }
        }

        this.disconnect = function() {
            if(socket) {
                socket.close();
                socket = null;
            }
        }
    }
    return socketHandler;
})();