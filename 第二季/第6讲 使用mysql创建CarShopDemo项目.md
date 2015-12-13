# 第6讲 使用mysql创建CarShopDemo项目

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
cnpm install connect-session-sequelize --save
```

## 创建数据库

使用Navicat Premium创建数据库`carShop`

## 先处理session的存储：mysql

旧代码：
```js
var MongoStore = require('connect-mongo')(session);
app.use(session({
  name: 'carshopsession',
  secret: 'carshopkey',
  resave: false,
  saveUninitialized: false,
  cookie:{maxAge:3 * 24 * 60 * 60 * 1000},
  store: new MongoStore({
    mongooseConnection: mongoose.connection,
  })
}));

```

新代码：
```js
var Sequelize = require('sequelize');
var SequelizeStore = require('connect-session-sequelize')(session.Store);
var sequelize = new Sequelize(
  'carShop',
  'root',
  '38259343', {
    host: 'localhost',
    dialect: 'mysql'
  });
var sequelizeStore = new SequelizeStore({
    db: sequelize
  });
sequelizeStore.sync();
app.use(session({
  name: 'carshopsession',
  secret: 'carshopkey',
  resave: false,
  saveUninitialized: false,
  cookie: {
    maxAge: 3 * 24 * 60 * 60 * 1000
  },
  store: sequelizeStore
}));

```

`sequelizeStore.sync();` 作用是创建表

表结构如下：

```js
/**
 * Session Model
 */
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('Session', {
    sid: {
      type: DataTypes.STRING,
      primaryKey: true
    }
    , expires: {
      type: DataTypes.DATE,
      allowNull: true
    }
    , data: DataTypes.TEXT
  });
};

```

