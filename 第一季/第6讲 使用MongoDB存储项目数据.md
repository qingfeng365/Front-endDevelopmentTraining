# 第6讲 使用MongoDB存储项目数据


## 有关第4讲的重要更新

因为`modelName`是 `mongoose.Schema`的保留字，因此要将`modelName`改为`carModelName`

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
