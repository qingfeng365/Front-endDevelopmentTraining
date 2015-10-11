# 第4讲 创建基于Express4的项目

从这一讲开始，我们将完成一个实战项目，该项目将从Express4的基础开始创建，并逐步迭代添加相关功能。从而完整讲述如何整合nodejs+Express4+MongoDB+gulp等框架，完成一个基本项目的开发。

而在后续阶段，还会进一步在前端应用AngularJs，实现目前流行的Mean全栈式开发：MongoDB+Express+Angular+nodejs

## 项目前期准备

在gitHub创建一个练习用的仓库，并克隆到本地。

[示例项目:CarShopDemoV1](https://github.com/qingfeng365/CarShopDemoV1)

## 项目目标

虚拟的一个精简版汽车商城，包括首页展示，汽车详情，后台列表，后台录入等功能。

## 初始化项目目录

> -- **server**
>> -- **models**  
>> -- **views**
>>> -- **include**  
>>> -- **pages**

> -- **client**
>> -- **js**  
>> -- **css**



目录说明:
server: 在nodejs环境下执行的代码或需要使用的文件
client: 在浏览器环境下执行的代码或需要使用的文件

## 使用npm初始化项目

在项目根目录下打开命令行窗口

```bash
npm init
```

前面一段话，提示会创建 `package.json` 文件

**注意:** 对于name有个规定，不能使用大写字母

## 安装后端模块

在项目根目录下打开命令行窗口

```bash
cnpm install express --save
```
```bash
cnpm install mongoose --save
```
```bash
cnpm install body-parser --save
```
```bash
cnpm install moment --save
```
```bash
cnpm install underscore --save
```

## 安装前端模块

### 先设置bower安装目录

如果没有指定设置，bower会默认在项目目录下创建bower_components目录，现在需要指定在client目录下创建。

在项目目录下新建文件 `.bowerrc` (注意没有文件名,只有扩展名)



## 设置git的忽略文件

对node_modules目录设置忽略该目录下的一切

