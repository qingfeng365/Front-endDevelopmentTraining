# 第4讲 创建基于Express4的项目

tags: Express4 nodejs

[TOC]

## 项目目标

从这一讲开始，我们将完成一个实战项目，该项目将从Express4的基础开始创建，并逐步迭代添加相关功能。从而完整讲述如何整合nodejs+Express4+MongoDB+gulp等框架，完成一个基本项目的开发。

而在后续阶段，还会进一步在前端应用AngularJs，实现目前流行的Mean全栈式开发：MongoDB+Express+Angular+nodejs

本项目的内容为虚拟的一个精简版汽车商城，包括首页展示，汽车详情，后台列表，后台录入等功能。

## 项目前期准备

在gitHub创建一个练习用的仓库，并克隆到本地。

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)


### 初始化项目目录

> -- **server**
>> -- **models**  
>> -- **views**
>>> -- **include**  
>>> -- **pages**

> -- **client**
>> -- **js**  
>> -- **css**



目录说明:
server: 在nodejs环境下执行的代码或需要使用的文件
client: 在浏览器环境下执行的代码或需要使用的文件

### 使用npm初始化项目

在项目根目录下打开命令行窗口

```bash
npm init
```

前面一段话，提示会创建 `package.json` 文件

**注意:** 对于name有个规定，不能使用大写字母

### 安装后端模块

在项目根目录下打开命令行窗口

```bash
cnpm install express --save
```
```bash
cnpm install jade --save
```
```bash
cnpm install mongoose --save
```
```bash
cnpm install body-parser --save
```
```bash
cnpm install moment --save
```
```bash
cnpm install underscore --save
```

### 安装前端模块

#### 先设置bower安装目录

如果没有指定设置，bower会默认在项目目录下创建bower_components目录，现在需要指定在client目录下创建。

在项目目录下新建文件 `.bowerrc` (注意没有文件名,只有扩展名)

内容如下:

```js
{
  "directory": "client/bower_components"
}
```

#### 初始化bower

在项目根目录下打开命令行窗口

```bash
bower init
```

#### 安装bootstrap4

```bash
bower install bootstrap#4.0.0-alpha --save
```


### 设置git的忽略文件

对node_modules目录设置忽略该目录下的一切
对client/bower_components目录设置忽略该目录下的一切

## 创建入口文件

### app.js

在 **server** 目录下新增 `app.js` 文件

输入以下内容:

```js
var express = require('express');
var port = 3000;
var app = express();

app.listen(port);

console.log("汽车商城网站服务已启动,监听端口号:"+port);
```

### 启动入口文件

在项目根目录下打开命令行窗口:

```bash
node server/app
```

打开浏览器，输入以下地址：
```url
localhost:3000
```

注意，此时页面会显示
```
Cannot GET / 
```

## 完成基本路由测试

### 设计路由

先设计以下几个路由

<table>
    <tr>
        <th>
            用途
        </th>
        <th>
            路由
        </th>
        <th>
            模板
        </th>        
    </tr>
    <tr>
        <td>
            首页
        </td>
        <td>
            /
        </td>
        <td>
            index.jade
        </td>        
    </tr>
    <tr>
        <td>
            汽车详情页
        </td>
        <td>
            /car/:id
        </td>
        <td>
            car_detail.jade
        </td>          
    </tr>
    <tr>
        <td>
            后台汽车列表页
        </td>
        <td>
            /admin/car/list
        </td>
         <td>
            car_list.jade
        </td>         
    </tr> 
    <tr>
        <td>
            后台汽车录入页 新增
        </td>
        <td>
            /admin/car/new
        </td>
         <td>
            car_admin.jade
        </td>         
    </tr>
    <tr>
        <td>
            后台汽车录入页 修改
        </td>
        <td>
            /admin/car/update/:id
        </td>
        <td>
            car_admin.jade
        </td>          
    </tr>                    
</table>

### 创建视图文件

在 `server/views` 目录下新增文件: `layout.jade`

内容如下:

```jade
doctype
html
  head
    meta(charset="utf-8")
  body
    block content
```

在 `server/views/pages` 目录下新增文件: 

index.jade   
car_detail.jade  
car_list.jade  
car_admin.jade 

内容全部为:

```jade
extends ../layout

block content
  h1=title
```
### 增加路由处理

在下面代码之后增加代码 

> `var app = express();`

```js
app.set('views', __dirname + '/views/pages');
app.set('view engine', 'jade');

app.get('/', function(req,res){
  res.render('index',{
    title: '汽车商城 首页'
  });
});

app.get('/car/:id', function(req,res){
  res.render('car_detail',{
    title: '汽车商城 详情页'
  });
});

app.get('/admin/car/list', function(req,res){
  res.render('car_list.jade',{
    title: '汽车商城 列表页'
  });
});

app.get('/admin/car/new', function(req,res){
  res.render('car_admin',{
    title: '汽车商城 后台录入页'
  });
});

app.get('/admin/car/update/:id', function(req,res){
  res.render('car_admin',{
    title: '汽车商城 后台录入页'
  });
});
```

req 是 request 的简写, res 是 response 的简写

### 重新启动node

在命令行窗口，`ctrl+c` 退出前面启动的 node ，重新输入：

```bash
node server/app
```
> 以后修改了文件,保存后,均要重新启动node,不再重复说明

### 在浏览器分别输入以下地址进行测试

http://localhost:3000/  
http://localhost:3000/car/1 
http://localhost:3000/admin/car/list
http://localhost:3000/admin/car/new
http://localhost:3000/admin/car/update/1

### 01-work 结束

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

分支 01-work 结束

## 使用虚拟数据测试模板样式

### 调整模板结构

在 `server/views/include` 目录下，新增 `head.jade` ，`foot.jade` 文件

`head.jade` 内容:

```jade
meta(name="viewport", content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no")
meta(http-equiv="X-UA-Compatible", content="IE=edge")
link(href="/bower_components/bootstrap/dist/css/bootstrap.min.css", rel="stylesheet")
```

`foot.jade` 内容:

```jade
script(src="/bower_components/jquery/dist/jquery.min.js")  
script(src="/bower_components/bootstrap/dist/js/bootstrap.min.js")
```

并修改 `layout.jade` 的内容:

```jade
doctype
html
  head
    meta(charset="utf-8")
    title #{title}  
    include ./include/head.jade 
  body
    block content
    include ./include/foot.jade
```

启动入口文件

在项目根目录下打开命令行窗口:

```bash
node server/app
```

打开浏览器,打开控制台,测试地址: `http://localhost:3000/`

控制台切换到网络面板,发现错误: `404 Not Found` 

访问地址如下:  
`http://localhost:3000/bower_components/bootstrap/dist/css/bootstrap.min.css`

### 设置静态资源路由

修改 `app.js` ，在下面代码之后增加代码 

> `var app = express();`

```js
var path = require('path');

app.use(express.static(path.join(__dirname, '../client')));
```

重新启动node，打开浏览器测试

> 扩展阅读
> [利用 Express 托管静态文件](http://www.expressjs.com.cn/starter/static-files.html)

