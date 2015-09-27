# 第3讲 Jade模板引擎与BootStrap4.md

## 目标

练习Jade模板引擎，同时学习使用最新的BootStrap4。

## 创建练习项目目录

目录层级结构如下:

> 前端培训练习
>> 第一季
>>> 第3讲 bootstrap4_jade
>>>> jade  
>>>> html  
>>>> css  

### 安装全局jade

```bash
cnpm install -g jade
```

安装后测试

```bash
jade --version
```

### jade 命令行工具

```bash
使用: jade [options] [dir|file ...]

选项:

  -h, --help         输出帮助信息
  -v, --version      输出版本号
  -o, --out <dir>    输出编译后的 HTML 到 <dir>
  -O, --obj <str>    JavaScript 选项
  -p, --path <path>  在处理 stdio 时，查找包含文件时的查找路径
  -P, --pretty       格式化 HTML 输出
  -c, --client       编译浏览器端可用的 runtime.js
  -D, --no-debug     关闭编译的调试选项(函数会更小)
  -w, --watch        监视文件改变自动刷新编译结果

Examples:

  # 编译整个目录
  $ jade templates

  # 生成 {foo,bar}.html
  $ jade {foo,bar}.jade

  # 在标准IO下使用jade 
  $ jade < my.jade > my.html

  # 在标准IO下使用jade, 同时指定用于查找包含的文件
  $ jade < my.jade -p my.jade > my.html

  # 在标准IO下使用jade 
  $ echo "h1 Jade!" | jade

  # foo, bar 目录渲染到 /tmp
  $ jade foo bar --out /tmp 

```





