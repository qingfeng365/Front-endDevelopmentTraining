# 第05讲 参数与body处理

## query 参数

`koa-router` 封装的 `request` 对象，里面的 `query` 方法或 `querystring` 方法可以直接获取到 `Get` 请求的数据，唯一不同的是 `query` 返回的是对象，而 `querystring` 返回的是字符串。

直接用 ctx 也可以

```ts
router.get('/home', async (ctx, next) => {
  console.log('ctx.request.query:',ctx.request.query);
  console.log('ctx.request.querystring:',ctx.request.querystring);
  console.log('ctx.query:', ctx.query);
  console.log('ctx.querystring:', ctx.querystring);

  ctx.response.body = '<h1>HOME page</h1>'
})
```

## 路由参数

koa-router 也支持参数，参数会被添加到 ctx.params 中。参数可以是一个正则表达式，这个功能的实现是通过 path-to-regexp 来实现的。原理是把 URL 字符串转化成正则对象，然后再进行正则匹配，之前的例子中的 * 通配符就是一种正则表达式。

```js
router.get('/:category/:title', function (ctx, next) {
  console.log(ctx.params);
  // => { category: 'programming', title: 'how-to-node' } 
});

```

## body

需要安装插件: `koa-bodyparser`

```
npm i koa-bodyparser
```

```ts
import * as Koa from "Koa";
import * as Router from "koa-router";


const app = new Koa();
const router = new Router();
const bodyParser = require('koa-bodyparser');

// body的中间件,要在一开始就调用
app.use(bodyParser())

 // 添加路由
 router.get('/', async (ctx, next) => {
  ctx.response.body = `<h1>index page</h1>`
})

router.get('/home', async (ctx, next) => {
  console.log('ctx.request.query:', ctx.request.query);
  console.log('ctx.request.querystring:', ctx.request.querystring);
  console.log('ctx.query:', ctx.query);
  console.log('ctx.querystring:', ctx.querystring);

  ctx.response.body = '<h1>HOME page</h1>'
})

// 增加返回表单页面的路由
router.get('/user', async (ctx, next) => {
  ctx.response.body =
    `
      <form action="/user/register" method="post">
        <input name="name" type="text" placeholder="请输入用户名：ikcamp"/> 
        <br/>
        <input name="password" type="text" placeholder="请输入密码：123456"/>
        <br/> 
        <button>GoGoGo</button>
      </form>
    `
})

// 增加响应表单请求的路由
router.post('/user/register', async (ctx, next) => {
  let { name, password } = (ctx.request as any).body
  if (name === 'ikcamp' && password === '123456') {
    ctx.response.body = `Hello， ${name}！`
  } else {
    ctx.response.body = '账号信息错误'
  }
})

app.use(router.routes());

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})

```

### Options

* **enableTypes**: parser will only parse when request type hits enableTypes, default is `['json', 'form']`.
* **encode**: requested encoding. Default is `utf-8` by `co-body`.
* **formLimit**: limit of the `urlencoded` body. If the body ends up being larger than this limit, a 413 error code is returned. Default is `56kb`.
* **jsonLimit**: limit of the `json` body. Default is `1mb`.
* **textLimit**: limit of the `text` body. Default is `1mb`.
* **strict**: when set to true, JSON parser will only accept arrays and objects. Default is `true`. See [strict mode](https://github.com/cojs/co-body#options) in `co-body`. In strict mode, `ctx.request.body` will always be an object(or array), this avoid lots of type judging. But text body will always return string type.
* **detectJSON**: custom json request detect function. Default is `null`.

  ```js
  app.use(bodyparser({
    detectJSON: function (ctx) {
      return /\.json$/i.test(ctx.path);
    }
  }));
  ```

* **extendTypes**: support extend types:

  ```js
  app.use(bodyparser({
    extendTypes: {
      json: ['application/x-javascript'] // will parse application/x-javascript type body as a JSON string
    }
  }));
  ```

* **onerror**: support custom error handle, if `koa-bodyparser` throw an error, you can customize the response like:

  ```js
  app.use(bodyparser({
    onerror: function (err, ctx) {
      ctx.throw('body parse error', 422);
    }
  }));
  ```

* **disableBodyParser**: you can dynamic disable body parser by set `ctx.disableBodyParser = true`.

```js
app.use(async (ctx, next) => {
  if (ctx.path === '/disable') ctx.disableBodyParser = true;
  await next();
});
app.use(bodyparser());
```

### Raw Body

You can access raw request body by `ctx.request.rawBody` after `koa-bodyparser` when:

1. `koa-bodyparser` parsed the request body.
2. `ctx.request.rawBody` is not present before `koa-bodyparser`.

### Koa 1 Support

To use `koa-bodyparser` with koa@1, please use [bodyparser 2.x](https://github.com/koajs/bodyparser/tree/2.x).

```bash
npm install koa-bodyparser@2 --save
```


## 其它用于body的中间件


`koa-better-body`: 处理类型比较全, 包括文件

https://github.com/tunnckoCore/koa-better-body

注意: 该中间件默认处理的容量比较小, 100k , 要注意调整参数

