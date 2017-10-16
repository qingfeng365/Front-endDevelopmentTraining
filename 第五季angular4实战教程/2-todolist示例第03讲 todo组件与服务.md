# 2-todolist示例第03讲 todo组件与服务

## 创建todo组件数据模型和服务

`ng g c todo`

`ng g cl model/todo`

`ng g s service/todo`

## 安装第三方包

```
npm install node-uuid --save
npm install @types/node-uuid --save-dev
```

`.angular-cli.json`

```ts
    "scripts": [
      "../node_modules/node-uuid/uuid.js"
    ],
```

## 设置路由和缺省路由

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TodoComponent } from './todo/todo.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/todo',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'todo',
    component: TodoComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

## 定义模型

`/src/app/model/todo.ts`

```ts

export class Todo {
  id: string;
  desc: string;
  completed: boolean;
}

```

## 安装 angular-in-memory-web-api

`npm install angular-in-memory-web-api --save`

## 创建内存数据服务

`ng g s mock-data/inMemoryData`

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Todo } from '../model/todo';

@Injectable()
export class InMemoryDataService implements InMemoryDbService {

  constructor() { }

  createDb(): {} {
    const todos: Todo[] = [
      {id: 'f823b191-7799-438d-8d78-fcb1e468fc78', desc: '签到', completed: true},
      {id: 'c316a3bf-b053-71f9-18a3-0073c7ee3b76', desc: '开会', completed: false}
    ];
    return {todos};
  }
}

```

## 在根模块导入HttpModule和InMemoryWebApiModule

同时提供 `TodoService`

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './service/auth.service';
import { TodoComponent } from './todo/todo.component';
import { HttpModule } from '@angular/http';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { TodoService } from './service/todo.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TodoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
  ],
  providers: [AuthService, TodoService],
  bootstrap: [AppComponent]
})
export class AppModule { }


```

## TodoService

`/src/app/service/todo.service.ts`

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
    return this.http.get(this.apiUrl)
      .toPromise()
      .then(res => res.json().data as Todo[])
      .catch(this.catchError);
  }

  toggleTodo(todo: Todo): Promise<Todo> {
    const url = `${this.apiUrl}/${todo.id}`;
    const updatedTodo = Object.assign({}, todo, {completed: !todo.completed});
    return this.http.put(url, updatedTodo)
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

}


```

说明:

- 使用以下方法导入 `node-uuid`:

`import * as UUID from 'node-uuid';`

- 在更新 (toggleTodo) 方法使用以下方式创建新对象:

`const updatedTodo = Object.assign({}, todo, {completed: !todo.completed});`

不改变原对象, 而是返回新对象

## 展示数据

`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { TodoService } from '../service/todo.service';
import { Todo } from "../model/todo";

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  todos: Todo[];

  constructor(private todoService: TodoService) { }

  ngOnInit() {
    this.getTodos();
  }
  getTodos(): void {
    this.todoService
      .getTodos()
      .then(todos => this.todos = [...todos]);
  }
}

```

>　注意:
>　`...todos` 中 `...`, 是ES6的操作符, 在TS已可使用 
>　
>　功能是将对象或数组 "打散,拍平"
>　
>　在这里实际上是重新创建了一个新数组
>　
>　目前新的编程趋势是不在过程中,改变输入参数
>　
>　在这个方法使用 `...`, 是为了跟其它方法保持一致
>　


`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  header.header
    h1 待办事项
  section.main(*ngIf='todos?.length > 0')
    ul.todo-list
      li(*ngFor='let todo of todos',[class.cIompleted]='todo.completed')
        .view
          label {{todo.desc}}
  footer.footer(*ngIf='todos?.length > 0')
    span.todo-count
      strong {{todos?.length}}
      | 条
```

## 处理样式

全局样式

`/src/styles.css`


```css
/* You can add global styles to this file, and also import other style files */

html,
body {
  margin: 0;
  padding: 0;
}

button {
  margin: 0;
  padding: 0;
  border: 0;
  background: none;
  font-size: 100%;
  vertical-align: baseline;
  font-family: inherit;
  font-weight: inherit;
  color: inherit;
  -webkit-appearance: none;
  appearance: none;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

body {
  font: 14px 'Helvetica Neue', Helvetica, Arial, sans-serif;
  line-height: 1.4em;
  background: #f5f5f5;
  color: #4d4d4d;
  min-width: 230px;
  max-width: 550px;
  margin: 0 auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-weight: 300;
}

:focus {
  outline: 0;
}

.hidden {
  display: none;
}

.info {
  margin: 65px auto 0;
  color: #bfbfbf;
  font-size: 10px;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
  text-align: center;
}

.info p {
  line-height: 1;
}

.info a {
  color: inherit;
  text-decoration: none;
  font-weight: 400;
}

.info a:hover {
  text-decoration: underline;
}
```

`/app/todo/todo.component.css`

```css
.todoapp {
  background: #fff;
  margin: 130px 0 40px 0;
  position: relative;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 25px 50px 0 rgba(0, 0, 0, 0.1);
}

.todoapp input::-webkit-input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

.todoapp input::-moz-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

.todoapp input::input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

.todoapp h1 {
  position: absolute;
  top: -155px;
  width: 100%;
  font-size: 100px;
  font-weight: 100;
  text-align: center;
  color: rgba(175, 47, 47, 0.15);
  -webkit-text-rendering: optimizeLegibility;
  -moz-text-rendering: optimizeLegibility;
  text-rendering: optimizeLegibility;
}

.new-todo,
.edit {
  position: relative;
  margin: 0;
  width: 100%;
  font-size: 24px;
  font-family: inherit;
  font-weight: inherit;
  line-height: 1.4em;
  border: 0;
  color: inherit;
  padding: 6px;
  border: 1px solid #999;
  box-shadow: inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.new-todo {
  padding: 16px 16px 16px 60px;
  border: none;
  background: rgba(0, 0, 0, 0.003);
  box-shadow: inset 0 -2px 1px rgba(0, 0, 0, 0.03);
}

.main {
  position: relative;
  z-index: 2;
  border-top: 1px solid #e6e6e6;
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

.todo-list li.editing {
  border-bottom: none;
  padding: 0;
}

.todo-list li.editing .edit {
  display: block;
  width: 506px;
  padding: 12px 16px;
  margin: 0 0 0 43px;
}

.todo-list li.editing .view {
  display: none;
}

.todo-list li .toggle {
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

.todo-list li .toggle:after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#ededed" stroke-width="3"/></svg>');
}

.todo-list li .toggle:checked:after {
  content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#bddad5" stroke-width="3"/><path fill="#5dc2af" d="M72 25L42 71 27 56l-4 4 20 20 34-52z"/></svg>');
}

.todo-list li label {
  word-break: break-all;
  padding: 15px 60px 15px 15px;
  margin-left: 45px;
  display: block;
  line-height: 1.2;
  transition: color 0.4s;
}

.todo-list li.completed label {
  color: #d9d9d9;
  text-decoration: line-through;
}

.todo-list li .destroy {
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

.todo-list li .destroy:hover {
  color: #af5b5e;
}

.todo-list li .destroy:after {
  content: '×';
}

.todo-list li:hover .destroy {
  display: block;
}

.todo-list li .edit {
  display: none;
}

.todo-list li.editing:last-child {
  margin-bottom: -1px;
}

.footer {
  color: #777;
  padding: 10px 15px;
  height: 20px;
  text-align: center;
  border-top: 1px solid #e6e6e6;
}

.footer:before {
  content: '';
  position: absolute;
  right: 0;
  bottom: 0;
  left: 0;
  height: 50px;
  overflow: hidden;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2), 0 8px 0 -3px #f6f6f6, 0 9px 1px -3px rgba(0, 0, 0, 0.2), 0 16px 0 -6px #f6f6f6, 0 17px 2px -6px rgba(0, 0, 0, 0.2);
}

.todo-count {
  float: left;
  text-align: left;
}

.todo-count strong {
  font-weight: 300;
}

.filters {
  margin: 0;
  padding: 0;
  list-style: none;
  position: absolute;
  right: 0;
  left: 0;
}

.filters li {
  display: inline;
}

.filters li a {
  color: inherit;
  margin: 3px;
  padding: 3px 7px;
  text-decoration: none;
  border: 1px solid transparent;
  border-radius: 3px;
}

.filters li a:hover {
  border-color: rgba(175, 47, 47, 0.1);
}

.filters li a.selected {
  border-color: rgba(175, 47, 47, 0.2);
}

.clear-completed,
html .clear-completed:active {
  float: right;
  position: relative;
  line-height: 20px;
  text-decoration: none;
  cursor: pointer;
}

.clear-completed:hover {
  text-decoration: underline;
}


/*
    Hack to remove background from Mobile Safari.
    Can't use it globally since it destroys checkboxes in Firefox
*/

@media screen and (-webkit-min-device-pixel-ratio:0) {
  .toggle-all,
  .todo-list li .toggle {
    background: none;
  }
  .todo-list li .toggle {
    height: 40px;
  }
  .toggle-all {
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
    -webkit-appearance: none;
    appearance: none;
  }
}

@media (max-width: 430px) {
  .footer {
    height: 50px;
  }
  .filters {
    bottom: 10px;
  }
}
```

## 处理新增功能

`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { TodoService } from '../service/todo.service';
import { Todo } from "../model/todo";

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  todos: Todo[];
  desc = '';

  constructor(private todoService: TodoService) { }

  ngOnInit() {
    this.getTodos();
  }

  getTodos(): void {
    this.todoService
      .getTodos()
      .then(todos => this.todos = [...todos]);
  }

  addTodo() {
    this.todoService
      .addTodo(this.desc)
      .then(todo => {
        this.todos = [...this.todos, todo];
        this.desc = '';
      });
  }
}

```

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  header.header
    h1 待办事项
    input.new-todo(placeholder='需要做什么?',autofocus='',[(ngModel)]='desc',
     (keyup.enter)='addTodo()')
  section.main(*ngIf='todos?.length > 0')
    ul.todo-list
      li(*ngFor='let todo of todos',[class.completed]='todo.completed')
        .view
          label {{todo.desc}}
  footer.footer(*ngIf='todos?.length > 0')
    span.todo-count
      strong {{todos?.length}}
      | 条

```

注意: `(keyup.enter)` 这种形式是 `angular` 额外提供的语法糖, 正常应为 `(keyup)` 

见 [https://angular.cn/docs/ts/latest/guide/user-input.html](https://angular.cn/docs/ts/latest/guide/user-input.html) 

`按键事件过滤（通过key.enter）` 一节的说明

```
(keyup)事件处理器监听每一次按键。 有时只在意回车键，因为它标志着用户结束输入。 解决这个问题的一种方法是检查每个$event.keyCode，只有键值是回车键时才采取行动。

更简单的方法是：绑定到 Angular 的keyup.enter 模拟事件。 然后，只有当用户敲回车键时，Angular 才会调用事件处理器。
```


## 处理切换状态功能

`/src/app/todo/todo.component.ts`

```ts
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
```

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  header.header
    h1 待办事项
    input.new-todo(placeholder='需要做什么?',autofocus='',[(ngModel)]='desc',
     (keyup.enter)='addTodo()')
  section.main(*ngIf='todos?.length > 0')
    ul.todo-list
      li(*ngFor='let todo of todos',[class.completed]='todo.completed')
        .view
          input.toggle(type='checkbox',(click)='toggleTodo(todo);$event.stopPropagation()',
            [checked]='todo.completed')
          label((click)='toggleTodo(todo);$event.stopPropagation()') {{todo.desc}}
  footer.footer(*ngIf='todos?.length > 0')
    span.todo-count
      strong {{todos?.length}}
      | 条

```

> 注意:
> `checkbox` 类别的 `input` , 要处理 `[checked]`

## 处理删除功能

`/src/app/todo/todo.component.ts`

```ts
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
```

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  header.header
    h1 待办事项
    input.new-todo(placeholder='需要做什么?',autofocus='',[(ngModel)]='desc',
     (keyup.enter)='addTodo()')
  section.main(*ngIf='todos?.length > 0')
    ul.todo-list
      li(*ngFor='let todo of todos',[class.completed]='todo.completed')
        .view
          input.toggle(type='checkbox',(click)='toggleTodo(todo);$event.stopPropagation()',
            [checked]='todo.completed')
          label((click)='toggleTodo(todo);$event.stopPropagation()') {{todo.desc}}
          button.destroy((click)='removeTodo(todo);$event.stopPropagation()')
  footer.footer(*ngIf='todos?.length > 0')
    span.todo-count
      strong {{todos?.length}}
      | 条

```

