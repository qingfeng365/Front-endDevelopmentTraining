# 第9讲 conference项目

## 创建项目


```bash
ionic start myconference-test blank --skip-deps --skip-link 

cd myconference

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

npm install  gulp gulp-jade --save-dev --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```

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


## 创建欢迎页