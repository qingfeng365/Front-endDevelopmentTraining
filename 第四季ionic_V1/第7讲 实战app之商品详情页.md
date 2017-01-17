# 第7讲 实战app之商品详情页

## 页面结构

- app容器
	+ tab容器
		- tab1
			+ 生活 (lefe)
			+ 美食 (cate)
		- tab2
			+ 附近	 (near)
			+ 商场  (mall)
		- tab3
			+ 精选  (pick)
		- tab4
			+ 我的		(user)
	+ 商品详情页

## 安装underscore

`bower install underscore --save`


`/trainApp-V1/www/index.html`

```
    <!-- your app's js -->
    <script src="lib/underscore/underscore-min.js"></script>
```

## 准备数据 

`/Users/macbook-hyf/Work/前端培训练习/ionicV1/trainApp-V1/www/app/app.service.js`

```js
        this.getStoreProductDetail = function(storeid, productid) {
          var self = this;
          return $q(function(resolve, reject) {
            //静态演示,制造1秒延时效果
            setTimeout(function() {
              var store = null;
              var product = null;

              store = _.findWhere(self.demoDatas.stores, { id: storeid });
              if (store) {
                product = _.findWhere(store.products, { id: productid });
              }
              resolve({ store: store, product: product });
            }, 1000);
          });
        };
```

## 创建购物车公共服务

`/Users/macbook-hyf/Work/前端培训练习/ionicV1/trainApp-V1/www/app/app.service.js`

```
  currApp
    .service('appCartService', ['$q', '$http',
      function($q, $http) {
        this.shoppingCart = {
          storeSells: [],
          qtyTotal: 0,
          moneyTotal: 0
        };

        this.addtoCart = function(store, product) {
          var self = this;
          return $q(function(resolve, reject) {
            var storeSellitem = _.findWhere(self.shoppingCart.storeSells, { id: store.id });
            if (!storeSellitem) {
              storeSellitem = {
                id: store.id,
                type: store.type,
                img: store.img,
                name: store.name,
                loc: store.loc,
                mallId: store.mallId,
                star: store.star,
                spend: store.spend,
                reservation: store.reservation,
                distance: store.distance,
                display: store.display,
                recommend: store.recommend,
              };

              storeSellitem.__select = true;
              storeSellitem.products = [];

              self.shoppingCart.storeSells.push(storeSellitem);
            }

            var productItem = _.findWhere(storeSellitem.products, { id: product.id });
            if (productItem) {
              productItem.qty = productItem.qty + 1;
            } else {
              productItem = {
                id: product.id,
                type: product.type,
                img: product.img,
                name: product.name,
                loc: product.loc,
                storeId: product.storeId,
                currentPrice: product.currentPrice,
                originPrice: product.originPrice,
                saledCount: product.saledCount,
                display: product.display
              };
              productItem.__select = true;
              productItem.qty = 1;
              storeSellitem.products.push(productItem);
            }
            self.updateTatal();
            resolve(true);
          });
        };

        //重算合计
        this.updateTatal = function() {
          var qtyTotal = 0;
          var moneyTotal = 0;
          _.each(this.shoppingCart.storeSells, function(store, index) {
            _.each(store.products, function(product, index) {
              if (product.__select) {
                qtyTotal = qtyTotal + product.qty;
                moneyTotal = moneyTotal + (product.qty * product.currentPrice);
              }
            });
          });
          this.shoppingCart.qtyTotal = qtyTotal;
          this.shoppingCart.moneyTotal = moneyTotal;
        };
      }
    ]);
```

## 在rootScope添加对购物车的引用

`/trainApp-V1/www/app/root.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('root');
  currApp
    .controller('root.rootController', ['$rootScope', '$state', '$ionicHistory',
      '$window','appCartService',
      function($rootScope, $state, $ionicHistory,$window,
        appCartService) {
        $rootScope.goBack = function() {
          $ionicHistory.goBack();
        };

        $rootScope.gotoUrl = function(targetURL){
          $window.location.href = targetURL;
        };

        $rootScope.shoppingCart = appCartService.shoppingCart;

      }
    ]);
})();

```


## 创建视图

`/trainApp-V1/www/jade_template/productDetail.template.main.jade`

```jade
ion-view(title="商品详情")
	ion-nav-buttons(side="left")
		button.button.button-clear.button-icon.icon.ion-arrow-left-c(
			ng-click="goBack()")
	ion-content(padding="true")
		ion-list
			ion-item.item-image
				img(ng-src="{{product.img}}")
			ion-item
				span.assertive.font-size-32 ¥ {{product.currentPrice}}
				span.gray.padding 团购价 门市价{{product.originPrice}}
			ion-item.item-icon-left.item-icon-right
				i.icon.ion-ios-location.calm
				h3.gray 新顺南大街6号商业楼一层 1.8km
				i.icon.ion-ios-telephone.calm
			ion-item.item-icon-right
				h3
					| 所有适用门店&nbsp;&nbsp;&nbsp;&nbsp;
					span.gray 44家
				i.icon.ion-ios-arrow-right
		ion-list.padding
			ion-item.item-icon-right
				h3 
					| 评价
					span.padding-left
						i.ion-android-star.energized
						i.ion-android-star.energized
						i.ion-android-star.energized
						i.ion-android-star.energized
				i.icon.ion-ios-arrow-right
		ion-list.padding
			ion-item.item-icon-right
				h3
					| 折扣须知
					span.gray.padding-left 不与其他优惠活动同时使用
				i.icon.ion-ios-arrow-right
		ion-list.padding
			ion-item
				h3 更多信息
			ion-item
				span.gray
					i.icon.ion-wifi
					| WiFi
				span &nbsp;&nbsp;&nbsp;&nbsp;
				span.gray
					i.icon.ion-no-smoking
					| 禁烟
			ion-item
				h3.gray 营业时间：00:00-23:59
	ion-footer-bar.bar-light(style="padding:0")
		.tabs.tabs-light(style="padding:0")
			a.tab-item.has-badge(ng-click="")
				i.icon.ion-ios-cart-outline(style="color:#ff0000")
				i.badge(style="background-color:#FF4683;color:#FFFFFF",
					ng-show="shoppingCart.qtyTotal>0")
					| {{shoppingCart.qtyTotal}}
			a.tab-item(style="background-color:#FF9900;color:#FFFFFF",
				ng-click="vm.addToCart(store, product)")
				| 加入购物车
			a.tab-item(style="background-color:#ff0000;color:#ffffff")
				| 立即抢购

```


## 创建productDetail模块

`/trainApp-V1/www/app/productDetail.js`

```js
(function() {
  'use strict';
 	angular.module('productDetail',['app']);
})();
```

`/trainApp-V1/www/app/productDetail.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('productDetail');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.productDetail', {
        url: '/productDetail/:storeId/:productId',
        views: {
          '': {
            templateUrl: 'app/productDetail.template.main.html',
            controller: 'productDetail.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/productDetail.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('productDetail');
  currApp
    .controller('productDetail.mainController', ['$scope', '$state', 
      '$ionicHistory',
      '$window','$stateParams',
      'appDataService',
      'appCartService',
      function($scope, $state,
        $ionicHistory, 
        $window,$stateParams,
      	appDataService,
        appCartService) {
      	var vm = this;
      	$scope.store = {};
      	$scope.product = {};

        var storeId = parseInt($stateParams.storeId);
        var productId = parseInt($stateParams.productId);

      	vm.getData = function(){
      		return appDataService.getStoreProductDetail(storeId, productId)
      		.then(function(data){
      			$scope.store = data.store;
      			$scope.product = data.product;
      		})
      		.catch(function(err){
      			
      		});
      	};

        vm.addToCart = function(store, product){
          return appCartService.addtoCart(store, product);
        };

      	vm.getData();
      }
    ]);
})();
```

## 增加对商品详情页的调用

`/trainApp-V1/www/jade_template/life.template.main.jade`

```jade
			ion-item.item-thumbnail-left(
				ng-repeat="product in store.products",
				ng-show="store.products.length",
				ng-click="gotoUrl('#/productDetail/{{store.id}}/{{product.id}}')")
				img(ng-src="{{product.img}}")
				h3 {{product.name}}
				.row.marging-top
					.col
						span.assertive.font-size-24 ¥ {{product.currentPrice}}
						span.gray.font-size-16
							strike {{product.originPrice}}
					.col
						span.gray.font-size-16 已售 {{product.saledCount}}
```

## 增加Loading效果

`/trainApp-V1/www/app/productDetail.controller.js`

```js
        vm.getData = function(){
          $ionicLoading.show();
          return appDataService.getStoreProductDetail(storeId, productId)
          .then(function(data){
            $scope.store = data.store;
            $scope.product = data.product;
            $ionicLoading.hide();
          })
          .catch(function(err){
            $ionicLoading.hide();
          });
        };
```