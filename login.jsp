<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IOP运维管理</title>

    <!-- CSS -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/app/image/ico/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/form-elements.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/login.css">

</head>

<body>

<!-- Top content -->

<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>IOP</strong>运维管理</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>登录</h3>
                            <%--<p>请输入用户名和密码:</p>--%>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="${pageContext.request.contextPath}/login" method="post" class="login-form">
                            <c:if test="${not empty param.error}"><div class="red-text">用户名或密码错误！</div></c:if>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户</label>
                                <input type="text" name="username" placeholder="用户名"
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">密码</label>
                                <input type="password" name="password" placeholder="密码"
                                       class="form-password form-control" id="form-password">
                            </div>
                            <button type="submit" class="btn" id="login-btn">确定</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Javascript -->
<script src="${pageContext.request.contextPath}/public/dist/js/jquery.js"></script>
<%--<script src="${pageContext.request.contextPath}/public/dist/js/bootstrap.min.js"></script>--%>
<script src="${pageContext.request.contextPath}/public/dist/js/jq/jquery.backstretch.min.js"></script>

<script type="text/javascript">
    jQuery(document).ready(function () {
        /*var curWwwPath = document.location.href,
                pathName = document.location.pathname,
                pos = curWwwPath.indexOf(pathName),
                localhostPaht = curWwwPath.substring(0, pos),
                projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1),
                rootPath = (localhostPaht + projectName);*/
        $.backstretch('app/image/login.jpg');
/*
        $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function () {
            $(this).removeClass('input-error');
        });
        var $username = $("#form-username"),
                $btnSubmit = $("#login-btn"),
                $password = $("#form-password");
        $('.login-form').on('submit', function (e) {
            var i = 0;
            if ($username.val() == "") {
                $username.addClass('input-error');
                e.preventDefault();
            }
            if ($password.val() == "") {
                $password.addClass('input-error');
                e.preventDefault();
            }
            $username.removeClass('input-error');
            $password.removeClass('input-error');
            var data = {
                'username': $username.val(),
                'password': $password.val()
            };
            $btnSubmit.text("正在登录...").prop('disabled', true);
            $.ajax({
                'type': 'POST',
                'url': "login/auth",
                'data': JSON.stringify(data),
                'dataType': 'json',
                'contentType': 'application/json',
                'success': function (res) {
                    window.location.href = rootPath+'/#/app';
                    return false;
                },
                'error': function (xhr, textStatus, errorThrown) {
                    debugger
                }
            });
        });*/
    });
</script>
</body>
</html>
