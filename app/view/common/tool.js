define(['common/router'], function (Router) {
    var Tool = {};
    Tool.getParams = function (name) {
        var args = Router.routeArguments();
        for (var arg in args) {
            if(arg == name) {
                return args[arg];
            }
        }
        return null;
    };
    var curWwwPath = window.document.location.href,
        pathName = window.document.location.pathname,
        pos = curWwwPath.indexOf(pathName),
        localhostPaht = curWwwPath.substring(0, pos),
        projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1),
        rootPath = (localhostPaht + projectName);
    Tool.rootPath = rootPath;
    Tool.webSocket = function (path, callback) {
        var websocket;
        websocket = new WebSocket("ws://"+rootPath.substr(7)+path);
        websocket.onopen = function (evnt) {
            console.log("websocket connect success: " + evnt.data);
        };
        websocket.onmessage = function (event) {
            typeof callback === "function" && callback.call(null, event);
        };
        websocket.onerror = function (evnt) {
        };
        websocket.onclose = function (evnt) {
        };
    }
    return Tool;
});