/**
 * Created by nathan on 2016/11/17.
 */
define(['jquery', 'common/router', 'template', 'jq/cookie'], function ($, Router, Template) {
    var $appView = document.getElementById("app-view-content");
    $('ul.nav li').click(function(){
        $(this).addClass('active');
        $(this).parent().children('li').not(this).removeClass('active');
    });
    Router.on('routeload', function (path) {
        /*if (!$.cookie("token")) {
            window.location.href = Router.getRootPath() + '/login';
        }*/
        if(!path || $.trim(path) == ''){
            path = 'deploy';
        }
        if (typeof(Router.cachedModulePaths[path]) !== 'undefined') {
            render(Router.cachedModulePaths[path], Router.routeArguments());
        } else {
            require([path], function (module) {
                if (!module.template) {
                    module.template = 'app/view/' + path + '.html';
                }
                render(module, Router.routeArguments());
                Router.cachedModulePaths[path] = module;
            });
        }
    }).init();
    function render(module, routeArguments) {
        // When a route loads, render the view and attach it to the document
        if (Router.templateCache[module.template]) {
            var render = Template.compile(Router.templateCache[module.template]);
            $appView.innerHTML = render(routeArguments);
            module.init();
        } else {
            $.get(module.template, function (html) {
                var render = Template.compile(html);
                $appView.innerHTML = render(routeArguments);
                module.init();
                Router.templateCache[module.template] = html;
            });
        }
    }
});