<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <title>IOP运维管理</title>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=0.9"/>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/app/image/ico/favicon.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/bootstrap-table.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/bootstrap-dialog.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/bootstrap-datetimepicker.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/fileinput.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/public/dist/css/dashboard.css">
    </head>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">IOP运维管理</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="javascript:void(0)" onclick="logout()">Quit</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">应用管理<span class=""></span></a></li>
                <li><a href="#/location">环境管理</a></li>
                <li><a href="#/topology">拓扑管理</a></li>
                <li><a href="#/repository">仓库管理</a></li>
                <li><a href="#/license">授权管理</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" id="app-view-content">

        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/public/dist/js/require.js"
        data-main="${pageContext.request.contextPath}/public/config.js"></script>
<script type="text/javascript">
    function logout() {
        var curWwwPath = document.location.href,
                pathName = document.location.pathname,
                pos = curWwwPath.indexOf(pathName),
                localhostPaht = curWwwPath.substring(0, pos),
                projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1),
                rootPath = (localhostPaht + projectName);
        $.removeCookie('login_name', {path: '/'});
        $.removeCookie('token', {path: '/'});
        window.location.href = rootPath+'/login';
    }
</script>
</body>
</html>
