# ionNavView常见问题

## 源码说明

`/www/lib/ionic/js/ionic-angular.js`

ionNavView (占位符)

:12163

```
IonicModule
.directive('ionNavView', [
```

$ionicNavView (ionNavView 的 控制器)

:6455

```
IonicModule
.controller('$ionicNavView', [
```

ionView (页面容器)

:14384

```
IonicModule
.directive('ionView', function() {
```

$ionicView (ionView 控制器)

:8616

```
IonicModule
.controller('$ionicView', [
  '$scope',
```



## 常见问题

### 如何禁用视图缓存

#### 全局禁用

```js
 $ionicConfigProvider.views.maxCache(0);
```

#### 指定状态禁用

```js
 $stateProvider.state('myState', {
    cache: false,
    url : '/myUrl',
    templateUrl : 'my-template.html'
 })
```

#### 在视图模板中设置禁用

```html
<ion-view cache-view="false" view-title="My Title!">
 ...
</ion-view>
```

### 关于resolve

```
 Note: We do not recommend using [resolve](https://github.com/angular-ui/ui-router/wiki#resolve)
 of AngularUI Router. The recommended approach is to execute any logic needed before beginning the state transition.
```

> 不推荐使用 resolve, 应该是状态切换前自已准备好需要的数据


### ionNavView 与 ionView 的关系

ionNavView 做为视图占位符, = ui-view

ionView 是页面容器, = div(页面级) 或 panel

ionView 会向ionNavView 传播消息, 用来控制 标题,返回按钮

ionView 提供页面切换时的动画效果

