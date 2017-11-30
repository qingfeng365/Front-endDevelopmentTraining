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

如: `Route::get('banner/:id', 'api/v1.Banner/getBanner');`

## 自定义验证器

```php
<?php

namespace app\api\validate;


use think\Validate;

class TestValidate extends Validate
{
    protected $rule = [
        'name' => 'require|max:10',
        'email' => 'email'
    ];
}
```
> 系统内置的验证规则:
> [https://www.kancloud.cn/manual/thinkphp5/129356](https://www.kancloud.cn/manual/thinkphp5/129356)


## 调用验证器

```php
<?php

namespace app\api\controller\v1;

use app\api\validate\TestValidate;

class Banner
{
    /**
     * 获取指定id的banner信息
     * @url /banner/:id
     * @http GET
     * @id banner的id号
     *
     */
    public function getBanner($id)
    {
//        输出
//        echo '{"ok":1}';

    //    验证器示例
       $state = [
           'name' => '32422sdfsdfsdfsdf',
           'email' => 'emailasa.com'
       ];

       $validdate = new TestValidate();

       $result = $validdate->batch()->check($state);

       var_dump($validdate->getError());

    }

}
```

输出内容:

```
array(2) { ["name"]=> string(25) "name长度不能超过 10" ["email"]=> string(17) "email格式不符" }
```

> 
> var_dump() 能打印出类型
print_r() 只能打出值
echo() 是正常输出...
需要精确调试的时候用 var_dump();
一般查看的时候用 print_r()
另外 ， echo不能显示数组  其余2个可以...

## 自定义验证规则函数完整示例

`/application/api/validate/IDMustBePostiveInt.php`

```php
<?php

namespace app\api\validate;

use think\Validate;

class IDMustBePostiveInt extends Validate
{
    protected $rule = [
        'id' => 'require|isPositiveInteger',
    ];


    protected $message=[
        'id' => 'id必须是正整数'
    ];

    protected function isPositiveInteger(
        $value, $rule = '',
        $data = '', $field = '')
    {
        if (is_numeric($value) && is_int($value + 0) && ($value + 0) > 0)
        {
            return true;
        }
        else
        {
            return false;
            //            return $field.'必须是正整数';
        }
    }
}
```

- $value: 要检查的值
- $rule: 目前要检查的规则
- $data:　完整的数据块（即所有传进来的参数集）
- $field: 要检查值的属性名
