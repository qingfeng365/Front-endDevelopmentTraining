# 2-todolist示例第05讲 完善todo组件

## TodoList

`ng g c todo/todoList`

`/src/app/todo/todo-list/todo-list.component.jade`

```jade
section.main(*ngIf!='todos?.length > 0')
  ul.todo-list
    li(*ngFor='let todo of todos',[class.completed]='todo.completed')
      .view
        input.toggle(type='checkbox',(click)='switchItem(todo);$event.stopPropagation()',
          [checked]='todo.completed')
        label((click)='switchItem(todo);$event.stopPropagation()') {{todo.desc}}
        button.destroy((click)='removeItem(todo);$event.stopPropagation()')
```

将方法名 改为 描述交互动作


`/src/app/todo/todo-list/todo-list.component.ts`

```ts
import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { Todo } from '../model/todo';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  @Input()
  todos: Todo[];
  @Output()
  onRequireSwitch = new EventEmitter<Todo>();
  @Output()
  onRequireRemove = new EventEmitter<Todo>();

  constructor() { }

  ngOnInit() {
  }
  switchItem(todo: Todo) {
    this.onRequireSwitch.emit(todo);
  }
  removeItem(todo: Todo) {
    this.onRequireRemove.emit(todo);
  }
}

```

发射事件

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  app-todo-list([todos]="todos",(onRequireSwitch)="toggleTodo($event)",(onRequireRemove)="removeTodo($event)")
  app-todo-footer([itemCount]="todos?.length")
```

注意: 自定义事件的数据要通过 `$event` 来接收

最后要处理一下样式

`/src/app/todo/todo-list/todo-list.component.css`

```css
.main {
  position: relative;
  z-index: 2;
  border-top: 1px solid #e6e6e6;
}

.todo-list {
  margin: 0;
  padding: 0;
  list-style: none;
}

.todo-list li {
  position: relative;
  font-size: 24px;
  border-bottom: 1px solid #ededed;
}

.todo-list li.completed label {
  color: #d9d9d9;
  text-decoration: line-through;
}

.todo-list li:last-child {
  border-bottom: none;
}

label[for='toggle-all'] {
  display: none;
}

.toggle-all {
  position: absolute;
  top: -55px;
  left: -12px;
  width: 60px;
  height: 34px;
  text-align: center;
  border: none;
  /* Mobile Safari */
}

.toggle-all:before {
  content: '❯';
  font-size: 22px;
  color: #e6e6e6;
  padding: 10px 27px 10px 27px;
}

.toggle-all:checked:before {
  color: #737373;
}


/*
    Hack to remove background from Mobile Safari.
    Can't use it globally since it destroys checkboxes in Firefox
*/

@media screen and (-webkit-min-device-pixel-ratio:0) {
  .toggle-all,
  .toggle {
    background: none;
  }
  .toggle {
    height: 40px;
  }
  .toggle-all {
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
    -webkit-appearance: none;
    appearance: none;
  }
}

input {
  text-align: center;
  width: 40px;
  /* auto, since non-WebKit browsers doesn't support input styling */
  height: auto;
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto 0;
  border: none;
  /* Mobile Safari */
  -webkit-appearance: none;
  appearance: none;
}

input::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#ededed" stroke-width="3"/></svg>');
}

input:checked::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#bddad5" stroke-width="3"/><path fill="#5dc2af" d="M72 25L42 71 27 56l-4 4 20 20 34-52z"/></svg>');
}

label {
  word-break: break-all;
  padding: 15px 60px 15px 15px;
  margin-left: 45px;
  display: block;
  line-height: 1.2;
  transition: color 0.4s;
}

.labelcompleted {
  color: #d9d9d9;
  text-decoration: line-through;
}

.destroy {
  display: none;
  position: absolute;
  top: 0;
  right: 10px;
  bottom: 0;
  width: 40px;
  height: 40px;
  margin: auto 0;
  font-size: 30px;
  color: #cc9a9a;
  margin-bottom: 11px;
  transition: color 0.2s ease-out;
}

.destroy:hover {
  color: #af5b5e;
}

.destroy:after {
  content: '×';
}

.destroy {
  display: block;
}
```

测试一下,功能是否正常

## 进一步拆分 TodoItem

`ng g c todo/todoItem`

原始视图内容:

```jade
      .view
        input.toggle(type='checkbox',(click)='switchItem(todo);$event.stopPropagation()',
          [checked]='todo.completed')
        label((click)='switchItem(todo);$event.stopPropagation()') {{todo.desc}}
        button.destroy((click)='removeItem(todo);$event.stopPropagation()')
```

- `todoItem` 不需要知道 `todo` 对象
- 只需要通知 `todoList` 事件发生,也不需要传递 `todo` 对象
- 另外注意: 原来的样式`completed` 是定义在 li 上
- 但实际是作用在 `label` ,因此样式也相应处理

`/src/app/todo/todo-item/todo-item.component.jade`

```jade
.view
  input.toggle(type='checkbox',(click)='switch();$event.stopPropagation()',
    [checked]='isChecked')
  label((click)='switch();$event.stopPropagation()',
    [class.labelcompleted]='isChecked') {{desc}}
  button.destroy((click)='remove();$event.stopPropagation()')
```

`/src/app/todo/todo-item/todo-item.component.ts`

```ts
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-todo-item',
  templateUrl: './todo-item.component.html',
  styleUrls: ['./todo-item.component.css']
})
export class TodoItemComponent implements OnInit {

  @Input()
  isChecked: boolean;

  @Input()
  desc: string;

  @Output()
  onRequireSwitch = new EventEmitter<void>();
  @Output()
  onRequireRemove = new EventEmitter<void>();

  constructor() { }

  ngOnInit() {
  }
  switch() {
    this.onRequireSwitch.emit();
  }
  remove() {
    this.onRequireRemove.emit();
  }
}

```

`/src/app/todo/todo-list/todo-list.component.jade`

```jade
section.main(*ngIf!='todos?.length > 0')
  ul.todo-list
    li(*ngFor='let todo of todos',[class.completed]='todo.completed')
      app-todo-item([isChecked]="todo.completed",
        [desc]="todo.desc",(onRequireSwitch)="switchItem(todo)",
        (onRequireRemove)="removeItem(todo)")
```

最后要处理一下所有样式

`/src/app/todo/todo-item/todo-item.component.css`

```css
input {
  text-align: center;
  width: 40px;
  /* auto, since non-WebKit browsers doesn't support input styling */
  height: auto;
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto 0;
  border: none;
  /* Mobile Safari */
  -webkit-appearance: none;
  appearance: none;
}

input::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#ededed" stroke-width="3"/></svg>');
}

input:checked::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#bddad5" stroke-width="3"/><path fill="#5dc2af" d="M72 25L42 71 27 56l-4 4 20 20 34-52z"/></svg>');
}

label {
  word-break: break-all;
  padding: 15px 60px 15px 15px;
  margin-left: 45px;
  display: block;
  line-height: 1.2;
  transition: color 0.4s;
}

.labelcompleted {
  color: #d9d9d9;
  text-decoration: line-through;
}

.destroy {
  display: none;
  position: absolute;
  top: 0;
  right: 10px;
  bottom: 0;
  width: 40px;
  height: 40px;
  margin: auto 0;
  font-size: 30px;
  color: #cc9a9a;
  margin-bottom: 11px;
  transition: color 0.2s ease-out;
}

.destroy:hover {
  color: #af5b5e;
}

.destroy:after {
  content: '×';
}

.destroy {
  display: block;
}
```

`/src/app/todo/todo-list/todo-list.component.css`

```css
.main {
  position: relative;
  z-index: 2;
  border-top: 1px solid #e6e6e6;
}

.todo-list {
  margin: 0;
  padding: 0;
  list-style: none;
}

.todo-list li {
  position: relative;
  font-size: 24px;
  border-bottom: 1px solid #ededed;
}

.todo-list li:last-child {
  border-bottom: none;
}

label[for='toggle-all'] {
  display: none;
}

.toggle-all {
  position: absolute;
  top: -55px;
  left: -12px;
  width: 60px;
  height: 34px;
  text-align: center;
  border: none;
  /* Mobile Safari */
  -webkit-appearance: none;
  appearance: none;
}


/* .toggle-all:before {
  content: '❯';
  font-size: 22px;
  color: #e6e6e6;
  padding: 10px 27px 10px 27px;
} */


/* .toggle-all:checked:before {
  color: #737373;
} */

.toggle-all::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#ededed" stroke-width="3"/></svg>');
}

.toggle-all:checked::after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#bddad5" stroke-width="3"/><path fill="#5dc2af" d="M72 25L42 71 27 56l-4 4 20 20 34-52z"/></svg>');
}


/*
    Hack to remove background from Mobile Safari.
    Can't use it globally since it destroys checkboxes in Firefox
*/

@media screen and (-webkit-min-device-pixel-ratio:0) {
  .toggle-all,
  .toggle {
    background: none;
  }
  .toggle {
    height: 40px;
  }
  /* .toggle-all {
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
    -webkit-appearance: none;
    appearance: none;
  } */
}
```

`/src/app/todo/todo.component.css`

```css
.todoapp {
  background: #fff;
  margin: 130px 0 40px 0;
  position: relative;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 25px 50px 0 rgba(0, 0, 0, 0.1);
}
```

测试一下,功能是否正常

## 增加过滤功能

### 需求

对于列表提供三种过滤: 全部 活跃(未完成) 已完成

### 修改服务

- 在 `TodoService` 增加 过滤类型 定义

```ts
export enum TodoFilterType {
  all,
  active,
  completed
}
```

- 实现 `filterTodos` 方法
- `getTodos` 转到 `filterTodos` 方法

`/src/app/todo/service/todo.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Todo } from '../model/todo';
import 'rxjs/add/operator/toPromise';
import * as UUID from 'node-uuid';

@Injectable()
export class TodoService {

  private apiUrl = 'api/todos';

  constructor(private http: Http) { }

  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  getTodos(): Promise<Todo[]> {
    return this.filterTodos(TodoFilterType.all);
  }

  toggleTodo(todo: Todo): Promise<Todo> {
    const url = `${this.apiUrl}/${todo.id}`;
    const updatedTodo = Object.assign({}, todo, { completed: !todo.completed });
    return this.http.put(url, todo)
      .toPromise()
      .then(() => updatedTodo)
      .catch(this.catchError);
  }

  addTodo(desc: string): Promise<Todo> {
    const body = {
      id: UUID.v4(),
      desc: desc,
      completed: false
    };
    return this.http.post(this.apiUrl, body)
      .toPromise()
      .then(res => res.json().data as Todo)
      .catch(this.catchError);
  }

  deleteTodoById(id: string): Promise<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http
      .delete(url)
      .toPromise()
      .then(() => null)
      .catch(this.catchError);
  }
  filterTodos(filterType: TodoFilterType): Promise<Todo[]> {
    switch (filterType) {
      case TodoFilterType.all:
        return this.http.get(this.apiUrl)
          .toPromise()
          .then(res => res.json().data as Todo[])
          .catch(this.catchError);
      case TodoFilterType.active:
        return this.http
          .get(`${this.apiUrl}?completed=false`)
          .toPromise()
          .then(res => res.json().data as Todo[])
          .catch(this.catchError);
      case TodoFilterType.completed:
        return this.http
          .get(`${this.apiUrl}?completed=true`)
          .toPromise()
          .then(res => res.json().data as Todo[])
          .catch(this.catchError);
    }
  }

}

export enum TodoFilterType {
  all,
  active,
  completed
}

```

### 使用 可选路由参数 调用

使用可选路由参数的理由:

- 只是增加附属功能,如果使用路由参数,则需要更改原有的路由导航代码
- 不使用的条件参数,是因为希望该路由可见
- 且统一用 params 处理



`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Todo } from './model/todo';
import { TodoService, TodoFilterType } from './service/todo.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  todos: Todo[];

  constructor(private todoService: TodoService,
    private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    const filterTypeName = this.routeInfo.snapshot.params['filterType'];
    if (!filterTypeName) {
      this.getTodos(TodoFilterType.all);
    } else {
      const keyname = filterTypeName as keyof typeof TodoFilterType;
      this.getTodos(TodoFilterType[keyname]);
    }
  }

  getTodos(filterType: TodoFilterType): void {

    this.todoService
      .filterTodos(filterType)
      .then(todos => this.todos = [...todos]);
  }

  addTodo(desc: string) {
    this.todoService
      .addTodo(desc)
      .then(todo => {
        this.todos = [...this.todos, todo];
      });
  }

  toggleTodo(todo: Todo) {
    const i = this.todos.indexOf(todo);
    this.todoService
      .toggleTodo(todo)
      .then(t => {
        this.todos = [
          ...this.todos.slice(0, i),
          t,
          ...this.todos.slice(i + 1)
        ];
      });
  }

  removeTodo(todo: Todo) {
    const i = this.todos.indexOf(todo);
    this.todoService
      .deleteTodoById(todo.id)
      .then(() => {
        this.todos = [
          ...this.todos.slice(0, i),
          ...this.todos.slice(i + 1)
        ];
      });
  }
}

```

在浏览器测试:

```
http://localhost:4200/todo
http://localhost:4200/todo;filterType=all
http://localhost:4200/todo;filterType=active
http://localhost:4200/todo;filterType=completed
```

### 在 TodoFooterComponent 增加过滤按钮

`/src/app/todo/todo-footer/todo-footer.component.jade`

```jade
footer.footer(*ngIf='itemCount > 0')
  span.todo-count
    strong {{itemCount}}
    | 条
  ul.filters
    li
      a([routerLink]="['/todo',{filterType:'all'}]") 全部
    li
      a([routerLink]="['/todo',{filterType:'active'}]") 活跃
    li
      a([routerLink]="['/todo',{filterType:'completed'}]") 已完成
```

测试一下,功能没有生效,原因是?

`/src/app/todo/todo.component.ts`

```ts
  ngOnInit() {
    this.routeInfo.params.subscribe(params => {
      const filterTypeName = params['filterType'];
      if (!filterTypeName) {
        this.getTodos(TodoFilterType.all);
      } else {
        const keyname = filterTypeName as keyof typeof TodoFilterType;
        this.getTodos(TodoFilterType[keyname]);
      }
    });

  }
```

> 从字符串转换为 `enum` ,比较复杂, 注意示例用法 

## 增加 清除已完成 功能

正常情况下,这是优先由后端实现的功能

但 `angular-in-memory-web-api` 没有对应功能

因此 改由 前端实现.


### 在 TodoFooterComponent 增加清除按钮


`/src/app/todo/todo-footer/todo-footer.component.jade`

```jade
footer.footer(*ngIf='itemCount > 0')
  span.todo-count
    strong {{itemCount}}
    | 条
  ul.filters
    li
      a([routerLink]="['/todo',{filterType:'all'}]") 全部
    li
      a([routerLink]="['/todo',{filterType:'active'}]") 活跃
    li
      a([routerLink]="['/todo',{filterType:'completed'}]") 已完成
  button.clear-completed(type="button", (click)="clear()") 清除已完成
```

`/src/app/todo/todo-footer/todo-footer.component.ts`


```ts
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-todo-footer',
  templateUrl: './todo-footer.component.html',
  styleUrls: ['./todo-footer.component.css']
})
export class TodoFooterComponent implements OnInit {

  @Input()
  itemCount: number;

  @Output()
  onRequireClear = new EventEmitter<void>();

  constructor() { }

  ngOnInit() {
  }

  clear() {
    this.onRequireClear.emit();
  }
}

```

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  app-todo-list([todos]="todos",(onRequireSwitch)="toggleTodo($event)",(onRequireRemove)="removeTodo($event)")
  app-todo-footer([itemCount]="todos?.length",
    (onRequireClear)="clearCompleted()")

```

### clearCompleted 的实现

`/src/app/todo/todo.component.ts`

第一步: 获取 要清除的 id

```ts
  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .map(todo => todo.id)
      .subscribe(result => console.log(result));
  }
```

第二步: 调用 `todoService.deleteTodoById`

```ts
  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .map(todo => this.todoService.deleteTodoById(todo.id))
      .subscribe(() => {
        console.log('删除成功!');
        this.getTodos(TodoFilterType.all);
      });
  }
```

问题:　
- `getTodos` 被多次调用
- 执行顺序不对, 因为 `map` 发射事件的值是 `promise`, 并不是 `promise` 执行的结果 

增加一些调试代码来测试一下:

`/src/app/todo/service/todo.service.ts`

```ts
  deleteTodoById(id: string): Promise<string>  {
    const url = `${this.apiUrl}/${id}`;
    console.log('deleteTodoById 准备删除:' + id);
    return this.http
      .delete(url)
      .toPromise()
      .then(() => {
        console.log('deleteTodoById 删除成功:' + id);
        return id;
      })
      .catch(this.catchError);
  }
```

`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Todo } from './model/todo';
import { TodoService, TodoFilterType } from './service/todo.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import 'rxjs/Rx';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  todos: Todo[];

  constructor(private todoService: TodoService,
    private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    this.routeInfo.params.subscribe(params => {
      const filterTypeName = params['filterType'];
      if (!filterTypeName) {
        this.getTodos(TodoFilterType.all);
      } else {
        const keyname = filterTypeName as keyof typeof TodoFilterType;
        this.getTodos(TodoFilterType[keyname]);
      }
    });

  }


  getTodos(filterType: TodoFilterType): void {

    this.todoService
      .filterTodos(filterType)
      .then(todos => {
        console.log('getTodos 完成...');
        this.todos = [...todos];
      });
  }

  addTodo(desc: string) {
    this.todoService
      .addTodo(desc)
      .then(todo => {
        this.todos = [...this.todos, todo];
      });
  }

  toggleTodo(todo: Todo) {
    const i = this.todos.indexOf(todo);
    this.todoService
      .toggleTodo(todo)
      .then(t => {
        this.todos = [
          ...this.todos.slice(0, i),
          t,
          ...this.todos.slice(i + 1)
        ];
      });
  }

  removeTodo(todo: Todo) {
    const i = this.todos.indexOf(todo);
    this.todoService
      .deleteTodoById(todo.id)
      .then(() => {
        this.todos = [
          ...this.todos.slice(0, i),
          ...this.todos.slice(i + 1)
        ];
      });
  }

  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .map(todo => this.todoService.deleteTodoById(todo.id))
      .do(result => {
        console.log('result:');
        console.log(result);
      })
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
  }
}

```

从测试结果可以看出问题

解决方法:

```ts
  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .map(todo => this.todoService.deleteTodoById(todo.id))
      .combineAll()
      .do(result => {
        console.log('result:');
        console.log(result);
      })
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
  }
```

> combineAll
> 
> 通过等待外部 Observable 完成然后应用 combineLatest ，将高阶 Observable 转化为一阶 Observable
> 
> 另外要注意:　combineAll 可以直接处理 promise
> 
 
等价写法

```ts
  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .map(todo => Observable.from(this.todoService.deleteTodoById(todo.id)))
      .combineAll()
      .do(result => {
        console.log('combineAll result:');
        console.log(result);
      })
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
  }
```

还可以这样写:

```ts
    Observable.from(completedTodos)
      .flatMap(todo => this.todoService.deleteTodoById(todo.id))
      .count()
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
```


###　串行执行的写法

上述写法, `promise` 是并行执行的, 如果要严格按串行顺序执行  `promise` 

则可按下面写法:

```ts
  clearCompleted() {
    const completedTodos = this.todos.filter(todo => todo.completed === true);
    Observable.from(completedTodos)
      .concatMap(todo => this.todoService.deleteTodoById(todo.id))
      .count()
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
  }
```

## 增加 设置/取消 全部已完成的功能

`/src/app/todo/todo-list/todo-list.component.jade`

```jade
section.main(*ngIf!='todos?.length > 0')
  input.toggle-all(type="checkbox",#checkAll="",(click)="toggleAll(checkAll.checked)")
  ul.todo-list
    li(*ngFor='let todo of todos',[class.completed]='todo.completed')
      app-todo-item([isChecked]="todo.completed",
        [desc]="todo.desc",(onRequireSwitch)="switchItem(todo)",
        (onRequireRemove)="removeItem(todo)")


```

`/src/app/todo/todo-list/todo-list.component.ts`

```ts
import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { Todo } from '../model/todo';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  @Input()
  todos: Todo[];
  @Output()
  onRequireSwitch = new EventEmitter<Todo>();
  @Output()
  onRequireRemove = new EventEmitter<Todo>();
  @Output()
  onRequireToggleAll = new EventEmitter<boolean>();
  constructor() { }

  ngOnInit() {
  }
  switchItem(todo: Todo) {
    this.onRequireSwitch.emit(todo);
  }
  removeItem(todo: Todo) {
    this.onRequireRemove.emit(todo);
  }
  toggleAll(isSelected: boolean) {
    this.onRequireToggleAll.emit(isSelected);
  }
}

```

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  app-todo-list([todos]="todos",(onRequireSwitch)="toggleTodo($event)",(onRequireRemove)="removeTodo($event)",
  (onRequireToggleAll)="toggleAll($event)")
  app-todo-footer([itemCount]="todos?.length",
    (onRequireClear)="clearCompleted()")
```

`/src/app/todo/todo.component.ts`

```ts
  toggleAll(isSelectAll: boolean) {
    const todos = this.todos.filter(todo => todo.completed === !isSelectAll);
    Observable.from(todos)
      .flatMap(todo => this.todoService.toggleTodo(todo))
      .count()
      .subscribe(() => {
        this.getTodos(TodoFilterType.all);
      });
  }
```

