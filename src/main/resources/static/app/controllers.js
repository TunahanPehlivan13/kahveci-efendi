(function (angular) {
    var ProductController = function ($scope, ProductFactory, $modal) {
        ProductFactory.query(function (response) {
            $scope.products = response ? response : [];
        });

        $scope.openCartDlg = function (product) {
            $scope.cartDlg = $modal.open({
                templateUrl: 'cart-dialog.html',
                controller: ToppingsController,
                scope: $scope,
                resolve: {
                    product: function () {
                        return product;
                    }
                }
            });
        };
    };

    var ToppingsController = function ($scope, ToppingsFactory, CartFactory, product) {
        ToppingsFactory.query(function (response) {
            $scope.toppings = response ? response : [];
        });

        $scope.selectedToppings = [];

        $scope.addToCart = function (quantity) {
            if (!quantity) {
                alert("You should enter quantity!");
                return;
            }
            new CartFactory({
                quantity: quantity,
                toppings: $scope.selectedToppings,
                product: product
            }).$save(function (cart) {
                alert("Success!");
                $scope.cartDlg.close();
            }, function () {
                alert("System Error");
            });
        };
    };

    var CheckoutController = function ($scope, $location, OrderFactory) {
        var response = OrderFactory.get();
        $scope.order = response ? response : {};

        $scope.completeOrder = function () {
            OrderFactory.save(function () {
                alert("Order completed!");
                $location.path('/')
            }, function () {
                alert("System Error");
            });
        };
    };
    var ReportController = function ($scope, OrderFactory, ProductFactory) {
        $scope.search = function (selectedProduct, selectedDiscountType) {
            OrderFactory.query({
                productId: selectedProduct ? selectedProduct.id : null,
                discountType: selectedDiscountType
            }, function (response) {
                $scope.orders = response ? response : [];
            });
        }

        ProductFactory.query(function (response) {
            $scope.products = response ? response : [];
        });

        $scope.discountTypes = [
            'TotalCostOverDiscount',
            'TotalProductCountOverDiscount'
        ];
    };

    ProductController.$inject = ['$scope', 'ProductFactory', '$modal'];
    ToppingsController.$inject = ['$scope', 'ToppingsFactory', 'CartFactory', 'product'];
    CheckoutController.$inject = ['$scope', '$location', 'OrderFactory'];
    ReportController.$inject = ['$scope', 'OrderFactory', 'ProductFactory'];
    angular.module("myApp.controllers")
        .controller("ProductController", ProductController)
        .controller("ToppingsController", ToppingsController)
        .controller("ReportController", ReportController)
        .controller("CheckoutController", CheckoutController);
}(angular));