# 第10讲 响应式编程Rxjs

## 概念

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