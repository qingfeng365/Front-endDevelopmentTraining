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

### flatMap

### flatMap 与 concatMap 的区别

flatMap 是并行执行 , 只要 上游(外层 input) 有值(内部 Observable )发出, 
就马上订阅 内部Observable, 并向下游发出

concatMap 是串行执行, 要等上一次的 上游(外层 input)发出的 值(内部 Observable ) 结束

### concatMap/flatMap 与 concatAll/combineAll 的区别

concatAll/combineAll
	
不需要设置回调函数, 并且要求 上游(外层 input)发出的 值 本身是 Observable

concatMap/flatMap 

要设置回调函数, 回调函数的作用是 将上游(外层 input)发出的 普通值 转化为 Observable(内部 Observable ),

并同时订阅 内部 Observable




