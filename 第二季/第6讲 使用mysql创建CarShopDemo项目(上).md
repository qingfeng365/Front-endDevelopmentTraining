# 第6讲 使用mysql创建CarShopDemo项目

<!-- MarkdownTOC -->

- [MySQL资源](#mysql资源)
- [在github中新建项目并克隆](#在github中新建项目并克隆)
- [复制文件](#复制文件)
- [安装新模块](#安装新模块)
- [创建数据库](#创建数据库)
- [sequelize](#sequelize)
  - [如何连接](#如何连接)
  - [如何建立模型](#如何建立模型)
  - [如何书写模型文件和引用模型](#如何书写模型文件和引用模型)
- [查询语法](#查询语法)
  - [查询参数对象](#查询参数对象)
- [关于promise](#关于promise)
  - [bluebird](#bluebird)
- [模型常用方法](#模型常用方法)
  - [查找某个特定实例](#查找某个特定实例)
  - [查找特定实例,不存在就创建](#查找特定实例不存在就创建)
  - [查找成批实例并同时返回记录数](#查找成批实例并同时返回记录数)
  - [查找所有符合条件的实例](#查找所有符合条件的实例)
  - [复合条件的示例](#复合条件的示例)
  - [分页排序分组的简单示例](#分页排序分组的简单示例)
  - [findOptions](#findoptions)
  - [原生查询](#原生查询)
  - [统计记录数](#统计记录数)
  - [最大最小](#最大最小)
  - [合计](#合计)
- [关联](#关联)
  - [关联示例](#关联示例)
  - [嵌套关联](#嵌套关联)
- [作用域](#作用域)
  - [定义](#定义)
  - [使用](#使用)
  - [作用域复用](#作用域复用)
  - [函数形式作用域](#函数形式作用域)
  - [组合作用域](#组合作用域)
  - [默认作用域与其它组合](#默认作用域与其它组合)
  - [作用域冲突](#作用域冲突)
  - [作用域与查询时条件](#作用域与查询时条件)
- [实例方法](#实例方法)
  - [新增实例不持久化](#新增实例不持久化)
  - [新增持久化实例](#新增持久化实例)
  - [更新持久化实例](#更新持久化实例)
  - [删除持久化实例](#删除持久化实例)
  - [获取简单对象](#获取简单对象)
- [批量处理方法](#批量处理方法)
  - [批量新增](#批量新增)
  - [批量更新](#批量更新)
  - [批量删除](#批量删除)
  - [指定持久化字段](#指定持久化字段)
  - [批量新增检查](#批量新增检查)
  - [刷新实例](#刷新实例)
  - [增减数量](#增减数量)
- [关联](#关联-1)
  - [关联种类](#关联种类)
  - [作用域与关联 (多态)](#作用域与关联-多态)
  - [有关实例的多对多关联方法](#有关实例的多对多关联方法)
  - [外键处理方式](#外键处理方式)
  - [新增含有关联的实例](#新增含有关联的实例)
  - [从主表新增实例](#从主表新增实例)
- [事件处理钩子](#事件处理钩子)
  - [事件顺序](#事件顺序)
  - [定义钩子](#定义钩子)

<!-- /MarkdownTOC -->

## MySQL资源

[MySQL 教程](http://www.runoob.com/mysql/mysql-tutorial.html)

[MySQL 中文版](http://wiki.jikexueyuan.com/project/mysql/select-query.html)

[MySQL函数](http://baike.baidu.com/link?url=n8YJ6B8z1tEOcqkXpYePhZH6C7BPEBsAYAmw3u3MSV85QQIVJi-dl_AVzYCMO-3NbX50bQkK43-5AhdJXOFBh_#2)

区别

[MySql与SqlServer的一些常用用法的差别](http://www.cnblogs.com/fish-li/archive/2011/04/05/2006107.html) 

[MySQL与SQL Server的一些区别浅析](http://www.jb51.net/article/54346.htm) 

[SQLServer和MySql区别详解](http://wenku.baidu.com/link?url=OjWadoySQ3gb3pV_bFEvUgTmtZL8xDXmjUjs0_oBqUWlYh3mccAWv_BqRdZBbH83LQdN-eGZOaIxfOWI-To3JkywxQiTeKOHXorlw4IMZGu)

## 在github中新建项目并克隆

```
CarShopDemoV1_mysql
```

## 复制文件

源目录：
```
CarShopDemoV1
```

目标目录：
```
CarShopDemoV1_mysql
```

## 安装新模块


```bash
cnpm install mysql --save
cnpm install sequelize --save
```

## 创建数据库

使用Navicat Premium创建数据库`carShop`

## sequelize

[Sequelize 文档](http://docs.sequelizejs.com/en/latest/)

使用前注意:

- 要手动创建数据库
- 要安装mysql模块


### 如何连接


连接语法:

```js
new Sequelize(c数据库名,c用户名,c密码,o配置对象);
```

配置对象常用属性:

```js
{
  host: '',//服务器地址
  dialect: '',//数据库类型 可选值: 'mysql'|'mariadb'|'sqlite'|'postgres'|'mssql'
  pool:{     //连接池配置
    max: 5,  
    min: 0,
    idle: 10000 
  },
  define:{
    classMethods:{ //全局模型扩展方法
      method1: function() {},
      method2: function() {}
    },
    instanceMethods:{ //全局实例扩展方法
      method3: function() {}
    }
  }
}
```

连接示例: 

```js
var Sequelize = require('sequelize');
var sequelize = new Sequelize(
  'carShop',
  'root',
  '38259343', {
    host: 'localhost',
    dialect: 'mysql'
  });
```

### 如何建立模型

[models-definition](http://docs.sequelizejs.com/en/latest/docs/models-definition/)

#### 数据类型

```js
Sequelize.STRING                      // VARCHAR(255)
Sequelize.STRING(1234)                // VARCHAR(1234)
Sequelize.STRING.BINARY               // VARCHAR BINARY
Sequelize.TEXT                        // TEXT

Sequelize.INTEGER                     // INTEGER
Sequelize.BIGINT                      // BIGINT
Sequelize.BIGINT(11)                  // BIGINT(11)

Sequelize.FLOAT                       // FLOAT
Sequelize.FLOAT(11)                   // FLOAT(11)
Sequelize.FLOAT(11, 12)               // FLOAT(11,12)

Sequelize.REAL                        // REAL        PostgreSQL only.
Sequelize.REAL(11)                    // REAL(11)    PostgreSQL only.
Sequelize.REAL(11, 12)                // REAL(11,12) PostgreSQL only.

Sequelize.DOUBLE                      // DOUBLE
Sequelize.DOUBLE(11)                  // DOUBLE(11)
Sequelize.DOUBLE(11, 12)              // DOUBLE(11,12)

Sequelize.DECIMAL                     // DECIMAL
Sequelize.DECIMAL(10, 2)              // DECIMAL(10,2)

Sequelize.DATE                        // DATETIME for mysql / sqlite, TIMESTAMP WITH TIME ZONE for postgres
Sequelize.BOOLEAN                     // TINYINT(1)

Sequelize.ENUM('value 1', 'value 2')  // An ENUM with allowed values 'value 1' and 'value 2'
Sequelize.ARRAY(Sequelize.TEXT)       // Defines an array. PostgreSQL only.

Sequelize.JSON                        // JSON column. PostgreSQL only.
Sequelize.JSONB                       // JSONB column. PostgreSQL only.

Sequelize.BLOB                        // BLOB (bytea for PostgreSQL)
Sequelize.BLOB('tiny')                // TINYBLOB (bytea for PostgreSQL. Other options are medium and long)

Sequelize.UUID                        // UUID datatype for PostgreSQL and SQLite, CHAR(36) BINARY for MySQL (use defaultValue: Sequelize.UUIDV1 or Sequelize.UUIDV4 to make sequelize generate the ids automatically)

Sequelize.RANGE(Sequelize.INTEGER)    // Defines int4range range. PostgreSQL only.
Sequelize.RANGE(Sequelize.BIGINT)     // Defined int8range range. PostgreSQL only.
Sequelize.RANGE(Sequelize.DATE)       // Defines tstzrange range. PostgreSQL only.
Sequelize.RANGE(Sequelize.DATEONLY)   // Defines daterange range. PostgreSQL only.
Sequelize.RANGE(Sequelize.DECIMAL)    // Defines numrange range. PostgreSQL only.

Sequelize.ARRAY(Sequelize.RANGE(Sequelize.DATE)) // Defines array of tstzrange ranges. PostgreSQL only.

```

注意: 有很多类型不适用于 mysql

#### 定义模型的语法

```js
sequelize.define(c模型名,o字段配置对象,[o模型设置对象]);
```

这里的 `sequelize` 是一个连接实例,即 `sequelize =  new Sequelize`

#### 字段配置对象

语法形式1:

```js
{
  字段名1: 字段类型,
  字段名2: 字段类型,
}
```

语法形式2

```js
{
  字段名1:{
    allowNull: Boolean,  //允许为null,=true
    defaultValue: value|Function,//=null
    unique: String | Boolean, //唯一,=false
    primaryKey: Boolean,//=false
    field: String, //映射字段名,=null,属性名与字段名不同名时用
    autoIncrement: Boolean,//=false
    comment: String, //=null
    references: String | Model, //关联模型 或 表 (外键表)
    onUpdate:String,//外键修改时处理 可选值: CASCADE(级联), RESTRICT(禁止), SET DEFAULT, SET NULL or NO ACTION
    onDelete:String,//同上
    get:Function, //自定义读值方法
    set:Function, //自定义写值方法
    validate: {}, //字段校验对象
  }
}
```
日期字段缺省值:

```js
 // default values for dates => current time
 myDate: { type: Sequelize.DATE, defaultValue: Sequelize.NOW },
```

多字段组合唯一示例:
```js
 // Creating two objects with the same value will throw an error. The unique property can be either a
 // boolean, or a string. If you provide the same string for multiple columns, they will form a
 // composite unique key.
 someUnique: {type: Sequelize.STRING, unique: true},
 uniqueOne: { type: Sequelize.STRING,  unique: 'compositeIndex'},
 uniqueTwo: { type: Sequelize.INTEGER, unique: 'compositeIndex'}
```

唯一不同设置方法:
```js
 // The unique property is simply a shorthand to create a unique index.
 someUnique: {type: Sequelize.STRING, unique: true}
 // It's exactly the same as creating the index in the model's options.
 {someUnique: {type: Sequelize.STRING}},
 {indexes: [{unique: true, fields: ['someUnique']}]}
```

主键:

```js
 // Go on reading for further information about primary keys
 identifier: { type: Sequelize.STRING, primaryKey: true},
```

自增长:

```js
 // autoIncrement can be used to create auto_incrementing integer columns
 incrementMe: { type: Sequelize.INTEGER, autoIncrement: true },
```

注释:

```js
 // Comments can be specified for each field for MySQL and PG
 hasComment: { type: Sequelize.INTEGER, comment: "I'm a comment!" },
```

字段名与属性不同名:
```js
 // You can specify a custom field name via the "field" attribute:
 fieldWithUnderscores: { type: Sequelize.STRING, field: "field_with_underscores" },
```

外键:
```js
 // It is possible to create foreign keys:
 bar_id: {
   type: Sequelize.INTEGER,

   references: {
     // This is a reference to another model
     model: Bar,

     // This is the column name of the referenced model
     key: 'id',
   }
 }
})
```

自定义读写值方法的示例:

```js

var Employee = sequelize.define('Employee', {
  name:  {
    type     : Sequelize.STRING,
    allowNull: false,
    get      : function()  {
      var title = this.getDataValue('title');
      // 'this' allows you to access attributes of the instance
      return this.getDataValue('name') + ' (' + title + ')';
    },
  },
  title: {
    type     : Sequelize.STRING,
    allowNull: false,
    set      : function(val) {
      this.setDataValue('title', val.toUpperCase());
    }
  }
});

Employee
  .create({ name: 'John Doe', title: 'senior engineer' })
  .then(function(employee) {
    console.log(employee.get('name')); // John Doe (SENIOR ENGINEER)
    console.log(employee.get('title')); // SENIOR ENGINEER
  })
```



字段属性的原始说明:

```js

[attributes.column.allowNull=true]  Boolean   If false, the column will have a NOT NULL constraint, and a not null validation will be run before an instance is saved.
[attributes.column.defaultValue=null]   Any   A literal default value, a javascript function, or an SQL function (see sequelize.fn)
[attributes.column.unique=false]  String | Boolean  If true, the column will get a unique constraint. If a string is provided, the column will be part of a composite unique index. If multiple columns have the same string, they will be part of the same unique index
[attributes.column.primaryKey=false]  Boolean   
[attributes.column.field=null]  String  If set, sequelize will map the attribute name to a different name in the database
[attributes.column.autoIncrement=false]   Boolean   
[attributes.column.comment=null]  String  
[attributes.column.references]  String | Model  If this column references another table, provide it here as a Model, or a string
[attributes.column.referencesKey='id']  String  The column of the foreign table that this column references
[attributes.column.onUpdate]  String  What should happen when the referenced key is updated. One of CASCADE, RESTRICT, SET DEFAULT, SET NULL or NO ACTION
[attributes.column.onDelete]  String  What should happen when the referenced key is deleted. One of CASCADE, RESTRICT, SET DEFAULT, SET NULL or NO ACTION
[attributes.column.get]   Function  Provide a custom getter for this column. Use this.getDataValue(String) to manipulate the underlying values.
[attributes.column.set]   Function  Provide a custom setter for this column. Use this.setDataValue(String, Value) to manipulate the underlying values.
```

[字段属性的原始说明](http://www.nodeclass.com/api/sequelize.html#definemodelname-attributes-options-model)

#### 字段校验对象

验证时机:  create update save
手动调用验证: call validate()


```js
validate: {
      is: ["^[a-z]+$",'i'],     // will only allow letters
      is: /^[a-z]+$/i,          // same as the previous example using real RegExp
      not: ["[a-z]",'i'],       // will not allow letters
      isEmail: true,            // checks for email format (foo@bar.com)
      isUrl: true,              // checks for url format (http://foo.com)
      isIP: true,               // checks for IPv4 (129.89.23.1) or IPv6 format
      isIPv4: true,             // checks for IPv4 (129.89.23.1)
      isIPv6: true,             // checks for IPv6 format
      isAlpha: true,            // will only allow letters
      isAlphanumeric: true      // will only allow alphanumeric characters, so "_abc" will fail
      isNumeric: true           // will only allow numbers
      isInt: true,              // checks for valid integers
      isFloat: true,            // checks for valid floating point numbers
      isDecimal: true,          // checks for any numbers
      isLowercase: true,        // checks for lowercase
      isUppercase: true,        // checks for uppercase
      notNull: true,            // won't allow null
      isNull: true,             // only allows null
      notEmpty: true,           // don't allow empty strings
      equals: 'specific value', // only allow a specific value
      contains: 'foo',          // force specific substrings
      notIn: [['foo', 'bar']],  // check the value is not one of these
      isIn: [['foo', 'bar']],   // check the value is one of these
      notContains: 'bar',       // don't allow specific substrings
      len: [2,10],              // only allow values with length between 2 and 10
      isUUID: 4,                // only allow uuids
      isDate: true,             // only allow date strings
      isAfter: "2011-11-05",    // only allow date strings after a specific date
      isBefore: "2011-11-05",   // only allow date strings before a specific date
      max: 23,                  // only allow values
      min: 23,                  // only allow values >= 23
      isArray: true,            // only allow arrays
      isCreditCard: true,       // check for valid credit card numbers

      // custom validations are also possible:
      isEven: function(value) {
        if(parseInt(value) % 2 != 0) {
          throw new Error('Only even values are allowed!')
        // we also are in the model's context here, so this.otherField
        // would get the value of otherField if it existed
        }
      }

```

allowNull: 如果字段设置allowNull:true,则当字段值为null时,不会检查.


如何自定义错误信息:

- 不需要参数的验证器

```js
isInt: {
  msg: "Must be an integer number of pennies"
}
```

- 需要参数的验证器

```js
isIn: {
  args: [['en', 'zh']],
  msg: "Must be English or Chinese"
}
```


#### 模型设置对象

常用配置说明:

```js

{
  getterMethods: { //自定义附加属性读取方法对象
    附加属性名1: function(){}, // 
  }, 
  setterMethods: { //自定义附加属性赋值方法对象
    附加属性名1: function(value){}, // 
  },
  validate:{ //模型校验方法对象,在字段校验之后执行检查
    检查方法1: function(){}, //
  },
  timestamps: Boolean, //=true,是否自动创建实例时间跟踪字段
  paranoid: Boolean, //=false,不实际删除
  underscored: Boolean, //=false,驼峰属性名转成下划线分隔
  underscoredAll: Boolean,//=false,驼峰模型名转成下划线分隔
  freezeTableName: Boolean, //=false,不自动使用模型名的复数形式做表名
  tableName: String, //自定义表名,freezeTableName=true时有效
  createdAt: String | Boolean, //是否自动创建createdAt
  updatedAt: String | Boolean, //
  deletedAt: String | Boolean, //
  comment: String, //模型(表)注释
  classMethods: { //模型自定义扩展方法
    方法名1: function(){}
  },
  instanceMethods: { //实例自定义扩展方法
    方法名1: function(){}
  },
  indexes:[
    {
      fields:[
        String | Object{
          attribute:
          length:
          order: 
          collate:
        },
      ]
      name: String, //可选,自动创建时,用model name + _ + fields
      type: String, //可选,仅用于mysql, 可选值: UNIQUE FULLTEXT SPATIAL
      unique: Boolean, //=false
    },
  ]
}

```


timestamps:

默认会自动创建 `createdAt` 和 `updatedAt` 两个日期型字段 

```js
var Foo = sequelize.define('Foo',  { /* bla */ }, {
  // don't forget to enable timestamps!
  timestamps: true,

  // I don't want createdAt
  createdAt: false,

  // I want updatedAt to actually be called updateTimestamp
  updatedAt: 'updateTimestamp'

  // And deletedAt to be called destroyTime (remember to enable paranoid for this to work)
  deletedAt: 'destroyTime',
  paranoid: true
})
```

paranoid:

```
Calling destroy will not delete the model, but instead set a deletedAt timestamp if this is true. Needs timestamps=true to work
```



自定义附加属性(类似于计算字段)的示例:


```js
var Foo = sequelize.define('Foo', {
  firstname: Sequelize.STRING,
  lastname: Sequelize.STRING
}, {
  getterMethods   : {
    fullName       : function()  { return this.firstname + ' ' + this.lastname }
  },

  setterMethods   : {
    fullName       : function(value) {
        var names = value.split(' ');

        this.setDataValue('firstname', names.slice(0, -1).join(' '));
        this.setDataValue('lastname', names.slice(-1).join(' '));
    },
  }
});
```

模型校验方法示例:

用于多个字段的组合检查

```js

var Pub = Sequelize.define('Pub', {
  name: { type: Sequelize.STRING },
  address: { type: Sequelize.STRING },
  latitude: {
    type: Sequelize.INTEGER,
    allowNull: true,
    defaultValue: null,
    validate: { min: -90, max: 90 }
  },
  longitude: {
    type: Sequelize.INTEGER,
    allowNull: true,
    defaultValue: null,
    validate: { min: -180, max: 180 }
  },
}, {
  validate: {
    bothCoordsOrNone: function() {
      if ((this.latitude === null) !== (this.longitude === null)) {
        throw new Error('Require either both latitude and longitude or neither')
      }
    }
  }
})
```

扩展方法示例:

```
var Foo = sequelize.define('Foo', { /* attributes */}, {
  classMethods: {
    method1: function(){ return 'smth' }
  },
  instanceMethods: {
    method2: function() { return 'foo' }
  }
})

// Example:
Foo.method1()
Foo.build().method2()
```

```js
var User = sequelize.define('User', { firstname: Sequelize.STRING, lastname: Sequelize.STRING }, {
  instanceMethods: {
    getFullname: function() {
      return [this.firstname, this.lastname].join(' ')
    }
  }
})

// Example:
User.build({ firstname: 'foo', lastname: 'bar' }).getFullname() // 'foo bar'
```


模型设置对象的原始说明:

```js

[options]   Object  These options are merged with the default define options provided to the Sequelize constructor
[options.defaultScope]  Object  Define the default search scope to use for this model. Scopes have the same form as the options passed to find / findAll
[options.scopes]  Object  More scopes, defined in the same way as defaultScope above. See Model.scope for more information about how scopes are defined, and what you can do with them
[options.omitNull]  Boolean   Don''t persits null values. This means that all columns with null values will not be saved
[options.timestamps=true]   Boolean   Adds createdAt and updatedAt timestamps to the model.
[options.paranoid=false]  Boolean   Calling destroy will not delete the model, but instead set a deletedAt timestamp if this is true. Needs timestamps=true to work
[options.underscored=false]   Boolean   Converts all camelCased columns to underscored if true
[options.underscoredAll=false]  Boolean   Converts camelCased model names to underscored tablenames if true
[options.freezeTableName=false]   Boolean   If freezeTableName is true, sequelize will not try to alter the DAO name to get the table name. Otherwise, the dao name will be pluralized
[options.name]  Object  An object with two attributes, singular and plural, which are used when this model is associated to others.
[options.name.singular=inflection.singularize(modelName)]   String  
[options.name.plural=inflection.pluralize(modelName)]   String  
[options.indexes]   Array<Object>   
[options.indexes[].name]  String  The name of the index. Defaults to model name + _ + fields concatenated
[options.indexes[].type]  String  Index type. Only used by mysql. One of UNIQUE, FULLTEXT and SPATIAL
[options.indexes[].method]  String  The method to create the index by (USING statement in SQL). BTREE and HASH are supported by mysql and postgres, and postgres additionally supports GIST and GIN.
[options.indexes[].unique=false]  Boolean   Should the index by unique? Can also be triggered by setting type to UNIQUE
[options.indexes[].concurrently=false]  Boolean   PostgreSQL will build the index without taking any write locks. Postgres only
[options.indexes[].fields]  Array<String | Object>  An array of the fields to index. Each field can either be a string containing the name of the field, or an object with the following attributes: attribute (field name), length (create a prefix index of length chars), order (the direction the column should be sorted in), collate (the collation (sort order) for the column)
[options.createdAt]   String | Boolean  Override the name of the createdAt column if a string is provided, or disable it if false. Timestamps must be true
[options.updatedAt]   String | Boolean  Override the name of the updatedAt column if a string is provided, or disable it if false. Timestamps must be true
[options.deletedAt]   String | Boolean  Override the name of the deletedAt column if a string is provided, or disable it if false. Timestamps must be true
[options.tableName]   String  Defaults to pluralized model name, unless freezeTableName is true, in which case it uses model name verbatim
[options.getterMethods]   Object  Provide getter functions that work like those defined per column. If you provide a getter method with the same name as a column, it will be used to access the value of that column. If you provide a name that does not match a column, this function will act as a virtual getter, that can fetch multiple other values
[options.setterMethods]   Object  Provide setter functions that work like those defined per column. If you provide a setter method with the same name as a column, it will be used to update the value of that column. If you provide a name that does not match a column, this function will act as a virtual setter, that can act on and set other values, but will not be persisted
[options.instanceMethods]   Object  Provide functions that are added to each instance (DAO). If you override methods provided by sequelize, you can access the original method using this.constructor.super_.prototype, e.g. this.constructor.super_.prototype.toJSON.apply(this, arguments)
[options.classMethods]  Object  Provide functions that are added to the model (Model). If you override methods provided by sequelize, you can access the original method using this.constructor.prototype, e.g. this.constructor.prototype.find.apply(this, arguments)
[options.schema='public']   String  
[options.engine]  String  
[options.charset]   String  
[options.comment]   String  
[options.collate]   String  
[options.hooks]   Object  An object of hook function that are called before and after certain lifecycle events. The possible hooks are: beforeValidate, afterValidate, beforeBulkCreate, beforeBulkDestroy, beforeBulkUpdate, beforeCreate, beforeDestroy, beforeUpdate, afterCreate, afterDestroy, afterUpdate, afterBulkCreate, afterBulkDestory and afterBulkUpdate. See Hooks for more information about hook functions and their signatures. Each property can either be a function, or an array of functions.
[options.validate]  Object  An object of model wide validations. Validations have access to all model values via this. If the validator function takes an argument, it is asumed to be async, and is called with a callback that accepts an optional error.

```

### 如何书写模型文件和引用模型

#### 官方推荐模型文件写法

```js
// The model definition is done in /path/to/models/project.js
// As you might notice, the DataTypes are the very same as explained above
module.exports = function(sequelize, DataTypes) {
  return sequelize.define("Project", {
    name: DataTypes.STRING,
    description: DataTypes.TEXT
  })
}
```

#### 官方推荐创建模型写法
```js
// in your server file - e.g. app.js
var Project = sequelize.import(__dirname + "/path/to/models/project")
```

注意: 官方推荐并没有提供缓存处理的代码,要自己书写.

## 查询语法

### 查询参数对象

#### 选择字段Attributes VS select 子句

`Attributes:[]` : 可以指定查询结果的字段列表

##### 普通选择

```js
Model.findAll({
  attributes: ['foo', 'bar']
});
```
相当于

```sql
SELECT foo, bar ...
```

##### 指定别名
```js
Model.findAll({
  attributes: ['foo', ['bar', 'baz']]
});

```
相当于
```sql
SELECT foo, bar AS baz ...
```
##### 使用聚合函数

```js
Model.findAll({
  attributes: [[sequelize.fn('COUNT', sequelize.col('hats')), 'no_hats']]
});
```
相当于
```sql
SELECT COUNT(hats) AS no_hats ...
```
##### 显式指定字段
```js
Model.findAll({
  attributes: ['id', 'foo', 'bar', 'baz', 'quz', [sequelize.fn('COUNT', sequelize.col('hats')), 'no_hats']]
});
```
相当于
```sql
SELECT id, foo, bar, baz, quz, COUNT(hats) AS no_hats ...
```

##### 隐式指定字段
```js
Model.findAll({
  attributes: { include: [[sequelize.fn('COUNT', sequelize.col('hats')), 'no_hats']] }
});
```
相当于
```sql
SELECT id, foo, bar, baz, quz, COUNT(hats) AS no_hats ...
```

##### 附加字段
```js
Model.findAll({
  attributes: { include: [[sequelize.fn('COUNT', sequelize.col('hats')), 'no_hats']] }
});
```
相当于
```sql
SELECT id, foo, bar, baz, quz, COUNT(hats) AS no_hats ...
```

##### 排除字段
```js
Model.findAll({
  attributes: { exclude: ['baz'] }
});
```
相当于
```sql
SELECT id, foo, bar, quz ...
```

#### 条件对象where VS where 子句

`where:{}` : 条件对象 

##### 一般条件

```js
Post.findAll({
  where: {
    authorId: 2
  }
});

```
相当于
```sql
SELECT * FROM post WHERE authorId = 2
```

```js
Post.findAll({
  where: {
    authorId: 12,
    status: 'active'
  }
});

```
相当于
```sql
SELECT * FROM post WHERE authorId = 12 AND status = 'active';
```

##### 运算符

```js
$and: {a: 5}           // AND (a = 5)
$or: [{a: 5}, {a: 6}]  // (a = 5 OR a = 6)
$gt: 6,                // > 6
$gte: 6,               // >= 6
$lt: 10,               // < 10
$lte: 10,              // <= 10
$ne: 20,               // != 20
$between: [6, 10],     // BETWEEN 6 AND 10
$notBetween: [11, 15], // NOT BETWEEN 11 AND 15
$in: [1, 2],           // IN [1, 2]
$notIn: [1, 2],        // NOT IN [1, 2]
$like: '%hat',         // LIKE '%hat'
$notLike: '%hat'       // NOT LIKE '%hat'
$like: { $any: ['cat', 'hat']}
                       // LIKE ANY ARRAY['cat', 'hat'] - also works for iLike and notLike
$col: 'user.organization_id' // = "user"."organization_id", with dialect specific column identifiers, PG in this example
                        
```

##### 运算符组合示例

```js
{
  rank: {
    $or: {
      $lt: 1000,
      $eq: null
    }
  }
}
```
相当于
```sql
rank < 1000 OR rank IS NULL
```


```js
{
  createdAt: {
    $lt: new Date(),
    $gt: new Date(new Date() - 24 * 60 * 60 * 1000)
  }
}
```
相当于
```sql
 createdAt < [timestamp] AND createdAt > [timestamp]
```

```js
{
  $or: [
    {
      title: {
        $like: 'Boat%'
      }
    },
    {
      description: {
        $like: '%boat%'
      }
    }
  ]
}

```
相当于
```sql
title LIKE 'Boat%' OR description LIKE '%boat%'
```

#### 分页

`limit: int` : 返回记录数 
`offset: int` : 跳过记录数

```js
// Fetch 10 instances/rows
Project.findAll({ limit: 10 })

// Skip 8 instances/rows
Project.findAll({ offset: 8 })

// Skip 5 instances and fetch the 5 after that
Project.findAll({ offset: 5, limit: 5 })

```

#### 排序

```js
something.findOne({
  order: [
    // Will escape username and validate DESC against a list of valid direction parameters
    ['username', 'DESC'],

    // Will order by max(age)
    sequelize.fn('max', sequelize.col('age')),

    // Will order by max(age) DESC
    [sequelize.fn('max', sequelize.col('age')), 'DESC'],

    // Will order by  otherfunction(`col1`, 12, 'lalala') DESC
    [sequelize.fn('otherfunction', sequelize.col('col1'), 12, 'lalala'), 'DESC'],

    // Will order by name on an associated User
    [User, 'name', 'DESC'],

  ]
  // All the following statements will be treated literally so should be treated with care
  order: 'convert(user_name using gbk)'
  order: 'username DESC'
  order: sequelize.literal('convert(user_name using gbk)')
})

```

## 关于promise


[promise的行为模式](http://www.ituring.com.cn/article/54547)

```
Promise.then(onFulFill, onReject)
```

then(成功/覆行时的回调,失败/拒绝时的回调)

- 在回调中，可以选择返回 普通值或对象，也可以继续返回另外一个Promise。
- then()返回的也是一个Promise，可以继续执行then()
- 上一个then()定义的回调返回的 普通值或对象，则返回值将做为下一个then()定义的回调函数的参数。
- 如果上一个then()定义的回调返回的是 另外一个Promise，则要等这个被返回的Promise执行结束，并将执行结果做为参数传到下一个then()定义的回调函数
- 如果Promise执行时出现错误，则会执行 then()链条中定义了onReject回调的方法。

### bluebird

[bluebird](http://bluebirdjs.com/docs/api-reference.html)







## 模型常用方法

### 查找某个特定实例

```js
// search for known ids
Project.findById(123).then(function(project) {
  // project will be an instance of Project and stores the content of the table entry
  // with id 123. if such an entry is not defined you will get null
})

// search for attributes
Project.findOne({ where: {title: 'aProject'} }).then(function(project) {
  // project will be the first entry of the Projects table with the title 'aProject' || null
})


Project.findOne({
  where: {title: 'aProject'},
  attributes: ['id', ['name', 'title']]
}).then(function(project) {
  // project will be the first entry of the Projects table with the title 'aProject' || null
  // project.title will contain the name of the project
})

```

### 查找特定实例,不存在就创建

以下示例创建了一笔新记录:

```js
User
  .findOrCreate({
    where: {username: 'sdepold'}, 
    defaults: {job: 'Technical Lead JavaScript'}
    })
  .spread(function(user, created) {
    console.log(user.get({
      plain: true
    }))
    console.log(created)
  })
```
返回结果:
```js
      {
        username: 'sdepold',
        job: 'Technical Lead JavaScript',
        id: 1,
        createdAt: Fri Mar 22 2013 21: 28: 34 GMT + 0100(CET),
        updatedAt: Fri Mar 22 2013 21: 28: 34 GMT + 0100(CET)
      }
      created: true
```

- [findOrCreate](http://docs.sequelizejs.com/en/latest/api/model/#findorcreateoptions-promiseinstance-created)

`findOrCreate(options) -> Promise.<Instance, created>`

注意:文档对返回值说明不够明确,返回的是数组:[Instance, created]
因此要用 spread 

[.spread](http://bluebirdjs.com/docs/api/spread.html)

spread: 将数组伸展成参数

以下示例findOrCreate没有创建记录


```js
User
  .create({ username: 'fnord', job: 'omnomnom' })
  .then(function() {
    User
      .findOrCreate({where: {username: 'fnord'}, defaults: {job: 'something else'}})
      .spread(function(user, created) {
        console.log(user.get({
          plain: true
        }))
        console.log(created)
      })
  })
```
返回结果: (job没变)
```js
          {
            username: 'fnord',
            job: 'omnomnom',
            id: 2,
            createdAt: Fri Mar 22 2013 21: 28: 34 GMT + 0100(CET),
            updatedAt: Fri Mar 22 2013 21: 28: 34 GMT + 0100(CET)
          }
          created: false
```
### 查找成批实例并同时返回记录数

记录数只按where条件统计,忽略 `limit` 和 `offset` 

```js
Project
  .findAndCountAll({
     where: {
        title: {
          $like: 'foo%'
        }
     },
     offset: 10,
     limit: 2
  })
  .then(function(result) {
    console.log(result.count);
    console.log(result.rows);
  });
```

-[findAndCountAll](http://docs.sequelizejs.com/en/latest/api/model/#findandcountfindoptions-promiseobject)

`findAndCount(findOptions) -> Promise.<Object>`

注意: 文档中的函数名为findAndCount, 也等于findAndCountAll

`Model.prototype.findAndCountAll = Model.prototype.findAndCount;`

### 查找所有符合条件的实例

```js
// find multiple entries
Project.findAll().then(function(projects) {
  // projects will be an array of all Project instances
})

// also possible:
Project.all().then(function(projects) {
  // projects will be an array of all Project instances
})

// search for specific attributes - hash usage
Project.findAll({ where: { name: 'A Project' } }).then(function(projects) {
  // projects will be an array of Project instances with the specified name
})

// search with string replacements
Project.findAll({ where: ["id > ?", 25] }).then(function(projects) {
  // projects will be an array of Projects having a greater id than 25
})

// search within a specific range
Project.findAll({ where: { id: [1,2,3] } }).then(function(projects) {
  // projects will be an array of Projects having the id 1, 2 or 3
  // this is actually doing an IN query
})

Project.findAll({
  where: {
    id: {
      $and: {a: 5}           // AND (a = 5)
      $or: [{a: 5}, {a: 6}]  // (a = 5 OR a = 6)
      $gt: 6,                // id > 6
      $gte: 6,               // id >= 6
      $lt: 10,               // id < 10
      $lte: 10,              // id <= 10
      $ne: 20,               // id != 20
      $between: [6, 10],     // BETWEEN 6 AND 10
      $notBetween: [11, 15], // NOT BETWEEN 11 AND 15
      $in: [1, 2],           // IN [1, 2]
      $notIn: [1, 2],        // NOT IN [1, 2]
      $like: '%hat',         // LIKE '%hat'
      $notLike: '%hat'       // NOT LIKE '%hat'
      $iLike: '%hat'         // ILIKE '%hat' (case insensitive)  (PG only)
      $notILike: '%hat'      // NOT ILIKE '%hat'  (PG only)
      $overlap: [1, 2]       // && [1, 2] (PG array overlap operator)
      $contains: [1, 2]      // @> [1, 2] (PG array contains operator)
      $contained: [1, 2]     // <@ [1, 2] (PG array contained by operator)
      $any: [2,3]            // ANY ARRAY[2, 3]::INTEGER (PG only)
    },
    status: {
      $not: false,           // status NOT FALSE
    }
  }
})
```

### 复合条件的示例 


```js
Project.findOne({
  where: {
    name: 'a project',
    $or: [
      { id: [1,2,3] },
      { id: { $gt: 10 } }
    ]
  }
})

Project.findOne({
  where: {
    name: 'a project',
    id: {
      $or: [
        [1,2,3],
        { $gt: 10 }
      ]
    }
  }
})
```
相当于:
```sql
SELECT *
FROM `Projects`
WHERE (
  `Projects`.`name` = 'a project'
   AND (`Projects`.`id` IN (1,2,3) OR `Projects`.`id` > 10)
)
LIMIT 1;
```


```js
Project.findOne({
  where: {
    name: 'a project',
    $not: [
      { id: [1,2,3] },
      { array: { $contains: [3,4,5] } }
    ]
  }
});
```
相当于:
```sql
SELECT *
FROM `Projects`
WHERE (
  `Projects`.`name` = 'a project'
   AND NOT (`Projects`.`id` IN (1,2,3) OR `Projects`.`array` @> ARRAY[1,2,3]::INTEGER[])
)
LIMIT 1;

```

### 分页排序分组的简单示例

```js
// limit the results of the query
Project.findAll({ limit: 10 })

// step over the first 10 elements
Project.findAll({ offset: 10 })

// step over the first 10 elements, and take 2
Project.findAll({ offset: 10, limit: 2 })
```


```js
Project.findAll({order: 'title DESC'})
// yields ORDER BY title DESC

Project.findAll({group: 'name'})
// yields GROUP BY name
```


```js
something.findOne({
  order: [
    'name',
    // will return `name`
    'username DESC',
    // will return `username DESC` -- i.e. don't do it!
    ['username', 'DESC'],
    // will return `username` DESC
    sequelize.fn('max', sequelize.col('age')),
    // will return max(`age`)
    [sequelize.fn('max', sequelize.col('age')), 'DESC'],
    // will return max(`age`) DESC
    [sequelize.fn('otherfunction', sequelize.col('col1'), 12, 'lalala'), 'DESC'],
    // will return otherfunction(`col1`, 12, 'lalala') DESC
    [sequelize.fn('otherfunction', sequelize.fn('awesomefunction', sequelize.col('col'))), 'DESC']
    // will return otherfunction(awesomefunction(`col`)) DESC, This nesting is potentially infinite!
    [{ raw: 'otherfunction(awesomefunction(`col`))' }, 'DESC']
    // This won't be quoted, but direction will be added
  ]
})

```

> To recap, the elements of the order/group array can be the following:
>
    String - will be quoted
    Array - first element will be quoted, second will be appended verbatim
    Object -
    
>>    Raw will be added verbatim without quoting
>>    
>>    Everything else is ignored, and if raw is not set, the query will fail
>>    
>>    Sequelize.fn and Sequelize.col returns functions and quoted cools





### findOptions

```
* The success listener is called with an array of instances if the query succeeds.
 *
 * @param  {Object}                    [options] A hash of options to describe the scope of the search
 * @param  {Object}                    [options.where] A hash of attributes to describe your search. See above for examples.
 * @param  {Array<String>|Object}      [options.attributes] A list of the attributes that you want to select, or an object with `include` and `exclude` keys. To rename an attribute, you can pass an array, with two elements - the first is the name of the attribute in the DB (or some kind of expression such as `Sequelize.literal`, `Sequelize.fn` and so on), and the second is the name you want the attribute to have in the returned instance
 * @param  {Array<String>}             [options.attributes.include] Select all the attributes of the model, plus some additional ones. Useful for aggregations, e.g. `{ attributes: { include: [[sequelize.fn('COUNT', sequelize.col('id')), 'total)]] }`
 * @param  {Array<String>}             [options.attributes.exclude] Select all the attributes of the model, except some few. Useful for security purposes e.g. `{ attributes: { exclude: ['password'] } }`
 * @param  {Boolean}                   [options.paranoid=true] If true, only non-deleted records will be returned. If false, both deleted and non-deleted records will be returned. Only applies if `options.paranoid` is true for the model.
 * @param  {Array<Object|Model>}       [options.include] A list of associations to eagerly load using a left join. Supported is either `{ include: [ Model1, Model2, ...]}` or `{ include: [{ model: Model1, as: 'Alias' }]}`. If your association are set up with an `as` (eg. `X.hasMany(Y, { as: 'Z }`, you need to specify Z in the as attribute when eager loading Y).
 * @param  {Model}                     [options.include[].model] The model you want to eagerly load
 * @param  {String}                    [options.include[].as] The alias of the relation, in case the model you want to eagerly load is aliassed. For `hasOne` / `belongsTo`, this should be the singular name, and for `hasMany`, it should be the plural
 * @param  {Association}               [options.include[].association] The association you want to eagerly load. (This can be used instead of providing a model/as pair)
 * @param  {Object}                    [options.include[].where] Where clauses to apply to the child models. Note that this converts the eager load to an inner join, unless you explicitly set `required: false`
 * @param  {Array<String>}             [options.include[].attributes] A list of attributes to select from the child model
 * @param  {Boolean}                   [options.include[].required] If true, converts to an inner join, which means that the parent model will only be loaded if it has any matching children. True if `include.where` is set, false otherwise.
 * @param  {Boolean}                   [options.include[].separate] If true, runs a separate query to fetch the associated instances, only supported for hasMany associations
 * @param  {Number}                    [options.include[].limit] Limit the joined rows, only supported with include.separate=true
 * @param  {Object}                    [options.include[].through.where] Filter on the join model for belongsToMany relations
 * @param  {Array}                     [options.include[].through.attributes] A list of attributes to select from the join model for belongsToMany relations
 * @param  {Array<Object|Model>}       [options.include[].include] Load further nested related models
 * @param  {String|Array|Sequelize.fn} [options.order] Specifies an ordering. If a string is provided, it will be escaped. Using an array, you can provide several columns / functions to order by. Each element can be further wrapped in a two-element array. The first element is the column / function to order by, the second is the direction. For example: `order: [['name', 'DESC']]`. In this way the column will be escaped, but the direction will not.
 * @param  {Number}                    [options.limit]
 * @param  {Number}                    [options.offset]
 * @param  {Transaction}               [options.transaction] Transaction to run query under
 * @param  {String|Object}             [options.lock] Lock the selected rows. Possible options are transaction.LOCK.UPDATE and transaction.LOCK.SHARE. Postgres also supports transaction.LOCK.KEY_SHARE, transaction.LOCK.NO_KEY_UPDATE and specific model locks with joins. See [transaction.LOCK for an example](transaction#lock)
 * @param  {Boolean}                   [options.raw] Return raw result. See sequelize.query for more information.
 * @param  {Function}                  [options.logging=false] A function that gets executed while running the query to log the sql.
 * @param  {Object}                    [options.having]
 * @param  {String}                    [options.searchPath=DEFAULT] An optional parameter to specify the schema search_path (Postgres only)
 *
```

### 原生查询

仅可用于大数据量只显示,不维护的情况

```js
Project.findAll({ where: { ... }, raw: true })
```

### 统计记录数

```js
Project.count().then(function(c) {
  console.log("There are " + c + " projects!")
})

Project.count({ where: ["id > ?", 25] }).then(function(c) {
  console.log("There are " + c + " projects with an id greater than 25.")
})
```

### 最大最小
```js
/*
  Let's assume 3 person objects with an attribute age.
  The first one is 10 years old,
  the second one is 5 years old,
  the third one is 40 years old.
*/
Project.max('age').then(function(max) {
  // this will return 40
})

Project.max('age', { where: { age: { lt: 20 } } }).then(function(max) {
  // will be 10
})
```


```js
/*
  Let's assume 3 person objects with an attribute age.
  The first one is 10 years old,
  the second one is 5 years old,
  the third one is 40 years old.
*/
Project.min('age').then(function(min) {
  // this will return 5
})

Project.min('age', { where: { age: { $gt: 5 } } }).then(function(min) {
  // will be 10
})

```

### 合计

```js
/*
  Let's assume 3 person objects with an attribute age.
  The first one is 10 years old,
  the second one is 5 years old,
  the third one is 40 years old.
*/
Project.sum('age').then(function(sum) {
  // this will return 55
})

Project.sum('age', { where: { age: { $gt: 5 } } }).then(function(sum) {
  // will be 50
})
```
## 关联

### 关联示例

#### 普通关联
```js
var User = sequelize.define('User', { name: Sequelize.STRING })
  , Task = sequelize.define('Task', { name: Sequelize.STRING })
  , Tool = sequelize.define('Tool', { name: Sequelize.STRING })

Task.belongsTo(User)
User.hasMany(Task)
User.hasMany(Tool, { as: 'Instruments' })

sequelize.sync().then(function() {
  // this is where we continue ...
})
```

```js
Task.findAll({ include: [ User ] }).then(function(tasks) {
  console.log(JSON.stringify(tasks))

  /*
    [{
      "name": "A Task",
      "id": 1,
      "createdAt": "2013-03-20T20:31:40.000Z",
      "updatedAt": "2013-03-20T20:31:40.000Z",
      "UserId": 1,
      "User": {
        "name": "John Doe",
        "id": 1,
        "createdAt": "2013-03-20T20:31:45.000Z",
        "updatedAt": "2013-03-20T20:31:45.000Z"
      }
    }]
  */
})
```

```js
User.findAll({ include: [ Task ] }).then(function(users) {
  console.log(JSON.stringify(users))

  /*
    [{
      "name": "John Doe",
      "id": 1,
      "createdAt": "2013-03-20T20:31:45.000Z",
      "updatedAt": "2013-03-20T20:31:45.000Z",
      "Tasks": [{
        "name": "A Task",
        "id": 1,
        "createdAt": "2013-03-20T20:31:40.000Z",
        "updatedAt": "2013-03-20T20:31:40.000Z",
        "UserId": 1
      }]
    }]
  */
})

```


```js
User.findAll({ include: [{ model: Tool, as: 'Instruments' }] }).then(function(users) {
  console.log(JSON.stringify(users))

  /*
    [{
      "name": "John Doe",
      "id": 1,
      "createdAt": "2013-03-20T20:31:45.000Z",
      "updatedAt": "2013-03-20T20:31:45.000Z",
      "Instruments": [{
        "name": "Toothpick",
        "id": 1,
        "createdAt": null,
        "updatedAt": null,
        "UserId": 1
      }]
    }]
  */
})
```

```js
User.findAll({
    include: [{
        model: Tool,
        as: 'Instruments',
        where: { name: { $like: '%ooth%' } }
    }]
}).then(function(users) {
    console.log(JSON.stringify(users))

    /*
      [{
        "name": "John Doe",
        "id": 1,
        "createdAt": "2013-03-20T20:31:45.000Z",
        "updatedAt": "2013-03-20T20:31:45.000Z",
        "Instruments": [{
          "name": "Toothpick",
          "id": 1,
          "createdAt": null,
          "updatedAt": null,
          "UserId": 1
        }]
      }],

      [{
        "name": "John Smith",
        "id": 2,
        "createdAt": "2013-03-20T20:31:45.000Z",
        "updatedAt": "2013-03-20T20:31:45.000Z",
        "Instruments": [{
          "name": "Toothpick",
          "id": 1,
          "createdAt": null,
          "updatedAt": null,
          "UserId": 1
        }]
      }],
    */
  })

```

```js
User.findAll({ include: [{ all: true }]});
```

相当于以下 sql:

```sql
 SELECT `Task`.`id`, `Task`.`name`, `Task`.`createdAt`, `Task`.`updatedAt`, `Task`.`UserId`, `User`.`id` AS `User.id`, `User`.`name` AS `User.name`, `User`.`createdAt` AS `User.createdAt`, `User`.`updatedAt` AS `User.updatedAt` FROM `Tasks` AS `Task` LEFT OUTER JOIN `Users` AS `User` ON `Task`.`UserId` = `User`.`id`;

 SELECT `User`.`id`, `User`.`name`, `User`.`createdAt`, `User`.`updatedAt`, `Tasks`.`id` AS `Tasks.id`, `Tasks`.`name` AS `Tasks.name`, `Tasks`.`createdAt` AS `Tasks.createdAt`, `Tasks`.`updatedAt` AS `Tasks.updatedAt`, `Tasks`.`UserId` AS `Tasks.UserId` FROM `Users` AS `User` LEFT OUTER JOIN `Tasks` AS `Tasks` ON `User`.`id` = `Tasks`.`UserId`;

 SELECT `User`.`id`, `User`.`name`, `User`.`createdAt`, `User`.`updatedAt`, `Instruments`.`id` AS `Instruments.id`, `Instruments`.`name` AS `Instruments.name`, `Instruments`.`createdAt` AS `Instruments.createdAt`, `Instruments`.`updatedAt` AS `Instruments.updatedAt`, `Instruments`.`UserId` AS `Instruments.UserId` FROM `Users` AS `User` LEFT OUTER JOIN `Tools` AS `Instruments` ON `User`.`id` = `Instruments`.`UserId`;

 SELECT `User`.`id`, `User`.`name`, `User`.`createdAt`, `User`.`updatedAt`, `Instruments`.`id` AS `Instruments.id`, `Instruments`.`name` AS `Instruments.name`, `Instruments`.`createdAt` AS `Instruments.createdAt`, `Instruments`.`updatedAt` AS `Instruments.updatedAt`, `Instruments`.`UserId` AS `Instruments.UserId` FROM `Users` AS `User` INNER JOIN `Tools` AS `Instruments` ON `User`.`id` = `Instruments`.`UserId` AND `Instruments`.`name` LIKE '%ooth%';

SELECT `User`.`id`, `User`.`name`, `User`.`createdAt`, `User`.`updatedAt`, `Tasks`.`id` AS `Tasks.id`, `Tasks`.`name` AS `Tasks.name`, `Tasks`.`createdAt` AS `Tasks.createdAt`, `Tasks`.`updatedAt` AS `Tasks.updatedAt`, `Tasks`.`UserId` AS `Tasks.UserId`, `Instruments`.`id` AS `Instruments.id`, `Instruments`.`name` AS `Instruments.name`, `Instruments`.`createdAt` AS `Instruments.createdAt`, `Instruments`.`updatedAt` AS `Instruments.updatedAt`, `Instruments`.`UserId` AS `Instruments.UserId` FROM `Users` AS `User` LEFT OUTER JOIN `Tasks` AS `Tasks` ON `User`.`id` = `Tasks`.`UserId` LEFT OUTER JOIN `Tools` AS `Instruments` ON `User`.`id` = `Instruments`.`UserId`;
```

#### 关联排序

```js
Company.findAll({ include: [ Division ], order: [ [ Division, 'name' ] ] });
Company.findAll({ include: [ Division ], order: [ [ Division, 'name', 'DESC' ] ] });
Company.findAll({
  include: [ { model: Division, as: 'Div' } ],
  order: [ [ { model: Division, as: 'Div' }, 'name' ] ]
});
Company.findAll({
  include: [ { model: Division, include: [ Department ] } ],
  order: [ [ Division, Department, 'name' ] ]
});
```
### 嵌套关联
```js
User.findAll({
  include: [
    {model: Tool, as: 'Instruments', include: [
      {model: Teacher, include: [ /* etc */]}
    ]}
  ]
}).then(function(users) {
  console.log(JSON.stringify(users))

  /*
    [{
      "name": "John Doe",
      "id": 1,
      "createdAt": "2013-03-20T20:31:45.000Z",
      "updatedAt": "2013-03-20T20:31:45.000Z",
      "Instruments": [{ // 1:M and N:M association
        "name": "Toothpick",
        "id": 1,
        "createdAt": null,
        "updatedAt": null,
        "UserId": 1,
        "Teacher": { // 1:1 association
          "name": "Jimi Hendrix"
        }
      }]
    }]
  */
})
```

有where选项时,会默认使用 inner 内联关联,需要显示使用 required: false
```js
User.findAll({
  include: [{
    model: Tool,
    as: 'Instruments',
    include: [{
      model: Teacher,
      where: {
        school: "Woodstock Music School"
      },
      required: false
    }]
  }]
}).then(function(users) {
  /* ... */
})

```

```js
User.findAll({ include: [{ all: true, nested: true }]});
```

## 作用域

### 定义 

```js
var Project = sequelize.define('project', {
  // Attributes
}, {
  defaultScope: {
    where: {
      active: true
    }
  },
  scopes: {
    deleted: {
      where: {
        deleted: true
      }
    },
    activeUsers: {
      include: [
        { model: User, where: { active: true }}
      ]
    }
    random: function () {
      return {
        where: {
          someNumber: Math.random()
        }
      }
    },
    accessLevel: function (value) {
      return {
        where: {
          accessLevel: {
            $gte: value
          }
        }
      }
    }
  }
});
```
### 使用

```js
Project.findAll()
```
相当于
```sql
SELECT * FROM projects WHERE active = true
```

The default scope can be removed by calling .unscoped(), .scope(null), or by invoking another scope:

```js
Project.scope('deleted').findAll(); // Removes the default scope
```
相当于
```sql
SELECT * FROM projects WHERE deleted = true
```

### 作用域复用

Scopes apply to .find, .findAll, .count, .update and .destroy.

```js
var DeletedProjects = Project.scope('deleted');

DeletedProjects.findAll();
// some time passes

// let's look for deleted projects again!
DeletedProjects.findAll();
```

### 函数形式作用域

scope的参数为作用域,当函数形式作用域无参数时,可直接使用字符串,
如果有参数时,使用对象方式

```js
Project.scope('random', { method: ['accessLevel', 19]}).findAll();
```
相当于
```sql
SELECT * FROM projects WHERE someNumber = 42 AND accessLevel >= 19

```
### 组合作用域

```js
// These two are equivalent
Project.scope('deleted', 'activeUsers').findAll();
Project.scope(['deleted', 'activeUsers']).findAll();
```
相当于
```sql
SELECT * FROM projects
INNER JOIN users ON projects.userId = users.id
AND users.active = true
```

### 默认作用域与其它组合

```js
Project.scope('defaultScope', 'deleted').findAll();
```
相当于
```sql
SELECT * FROM projects WHERE active = true AND deleted = true
```

### 作用域冲突

```js
{
  scope1: {
    where: {
      firstName: 'bob',
      age: {
        $gt: 20
      }
    },
    limit: 2
  },
  scope2: {
    where: {
      age: {
        $gt: 30
      }
    },
    limit: 10
  }
}
```

后面的定义覆盖前面的定义 (仅覆盖相同字段)

相当于
```sql
WHERE firstName = 'bob' AND age > 30 LIMIT 10
```
### 作用域与查询时条件
```js
Project.scope('deleted').findAll({
  where: {
    firstName: 'john'
  }
})

```
相当于
```sql
WHERE deleted = true AND firstName = 'john'
```

## 实例方法

### 新增实例不持久化

#### build

```js
var project = Project.build({
  title: 'my awesome project',
  description: 'woot woot. this will make me a rich man'
})
 
var task = Task.build({
  title: 'specify the project idea',
  description: 'bla',
  deadline: new Date()
})
```

#### 使用缺省值 

```js
// first define the model
var Task = sequelize.define('Task', {
  title: Sequelize.STRING,
  rating: { type: Sequelize.STRING, defaultValue: 3 }
})
 
// now instantiate an object
var task = Task.build({title: 'very important task'})
 
task.title  // ==> 'very important task'
task.rating // ==> 3
```

#### 显式保存

```js
project.save().then(function() {
  // my nice callback stuff
})
 
task.save().catch(function(error) {
  // mhhh, wth!
})
 
// you can also build, save and access the object with chaining:
Task
  .build({ title: 'foo', description: 'bar', deadline: new Date() })
  .save()
  .then(function(anotherTask) {
    // you can now access the currently saved task with the variable anotherTask... nice!
  }).catch(function(error) {
    // Ooops, do some error-handling
  })
```

### 新增持久化实例

```js
Task.create({ title: 'foo', description: 'bar', deadline: new Date() }).then(function(task) {
  // you can now access the newly created task via the variable task
})
```

```js
User.create({ username: 'barfooz', isAdmin: true }, { fields: [ 'username' ] }).then(function(user) {
  // let's assume the default of isAdmin is false:
  console.log(user.get({
    plain: true
  })) // => { username: 'barfooz', isAdmin: false }
})
```

### 更新持久化实例 

```js
// way 1
task.title = 'a very different title now'
task.save().then(function() {})
 
// way 2
task.update({
  title: 'a very different title now'
}).then(function() {})

```

```js
task.title = 'foooo'
task.description = 'baaaaaar'
task.save({fields: ['title']}).then(function() {
 // title will now be 'foooo' but description is the very same as before
})
 
// The equivalent call using update looks like this:
task.update({ title: 'foooo', description: 'baaaaaar'}, {fields: ['title']}).then(function() {
 // title will now be 'foooo' but description is the very same as before
})
```

### 删除持久化实例
```js
Task.create({ title: 'a task' })
.then(function(task) {
  // now you see me...
  return task.destroy();
})
.then(function() {
 // now i'm gone :)
})

```

### 获取简单对象

```js
Person.create({
  name: 'Rambow',
  firstname: 'John'
}).then(function(john) {
  console.log(john.get({
    plain: true
  }))
})
 
// result:
 
// { name: 'Rambow',
//   firstname: 'John',
//   id: 1,
//   createdAt: Tue, 01 May 2012 19:12:16 GMT,
//   updatedAt: Tue, 01 May 2012 19:12:16 GMT
// }

```


## 批量处理方法

### 批量新增
```js
User.bulkCreate([
  { username: 'barfooz', isAdmin: true },
  { username: 'foo', isAdmin: true },
  { username: 'bar', isAdmin: false }
]).then(function() { // Notice: There are no arguments here, as of right now you'll have to...
  return User.findAll();
}).then(function(users) {
  console.log(users) // ... in order to get the array of user objects
})
```

bulkCreate 不返回结果

### 批量更新

Model.update 返回影响行数

```js
Task.bulkCreate([
  {subject: 'programming', status: 'executing'},
  {subject: 'reading', status: 'executing'},
  {subject: 'programming', status: 'finished'}
]).then(function() {
  return Task.update(
    { status: 'inactive' }, /* set attributes' value */,
    { where: { subject: 'programming' }} /* where criteria */
  );
}).then(function(affectedRows) {
  // affectedRows will be 2
  return Task.findAll();
}).then(function(tasks) {
  console.log(tasks) // the 'programming' tasks will both have a status of 'inactive'
})
```
### 批量删除
```js
Task.bulkCreate([
  {subject: 'programming', status: 'executing'},
  {subject: 'reading', status: 'executing'},
  {subject: 'programming', status: 'finished'}
]).then(function() {
  return Task.destroy({
    where: {
      subject: 'programming'
    },
    truncate: true /* this will ignore where and truncate the table instead */
  });
}).then(function(affectedRows) {
  // affectedRows will be 2
  return Task.findAll();
}).then(function(tasks) {
  console.log(tasks) // no programming, just reading :(
})
```
### 指定持久化字段
```js
User.bulkCreate([
  { username: 'foo' },
  { username: 'bar', admin: true}
], { fields: ['username'] }).then(function() {
  // nope bar, you can't be admin!
})
```

### 批量新增检查

默认不检查

```js
var Tasks = sequelize.define('Task', {
  name: {
    type: Sequelize.STRING,
    validate: {
      notNull: { args: true, msg: 'name cannot be null' }
    }
  },
  code: {
    type: Sequelize.STRING,
    validate: {
      len: [3, 10]
    }
  }
})
 
Tasks.bulkCreate([
  {name: 'foo', code: '123'},
  {code: '1234'},
  {name: 'bar', code: '1'}
], { validate: true }).catch(function(errors) {
  /* console.log(errors) would look like:
  [
    { record:
    ...
    errors:
      { name: 'SequelizeValidationError',
        message: 'Validation error',
        errors: [Object] } },
    { record:
      ...
      errors:
        { name: 'SequelizeValidationError',
        message: 'Validation error',
        errors: [Object] } }
  ]
  */
})
```
Hint:You can also transform an instance into JSON by using JSON.stringify(instance). This will basically return the very same as values.

### 刷新实例
```js
Person.findOne({ where: { name: 'john' } }).then(function(person) {
  person.name = 'jane'
  console.log(person.name) // 'jane'
 
  person.reload().then(function() {
    console.log(person.name) // 'john'
  })
})
```
### 增减数量
```js
User.findById(1).then(function(user) {
  return user.increment('my-integer-field', {by: 2})
}).then(/* ... */)

```

```js
User.findById(1).then(function(user) {
  return user.increment([ 'my-integer-field', 'my-very-other-field' ], {by: 2})
}).then(/* ... */)

```

```js
User.findById(1).then(function(user) {
  return user.increment({
    'my-integer-field':    2,
    'my-very-other-field': 3
  })
}).then(/* ... */)
```

```js
User.findById(1).then(function(user) {
  return user.decrement('my-integer-field', {by: 2})
}).then(/* ... */)
```

```js
User.findById(1).then(function(user) {
  return user.decrement([ 'my-integer-field', 'my-very-other-field' ], {by: 2})
}).then(/* ... */)
```

```js
User.findById(1).then(function(user) {
  return user.decrement({
    'my-integer-field':    2,
    'my-very-other-field': 3
  })
}).then(/* ... */)
```

## 关联

### 关联种类

#### 外键 One-To-One 

BelongsTo 属于

源模型.BelongsTo(目标模型)

外键字段(目标模型的主键)添加到源模型中


```js
var Player = this.sequelize.define('Player', {/* attributes */})
  , Team  = this.sequelize.define('Team', {/* attributes */});

Player.belongsTo(Team); // Will add a TeamId attribute to Player to hold the primary key value for Team

```

By default the foreign key for a belongsTo relation will be generated from the target model name and the target primary key name.

外键名=模型名+Id


```js
var User = this.sequelize.define('User', {/* attributes */})
  , Company  = this.sequelize.define('Company', {/* attributes */});

User.belongsTo(Company); // Will add CompanyId to user

var User = this.sequelize.define('User', {/* attributes */}, {underscored: true})
  , Company  = this.sequelize.define('Company', {
    uuid: {
      type: Sequelize.UUID,
      primaryKey: true
    }
  });

User.belongsTo(Company); // Will add company_uuid to user

```

指定目标模型的别名(用于目标模型已存在定义,不可更改的情况)
```js
var User = this.sequelize.define('User', {/* attributes */})
  , UserRole  = this.sequelize.define('UserRole', {/* attributes */});

User.belongsTo(UserRole, {as: 'Role'}); // Adds RoleId to user rather than UserRoleId

```

强制指定外键名

```js
var User = this.sequelize.define('User', {/* attributes */})
  , Company  = this.sequelize.define('Company', {/* attributes */});

User.belongsTo(Company, {foreignKey: 'fk_company'}); // Adds fk_company to User

```

强制指定目标模型的关联字段(当关联字段不是目标模型的主键时用)

```js
var User = this.sequelize.define('User', {/* attributes */})
  , Company  = this.sequelize.define('Company', {/* attributes */});

User.belongsTo(Company, {foreignKey: 'fk_companyname', targetKey: 'name'}); // Adds fk_companyname to User

```
#### HasOne 一对一的反向

HasOne 有一

源模型.HasOne(目标模型)

源模型的主键是目标模型的外键字段 (但不是用于一对多的情况)

两个模型仍然是一对一的情况


```js
var User = sequelize.define('User', {/* ... */})
var Project = sequelize.define('Project', {/* ... */})
 
// One-way associations
Project.hasOne(User)
 
/*
  In this example hasOne will add an attribute ProjectId to the User model!
  Furthermore, Project.prototype will gain the methods getUser and setUser according
  to the first parameter passed to define. If you have underscore style
  enabled, the added attribute will be project_id instead of ProjectId.

  The foreign key will be placed on the users table.

  You can also define the foreign key, e.g. if you already have an existing
  database and want to work on it:
*/
```

```js 
Project.hasOne(User, { foreignKey: 'initiator_id' })
 
/*
  Because Sequelize will use the model's name (first parameter of define) for
  the accessor methods, it is also possible to pass a special option to hasOne:
*/
```

```js 
Project.hasOne(User, { as: 'Initiator' })
// Now you will get Project#getInitiator and Project#setInitiator
```

```js 
// Or let's define some self references
var Person = sequelize.define('Person', { /* ... */})
 
Person.hasOne(Person, {as: 'Father'})
// this will add the attribute FatherId to Person
```

```js 
// also possible:
Person.hasOne(Person, {as: 'Father', foreignKey: 'DadId'})
// this will add the attribute DadId to Person
 
// In both cases you will be able to do:
Person#setFather
Person#getFather
```

```js 
// If you need to join a table twice you can double join the same table
Team.hasOne(Game, {as: 'HomeTeam', foreignKey : 'homeTeamId'});
Team.hasOne(Game, {as: 'AwayTeam', foreignKey : 'awayTeamId'});

Game.belongsTo(Team);

```

#### 一对多 One-To-Many 

This will add the attribute ProjectId or project_id to User. Instances of Project will get the accessors getWorkers and setWorkers. 
```js 
var User = sequelize.define('User', {/* ... */})
var Project = sequelize.define('Project', {/* ... */})
 
// OK. Now things get more complicated (not really visible to the user :)).
// First let's define a hasMany association
Project.hasMany(User, {as: 'Workers'})
```

#### 多对多 Belongs-To-Many

##### 普通多对多

This will create a new model called UserProject with the equivalent foreign keys ProjectId and UserId.

```js 
Project.belongsToMany(User, {through: 'UserProject'});
User.belongsToMany(Project, {through: 'UserProject'});
```

This will add methods getUsers, setUsers, addUser,addUsers to Project, and getProjects, setProjects and addProject, addProjects to Users

##### 自定义命名多对多模型

```js 
User.belongsToMany(Project, { as: 'Tasks', through: 'worker_tasks', foreignKey: 'userId' })
Project.belongsToMany(User, { as: 'Workers', through: 'worker_tasks', foreignKey: 'projectId' })
```

##### 自身多对多

```js 
Person.belongsToMany(Person, { as: 'Children', through: 'PersonChildren' })
// This will create the table PersonChildren which stores the ids of the objects.
```

##### 使用现有的多对多模型

```js 
User = sequelize.define('User', {})
Project = sequelize.define('Project', {})
UserProjects = sequelize.define('UserProjects', {
    status: DataTypes.STRING
})
 
User.belongsToMany(Project, { through: UserProjects })
Project.belongsToMany(User, { through: UserProjects })

```


```js 
user.addProject(project, { status: 'started' })
```

默认多对多模型不会创建单独的主键字段,用组合字段做主键,也可以显式的声明主键.

```js 
UserProjects = sequelize.define('UserProjects', {
    id: {
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true
    },
    status: DataTypes.STRING
})
```

### 作用域与关联 (多态)

#### 贴子图片评论的关系


```js 
this.Comment = this.sequelize.define('comment', {
  title: Sequelize.STRING,
  commentable: Sequelize.STRING,
  commentable_id: Sequelize.INTEGER
}, {
  instanceMethods: {
    getItem: function() {
      return this['get' + this.get('commentable').substr(0, 1).toUpperCase() + this.get('commentable').substr(1)]();
    }
  }
});

this.Post.hasMany(this.Comment, {
  foreignKey: 'commentable_id',
  constraints: false,
  scope: {
    commentable: 'post'
  }
});
this.Comment.belongsTo(this.Post, {
  foreignKey: 'commentable_id',
  constraints: false,
  as: 'post'
});

this.Image.hasMany(this.Comment, {
  foreignKey: 'commentable_id',
  constraints: false,
  scope: {
    commentable: 'image'
  }
});
this.Comment.belongsTo(this.Image, {
  foreignKey: 'commentable_id',
  constraints: false,
  as: 'image'
});

```

使用示例:

```js 
image.getComments()
SELECT * FROM comments WHERE commentable_id = 42 AND commentable = 'image';

image.createComment({
  title: 'Awesome!'
})
INSERT INTO comments (title, commentable_id, commentable) VALUES ('Awesome!', 42, 'image');

image.addComment(comment);
UPDATE comments SET commentable_id = 42, commentable = 'image'
```

#### 贴子与项目标签的关系

项目标签是抽象的多对多关系

即项目标签的项目可以是贴子,也可以是图片

```js 
ItemTag = sequelize.define('item_tag', {
  tag_id: {
    type: DataTypes.INTEGER,
    unique: 'item_tag_taggable'
  },
  taggable: {
    type: DataTypes.STRING,
    unique: 'item_tag_taggable'
  },
  taggable_id: {
    type: DataTypes.INTEGER,
    unique: 'item_tag_taggable',
    references: null
  }
});
Tag = sequelize.define('tag', {
  name: DataTypes.STRING
});

Post.belongsToMany(Tag, {
  through: {
    model: ItemTag,
    unique: false,
    scope: {
      taggable: 'post'
    }
  },
  foreignKey: 'taggable_id',
  constraints: false
});
Tag.belongsToMany(Post, {
  through: {
    model: ItemTag,
    unique: false
  },
  foreignKey: 'tag_id'
});
```
#### 更复杂的示例 

```js 
Post.hasMany(Tag, {
  through: {
    model: ItemTag,
    unique: false,
    scope: {
      taggable: 'post'
    }
  },
  scope: {
    status: 'pending'
  },
  as: 'pendingTags',
  foreignKey: 'taggable_id',
  constraints: false
});

Post.getPendingTags();
```
相当于
```js 
SELECT `tag`.*  INNER JOIN `item_tags` AS `item_tag`
ON `tag`.`id` = `item_tag`.`tagId`
  AND `item_tag`.`taggable_id` = 42
  AND `item_tag`.`taggable` = 'post'
WHERE (`tag`.`status` = 'pending');
```
#### 指定命名

```js 
User.belongsToMany(Project, { as: { singular: 'task', plural: 'tasks' }})
```
### 有关实例的多对多关联方法

#### 简单示例 

```js 
Project.belongsToMany(Task)
Task.belongsToMany(Project)
 
Project.create()...
Task.create()...
Task.create()...
 
// save them... and then:
project.setTasks([task1, task2]).then(function() {
  // saved!
})
 
// ok, now they are saved... how do I get them later on?
project.getTasks().then(function(associatedTasks) {
  // associatedTasks is an array of tasks
})
 
// You can also pass filters to the getter method.
// They are equal to the options you can pass to a usual finder method.
project.getTasks({ where: 'id > 10' }).then(function(tasks) {
  // tasks with an id greater than 10 :)
})
 
// You can also only retrieve certain fields of a associated object.
project.getTasks({attributes: ['title']}).then(function(tasks) {
    // retrieve tasks with the attributes "title" and "id"
})

```


```js 
// remove the association with task1
project.setTasks([task2]).then(function(associatedTasks) {
  // you will get task2 only
})
 
// remove 'em all
project.setTasks([]).then(function(associatedTasks) {
  // you will get an empty array
})
 
// or remove 'em more directly
project.removeTask(task1).then(function() {
  // it's gone
})
 
// and add 'em again
project.addTask(task1).then(function() {
  // it's back again
})

```

#### 实例检查关联是否存在

```js 
// check if an object is one of associated ones:
Project.create({ /* */ }).then(function(project) {
  return User.create({ /* */ }).then(function(user) {
    return project.hasUser(user).then(function(result) {
      // result would be false
      return project.addUser(user).then(function() {
        return project.hasUser(user).then(function(result) {
          // result would be true
        })
      })
    })
  })
})
 
// check if all associated objects are as expected:
// let's assume we have already a project and two users
project.setUsers([user1, user2]).then(function() {
  return project.hasUsers([user1]);
}).then(function(result) {
  // result would be false
  return project.hasUsers([user1, user2]);
}).then(function(result) {
  // result would be true
})
```

### 外键处理方式

#### 自动生成外键

```js 
var Task = this.sequelize.define('Task', { title: Sequelize.STRING })
  , User = this.sequelize.define('User', { username: Sequelize.STRING })
 
User.hasMany(Task)
Task.belongsTo(User)
```
对应结构:
```sql
CREATE TABLE IF NOT EXISTS `User` (
  `id` INTEGER PRIMARY KEY,
  `username` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `Task` (
  `id` INTEGER PRIMARY KEY,
  `title` VARCHAR(255),
  `user_id` INTEGER REFERENCES `User` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
```

```js 
var Document = this.sequelize.define('Document', {
      author: Sequelize.STRING
    })
  , Version = this.sequelize.define('Version', {
      timestamp: Sequelize.DATE
    })

Document.hasMany(Version) // This adds document_id to version
Document.belongsTo(Version, { as: 'Current', foreignKey: 'current_version_id'}) // This adds current_version_id to document

```
如果执行同步命令,会报错:

```
Cyclic dependency found. 'Document' is dependent of itself. Dependency Chain: Document -> Version => Document. In order to alleviate that, we can pass constraints: false to one of the associations:
```

```js 
Document.hasMany(Version)
Document.belongsTo(Version, { as: 'Current', foreignKey: 'current_version_id', constraints: false})
```

```sql
CREATE TABLE IF NOT EXISTS `Document` (
  `id` INTEGER PRIMARY KEY,
  `author` VARCHAR(255),
  `current_version_id` INTEGER
);
CREATE TABLE IF NOT EXISTS `Version` (
  `id` INTEGER PRIMARY KEY,
  `timestamp` DATETIME,
  `document_id` INTEGER REFERENCES `Document` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
```

#### 手动生成外键

手动方式生成外键,不会使用约束

```js 
var Series, Trainer, Video
 
// Series has a trainer_id=Trainer.id foreign reference key after we call Trainer.hasMany(series)
Series = sequelize.define('Series', {
  title:        DataTypes.STRING,
  sub_title:    DataTypes.STRING,
  description:  DataTypes.TEXT,
 
  // Set FK relationship (hasMany) with `Trainer`
  trainer_id: {
    type: DataTypes.INTEGER,
    references: {
      model: "Trainers",
      key: "id"
    }
  }
})
 
Trainer = sequelize.define('Trainer', {
  first_name: DataTypes.STRING,
  last_name:  DataTypes.STRING
});
 
// Video has a series_id=Series.id foreign reference key after we call Series.hasOne(Video)...
Video = sequelize.define('Video', {
  title:        DataTypes.STRING,
  sequence:     DataTypes.INTEGER,
  description:  DataTypes.TEXT,
 
  // set relationship (hasOne) with `Series`
  series_id: {
    type: DataTypes.INTEGER,
    references: {
      model: Series, // Can be both a string representing the table name, or a reference to the model
      key:   "id"
    }
  }
});
 
Series.hasOne(Video);
Trainer.hasMany(Series);

```

### 新增含有关联的实例

#### 从外键表新增实例 
```js 
var Product = this.sequelize.define('Product', {
  title: Sequelize.STRING
});
var User = this.sequelize.define('User', {
  first_name: Sequelize.STRING,
  last_name: Sequelize.STRING
});

Product.belongsTo(User);
// Also works for `hasOne`
```

```js 
return Product.create({
  title: 'Chair',
  User: {
    first_name: 'Mick',
    last_name: 'Broadstone'
  }
}, {
  include: [ User ]
});
```

使用别名:
```js 
var Creator = Product.belongsTo(User, {as: 'creator'});

return Product.create({
  title: 'Chair',
  creator: {
    first_name: 'Matt',
    last_name: 'Hansen'
  }
}, {
  include: [ Creator ]
});
```

### 从主表新增实例 

```js 
var Tag = this.sequelize.define('Tag', {
  name: Sequelize.STRING
});

Product.hasMany(Tag);
// Also works for `belongsToMany`.
```

```js 
Product.create({
  id: 1,
  title: 'Chair',
  Tags: [
    { name: 'Alpha'},
    { name: 'Beta'}
  ]
}, {
  include: [ Tag ]
})
```

使用别名:
```js 
var Categories = Product.hasMany(Tag, {as: 'categories'});

Product.create({
  id: 1,
  title: 'Chair',
  categories: [
    {id: 1, name: 'Alpha'},
    {id: 2, name: 'Beta'}
  ]
}, {
  include: [ Categories ]
})
```
## 事件处理钩子

### 事件顺序

(1)
  beforeBulkCreate(instances, options, fn)
  beforeBulkDestroy(instances, options, fn)
  beforeBulkUpdate(instances, options, fn)
(2)
  beforeValidate(instance, options, fn)
(-)
  validate
(3)
  afterValidate(instance, options, fn)
(4)
  beforeCreate(instance, options, fn)
  beforeDestroy(instance, options, fn)
  beforeUpdate(instance, options, fn)
(-)
  create
  destroy
  update
(5)
  afterCreate(instance, options, fn)
  afterDestroy(instance, options, fn)
  afterUpdate(instance, options, fn)
(6)
  afterBulkCreate(instances, options, fn)
  afterBulkDestroy(instances, options, fn)
  afterBulkUpdate(instances, options, fn)

### 定义钩子

```js 
// Method 1 via the .define() method
var User = sequelize.define('User', {
  username: DataTypes.STRING,
  mood: {
    type: DataTypes.ENUM,
    values: ['happy', 'sad', 'neutral']
  }
}, {
  hooks: {
    beforeValidate: function(user, options) {
      user.mood = 'happy'
    },
    afterValidate: function(user, options) {
      user.username = 'Toni'
    }
  }
})

// Method 2 via the .hook() method
User.hook('beforeValidate', function(user, options) {
  user.mood = 'happy'
})

User.hook('afterValidate', function(user, options) {
  return sequelize.Promise.reject("I'm afraid I can't let you do that!")
})

// Method 3 via the direct method
User.beforeCreate(function(user, options) {
  return hashPassword(user.password).then(function (hashedPw) {
    user.password = hashedPw;
  });
})

User.afterValidate('myHookAfter', function(user, options, fn) {
  user.username = 'Toni'
})
```

```js 

```

```js 

```

```js 

```

```js 

```

```js 

```

```js 

```

```js 

```

```js 

```

