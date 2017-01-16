# 第5讲 实战app之首页及导航

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
			
## 创建视图

`/trainApp-V1/www/jade_template/app.template.main.jade`

```
ion-view
	ion-nav-bar.bar-stable
	ion-nav-view
```

`/trainApp-V1/www/jade_template/app.template.tabs.jade`

```
ion-tabs.tabs-icon-top.tabs-color-active-assertive
	ion-tab(title="首页" icon-off="ion-ios-home-outline" icon-on="ion-ios-home" href="#/life")
		ion-nav-view(name="tab-life")
	ion-tab(title="附近" icon-off="ion-ios-location-outline" icon-on="ion-ios-location" href="#/near")
		ion-nav-view(name="tab-near")
	ion-tab(title="精选" icon-off="ion-ios-star-outline" icon-on="ion-ios-star" href="#/pick")
		ion-nav-view(name="tab-pick")
	ion-tab(title="我的" icon-off="ion-ios-person-outline" icon-on="ion-ios-person" href="#/user")
		ion-nav-view(name="tab-user")
```

`/trainApp-V1/www/jade_template/life.template.main.jade`

```
ion-view(title="首页")
	ion-content
		h1 life-111111
		h2 life-222222
		h3 life-333333
		a(href="#/cate") 美食
```

`/trainApp-V1/www/jade_template/near.template.main.jade`

```
ion-view(title="附近")
	ion-content
		h1 near-111111
		h2 near-222222
		h3 near-333333
		a(href="#/mall") 商场
```

`/trainApp-V1/www/jade_template/pick.template.main.jade`

```
ion-view(title="精选")
	ion-content
		h1 pick-111111
		h2 pick-222222
		h3 pick-333333
```

`/trainApp-V1/www/jade_template/user.template.main.jade`

```
ion-view(title="我的")
	ion-content
		h1 user-111111
		h2 user-222222
		h3 user-333333
```

另开命令窗口执行: `gulp templates`

## 先处理app与tab容器

`/trainApp-V1/www/app/app.js`

```js
(function() {
  'use strict';
 	angular.module('app',['root','life','near','pick','user',
 		'cate','mall']);
})();
```

`/trainApp-V1/www/app/app.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('app');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app', {
        url: '',
        abstract: true,
        views: {
          '': {
            templateUrl: 'app/app.template.main.html',
          }
        }
      });  

      $stateProvider.state('app.tab', {
        url: '',
        abstract: true,
        views: {
          '': {
            templateUrl: 'app/app.template.tabs.html',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/app.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('app');
  currApp
    .controller('app.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```

`/trainApp-V1/www/app/life.js`

```js
(function() {
  'use strict';
 	angular.module('life',[]);
})();
```

`/trainApp-V1/www/app/life.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('life');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.life', {
        url: '/life',
        views: {
          'tab-life': {
            templateUrl: 'app/life.template.main.html',
            controller: 'life.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/life.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('life');
  currApp
    .controller('life.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```

`/trainApp-V1/www/app/near.js`

```js
(function() {
  'use strict';
 	angular.module('near',[]);
})();
```

`/trainApp-V1/www/app/near.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('near');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.near', {
        url: '/near',
        views: {
          'tab-near': {
            templateUrl: 'app/near.template.main.html',
            controller: 'near.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```


`/trainApp-V1/www/app/near.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('near');
  currApp
    .controller('near.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```

`/trainApp-V1/www/app/pick.js`

```js
(function() {
  'use strict';
 	angular.module('pick',[]);
})();
```

`/trainApp-V1/www/app/pick.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('pick');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.pick', {
        url: '/pick',
        views: {
          'tab-pick': {
            templateUrl: 'app/pick.template.main.html',
            controller: 'pick.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/pick.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('pick');
  currApp
    .controller('pick.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();

```

`/trainApp-V1/www/app/user.js`

```js
(function() {
  'use strict';
 	angular.module('user',[]);
})();
```

`/trainApp-V1/www/app/user.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('user');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.user', {
        url: '/user',
        views: {
          'tab-user': {
            templateUrl: 'app/user.template.main.html',
            controller: 'user.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/user.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('user');
  currApp
    .controller('user.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```

现在可以测试一下一级页面,
测试前检查一下 index.html

并修改

`/trainApp-V1/www/app/intro.controller.js`

```js

        vm.gotoHome = function(){
          $state.go('app.tab.life', {});
          $window.localStorage['intromain_isshowed'] = 'true';
        };

```

`/trainApp-V1/www/app/intro.js`

```js
(function() {
  'use strict';
 	angular.module('intro',['app','life']);
})();
```

并检查 

`/trainApp-V1/www/app/app.js` 依赖的模块


## 继续处理二级页面

`/trainApp-V1/www/app/cate.js`

```js
(function() {
  'use strict';
 	angular.module('cate',[]);
})();
```

`/trainApp-V1/www/app/cate.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('cate');
  currApp  	
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.cate', {
        url: '/cate',

        // 注意占位符的名称为 tab-life
        views: {
          'tab-life': {
            templateUrl: 'app/cate.template.main.html',
            controller: 'cate.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/cate.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('cate');
  currApp
    .controller('cate.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```


`%/trainApp-V1/www/app/mall.js`

```js
(function() {
  'use strict';
 	angular.module('mall',[]);
})();
```


`/trainApp-V1/www/app/mall.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('mall');
  currApp   
  .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('app.tab.mall', {
        url: '/mall',

        // 注意占位符的名称为 tab-near
        views: {
          'tab-near': {
            templateUrl: 'app/mall.template.main.html',
            controller: 'mall.mainController',
            controllerAs: 'vm',
          }
        }
      });  
  }]);
})();
```

`/trainApp-V1/www/app/mall.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('mall');
  currApp
    .controller('mall.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
      	var vm = this;
      }
    ]);
})();
```

## 增加通用方法的处理

`/trainApp-V1/www/app/root.js`

```js
(function() {
  'use strict';
 	angular.module('root',[]);
})();
```

`/trainApp-V1/www/app/root.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('root');
  currApp
    .controller('root.rootController', ['$rootScope', '$state', '$ionicHistory',
      function($rootScope, $state, $ionicHistory) {
        $rootScope.goBack = function() {
          $ionicHistory.goBack();
        };
      }
    ]);
})();
```

`/trainApp-V1/www/index.html`

```html
    <!-- your app's js -->
    <script src="js/main.js"></script>
    <script src="js/main.router.js"></script>
    <script src="app/root.js"></script>
    <script src="app/root.controller.js"></script>    
    <script src="app/intro.js"></script>
    <script src="app/intro.router.js"></script>
    <script src="app/intro.controller.js"></script>
    <script src="app/app.js"></script>
    <script src="app/app.router.js"></script>
    <script src="app/app.controller.js"></script>
    <script src="app/life.js"></script>
    <script src="app/life.router.js"></script>
    <script src="app/life.controller.js"></script>
    <script src="app/near.js"></script>
    <script src="app/near.router.js"></script>
    <script src="app/near.controller.js"></script>
    <script src="app/pick.js"></script>
    <script src="app/pick.router.js"></script>
    <script src="app/pick.controller.js"></script>
    <script src="app/user.js"></script>
    <script src="app/user.router.js"></script>
    <script src="app/user.controller.js"></script>
    <script src="app/cate.js"></script>
    <script src="app/cate.router.js"></script>
    <script src="app/cate.controller.js"></script>
    <script src="app/mall.js"></script>
    <script src="app/mall.router.js"></script>
    <script src="app/mall.controller.js"></script>

  </head>
  <body ng-app="main" ng-controller="root.rootController">
    <ion-nav-view></ion-nav-view> 
  </body>
```

`/trainApp-V1/www/js/main.js`

```js
  angular.module('main', ['ionic','intro','root'])
```

完成导航测试

## 说明

- 不使用系统自带的返回按钮
- 自已控制哪些页面有返回按钮