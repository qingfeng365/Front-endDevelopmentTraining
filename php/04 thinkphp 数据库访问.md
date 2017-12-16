# 04 thinkphp 数据库访问

## 配置

### `database.php`

```php
<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2016 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

return [
    // 数据库类型
    'type'            => 'mysql',
    // 服务器地址
    'hostname'        => '127.0.0.1',
    // 数据库名
    'database'        => 'zerg',
    // 用户名
    'username'        => 'root',
    // 密码
    'password'        => '123456',
    // 端口
    'hostport'        => '',
    // 连接dsn
    'dsn'             => '',
    // 数据库连接参数
    'params'          => [],
    // 数据库编码默认采用utf8
    'charset'         => 'utf8',
    // 数据库表前缀
    'prefix'          => '',
    // 数据库调试模式
    'debug'           => true,
    // 数据库部署方式:0 集中式(单一服务器),1 分布式(主从服务器)
    'deploy'          => 0,
    // 数据库读写是否分离 主从式有效
    'rw_separate'     => false,
    // 读写分离后 主服务器数量
    'master_num'      => 1,
    // 指定从服务器序号
    'slave_no'        => '',
    // 是否严格检查字段是否存在
    'fields_strict'   => true,
    // 数据集返回类型
    'resultset_type'  => 'array',
    // 自动写入时间戳字段
    'auto_timestamp'  => false,
    // 时间字段取出后的默认时间格式
    'datetime_format' => 'Y-m-d H:i:s',
    // 是否需要进行SQL性能分析
    'sql_explain'     => false,
];

```

## 修改默认输出json

### `config.php`

旧:
```php
    // 默认输出类型
    'default_return_type'    => 'html',
```

新:
```php
    // 默认输出类型
    'default_return_type'    => 'json',
```

### 自定义应用配置

在`application` 上创建 `extra` 目录, 这是系统默认配置目录

### `extra/setting.php`  

```php
<?php

//localhost/zerg/public/images/1@theme.png

return [
    'img_prefix' => 'localhost/zerg/public/images',
    'token_expire_in' => 7200
];
```


## 访问自定义应用配置

### `api/model/BaseModel.php`

```php
<?php

namespace app\api\model;


use think\Model;

class BaseModel extends Model
{
    protected function prefixImgUrl ($value,$data){
        $finalUrl = $value;
        if($data['from'] == 1){
            $finalUrl = config('setting.img_prefix').$value;
        }
        return $finalUrl;
    }
}
```

> config 是 系统提供的函数


## 构建关联模型

### `api/model/Banner.php`

```php
<?php


namespace app\api\model;

class Banner extends BaseModel
{

    protected $hidden = ['update_time','delete_time'];

    public function items()
    {
        return $this->hasMany('BannerItem', 'banner_id', 'id');
    }


    public static function getBannerByID($id)
    {
        $banner = self::with(['items', 'items.img'])
            ->find($id);

        return $banner;
    }
}
```

### `api/model/BannerItem.php`

```php
<?php


namespace app\api\model;

class BannerItem extends BaseModel
{
    protected $hidden = ['id','img_id','banner_id','update_time','delete_time'];

    public function img(){
        return $this->belongsTo('Image','img_id','id');
    }
}
```

### `api/model/Image.php`

```php
<?php

namespace app\api\model;


class Image extends BaseModel
{
    protected $hidden = ['id', 'from', 'delete_time','update_time'];

    public function getUrlAttr($value, $data){
        return $this->prefixImgUrl($value, $data);
    }

}
```

## 将路由改为可指定版本号访问

### `route.php`

```php
<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2016 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

use think\Route;

//测试路由前缀  localhost/zerg/public/index.php

//Route::rule('hello', 'sample/Test/hello');

// localhost/zerg/public/index.php/hello
Route::get('hello', 'sample/Test/hello');


// localhost/zerg/public/index.php/api/v1/banner/1
Route::get('api/:version/banner/:id', 'api/:version.Banner/getBanner');
```


