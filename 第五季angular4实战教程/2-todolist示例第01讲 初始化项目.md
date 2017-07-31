# 2-todolist示例第01讲 初始化项目

## 创建项目

- 新建普通项目

```bash
ng new mytodo --skip-install --routing

cd mytodo

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

## 设置根模块视图

`/src/app/app.component.jade`

```jade
h3 待办事项管理
router-outlet
```