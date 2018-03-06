# 02 ipython使用方法

## 帮助

?

输入 ? 可获得帮助

## [tab] 键自动补全

## 内省

在变量的前面或后面加上一个问号(?)就可以将有关该对象的一些通用信息显示出来。这就叫做对象的内省。

如果对象是一个函数或实例方法，则其docstring也会被现实出来。

使用??还将显示出该函数的源代码。


一些字符串配以通配符(*)即可显示出所有与该通配符表达式相匹配的名称。

例如，我们可以列出Numpy顶级命名空间中含有“load”的所有函数

```
import numpy as np

np.*load*?
```

## 中断

^C

## 

## %run 运行代码文件

这是一个magic命令, 能把你的脚本里面的代码运行, 并且把对应的运行结果存入ipython的环境变量中:

如:

```py
$ cat t.py
# coding=utf-8
l = range(5)

$ ipython
In [1]: %run t.py # `%`可加可不加
In [2]: l # 这个l本来是t.py里面的变量, 这里直接可以使用了
Out[2]: [0, 1, 2, 3, 4]
```

## 参考 

[https://www.cnblogs.com/zzhzhao/p/5295476.html](https://www.cnblogs.com/zzhzhao/p/5295476.html)