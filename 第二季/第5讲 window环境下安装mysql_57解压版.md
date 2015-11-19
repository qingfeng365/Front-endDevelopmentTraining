# 第5讲 window环境下安装mysql V5.7解压版

## 官网下载地址

<http://dev.mysql.com/downloads/mysql/>

- Windows (x86, 32-bit), ZIP Archive
- Windows (x86, 64-bit), ZIP Archive

## 创建mysql程序目录

```
c:\mysql
```

## 创建mysql数据存储目录

```
d:\mysqldata\data
```
## 解压后的程序

将解压后的程序目录（bin目录的父目录）拷到`mysql`目录

## 设置环境变量

将 `c:\mysql\mysql-5.7.9-winx64\bin;`添加到`path`环境变量中。

（32位程序注意路径名）

## 创建`my.ini`

在（bin目录的父目录）如：

`c:\mysql\mysql-5.7.9-winx64`

创建文件 `my.ini`

内容如下：

```ini
[mysqld]

basedir = "c:\mysql\mysql-5.7.9-winx64"
datadir = "d:\mysqldata\data"
port = 3306
```

## 安装服务

**注意**

- 使用windows的命令行窗口
- 使用管理员身份
- 为防止环境变量没有生效，在命令行窗口切换到`bin`目录，或者重启机器

```bash
mysqld -install
```

## 初始化数据库

**注意：以下步骤非常重要**

- 在命令行窗口切换到`d:\mysqldata\data`目录

- 注意一定要在`data`目录下执行下面的命令


```bash
mysqld --initialize --user=mysql 
```

如果`mysqld`不存在，重启机器

## 保存初始密码（重要）

- 初始化完成后，在`data`目录下查找下面文件

`<当前机器名>.err`

找到这一行：

```
A temporary password is generated for root@localhost: xxxxxxx
```

将这一行复制下来保存到另外一个文件。

这是系统自动生成的超级用户（root）初始密码

## 启动mysql服务

在windows服务管理器，找到 `MySql`服务,执行启动。

注意：要初始化完成后，才能启动服务。


## 登录mysql

在命令行窗口

```bash
mysql -uroot -p
```

执行命令后，输入初始密码

在提示符输入`exit;`退出
```
mysql> exit;
```

## 修改root密码（重要）

初始密码没有修改前，不能做任何操作。

- 在命令行窗口，用初始密码完成登录

```bash
mysql -uroot -p
```

在提示符输入
```
mysql> set password for root@localhost = password("新密码");
```

注意：

- 命令要加分号
- 密码要用引号括起

在提示符输入`exit;`退出
```
mysql> exit;
```

在命令行窗口，再用新密码登录：

```bash
mysql -uroot -p
```

## 安装可视化工具 


- 关闭全部杀毒软件（重要）

按说明安装，并执行补丁程序即可



