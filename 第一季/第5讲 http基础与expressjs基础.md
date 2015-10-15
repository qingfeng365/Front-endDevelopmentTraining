# 第5讲 http基础与expressjs基础

tags: http Expressjs 

[TOC]

## 有关第4讲的重要更新

因为`modelName`是 `mongoose.Schema`的保留字，因此要将`modelName`改为`carModelName`

## 学习资源

- [Express 4.x API 中文手册](http://www.expressjs.com.cn/4x/api.html) 
- [Node.js 0.12.7 API 中文文档](http://www.nodeapp.cn/index.html)

## nodejs

### 主文件
常规命名: `app.js` `server.js` `index.js` `www`

### favicon.ico
浏览器会尝试读取 http://xyz.com:xxxx/favicon.ico


## HTTP基础

### 浏览器与服务器的请求与响应

#### 整个过程简述

[ 浏览器与服务器响应流程 ](http://wenku.baidu.com/link?url=N4YdqJcanw-_5KWbwH29cIWe0H1KUxY9pRQYNgVBJ5CtJNKQzl8boSz6wkKtKT4vY34MU1houHRxkbt-y4j-iZfVor6v9BDToC8t7oPQ1Ei)

#### 时间线名词解释

通过谷歌浏览器的开发者工具查看时间线

- **Stalled:** 是浏览器得到要发出这个请求的指令，到请求可以发出的等待时间，一般是代理协商、以及等待可复用的TCP连接释放的时间，不包括DNS查询、建立TCP连接等时间等
- **DNS lookup:** 域名解析时间
- **Request sent:** 请求第一个字节发出前到最后一个字节发出后的时间，也就是上传时间
- **Waiting:** 请求发出后，到收到响应的第一个字节所花费的时间(Time To First Byte)
- **Content Download:** 收到响应的第一个字节，到接受完最后一个字节的时间，就是下载时间


### request 与 response

**request:** 请求头，请求内容，请求发送的cookies

**response:** 响应头，响应内容，收到响应的cookies

在响应头中，要包括 响应状态码和响应头的内容类型（content-type）

### 常见的状态码

- 200 : OK
- 300 : 重定向
- 304 : 所请求的资源未修改，不返回任何资源
- 400 : 客户端请求的语法错误，服务器无法理解
- 401 : 请求要求用户的身份认证
- 403 : 服务器理解请求客户端的请求，但是拒绝执行此请求
- 404 : Not Found
- 500 : Internal Server Error 服务器内部错误，无法完成请求
- 503 : Bad Gateway 充当网关或代理的服务器，从远端服务器接收到了一个无效的请求

[HTTP状态码对照表](http://tools.jb51.net/table/http_status_code)

### 常见的内容类型

[HTTP Content-type 对照表](http://tools.jb51.net/table/http_content_type)

### URL格式

百度百科:

协议://用户名:密码@子域名.域名.顶级域名:端口号/目录/文件名.文件后缀?参数=值&参数1=值1#标志

[node api : url](http://www.nodeapp.cn/url.html)

    href: 准备解析的完整的 URL，包含协议和主机（小写）。

    例子： 'http://user:pass@host.com:8080/p/a/t/h?query=string#hash'

    protocol: 请求协议, 小写.

    例子： 'http:'

    slashes: 协议要求的斜杠（冒号后）

    例子： true 或 false

    host: 完整的 URL 小写 主机部分，包含端口信息。

    例子： 'host.com:8080'

    auth: url 中的验证信息。

    例子： 'user:pass'

    hostname: 域名中的小写主机名

    例子： 'host.com'

    port: 主机的端口号

    例子： '8080'

    pathname: URL 中的路径部分，在主机名后，查询字符前，包含第一个斜杠。

    例子： '/p/a/t/h'

    search: URL 中得查询字符串，包含开头的问号

    例子： '?query=string'

    path: pathname 和 search 连在一起

    例子： '/p/a/t/h?query=string'

    query: 查询字符串中得参数部分，或者使用 querystring.parse() 解析后返回的对象。

    例子： 'query=string' or {'query':'string'}

    hash: URL 的 “#” 后面部分（包括 # 符号）

    例子： '#hash'


路径

查询串



## expressjs基础

### 路由

根据请求的URL路径，服务器提供不同的响应。

**路由:** 就是将路径请求对应到请求处理程序（request handler）,请求处理程序处理之后，要提供响应内容（response）

路由定义方式

- **静态字符串**
- **参数字符串**
- 字符串模式串   
- 正则表达式

### 请求方法


#### req.params

An object containing properties mapped to the named route “parameters”. For example, if you have the route /user/:name, then the “name” property is available as req.params.name. This object defaults to {}.

```js
// GET /user/tj
req.params.name
// => "tj"
```
When you use a regular expression for the route definition, capture groups are provided in the array using req.params[n], where n is the nth capture group. This rule is applied to unnamed wild card matches with string routes such as /file/*:

```js
// GET /file/javascripts/jquery.js
req.params[0]
// => "javascripts/jquery.js"
```


#### req.query

An object containing a property for each query string parameter in the route. If there is no query string, it is the empty object, {}.

```js
// GET /search?q=tobi+ferret
req.query.q
// => "tobi ferret"

// GET /shoes?order=desc&shoe[color]=blue&shoe[type]=converse
req.query.order
// => "desc"

req.query.shoe.color
// => "blue"

req.query.shoe.type
// => "converse"
```


#### req.route

The currently-matched route, a string. For example:

```js
app.get('/user/:id?', function userIdHandler(req, res) {
  console.log(req.route);
  res.send('GET');
})
```

Example output from the previous snippet:

```js
{ path: '/user/:id?',
  stack:
   [ { handle: [Function: userIdHandler],
       name: 'userIdHandler',
       params: undefined,
       path: undefined,
       keys: [],
       regexp: /^\/?$/i,
       method: 'get' } ],
  methods: { get: true } }
```

### 响应方法

|- 方法 | 描述
|-  res.download() | 提示下载文件。
|-  res.end() |  终结响应处理流程。
|-  res.json() | 发送一个 JSON 格式的响应。
|-  res.jsonp() |    发送一个支持 JSONP 的 JSON 格式的响应。
|-  res.redirect() | 重定向请求。
|-  res.render()   | 渲染视图模板。
|-  res.send() | 发送各种类型的响应。
|-  res.sendFile  |  以八位字节流的形式发送文件。
|-  res.sendStatus() |   设置响应状态代码，并将其以字符串形式作为响应体的一部分发送。


### 扩展阅读

[express 路由指南](http://www.expressjs.com.cn/guide/routing.html)

## 示例

新建文件 `demo_route.js`

```js
var express = require('express');
var port = 4000;
var app = express();

app.listen(port);
console.log("路由测试服务已启动,监听端口号:" + port);

var showRoute = function(req, res) {
  console.log('================');
  console.log('当前生效的路由规则:');
  console.log(req.route.path);
  console.log('');

  console.log('req.route.methods');
  console.log(req.route.methods);
  console.log(''); 

  console.log('req.originalUrl');
  console.log(req.originalUrl);
  console.log(''); 

  console.log('req.body');
  console.log(req.body);
  console.log('');  

  console.log('req.cookies');
  console.log(req.cookies);
  console.log('');  


  console.log('req.hostname');
  console.log(req.hostname);
  console.log('');  


  console.log('req.ip');
  console.log(req.ip);
  console.log(''); 


  console.log('req.params');
  console.log(req.params);
  console.log(''); 

  console.log('req.path');
  console.log(req.path);
  console.log(''); 

  console.log('req.protocol');
  console.log(req.protocol);
  console.log(''); 

  console.log('req.query');
  console.log(req.query);
  console.log(''); 

  res.sendStatus(200);
};

app.get('/',showRoute);

/**
 *
 * http://localhost:4000/user/100/xyz/abcd?a=1&b=2&c[id]=100&c[name]=xxxx
 *
 * http://localhost:4000/user/100/xyz/abcd?a=1&b=2&c[id][a]=100&c[id][b]=xxxx
 */


app.get('/user/:id/xyz/:name',showRoute);
app.get('/user/:id/:name',showRoute);

app.get('/user/:id::name',showRoute);
app.get('/user/:id,:name',showRoute);
app.get('/user/:id;:name',showRoute);

app.get('/user/:id&:name',showRoute);
app.get('/user/:id-:name',showRoute);
app.get('/user/:id=:name',showRoute);

/**
 * 这种情况是有意义的, 适用于
 *
 * http://localhost:4000/user/1
 * http://localhost:4000/user/
 * 两种情况
 */
app.get('/user/:id?',showRoute);

/**
 * ? * + | 在多参数时不要使用 
 */
app.get('/user/:id,:name?',showRoute);
app.get('/user/:id,:name+',showRoute);
app.get('/user/:id,:name*',showRoute);

app.get('/user/:id|:name',showRoute);
app.get('/user/:id+:name',showRoute);
app.get('/user/:id?:name',showRoute);
app.get('/user/:id*:name',showRoute);

/**
 *
 * http://localhost:4000/abc/xyz
 * 
 */

app.get('/*',showRoute);
```
## express 访问日志中间件

安装 `morgan` 中间件

在第4讲的项目根目录，打开命令行窗口：

```bash
cnpm install morgan
```
修改 `app.js`

在下面这行代码之前插入:

> 'app.use(express.static(path.join(__dirname, '../client')));'

```js
var morgan = require('morgan');
app.use(morgan('dev'));
```

## 设置express模板渲染友好格式

修改 `app.js`, 加入一行代码:

```js
app.locals.pretty = true;
```





