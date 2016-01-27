# 第4讲 angular下拉框联动-图片上传-树图的实现方法

## 下拉框联动

### angular默认select

省

```html
  <select name="province" ng-model="input.province" ng-options="province.name for province in area" required>
    <option value="">选择省</option>
```

市

```html
  <select name="city" ng-model="input.city" ng-options="city.name for city in input.province.city" required>
    <option value="">选择城市</option>
  </select>
```

区:

```html
  <select name="downtown" ng-model="input.downtown" ng-options="downtown for downtown in input.city.area" required>
    <option value="">选择城区</option>
  </select>
```

### ui-select

使用ui-select需要先初始化

```js
  $scope.input = {
  	province:null,
  	city:null,
  };
```

省

```js
	.col-md-4
		ui-select(ng-model="input.province",theme="bootstrap",required,name="province",on-select="onselectprovince($select,$item)")
			ui-select-match(placeholder="请选择省...") {{$select.selected.name}}
			ui-select-choices(repeat="province in area | filter: $select.search")
				div(ng-bind-html="province.name | highlight: $select.search")
```

市

```js
	.col-md-4
		ui-select(ng-model="input.city",theme="bootstrap",required,name="city"												,on-select="onselectcity($select,$item)")
			ui-select-match(placeholder="请选择城市...") {{$select.selected.name}}
			ui-select-choices(repeat="city in input.province.city | filter: $select.search")
				div(ng-bind-html="city.name | highlight: $select.search")
```

区:

```js
	.col-md-4
		ui-select(ng-model="input.downtown",theme="bootstrap",required,name="downtown")
			ui-select-match(placeholder="请选择城区...") {{$select.selected}}
			ui-select-choices(repeat="downtown in input.city.area | filter: $select.search")
				div(ng-bind-html="downtown | highlight: $select.search")
```

还要主动清空对象:

```js
    $scope.onselectprovince = function ($select, $item) {
      $scope.input.city = null;
      $scope.input.downtown = '';
    };
    $scope.onselectcity = function ($select, $item) {
      $scope.input.downtown = '';
    };   
```

## 文件上传

### 普通表单上传文件处理过程

表单选择文件字段:

```js
	input(type="file",name="imagefile")  
```

后台处理过程:

安装`connect-multiparty`

```bash
	cnpm install connect-multiparty --save
```

在接收文件的路由:

```js
var multipart = require('connect-multiparty');
var multipartMiddleware = multipart();

app.post('/xxxx', multipartMiddleware, xxxx.savefile,xxxx.save);
```

在savefile方法中:

使用 req.files 可获得所有 type="file" 的文件数据

使用 req.files.<name> 可获得对应字段的文件数据

req.files的数据格式:
```js
  { imagefile: 
     { fieldName: 'imagefile',
       originalFilename: 'Uruguay.png',
       path: '/var/folders/fy/fdwf7c5928v6zzlrnft1vq540000gn/T/MUZtZDKNJFOV1tgbbacolpMi.png',
       headers: 
        { 'content-disposition': 'form-data; name="imagefile"; filename="Uruguay.png"',
          'content-type': 'image/png' },
       size: 40083,
       name: 'Uruguay.png',
       type: 'image/png' } }
```

保存文件的代码:

```js
  var fileData = req.files.imagefile;
  var filePath = fileData.path;
  var fileSize = fileData.size;

    fs.readFile(filePath, function(err, data) {
      var timestamp = Date.now();
      var type = posterData.type.split('/')[1];
      var filename = timestamp + '.' + type;
      var newPath = path.join(process.cwd(), '/public/upload/' + filename);

      fs.writeFile(newPath, data, function(err) {
        req.poster = poster;
        next();

      });
    });
```

文件名的另一种方案:

安装`node-uuid`

```bash
cnpm install node-uuid --save
```

```js
 var filename = uuid.v1()+'.' + type;;
```


### angular-file-upload

```bash
bower install angular-file-upload
```


## 树图

```bash
bower install angular-ui-tree --save
```

