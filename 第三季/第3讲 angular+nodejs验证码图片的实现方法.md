# 第3讲 angular+nodejs验证码图片的实现方法

## 要考虑的要点

- 生成图片
- 展示图片
- 不刷新页面刷新图片
- 验证码如何存储
- 验证码校验
- 异步校验

## 使用到的插件

### 后端

- captchapng  图片生成(仅js)
- node-smple-cache 缓存管理

### 前端

- Math.uuid.js (没有安装包)

## 制作过程

### 生成图片

`app.js`

```js
var validimgcode = require('./routes/validimgcode');
app.use('/validimgcode', validimgcode);
```

`/routes/validimgcode.js`


```js
'use strict';
var express = require('express');
var router = express.Router();
var validimgcodeController = require('../controllers/validimgcode');
/**
 * 获取验证码图片
 */
router.get('/', validimgcodeController.getValidImg);
/**
 * 提交验证码进行检查
 */
router.post('/', validimgcodeController.checkValidImgCode);
module.exports = router;
```

`/controllers/validimgcode.js`


```js
'use strict';

var validator = require('validator');
var captchapng = require('captchapng');
var cache = require('node-smple-cache').createCache('LRU', 100 * 100 * 10);

module.exports.getValidImg = function (req, res, next) {
  // res.render('index', { title: '首页' });
  var cacheId = req.query.id;
  console.log(cacheId);
  if (cacheId) {
    var validValue = parseInt(Math.random() * 9000 + 1000);
    cache.set(cacheId, validValue, 1000 * 60 * 30);
    console.log('validValue:' + cache.get(cacheId));
    var p = new captchapng(80, 30, validValue); // width,height,numeric
    p.color(60, 179, 215, 100); // First color: background (red, green, blue, alpha)
    p.color(80, 80, 80, 255); // Second color: paint (red, green, blue, alpha)
    var img = p.getBase64();
    var imgbase64 = new Buffer(img, 'base64');
    res.set('Content-Type', 'image/png');
    res.send(imgbase64);
  } else {
    res.send('');
  }
};
```

#### 说明

`Buffer`

一个 Buffer 实例类似于一个整数数组

Buffer对象可以和字符串相互转换，支持的编码类型如下：

ASCII、UTF-8、UTF-16LE/UCS-2、Base64、Binary、Hex

字符串转Buffer

new Buffer(str, [encoding])，默认UTF-8

### 展示图片

在浏览器直接访问

`http://localhost:4001/validimgcode?id=111`

如何在前端生成uuid

`Math.uuid.js`

```js
$scope.validcodeid = Math.uuid();
```


```js
	.form-group.has-feedback(ng-class="{'has-success':form['validcode'].$valid && form['validcode'].$dirty,'has-error':form['validcode'].$invalid && form['validcode'].$dirty}")
		label.col-md-3.control-label 验证码
		.col-md-5
			.input-group
				input.form-control(type="text",ng-model="validcode", required,
				name="validcode",
				ui-validate-async="{validcode:'checkValidCodeAsync($value)'}")
				img.form-control-feedback(ng-src="/validimgcode?id={{validcodeid}}",
						data-id="{{validcodeid}}",style="width:80px;right:54px;z-index:5;")
				span.input-group-btn
					button.btn.btn-info(type="button",ng-click="changeValidCode($event)") 刷新
			div(ng-messages="form['validcode'].$error",ng-show="form['validcode'].$dirty")
				.help-block(ng-message="required") 请输入验证码
				.help-block(ng-message="validcode") 验证码不正确												
		.col-md-4
			p.help-block
```

### 不刷新页面刷新图片

```js
  $scope.changeValidCode = function () {
    $scope.validcodeid = Math.uuid();
    $scope.validcode = '';
  };
```

### 验证码如何存储


`/controllers/validimgcode.js`

```js
var cache = require('node-smple-cache').createCache('LRU', 100 * 100 * 10);
```

```js
    var validValue = parseInt(Math.random() * 9000 + 1000);
    cache.set(cacheId, validValue, 1000 * 60 * 30);
```

### 验证码校验

`/controllers/validimgcode.js`

```js
module.exports.checkValidImgCode = function (req, res, next) {
  var cacheId = req.body.id;
  var code = req.body.code;
  console.log(cacheId + ':' + code);
  var checkcode = cache.get(cacheId);
  if (checkcode) {
    if (validator.equals(checkcode, code)) {
      res.json({
        ok: true
      });
    } else {
      res.status(400).json({
        error: 'invalid code!'
      });
    }
  } else {
    res.status(400).json({
      error: 'invalid code!'
    });
  }
};
```

### 异步校验

```js
	ui-validate-async="{validcode:'checkValidCodeAsync($value)'}")
```

```js
  $scope.checkValidCodeAsync = function (value) {
    if (value.length >= 4) {
      return $http.post('/validimgcode', {
          id: $scope.validcodeid,
          code: value
        })
        .success(function (data, status, headers, config) {
          console.log(data);
        })
        .error(function (data, status, headers, config) {
          console.log(data);
        });
    } else {
      return $q.reject('invalid code');
    }
  };
```









