# 第3讲 实战app之准备

## 实例项目网址

`https://github.com/qingfeng365/trainApp-V1`

课堂练习源码项目:

`https://github.com/qingfeng365/trainApp-V1-step`

## 练习项目名称

`trainApp-V1-step`

## 项目初始化

`ionic start trainApp-V1-step blank`

## 项目运行


`ionic serve`

默认8100端口

或

`ionic serve -p9100`


## 命名规则 

### 目录规则 

`www/app`

分模块的文件均放到该目录下

### js文件规则 

模块: 指业务逻辑意义上的模块,如 首页 搜索页 详情页 购物车等等

- 路由文件

	模块.router.js

- 控制器文件

	模块.controller.js

- 模块专属服务文件

	模块.service.js

- 模块专属指令文件

	模块.directive.js

- 模块专属杂项文件

	模块.util.js
	
	如过滤器等等

### css文件规则 

`www/css`

   模块.css

### templates文件规则 

`www/jade_template`

模块.template.功能.jade

编译后目录 `www/app`

编译后文件名: 模块.template.功能.html

注意jade的内容不应出现后端变量,并使用gulp前提编译,不是在后台运行时编译


### 入口文件

`www/index.html`

`www/js/main.js`


- js和css文件加载均写到 head 小节


### js写法

```js
(function(){
	'use strict';


})();
```

主要目的是保证不会产生全局变量

### 更改app

`www/js/main.js`

```js
(function() {
  'use strict';
  angular.module('main', ['ionic'])
    .config(['$ionicConfigProvider',
      function($ionicConfigProvider) {
        /**
         * ios android , tabs都在底部,并统一采用ios样式
         */
        $ionicConfigProvider.tabs.position('bottom');
        $ionicConfigProvider.tabs.style('standard');

      }
    ])
    .run(function($ionicPlatform) {
      $ionicPlatform.ready(function() {
        if (window.cordova && window.cordova.plugins.Keyboard) {
          // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
          // for form inputs)
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

          // Don't remove this line unless you know what you are doing. It stops the viewport
          // from snapping when text inputs are focused. Ionic handles this internally for
          // a much nicer keyboard experience.
          cordova.plugins.Keyboard.disableScroll(true);
        }
        if (window.StatusBar) {
          StatusBar.styleDefault();
        }
      });
    });

})();

```

`www/index.html`

```html
  <body ng-app="main">
    <ion-nav-view></ion-nav-view> 
  </body>
```


`/trainApp-V1/gulpfile.js`

```js
var gulp = require('gulp');
var gutil = require('gulp-util');
var bower = require('bower');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var minifyCss = require('gulp-minify-css');
var rename = require('gulp-rename');
var sh = require('shelljs');
var jade = require('gulp-jade');

var paths = {
  sass: ['./scss/**/*.scss']
};

gulp.task('default', ['sass','templates']);

gulp.task('sass', function(done) {
  gulp.src('./scss/ionic.app.scss')
    .pipe(sass())
    .on('error', sass.logError)
    .pipe(gulp.dest('./www/css/'))
    .pipe(minifyCss({
      keepSpecialComments: 0
    }))
    .pipe(rename({ extname: '.min.css' }))
    .pipe(gulp.dest('./www/css/'))
    .on('end', done);
});

gulp.task('watch', ['sass','templates'], function() {
  gulp.watch(paths.sass, ['sass']);
  gulp.watch(['./www/jade_template/*.jade'], ['templates']);
});

gulp.task('install', ['git-check'], function() {
  return bower.commands.install()
    .on('log', function(data) {
      gutil.log('bower', gutil.colors.cyan(data.id), data.message);
    });
});

gulp.task('git-check', function(done) {
  if (!sh.which('git')) {
    console.log(
      '  ' + gutil.colors.red('Git is not installed.'),
      '\n  Git, the version control system, is required to download Ionic.',
      '\n  Download git here:', gutil.colors.cyan('http://git-scm.com/downloads') + '.',
      '\n  Once git is installed, run \'' + gutil.colors.cyan('gulp install') + '\' again.'
    );
    process.exit(1);
  }
  done();
});

/**
 * 转换前端模板
 */
gulp.task('templates', function() {
  gulp.src('./www/jade_template/*.jade')
    .pipe(jade({
      pretty: true
    }))
    .pipe(gulp.dest('./www/app/'));
});
```

注意,要另开命令窗口单独运行 `gulp templates`



