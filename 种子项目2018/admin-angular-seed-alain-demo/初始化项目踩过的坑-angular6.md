# 初始化项目踩过的坑


## less 版本升级到 3 后, 会出错

错误 1: 

`/src/styles.less`

```
 @import '~@delon/theme/styles/index';
```

不支持 `~` 这种路径

要改为 

```
@import '../node_modules/@delon/theme/styles/index.less';
```

错误 2:

`/node_modules/ng-zorro-antd/src/style/color/bezierEasing.less`

报编译错误: `Inline JavaScript is not enabled. Is it set in your options?`


主要原因为 less 3 的版本有问题, 而 angular 6 默认安装 less 3

`解决方案`

强制安装 less 2

    "less": "2.7.3",
    "less-loader": "4.1.0",

```
yarn add less@2.7.3 less-loader@4.1.0
```

不支持 `~` 这种路径 , 降级后,也没有解决, 原因不明

## 不支持 ts 模块设置路径别名

`/src/tsconfig.app.json`

```
    "paths": {
      "@shared": [
        "app/shared"
      ],
      "@shared/*": [
        "app/shared/*"
      ],
      "@core": [
        "app/core/"
      ],
```

上面的设置没有效果.

以下写法会报 找不到模块的 错误,

```ts
 import { throwIfAlreadyLoaded } from '@core/module-import-guard';

```

`解决方案:`

注释掉, 重新引入


## ng-alain 用的 @delon/abc 本身自带的 angular 5 版本 有冲突

`解决方案:`

找到

`/node_modules/@delon/abc/node_modules/@angular`

改名为

`/node_modules/@delon/abc/node_modules/@angular.bak`

## UEditor 配置方式已过时

下载 UEditor 最新的 php utf-8 版本, 把文件拷到 

`src/assets/ueditor` 目录, 并按以下方式配置

`/src/app/app.module.ts`

```ts
    UEditorModule.forRoot({
      js: [
        `./assets/ueditor/ueditor.all.min.js`,
        `./assets/ueditor/ueditor.config.js`,
      ],
      // 默认全局配置项
      options: {
        UEDITOR_HOME_URL: '',
        themePath: './assets/ueditor/themes/',
      },
    }),
```

