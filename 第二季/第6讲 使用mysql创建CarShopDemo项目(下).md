# 第6讲 使用mysql创建CarShopDemo项目(下)


## 建立汽车模型

### 模型目录 

在`server`目录创建`models_mysql`

### 创建Car模型

在`models_mysql`目录创建文件`car.js`

内容如下:

```js
'use strict';
var _ = require('underscore');

module.exports = function (sequelize, DataTypes) {
  var Car = sequelize.define('Car', {
    proTitle: DataTypes.STRING,
    brand: DataTypes.STRING,
    series: DataTypes.STRING,
    color: DataTypes.STRING,
    yearStyle: DataTypes.STRING,
    carModelName: DataTypes.STRING,
    ml: DataTypes.STRING,
    kw: DataTypes.STRING,
    gearbox: DataTypes.STRING,
    guidePrice: DataTypes.STRING,
    imageLogo: DataTypes.STRING,
    buyNum: {
      type: DataTypes.INTEGER,
      defaultValue: 0
    }
  }, {
    charset: 'utf8',
    classMethods: {
      associate: function (models) {

      },
      fetch: function (op) {
        return this
          .findAll(_.extend({
            order: ['createdAt']
          }, op || {}));
      },
      getCount: function (s) {
        if (s) {
          return this
            .count({
              where: {
                $or: [{
                  proTitle: {
                    $like: '%' + s + '%'
                  }
                }, {
                  brand: {
                    $like: '%' + s + '%'
                  }
                }, {
                  series: {
                    $like: '%' + s + '%'
                  }
                }, {
                  carModelName: {
                    $like: '%' + s + '%'
                  }
                }]
              }
            });
        } else {
          return this
            .count();
        }
      },
      findByPage: function (s, page, size) {
        if (s) {
          return this
            .findAll({
              where: {
                $or: [{
                  proTitle: {
                    $like: '%' + s + '%'
                  }
                }, {
                  brand: {
                    $like: '%' + s + '%'
                  }
                }, {
                  series: {
                    $like: '%' + s + '%'
                  }
                }, {
                  carModelName: {
                    $like: '%' + s + '%'
                  }
                }]
              },
              order: ['createdAt'],
              offset: (page - 1) * size,
              limit: size
            });
        } else {
          return this
            .findAll({
              order: ['createdAt'],
              offset: (page - 1) * size,
              limit: size
            });
        }
      }
    }
  });

  return Car;
};

```

### 创建公用服务

在`server`目录创建`sequelizeService.js`

内容如下:

```js
'use strict';

var fs        = require('fs');
var path      = require('path');
var Sequelize = require('sequelize');

var sequelize = new Sequelize(
  'carShop',
  'root',
  '38259343', {
    host: 'localhost',
    dialect: 'mysql'
  });


var sequelizeService = {};
sequelizeService.Sequelize = Sequelize;
sequelizeService.sequelize = sequelize;

sequelizeService.models = {};

var modelpath = path.join(__dirname, 'models_mysql');


fs
  .readdirSync(modelpath)
  .filter(function(file) {
    return (file.indexOf('.js') !== 0);
  })
  .forEach(function(file){
  	var model = sequelize.import(path.join(modelpath, file));
  	sequelizeService.models[model.name] = model;
  });

Object.keys(sequelizeService.models).forEach(function(modelName) {
  if ('associate' in sequelizeService.models[modelName]) {
    sequelizeService.models[modelName].associate(sequelizeService.models);
  }
});
module.exports = sequelizeService;
```


### 增加演示数据

在`server`目录创建演示数据文件`addDemoCar_mysql.js`

```js
'use strict';

var sequelizeService = require('./sequelizeService');

sequelizeService.sequelize
  .sync()
  .then(function() {
    var ModelCar = sequelizeService.models.Car;

    var carArray = [{
      proTitle: '英朗',
      brand: '别克',
      series: '英朗',
      color: '中国红',
      yearStyle: '2015款',
      carModelName: '15N 手动 进取型',
      ml: '1.5L',
      kw: '84kw',
      gearbox: '6挡 手自一体',
      guidePrice: '11.99',
      imageLogo: 'http://img10.360buyimg.com/n7/jfs/t751/148/1231629630/30387/67209b8b/5528c39cNab2d388c.jpg',
      buyNum: 200
    }, {
      proTitle: '哈弗H6',
      brand: '哈弗',
      series: '哈弗',
      color: '雅致银',
      yearStyle: '2015款',
      carModelName: '升级版 1.5T 手动 两驱 都市型',
      ml: '1.5L',
      kw: '110kw',
      gearbox: '6挡 手动',
      guidePrice: '9.63',
      imageLogo: 'http://img10.360buyimg.com/n7/jfs/t874/304/396255796/41995/328da75e/5528c399N3f9cc646.jpg',
      buyNum: 888
    }, {
      proTitle: '速腾',
      brand: '大众',
      series: '速腾',
      color: '雅士银',
      yearStyle: '2015款',
      carModelName: '1.4T 双离合 230TSI 舒适型',
      ml: '1.4L',
      kw: '96kw',
      gearbox: '7挡 双离合',
      guidePrice: '12.30',
      imageLogo: 'http://img10.360buyimg.com/n7/jfs/t988/239/475904647/32355/a1d35780/55278f2cN574b21ab.jpg',
      buyNum: 100
    }, {
      proTitle: '捷达',
      brand: '一汽大众',
      series: '捷达',
      color: '珊瑚蓝',
      yearStyle: '2015款',
      carModelName: '质惠版 1.4L 手动时尚型',
      ml: '1.4L',
      kw: '66kw',
      gearbox: '5挡 手动',
      guidePrice: '7.51',
      imageLogo: 'http://img10.360buyimg.com/n7/jfs/t1108/41/489298815/33529/38655c9f/5528c276N41f39d00.jpg',
      buyNum: 300
    }, {
      proTitle: '本田XR-V',
      brand: '东风本田',
      series: 'XR-V',
      color: '炫金银',
      yearStyle: '2015款',
      carModelName: '1.5L 自动 经典版',
      ml: '1.5L',
      kw: '96kw',
      gearbox: '无级挡 CVT无级变速',
      guidePrice: '12.78',
      imageLogo: 'http://img10.360buyimg.com/n7/jfs/t754/341/1237166856/40843/baf73c5c/5528c273Ncb42f04c.jpg',
      buyNum: 500
    }];

    ModelCar
      .bulkCreate(carArray)
      .then(function() {
        console.log('新增 %d 条记录', carArray.length);
      })
      .error(function(err) {
        console.log(err);
      });
  });


```

## 修改首页控制器

在`server/controllers/`目录修改`index.js`文件

```js
'use strict';
var sequelizeService = require('../sequelizeService');
var Car = sequelizeService.models.Car;

module.exports.index = function(req, res, next) {
  return Car.fetch()
    .then(function(cars) {
      console.log('then...1');
      return res.render('index', {
        title: '汽车商城 首页',
        cars: cars
      });
    })
    .error(function(err) {
      return next(err);
    });
};
```

运行整个项目,首页已完成由mysql实现

## 建立用户模型

在`models_mysql`目录创建文件`user.js`

内容如下:

```js
'use strict';
var _ = require('underscore');
var bcrypt = require('bcryptjs');
var Promise = require('bluebird');

function encodePassword(user) {
  return new Promise(function(resolve, reject) {
    if (!user.password) {
      resolve(user);
    } else {
      if (user.changed('password')) {
        bcrypt.genSalt(10, function(err, salt) {
          if (err) {
            return reject(err);
          }
          bcrypt.hash(user.password, salt, function(err, hash) {
            if (err) {
              return reject(err);
            }
            user.password = hash;
            resolve(user);
          });
        });
      }else{
        resolve(user);
      }
    }
  });
}

module.exports = function(sequelize, DataTypes) {
  var User = sequelize.define('User', {
    name: {
      type: DataTypes.STRING,
      unique: true
    },
    password: DataTypes.STRING,
    level: {
      type: DataTypes.INTEGER,
      defaultValue: 0
    },
    lastSigninDate: DataTypes.DATE
  }, {
    charset: 'utf8',
    classMethods: {
      associate: function(models) {

      },
      fetch: function(op) {
        return this
          .findAll(_.extend({
            order: ['createdAt']
          }, op || {}));
      }
    },
    instanceMethods: {
      comparePassword: function(inputpw, cb) {
        var user = this;
        bcrypt.compare(inputpw, user.password,
          function(err, isMatch) {
            if (err) {
              return cb(err);
            }
            cb(null, isMatch);
          });
      }
    },
    hooks: {
      beforeCreate: function(user, options) {
        return encodePassword(user);
      },
      beforeUpdate: function(user, options) {
        return encodePassword(user);
      },
    }
  });
  return User;
};

```

### 增加演示数据

在`server`目录创建演示数据文件`addDemoAdminUser_mysql.js`

```js
'use strict';

var sequelizeService = require('./sequelizeService');

sequelizeService.sequelize
  .sync()
  .then(function() {
    var User = sequelizeService.models.User;

    var adminUserArray = [{
      name: 'admin',
      password: 'admin',
      level: 900
    }, {
      name: 'superadmin',
      password: 'superadmin',
      level: 999
    }];

    User
      .bulkCreate(adminUserArray,{individualHooks:true})
      .then(function() {
        console.log('新增 %d 条记录', adminUserArray.length);
      })
      .error(function(err) {
        console.log(err);
      });
  });
```
### 修改用户控制器

在`server/controllers/`目录修改`user.js`文件

```js
'use strict';

var sequelizeService = require('../sequelizeService');
var User = sequelizeService.models.User;

module.exports.showSignup = function(req, res, next) {
  res.render('signup', {
    title: '汽车商城 注册页',
    user: {}
  });
};

module.exports.showSignin = function(req, res, next) {
  res.render('signin', {
    title: '汽车商城 登录页',
    user: {}
  });
};

module.exports.postSignup = function(req, res, next) {
  var userObj = req.body.user;
  if (!userObj) {
    return res.status(400).send('找不到合法数据.');
  }

  User
    .build(userObj)
    .save()
    .then(function(_user) {
      req.session.loginuser = _user;
      return res.redirect('/');
    })
    .error(function(err) {
      res.locals.syserrmsg = '用户名已存在，不能完成注册';
      return module.exports.showSignup(req, res, next);
    });
};

module.exports.postSignin = function(req, res, next) {
  var userObj = req.body.user;
  if (!userObj) {
    return res.status(400).send('找不到合法数据.');
  }
  var name = userObj.name;
  var inputpw = userObj.password;

  User.findOne({
      where: {
        name: name
      }
    })
    .then(function(_user) {
      if (!_user) {
        res.locals.syserrmsg = '用户名不存在...';
        return module.exports.showSignin(req, res, next);
      } else {
        _user.comparePassword(inputpw, function(err, isMatch) {
          if (err) {
            console.log(err);
            return res.redirect('/signin');
          }
          if (isMatch) {
            console.log('用户: %s 登录验证成功.', name);
            req.session.loginuser = _user.get({
              plain: true
            });
            var id = _user.id;
            _user
              .update({
                lastSigninDate: Date.now()
              })
              .then(function() {
                return res.redirect('/');
              })
              .error(function(err) {
                return next(err);
              });
          } else {
            res.locals.syserrmsg = '密码不正确，请重新输入...';
            return module.exports.showSignin(req, res, next);
          }
        });
      }
    })
    .error(function(err) {
      console.log(err);
      return res.redirect('/signup');
    });

};

module.exports.logout = function(req, res, next) {
  req.session.destroy(function(err) {
    return res.redirect('/');
  });
};

module.exports.requireSignin = function(req, res, next) {
  var user = req.session.loginuser;
  if (!user) {
    return res.redirect('/signin');
  }
  next();
};

module.exports.requireAdmin = function(req, res, next) {
  var user = req.session.loginuser;
  if (!user) {
    return res.redirect('/signin');
  }
  if (!user.level) {
    return res.redirect('/signin');
  }
  if (user.level < 900) {
    return res.redirect('/signin');
  }
  next();
};

```

## 评论与回复模型

在`models_mysql`目录创建文件`comment.js`

```js
'use strict';

module.exports = function (sequelize, DataTypes) {
  var Comment = sequelize.define('Comment', {
    content: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {
    charset: 'utf8',
    classMethods: {
      associate: function (models) {
        Comment.belongsTo(models.Car);
        Comment.belongsTo(models.User, {
          as: 'From',
          constraints: false
        });
        Comment.hasMany(models.Reply);
      },
      fetchByCarId: function (carId) {
        // console.dir(this);
        return this
          .findAll({
            include: [{
              association: this.associations.Car,
              where: {
                id: carId
              },
              attributes: []
            }, {
              all: true, nested: true
            }],
            order: ['createdAt','id','Replies.id']
          });

      },
    }
  });

  return Comment;
};

```

在`models_mysql`目录创建文件`reply.js`

```js
'use strict';

module.exports = function (sequelize, DataTypes) {
  var Reply = sequelize.define('Reply', {
    content: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {
    charset: 'utf8',
    classMethods: {
      associate: function (models) {
        Reply.belongsTo(models.Comment);
        Reply.belongsTo(models.User, {
          as: 'From',
          constraints: false
        });
        Reply.belongsTo(models.User, {
          as: 'To',
          constraints: false
        });
      }
    }
  });
  return Reply;
};
```

### 增加评论演示数据

在`server`目录创建演示数据文件`addDemoComment_mysql.js`

```js
'use strict';

var sequelizeService = require('./sequelizeService');
var Promise = require('bluebird');

var join = Promise.join;

sequelizeService.sequelize
  .sync()
  .then(function() {
    var Car = sequelizeService.models.Car;
    var User = sequelizeService.models.User;
    var Comment = sequelizeService.models.Comment;

    var getCar = Car.findOne({
      order: ['createdAt']
    });
    var getUser = User.findOne({
      order: ['createdAt']
    });

    join(getCar, getUser, function(car, user) {
      var buildPromiseArray = [];

      buildPromiseArray.push(
        Promise.resolve(Comment.build({
          content: '这是评论1....',
        })).then(function(comment) {
          return join(comment.setCar(car, {
              save: false
            }),
            comment.setFrom(user, {
              save: false
            }),
            function() {
              comment.save().then(function() {
                // console.dir(comment);
              });
            });
        })
      );

      buildPromiseArray.push(
        Promise.resolve(Comment.build({
          content: '这是评论2....',
        })).then(function(comment) {
          return join(comment.setCar(car, {
              save: false
            }),
            comment.setFrom(user, {
              save: false
            }),
            function() {
              comment.save().then(function() {
                // console.dir(comment);
              });
            });
        })
      );

      buildPromiseArray.push(
        Promise.resolve(Comment.build({
          content: '这是评论3....',
        })).then(function(comment) {
          return  join(comment.setCar(car, {
              save: false
            }),
            comment.setFrom(user, {
              save: false
            }),
            function() {
              comment.save().then(function() {
                // console.dir(comment);
              });
            });
        })
      );

      Promise.all(buildPromiseArray)
        .then(function() {
          console.log('新增 %d 条记录...', buildPromiseArray.length);
        })
        .error(function(err) {
          console.log(err);
        });
    });
  });

```

### 增加回复演示数据

在`server`目录创建演示数据文件`addDemoCommentReply_mysql.js`

```js
'use strict';

var sequelizeService = require('./sequelizeService');
var Promise = require('bluebird');
var join = Promise.join;

sequelizeService.sequelize
  .sync()
  .then(function () {
    var Car = sequelizeService.models.Car;
    var User = sequelizeService.models.User;
    var Comment = sequelizeService.models.Comment;
    var Reply = sequelizeService.models.Reply;

    var getComment = Comment.findOne({
      order: ['createdAt']
    });
    var getUser = User.findOne({
      order: [
        ['createdAt', 'DESC']
      ]
    });

    join(getComment, getUser, function (comment, fromUser) {
      comment.getFrom().then(function (toUser) {
        var buildPromiseArray = [];
        buildPromiseArray.push(
          Promise.resolve(Reply.build({
            content: '这是回复1....'
          }))
          .then(function (reply) {
            return join(
              reply.setFrom(fromUser, {
                save: false
              }),
              reply.setTo(toUser, {
                save: false
              }),
              reply.setComment(comment, {
                save: false
              }),
              function () {
                return reply.save();
              });
          })
        );
        buildPromiseArray.push(
          Promise.resolve(Reply.build({
            content: '这是回复2....'
          }))
          .then(function (reply) {
            return join(
              reply.setFrom(fromUser, {
                save: false
              }),
              reply.setTo(toUser, {
                save: false
              }),
              reply.setComment(comment, {
                save: false
              }),
              function () {
                return reply.save();
              });
          })
        );
        buildPromiseArray.push(
          Promise.resolve(Reply.build({
            content: '这是回复3....'
          }))
          .then(function (reply) {
            return join(
              reply.setFrom(fromUser, {
                save: false
              }),
              reply.setTo(toUser, {
                save: false
              }),
              reply.setComment(comment, {
                save: false
              }),
              function () {
                return reply.save();
              });
          })
        );

        Promise.all(buildPromiseArray)
          .then(function () {
            console.log('新增 %d 条记录...', buildPromiseArray.length);
          })
          .error(function (err) {
            console.log(err);
          });
      });
    });
  });

```

### 修改车辆控制器

在`server/controllers/`目录修改`car.js`文件

```js
'use strict';
var _ = require('underscore');

var sequelizeService = require('../sequelizeService');
var Car = sequelizeService.models.Car;
var Comment = sequelizeService.models.Comment;

module.exports.showDetail = function (req, res, next) {
  var id = req.params.id;
  var car;
  var comments;
  Car.findById(id)
    .then(function (_car) {
      car = _car;
      return Comment.fetchByCarId(id);
    })
    .then(function (_comments) {
      comments = _comments;
      return res.render('car_detail', {
        title: '汽车商城 详情页',
        car: car,
        comments: comments
      });
    })
    .error(function (err) {
      return next(err);
    });
};

// admin/car/list?page=&pagetotal=&search=
module.exports.showList = function (req, res, next) {

  var size = 6;
  var page = parseInt(req.query.page);
  var pagetotal = parseInt(req.query.pagetotal);
  var search = req.query.search;
  console.log('search');
  console.log(search);

  var searchquery = '';
  if (search) {
    searchquery = '&search=' + encodeURIComponent(search);
  }

  if (!page) {
    //第一次调用
    Car.getCount(search)
      .then(function (totalsize) {
        page = 1;
        pagetotal = Math.ceil(totalsize / size);
        return Car.findByPage(search, page, size);
      })
      .then(function (cars) {
        res.render('car_list.jade', {
          title: '汽车商城 列表页',
          cars: cars,
          page: page,
          size: size,
          pagetotal: pagetotal,
          searchquery: searchquery
        });
      })
      .error(function (err) {
        return next(err);
      });
  } else {
    Car.findByPage(search, page, size)
      .then(function (cars) {
        res.render('car_list.jade', {
            title: '汽车商城 列表页',
            cars: cars,
            page: page,
            size: size,
            pagetotal: pagetotal,
            searchquery: searchquery
          })
          .error(function (err) {
            return next(err);
          });
      });
  }
};

module.exports.search = function (req, res, next) {
  var search = req.body.search;
  var s = encodeURIComponent(search.text);

  res.redirect('/admin/car/list?search=' + s);

};

module.exports.new = function (req, res, next) {
  res.render('car_admin', {
    title: '汽车商城 后台录入页',
    car: {}
  });
};
module.exports.update = function (req, res, next) {
  var id = req.params.id;

  Car.findById(id)
    .then(function (car) {
      return res.render('car_admin', {
        title: '汽车商城 后台录入页',
        car: car
      });
    })
    .error(function (err) {
      return next(err);
    });

};
module.exports.post = function (req, res, next) {
  var carObj = req.body.car;
  if (!carObj) {
    return res.status(400).send('找不到合法数据.');
  }
  var id = carObj.id;

  if (!id) {
    //新增
    Car.build(carObj)
      .save()
      .then(function (_car) {
        return res.redirect('/car/' + _car.id);
      })
      .error(function (err) {
        return next(err);
      });
  } else {
    Car.findById(id)
      .then(function (_car) {
        _car = _.extend(_car, carObj);
        return _car.save();
      })
      .then(function (_car) {
        return res.redirect('/car/' + _car.id);
      })
      .error(function (err) {
        return next(err);
      });
  }
};
module.exports.del = function (req, res, next) {
  var id = req.query.id;
  if (id) {

    Car.destroy({
        where: {
          id: id
        }
      })
      .then(function (row) {
        res.json({
          ok: 1
        });
      })
      .error(function (err) {
        res.status(500).json({
          ok: 0
        });
      });

  } else {
    res.json({
      ok: 0
    });
  }
};

```

### 修改评论控制器

在`server/controllers/`目录修改`comment.js`文件

```js
'use strict';
var sequelizeService = require('../sequelizeService');
var Car = sequelizeService.models.Car;
var Comment = sequelizeService.models.Comment;
var User = sequelizeService.models.User;
var Reply = sequelizeService.models.Reply;

var Promise = require('bluebird');
var join = Promise.join;

module.exports.post = function (req, res, next) {
  var commentObj = req.body.comment;
  if (!commentObj) {
    return res.status(400).send('找不到合法数据.');
  }
  console.dir(commentObj);
  var carId = commentObj.car;
  var commentId = commentObj.commentid;
  var fromUserId = commentObj.from;
  var toUserId = commentObj.to;
  var content = commentObj.content;

  var car = Car.build({
    id: carId
  });
  var fromUser = User.build({
    id: fromUserId
  });

  if (!commentObj.commentid) {
    //新增评论
    var comment = Comment.build({
      content: content
    });

    join(comment.setCar(car, {
          save: false
        }),
        comment.setFrom(fromUser, {
          save: false
        }),
        function () {
          comment.save().then(function () {
            return res.redirect('/car/' + carId);
          });
        })
      .error(function (err) {
        return next(err);
      });

  } else {
    //回复评论
    var toUser = User.build({
      id: toUserId
    });
    Comment.findById(commentId, {
        attributes: ['id']
      })
      .then(function (comment) {
        if (!comment) {
          return Promise.reject(new Error('Comment not found.'));
        }
        var reply = Reply.build({
          content: content
        });
        return join(
          reply.setFrom(fromUser, {
            save: false
          }),
          reply.setTo(toUser, {
            save: false
          }),
          reply.setComment(comment, {
            save: false
          }),
          function () {
            return reply.save();
          });
      })
      .then(function(){
        return res.redirect('/car/' + carId);
      })
      .error(function (err) {
        return next(err);
      });

  }
};

```

## 最后处理session的存储：mysql(不推荐使用)

### 安装新模块

```bash
cnpm install connect-session-sequelize --save
```

### 修改有关session代码

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