# 第2讲 实现tabs项目(1)

以下内容均在 第一讲, 使用 blank模板创建的 tabsMe 项目中实现.

### 先观察tabsMe 的 index.html

```html
  <body ng-app="starter">
    <ion-pane>
      <ion-header-bar class="bar-stable">
        <h1 class="title">Ionic Blank Starter</h1>
      </ion-header-bar>
      <ion-content>
      </ion-content>
    </ion-pane>
  </body>  
```

ion-pane: 简单的页面容器,一般情况下不会使用.

所谓页面容器,就是指自动充满整个屏

#### 附加阅读(不需要理解)

ion-pane 指令会加入 .pane css类

`/www/lib/ionic/css/ionic.css`

```css
.pane {
  -webkit-transform: translate3d(0, 0, 0);
  transform: translate3d(0, 0, 0);
  -webkit-transition-duration: 0;
  transition-duration: 0;
  z-index: 1; }

.pane,
.view {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #fff;
  overflow: hidden; }  
```

translate3d(0, 0, 0) : 移动端触发GPU加速常用技巧

### 清除初始设置,改用ionNavView占位符

```html
  <body ng-app="starter">
    <ion-nav-view></ion-nav-view>
  </body>
```

ion-nav-view 指令会加入 `.view-container` css类

:3563

```css
.view-container {
  position: absolute;
  display: block;
  width: 100%;
  height: 100%; }
```

在index.html使用ionNavView的意义:

- 在屏幕看到的所有内容均需要通过 **占据ionNavView** 来显示,

ionNavView的功能:

- 能记忆 **占据ionNavView** 的所有视图的 占据顺序,
- 并能控制 `ion-nav-bar`
- ion-nav-view = ui-view, 但如果需要对视图占位符命名时,方式有所不同,
- ion-nav-view因为是元素标签,所以使用name属性命名

### 增加导航条ion-nav-bar

```html
  <body ng-app="starter">
    <ion-nav-bar class="bar-stable">
      <ion-nav-back-button>
      </ion-nav-back-button>
    </ion-nav-bar>
    <ion-nav-view>
      <p>1</p>
      <p>2</p>
      <p>3</p>
    </ion-nav-view>
  </body>

```

布局说明:

- ion-nav-view 和 ion-nav-bar(内部的子元素.nav-bar-block) 都是绝对定位
- ion-nav-bar 的z-index 高于 ion-nav-view
- ion-nav-bar 会盖住 ion-nav-view 44px高度


#### ion-nav-bar实际结构  附加阅读(不需要理解)

ion-nav-bar 生成的html

```html
<ion-nav-bar class="bar-stable nav-bar-container" nav-bar-transition="ios">
  <ion-nav-back-button class="hide"></ion-nav-back-button>
  <div class="nav-bar-block" nav-bar="cached">
    <ion-header-bar class="bar-stable bar bar-header" align-title="center">
      <button ng-click="$ionicGoBack()" class="button back-button hide buttons  button-clear header-item"><i class="icon ion-ios-arrow-back"></i>
        <span class="back-text"><span class="default-title">Back</span><span class="previous-title hide"></span></span>
      </button>
      <div class="title title-center header-item"></div>
    </ion-header-bar>
  </div>
  <div class="nav-bar-block" nav-bar="active">
    <ion-header-bar class="bar-stable bar bar-header" align-title="center">
      <button ng-click="$ionicGoBack()" class="button back-button hide buttons  button-clear header-item"><i class="icon ion-ios-arrow-back"></i>
        <span class="back-text"><span class="default-title">Back</span><span class="previous-title hide"></span></span>
      </button>
      <div class="title title-center header-item"></div>
    </ion-header-bar>
  </div>
</ion-nav-bar>

```

### ion-nav-bar 功能理解 

- 全局工具栏占位符,所有期望有工具栏的页面,提供工具栏的配置,实际显示位置是在ion-nav-bar显示
- 受ion-nav-view控制,可自动处理 返回按钮
- 绝对定位,且会盖住 ion-nav-view 44px高度,因此页面内容在显示时需要考虑处理这个高度

### 增加底部导航状态和视图

先清除ion-nav-view的内容

index.html

```html
  <body ng-app="starter">
    <ion-nav-bar class="bar-stable">
      <ion-nav-back-button>
      </ion-nav-back-button>
    </ion-nav-bar>
    <ion-nav-view></ion-nav-view>
  </body>
```

增加路由定义:

app.js 

```js
.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('tab', {
    url: '/tab',
    templateUrl: 'templates/tabs.html'
  })
})
```

在 www 目录下创建 `templates` 目录

在`templates` 目录下创建 `tabs.html`

tabs.html

```html
<ion-tabs class="tabs-icon-top tabs-color-active-positive">

  <ion-tab title="Status" icon-off="ion-ios-pulse" icon-on="ion-ios-pulse-strong" href="#/tab/dash">
  </ion-tab>

  <ion-tab title="Chats" icon-off="ion-ios-chatboxes-outline" icon-on="ion-ios-chatboxes" href="#/tab/chats">
  </ion-tab>

  <ion-tab title="Account" icon-off="ion-ios-gear-outline" icon-on="ion-ios-gear" href="#/tab/account">
  </ion-tab>

</ion-tabs>
```

观察效果

```js
.config(function($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/tab');
  
  $stateProvider
    .state('tab', {
    url: '/tab',
    templateUrl: 'templates/tabs.html'
  })
})
```


### ion-tabs的说明

实际生成的html为三层结构

```html
<ion-tabs class="tabs-icon-top tabs-color-active-positive pane tabs-bottom tabs-standard" state="tab" nav-view="active" style="opacity: 1; transform: translate3d(0%, 0px, 0px);">
  <div class="tab-nav tabs">
    <ion-tab icon-off="ion-ios-pulse" icon-on="ion-ios-pulse-strong" href="#/tab/dash"></ion-tab>
    <ion-tab icon-off="ion-ios-chatboxes-outline" icon-on="ion-ios-chatboxes" href="#/tab/chats"></ion-tab>
    <ion-tab icon-off="ion-ios-gear-outline" icon-on="ion-ios-gear" href="#/tab/account"></ion-tab>
    <a ng-class="{'has-badge':badge, 'tab-hidden':isHidden(), 'tab-item-active': isTabActive()}" ng-disabled="disabled()" class="tab-item tab-item-active" icon-on="ion-ios-pulse-strong" icon-off="ion-ios-pulse" style="">
      <!-- ngIf: badge -->
      <!-- ngIf: getIcon() --><i class="icon ion-ios-pulse-strong" ng-if="getIcon()"></i>
      <!-- end ngIf: getIcon() --><span class="tab-title ng-binding" ng-bind-html="title">Status</span></a>
    <a ng-class="{'has-badge':badge, 'tab-hidden':isHidden(), 'tab-item-active': isTabActive()}" ng-disabled="disabled()" class="tab-item" icon-on="ion-ios-chatboxes" icon-off="ion-ios-chatboxes-outline">
      <!-- ngIf: badge -->
      <!-- ngIf: getIcon() --><i class="icon ion-ios-chatboxes-outline" ng-if="getIcon()"></i>
      <!-- end ngIf: getIcon() --><span class="tab-title ng-binding" ng-bind-html="title">Chats</span></a>
    <a ng-class="{'has-badge':badge, 'tab-hidden':isHidden(), 'tab-item-active': isTabActive()}" ng-disabled="disabled()" class="tab-item" icon-on="ion-ios-gear" icon-off="ion-ios-gear-outline">
      <!-- ngIf: badge -->
      <!-- ngIf: getIcon() --><i class="icon ion-ios-gear-outline" ng-if="getIcon()"></i>
      <!-- end ngIf: getIcon() --><span class="tab-title ng-binding" ng-bind-html="title">Account</span></a>
  </div>
</ion-tabs>

```

### 实现第一个tab

app.js

```
  .state('tab.dash', {
    url: '/dash',
    templateUrl: 'templates/tab-dash.html',
    }
  )
```

创建 `/www/templates/tab-dash.html`

```html
<ion-view view-title="Dashboard">
  <ion-content class="padding">
    <h2>Welcome to Ionic</h2>
    <p>
    This is the Ionic starter for tabs-based apps. For other starters and ready-made templates, check out the <a href="http://market.ionic.io/starters" target="_blank">Ionic Market</a>.
    </p>
    <p>
      To edit the content of each tab, edit the corresponding template file in <code>www/templates/</code>. This template is <code>www/templates/tab-dash.html</code>
    </p>
    <p>
    If you need help with your app, join the Ionic Community on the <a href="http://forum.ionicframework.com" target="_blank">Ionic Forum</a>. Make sure to <a href="http://twitter.com/ionicframework" target="_blank">follow us</a> on Twitter to get important updates and announcements for Ionic developers.
    </p>
    <p>
      For help sending push notifications, join the <a href="https://apps.ionic.io/signup" target="_blank">Ionic Platform</a> and check out <a href="http://docs.ionic.io/docs/push-overview" target="_blank">Ionic Push</a>. We also have other services available.
    </p>
  </ion-content>
</ion-view>
```

测试一下,思考会出现什么结果?


### 修改tabs状态模板

`/www/templates/tabs.html`

```html
  <ion-tab title="Status" icon-off="ion-ios-pulse" icon-on="ion-ios-pulse-strong" href="#/tab/dash">
    <ion-nav-view></ion-nav-view>
  </ion-tab>
```

测试一下,并注意观察以下几点:

- 模板中`ion-tab`内部的`ion-nav-view` 与 实际生成的html有什么区别
- 标题如何显示

### 了解ion-content的意义

使用了`ion-view`之后,为什么一般都要结合使用 `ion-content` ?

`<ion-content class="padding">` 还可以写成 `<ion-content padding>`

`ion-content` 相关css类

:3533

```css
.scroll-content {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;
  margin-top: -1px;
  padding-top: 1px;
  margin-bottom: -1px;
  width: auto;
  height: auto; }
```

```css
.has-header {
    top: 44px;
}

.has-tabs, .bar-footer.has-tabs {
    bottom: 49px;
}

.padding {
    padding: 10px;
}
```

### 如何解决ios与android的tabs风格不统一的问题

app.js

```
.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {

  $ionicConfigProvider.tabs.position('bottom');
  $ionicConfigProvider.tabs.style('standard');
```

[$ionicConfigProvider](http://ionicframework.com/docs/api/provider/$ionicConfigProvider/)http://ionicframework.com/docs/api/provider/$ionicConfigProvider/


