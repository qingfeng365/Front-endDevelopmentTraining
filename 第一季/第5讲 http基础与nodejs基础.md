# 第5讲 http基础与nodejs基础

## 学习资源

- [Express 4.x API 中文手册](http://www.expressjs.com.cn/4x/api.html) 
- [Node.js 0.12.7 API 中文文档](http://www.nodeapp.cn/index.html)

## hello nodejs

hello.js

```js
console.log("Hello NodeJs!");
```

```bash
node hello
```

## 浏览器与服务器的请求与响应

### 发起请求

#### 整个过程简述

#### 时间线名词解释
- **Stalled:** 是浏览器得到要发出这个请求的指令，到请求可以发出的等待时间，一般是代理协商、以及等待可复用的TCP连接释放的时间，不包括DNS查询、建立TCP连接等时间等
- **DNS lookup:** 域名解析时间
- **Request sent:** 请求第一个字节发出前到最后一个字节发出后的时间，也就是上传时间
- **Waiting:** 请求发出后，到收到响应的第一个字节所花费的时间(Time To First Byte)
- **Content Download:** 收到响应的第一个字节，到接受完最后一个字节的时间，就是下载时间

### 路由

根据请求的URL，服务器提供不同的响应。**路由**就是将请求对应到请求处理程序（request handler）

请求处理程序处理之后，要提供响应内容（response）

### request 与 response

**request:** 请求头，请求内容，请求发送的cookies

**response:** 响应头，响应内容，收到响应的cookies

在响应头中，要包括 响应状态码和响应头的内容类型（content-type）



### 常见的状态码

### 常见的内容类型


## 主文件

常规命名: `app.js` `server.js` `index.js` `www`


## favicon.ico

浏览器会尝试读取 http://xyz.com:xxxx/favicon.ico

## URL知识

### 属性

http://localhost:8888/start?foo=bar&hello=world     

url.parse(string).query

url.parse(string).pathname

querystring(string)["foo"]

querystring(string)["hello"]


## 常用nodejs

console.dir(obj[, options])
