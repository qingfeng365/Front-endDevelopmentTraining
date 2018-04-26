# 第01讲 iKcamp-01-启动服务器

## 创建web服务器的方法

```ts
import * as Koa from "Koa";

const app = new Koa();

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})

```

只是监听端口, 但还没有响应 res


##　采用中间件方式进行响应

```ts
import * as Koa from "Koa";

const app = new Koa();

app.use(async (ctx, next)=>{
  await next();
  ctx.response.type = 'text/html';
  ctx.response.body = '<h1>Hello World</h1>';
});

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})

```

app.use 的定义

```ts
use(middleware: Application.Middleware): this;
```


```ts
type Middleware = compose.Middleware<Context>;

declare namespace compose {
    type Middleware<T> = (context: T, next: () => Promise<any>) => any;
    type ComposedMiddleware<T> = (context: T, next?: () => Promise<any>) => Promise<void>;
}

```

