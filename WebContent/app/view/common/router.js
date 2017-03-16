define([], function () {
    'use strict';

    // Private closure variables
    var eventHandlers = {
        statechange: [],
        routeload: []
    };

    // In some modern browsers a hashchange also fires a popstate. There isn't a check to see if the browser will fire
    // one or both. We have to keep track of the previous state to prevent it from fireing a statechange twice.
    var previousState = '';
    var popstateHashchangeEventLisener = function popstateHashchangeEventLisener() {
        if (previousState != window.location.href) {
            previousState = window.location.href;
            router.fire('statechange');
        }
    };

    // router public interface
    //
    // There is only one instance of the router. Loading it in multiple modules will always load the same router.
    var router = {
        cachedUrlPaths: {},
        templateCache : {},
        cachedModulePaths : {},
        // router.init([options]) - initializes the router
        getRootPath: function () {
            var curWwwPath = window.document.location.href,
                pathName = window.document.location.pathname,
                pos = curWwwPath.indexOf(pathName),
                localhostPaht = curWwwPath.substring(0, pos),
                projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1),
                rootPath = (localhostPaht + projectName);
            return rootPath;
        },
        init: function init(options) {
            if (typeof(options) === 'undefined') options = {};

            // Set up the window popstate and hashchange event listeners
            if (window.addEventListener) {
                window.addEventListener('popstate', popstateHashchangeEventLisener, false);
                window.addEventListener('hashchange', popstateHashchangeEventLisener, false);
            } else {
                // IE 8 and lower
                window.attachEvent('popstate', popstateHashchangeEventLisener); // In case pushState has been polyfilled
                window.attachEvent('onhashchange', popstateHashchangeEventLisener);
            }

            // Call loadCurrentRoute on every statechange event
            if (options.loadCurrentRouteOnStateChange !== false) {
                router.on('statechange', function onstatechange() {
                    router.load();
                });
            }

            // Fire the initial statechange event
            if (options.fireInitialStateChange !== false) {
                router.fire('statechange');
            }

            return router;
        },


        // router.on(eventName, eventHandler([arg1, [arg2]]) {}) - Register an event handler
        //
        // The two main events are 'statechange' and 'routeload'.
        on: function on(eventName, eventHandler) {
            if (typeof(eventHandlers[eventName]) === 'undefined') eventHandlers[eventName] = [];
            eventHandlers[eventName].push(eventHandler);
            return router;
        },

        // router.fire(eventName, [arg1, [arg2]]) - Fire an event
        //
        // This will call all eventName event handlers with the arguments passed in.
        fire: function fire(eventName) {
            if (eventHandlers[eventName]) {
                var eventArguments = Array.prototype.slice.call(arguments, 1);
                for (var i = 0; i < eventHandlers[eventName].length; i++) {
                    eventHandlers[eventName][i].apply(router, eventArguments);
                }
            }
            return router;
        },

        // router.off(eventName, eventHandler) - Remove an event handler
        //
        // If you want remove an event handler you need to keep a reference to it so you can tell router.off() with the
        // original event handler.
        off: function off(eventName, eventHandler) {
            if (eventHandlers[eventName]) {
                var eventHandlerIndex = eventHandlers[eventName].indexOf(eventHandler);
                if (eventHandlerIndex !== -1) {
                    eventHandlers[eventName].splice(eventHandlerIndex, 1);
                }
            }
            return router;
        },

        // router.loadCurrentRoute() - Manually tell the router to load the module for the current route
        load: function (url) {
            var path = router.urlPath(url || window.location.href);
            router.fire('routeload', path);
            return router;
        },

        // urlPath(url) - Parses the url to get the path
        //
        // This will return the hash path if it exists or return the real path if no hash path exists.
        //
        // Example URL = 'http://domain.com/other/path?queryParam3=false#/example/path?queryParam1=true&queryParam2=example%20string'
        // path = '/example/path'
        //
        // Note: The URL must contain the protocol like 'http(s)://'
        urlPath: function urlPath(url) {
            // Check the cache to see if we've already parsed this URL
            if (typeof(router.cachedUrlPaths[url]) !== 'undefined') {
                return router.cachedUrlPaths[url];
            }

            // The relative URI is everything after the third slash including the third slash
            // Example relativeUri = '/other/path?queryParam3=false#/example/path?queryParam1=true&queryParam2=example%20string'
            var splitUrl = url.split('/');
            var relativeUri = '/' + splitUrl.splice(3, splitUrl.length - 3).join('/');

            // The path is everything in the relative URI up to the first ? or #
            // Example path = '/other/path'
            var path = relativeUri.split(/[\?#]/)[0];

            // The hash is everything from the first # up to the the search starting with ? if it exists
            // Example hash = '#/example/path'
            var hashIndex = relativeUri.indexOf('#');
            if (hashIndex !== -1) {
                var hash = relativeUri.substring(hashIndex).split('?')[0];
                if (hash.substring(0, 2) === '#/') {
                    // Hash path
                    path = hash.substring(2);
                } else if (hash.substring(0, 3) === '#!/') {
                    // Hashbang path
                    path = hash.substring(3);
                } else {
                    path = ''
                }
            } else {
                path = '';
            }

            // Cache the path for this URL
            router.cachedUrlPaths[url] = path;

            return path;
        },

        routeArguments: function () {
            // Get the query parameter values
            // The search is the query parameters including the leading '?'
            var url = window.location.href;
            var args = {};
            var searchIndex = url.indexOf('?');
            var search = '';
            if (searchIndex !== -1) {
                search = url.substring(searchIndex);
                var hashIndex = search.indexOf('#');
                if (hashIndex !== -1) {
                    search = search.substring(0, hashIndex);
                }
            }
            // If it's a hash URL we need to get the search from the hash
            var hashPathIndex = url.indexOf('#/');
            var hashBangPathIndex = url.indexOf('#!/');
            if (hashPathIndex !== -1 || hashBangPathIndex !== -1) {
                var hash = '';
                if (hashPathIndex !== -1) {
                    hash = url.substring(hashPathIndex);
                } else {
                    hash = url.substring(hashBangPathIndex);
                }
                searchIndex = hash.indexOf('?');
                if (searchIndex !== -1) {
                    search = hash.substring(searchIndex);
                }
            }

            var queryParameters = search.substring(1).split('&');
            // split() on an empty string has a strange behavior of returning [''] instead of []
            if (queryParameters.length === 1 && queryParameters[0] === '') {
                queryParameters = [];
            }
            for (var i in queryParameters) {
                if (queryParameters.hasOwnProperty(i)) {
                    var queryParameter = queryParameters[i];
                    var queryParameterParts = queryParameter.split('=');
                    args[queryParameterParts[0]] = queryParameterParts.splice(1, queryParameterParts.length - 1).join('=');
                }
            }

            // Parse the arguments into unescaped strings, numbers, or booleans
            for (var arg in args) {
                var value = args[arg];
                if (value === 'true') {
                    args[arg] = true;
                } else if (value === 'false') {
                    args[arg] = false;
                } else if (!isNaN(value) && value !== '' && value.charAt(0) !== '0') {
                    // numeric
                    args[arg] = +value;
                } else {
                    // string
                    args[arg] = decodeURIComponent(value);
                }
            }

            return args;
        }

    };
    return router;
});