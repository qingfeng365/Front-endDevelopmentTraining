# 使用jade的方法

## 安装插件

`npm install gulp gulp-jade --save`

## 在项目目录创建 gulpfile.js

`/gulpfile.js`

```js
'use strict';

var gulp = require('gulp');
var jade = require('gulp-jade');

gulp.task('watch', function() {
  gulp.watch('src/**/*.jade', ['jade']);
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

## 执行gulp

另外打开一个新的命令行窗口

```bash
gulp
```

## 设置editor.tabSize

首选项/设置/常用设置

editor.tabSize: 2

