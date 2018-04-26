# 第04讲 使用 koa-router 管理路由

## 安装 koa-router

```
npm i koa-router

```

## 使用 koa-router 的方法

- 先定义路由 
- 调用路由中间件


##　示例

```ts
import * as Koa from "Koa";
import * as Router from "koa-router";

const app = new Koa();
const router = new Router();

 // 添加路由
 router.get('/', async (ctx, next) => {
  ctx.response.body = `<h1>index page</h1>`
})

router.get('/home', async (ctx, next) => {
  ctx.response.body = '<h1>HOME page</h1>'
})

app.use(router.routes());

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})

```

## koa-router 处理方法

```ts
router
  .get('/', async (ctx, next) => {
    ctx.body = 'Hello World!';
  })
  .post('/users', async (ctx, next) => {
    // ... 
  })
  .put('/users/:id', async (ctx, next) => {
    // ... 
  })
  .del('/users/:id', async (ctx, next) => {
    // ... 
  })
  .all('/users/:id', async (ctx, next) => {
    // ... 
  });
```

在任意http请求中，遵从 `RESTful` 规范，可以把 `GET`、`POST`、`PUT`、`DELETE` 类型的请求分别对应 `查`，`增`，`改`，`删`，这里 `router` 的方法也一一对应。通常我们使用 `GET` 来查询和获取数据，使用 `POST` 来更新资源。`PUT` 和 `DELETE` 使用比较少，但是如果你们团队采用 `RESTful架构`，就比较推荐使用了。我们注意到，上述代码中还有一个`all` 方法。`all` 方法通常用于匹配一组路由或者全部路由从而做一些统一设置和处理，也可以处理不确定客户端发送的请求方法类型的情况。


## 在路由中指定通配符

设置跨域头

```ts
router.all('/*', async (ctx, next) => {
  // *代表允许来自所有域名请求
  ctx.set("Access-Control-Allow-Origin", "*");
  // 其他一些设置...
  await next();
});
```

##　多个路由都命中的处理

```ts
router.get('/', async (ctx, next) => {
  ctx.response.body = `<h1>index page</h1>`
  await next();
})
router.all('/', async (ctx, next) => {
  console.log('match "all" method')
  await next();
});
```

执行这段代码，我们不仅能够访问http://localhost:3000/看到“index page”，也能够在控制台中看到“'match "all" method'”，说明路由"/"不仅执行了`get`方法的回调，也执行了`all`方法的回调函数。但是，如果我们把`get`方法中的`await next()`去掉，那么就不会命中`all`方法的路由规则，也不会执行`all`方法的回调函数了。因为说到底，对路由的处理也是一种中间件，如果不执行`await next()`把控制权交给下一个中间件，那么后面的路由就不会再执行了。


## 命名路由

在开发过程中我们能够很方便的生成路由 `URL`：

```js
router.get('user', '/users/:id', function (ctx, next) {
  // ... 
});

router.url('user', 3);
// => 生成路由 "/users/3" 
 
router.url('user', { id: 3 });
// => 生成路由 "/users/3" 
 
router.use(function (ctx, next) {
  // 重定向到路由名称为 “sign-in” 的页面 
  ctx.redirect(ctx.router.url('sign-in'));
})
```

`router.url` 方法方便我们在代码中根据路由名称和参数(可选)去生成具体的 `URL`，而不用采用字符串拼接的方式去生成 `URL` 了。


## 单个路由多中间件

koa-router 也支持单个路由多中间件的处理。通过这个特性，我们能够为一个路由添加特殊的中间件处理。也可以把一个路由要做的事情拆分成多个步骤去实现，当路由处理函数中有异步操作时，这种写法的可读性和可维护性更高。

```js
router.get(
  '/users/:id',
  function (ctx, next) {
    return User.findOne(ctx.params.id).then(function(user) {
      // 首先读取用户的信息，异步操作
      ctx.user = user;
      next();
    });
  },
  function (ctx) {
    console.log(ctx.user);
    // 在这个中间件中再对用户信息做一些处理
    // => { id: 17, name: "Alex" }
  }
);
```

## 嵌套路由

我们可以在应用中定义多个路由，然后把这些路由组合起来用，这样便于我们管理多个路由，也简化了路由的写法。

```js
var forums = new Router();
var posts = new Router();

posts.get('/', function (ctx, next) {...});
posts.get('/:pid', function (ctx, next) {...});
forums.use('/forums/:fid/posts', posts.routes(), posts.allowedMethods());

// 可以匹配到的路由为 "/forums/123/posts" 或者 "/forums/123/posts/123"
app.use(forums.routes());

```

## 路由前缀

通过 prefix 这个参数，我们可以为一组路由添加统一的前缀，和嵌套路由类似，也方便我们管理路由和简化路由的写法。不同的是，前缀是一个固定的字符串，不能添加动态参数。


```js
var router = new Router({
  prefix: '/users'
});

router.get('/', ...); // 匹配路由 "/users" 
router.get('/:id', ...); // 匹配路由 "/users/:id"

```

## 路由参数

koa-router 也支持参数，参数会被添加到 ctx.params 中。参数可以是一个正则表达式，这个功能的实现是通过 path-to-regexp 来实现的。原理是把 URL 字符串转化成正则对象，然后再进行正则匹配，之前的例子中的 * 通配符就是一种正则表达式。

```js
router.get('/:category/:title', function (ctx, next) {
  console.log(ctx.params);
  // => { category: 'programming', title: 'how-to-node' } 
});

```

## router.allowedMethods()

allowedMethods() 是设置如何 响应 405 Method Not Allowed 501 Not Implemented

一般直接调用即可, 或配合 boom

```js
var Koa = require('koa');
var Router = require('koa-router');

var app = new Koa();
var router = new Router();

app.use(router.routes());
app.use(router.allowedMethods());
```

```js
var Koa = require('koa');
var Router = require('koa-router');
var Boom = require('boom');

var app = new Koa();
var router = new Router();

app.use(router.routes());
app.use(router.allowedMethods({
  throw: true,
  notImplemented: () => new Boom.notImplemented(),
  methodNotAllowed: () => new Boom.methodNotAllowed()
}));
```