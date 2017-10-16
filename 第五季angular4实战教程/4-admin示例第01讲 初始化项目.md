# 4-admin示例第01讲 初始化项目

## 创建项目

- 新建普通项目

```bash
ng new myadmin --skip-install --routing --style scss

cd myadmin

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass


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

### 安装第三方库

```bash

npm install bootstrap@4.0.0-beta font-awesome ionicons nebular-icons --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist


npm install @nebular/theme @nebular/auth @ng-bootstrap/ng-bootstrap --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist

npm install primeng tinymce --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist


```

`/.angular-cli.json`

```js
    "styles": [
      "../node_modules/bootstrap/dist/css/bootstrap.min.css",
      "../node_modules/ionicons/dist/scss/ionicons.scss",
      "../node_modules/font-awesome/scss/font-awesome.scss",
      "../node_modules/nebular-icons/scss/nebular-icons.scss",
      "./styles.scss"
    ],
    "scripts": [
      "../node_modules/tinymce/tinymce.min.js",
      "../node_modules/tinymce/themes/modern/theme.min.js",
      "../node_modules/tinymce/plugins/link/plugin.min.js",
      "../node_modules/tinymce/plugins/paste/plugin.min.js",
      "../node_modules/tinymce/plugins/table/plugin.min.js"
    ],
```

## 首页初始动画

`/src/index.html`

```html
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>MyadminTest</title>
  <base href="/">

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>

<body>
  <app-root>Loading...</app-root>
  <style>
    @-webkit-keyframes spin {
      0% {
        transform: rotate(0)
      }
      100% {
        transform: rotate(360deg)
      }
    }
    
    @-moz-keyframes spin {
      0% {
        -moz-transform: rotate(0)
      }
      100% {
        -moz-transform: rotate(360deg)
      }
    }
    
    @keyframes spin {
      0% {
        transform: rotate(0)
      }
      100% {
        transform: rotate(360deg)
      }
    }
    
    .spinner {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 1003;
      background: #000000;
      overflow: hidden
    }
    
    .spinner div:first-child {
      display: block;
      position: relative;
      left: 50%;
      top: 50%;
      width: 150px;
      height: 150px;
      margin: -75px 0 0 -75px;
      border-radius: 50%;
      box-shadow: 0 3px 3px 0 rgba(255, 56, 106, 1);
      transform: translate3d(0, 0, 0);
      animation: spin 2s linear infinite
    }
    
    .spinner div:first-child:after,
    .spinner div:first-child:before {
      content: '';
      position: absolute;
      border-radius: 50%
    }
    
    .spinner div:first-child:before {
      top: 5px;
      left: 5px;
      right: 5px;
      bottom: 5px;
      box-shadow: 0 3px 3px 0 rgb(255, 228, 32);
      -webkit-animation: spin 3s linear infinite;
      animation: spin 3s linear infinite
    }
    
    .spinner div:first-child:after {
      top: 15px;
      left: 15px;
      right: 15px;
      bottom: 15px;
      box-shadow: 0 3px 3px 0 rgba(61, 175, 255, 1);
      animation: spin 1.5s linear infinite
    }
  </style>
  <div class="spinner">
    <div></div>
  </div>

</body>

</html>
```

说明: 

- spinner 覆盖全屏
- spinner 的 第一个 div ,设置了三种颜色的 底边阴影 (Y-offset)
- 子 div 通过绝对定位水平与垂直居中 


