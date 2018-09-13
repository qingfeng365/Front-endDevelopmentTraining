## 安装 nodejs

参考来源：[Linux系统（Centos）下安装nodejs并配置环境](https://blog.csdn.net/qq_21794603/article/details/68067821)

访问[nodejs 官网下载地址](https://nodejs.org/en/download/current)，复制Linux Binaries (x64)的链接地址，如：https://nodejs.org/dist/v10.4.1/node-v10.4.1-linux-x64.tar.xz

以root用户身份登录CentOS，在终端中打开文件夹：/usr/local/src

下载安装文件  
```
wget https://nodejs.org/dist/v10.4.1/node-v10.4.1-linux-x64.tar.xz
```

使用xz转换安装文件，将node-v10.4.1-linux-x64.tar.xz转换成node-v10.4.1-linux-x64.tar  
```
xz -d node-v10.4.1-linux-x64.tar.xz
```

解压安装文件  
```
tar -xf node-v10.4.1-linux-x64.tar
```

将解压的文件夹改名，并删除安装源文件node-v10.4.1-linux-x64.tar  
```
mv node-v10.4.1-linux-x64 node-v10.4.1
rm node-v10.4.1-linux-x64.tar
```

创建链接，便于全局全局使用node、npm命令  
```
ln -s /usr/local/src/node-v10.4.1/bin/node /usr/local/bin/node
ln -s /usr/local/src/node-v10.4.1/bin/npm /usr/local/bin/npm
```

检查版本，能正确显示版本号表示安装成功  
```
node -v
npm -v
```

全局安装yarn  
```
npm -g install yarn
```

为yarn创建链接，便于全局使用  
```
ln -s /usr/local/src/node-v10.4.1/bin/yarn /usr/local/bin/yarn
```

检查版本，能正确显示版本号表示安装成功  
```
yarn -v
```  

## 安装 Redis

参考来源：[CentOS下Redis的安装](https://www.cnblogs.com/renzhicai/p/7773080.html)

### 安装

从 [Redis 网站](https://redis.io/)得知目前的正式版本是4.0.10，下载地址是：http://download.redis.io/releases/redis-4.0.10.tar.gz

以root用户身份登录CentOS，在终端中打开文件夹：/usr/local/src

下载安装文件  
```
wget http://download.redis.io/releases/redis-4.0.10.tar.gz
```

解压安装文件  
```
tar -xzf redis-4.0.10.tar.gz
```

删除安装源文件redis-4.0.10.tar.gz  
```
rm redis-4.0.10.tar.gz
```

进入解压后的文件夹  
```
cd redis-4.0.10
```

安装  
```
make
make install
```

直接启动服务
```
redis-server
```

### 通过初始化脚本启动Redis

将初始化脚本文件redis_init_script复制到/etc/init.d中，并改名为redis_6379
```
cp utils/redis_init_script /etc/init.d/redis_6379
```

创建Redis的配置文件夹
```
mkdir /etc/redis
```

创建Redis的持久化文件夹
```
mkdir /var/redis
mkdir /var/redis/6379
```

复制配置文件模板（redis-4.0.2/redis.conf）到/etc/redis 目录中，并命名为6379.conf”）
```
cp redis.conf /etc/redis/6379.conf
```
修改配置文件/etc/redis/6379.conf，根据环境不同，编辑器可能是pluma或者vi或者其他
```
gedit /etc/redis/6379.conf
```
[vi命令详解](http://man.linuxde.net/vi)

要修改的内容见下表

|参数|值|说明|
|:------|:------|:------|
|daemonize|yes|使Redis以守护进程模式运行，默认no|
|pidfile|/var/run/redis_6379.pid|设置Redis的PID文件位置，默认/var/run/redis_6379.pid|
|port|6379|设置Redis监听的端口号，默认6379|
|dir|/var/redis/6379|设置持久化文件存放位置，默认./|
|protected-mode|no|设置保护模式，默认yes|
|requirepass|自行设置|设置密码，默认未生效(被注释掉了，生效要去掉前面的#)，默认密码foobared|

测试启动服务
```
/etc/init.d/redis_6379 start
```

停止服务

测试用`/etc/init.d/redis_6379 stop`是无法停止服务的，要用`kill`命令停止

先用`ps -A`找出`redis-server`的进程ID，比如：29307
```
kill 29307
```

让Redis随系统自动启动，这还需要对Redis初始化脚本进行简单修改
```
gedit /etc/init.d/redis_6379
```
在打开的redis初始化脚本文件头部第四行的位置，追加下面两句
```
# chkconfig: 2345 90 10 
# description: Redis is a persistent key-value database
```
追加后效果如下
```
#!/bin/sh
#
# Simple Redis init.d script conceived to work on Linux systems
# chkconfig: 2345 90 10 
# description: Redis is a persistent key-value database
# as it does use of the /proc filesystem.

### BEGIN INIT INFO
# Provides:     redis_6379
# Default-Start:        2 3 4 5
# Default-Stop:         0 1 6
# Short-Description:    Redis data structure server
# Description:          Redis data structure server. See https://redis.io
### END INIT INFO

REDISPORT=6379
EXEC=/usr/local/bin/redis-server
CLIEXEC=/usr/local/bin/redis-cli

......
```

将Redis加入系统启动项里
```
chkconfig redis_6379 on
```

通过上面的操作后，以后也可以直接用下面的命令对Redis进行启动
```
service redis_6379 start
```
要停止Redis，请按前面的说明使用kill命令结束

## 安装 Nginx

参考来源：[linux系统部署Nginx](https://blog.csdn.net/qq314499182/article/details/79117836)

### 安装

安装Nginx之前首先必须安装以下三个依赖的包，注意安装顺序如下：

1. SSL功能需要openssl库
2. gzip模块需要zlib库
3. rewrite模块需要pcre库

```
yum install openssl
yum install zlib
yum install pcre
```

使用yum安装nginx需要包括Nginx的库，安装Nginx的库
```
rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
```

使用下面命令安装nginx
```
yum install nginx
```

启动Nginx
```
service nginx start
```

停止Nginx
```
service nginx stop
```

直接浏览器访问`http://localhost/`  
出现Nginx的欢迎界面表示安装成功了  
否则就是安装失败了

### 配置Nginx

CentOS安装Nginx后，安装在了`/etc/nginx/`目录下
可以打开`/etc/nginx/conf.d/default.conf`查看里面的配置，包括监听端口，域名和nginx访问的根目录

```
gedit /etc/nginx/conf.d/default.conf
```

配置示例：
```
    server {
        listen       80;
        listen       [::]:80;
        server_name  test1.aabbcc.iego.cn;
        root         /usr/share/nginx/html;

        location / {
          proxy_pass http://127.0.0.1:4400;
          proxy_set_header Host $host;
          proxy_set_header X-Forwarded-Host $server_name;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
          set $Real $http_x_forwarded_for;
          if ( $Real ~ (\d+)\.(\d+)\.(\d+)\.(\d+),(.*) ){
            set $Real $1.$2.$3.$4;
          }
          proxy_set_header X-Real-IP $Real; 
        }

        error_page 404 /404.html;
            location = /40x.html {
        }

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
        }
    }  

    server {
        listen       80;
        listen       [::]:80;
        server_name  test2.aabbcc.iego.cn;
        root         /usr/share/nginx/html;

        location / {
          proxy_pass http://127.0.0.1:5010;
          proxy_set_header Host $host;
          proxy_set_header X-Forwarded-Host $server_name;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
          set $Real $http_x_forwarded_for;
          if ( $Real ~ (\d+)\.(\d+)\.(\d+)\.(\d+),(.*) ){
            set $Real $1.$2.$3.$4;
          }
          proxy_set_header X-Real-IP $Real; 
        }

        error_page 404 /404.html;
            location = /40x.html {
        }

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
        }
    }

    server {
        listen       4400;
        listen       [::]:4400;
        server_name  127.0.0.1;
        root         /usr/local/website/ykmobile-web-bi/dist;

            
        try_files $uri $uri/ /index.html;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;
    } 
```

使修改后配置生效
```
nginx -s reload
```