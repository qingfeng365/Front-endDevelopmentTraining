# 第8讲 项目重构成为MVC结构


注: 本讲内容为 04-work 分支

## 为什么要重构


## 第一步重构:将路由处理代码剥离出来

在`server`目录下增加 `routes.js` 

内容如下:
```js
module.exports = function(app){
	
}
```

然后将 `app.js` 中，类似下面的处理代码，全部移到`routes.js`

```js
app.get('/', function(req, res, next) {
```

并将 

```js
var ModelCar = require('./models/car');
```
移到`routes.js`的最前面.


处理后，`routes.js`文件的内容如下：

```js
'use strict';
var ModelCar = require('./models/car');

module.exports = function(app) {
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

  app.get('/admin/car/new', function(req, res) {
    res.render('car_admin', {
      title: '汽车商城 后台录入页',
      car: {}
    });
  });

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
}
```

同时`app.js`在刚才移走的路由处理代码处，增加一行代码:

```js
require('routes')(app);
```

测试通过。

## 第二步重构:服务端增加 `controller` 层

在`server`目录下增加`controllers`目录。

### 增加首页的controller

在 `server\controllers` 目录下增加 `index.js` 文件

```js
'use strict';
var ModelCar = require('../models/car');

module.exports.index = function(req, res, next) {
  ModelCar.fetch(function(err, cars) {
    if (err) {
      return next(err);
    }
    res.render('index', {
      title: '汽车商城 首页',
      cars: cars
    });
  });
}
```
修改`routes.js`的内容:
```js
app.get('/', indexController.index);
```

### 增加car的controller

在 `server\controllers` 目录下增加 `car.js` 文件

```js
'use strict';

var ModelCar = require('../models/car');

module.exports.showDetail = function(req, res, next) {
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
}

module.exports.showList = function(req, res, next) {
  ModelCar.fetch(function(err, cars) {
    if (err) {
      return next(err);
    }
    res.render('car_list.jade', {
      title: '汽车商城 列表页',
      cars: cars
    });
  });
}

module.exports.new = function(req, res, next) {
  res.render('car_admin', {
    title: '汽车商城 后台录入页',
    car: {}
  });
}
module.exports.update = function(req, res, next) {
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
}
module.exports.post = function(req, res, next) {
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
}
module.exports.del = function(req, res, next) {
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
}

```
### 调整routes.js

最后内容如下:

```js
'use strict';

var indexController = require('./controllers/index');
var carController = require('./controllers/car');

module.exports = function(app) {
  app.get('/', indexController.index);

  app.get('/car/:id', carController.showDetail);

  app.get('/admin/car/list', carController.showList);

  app.get('/admin/car/new', carController.new);

  app.get('/admin/car/update/:id', carController.update);

  app.post('/admin/car', carController.post);

  // /admin/list?id=xxxxx
  app.delete('/admin/list', carController.del);
};
```

## 04-work 结束

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

分支 04-work 结束








