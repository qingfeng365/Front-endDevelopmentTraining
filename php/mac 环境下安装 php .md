
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