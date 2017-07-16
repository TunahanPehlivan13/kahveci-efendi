(function (angular) {
    var ProductFactory = function ($resource) {
        return $resource('/product/:id', {
            id: '@id'
        });
    };

    var ToppingsFactory = function ($resource) {
        return $resource('/toppings/:id', {
            id: '@id'
        });
    };

    var CartFactory = function ($resource) {
        return $resource('/cart/:id', {
            id: '@id'
        });
    };

    var OrderFactory = function ($resource) {
        return $resource('/order', null, {
            'query': {
                method: 'GET',
                url: 'order/query',
                isArray: true
            }
        });
    };

    ProductFactory.$inject = ['$resource'];
    ToppingsFactory.$inject = ['$resource'];
    CartFactory.$inject = ['$resource'];
    OrderFactory.$inject = ['$resource'];
    angular.module("myApp.services")
        .factory("ProductFactory", ProductFactory)
        .factory("ToppingsFactory", ToppingsFactory)
        .factory("CartFactory", CartFactory)
        .factory("OrderFactory", OrderFactory);
}(angular));