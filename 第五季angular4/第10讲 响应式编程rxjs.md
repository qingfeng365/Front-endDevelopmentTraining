# 第10讲 响应式编程Rxjs

## 概念

### RxJS中解决异步事件管理的基本概念

- Observable可观察对象：表示一个可调用的未来值或者事件的集合。 
- Observer观察者：一个回调函数集合,它知道怎样去监听被Observable发送的值 
- Subscription订阅： 表示一个可观察对象的执行，主要用于取消执行。 
- Operators操作符：纯粹的函数，使得以函数编程的方式处理集合比如: map, filter, contact, flatmap。 
- Subject(主题)：等同于一个事件驱动器，是将一个值或者事件广播到多个观察者的唯一途径。 
- Schedulers(调度者)：用来控制并发，当计算发生的时候允许我们协调，比如 setTimeout, requestAnimationFrame。 


### 观察者模式与Rxjs


```
         ------- 调用  ------->

  可观察对象                    观察者

         <------ 注册  ---------

```

```js
//...
import { Observable } from 'rxjs/Rx';
//...
var subscription = Observable.from([1,2,3,4])
    .filter((e)=>e%2===0)
    .map((e)=>(e*e))
    .subscribe(
			e=>console.log(e),
			error=>console.error(error),
			()=>console.log('end!')
    	);
```

- 可观察对象`Observable`(流):表示一组值或事件的集合 , 如 `[1,2,3,4]`
- 观察者Observer: 一个回调函数集合,监听`Observable`发送的值
	+ 这里的观察者为 `subscribe` 里面的三个回调函数
- 订阅`subscription`对象:可观察对象,用于取消注册 , 该对象就是 `.subscribe` 的返回值
	+ `.subscribe` 就是上面模型的注册过程
- 操作符Operators: 处理集合的函数, 如 `.filter` , `.map`

> `.subscribe` 可以只定义一个回调,后两个回调是可选的
> `import { Observable } from 'rxjs/Rx';`  注意`Observable`引入的位置
> 辅助插件有可能找错位置


## 有关事件的处理方式

### 事件监听(普通方式)

```html
<input #myField type="text" (keyup)="onKey(myField.value)">
```

```ts
  onKey(value: string) {
    console.log(value);
  }
```


- `#myField`: 模块本地变量, 用`#`+变量名做前缀
- 模块本地变量表示 当前 html 元素 的 dom对象
- `#` 是一个语法糖,表示 声明变量

使用模块本地变量,是为了处理过程不依赖 $event对象,否则要这样处理


```html
<input type="text" (keyup)="onKey($event)">
```

```
  onKey(event: any) {
    console.log(event.target.value);
  }
```


### 事件监听(Rx方式)

引入 响应式编程模块

`/src/app/app.module.ts`

```ts
//...
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
//...

@NgModule({
//...
  imports: [
		//...
    ReactiveFormsModule
  ],
//...
})

//...
```

### 创建表单元素控制对象


`/src/app/bind/bind.component.ts`

```ts
import { Observable } from 'rxjs/Rx';
import { FormControl } from '@angular/forms';

//...

export class BindComponent implements OnInit {

	//...
	searchInput: FormControl = new FormControl();


}

```

### 模板绑定表单元素控制对象

`/src/app/bind/bind.component.html`

```
<input type="text" [formControl]="searchInput">
```

- `formControl` 是angular的指令,表示将`input`的formControl绑定到组件的`searchInput`对象
- 绑定后,当`input`的值发生改变时, `searchInput`对象将发送事件流

### 订阅事件流

`/src/app/bind/bind.component.ts`

```ts
//...
export class BindComponent implements OnInit {

  searchInput: FormControl = new FormControl();

  constructor() {

    this.searchInput.valueChanges
      .subscribe(code => this.getInfo(code));
  }

  getInfo(code: string) {
    console.log(code);
  }
}
```

### 增加操作符: 等500毫秒才触发

```ts
    this.searchInput.valueChanges
      .debounceTime(500)
      .subscribe(code => this.getInfo(code));
```




## 本讲项目介绍

### 项目名称

`myrx`

github 项目地址


备课项目

`myrx-test`

github 项目地址


分支: E07


### 初始准备

- 新建普通项目
- 引入jquery bootstrap 第三包

```bash
ng new myrx --skip-install

cd myrx

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

ng serve

npm install jquery bootstrap @types/jquery @types/bootstrap --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```


`.angular-cli.json`

```
      "styles": [
        "styles.css",
        "../node_modules/bootstrap/dist/css/bootstrap.css"
      ],
      "scripts": [
        "../node_modules/jquery/dist/jquery.js",
        "../node_modules/bootstrap/dist/js/bootstrap.js"
      ],
```

[使用jade的方法.md](使用jade的方法.md)


### 创建组件



`ng g c rxdemo`


### 根组件

`/src/app/app.component.html`

```html
<app-rxdemo></app-rxdemo>
```

###

`/src/app/rxdemo/rxdemo.component.jade`

```jade
h3 例子:统计点击次数
#demo1.alert.alert-success(#demo1='',) 点我

```

```ts
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import 'rxjs/Rx';
import { Observable } from 'rxjs/Rx';

@Component({
  selector: 'app-rxdemo',
  templateUrl: './rxdemo.component.html',
  styleUrls: ['./rxdemo.component.css']
})
export class RxdemoComponent implements OnInit {

  @ViewChild('demo1')
  demo1: ElementRef;

  constructor() { }

  ngOnInit() {
    console.log(this.demo1);

    const demo1$ = document.querySelector('#demo1');
    console.log(demo1$);
    Observable.fromEvent(this.demo1.nativeElement, 'click')
      .mapTo(1)
      .scan(count => count + 1, 0)
      .subscribe(count => console.log(`Clicked ${count} times`));
  }

}

```