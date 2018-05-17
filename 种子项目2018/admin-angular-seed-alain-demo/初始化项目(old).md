# 01 初始化项目

## 环境

`ng -v`



## 创建项目

```
ng new admin-angular-seed-alain-demo --routing --skip-install --style less -S

cd admin-angular-seed-alain-demo

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

或用 

yarn

```

## 安装  NG-ZORRO

[https://ng.ant.design/#/docs/angular/introduce](https://ng.ant.design/#/docs/angular/introduce)


```
npm install ng-zorro-antd --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```

## 配置jade (pug)

```
npm install gulp gulp-pug --save --registry=https://registry.npm.taobao.org 

```

`/gulpfile.js`


```js
var gulp = require('gulp');
var pug = require('gulp-pug');


function onError(error) {
  console.error(error.toString())
  this.emit('end')
}

gulp.task('watch', function() {
  gulp.watch('src/**/*.pug', ['build:pug']);
});

gulp.task('build:pug', function() {
  gulp.src('src/**/*.pug', { base: '.' })
    .pipe(pug({
      pretty: true
    }))
    .on('error', onError)
    .pipe(gulp.dest('.'));
});

gulp.task('default', ['watch', 'build:pug']);
```

## 参考项目

[http://ng-alain.com/docs/getting-started](http://ng-alain.com/docs/getting-started)