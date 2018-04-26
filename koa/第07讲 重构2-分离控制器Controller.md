# 第07讲 重构2-分离控制器Controller

`/controller/home.controller.ts`

```ts
import * as Router from "koa-router";

class HomeController {
  constructor() {

  }
  async index(ctx: Router.IRouterContext, next: () => Promise<any>) {
    ctx.response.body = `<h1>index page</h1>`
  }

  async home(ctx: Router.IRouterContext, next: () => Promise<any>) {
    console.log(ctx.request.query)
    console.log(ctx.request.querystring)
    ctx.response.body = '<h1>HOME page</h1>'
  }
  async  homeParams(ctx: Router.IRouterContext, next: () => Promise<any>) {
    console.log(ctx.params)
    ctx.response.body = '<h1>HOME page /:id/:name</h1>'
  }
  async login(ctx: Router.IRouterContext, next: () => Promise<any>) {
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
  }
  async register(ctx: Router.IRouterContext, next: () => Promise<any>) {
    let {
      name,
      password
    } = ctx.body;
    if (name == 'ikcamp' && password == '123456') {
      ctx.response.body = `Hello， ${name}！`
    } else {
      ctx.response.body = '账号信息错误'
    }
  }
}

const homeController = new HomeController();

export { homeController };

```

`router.ts`

```ts
import * as Router from "koa-router";
import * as Koa from "Koa";

import { homeController } from "./controller/home.controller";


const router = new Router();

const routesHandle = function (app: Koa) {
  router.get('/', homeController.index);

  router.get('/home', homeController.home);

  router.get('/user', homeController.login)

  router.post('/user/register', homeController.register)

  app
    .use(router.routes())
    .use(router.allowedMethods());
}

export { routesHandle };
```

`app.ts`

```ts
import * as Koa from "Koa";
import { routesHandle } from "./router";

const app = new Koa();

const bodyParser = require('koa-bodyparser');

app.use(bodyParser())

routesHandle(app);

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})


```

## 进一步从控制器分离出service

`/service/home.service.ts`

```ts
class HomeService {
  async register(name: string, pwd: string) {
    let data: string;
    if (name == 'ikcamp' && pwd == '123456') {
      data = `Hello， ${name}！`
    } else {
      data = '账号信息错误'
    }
    return data
  }
}

const homeService = new HomeService();

export { homeService };

```

`/controller/home.controller.ts`

```ts
  async register(ctx: Router.IRouterContext, next: () => Promise<any>) {
    let {
      name,
      password
    } = ctx.body;
    let data = await homeService.register(name, password);
    ctx.response.body = data;
  }
```