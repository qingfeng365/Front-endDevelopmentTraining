# 第2讲 实现tabs项目(2)

以下内容均在 第一讲, 使用 blank模板创建的 tabsMe 项目中实现.

### 增加第二个tab的路由

app.js

```js
  $stateProvider
    .state('tab', {
      url: '/tab',
      abstract: true,
      templateUrl: 'templates/tabs.html'
    })
    .state('tab.dash', {
      url: '/dash',
      views: {
        'tab-dash': {
          templateUrl: 'templates/tab-dash.html',
        }
      }
    })
    .state('tab.chats', {
      url: '/chats',
      views: {
        'tab-chats': {
          templateUrl: 'templates/tab-chats.html',
          controller: 'ChatsCtrl'
        }
      }
    })
```

templates/tab-chats.html

```html
<ion-view view-title="Chats">
  <ion-content>
    <ion-list>
      <ion-item>
        <p>1</p>
      </ion-item>
      <ion-item>
        <p>2</p>
      </ion-item>
      <ion-item>
        <p>3</p>
      </ion-item>
    </ion-list>
  </ion-content>
</ion-view>
```

增加 `/www/js/controllers.js`

```js
angular.module('starter.controllers', [])

.controller('ChatsCtrl', function($scope) {
  
})

```

修改 index.html

```html
    <!-- your app's js -->
    <script src="js/app.js"></script>
    <script src="js/controllers.js"></script>
```

修改 tabs.html

```html
<ion-tabs class="tabs-icon-top tabs-color-active-positive">
  <ion-tab title="Status" icon-off="ion-ios-pulse" icon-on="ion-ios-pulse-strong" href="#/tab/dash">
    <ion-nav-view name="tab-dash"></ion-nav-view>
  </ion-tab>
  <ion-tab title="Chats" icon-off="ion-ios-chatboxes-outline" icon-on="ion-ios-chatboxes" href="#/tab/chats">
    <ion-nav-view name="tab-chats"></ion-nav-view>
  </ion-tab>
  <ion-tab title="Account" icon-off="ion-ios-gear-outline" icon-on="ion-ios-gear" href="#/tab/account">
  </ion-tab>
</ion-tabs>
```

### ion-list

[ion-list api](http://ionichina.cn/docs/api/directive/ionList/)http://ionichina.cn/docs/api/directive/ionList/

- ion-list的用途非常广泛,要结合css一起看
- 跟 card 的css结合,可形成更多用途

[ion-list 样式](http://ionichina.cn/docs/components/#list)http://ionichina.cn/docs/components/#list

### 进一步完善chats

新建 `/www/js/services.js`

```js
angular.module('starter.services', [])

.factory('Chats', function() {
  var chats = [{
    id: 0,
    name: 'Ben Sparrow',
    lastText: 'You on your way?',
    face: 'img/ben.png'
  }, {
    id: 1,
    name: 'Max Lynx',
    lastText: 'Hey, it\'s me',
    face: 'img/max.png'
  }, {
    id: 2,
    name: 'Adam Bradleyson',
    lastText: 'I should buy a boat',
    face: 'img/adam.jpg'
  }, {
    id: 3,
    name: 'Perry Governor',
    lastText: 'Look at my mukluks!',
    face: 'img/perry.png'
  }, {
    id: 4,
    name: 'Mike Harrington',
    lastText: 'This is wicked good ice cream.',
    face: 'img/mike.png'
  }];

  return {
    all: function() {
      return chats;
    },
    remove: function(chat) {
      chats.splice(chats.indexOf(chat), 1);
    },
    get: function(chatId) {
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          return chats[i];
        }
      }
      return null;
    }
  };
});

```


index.html

```html
    <!-- your app's js -->
    <script src="js/app.js"></script>
    <script src="js/controllers.js"></script>
    <script src="js/services.js"></script>
```

app.js

```js
angular.module('starter', ['ionic', 'starter.controllers','starter.services'])
```



