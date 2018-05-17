# 初始化项目踩过的坑

## 报 Cannot redeclare block-scoped variable 'ngDevMode' 的错误

原因是 ngx-ueditor 不能升级到 >= 2.0.0

ngx-ueditor 2 用的是 angular 6

要改用: 

"ngx-ueditor": "^1.1.0", 