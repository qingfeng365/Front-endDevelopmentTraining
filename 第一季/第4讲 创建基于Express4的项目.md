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

### 路由规划

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

### 进一步调整模板结构


在 `server/views/include` 目录下，新增 `header.jade` 文件

`head.jade` 内容:

```jade
.container
	.row.m-a-md
		h1=title
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
		include ./include/header.jade
		block content
		include ./include/foot.jade
```

### 首页模拟数据

旧代码:

```js
app.get('/', function(req,res){
		res.render('index',{
			title: '汽车商城 首页'
		});
});
```
改为新代码:

```js
app.get('/', function(req,res){
	res.render('index',{
		title: '汽车商城 首页',
		cars:[{
			_id:1,
			proTitle:"英朗",
			guidePrice:"11.99",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg",
			buyNum: 200
		},{
			_id:2,
			proTitle:"哈弗H6",
			guidePrice:"9.63",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t874/304/396255796/41995/328da75e/5528c399N3f9cc646.jpg",
			buyNum: 888
		},{
			_id:3,
			proTitle:"速腾",
			guidePrice:"12.30",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t988/239/475904647/32355/a1d35780/55278f2cN574b21ab.jpg",
			buyNum: 100
		},{
			_id:4,
			proTitle:"捷达",
			guidePrice:"7.51",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t1108/41/489298815/33529/38655c9f/5528c276N41f39d00.jpg",
			buyNum: 300	
		},{
			_id:5,
			proTitle:"本田XR-V",
			guidePrice:"12.78",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t754/341/1237166856/40843/baf73c5c/5528c273Ncb42f04c.jpg",
			buyNum: 500
		}
		]
	});
});
```
在 `server/views/pages` 目录下修改文件 `index.jade` 

内容如下:

```jade
extends ../layout

block content
	.container
		.row
			each car in cars
				.col-sm-3
					.card
						a(href="/car/#{car._id}")
							img.card-img-top(src=car.imageLogo, alt=car.proTitle)
						.card-block
							h4.card-title.text-center=car.proTitle
							span.h3.text-danger=car.guidePrice
							span.text-danger 万元起
							a.btn.btn-primary.btn-sm.pull-right(href="/car/#{car._id}") 详情
						.card-footer.text-muted
							span #{car.buyNum}人正在购买	
```


重新启动node，打开浏览器,打开控制台,测试地址: `http://localhost:3000/`

### 详情页模拟数据

旧代码:

```js
app.get('/admin/car/list', function(req,res){
	res.render('car_list.jade',{
		title: '汽车商城 列表页'
	});
});
```
改为新代码:

```js
app.get('/car/:id', function(req,res){
	res.render('car_detail',{
		title: '汽车商城 详情页',
		car:{
			_id:1,
			proTitle:"英朗",
			brand:"别克",
			series:"英朗",
			color:"中国红",
			yearStyle:"2015款",
			modelName:"15N 手动 进取型",
			ml:"1.5L",
			kw:"84kw",
			gearbox:"6挡 手自一体",
			guidePrice:"11.99",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg",
			buyNum: 200			
		}
	});
});
```

在 `server/views/pages` 目录下修改文件 `car_detail.jade` 

内容如下:

```jade
extends ../layout

block content
	.container
		.row
			.col-md-6
				.text-center
					figure.figure
						img.img-thumbnail(src=car.imageLogo, alt=car.proTitle)
						figcaption.figure-caption=car.modelName
			.col-md-6
				dl.dl-horizontal
					dt.col-sm-3 厂牌
					dd.col-sm-9=car.brand
					dt.col-sm-3 车系
					dd.col-sm-9=car.series
					dt.col-sm-3 颜色
					dd.col-sm-9=car.color
					dt.col-sm-3 年款
					dd.col-sm-9=car.yearStyle
					dt.col-sm-3 车型
					dd.col-sm-9=car.modelName
					dt.col-sm-3 排量
					dd.col-sm-9=car.ml
					dt.col-sm-3 最大功率
					dd.col-sm-9=car.kw
					dt.col-sm-3 变速箱
					dd.col-sm-9=car.gearbox
					dt.col-sm-3 指导价(万)
					dd.col-sm-9=car.guidePrice
```

重新启动node，打开浏览器,打开控制台,测试地址: `http://localhost:3000/car/1`

### 列表页模拟数据

旧代码:

```js
app.get('/admin/car/list', function(req,res){
	res.render('car_list.jade',{
		title: '汽车商城 列表页'
	});
});
```
改为新代码:

```js
app.get('/admin/car/list', function(req,res){
	res.render('car_list.jade',{
		title: '汽车商城 列表页',
		cars:[{
			_id:1,
			proTitle:"英朗",
			brand:"别克",
			series:"英朗",
			color:"中国红",
			yearStyle:"2015款",
			modelName:"15N 手动 进取型",
			ml:"1.5L",
			kw:"84kw",
			gearbox:"6挡 手自一体",
			guidePrice:"11.99",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg",
			buyNum: 200
		},{
			_id:2,
			proTitle:"哈弗H6",
			brand:"哈弗",
			series:"哈弗",
			color:"雅致银",
			yearStyle:"2015款",
			modelName:"升级版 1.5T 手动 两驱 都市型",
			ml:"1.5L",
			kw:"110kw",
			gearbox:"6挡 手动",
			guidePrice:"9.63",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t874/304/396255796/41995/328da75e/5528c399N3f9cc646.jpg",
			buyNum: 888
		},{
			_id:3,
			proTitle:"速腾",
			brand:"大众",
			series:"速腾",
			color:"雅士银",
			yearStyle:"2015款",
			modelName:"1.4T 双离合 230TSI 舒适型",
			ml:"1.4L",
			kw:"96kw",
			gearbox:"7挡 双离合",
			guidePrice:"12.30",
			imageLogo:"http://img10.360buyimg.com/n7/jfs/t988/239/475904647/32355/a1d35780/55278f2cN574b21ab.jpg",
			buyNum: 100
		}]
	});
});
```

在 `server/views/pages` 目录下修改文件 `car_list.jade` 

内容如下:

```jade
extends ../layout

block content
	.container
		.row
			table.table.table-hover.table-bordered.table-striped.table-sm
				thead.thead-default
					tr
						th 厂牌
						th 车系
						th 颜色
						th 年款
						th 车型
						th 排量
						th 最大功率
						th 变速箱
						th 指导价(万)
						th 操作
				tbody
					each car in cars
						tr
							td=car.brand
							td=car.series
							td=car.color
							td=car.yearStyle
							td=car.modelName
							td=car.ml
							td=car.kw
							td=car.gearbox
							td=car.guidePrice
							td
								a.btn.btn-primary.btn-sm(href="/car/#{car._id}") 查看
								span &nbsp;
								a.btn.btn-primary.btn-sm(href="/admin/car/update/#{car._id}") 修改
								span &nbsp;
								button.btn.btn-danger.btn-sm(type="button", data-id="#{car._id}") 删除
```

重新启动node，打开浏览器,打开控制台,测试地址: `http://localhost:3000/admin/car/list`

### 录入页模拟数据

旧代码:

```js
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
```
改为新代码:

```js
app.get('/admin/car/new', function(req, res) {
  res.render('car_admin', {
    title: '汽车商城 后台录入页',
    car: {
      proTitle: "",
      brand: "",
      series: "",
      color: "",
      yearStyle: "",
      modelName: "",
      ml: "",
      kw: "",
      gearbox: "",
      guidePrice: "",
      imageLogo: "",
      buyNum: 0
    }
  });
});

app.get('/admin/car/update/:id', function(req, res) {
  res.render('car_admin', {
    title: '汽车商城 后台录入页',
    car: {
      _id: 1,
      proTitle: "英朗",
      brand: "别克",
      series: "英朗",
      color: "中国红",
      yearStyle: "2015款",
      modelName: "15N 手动 进取型",
      ml: "1.5L",
      kw: "84kw",
      gearbox: "6挡 手自一体",
      guidePrice: "11.99",
      imageLogo: "http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg",
      buyNum: 200
    }
  });
});
```


在 `server/views/pages` 目录下修改文件 `car_admin.jade` 

内容如下:

```jade
extends ../layout

block content
	.container
		.row
			form(id="form", method="post", action="/admin/car")
				input(type="hidden", name="car[_id]", value=car._id)
				.form-group.row
					label.col-sm-2.form-control-label(for="inputbrand") 厂牌
					.col-sm-10
						input#inputbrand.form-control(type="text", name="car[brand]", value=car.brand)
				.form-group.row
					label.col-sm-2.form-control-label(for="inputseries") 车系
					.col-sm-10
						input#inputseries.form-control(type="text", name="car[series]", value=car.series)
				.form-group.row
					label.col-sm-2.form-control-label(for="inputcolor") 颜色
					.col-sm-10
						input#inputcolor.form-control(type="text", name="car[color]", value=car.color)
				.form-group.row
					label.col-sm-2.form-control-label(for="yearStyle") 年款
					.col-sm-10
						input#yearStyle.form-control(type="text", name="car[yearStyle]", value=car.yearStyle)
				.form-group.row
					label.col-sm-2.form-control-label(for="inputmodelName") 车型
					.col-sm-10
						input#inputmodelName.form-control(type="text", name="car[modelName]", value=car.modelName)
				.form-group.row
					label.col-sm-2.form-control-label(for="ml") 排量
					.col-sm-10
						input#ml.form-control(type="text", name="car[ml]", value=car.ml)
				.form-group.row
					label.col-sm-2.form-control-label(for="kw") 最大功率
					.col-sm-10
						input#kw.form-control(type="text", name="car[kw]", value=car.kw)
				.form-group.row
					label.col-sm-2.form-control-label(for="gearbox") 变速箱
					.col-sm-10
						input#gearbox.form-control(type="text", name="car[gearbox]", value=car.gearbox)
				.form-group.row
					label.col-sm-2.form-control-label(for="guidePrice") 指导价(万)
					.col-sm-10
						input#guidePrice.form-control(type="text", name="car[guidePrice]", value=car.guidePrice)
				.form-group.row
					label.col-sm-2.form-control-label(for="imageLogo") 图片
					.col-sm-10
						input#imageLogo.form-control(type="text", name="car[imageLogo]", value=car.imageLogo)
				.row.text-right
					button.btn.btn-primary(type="submit") 提交

```
重新启动node，打开浏览器,打开控制台,测试地址: 

`http://localhost:3000/admin/car/new`

`http://localhost:3000/admin/car/update/1`

### 02-work 结束

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

分支 02-work 结束
