/**
 * Created by Administrator on 2016/11/16.
 */
define(["jquery"], function($) {
    var Ajax = {};
    function successCallback() {

    }
    function errorCallback() {

    }
    Ajax.get = function (url, success, error) {
        return $.ajax({
            url: url,
            type: 'GET',
            headers: {
                'Accept': "application/json; charset=utf-8",
                'Content-Type': "application/json; charset=utf-8"
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };
    Ajax.postJson = function (url, data, success, error) {
        return $.ajax({
            url: url,
            type: 'POST',
            headers: {
                'Accept': "application/json; charset=utf-8",
                'Content-Type': "application/json; charset=utf-8"
            },
            dataType: "json",
            data: JSON.stringify(data),
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };
    Ajax.putJson = function (url, data, success, error) {
        return $.ajax({
            url: url,
            type: 'PUT',
            headers: {
                'Accept': "application/json; charset=utf-8",
                'Content-Type': "application/json; charset=utf-8"
            },
            dataType: "json",
            data: JSON.stringify(data),
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };
    Ajax.post = function (url, success, error) {
        return $.ajax({
            url: url,
            type: 'POST',
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };
    Ajax.put = function (url, success, error) {
        return $.ajax({
            url: url,
            type: 'PUT',
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };
    Ajax.delete = function (url, success, error) {
        return $.ajax({
            url: url,
            type: 'DELETE',
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                error && error(errorThrown);
            },
            success: function (response) {
                success && success(response);
            }
        });
    };

    return Ajax;
});