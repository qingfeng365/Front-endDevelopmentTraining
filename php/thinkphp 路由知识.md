# thinkphp 路由知识

## PATH_INFO 模式

格式为 

服务器/程序名/public/index.php/应用名/控制器名/方法名

如 

程序名 zerg
应用名 sample
控制器名 test
方法名 hello

路由为: `http://localhost/zerg/public/index.php/sample/test/hello`

`/application/sample/controller/Test.php`

```php
<?php

namespace app\sample\controller;


class Test
{
    public function hello(){
        return 'hello, world';
    }
}
```

## 自定义路由 (动态注册)

`/application/route.php`

路由: `http://localhost/zerg/public/index.php/hello`

```php
<?php

use think\Route;

Route::rule('hello', 'sample/Test/hello');

```

定义路由 就 不能使用 PATH_INFO 模式

完整格式:

Route::rule(路由表达式, 路由地址, 请求类型, 路由参数数组, 变量规则数组)

- 请求类型: GET POST DELETE PUT * , 默认为 *
- 多个请求类型:  'GET|POST'
- 路由参数数组: 见开发文档
- 变量规则数组: 见开发文档

快捷方法:

Route::get(路由表达式, 路由地址);
Route::post(路由表达式, 路由地址);
Route::any(路由表达式, 路由地址);
...

## 路由参数

路由参数: 　:参数名称

