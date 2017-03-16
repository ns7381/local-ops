/*
 * 针对bootstrap alert 二次封装。
 */
define(['bs/alert'], function () {
    var Alert = {};

    function sortFuc() {
        return Math.random() > 0.5 ? -1 : 1;
    }

    Alert.genUUID = function () {
        return "dialog-" + ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'q', 'W', 'w', 'E', 'e', 'R', 'r', 'T', 't', 'Y', 'y', 'U', 'u', 'I', 'i', 'O', 'o', 'P', 'p', 'A', 'a', 'S', 's', 'D', 'd', 'F', 'f', 'G', 'g', 'H', 'h', 'J', 'j', 'K', 'k', 'L', 'l', 'Z', 'z', 'X', 'x', 'C', 'c', 'V', 'v', 'B', 'b', 'N', 'n', 'M', 'm'].sort(sortFuc).join('').substring(5, 20);
    };
    Alert.success = function (message) {
        var obj = $("body");
        var alertId = Alert.genUUID();
        var tmpHtml = '<div style="min-width: 20em; max-width: 40px;position: absolute; top: 60px; right: 0px;" class="alert alert-success fade in" id="' + alertId + '">' +
            '<a href="#" class="close" data-dismiss="alert">&times;</a>' +
            '<i class="fa fa-check-circle"></i>&nbsp;' + message +
            '</div>';
        obj.append(tmpHtml);
        $('#' + alertId).alert();
        setTimeout(function () {
            $('#' + alertId).alert('close');
        }, 1500);
    };
    Alert.error = function (message) {
        var obj = $("body");
        var alertId = Alert.genUUID();
        var tmpHtml = '<div style="min-width: 20em; max-width: 60px;position: absolute; top: 60px; right: 0px;" class="alert alert-danger fade in" id="' + alertId + '">' +
            '<a href="#" class="close" data-dismiss="alert">&times;</a>' +
            '<i class="fa fa-bug"></i>&nbsp;' + message +
            '</div>';
        obj.append(tmpHtml);
        $('#' + alertId).alert();
        setTimeout(function () {
            $('#' + alertId).alert('close');
        }, 1500);
    };
    return Alert;
});