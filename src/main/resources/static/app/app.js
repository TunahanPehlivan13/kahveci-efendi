(function (angular) {
    angular.module("myApp.controllers", ["ui.bootstrap", "checklist-model"]);
    angular.module("myApp.services", []);
    angular.module("myApp", ["ngResource", "myApp.controllers", "myApp.services", "ngRoute"])
        .config(['$routeProvider',
            function ($routeProvider) {
                $routeProvider.when('/', {
                    templateUrl: 'home.html',
                    controller: 'ProductController'
                }).when('/checkout', {
                    templateUrl: 'checkout.html',
                    controller: 'CheckoutController'
                }).when('/report', {
                    templateUrl: 'report.html',
                    controller: 'ReportController'
                }).otherwise({
                    redirectTo: '/'
                });
            }]);
}(angular));