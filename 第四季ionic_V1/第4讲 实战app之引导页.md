# 第4讲 实战app之引导页

## 引导页

### 设置缺省路由

`/trainApp-V1/www/js/main.router.js`

```js
(function(){
  'use strict';

  var currApp = angular.module('main');

  /**
   * 定义顶级缺省路由
   */
  currApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

  }]);


})();


```



### 创建引导页视图

`/trainApp-V1/www/jade_template/intro.template.main.jade`

```jade
ion-view
	//- 旧模式,并要对样式做一点设置
	//- ion-slide-box
	//- 	ion-slide
	//- 		img(src="img/引导1.png")
	//- 	ion-slide
	//- 		img(src="img/引导2.png")
	//- 	ion-slide
	//- 		img(src="img/引导3.png")

	//- 使用ion-slides模式,slider是不可以少的,slider是指定在$scope中存储swiper实例的变量
	ion-slides(options="vm.options" slider="vm.slider" style="background-color:#19BA99")
		ion-slide-page(style="text-align: center;")
			img(src="img/引导1.png")
		ion-slide-page
			img(src="img/引导2.png")
		ion-slide-page
			img(src="img/引导3.png")
```

[ion-slides.md](ion-slides.md)

ion-slides 是利用swiper插件,因此部分设置还需要看swiper api

http://idangero.us/swiper/api/#.WGnhtJKdrCg


### 创建引导页模块

`/trainApp-V1/www/app/intro.js`

```js
(function() {
  'use strict';
 	angular.module('intro',[]);
})();
```

### 创建引导页路由

`/trainApp-V1/www/app/intro.router.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('intro');
  currApp
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
      $stateProvider.state('intro', {
        url: '/',
        views: {
          '': {
            templateUrl: 'app/intro.template.main.html',
            controller: 'intro.mainController',
            controllerAs: 'vm',
          }
        }
      });
    }]);
})();
```


### 创建引导页控制器


`/trainApp-V1/www/app/intro.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('intro');
  currApp
    .controller('intro.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
        var vm = this;

        //ion-slides 是利用swiper插件,因此部分设置还需要看swiper api
        //http://idangero.us/swiper/api/#.WGnhtJKdrCg
        vm.options = {
          loop: false,
          effect: 'fade',
          speed: 500,
        };
      }
    ]);
})();

```

### 引用js

`/trainApp-V1/www/index.html`

```html
    <!-- your app's js -->
    <script src="js/main.js"></script>
    <script src="js/main.router.js"></script>
    <script src="app/intro.js"></script>
    <script src="app/intro.router.js"></script>
    <script src="app/intro.controller.js"></script>
```

以后每增加一个js文件,都需要增加引用,引用步骤不再特别说明


### 引用模块

`/trainApp-V1/www/js/main.js`

```js
	//...
  angular.module('main', ['ionic','intro'])
  //...
```


运行查看结果


## 增加引导页跳转到首页功能

### 首页视图

`/trainApp-V1/www/jade_template/app.template.main.jade`

```jade
ion-view
	h1 这是首页
```

### 首页相关文件

`/trainApp-V1/www/app/app.js`

```js
(function() {
  'use strict';
 	angular.module('app',[]);
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
        url: '/app',
        views: {
          '': {
            templateUrl: 'app/app.template.main.html',
            controller: 'app.mainController',
            controllerAs: 'vm',
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

### 增加引用模块

`/trainApp-V1/www/app/intro.js`

```js
(function() {
  'use strict';
 	angular.module('intro',['app']);
})();
```

### 处理只显示一次引导页和跳转到首页

`/trainApp-V1/www/app/intro.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('intro');
  currApp
    .controller('intro.mainController', ['$scope', '$state', '$ionicHistory',
      '$window',
      function($scope, $state, $ionicHistory, $window) {
        var vm = this;

        //ion-slides 是利用swiper插件,因此部分设置还需要看swiper api
        //http://idangero.us/swiper/api/#.WGnhtJKdrCg
        vm.options = {
          loop: false,
          effect: 'fade',
          speed: 500,
        };

        vm.gotoHome = function(){
          $state.go('app', {});
          $window.localStorage['intromain_isshowed'] = 'true';
        };

        
        console.log('localStorage["intromain_isshowed"]:' + $window.localStorage['intromain_isshowed']);

        /**
         * 如果想每次打开均见到引导页,则取消下面的注释
         */
        // $window.localStorage['intromain_isshowed'] = 'false';

        // $window.localStorage 只能存储字符串
        if ($window.localStorage['intromain_isshowed'] === 'true'){
          // 表示引导页已被执行过,直接跳转
          vm.gotoHome();
        }else{
          //如果没有被执行过,则等用户手动跳转          
        }
      }
    ]);
})();

```

`/trainApp-V1/www/jade_template/intro.template.main.jade`

```jade
ion-view
	//- 旧模式,并要对样式做一点设置
	//- ion-slide-box
	//- 	ion-slide
	//- 		img(src="img/引导1.png")
	//- 	ion-slide
	//- 		img(src="img/引导2.png")
	//- 	ion-slide
	//- 		img(src="img/引导3.png")

	//- 使用ion-slides模式,slider是不可以少的,slider是指定在$scope中存储swiper实例的变量
	ion-slides(options="vm.options" slider="vm.slider" style="background-color:#19BA99")
		ion-slide-page(style="text-align: center;")
			img(src="img/引导1.png")
		ion-slide-page
			img(src="img/引导2.png")
		ion-slide-page
			img(src="img/引导3.png",ng-click="vm.gotoHome()")
```

`/trainApp-V1/www/index.html`

```html
    <!-- your app's js -->
    <script src="js/main.js"></script>
    <script src="js/main.router.js"></script>
    <script src="app/intro.js"></script>
    <script src="app/intro.router.js"></script>
    <script src="app/intro.controller.js"></script>
    <script src="app/app.js"></script>
    <script src="app/app.router.js"></script>
    <script src="app/app.controller.js"></script>
```

测试

## 说明

- 通常第一个界面并不是引导页,而是欢迎页
- 再由欢迎页判断是转引导页,还是首页
- 全屏一般不会直接使用图片做全屏,因为要对图片做响应式处理比较麻烦
- 应由样式处理全屏效果,图片只是局部元素