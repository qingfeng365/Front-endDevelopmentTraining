# 第2讲 使用express初始化项目

<!-- MarkdownTOC -->

- [安装express命令行工具](#安装express命令行工具)
- [在github创建一个练习项目](#在github创建一个练习项目)
- [使用express初始化项目](#使用express初始化项目)
- [生成代码讲解](#生成代码讲解)
	- [先察看`package.json`](#先察看packagejson)
	- [入口文件`bin/www`](#入口文件binwww)
	- [debug模块](#debug模块)
	- [app.js 创建服务器实例](#appjs-创建服务器实例)

<!-- /MarkdownTOC -->

## 安装express命令行工具

```bash
cnpm install -g express-generator
```


## 在github创建一个练习项目

创建项目`express_build_demo`,并克隆到本地


## 使用express初始化项目

项目根目录命令行窗口

```bash
express
```

运行后,提示以下内容,输入"Y"

> destination is not empty, continue? [y/N] 

运行后,提示以下内容:

> install dependencies:  
> 		$ **cd . && npm install**
>
> run the app:  
> 		$ **DEBUG=express_build_demo:* npm start**

- 提示1: npm install 安装模块
- 提示2: 运行命令为 DEBUG=express_build_demo:* npm start(不适用于windows)

	windows命令应为 	
		
	- 如果是在cmd命令窗口
		
		```bash
		set DEBUG=express_build_demo:* & npm start
		```

	- 如果是在git bash命令窗口
	<div></div>

		```bash
		export DEBUG=express_build_demo:* 
		npm start
		```


## 生成代码讲解

### 先察看`package.json`

```js
{
  "name": "express_build_demo",
  "version": "0.0.0",
  "private": true,
  "scripts": {
    "start": "node ./bin/www"
  },
  "dependencies": {
    "body-parser": "~1.13.2",
    "cookie-parser": "~1.3.5",
    "debug": "~2.2.0",
    "express": "~4.13.1",
    "jade": "~1.11.0",
    "morgan": "~1.6.1",
    "serve-favicon": "~2.3.0"
  }
}
```

### 入口文件`bin/www`

```js
#!/usr/bin/env node

/**
 * Module dependencies.
 */

var app = require('../app');
var debug = require('debug')('express_build_demo:server');
var http = require('http');

/**
 * Get port from environment and store in Express.
 */

var port = normalizePort(process.env.PORT || '3000');
app.set('port', port);

/**
 * Create HTTP server.
 */

var server = http.createServer(app);

/**
 * Listen on provided port, on all network interfaces.
 */

server.listen(port);
server.on('error', onError);
server.on('listening', onListening);

/**
 * Normalize a port into a number, string, or false.
 */

function normalizePort(val) {
  var port = parseInt(val, 10);

  if (isNaN(port)) {
    // named pipe
    return val;
  }

  if (port >= 0) {
    // port number
    return port;
  }

  return false;
}

/**
 * Event listener for HTTP server "error" event.
 */

function onError(error) {
  if (error.syscall !== 'listen') {
    throw error;
  }

  var bind = typeof port === 'string'
    ? 'Pipe ' + port
    : 'Port ' + port;

  // handle specific listen errors with friendly messages
  switch (error.code) {
    case 'EACCES':
      console.error(bind + ' requires elevated privileges');
      process.exit(1);
      break;
    case 'EADDRINUSE':
      console.error(bind + ' is already in use');
      process.exit(1);
      break;
    default:
      throw error;
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */

function onListening() {
  var addr = server.address();
  var bind = typeof addr === 'string'
    ? 'pipe ' + addr
    : 'port ' + addr.port;
  debug('Listening on ' + bind);
}

```

入口文件与第一季`app.js`的区别

- 将环境初始化工作 与 启动监听过程 进一步分离
- 增加启动监听的处理过程

### debug模块

### app.js 创建服务器实例

部分代码如下：

```js
var routes = require('./routes/index');
var users = require('./routes/users');

app.use('/', routes);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

```

- 路由处理部分进一步按模块分离
- 路由按模块空间（路径前缀）进一步分离。
- 错误处理更严谨
	+ 404处理
	+ 开发环境下显示详情错误
	+ 普通环境下显示简要错误
	+ 有专用错误显示页面
	








