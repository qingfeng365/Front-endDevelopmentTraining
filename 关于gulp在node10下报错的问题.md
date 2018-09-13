# 关于gulp在node10下报错的问题

gulp 3.9 的版本 , 在 node 10 下报错


[https://davidsekar.com/nodejs/upgrading-your-gulp-for-running-with-node-v10](https://davidsekar.com/nodejs/upgrading-your-gulp-for-running-with-node-v10)

## 解决步骤

### 重新安装 

```

npm rm -g gulp     // 删除旧的全局版本

npm install -g gulp-cli  // 删除新的命令行工具

npm install --save-dev gulp@next

```

正确的版本应为:

```

C:/myprojectfolder> gulp -v
[21:03:13] CLI version 1.3.0
[21:03:13] Local version 4.0.0

```


### 代码兼容问题


#### 新版本要求任务要标记完成

原写法:

```
gulp.task('sass', function () {
    gulp.src(sassPath)
    .pipe(plumber({ errorHandler: notify.onError("Error: <%=error.message %>") }))
    .pipe(sourcemaps.init())
    .pipe(sass({ outputStyle: 'compressed' }).on('error', sass.logError))
    .pipe(autoprefixer({ browsers: ['> 1%', 'IE 8'], cascade: false }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(cssPath));
});
```

新写法:

```
gulp.task('sass', function (done) {
    gulp.src(sassPath)
    .pipe(plumber({ errorHandler: notify.onError("Error: <%=error.message %>") }))
    .pipe(sourcemaps.init())
    .pipe(sass({ outputStyle: 'compressed' }).on('error', sass.logError))
    .pipe(autoprefixer({ browsers: ['> 1%', 'IE 8'], cascade: false }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(cssPath));
    done();
});
```

#### 调用子任务

原写法:

```
gulp.task('default', ['sass', 'imagemin']);

```

新的写法:

```
// If your tasks are dependent then change above to series as follows
gulp.task('default', gulp.series('sass', 'imagemin'));

// OR ELSE, if the tasks are independent then it can be run in parallel as follows
gulp.task('default', gulp.parallel('sass', 'imagemin'));

```

#### Watcher 事件

旧:

```
gulp.task('watch', function(){
    gulp.watch('path/to/css/*.scss').on('change', function(event) {
        console.log(event.type + " : " + event.path);
    });
});
```

新:

```
gulp.task('watch', function(){
    gulp.watch('path/to/css/*.scss')
     .on('change', function(path, stats) {
         console.log('File ' + path + ' was changed');
     }).on('unlink', function(path) {
         console.log('File ' + path + ' was removed');
     });
});
```