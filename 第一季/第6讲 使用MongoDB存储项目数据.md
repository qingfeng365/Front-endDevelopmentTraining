# 第6讲 使用MongoDB存储项目数据

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
  modelName: String,
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




### Document对象实用方法

Document#isNew

Boolean flag specifying if the document is new.



