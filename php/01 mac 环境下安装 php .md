
## 使用 xampp 套件安装 

[https://www.apachefriends.org/download.html](https://www.apachefriends.org/download.html)

- 一般选用 5.6 版本

xampp 自动安装   Apache PHP mysql Perl 

安装的 mysql 的 root 用户密码为空

## 安装后使用 xampp 启动 Apache 和 mysql

## 命令行登录 mysql

```
cd  /Applications/xampp/bin

./mysql -uroot -p
```

## 命令行检查  php 

```
php -v
```

## 设置 mysql 命令行软链

```
sudo ln -s /applications/xampp/bin/mysql /usr/local/bin
```

这样就可在任意目录使用 mysql


## 下载 ThinkPHP 源码

应用项目：https://github.com/top-think/think
核心框架：https://github.com/top-think/framework

选 对应 tag  如: 5.0.7

将解压出来的两个文件夹拷到 /applications/xampp/htdocs

- 将 framework* 目录 剪切到 think* 目录
- 将 think* 目录改为 真实项目名
- 将 framework* 目录 改名为 thinkphp

在浏览器访问  localhost/真实项目名/public



## 资源

ThinkPHP5.0完全开发手册

[https://www.kancloud.cn/manual/thinkphp5/118003](https://www.kancloud.cn/manual/thinkphp5/118003)


## 常见问题

### 

### mkdir() premission denied

对 runtime 权限放开(不安全)

```
chmod -R 777 runtime
```


### 配置文件

`/application/config.php`

原始内容:

```php
    // 应用调试模式
    'app_debug'              => true,
```

新配置:

```php
    // 应用调试模式
    'app_debug'              => false,
```

原始内容:

```php
    // +----------------------------------------------------------------------
    // | 日志设置
    // +----------------------------------------------------------------------

    'log'                    => [
        // 日志记录方式，内置 file socket 支持扩展
        'type'  => 'File',
        // 日志保存目录
        'path'  => LOG_PATH,
        // 日志记录级别
        'level' => [],
    ],
```

新配置:

```php
    // +----------------------------------------------------------------------
    // | 日志设置
    // +----------------------------------------------------------------------

    'log'                    => [
        // 日志记录方式，内置 file socket 支持扩展
        'type'  => 'test',
        // 日志保存目录
        'path'  => LOG_PATH,
        // 日志记录级别
        'level' => ['sql'],
    ],
```

### 重新定义日志存放目录

`/public/index.php`

```php
<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006-2016 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

// [ 应用入口文件 ]

// 定义应用目录
define('APP_PATH', __DIR__ . '/../application/');
define('LOG_PATH', __DIR__ . '/../log/');

// 加载框架引导文件
require __DIR__ . '/../thinkphp/start.php';

```


