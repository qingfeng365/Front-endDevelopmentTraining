# 具有转化高阶observable的操作符.md

[http://www.cnblogs.com/BestMePeng/p/rxjs_learn_combination_multicasting.html](http://www.cnblogs.com/BestMePeng/p/rxjs_learn_combination_multicasting.html)

### combineAll

将高阶 Observable 转化为一阶。当外层 Observable 结束时，对每个内层 Observable 使用 combineLastest ，并最终以数组项的形式返回每个内层Observable的最新值。

可以直接处理 promise

### concatAll 

将高阶Observable转化为一阶。并将结果以链式拼接的形式进行发射。

### combineAll 与 concatAll 的区别

- combineAll 可以直接处理 promise
- concatAll 需要 将promise 转成 Observable
- combineAll 会等到 上游(外层 input) Observable 结束时, 才向 下游(output.next) 返回值

### concatMap

