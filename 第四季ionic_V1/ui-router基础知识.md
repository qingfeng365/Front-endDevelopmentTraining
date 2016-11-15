# ui-router基础知识

### 与ionic相关的ui-router

ionic1 使用的 ui-router 版本是 0.2.13

因此在阅读ui-router文档,要注意版本

[0.2.x api](https://ui-router.github.io/docs/0.2.14/index.html#/api/ui.router.state) https://ui-router.github.io/docs/0.2.14/index.html#/api/ui.router.state

[官方三个小例子](https://ui-router.github.io/tutorial/ng1/helloworld)https://ui-router.github.io/tutorial/ng1/helloworld

[官方指南](https://github.com/angular-ui/ui-router/wiki)https://github.com/angular-ui/ui-router/wiki

### 为什么要使用前端路由


### 前端路由与后端路由差别

相同点:

- 都有url(#)
- :路由参数
- 条件串(一般不使用,改为直接传对象)

不同点:

- 不需要指定方法
- 没有post body 概念,但可以直接传对象
- 不产生页面切换(浏览器不会产生向后台访问的行为)

### 前端路由特殊点

url 与 视图模板(内容) 与 视图(占位符) 的关系

视图模板(内容) : 相当于 后台响应的 html 内容

视图(占位符) : 相当于 前端浏览器当前窗口

### ui-router对前端路由的扩展

#### 状态

- 状态对应url (末级状态对应url)
- 状态可有层级
- 视图有名称(占位符带特殊标志)


### hello world

index.html

```html
<html>
  <head>
    <script src="/bower_components/angular/angular.js"></script>
    <script src="/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
    <script src="/static/public/ui-routerdemo/HelloWorld/helloworld.js"></script>

    <style>.active { color: red; font-weight: bold; }</style>
  </head>

  <body ng-app="helloworld">
    <a ui-sref="hello" ui-sref-active="active">Hello</a>
    <a ui-sref="about" ui-sref-active="active">About</a>

    <ui-view></ui-view>
  </body>
</html>
```


helloworld.js

```js
var myApp = angular.module('helloworld', ['ui.router']);

myApp.config(function($stateProvider) {
  var helloState = {
    name: 'hello',
    url: '/hello',
    template: '<h3>hello world!</h3>'
  }

  var aboutState = {
    name: 'about',
    url: '/about',
    template: '<h3>Its the UI-Router hello world app!</h3>'
  }

  $stateProvider.state(helloState);
  $stateProvider.state(aboutState);
});
```

### 机制说明

- ui-sref : 相当于 href, href 指向后端路由, ui-sref 指向前端路由(状态)
- $stateProvider.state : 相当于后端控制器注册的路由定义
	但定义的内容要多一些,包括 状态 路由 视图内容(模板)
- 视图模板计算后生成的html片断 会填充到页面的哪个位置的计算方式:

	`找父状态的模板对应的视图占位符` , 这种说法不是很严谨,因为要根据定义视图名规则而定.

	但通常情况下可以这样理解,不需要理解非常复杂的视图名定义规则.



