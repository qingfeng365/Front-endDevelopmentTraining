# 第6讲 使用MongoDB存储项目数据

## 学习资源

- [mongoose官网](http://mongoosejs.com/index.html)
- [MongoDB 2.6 中文文档](http://docs.mongoing.com/manual-zh/index.html)

## mongoose基本概念

- **Schema** 用户定义的文档结构及方法
- **Model**  模型，本质上是mongoose根据Schema的定义封装的一个新对象，这个对象即包含Schema定义的方法，也包含mongoose扩展的方法
- **Document** 文档实例对象(Entity)，通过模型可以将普通对象，如一个字面量对象，封装成文档实例对象，该对象可通过mongoose扩展的方法进行数据库操作

命名约定

```js
xxxSchema
xxxModel
xxxDoc
```


