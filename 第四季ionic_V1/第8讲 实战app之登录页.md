# 第8讲 实战app之登录页

## 实现以下功能

- 在状态变换前判断是否需要登录
- 需要登录时,弹出模态视图

## ionic 模态视图特点

- 是单独全屏的视图
- 需要自已处理视图的全部布局
- 要先创建模态视图对象
- 模态视图对象可以一直存在,重复使用

## 创建登录模态视图

### 视图

`/trainApp-V1/www/jade_template/loginModal.jade`

```jade
ion-modal-view
	//- 设置align-title是因为ios, android默认样式不一样
	ion-header-bar.bar.bar-header.bar-stable(align-title="center")
		h1.title 用户登录
		button.button.button-clear.button-primary.icon.ion-close(
			ng-click="close()")
	ion-content
		.list
			.item.item-input
				i.icon.ion-ios-person-outline.positive
				input(type="text" placeholder="请输入您的用户名" ng-model="input.userName")
			.item.item-input
				i.icon.ion-ios-unlocked-outline.positive
				input(type="password" placeholder="请输入您的密码" ng-model="input.password")
		.padding-horizontal
			button.button.button-block.button-balanced(
				ng-click="login()") 登录
```

### 创建有关登录与权限的公共服务

`/trainApp-V1/www/app/app.service.js`

```js
  currApp.service('appAuthService', ['$q', function($q){
    this.loginUser = {
      islogined: false,
      userId:'',
      userName:'',
    };
  }]);
```

### 监听状态转换事件

ui-router在状态改变前后,均会广播事件,因此可用$rootScope监听事件

下面代码是演示,当点击生活首页店铺的某个产品时,要求先登录,才能显示出产品的详情页.

`/trainApp-V1/www/app/root.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('root');
  currApp
    .controller('root.rootController', ['$rootScope', '$state', '$ionicHistory',
      '$window', 'appCartService', '$ionicModal', 'appAuthService',
      function($rootScope, $state, $ionicHistory, $window,
        appCartService, $ionicModal, appAuthService) {
        $rootScope.goBack = function() {
          $ionicHistory.goBack();
        };

        $rootScope.gotoUrl = function(targetURL) {
          $window.location.href = targetURL;
        };

        $rootScope.shoppingCart = appCartService.shoppingCart;

        // 创建公用的登录模态视图

        var loginModal = null;

        // 为登录模态视图创建独立的Scope,如果是普通的模态窗口,
        // 不需要这样处理,直接使用调用者自身的$scope一般就可以了
        // 这里是因为有很多地方调用,避免混乱,因为ionic的模态窗口,
        // 不能指定controller,只能指定scope
        var loginModalScope = $rootScope.$new();
        loginModalScope.input = {
          userName:'',
          password:'',
        };
        // 初始化登录模态视图所使用的方法和变量
        // 
        // 登录模态视图的关闭按钮的方法
        loginModalScope.close = function() {
          loginModal.hide();
        };

        loginModalScope.login = function() {
          // 判断登录是否成功,此处省略
          var isOk = (loginModalScope.input.userName !== '');

          // 登录成功就跳转,不成功就禁止状态转换
          if (isOk) {
            // 此处有一系列登录成功后的初始化工作,
            // 如处理登录用户对象,购物车对象,用户中心等等

            // 如果登录成功,就初始化
            appAuthService.loginUser.islogined = true;
            appAuthService.loginUser.userId = 1;
            appAuthService.loginUser.userName = 'user1';

            loginModal.hide();
            console.log('准备跳转:');
            console.log(loginModalScope.next);

            if(loginModalScope.next.toState){
              $state.go(loginModalScope.next.toState.name,
                loginModalScope.next.toParams || {});
            } else {
              var nextURL = loginModalScope.next.nextURL || '#/';
              $window.location.href = nextURL;
            }

          } else {
            //如果不成功,应提示错误信息
            console.log('登录信息不正确!!!!');
          }
        };


        // 创建模态视图对象
        // 注意:
        // 模态视图对象中实际scope,是利用传进去的scope创建的子scope,
        // 即实际scope的父级scope才是loginModalScope,
        // 因此用于编辑框的ng-model必须是对象的属性,如 input.xxxx
        // 不可以直接使用普通变量
        $ionicModal.fromTemplateUrl('app/loginModal.html', {
          scope: loginModalScope
        }).then(function(loginModalobj) {
          console.log('loginModal create.....');
          loginModal = loginModalobj;
        });

        // 清除模态视图对象
        $rootScope.$on('$destroy', function() {
          loginModal.remove();
        });

        // 调用弹窗的方法
        /**
         * [openLoginModal 打开登录视图]
         * @param  {object} next [登录成功后要跳转的配置对象]
         * @return {[type]}         [description]
         *
         * next格式:
         *
         * {
         *   toState: {}, 
         *   toParams: {},
         *   nextUrl: '',
         * }
         * 
         */
        $rootScope.openLoginModal = function(next) {
          loginModalScope.next = next;
          loginModal.show();
        };

        // 拦截状态变更开始事件
        // https://ui-router.github.io/ng1/docs/0.3.1/index.html#/api/ui.router.state.$state
        // 相关事件: $stateChangeError $stateChangeStart $stateChangeSuccess $stateNotFound
        // 
        // 
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
          console.log('toState:');
          console.log(toState);
          console.log('toParams:');
          console.log(toParams);
          console.log('fromState:');
          console.log(fromState);
          console.log('fromParams:');
          console.log(fromParams);

          // 判断是否为需要登录的事件
          // 此处应用独立的服务处理哪个状态应该需要登录

          // 判断过程
          // - 状态是否需要登录
          // -- 如果需要登录
          // --- 目前是否已经登录
          // ----- 如果已经登录,不做处理
          // ----- 如果没有登录,禁止事件处理,弹出登录视图
          // -- 如果不需要登录,不做处理

          if(toState.name === 'app.productDetail'){
            // 该状态需要登录

            if(!(appAuthService.loginUser && appAuthService.loginUser.islogined)){
              // 当前没有登录

              //未登录，阻止页面跳转。
              event.preventDefault();

              $rootScope.openLoginModal({
                toState:toState,
                toParams:toParams,
              });
            }
          }
        });
      }
    ]);
})();

```

## 使用模态窗口提示错误信息($ionicPopup)

### 模态窗口($ionicPopup)与模态视图($ionicModal)的区别

- 模态视图是全屏的,与当前视图有切换动画,当前视图隐藏
- 模态窗口是在当前视图前有一阴影蒙板,视图显示部分只占据屏幕中间一小块
- 模态窗口弹出时,当前视图是不变化的
- 模态窗口有默认的几种系统内置的对话框alert prompt confirm,也可以自定义


### 修改login代码

`/trainApp-V1/www/app/root.controller.js`

```js
  currApp
    .controller('root.rootController', ['$rootScope', '$state', '$ionicHistory',
      '$window', 'appCartService', '$ionicModal', 'appAuthService',
      '$ionicPopup',
      function($rootScope, $state, $ionicHistory, $window,
        appCartService, $ionicModal, appAuthService,
        $ionicPopup) {
```

```js
        loginModalScope.login = function() {
          // 判断登录是否成功,此处省略
          var isOk = (loginModalScope.input.userName !== '');

          // 登录成功就跳转,不成功就禁止状态转换
          if (isOk) {
            // 此处有一系列登录成功后的初始化工作,
            // 如处理登录用户对象,购物车对象,用户中心等等

            // 如果登录成功,就初始化
            appAuthService.loginUser.islogined = true;
            appAuthService.loginUser.userId = 1;
            appAuthService.loginUser.userName = 'user1';

            loginModal.hide();
            console.log('准备跳转:');
            console.log(loginModalScope.next);

            if(loginModalScope.next.toState){
              $state.go(loginModalScope.next.toState.name,
                loginModalScope.next.toParams || {});
            } else {
              var nextURL = loginModalScope.next.nextURL || '#/';
              $window.location.href = nextURL;
            }

          } else {
            //如果不成功,应提示错误信息
            console.log('登录信息不正确!!!!');

            // title template okText 都可以使用html片断
            // 以显示特殊效果
            $ionicPopup.alert({
              title:'提示',
              template:'请输入用户名和密码...',
              okText:'确定',
              okType:'button-assertive'
            })
            .then(function(result){
              console.log(result);
            });

          }
        };
```