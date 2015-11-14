# 第2讲 使用express初始化项目

<!-- MarkdownTOC -->

- [安装express命令行工具][安装express命令行工具]
- [在github创建一个练习项目][在github创建一个练习项目]
- [使用express初始化项目][使用express初始化项目]
- [生成代码讲解][生成代码讲解]

<!-- /MarkdownTOC -->


<a name="安装express命令行工具"></a>
## 安装express命令行工具

```bash
cnpm install -g express-generator
```

<a name="在github创建一个练习项目"></a>
## 在github创建一个练习项目

创建项目`express_build_demo`,并克隆到本地

<a name="使用express初始化项目"></a>
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
		
		```bash
		export DEBUG=express_build_demo:* & npm start
		```

<a name="生成代码讲解"></a>
## 生成代码讲解






