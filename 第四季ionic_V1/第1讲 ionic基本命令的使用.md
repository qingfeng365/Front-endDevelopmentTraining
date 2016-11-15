# 第1讲 ionic基本命令的使用

## 创建项目均使用 ionic 全局命令实现

常见的 ionic 全局命令, 以下命令均在命令行窗口下执行

### 创建项目

命令语法: ionic start <项目名> [template]

- 项目名:　会在当前命令行窗口目录，创建　同名　子目录
- 会自动根据指定的　template模板创建项目

可选的模板(template)：

- tabs : 缺省模板, 底部导航型
- sidemenu : 边栏菜单型
- blank : 空项目

创建项目后, ionic 会自动执行安装脚本,下载必要的包,可能要很长时间.

如以下命令:

```
ionic start myProject tabs
```

如果创建成功,则最后会出现以下提示:

```
♬ ♫ ♬ ♫  Your Ionic app is ready to go! ♬ ♫ ♬ ♫

Some helpful tips:

Run your app in the browser (great for initial development):
  ionic serve

Run on a device or simulator:
  ionic run ios[android,browser]

Test and share your app on device with Ionic View:
  http://view.ionic.io

Build better Enterprise apps with expert Ionic support:
  http://ionic.io/enterprise

New! Add push notifications, live app updates, and more with Ionic Cloud!
  https://apps.ionic.io/signup
```

### 开发调试(浏览器模式)


命令语法: ionic serve [options]

启动项目,并找开浏览器

如果有报错,说明包安装不全,手动执行:

```
npm install
bower install
```


可选的参数:

[--consolelogs|-c] ......  Print app console logs to Ionic CLI
[--serverlogs|-s] .......  Print dev server logs to Ionic CLI
[--port|-p] .............  Dev server HTTP port (8100 default)
[--livereload-port|-i] ..  Live Reload port (35729 default)
[--nobrowser|-b] ........  Disable launching a browser
[--nolivereload|-r] .....  Do not start live reload
[--noproxy|-x] ..........  Do not add proxies


一般使用 ionic serve 即可


### 指定项目app目标平台

命令语法: ionic platform add [ios|android]

如:

ionic platform add android

表示增加项目目标为  android

### 生成目标平台项目文件

命令语法: ionic build [ios|android]

如: 

ionic build ios

因为生成目标平台项目文件时间可能会很长,因此一般分别指定生成

生成android项目文件,因要下载很多相关文件,需要更多时间.

注意,该工作平时开发是不需要的,只有准备发布app,才开始处理.

或者对某种功能,需要在特定模拟器或真机观察,才需要.

### 创建app图标和启动页文件

[http://ionicframework.com/docs/cli/icon-splashscreen.html](http://ionicframework.com/docs/cli/icon-splashscreen.html)

命令语法: ionic resources


### 练习创建项目

#### 用tabs模板创建演示项目

```
ionic start tabsDemo tabs
```

#### 用blank模板创建练习项目

```
ionic start tabsMe blank
```



