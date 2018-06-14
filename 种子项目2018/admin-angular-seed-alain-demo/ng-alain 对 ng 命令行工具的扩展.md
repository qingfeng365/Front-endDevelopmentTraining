# ng-alain 对 ng 命令行工具的扩展

ng g ng-alain:<类型> 名称 参数

注意, 根据测试, 要在 routes 目录下的终端窗口执行, 生成的目录才是对的

类型:

- module 模块
- list   列表页
- edit   编辑页
- view   查看页
- curd   查增改成组页面


## 创建业务模块


```

创建 myform 模块

ng g ng-alain:module myform

```

注意, 创建业务模块后,要在路由 `routes-routing.module.ts` 中

配置懒加载

## 创建 业务页面

要使用参数指定所在模块 -m myform

### 编辑页 (模态动态表单)

注意: 如果要生成普通页面, 就不要使用 ng-alain 扩展

用 ng g c 命令即可

```

创建 编辑页

ng g ng-alain:edit basicForm -m myform

```
