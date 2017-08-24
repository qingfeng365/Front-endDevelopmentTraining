# 2-todolist示例第07讲 使用angular-mdl组件库

## 安装angular-mdl

Material Design Lite 是 Google 为 web 做的样式库

Angular - Material Design Lite 是第三方基于 Material Design Lite 样式做的组件库

github

[https://github.com/mseemann/angular2-mdl](https://github.com/mseemann/angular2-mdl)

安装 

`npm install @angular-mdl/core --save --registry=https://registry.npm.taobao.org`


## 启用 angular-mdl

### 引入 MaterialIcons

`/src/index.html`

```html
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <title>MytodoTest</title>
  <base href="/">

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" type="image/x-icon" href="favicon.ico">
  <link rel="stylesheet" href="https://fonts.lug.ustc.edu.cn/icon?family=Material+Icons">
</head>

<body>
  <app-root></app-root>
</body>

</html>
```

### 定义主色与配色方案

手动新建文件

`/src/styles.scss`

```sass
@import "~@angular-mdl/core/scss/color-definitions";
$color-primary: $palette-blue-500;
$color-primary-dark: $palette-blue-700;
$color-accent: $palette-amber-A200;
$color-primary-contrast: $color-dark-contrast;
$color-accent-contrast: $color-dark-contrast;
@import '~@angular-mdl/core/scss/material-design-lite';
```



### 启用 styles.scss

`/.angular-cli.json`

```
    "styles": [
      "styles.scss"
    ],
```

启用后,要重启 `ng serve`

原来 `styles.css` 中设置的样式会丢失,暂不用管

### 引用 MdlModule 模块

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HttpModule } from '@angular/http';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { TodoModule } from './todo/todo.module';
import { FormsModule } from '@angular/forms';
import { CoreModule } from './core/core.module';
import {MdlModule} from '@angular-mdl/core';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MdlModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    CoreModule,
    TodoModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

```

### 修改根组件

`/src/app/app.component.jade`

```jade
mdl-layout.mdl-layout-fixed-header.mdl-layout-header-seamed
  mdl-layout-header
    mdl-layout-header-row
      mdl-layout-title {{title}}
      mdl-layout-spacer
      nav.mdl-navigation
        a.mdl-navigation__link 登出
  mdl-layout-drawer
    mdl-layout-title {{title}}
    nav.mdl-navigation
      a.mdl-navigation__link Link
  mdl-layout-content.content
    router-outlet

```

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '待办事项管理';
}

```

## 增加根组件样式

`/src/app/app.component.css`

```css
.content {
  padding: 12px;
}
```


## 调整 TodoComponent

`/src/app/todo/todo.component.jade`

```jade
div
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  app-todo-list([todos]="todos",(onRequireSwitch)="toggleTodo($event)",(onRequireRemove)="removeTodo($event)",
  (onRequireToggleAll)="toggleAll($event)")
  app-todo-footer([itemCount]="todos?.length",
    (onRequireClear)="clearCompleted()")

```

`/src/app/todo/todo.component.css`

取消原有样式

```css
```

### 调整 TodoHeaderComponent

`/src/app/todo/todo-header/todo-header.component.jade`

```jade
div
  input.new-todo([placeholder]="hint",autofocus='',[(ngModel)]='inputText',
    (keyup.enter)='inputCompleted()')

```

`/src/app/todo/todo-header/todo-header.component.css`

```css
.new-todo {
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
  padding: 16px 16px 16px 60px;
  border: none;
  background: rgba(0, 0, 0, 0.003);
  box-shadow: inset 0 -2px 1px rgba(0, 0, 0, 0.03);
}
```

### 调整 TodoItemComponent

`/src/app/todo/todo-item/todo-item.component.jade`

```jade
div
  mdl-icon-toggle((click)='switch();$event.stopPropagation()',
    [(ngModel)]="isChecked") check_circle
  label.todoDesc((click)='switch();$event.stopPropagation()',
    [class.labelcompleted]='isChecked') {{desc}}
  button.destroy((click)='remove();$event.stopPropagation()')

```

注意要改成使用 `[(ngModel)]`

`/src/app/todo/todo-item/todo-item.component.css`

```css
.todoDesc {
  word-break: break-all;
  padding: 15px 15px 15px 15px;
  display: inline-flex;
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
  border: 0px;
  background-color: transparent;
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

### 调整 TodoListComponent

`/src/app/todo/todo-list/todo-list.component.jade`

```jade
section.main(*ngIf!='todos?.length > 0')
  mdl-icon-toggle.toggle-all([(ngModel)]="isToggleAll",(click)="toggleAll(isToggleAll)") check_circle
  ul.todo-list
    li(*ngFor='let todo of todos',[class.completed]='todo.completed')
      app-todo-item([isChecked]="todo.completed",
        [desc]="todo.desc",(onRequireSwitch)="switchItem(todo)",
        (onRequireRemove)="removeItem(todo)")

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

.toggle-all {
  position: absolute;
  top: -50px;
  left: -1px;
  width: 32px;
  height: 32px;
  text-align: center;
}
```

### 调整 TodoFooterComponent

`/src/app/todo/todo-footer/todo-footer.component.css`

```css
.footer {
  color: #777;
  padding: 10px 15px;
  height: 35px;
  text-align: center;
  border-top: 1px solid #e6e6e6;
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

li {
  display: inline;
}

a {
  color: inherit;
  margin: 3px;
  padding: 3px 7px;
  text-decoration: none;
  border: 1px solid transparent;
  border-radius: 3px;
}

li a:hover {
  border-color: rgba(175, 47, 47, 0.1);
}

a.active {
  color: white;
  background-color: rgba(175, 47, 47, 0.2);
  border: none;
}

.clear-completed,
.clear-completed:active {
  float: right;
  position: relative;
  line-height: 20px;
  text-decoration: none;
  cursor: pointer;
  background: transparent;
  border: none;
}

.clear-completed:hover {
  text-decoration: underline;
}
```

### 标记页脚当前过滤状态

注意: 由于使用的是可选参数路由, 不能使用 `routerLinkActive` 功能

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

  @Input()
  currFilterType: string;

  constructor() { }

  ngOnInit() {
  }

  clear() {
    this.onRequireClear.emit();
  }
}

```

`/src/app/todo/todo-footer/todo-footer.component.jade`

```jade
footer.footer(*ngIf='itemCount > 0')
  span.todo-count
    strong {{itemCount}}
    | 条
  ul.filters
    li
      a([routerLink]="['/todo',{filterType:'all'}]",
        [class.active]="currFilterType==='all'") 全部
    li
      a([routerLink]="['/todo',{filterType:'active'}]",
        [class.active]="currFilterType==='active'") 活跃
    li
      a([routerLink]="['/todo',{filterType:'completed'}]",
        [class.active]="currFilterType==='completed'") 已完成
  button.clear-completed(type="button", (click)="clear()") 清除已完成

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
  currFilterType: string;
  constructor(private todoService: TodoService,
    private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    this.routeInfo.params.subscribe(params => {
      const filterTypeName = params['filterType'];
      this.currFilterType = filterTypeName || 'all';
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
        console.log(this.todos);
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
      .concatMap(todo => this.todoService.deleteTodoById(todo.id))
      .count()
      .subscribe(() => {
        console.log('准备读取Todos...');
        this.getTodos(TodoFilterType.all);
      });
  }

  toggleAll(isSelectAll: boolean) {
    const todos = this.todos.filter(todo => todo.completed === !isSelectAll);
    Observable.from(todos)
      .flatMap(todo => this.todoService.toggleTodo(todo))
      .count()
      .subscribe(() => {
        this.getTodos(TodoFilterType.all);
      });
  }
}

```

`/src/app/todo/todo.component.jade`

```jade
div
  app-todo-header(hint="需要做什么?",(onInputCompleted)="addTodo($event)")
  app-todo-list([todos]="todos",(onRequireSwitch)="toggleTodo($event)",(onRequireRemove)="removeTodo($event)",
  (onRequireToggleAll)="toggleAll($event)")
  app-todo-footer([itemCount]="todos?.length",
    (onRequireClear)="clearCompleted()",
    [currFilterType]="currFilterType")


```

## 调整 LoginComponent

`/src/app/login/login.component.jade`

```jade
div
  form(#formCtrl="ngForm",(ngSubmit)="onLogin(formCtrl.valid)")
    mdl-textfield(type="text",label="用户帐号",name="username",
      floating-label="",required,minlength="2",[(ngModel)]="username")
    mdl-textfield(type="password",label="密码",name="password",
      floating-label="",required,[(ngModel)]="password")
    button(mdl-button="", mdl-button-type="raised", mdl-colored="primary",
      mdl-ripple="",type="submit") 登录
    p(*ngIf="auth?.hasError") {{auth?.errMsg}}
    p(*ngIf="formCtrl.controls['username']?.touched && formCtrl.controls['username']?.errors?.minlength") 用户帐号长度不合法.

```

