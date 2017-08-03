# 2-todolist示例第04讲 拆分组件与重构模块

## 对现有 TodoComponent 结构的分析

```js
`|---------------------| ` 
`| input     ( 页头 )   | ` 
`|---------------------| ` 
`| list      ( 列表 )   | `
`|  + list-item ( 项 ) | `  
`|---------------------| `  
`| 状态 功能按钮 ( 页脚 ) | ` 
`|---------------------| ` 
```

- 将 TodoComponent 有关显示的功能交到子组件处理,
- TodoComponent 只负责业务逻辑
- 并将 TodoComponent 和 子组件 整合到一个模块中

## 重构步骤

### 新建模块

`ng g m todo`

创建 `TodoModule`

接下来,将 `TodoComponent` 声明到 `TodoModule` 中

并把 相关服务 和 模型 整合到 `todo` 目录中

### 创建目录

`/src/app/todo/model`

`/src/app/todo/service`

移动文件到新目录:

`/src/app/todo/service/todo.service.ts`

`/src/app/todo/model/todo.ts`

更新 `TodoComponent` 的引用路径:

`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Todo } from './model/todo';
import { TodoService } from './service/todo.service';


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

更新 `InMemoryDataService` 引用路径

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Todo } from '../todo/model/todo';

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


### TodoModule 引入模块

`/src/app/todo/todo.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TodoComponent } from './todo.component';
import { TodoService } from './service/todo.service';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
  ],
  declarations: [TodoComponent],
  providers: [TodoService]
})
export class TodoModule { }

```

### AppModule 引入 TodoModule 

- 增加 TodoModule
- 取消 TodoComponent 、 TodoService

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './service/auth.service';
import { HttpModule } from '@angular/http';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { TodoModule } from './todo/todo.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    TodoModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }


```

测试一下,原有功能是否正常

## 拆分组件

### 增加页脚组件

`ng g c todo/todoFooter`

注意看提示, 组件会自动绑定到 `TodoModule`



将 `TodoComponent` 的页脚内容移到 `TodoFooterComponent`

`/src/app/todo/todo-footer/todo-footer.component.jade`

```jade
footer.footer(*ngIf='todos?.length > 0')
  span.todo-count
    strong {{todos?.length}}
    | 条
```

通过分析, 页脚组件只需要知道 `length` 数据, 因此页脚组件不需要获得 `todos` 对象

修改如下:

`/src/app/todo/todo-footer/todo-footer.component.jade` 

```jade
footer.footer(*ngIf='itemCount > 0')
  span.todo-count
    strong {{itemCount}}
    | 条
```

增加输入属性 `itemCount`

`/src/app/todo/todo-footer/todo-footer.component.ts`

```ts
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-todo-footer',
  templateUrl: './todo-footer.component.html',
  styleUrls: ['./todo-footer.component.css']
})
export class TodoFooterComponent implements OnInit {

  @Input()
  itemCount: number;

  constructor() { }

  ngOnInit() {
  }

}

```

`TodoComponent` 改为使用子组件 `TodoFooterComponent`

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
  app-todo-footer([itemCount]="todos?.length")
```

最后处理一下 `TodoFooterComponent` 的样式

`/src/app/todo/todo-footer/todo-footer.component.css`

```css
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

@media (max-width: 430px) {
  .footer {
    height: 50px;
  }
  .filters {
    bottom: 10px;
  }
}
```

测试一下,功能是否正常

### 增加页头组件

`ng g c todo/todoHeader`


将 `TodoComponent` 的页头内容移到 `TodoHeaderComponent`


`/src/app/todo/todo-header/todo-header.component.jade`

```jade
header.header
  h1 待办事项
  input.new-todo(placeholder='需要做什么?',autofocus='',[(ngModel)]='desc',
    (keyup.enter)='addTodo()')
```

- placeholder 改为可以指定
- `desc` 变量名 改为 更通用
- `addTodo()` 方法名 改为 表达某事件要触发, 而不是与业务逻辑相关,
	+ 业务逻辑由父组件处理, 子组件只处理显示与交互

修改如下:

`/src/app/todo/todo-header/todo-header.component.jade`

```jade
header.header
  h1 待办事项
  input.new-todo([placeholder]="hint",autofocus='',[(ngModel)]='inputText',
    (keyup.enter)='inputCompleted()')
```

增加 输入输出属性:

`/src/app/todo/todo-header/todo-header.component.ts`

```ts
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-todo-header',
  templateUrl: './todo-header.component.html',
  styleUrls: ['./todo-header.component.css']
})
export class TodoHeaderComponent implements OnInit {

  @Input()
  hint = '';

  @Output()
  onInputCompleted = new EventEmitter<string>();

  inputText: string;

  constructor() { }

  ngOnInit() {
  }

  inputCompleted() {
    if (this.inputText !== '') {
      this.onInputCompleted.emit(this.inputText);
      this.inputText = '';
    }
  }

}

```

`TodoComponent` 改为使用子组件 `TodoHeaderComponent`

`/src/app/todo/todo.component.jade`

```jade
section.todoapp
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  section.main(*ngIf!='todos?.length > 0')
    ul.todo-list
      li(*ngFor='let todo of todos',[class.completed]='todo.completed')
        .view
          input.toggle(type='checkbox',(click)='toggleTodo(todo);$event.stopPropagation()',
            [checked]='todo.completed')
          label((click)='toggleTodo(todo);$event.stopPropagation()') {{todo.desc}}
          button.destroy((click)='removeTodo(todo);$event.stopPropagation()')
  app-todo-footer([itemCount]="todos?.length")

```

`/src/app/todo/todo.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Todo } from './model/todo';
import { TodoService } from './service/todo.service';


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

最后处理一下 `TodoHeaderComponent` 的样式

`/src/app/todo/todo-header/todo-header.component.css`

```css
h1 {
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

input::-webkit-input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

input::-moz-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

input::input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
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
```

测试一下,功能是否正常


## 独立路由

`ng g m todo/todoRouting --flat`

注意: `--flat` 表示不要自动创建 `todo-routing` 的专属目录

`/src/app/todo/todo-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TodoComponent } from './todo.component';

const routes: Routes = [
  {
    path: 'todo',
    component: TodoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TodoRoutingModule { }

```

注意: 

要用 `RouterModule.forChild`

`/src/app/todo/todo.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TodoComponent } from './todo.component';
import { TodoService } from './service/todo.service';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { TodoFooterComponent } from './todo-footer/todo-footer.component';
import { TodoHeaderComponent } from './todo-header/todo-header.component';
import { TodoRoutingModule } from './todo-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    TodoRoutingModule
  ],
  declarations: [TodoComponent, TodoFooterComponent, TodoHeaderComponent],
  providers: [TodoService]
})
export class TodoModule { }

```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';

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
    redirectTo: '/todo',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

测试一下,功能是否正常


