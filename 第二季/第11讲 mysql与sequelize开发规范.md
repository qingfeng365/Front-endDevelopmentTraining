# 第11讲 mysql与sequelize开发规范

## 字段定义规范

### 字段常用类型

```js
Sequelize.STRING    //默认是255
Sequelize.STRING(1234)
Sequelize.TEXT

Sequelize.INTEGER                     
Sequelize.BIGINT                      
Sequelize.BIGINT(11)                 

Sequelize.FLOAT                       
Sequelize.FLOAT(11)                   
Sequelize.FLOAT(11, 12)               

Sequelize.DECIMAL                     // DECIMAL
Sequelize.DECIMAL(10, 2)              // DECIMAL(10,2)

Sequelize.DATE       // DATETIME for mysql / sqlite, TIMESTAMP WITH TIME ZONE for postgres
Sequelize.BOOLEAN                     // TINYINT(1)
```

示例:

```js
var Employee = sequelize.define('Employee', {
  name:  {
    type     : Sequelize.STRING,
	}});
```

### 字段常用选项

```js
allowNull: Boolean,  //允许为null,=true
defaultValue: value|Function,//=null
unique: String | Boolean, //唯一,=false
comment: String, //=null
```

示例:

```js
var Employee = sequelize.define('Employee', {
  name:  {
    type     : Sequelize.STRING,
    allowNull: false,
    defaultValue : 'name',
    unique: true,
    comment: '姓名'
	}});
```

#### 其它不常用选项

```js
	primaryKey: Boolean,//=false
	field: String, //映射字段名,=null,属性名与字段名不同名时用
	autoIncrement: Boolean,//=false
	onUpdate:String,//外键修改时处理 可选值: CASCADE(级联), RESTRICT(禁止), SET DEFAULT, SET NULL or NO ACTION
	onDelete:String,//同上
```

### 字段常用检验

设置检验时均要使用以下格式:

```js
validate:{
	notEmpty:{
		msg: '不允许为空'
	},
	len:{
		args:[2,10],
		msg: '长度不允许超过10'
	}
}
```

系统预置的常用检验:

```js
is: ["^[a-z]+$",'i'],     // will only allow letters
is: /^[a-z]+$/i,          // same as the previous example using real RegExp
not: ["[a-z]",'i'],       // will not allow letters
isEmail: true,            // checks for email format (foo@bar.com)
isUrl: true,              // checks for url format (http://foo.com)
isIP: true,               // checks for IPv4 (129.89.23.1) or IPv6 format
isAlpha: true,            // will only allow letters 字母
isAlphanumeric: true      // will only allow alphanumeric characters, so "_abc" will fail 字母数字
isNumeric: true           // will only allow numbers
isInt: true,              // checks for valid integers
isFloat: true,            // checks for valid floating point numbers
isDecimal: true,          // checks for any numbers
notNull: true,            // won't allow null
isNull: true,             // only allows null
notEmpty: true,           // don't allow empty strings
equals: 'specific value', // only allow a specific value
contains: 'foo',          // force specific substrings
notIn: [['foo', 'bar']],  // check the value is not one of these
isIn: [['foo', 'bar']],   // check the value is one of these
notContains: 'bar',       // don't allow specific substrings
len: [2,10],              // only allow values with length between 2 and 10
isDate: true,             // only allow date strings
isAfter: "2011-11-05",    // only allow date strings after a specific date
isBefore: "2011-11-05",   // only allow date strings before a specific date
max: 23,                  // only allow values
min: 23,                  // only allow values >= 23
isCreditCard: true,       // check for valid credit card numbers
```

#### 字段自定义校验

```js
isEven: function(value) {
        if(parseInt(value) % 2 != 0) {
          throw new Error('Only even values are allowed!')
        }
      }
```

#### 模型自定义校验方法

```js
var Pub = Sequelize.define('Pub', {
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

## 模型方法

- 模型校验方法对象
- 模型方法对象


