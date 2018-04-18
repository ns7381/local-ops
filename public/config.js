var curWwwPath = window.document.location.href,
    pathName = window.document.location.pathname,
    pos = curWwwPath.indexOf(pathName),
    localhostPaht = curWwwPath.substring(0, pos),
    projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1),
    rootPath = (localhostPaht + projectName);

requirejs.config({
    baseUrl: rootPath + '/app/view',
    paths: {
        app: 'app',
        text: rootPath+'/public/dist/js/rq/text',
        css: rootPath+'/public/dist/js/rq/css',
        jquery: rootPath+'/public/dist/js/jquery',
        'jq/validate': rootPath+'/public/dist/js/jq/jquery.validate',
        'jq/cookie': rootPath+'/public/dist/js/jq/jquery.cookie',
        'jq/backstretch': rootPath+'/public/dist/js/jq/jquery.backstretch.min',
        'jq/fileDownload': rootPath+'/public/dist/js/jq/jquery.fileDownload',
        bootstrap: rootPath+'/public/dist/js/bootstrap.min',
        'bs/tooltip': rootPath+'/public/dist/js/bs/tooltip',
        'bs/table': rootPath+'/public/dist/js/bs/bootstrap-table',
        'bs/dialog': rootPath+'/public/dist/js/bs/bootstrap-dialog',
        'bs/alert': rootPath+'/public/dist/js/bs/alert',
        'bs/tab': rootPath+'/public/dist/js/bs/tab',
        'bs/fileinput': rootPath+'/public/dist/js/bs/fileinput',
        'bs/datetimepicker': rootPath+'/public/dist/js/bs/bootstrap-datetimepicker',
        template: rootPath+'/public/dist/js/template-debug'
    },
    map: {
        '*': {
            'css': rootPath+'/public/dist/js/rq/css'
        }
    },
    shim: {
        /*login: {
            deps: ["css!../../css/login.css"]
        },*/
        bootstrap: {
            deps: ["jquery"]
        },
        'jq/validate': {
            deps: ["jquery"]
        },
        'jq/cookie': {
            deps: ["jquery"]
        },
        'bs/table': {
            deps: ["jquery"]
        },
        'bs/dialog': {
            deps: ["jquery", "bootstrap"],
            exports: 'BootstrapDialog'
        }
    }
});
requirejs(['start']);