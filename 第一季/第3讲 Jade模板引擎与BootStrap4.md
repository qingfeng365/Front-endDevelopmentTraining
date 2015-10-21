# 第3讲 Jade模板引擎与BootStrap4

tags: Jade模板 BootStrap

[TOC]

## 目标

练习Jade模板引擎，同时学习使用最新的BootStrap4。

## 学习资源
- [jade语法简明示例](http://naltatis.github.io/jade-syntax-docs/#id-classes) 虽然是英文，但强烈推荐
- [jade模板引擎中文教程](http://wenku.baidu.com/link?url=MIUNmpHhoCFnNarWNOx_Pi6jbuZ167hAko76dTmtFLGXpG2GBZsy2ERr2Wf4b3Hif2IqLp-L7WjVpXL06oo1oenwA1HJMX4JhDevojASRfW)
 
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
### 在项目目录安装 bootstrap 4

#### 初始化bower

在项目目录下，右键菜单`git bash`，进入命令行窗口，先初始化bower

```bash
bower init
```
name: 项目名称
version: 版本号
authors: 作者
moduleType: AMD
keywords: 关键字
license: MIT

初始化，当前目录会出现 `bower.json` 文件

#### 安装bootstrap 4

```bash
bower install bootstrap#4.0.0-alpha --save
```

> 因bootstrap 4还没有正式版发布，因此需要额外指定版本号，bower指定版本号是用 `#`

安装bootstrap后，jquery也自动装好了。

#### 在SourceTree设置忽略文件

打开SourceTree，选择属于 `bower_components` 目录下任一文件，点击文件右侧的 `...` 小按钮，选择`忽略下面的一切`，并指向 `bower_components` 目录

> 在项目根目录下的 bower.json 不要忽略，要做正常的版本管理
> 做完忽略后，在项目根目录下会新增 `.gitignore`，这个文件也是需要做正常的版本管理的
#### `bower.json`的作用

```bash
bower install
```

自动根据 `bower.json` 的 dependencies 一节定义安装项目依赖包。


#### 全局安装jade-lint

```bash
cnpm install -g jade-lint
```

#### 安装sublime插件

- SublimeLinter-contrib-jade-lint : jade语法检查插件
- jade : jade语法高亮
- Jade Snippets : jade代码片断

可选安装的插件（对Bootstrap 3很熟悉后才安装，初学者不要安装）
 
`Bootstrap 3 Jade Snippets`

#### 修改sublime用户配置

**Preferences/设置 - 用户**

Preferences.sublime-settings

增加两项
```js
  "tab_size": 2,
  "translate_tabs_to_spaces": false
```
保存关闭即可

或参照第1讲的附件:[第1讲 Sublime Text 3 用户配置和插件配置.md](https://github.com/qingfeng365/Front-endDevelopmentTraining/blob/master/%E7%AC%AC%E4%B8%80%E5%AD%A3/%E9%99%84%E4%BB%B6/%E7%AC%AC1%E8%AE%B2%20Sublime%20Text%203%20%E7%94%A8%E6%88%B7%E9%85%8D%E7%BD%AE%E5%92%8C%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE.md)

## 练习1 

### 新建jade文件

在 `jade` 目录新建文件 `demo1.jade`

> 如果已经设置了配置文件，就不需要做以下动作：
> 在sublime菜单`查看/缩进`，选择`选项卡宽度:2`
 
```jade
doctype
html
  head
    meta(charset="utf-8")
    title jade演示一
  body
    h1 hollo jade!
```

- 注意缩进使用TAB键缩进(需要做上面的设置)，同时不要一会用TAB键缩进，一会用空格缩进。
- 建议总是用TAB键缩进
- `doctype` 表示html5
- `html` 必须为顶级节点，其它节点根据层级，每级单独一行，每级缩进2格(一个TAB)
- 文本与元素标签之间要空1格
- 属性在标签后面用括号`(key1="value1", key2="value2")`

在项目目录，打开命令行窗口，将`jade`编译成`html`

```bash
jade jade/demo1.jade --out html
```
表示将 jade目录下的demo1.jade，编译并输出到 html 目录下的 demo1.html

默认是采用压缩格式，可改用以下命令

```bash
jade jade/demo1.jade --out html -P
```

> *注意:*  P为大写

继续改用以下命令

```bash
jade jade/demo1.jade --out html -P -w
```

jade jade/pages/index.jade --out html -P -w
> *注意:*  w为小写

用sublime设置两列布局，左边打开demo1.jade，右边打开demo1.html。
修改demo1.jade并保存，可实时查看demo1.html变化。



## 正式练习

### 准备工作 

- 在jade目录新建下级目录

> jade
>> include  
>> pages

- 在jade目录新建`layout.jade`

```jade
doctype
html
  head
    meta(charset="utf-8")
    title bootstrap 4 一览
  body
    block content
```

- 在pages目录下新建`index.jade`

```jade
extends ../layout.jade

block content
  .container
    h1 bootstrap 4 效果展示
```

#### jade 监听命令

在项目目录的命令行窗口输入:

```bash
jade jade/pages/index.jade --out html -P -w
```
- 在`include`目录新建`head.jade` 

```jade
meta(name="viewport", content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no")
meta(http-equiv="X-UA-Compatible", content="IE=edge")
link(href="../bower_components/bootstrap/dist/css/bootstrap.min.css", rel="stylesheet")
```

- 在`include`目录新建`foot.jade`

```
script(src="../bower_components/jquery/dist/jquery.min.js")  
script(src="../bower_components/bootstrap/dist/js/bootstrap.min.js")  
```
- 修改layout.jade

```jade
doctype
html
  head
    meta(charset="utf-8")
    title bootstrap 4 一览
    include ./include/head.jade
  body
    block content
    include ./include/foot.jade
```

完成以上处理后，`index.html`内容如下

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>bootstrap 4 一览</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">
      <h1 class="text-center">bootstrap 4 效果展示</h1>
    </div>
    <script src="../bower_components/jquery/dist/jquery.min.js"> </script>
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"> </script>
  </body>
</html>
```

当前目录结构如下:

> jade  
>> layout.jade  
>> include  
>>> head.jade  
>>> foot.jade 
 
>> pages  
>>> index.jade
  
> html  
>> index.html  

### 练习: 

[Bootstrap4_Jade_Demo](https://github.com/qingfeng365/Bootstrap4_Jade_Demo)

## 如何在模板中使用Markdown语法

### 安装 `jstransformer-markdown-it`

打开命令行窗口：

```bash
cnpm install -g jstransformer-markdown-it
```

### 在模板中使用 `:markdown-it`
在jade模板中使用 
```jade
  :markdown-it
    ```
    ul.nav
      li.nav-item
        a.nav-link(href="#") 链接1
      li.nav-item
        a.nav-link.active(href="#") 活跃链接2
      li.nav-item
        a.nav-link(href="#") 链接3
      li.nav-item
        a.nav-link.disabled(href="#") 无效链接4
    ```
```



