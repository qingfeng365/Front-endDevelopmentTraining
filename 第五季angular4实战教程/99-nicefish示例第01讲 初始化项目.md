# 3-nicefish示例第01讲 初始化项目.md

## 创建项目

- 新建普通项目

```bash
ng new mynicefish --skip-install --routing --style scss

cd mynicefish

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

ng serve

npm install  gulp gulp-jade --save-dev --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass


```


### 创建gulpfile.js

`/gulpfile.js`

```js
'use strict';

var gulp = require('gulp');
var jade = require('gulp-jade');

gulp.task('watch', function() {
  gulp.watch('**/*.jade', ['jade']);
});

gulp.task('jade', function() {
  gulp.src('src/**/*.jade', { base: '.' })
    .pipe(jade({
      pretty: true
    }))
    .pipe(gulp.dest('.'));
});
gulp.task('default', ['watch', 'jade']);
```

## 安装 bootstrap4 和 jquery

```bash

 npm install popper.js --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist


npm install bootstrap@4.0.0-beta --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist


npm install  jquery --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass


npm install font-awesome --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```

`.angular-cli.json`

```js

    "styles": [
      "../node_modules/bootstrap/dist/css/bootstrap.min.css",
      "../node_modules/font-awesome/css/font-awesome.min.css",
      "styles.scss"
    ],
    "scripts": [
      "../node_modules/jquery/dist/jquery.min.js",
      "../node_modules/popper.js/dist/popper.min.js",
      "../node_modules/bootstrap/dist/js/bootstrap.min.js"
    ],

```

## 安装 primeng

```
npm install primeng  --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
```



## 定义字体

`/src/styles.scss`

```css
body {
  font-family: '微软雅黑', 'Microsoft YaHei', '宋体', Tahoma, Helvetica, Arial, sans-serif;
}
```

## index.html

`/src/index.html`

```html
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>MynicefishTest</title>
  <base href="/">

  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="renderer" content="webkit">
  <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>

<body>
  <app-root>Loading...</app-root>
</body>

</html>
```


## 项目主体内容说明

- 项目主要是一个博客示例
- 贴子列表
- 贴子详情
- 用户中心
- 角色分为 读者 博主 两种
- 博主 有发贴功能
- 读者 有评论与关注功能
- 注意示例为单个博客, 即网址对应的是一个博主的博客

## 特性模块

- Home 首页模块
- Post 贴子模块
- User 用户模块
- Manage 后台管理模块

## 共享模块

- Shared

## 创建模块

```
ng g m Home
ng g m Post
ng g m User
ng g m Manage
ng g m Shared

```