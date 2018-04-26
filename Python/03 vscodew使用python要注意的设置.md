# vscode 

## 如果使用 　pyenv　启用多版本管理

则需要在项目的 `.vscode` 目录

定义 `settings.json`


```
{
  "python.pythonPath": "/Users/macbook-hyf/.pyenv/versions/3.6.4/bin/python"
}
```

设置后可正常使用调试功能

## 安装 flake8 语法检查

```
pip install flake8
```

安装flake8成功后，打开VScode，文件->首选项->用户设置，在settings.json文件中输入"python.linting.flake8Enabled": true

放宽flake8的每一行最大字符限制

在用户设置文件中加上 

`"python.linting.flake8Args": ["--max-line-length=248"]`

## 安装  yapf 代码格式化

```
pip install yapf
```

安装yapf成功后，打开VScode，文件->首选项->用户设置，在settings.json文件中输入"python.formatting.provider": "yapf"



## 参考

[https://www.cnblogs.com/bloglkl/archive/2016/08/23/5797805.html](https://www.cnblogs.com/bloglkl/archive/2016/08/23/5797805.html)