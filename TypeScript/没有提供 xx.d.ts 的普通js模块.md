# 没有提供 xx.d.ts 的普通js模块

## nodejs 后端程序 

`tsconfig.json`

```json
 "compilerOptions": {
    "module": "commonjs",
}
```

可以直接使用 require


```ts
const bodyParser = require('koa-bodyparser');
```