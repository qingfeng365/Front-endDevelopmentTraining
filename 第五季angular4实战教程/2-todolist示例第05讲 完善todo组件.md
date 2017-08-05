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
