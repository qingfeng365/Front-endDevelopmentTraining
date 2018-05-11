# rx5 升级到 rx6 要注意的事项

如果升级到最新的 rx6, 则 需要同时安装  rxjs-compat , 防止旧代码无法运行

## 官方的升级说明

[https://github.com/ReactiveX/rxjs/blob/master/MIGRATION.md](https://github.com/ReactiveX/rxjs/blob/master/MIGRATION.md)

## 导入操作符的变化

理解操作符导入
[https://rxjs-cn.github.io/learn-rxjs-operators/concepts/operator-imports.html](https://rxjs-cn.github.io/learn-rxjs-operators/concepts/operator-imports.html)

rx5

```ts
import 'rxjs/add/operator/take';
```
这种写法是将操作符 take 挂到 Observable 的原形上

```ts
import { Observable } from '../../Observable';
import { take } from '../../operator/take';

Observable.prototype.take = take;

declare module '../../Observable' {
  interface Observable<T> {
    take: typeof take;
  }
}

```

Rx 6

将操作符分为以下两种 

- creation function : 创建 Observable 的操作符
- pipeable operators : 可管道调用的操作符

以及

- pipe 函数 (pipe 函数, 与 Observable 的 pipe 方法作用是一样的)
		pipe 函数 主要用于 动态创建 一系列操作符操作


创建操作符 及 pipe 函数 用以下方式导入:

import { } from 'rxjs';

```
import { interval, pipe } from 'rxjs';
```
pipeable 操作符用以下方式导入:

import { } from 'rxjs/operators';

```
import { map, take} from 'rxjs';
```

关于 pipeable 操作符的说明

[https://github.com/ReactiveX/rxjs/blob/master/doc/pipeable-operators.md#build-and-treeshaking](https://github.com/ReactiveX/rxjs/blob/master/doc/pipeable-operators.md#build-and-treeshaking)

## 完整的导入说明

```ts

    rxjs: Creation methods, types, schedulers and utilities

import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';

    rxjs/operators: All pipeable operators:

import { map, filter, scan } from 'rxjs/operators';

    rxjs/webSocket: The web socket subject implementation

import { webSocket } from 'rxjs/webSocket';

    rxjs/ajax: The Rx ajax implementation

import { ajax } from 'rxjs/ajax';

    rxjs/testing: The testing utilities

import { TestScheduler } from 'rxjs/testing';
```


## 部分操作符改名


    do -> tap
    catch -> catchError
    switch -> switchAll
    finally -> finalize

    let -> pipe

The let operator is now part of Observable as pipe and cannot be imported.

source$.let(myOperator) -> source$.pipe(myOperator)


The former toPromise() "operator" has been removed because an operator returns an Observable, not a Promise. There is now an Observable.toPromise()instance method.

Because throw is a key word you could use _throw after import { _throw } from 'rxjs/observable/throw'

```ts
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
...
const e = ErrorObservable.create(new Error('My bad'));
const e2 = new ErrorObservable(new Error('My bad too'));

```

## 操作符链式调用的变化

Rx6 不推荐操作符链式调用, 要改用 pipe 方法调用

```ts
import { range } from 'rxjs/observable/range';
import { map, filter, scan } from 'rxjs/operators';

const source$ = range(0, 10);

source$.pipe(
  filter(x => x % 2 === 0),
  map(x => x + x),
  scan((acc, x) => acc + x, 0)
)
.subscribe(x => console.log(x))
```

## 部分操作符用法改变的说明


非常重要

[https://github.com/ReactiveX/rxjs/blob/master/MIGRATION.md#pipe-syntax](https://github.com/ReactiveX/rxjs/blob/master/MIGRATION.md#pipe-syntax)

## 使用迁移工具

[https://github.com/ReactiveX/rxjs-tslint](https://github.com/ReactiveX/rxjs-tslint)

Migration to RxJS 6

Using the current set of rules allows you to automatically migrate your project which uses RxJS 5 to RxJS 6. Here's how you can perform the automatic migration:

npm i -g rxjs-tslint
rxjs-5-to-6-migrate -p [PATH_TO_TSCONFIG]

For an Angular CLI project the invocation of rxjs-5-to-6-migrate will be:

rxjs-5-to-6-migrate -p src/tsconfig.app.json