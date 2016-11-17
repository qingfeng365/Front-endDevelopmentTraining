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


## ui-router sample

### home

app.js : 55

```js
        .state("home", {

          // Use a url of "/" to set a state as the "index".
          url: "/",

          // Example of an inline template string. By default, templates
          // will populate the ui-view within the parent state's template.
          // For top level states, like this one, the parent template is
          // the index.html file. So this template will be inserted into the
          // ui-view within index.html.
          template: '<p class="lead">Welcome to the UI-Router Demo</p>' +
            '<p>Use the menu above to navigate. ' +
            'Pay attention to the <code>$state</code> and <code>$stateParams</code> values below.</p>' +
            '<p>Click these links—<a href="#/c?id=1">Alice</a> or ' +
            '<a href="#/user/42">Bob</a>—to see a url redirect in action.</p>'

        })


```

index.html : 64

```html
    <div ui-view class="container slide" style="padding-top: 80px;"></div>
```

注意: 总是要指定一个状态对应 url `/`

### about

略

### contacts

/app/contacts/contacts.js : 8

```js
      $stateProvider
        //////////////
        // Contacts //
        //////////////
        .state('contacts', {

          // With abstract set to true, that means this state can not be explicitly activated.
          // It can only be implicitly activated by activating one of its children.
          abstract: true,

          // This abstract state will prepend '/contacts' onto the urls of all its children.
          url: '/contacts',

          // Example of loading a template from a file. This is also a top level state,
          // so this template file will be loaded and then inserted into the ui-view
          // within index.html.
          templateUrl: 'app/contacts/contacts.html',

          // Use `resolve` to resolve any asynchronous controller dependencies
          // *before* the controller is instantiated. In this case, since contacts
          // returns a promise, the controller will wait until contacts.all() is
          // resolved before instantiation. Non-promise return values are considered
          // to be resolved immediately.
          resolve: {
            contacts: ['contacts',
              function( contacts){
                return contacts.all();
              }]
          },

          // You can pair a controller to your template. There *must* be a template to pair with.
          controller: ['$scope', '$state', 'contacts', 'utils',
            function (  $scope,   $state,   contacts,   utils) {

              // Add a 'contacts' field in this abstract parent's scope, so that all
              // child state views can access it in their scopes. Please note: scope
              // inheritance is not due to nesting of states, but rather choosing to
              // nest the templates of those states. It's normal scope inheritance.
              $scope.contacts = contacts;

              $scope.goToRandom = function () {
                var randId = utils.newRandomKey($scope.contacts, "id", $state.params.contactId);

                // $state.go() can be used as a high level convenience method
                // for activating a state programmatically.
                $state.go('contacts.detail', { contactId: randId });
              };
            }]
        })

```

```html
<div class="row">
  <div class="span3">
    <div class="pa-sidebar well well-small">
      <ul class="nav nav-list">
        <li ng-class="{ active: $state.includes('contacts.list') }"><a ui-sref="contacts.list">All Contacts</a></li>
        <li class="nav-header">Top Contacts</li>

        <!-- This <li> will only add the 'active' class if 'contacts.detail' or its descendants are active
             AND if it is the link for the active contact (aka contactId) -->
        <li ng-repeat="contact in contacts | limitTo:2" ui-sref-active="active">

          <!-- Here's a ui-sref that is also providing necessary parameters -->
          <a ui-sref="contacts.detail({contactId:contact.id})">{{contact.name}}</a>
        </li>
      </ul>
      <hr>
      <button class="btn" ng-click="goToRandom()">Show random contact</button>

      <!-- Another named view -->
      <div ui-view="menuTip" class="slide"></div>
    </div>
  </div>

  <!-- Our unnamed main ui-view for this template -->
  <div ui-view class="span9 slide"></div>
</div>

```


- abstract: 是抽象状态 
- resolve: 可被控制器依赖注入的数据 
- 理解切换状态(路由)的方法: 
  + `ui-sref="contacts.detail({contactId:contact.id})"` 
  + `$state.go('contacts.detail', { contactId: randId });` 


### contacts.list

/app/contacts/contacts.js : 64

```js
        .state('contacts.list', {

          // Using an empty url means that this child state will become active
          // when its parent's url is navigated to. Urls of child states are
          // automatically appended to the urls of their parent. So this state's
          // url is '/contacts' (because '/contacts' + '').
          url: '',

          // IMPORTANT: Now we have a state that is not a top level state. Its
          // template will be inserted into the ui-view within this state's
          // parent's template; so the ui-view within contacts.html. This is the
          // most important thing to remember about templates.
          templateUrl: 'app/contacts/contacts.list.html'
        })
```

```html
<h2>All Contacts</h2>
<ul>
  <li ng-repeat="contact in contacts">
    <a ui-sref="contacts.detail({contactId:contact.id})">{{contact.name}}</a>
  </li>
</ul>    
```

### contacts.detail

/app/contacts/contacts.js : 85

```js
        .state('contacts.detail', {

          // Urls can have parameters. They can be specified like :param or {param}.
          // If {} is used, then you can also specify a regex pattern that the param
          // must match. The regex is written after a colon (:). Note: Don't use capture
          // groups in your regex patterns, because the whole regex is wrapped again
          // behind the scenes. Our pattern below will only match numbers with a length
          // between 1 and 4.

          // Since this state is also a child of 'contacts' its url is appended as well.
          // So its url will end up being '/contacts/{contactId:[0-9]{1,4}}'. When the
          // url becomes something like '/contacts/42' then this state becomes active
          // and the $stateParams object becomes { contactId: 42 }.
          url: '/{contactId:[0-9]{1,4}}',

          // If there is more than a single ui-view in the parent template, or you would
          // like to target a ui-view from even higher up the state tree, you can use the
          // views object to configure multiple views. Each view can get its own template,
          // controller, and resolve data.

          // View names can be relative or absolute. Relative view names do not use an '@'
          // symbol. They always refer to views within this state's parent template.
          // Absolute view names use a '@' symbol to distinguish the view and the state.
          // So 'foo@bar' means the ui-view named 'foo' within the 'bar' state's template.
          views: {

            // So this one is targeting the unnamed view within the parent state's template.
            '': {
              templateUrl: 'app/contacts/contacts.detail.html',
              controller: ['$scope', '$stateParams', 'utils',
                function (  $scope,   $stateParams,   utils) {
                  $scope.contact = utils.findById($scope.contacts, $stateParams.contactId);
                }]
            },

            // This one is targeting the ui-view="hint" within the unnamed root, aka index.html.
            // This shows off how you could populate *any* view within *any* ancestor state.
            'hint@': {
              template: 'This is contacts.detail populating the "hint" ui-view'
            },

            // This one is targeting the ui-view="menuTip" within the parent state's template.
            'menuTip': {
              // templateProvider is the final method for supplying a template.
              // There is: template, templateUrl, and templateProvider.
              templateProvider: ['$stateParams',
                function (        $stateParams) {
                  // This is just to demonstrate that $stateParams injection works for templateProvider.
                  // $stateParams are the parameters for the new state we're transitioning to, even
                  // though the global '$stateParams' has not been updated yet.
                  return '<hr><small class="muted">Contact ID: ' + $stateParams.contactId + '</small>';
                }]
            }
          }
        })
```

- url: 使用 /:contactId 或 /{contactId:} 均可,后者可使用正则表达式
- 实际url: 父状态的url+'/'+子状态的url
- 视图名定位规则:
  + 相对定位: 找父状态的模板对应的视图占位符
  + 绝对定位: 视图名@状态名, 找@状态名所属的模板对应的视图占位符,@后面省略状态,表示顶态状态(即空状态-> html)
- 提供模板内容的三种方式: template, templateUrl, and templateProvider

```html
<div>
  <h2>{{contact.name}}</h2>
  <ul>
    <li ng-repeat="item in contact.items">

      <!-- Here's another ui-sref except THIS time we are passing parameters
           AND inheriting parameters from active ancestor states. So we don't
           need to resupply the contactId parameter. -->
      <a ui-sref="contacts.detail.item({itemId:item.id})">{{item.type}}</a>
    </li>
  </ul>
  <div ui-view class="slide">
    <!-- Example of default content. This content will be replace as soon as
         this ui-view is populate with a template -->
    <small class="muted">Click on a contact item to view and/or edit it.</small>
  </div>
</div>
```



### contacts.detail.item

```js
        .state('contacts.detail.item', {

          // So following what we've learned, this state's full url will end up being
          // '/contacts/{contactId}/item/:itemId'. We are using both types of parameters
          // in the same url, but they behave identically.
          url: '/item/:itemId',
          views: {

            // This is targeting the unnamed ui-view within the parent state 'contact.detail'
            // We wouldn't have to do it this way if we didn't also want to set the 'hint' view below.
            // We could instead just set templateUrl and controller outside of the view obj.
            '': {
              templateUrl: 'app/contacts/contacts.detail.item.html',
              controller: ['$scope', '$stateParams', '$state', 'utils',
                function (  $scope,   $stateParams,   $state,   utils) {
                  $scope.item = utils.findById($scope.contact.items, $stateParams.itemId);

                  $scope.edit = function () {
                    // Here we show off go's ability to navigate to a relative state. Using '^' to go upwards
                    // and '.' to go down, you can navigate to any relative state (ancestor or descendant).
                    // Here we are going down to the child state 'edit' (full name of 'contacts.detail.item.edit')
                    $state.go('.edit', $stateParams);
                  };
                }]
            },

            // Here we see we are overriding the template that was set by 'contacts.detail'
            'hint@': {
              template: ' This is contacts.detail.item overriding the "hint" ui-view'
            }
          }
        })
```

```html
<hr>
<h4>{{item.type}} <button class="btn" ng-click="edit()">Edit</button></h4>
<div>{{item.value}}</div>
```

#### contacts.detail.item.edit



```js

        // Notice that this state has no 'url'. States do not require a url. You can use them
        // simply to organize your application into "places" where each "place" can configure
        // only what it needs. The only way to get to this state is via $state.go (or transitionTo)
        .state('contacts.detail.item.edit', {
          views: {

            // This is targeting the unnamed view within the 'contacts.detail' state
            // essentially swapping out the template that 'contacts.detail.item' had
            // inserted with this state's template.
            '@contacts.detail': {
              templateUrl: 'app/contacts/contacts.detail.item.edit.html',
              controller: ['$scope', '$stateParams', '$state', 'utils',
                function (  $scope,   $stateParams,   $state,   utils) {
                  $scope.item = utils.findById($scope.contact.items, $stateParams.itemId);
                  $scope.done = function () {
                    // Go back up. '^' means up one. '^.^' would be up twice, to the grandparent.
                    $state.go('^', $stateParams);
                  };
                }]
            }
          }
        });

```

- 没有定义url, 只能通过状态跳转, 即这种状态是不可以被收藏的
- @contacts.detail: 绝对定位, 因为要把父状态的模板全部替换

