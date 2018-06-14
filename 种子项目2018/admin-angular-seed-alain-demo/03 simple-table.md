# 03 simple-table

## 内部对记录属性的约定

checked | boolean : 选择框或单选框状态值
disabled | boolean : 选择框或单选框 disabled 值

目前没有提供指定别名功能,因此尽量不使用 这两个名称做字段名

## 选择数据

内部对每页选择都单独有记录, 但在 事件中, 只返回 当前页所选的记录列表

