
### gulp-sass 安装问题

nodejs版本大于4时,ionic原有项目有可能报以下错误:

```
Error: `libsass` bindings not found. Try reinstalling `node-sass`?
```

解决方案:

```
npm uninstall --save-dev gulp-sass
npm install --save-dev gulp-sass@2
```


### 使用sass的方法

[http://ionicframework.com/docs/cli/sass.html](http://ionicframework.com/docs/cli/sass.html)


Using Sass

By default, starter projects are hooked up to Ionic’s precompiled CSS file, which is found in the project’s www/lib/ionic/css directory, and is linked to the app in the head of the root index.html file. However, Ionic projects can also be customized using Sass, which gives developers and designers “superpowers” in terms of creating and maintaining CSS. Below are two ways to setup Sass for your Ionic project (the ionic setup sass command simply does the manual steps for you). Once Sass has been setup for your Ionic project, then the ionic serve command will also watch for Sass changes.
Setup Sass Automatically

$ ionic setup sass

You can start to write your sass in your ./scss/ionic.app.scss file. If you need any more help working with sass, we have a YouTube video that covers the basics.
Setup Sass Manually

    Run npm install from the working directory of an Ionic project. This will install gulp.js and a few handy tasks, such as gulp-sass and gulp-minify-css.
    Remove <link href="lib/ionic/css/ionic.css" rel="stylesheet"> from the <head> of the root index.html file.
    Remove <link href="css/style.css" rel="stylesheet"> from the <head> of the root index.html file.
    Add <link href="css/ionic.app.css" rel="stylesheet"> to the <head> of the root index.html file.
    In the ionic.project file, add the JavaScript property "gulpStartupTasks": ["sass", "watch"] (this can also be customized to whatever gulp tasks you’d like).


