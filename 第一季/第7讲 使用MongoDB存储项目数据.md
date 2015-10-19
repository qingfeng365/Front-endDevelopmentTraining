# 第7讲 使用MongoDB存储项目数据


## 有关第4讲的重要更新

因为`modelName`是 `mongoose.Schema`的保留字，因此要将`modelName`改为`carModelName`

## 项目

本讲所涉及项目为第4讲创建的项目

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

本节内容为03-work分支

## 学习资源

- [mongoose官网](http://mongoosejs.com/index.html)
- [MongoDB 2.6 中文文档](http://docs.mongoing.com/manual-zh/index.html)

## mongoose基本概念

- **Schema** 用户定义的文档结构及方法
- **Model**  模型，本质上是mongoose根据Schema的定义封装的一个新对象，这个对象即包含Schema定义的方法，也包含mongoose扩展的方法
- **Document** 文档实例对象(Entity)，通过模型可以将普通对象，如一个字面量对象，封装成文档实例对象，该对象可通过mongoose扩展的方法进行数据库操作

命名约定

```js
schema...
Model...
doc...
```
### Schema

可以简单理解Schema为文档结构(类似于表结构)

Schema 是供用户定义的对象，用于创建 Model

### Schema type

- String
- Number
- Date
- Boolean
- Objectid

还有两种

对象: 直接用 {} 定义，相当于 Mixed
数组: 直接用 [] 定义，相当于 Array

附加属性: default

定义方式: 

- 字段名:类型 
- 字段名:{type:类型, default:缺省值}

日期型字段常用缺省值: Date.now

### 回调函数参数约定

第一个参数总是  `err` ，即错误对象。

第二个参数，可能是一个文档对象的数组，也可能是一个文档对象。

## 用mongoDb数据替换模拟数据

### 创建car的模型

在 `server/models` 目录下，创建 `car.js`

内容如下:

```js
var mongoose = require('mongoose');

var SchemaCar = new mongoose.Schema({
  proTitle: String,
  brand: String,
  series: String,
  color: String,
  yearStyle: String,
  carModelName: String,
  ml: String,
  kw: String,
  gearbox: String,
  guidePrice: String,
  imageLogo: String,
  buyNum: {
    type: Number,
    default: 0
  },
  meta: {
    createDate: {
      type: Date,
      default: Date.now()
    },
    updateDate: {
      type: Date,
      default: Date.now()
    }
  }  
});

var ModelCar = mongoose.model('ModelCar', SchemaCar, 'car');

module.exports = ModelCar;


```

- **mongoose.Schema**

mongoose.Schema 是构造器函数，因此Schema首字母大写，

而 `var schemaCar = new mongoose.Schema(...)` 是用构造器创建了对象.

- **mongoose.model**

mongoose.model 是普通函数，因此model首字母小写，同时model返回的是构造器函数，

`var ModelCar ` 是构造器函数，因此ModelCar首字母大写

### 向数据库添加模拟数据

在`server` 目录，新建 `addDemoData.js`

内容如下：

```js
var mongoose = require('mongoose');

var ModelCar = require('./models/car');

mongoose.connect('mongodb://localhost/carShop');

var carArray = [{
    proTitle: "英朗",
    brand: "别克",
    series: "英朗",
    color: "中国红",
    yearStyle: "2015款",
    carModelName: "15N 手动 进取型",
    ml: "1.5L",
    kw: "84kw",
    gearbox: "6挡 手自一体",
    guidePrice: "11.99",
    imageLogo: "http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg",
    buyNum: 200
  },
  {
    proTitle: "哈弗H6",
    brand: "哈弗",
    series: "哈弗",
    color: "雅致银",
    yearStyle: "2015款",
    carModelName: "升级版 1.5T 手动 两驱 都市型",
    ml: "1.5L",
    kw: "110kw",
    gearbox: "6挡 手动",
    guidePrice: "9.63",
    imageLogo: "http://img10.360buyimg.com/n7/jfs/t874/304/396255796/41995/328da75e/5528c399N3f9cc646.jpg",
    buyNum: 888
  },
  {
    proTitle: "速腾",
    brand: "大众",
    series: "速腾",
    color: "雅士银",
    yearStyle: "2015款",
    carModelName: "1.4T 双离合 230TSI 舒适型",
    ml: "1.4L",
    kw: "96kw",
    gearbox: "7挡 双离合",
    guidePrice: "12.30",
    imageLogo: "http://img10.360buyimg.com/n7/jfs/t988/239/475904647/32355/a1d35780/55278f2cN574b21ab.jpg",
    buyNum: 100
  },
  {
    proTitle: "捷达",
    brand: "一汽大众",
    series: "捷达",
    color: "珊瑚蓝",
    yearStyle: "2015款",
    carModelName: "质惠版 1.4L 手动时尚型",
    ml: "1.4L",
    kw: "66kw",
    gearbox: "5挡 手动",
    guidePrice: "7.51",
    imageLogo: "http://img10.360buyimg.com/n7/jfs/t1108/41/489298815/33529/38655c9f/5528c276N41f39d00.jpg",
    buyNum: 300
  },
  {
    proTitle: "本田XR-V",
    brand: "东风本田",
    series: "XR-V",
    color: "炫金银",
    yearStyle: "2015款",
    carModelName: "1.5L 自动 经典版",
    ml: "1.5L",
    kw: "96kw",
    gearbox: "无级挡 CVT无级变速",
    guidePrice: "12.78",
    imageLogo: "http://img10.360buyimg.com/n7/jfs/t754/341/1237166856/40843/baf73c5c/5528c273Ncb42f04c.jpg",
    buyNum: 500
  }
];

ModelCar.create(carArray,function(err, cars){
  if(err){
    console.log(err);
  }else{
    console.log('新增 %d 条记录', cars.length);
  }
  mongoose.disconnect();
});

```

在项目根目录命令行窗口：

```bash
node server/addDemoData
```

执行成功后，用 Robomongo 查看数据。

### 

### 增加读取数据方法

- fetch : 读取全部数据，并按日期排序
- findById : 查询指定Id的文档

在 `server/models` 目录，修改 `car.js`

在下面代码之前插入:

> 'var ModelCar = mongoose.model('ModelCar', schemaCar, 'car');'

新增内容:

```js
schemaCar.statics = {
  fetch: function(cb) {
    return this
      .find({})
      .sort('meta.createDate')
      .exec(cb);
  },
  findById: function(id, cb) {
    return this
      .findOne({
        _id: id
      })
      .exec(cb);
  }
}
```

### 将首页路由处理改为从数据库读取



修改 `app.js` ，在下面代码之后增加代码 

> `var app = express();`

```js
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/carShop');

var ModelCar = require('./models/car');
```

旧代码:

```js 
app.get('/', function(req, res) { 
  ...... 
}); 
``` 

改为新代码:

```js
app.get('/', function(req, res) {
  ModelCar.fetch(function(err, cars) {
    if (err) {
      console.log(err);
      return res.sendStatus(500);
    }
    res.render('index', {
      title: '汽车商城 首页',
      cars: cars
    });
  });
});
```

### 增加错误处理中间件

安装 `errorhandler` 中间件

在第4讲的项目根目录，打开命令行窗口：

```bash
cnpm install errorhandler
```


修改 `app.js` ，在文件的最后，增加以下代码：

```js
var errorhandler = require('errorhandler');
app.use(errorhandler);
```
在开发环境，详细的错误信息将发到浏览器。

在生产环境，调用方式为：
 
```bash
NODE_ENV=production node server/app
```
用这种方式，则不会将详细的错误信息发到浏览器。


使用错误处理中间件后，则要上面的代码改为:

```js
app.get('/', function(req, res, next) {
  ModelCar.fetch(function(err, cars) {
    if (err) {
      return next(err);
    }
    res.render('index', {
      title: '汽车商城 首页',
      cars: cars
    });
  });
});
```
### 关于express路由处理next的说明

一共有三种形式:

- next()     转本路径路由的下一个处理句柄
- next(err)  转错误处理路由的中间件 app.use()
- next('route')  跳过本路径路由，找下一个同路径路由处理


### 将详情页路由处理改为从数据库读取

修改 `app.js` 

旧代码:

```js 
app.get('/car/:id', function(req, res) {
  ...... 
}); 
``` 

改为新代码:

```js
app.get('/car/:id', function(req, res, next) {
  var id = req.params.id;
  ModelCar.findById(id, function(err, car) {
    if (err) {
      return next(err);
    }
    res.render('car_detail', {
      title: '汽车商城 详情页',
      car: car
    });
  });
});
```

### 将列表页路由处理改为从数据库读取

修改 `app.js` 

旧代码:

```js 
app.get('/admin/car/list', function(req, res) {
  ...... 
}); 
```
改为新代码:

```js
app.get('/admin/car/list', function(req, res, next) {
  ModelCar.fetch(function(err, cars) {
    if (err) {
      return next(err);
    }
    res.render('car_list.jade', {
      title: '汽车商城 列表页',
      cars: cars
    });
  });
});
```

### 在后台列表页增加显示meta.createDate

在 `server/views/pages` 目录下修改文件 `car_list.jade` 

增加代码:

```jade

            th 指导价(万)
            th 录入日期
            th 操作

```

```jade
              
              td=car.gearbox
              td=car.guidePrice
              td=car.meta.createDate

```

在浏览器测试 `http://localhost:3000/admin/car/list`，发现日期格式不符。

需要引入 `moment` 轻量级js日期处理库，由于只要页面有需要处理日期，因此把 `moment` 直接放到 app.local 对象上. 

>
> **app.locals**
> 
> The app.locals object is a JavaScript object, and its properties are local > > variables within the application.
> 
> app.locals.title
> // => 'My App'
> 
> app.locals.email
> // => 'me@myapp.com'
> 
> Once set, the value of app.locals properties persist throughout the life of > the application, in contrast with res.locals properties that are valid only > for the lifetime of the request.
> 
> You can access local variables in templates rendered within the application. > This is useful for providing helper functions to templates, as well as app-level data. 

修改 `app.js` 

修改 `app.js` ，在下面代码之后增加代码 

> `app.use(express.static(path.join(__dirname, '../client')));`

```js
app.locals.moment = require('moment');
```

在 `server/views/pages` 目录下修改文件 `car_list.jade` 

```jade
              td=car.gearbox
              td=car.guidePrice
              td=moment(car.meta.createDate).format("YYYY-MM-DD")
```

[Moment.js 文档](http://momentjs.cn/docs/#/parsing/string-format/)

### 增加后台录入页模板少了的proTitle字段

修改 `server/views/pages` 目录的 `car_admin.jade`

```jade
				.form-group.row
					label.col-sm-2.form-control-label(for="inputproTitle") 展示名称
					.col-sm-10
						input#inputproTitle.form-control(type="text", name="car[proTitle]", value=car.proTitle)
```

### 将后台录入页路由处理改为从数据库读取

修改 `app.js` 

旧代码:

```js 
app.get('/admin/car/new', function(req, res) {
  ...... 
}); 
```
改为新代码:

```js
app.get('/admin/car/new', function(req, res) {
  res.render('car_admin', {
    title: '汽车商城 后台录入页',
    car: {}
  });
});
```


旧代码:

```js 
app.get('/admin/car/update/:id', function(req, res) {
  ...... 
}); 
```
改为新代码:

```js
app.get('/admin/car/update/:id', function(req, res, next) {
  var id = req.params.id;
  ModelCar.findById(id, function(err, car) {
    if (err) {
      return next(err);
    }
    res.render('car_admin', {
      title: '汽车商城 后台录入页',
      car: car
    });
  });
});
```
### 后台录入页-新增的提交路由处理

测试一下后台获取的数据

修改 `app.js`, 增加路由处理：

```js
app.post('/admin/car',function(req,res){
  console.log(req.body);

  res.sendStatus(200);
});
```

在谷歌浏览器查看数据传输过程。

引用 `body-parser`, 

修改 `app.js` ，在下面代码之前增加代码 

> `app.get('/', function(req, res) {`

```js
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({
  extended: true
}));
```

已经可以解析Form Data数据到req.body

但是对于新增，不应该有_id属性，需要处理一下视图


修改 `server/views/pages` 目录的 `car_admin.jade`

对隐藏input增加条件:

```jade
			form(id="form", method="post", action="/admin/car")
				if car._id
					input(type="hidden", name="car[_id]", value=car._id)
```

修改 `app.js`，增加新增的保存代码：

```js
app.post('/admin/car', function(req, res) {
  var carObj = req.body.car;
  if(!carObj){
    return res.status(400).send('找不到合法数据.');
  }
  var id = carObj._id;
  if (!id) {
    //新增
    var docCar = new ModelCar(carObj);
    docCar.save(function(err, _car) {
      if (err) {
        return next(err);
      }
      return res.redirect('/car/' + _car._id);
    });
  }
});
```

### 后台录入页-修改的提交路由处理


测试通过后，修改 `app.js`，增加修改的保存代码：

```js
app.post('/admin/car', function(req, res, next) {
  var carObj = req.body.car;
  if (!carObj) {
    return res.status(400).send('找不到合法数据.');
  }
  var id = carObj._id;
  if (!id) {
    //新增
    var docCar = new ModelCar(carObj);
    docCar.save(function(err, _car) {
      if (err) {
        return next(err);
      }
      return res.redirect('/car/' + _car._id);
    });
  } else {
    //修改
    ModelCar.findByIdAndUpdate(id, carObj, function(err, _car) {
      if (err) {
        return next(err);
      }
      return res.redirect('/car/' + id);
    });
  }
});
```
### 对列表页-删除的路由处理

修改 `app.js`，增加删除的处理代码：

```js
// /admin/list?id=xxxxx
 
app.delete('/admin/list', function(req, res, next) {
  var id = req.query.id;
  if (id) {
    ModelCar.findByIdAndRemove(id, function(err, _car) {
      if (err) {
        res.status(500).json({
          ok: 0
        });
        return next(err);
      } else {
        res.json({
          ok: 1
        });
      }
    });
  } else {
    res.json({
      ok: 0
    });
  }
});
```

### 对列表页-删除按钮的处理:方案一

点击删除按钮后，通过jquery的ajax方法

首先要对删除按钮增加类名，如： `.del`

修改 `car_list.jade`

> 旧代码:

```jade
                button.btn.btn-danger.btn-sm(type="button", data-id="#{car._id}") 删除
```

> 新代码:

```jade
                button.del.btn.btn-danger.btn-sm(type="button", data-id="#{car._id}") 删除    
```
 
增加前端页面的处理代码，在 `client\js` 目录增加 `car_list.js`

内容如下:

```js
'use strict';

$(function() {
  $('.del').click(function(event) {
    var target = $(event.target);
    var id = target.data('id');
    var tr = $('.item-id-' + id);
    console.log(id);
    $.ajax({
      type: 'DELETE',
      url: '/admin/list?id=' + id
    }).done(function(results) {
      console.log(results);
      if (results.ok === 1) {
        if (tr.length > 0) {
          tr.remove();
        }
      }
    });
  });
});

```

修改 `car_list.jade`，增加对 `car_list.js` 的引用

不能直接在原来的 `block content` 的内容增加，因为这样`car_list.js`会在`jquery`之前。

要另外增加 block

```jade
block pagesrc
  script(src="/js/car_list.js")
```
修改`layout.jade`，增加对 `block pagesrc` 的引用

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
    block pagesrc
```

测试通过。



### 对列表页-删除按钮的处理:方案二


修改 `car_list.jade`

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
						th 录入日期
						th 操作
				tbody
					each car in cars
						tr(class="item-id-#{car._id}")
							td=car.brand
							td=car.series
							td=car.color
							td=car.yearStyle
							td=car.carModelName
							td=car.ml
							td=car.kw
							td=car.gearbox
							td=car.guidePrice
							td=moment(car.meta.createDate).format("YYYY-MM-DD")
							td
								a.btn.btn-primary.btn-sm(href="/car/#{car._id}") 查看
								span &nbsp;
								a.btn.btn-primary.btn-sm(href="/admin/car/update/#{car._id}") 修改
								span &nbsp;
								button.btn.btn-danger.btn-sm(type="button", 
									data-id="#{car._id}",data-toggle="modal", data-target="#delConfirm") 删除
	.container							
		#delConfirm.modal.fade
			.modal-dialog
				.modal-content
					.modal-header
						button.close(type="button", data-dismiss="modal")
							span &times;
						h6.modal-title 智能助手
					.modal-body
						p 确定删除吗?
					.modal-footer
						button#delConfirmbtnOk.btn.btn-primary(type="button") 确定
						button.btn.btn-secondary(type="button", data-dismiss="modal") 取消
block pagesrc
	script(src="/js/car_list.js")
```

修改 `layout.jade`

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
		block pagesrc
```

在 `client\js` 目录新增 `car_list.js`

内容如下:

```js
$(function() {
  $('#delConfirm').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);
    var id = button.attr('data-id');
    var okbtn = $('#delConfirmbtnOk');
    okbtn.attr('data-id', id);
  });

  $('#delConfirmbtnOk').click(function(event) {
    var target = $(event.target);
    var id = target.data('id');
    var tr = $('.item-id-' + id);

    console.log(id);

    $.ajax({
      type: 'DELETE',
      url: '/admin/list?id=' + id
    }).done(function(results) {
      console.log(results);
      if (results.ok === 1) {
        if (tr.length > 0) {
          tr.remove();
        }
      }
      $('#delConfirm').modal('hide');
    });
  });

});

```

### 增加处理最新修改日期的代码

在 `server/models` 目录下，修改 `car.js`
```js

schemaCar.pre('save',function(next){
  if (!this.isNew){
    this.meta.updateDate = Date.now();
  }
  next();
});

```

### 03-work 结束

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

分支 03-work 结束


### Document对象实用方法

Document#isNew

Boolean flag specifying if the document is new.


### schema 保留字

schema.js  line:434

```js
Schema.reserved = Object.create(null);
var reserved = Schema.reserved;
// EventEmitter
reserved.emit =
reserved.on =
reserved.once =
// document properties and functions
reserved.collection =
reserved.db =
reserved.errors =
reserved.init =
reserved.isModified =
reserved.isNew =
reserved.get =
reserved.modelName =
reserved.save =
reserved.schema =
reserved.set =
reserved.toObject =
reserved.validate =
// hooks.js
reserved._pres = reserved._posts = 1;
```


一个有趣的问题是，为什么 Node.js 约定，回调函数的第一个参数，必须是错误对象err（如果没有错误，该参数就是 null）？原因是执行分成两段，在这两段之间抛出的错误，程序无法捕捉，只能当作参数，传入第二段。