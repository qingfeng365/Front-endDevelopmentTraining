# 第7讲 使用MongoDB存储项目数据

tags: Mongoose MongoDB

[TOC]

## 有关第4讲的重要更新

因为`modelName`是 `mongoose.Schema`的保留字，因此要将`modelName`改为`carModelName`

## 项目

本讲所涉及项目为第4讲创建的项目

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

本节内容为03-work分支

## 学习资源

- [mongoose官网](http://mongoosejs.com/index.html)
- [](http://mongoosejs.com/docs/index.html)
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
'use strict';

var mongoose = require('mongoose');

var schemaCar = new mongoose.Schema({
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

var ModelCar = mongoose.model('ModelCar', schemaCar, 'car');

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
      .sort('-meta.createDate')
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

### 对Query.sort()方法的说明

`sort(arg)` : 参数可以是字符串，或对象。

- **字符串形式:** 用空格分隔路径列表， 默认升序，降序使用 `-路径名`
  
    如: `query.sort('field -test');`
    
- **对象形式:** 对象中可列举多个属性，属性名为路径名，多级路径要为引号，属性值为以下几种选项
    + 升序：`'asc'`  `'ascending'` `1`
    + 降序：`'desc'`  `'descending'` `-1`
     
    如: `query.sort({ field: 'asc', test: -1 });`

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

## mongoose 常用方法简介

语法参数说明:

- **path:** 相当于字段名，当要指向某属性时，可用 父属性.子属性 的形式访问嵌套的属性
- **doc:** 表示该参数是一个由Model创建的Document对象
- **update:** 普通js对象(即用来创建Document对象的原始对象) 或 含原生`MongoDB`的`update`运算符的普通js对象
- conditions : 查询条件对象
- **options:** 提供配置的普通js对象，一般不需要，因mongoose已做了优化
- **callpack:** 回调函数

返回特殊说明:

- **query 对象:** 当一个操作是 **查询** 时，如果不定义回调函数，则该操作不会马上执行，而是返回一个 `query 对象`，可以继续链式调用`query`的方法，直到执行`exec(callback)`方法。(如果exec()中没有callback,则exec()返回的是Promise 对象)

- **Promise 对象:** 当一个操作是 **更新** 时，如果不定义回调函数，则会返回Promise 对象。

[promise的行为模式](http://www.ituring.com.cn/article/54547)

```
Promise.then(onFulFill, onReject)
```

then(成功/覆行时的回调,失败/拒绝时的回调)

- 在回调中，可以选择返回 普通值或对象，也可以继续返回另外一个Promise。
- then()返回的也是一个Promise，可以继续执行then()
- 上一个then()定义的回调返回的 普通值或对象，则返回值将做为下一个then()定义的回调函数的参数。
- 如果上一个then()定义的回调返回的是 另外一个Promise，则要等这个被返回的Promise执行结束，并将执行结果做为参数传到下一个then()定义的回调函数
- 如果Promise执行时出现错误，则会执行 then()链条中定义了onReject回调的方法。ø

示例：

```js
var promise = Meetups.find({ tags: 'javascript' }).select('_id').exec();
promise.then(function (meetups) {
  var ids = meetups.map(function (m) {
    return m._id;
  });
  return People.find({ meetups: { $in: ids }).exec();
}).then(function (people) {
  if (people.length < 10000) {
    throw new Error('Too few people!!!');
  } else {
    throw new Error('Still need more people!!!');
  }
}).then(null, function (err) {
  assert.ok(err instanceof Error);
});
```







### Document对象实用方法

这些方法的官方说明:

[document.js]<http://mongoosejs.com/docs/api.html#document-js>

**最常用方法**

<table>
  <thead>
    <tr>
      <th>方法/属性</th>
      <th>无回调时返回</th>
      <th>说明</th>
      <th>示例</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>id</td>
      <td></td>
      <td>即_id</td>
      <td></td>
    </tr>
    <tr>
      <td>get(path,[type])</td>
      <td></td>
      <td>读属性值</td>
      <td></td>
    </tr> 
    <tr>
      <td>set(path,varlue,[type])</td>
      <td></td>
      <td>写属性值</td>
      <td></td>
    </tr>
    <tr>
      <td>
      save([callpack]) <br/>
      save()
      </td>
      <td>Promise</td>
      <td>保存文档,自动新增或修改</td>
      <td>
        <pre><code>
product.sold = Date.now();
product.save(function (err, product, numAffected) {
  if (err) ..
})
//----------
product.save().then(function(product) {
   ...
});
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>isNew</td>
      <td></td>
      <td>还没有存储过的新对象</td>
      <td></td>
    </tr>
  </tbody>
</table>

**需了解方法**

<table>
  <thead>
    <tr>
      <th>方法/属性</th>
      <th>无回调时返回</th>
      <th>说明</th>
      <th>示例</th>
    </tr>
  </thead>
    <tr>
      <td>equals(doc)</td>
      <td>Query</td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>update(update,[options],[callback]</td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>remove([callback])</td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>isSelect(path)</td>
      <td>isModified(path)</td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>errors</td>
      <td></td>
      <td>错误对象数组</td>
      <td></td>
    </tr>
    <tr>
      <td>schema</td>
      <td></td>
      <td>schema对象</td>
      <td></td>
    </tr>
  </tbody>
</table>


### Model 或 Query 方法。

<table>
  <thead>
    <tr>
      <th>方法/属性</th>
      <th>无回调时返回</th>
      <th>说明</th>
      <th>示例</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Model.create(doc(s), [callback])</td>
      <td>Promise</td>
      <td>批量新增 doc(s)的成员是普通JS对象</td>
      <td></td>
        <pre><code>
// pass individual docs
Candy.create({ type: 'jelly bean' }, { type: 'snickers' }, function (err, jellybean, snickers) {
  if (err) // ...
});

// pass an array
var array = [{ type: 'jelly bean' }, { type: 'snickers' }];
Candy.create(array, function (err, candies) {
  if (err) // ...

  var jellybean = candies[0];
  var snickers = candies[1];
  // ...
});

// callback is optional; use the returned promise if you like:
var promise = Candy.create({ type: 'jawbreaker' });
promise.then(function (jawbreaker) {
  // ...
})
        </code></pre>
    </tr>
    <tr>
      <td>
        Model.count(conditions, [callback])
        <br/><br/>
        Query#count([criteria], [callback])
      </td>
      <td>Query</td>
      <td></td>
      <td>
        <pre><code>
Adventure.count({ type: 'jungle' }, function (err, count) {
  if (err) ..
  console.log('there are %d jungle adventures', count);
});
//------
var countQuery = model.where({ 'color': 'black' }).count();

query.count({ color: 'black' }).count(callback)

query.count({ color: 'black' }, callback)

query.where('color', 'black').count(function (err, count) {
  if (err) return handleError(err);
  console.log('there are %d kittens', count);
})
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
      Model.distinct(field, [conditions], [callback])</td>
      <td>Query</td>
      <td>结果为指定字段不同值</td>
      <td>
        <pre><code>
Link.distinct('url', { clicks: {$gt: 100}}, function (err, result) {
  if (err) return handleError(err);

  assert(Array.isArray(result));
  console.log('unique urls with more than 100 clicks', result);
})

var query = Link.distinct('url');
query.exec(callback);
//-----
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
        Model.find(conditions, [projection], [options], [callback])
        //条件,返回字段列表,配置,回调
        <br/><br/>
        Query#find([criteria], [callback])
      </td>
      <td>Query</td>
      <td>查找</td>
      <td>
        <pre><code>
// named john and at least 18
MyModel.find({ name: 'john', age: { $gte: 18 }});

// executes immediately, passing results to callback
MyModel.find({ name: 'john', age: { $gte: 18 }}, function (err, docs) {});

// name LIKE john and only selecting the "name" and "friends" fields, executing immediately
MyModel.find({ name: /john/i }, 'name friends', function (err, docs) { })

// passing options
MyModel.find({ name: /john/i }, null, { skip: 10 })

// passing options and executing immediately
MyModel.find({ name: /john/i }, null, { skip: 10 }, function (err, docs) {});

// executing a query explicitly
var query = MyModel.find({ name: /john/i }, null, { skip: 10 })
query.exec(function (err, docs) {});

// using the promise returned from executing a query
var query = MyModel.find({ name: /john/i }, null, { skip: 10 });
var promise = query.exec();
promise.addBack(function (err, docs) {});
//---
query.find({ name: 'Los Pollos Hermanos' }).find(callback)
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
      Model.findOne([conditions], [projection], [options], [callback])
      <br/><br/>
      Query#findOne([criteria], [projection], [callback])
      </td>
      <td>Query</td>
      <td>查找第一笔</td>
      <td>
        <pre><code>
// find one iphone adventures - iphone adventures??
Adventure.findOne({ type: 'iphone' }, function (err, adventure) {});

// same as above
Adventure.findOne({ type: 'iphone' }).exec(function (err, adventure) {});

// select only the adventures name
Adventure.findOne({ type: 'iphone' }, 'name', function (err, adventure) {});

// same as above
Adventure.findOne({ type: 'iphone' }, 'name').exec(function (err, adventure) {});

// specify options, in this case lean
Adventure.findOne({ type: 'iphone' }, 'name', { lean: true }, callback);

// same as above
Adventure.findOne({ type: 'iphone' }, 'name', { lean: true }).exec(callback);

// chaining findOne queries (same as above)
Adventure.findOne({ type: 'iphone' }).select('name').lean().exec(callback);

//----------
var query  = Kitten.where({ color: 'white' });
query.findOne(function (err, kitten) {
  if (err) return handleError(err);
  if (kitten) {
    // doc may be null if no document matched
  }
});
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
        Model.findOneAndRemove(conditions, [options], [callback])
        <br/><br/>
        Query#findOneAndRemove([conditions], [options], [callback])
      </td>
      <td></td>
      <td></td>
      <td>
A.findByIdAndRemove(id, options, callback) // executes
A.findByIdAndRemove(id, options)  // return Query
A.findByIdAndRemove(id, callback) // executes
A.findByIdAndRemove(id) // returns Query
A.findByIdAndRemove()           // returns Query

A.where().findOneAndRemove(conditions, options, callback) // executes
A.where().findOneAndRemove(conditions, options)  // return Query
A.where().findOneAndRemove(conditions, callback) // executes
A.where().findOneAndRemove(conditions) // returns Query
A.where().findOneAndRemove(callback)   // executes
A.where().findOneAndRemove()           // returns Query
        <pre><code>
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
        Model.findOneAndUpdate([conditions], [update], [options], [callback])
        <br/><br/>
        Query#findOneAndUpdate([query], [doc], [options], [callback])
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
A.findOneAndUpdate(conditions, update, options, callback) // executes
A.findOneAndUpdate(conditions, update, options)  // returns Query
A.findOneAndUpdate(conditions, update, callback) // executes
A.findOneAndUpdate(conditions, update)           // returns Query
A.findOneAndUpdate()                             // returns Query

query.findOneAndUpdate(conditions, update, options, callback) // executes
query.findOneAndUpdate(conditions, update, options)  // returns Query
query.findOneAndUpdate(conditions, update, callback) // executes
query.findOneAndUpdate(conditions, update)           // returns Query
query.findOneAndUpdate(update, callback)             // returns Query
query.findOneAndUpdate(update)                       // returns Query
query.findOneAndUpdate(callback)                     // executes
query.findOneAndUpdate()                             // returns Query
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
        Model.remove(conditions, [callback])
        <br/><br/>
        Query#remove([criteria], [callback])
      </td>
      <td>
      Query
      </td>
      <td></td>
      <td>
        <pre><code>
Comment.remove({ title: 'baby born from alien father' }, function (err) {

});
var query = Comment.remove({ _id: id });
query.exec();

//-------------
// not executed
var query = Model.find().remove({ name: 'Anne Murray' })

// executed
query.remove({ name: 'Anne Murray' }, callback)
query.remove({ name: 'Anne Murray' }).remove(callback)

// executed without a callback (unsafe write)
query.exec()

// summary
query.remove(conds, fn); // executes
query.remove(conds)
query.remove(fn) // executes
query.remove()        
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
      Model.update(conditions, doc, [options], [callback])
        <br/><br/>
        Query#update([criteria], [doc], [options], [callback])
      </td>
      <td>Query</td>
      <td></td>
      <td>
        <pre><code>
Valid options:

    safe (boolean): safe mode (defaults to value set in schema (true))
    upsert (boolean): whether to create the doc if it doesn't match (false)
    multi (boolean): whether multiple documents should be updated (false)
    strict (boolean): overrides the strict option for this update
    overwrite (boolean): disables update-only mode, allowing you to overwrite the doc (false)
//------
MyModel.update({ age: { $gt: 18 } }, { oldEnough: true }, fn);
MyModel.update({ name: 'Tobi' }, { ferret: true }, { multi: true }, function (err, raw) {
  if (err) return handleError(err);
  console.log('The raw response from Mongo was ', raw);
});
//------
Model.where({ _id: id }).update({ title: 'words' })

// becomes

Model.where({ _id: id }).update({ $set: { title: 'words' }})

//-----
var q = Model.where({ _id: id });
q.update({ $set: { name: 'bob' }}).update(); // not executed

q.update({ $set: { name: 'bob' }}).exec(); // executed as unsafe

// keys that are not $atomic ops become $set.
// this executes the same command as the previous example.
q.update({ name: 'bob' }).exec();

// overwriting with empty docs
var q = Model.where({ _id: id }).setOptions({ overwrite: true })
q.update({ }, callback); // executes

// multi update with overwrite to empty doc
var q = Model.where({ _id: id });
q.setOptions({ multi: true, overwrite: true })
q.update({ });
q.update(callback); // executed

// multi updates
Model.where()
     .update({ name: /^match/ }, { $set: { arr: [] }}, { multi: true }, callback)

// more multi updates
Model.where()
     .setOptions({ multi: true })
     .update({ $set: { arr: [] }}, callback)

// single update by default
Model.where({ email: 'address@example.com' })
     .update({ $inc: { counter: 1 }}, callback)

        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
        Query#limit(val)
      </td>
      <td>Query</td>
      <td></td>
      <td>
        <pre><code>
        query.limit(20)
        </code></pre>
      </td>
    </tr>  
    <tr>
      <td>
        Query#select(arg)
      </td>
      <td>Query</td>
      <td></td>
      <td>
        <pre><code>
// include a and b, exclude other fields
query.select('a b');

// exclude c and d, include other fields
query.select('-c -d');

// or you may use object notation, useful when
// you have keys already prefixed with a "-"
query.select({ a: 1, b: 1 });
query.select({ c: 0, d: 0 });

// force inclusion of field excluded at schema level
query.select('+path')
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
        Query#sort(arg)
      </td>
      <td>Query</td>
      <td></td>
      <td>
        <pre><code>
// sort by "field" ascending and "test" descending
query.sort({ field: 'asc', test: -1 });

// equivalent
query.sort('field -test');
        </code></pre>
      </td>
    </tr>    
    <tr>
      <td>
        Query#skip(val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        query.skip(100).limit(20)
        </code></pre>
      </td>
    </tr> 
  </tbody>
</table>

### 查询运算符方法

```js
// With a JSON doc
Person.
  find({
    occupation: /host/,
    'name.last': 'Ghost',
    age: { $gt: 17, $lt: 66 },
    likes: { $in: ['vaporizing', 'talking'] }
  }).
  limit(10).
  sort({ occupation: -1 }).
  select({ name: 1, occupation: 1 }).
  exec(callback);
  
// Using query builder
Person.
  find({ occupation: /host/ }).
  where('name.last').equals('Ghost').
  where('age').gt(17).lt(66).
  where('likes').in(['vaporizing', 'talking']).
  limit(10).
  sort('-occupation').
  select('name occupation').
  exec(callback);
```



<table>
  <thead>
    <tr>
      <th>方法/属性</th>
      <th>无回调时返回</th>
      <th>说明</th>
      <th>示例</th>
    </tr>
  </thead>
  <tbody>
    </tr> 
    <tr>
      <td>
        Query#where([path], [val])
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
// instead of writing:
User.find({age: {$gte: 21, $lte: 65}}, callback);

// we can instead write:
User.where('age').gte(21).lte(65);

// passing query conditions is permitted
User.find().where({ name: 'vonderful' })

// chaining
User
.where('age').gte(21).lte(65)
.where('name', /^vonderful/i)
.where('friends').slice(10)
.exec(callback)        
        </code></pre>
      </td>
    </tr> 
     <tr>
      <td>
        Query#gt([path], val)
      </td>
      <td></td>
      <td>大于</td>
      <td>
        <pre><code>
Thing.find().where('age').gt(21)

// or
Thing.find().gt('age', 21)        
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
      Query#gte([path], val)
      </td>
      <td></td>
      <td>大于等于</td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr>              
    <tr>
      <td>
      Query#lt([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
      Query#lte([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr>     
    <tr>
      <td>
      Query#ne([path], val)
      </td>
      <td>不等于</td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr>
    <tr>
      <td>
      Query#in([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
      Query#nin([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
      Query#or(array)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        query.or([{ color: 'red' }, { status: 'emergency' }])
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
      Query#and(array)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        query.and([{ color: 'green' }, { status: 'ok' }])
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
      Query#nor(array)
      </td>
      <td>全否</td>
      <td></td>
      <td>
        <pre><code>
        query.nor([{ color: 'green' }, { status: 'ok' }])
        </code></pre>
      </td>
    </tr>     
    <tr>
      <td>
Query#exists([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
// { name: { $exists: true }}
Thing.where('name').exists()
Thing.where('name').exists(true)
Thing.find().exists('name')

// { name: { $exists: false }}
Thing.where('name').exists(false);
Thing.find().exists('name', false);        
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
Query#mod([path], val)
      </td>
      <td>=取模的余数</td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
Query#regex([path], val)
      </td>
      <td></td>
      <td></td>
      <td>
        <pre><code>
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
Query#all([path], array)
      </td>
      <td>对数组属性的条件</td>
      <td></td>
      <td>
        <pre><code>
        query.all('myarr', ['one','two','three'])
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
Query#elemMatch(path, criteria)
      </td>
      <td>对象字段(子文档)的子属性</td>
      <td></td>
      <td>
        <pre><code>
query.elemMatch('comment', { author: 'autobot', votes: {$gte: 5}})

query.where('comment').elemMatch({ author: 'autobot', votes: {$gte: 5}})

query.elemMatch('comment', function (elem) {
  elem.where('author').equals('autobot');
  elem.where('votes').gte(5);
})

query.where('comment').elemMatch(function (elem) {
  elem.where({ author: 'autobot' });
  elem.where('votes').gte(5);
})        
        </code></pre>
      </td>
    </tr> 
    <tr>
      <td>
Query#size([path], val)
      </td>
      <td>数组成员数</td>
      <td></td>
      <td>
        <pre><code>
        MyModel.where('tags').size(0).exec(function (err, docs) {
  if (err) return handleError(err);

  assert(Array.isArray(docs));
  console.log('documents with 0 tags', docs);
})
        </code></pre>
      </td>
    </tr>             
  </tbody>
</table>

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
