# 2-todolist示例第06讲 重构成多用户版本

## 重构数据模型


### 增加 User 模型

`ng g cl todo/model/user`

`/src/app/todo/model/user.ts`

```ts
export class User {
  id: number;
  username: string;
  password: string;
}
```

### 修改 Todo 模型

`/src/app/todo/model/todo.ts`

```ts
export class Todo {
  id: string;
  desc: string;
  completed: boolean;
  userId: number;
}

```

### 修改模拟数据

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Todo } from '../todo/model/todo';
import { User } from '../todo/model/user';

@Injectable()
export class InMemoryDataService implements InMemoryDbService {

  constructor() { }

  createDb(): {} {
    const todos: Todo[] = [
      {
        id: 'f823b191-7799-438d-8d78-fcb1e468fc78',
        desc: '签到',
        completed: true,
        userId: 1
      },
      {
        id: 'c316a3bf-b053-71f9-18a3-0073c7ee3b76',
        desc: '开会',
        completed: false,
        userId: 1
      },
      {
        id: '5894a12f-dae1-5ab0-5761-1371ba4f703e',
        desc: '聚餐',
        completed: true,
        userId: 2
      },
      {
        id: '0d2596c4-216b-df3d-1608-633899c5a549',
        desc: '培训',
        completed: true,
        userId: 1
      },
      {
        id: '0b1f6614-1def-3346-f070-d6d39c02d6b7',
        desc: '考试',
        completed: false,
        userId: 2
      },
      {
        id: 'c1e02a43-6364-5515-1652-a772f0fab7b3',
        desc: '工作',
        completed: false,
        userId: 1
      }
    ];
    const users: User[] = [
      {
        id: 1,
        username: '张三',
        password: '1111'
      },
      {
        id: 2,
        username: '李四',
        password: '2222'
      }
    ];

    return { todos, users };
  }
}
```

## 创建核心模块

核心模块有以下几个特点:

- 只希望导入一次
- 只希望在根模块导入,不会在其它地方导入
- 在核心模块中提供的服务
  + 都是其它地方都需要使用的服务
  + 并希望是一个单例,不会被创建多次
- 在核心模块中提供的组件
  + 大部分是在 根组件 用到的一次性组件
- 核心模块推荐命名: core

### 核心模块

`ng g m core`

设置核心模块,只允许导入一次的方法:

`/src/app/core/core.module.ts`

```ts
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class CoreModule {
  constructor(
    @Optional()
    @SkipSelf()
    parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule 已经存在.');
    }
  }
}

```

`@SkipSelf()` 要求注入时,跳过自身注入器,只检查祖先注入器
`@Optional()` 要求注入时,如果找不到,不要报错

### 在根模块引入核心模块

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
import { CoreModule } from './core/core.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    CoreModule,
    TodoModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }

```

### 核心服务

计划在核心模块中提供的服务

用户服务: user
权限服务: auth
路由权限守卫: auth.guard

这些服务都放在 core 目录


`ng g s core/user`

`ng g s core/auth`

`ng g g core/auth`

设置依赖注入提供器

`/src/app/core/core.module.ts`

```ts
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from './user.service';
import { AuthGuard } from './auth.guard';
import { AuthService } from './auth.service';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers: [UserService, AuthGuard, AuthService]
})
export class CoreModule {
  constructor(
    @Optional()
    @SkipSelf()
    parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule 已经存在.');
    }
  }
}

```

### 路由权限守卫

`/src/app/core/auth.guard.ts`

```ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router) { }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const url = state.url;

    if (localStorage.getItem('userId') !== null) {
      return true;
    }
    // 如果没有登录,则先保存当前 url
    localStorage.setItem('redirectUrl', url);

    this.router.navigate(['/login']);

    return false;
  }

}

```

### 用户服务

`/src/app/core/user.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { User } from './model/user';

@Injectable()
export class UserService {
  private apiUrl = 'api/users';

  constructor(private http: Http) { }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  findUser(username: string): Promise<User> {
    const url = `${this.apiUrl}?username=${username}`;
    return this.http.get(url)
      .toPromise()
      .then(res => {
        const users = res.json().data as User[];
        console.log(users);
        if (users.length > 0) {
          return users[0];
        } else {
          return null;
        }
      })
      .catch(this.catchError);
  }
}

```

> InMemoryDataService 返回的是数组

### 权限对象模型

先把 用户模型 目录重构一下: 移动到 `core/model`

更新 

`/src/app/core/user.service.ts`

```ts
import { User } from './model/user';
```

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { User } from '../core/model/user';
```


####　创建　auth　权限对象模型

`ng g cl core/model/auth`

`/src/app/core/model/auth.ts`

```ts
import { User } from './user';
export class Auth {
  user?: User;
  hasError?: boolean;
  errMsg?: string;
  redirectUrl?: string;
}

```

### 权限服务

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

@Injectable()
export class AuthService {

  constructor(private userService: UserService) { }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  loginValid(username: string, pw: string): Promise<Auth> {
    return this.userService
      .findUser(username)
      .then(user => {
        const auth = new Auth();

        // 先清除以前的登录状态
        localStorage.removeItem('userId');

        // 要跳回的路由
        auth.redirectUrl = localStorage.getItem('redirectUrl');
        if (!auth.redirectUrl) {
          auth.redirectUrl = '/';
        }

        auth.hasError = false;
        auth.errMsg = '';
        auth.user = null;
        if (!user) {
          auth.hasError = true;
          auth.errMsg = '用户不存在.';
        } else {
          if (user.password !== pw) {
            auth.hasError = true;
            auth.errMsg = '密码不正确.';
          }
        }
        if (!auth.hasError) {
          auth.user = Object.assign({}, user);
          localStorage.setItem('userId', String(user.id));
        }
        return auth;
      })
      .catch(this.catchError);
  }

}

```

> Object.assign 是 ES6 的方法, 用来浅复制


### 重构 LoginComponent

#### 修改视图

`/src/app/login/login.component.jade`

```jade
div
  form(#formCtrl="ngForm",(ngSubmit)="onLogin(formCtrl.valid)")
    div 用户:
      input(type="text",[(ngModel)]="username",required,minlength="3",name="username")
      p(*ngIf="formCtrl.controls['username']?.touched && formCtrl.controls['username']?.errors?.required") 请输入用户帐号.
      p(*ngIf="formCtrl.controls['username']?.touched && formCtrl.controls['username']?.errors?.minlength") 用户帐号长度不合法.
    div 密码:
      input(type="password",[(ngModel)]="password",required,name="password")
      p(*ngIf="formCtrl.controls['password']?.touched && formCtrl.controls['password']?.errors?.required") 请输入密码.
    div
      button(type="submit") 登录
    hr
    p(*ngIf="auth?.hasError") {{auth?.errMsg}}

```


#### 修改组件

注意, 要重新引入 AuthService

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { AuthService } from '../core/auth.service';
import { Router } from '@angular/router';
import { Auth } from '../core/model/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  auth: Auth;

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {

      this.authService
        .loginValid(this.username, this.password)
        .then(auth => {
          const redirectUrl = (auth.redirectUrl === null) ? '/' : auth.redirectUrl;
          if (!auth.hasError) {
            this.router.navigate([redirectUrl]);
            localStorage.removeItem('redirectUrl');
          } else {
            this.auth = Object.assign({}, auth);
          }
        });
    } else {
      console.log('检验不通过');
    }
  }
}

```

### 清理旧的 AuthService

删除

`/src/app/service/auth.service.ts`

`/src/app/app.module.ts`

删除
```ts
import { AuthService } from './service/auth.service';
//...
 providers: [AuthService],
```

### 重构 TodoService

- addTodo
- filterTodos

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
    return this.http.put(url, updatedTodo)
      .toPromise()
      .then(() => updatedTodo)
      .catch(this.catchError);
  }

  addTodo(desc: string): Promise<Todo> {
    const userId: number = +localStorage.getItem('userId');
    const body = {
      id: UUID.v4(),
      desc: desc,
      completed: false,
      userId
    };
    return this.http.post(this.apiUrl, body)
      .toPromise()
      .then(res => res.json().data as Todo)
      .catch(this.catchError);
  }

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
  filterTodos(filterType: TodoFilterType): Promise<Todo[]> {
    const userId: number = +localStorage.getItem('userId');
    switch (filterType) {
      case TodoFilterType.all:
        return this.http.get(`${this.apiUrl}?userId=${userId}`)
          .toPromise()
          .then(res => res.json().data as Todo[])
          .catch(this.catchError);
      case TodoFilterType.active:
        return this.http
          .get(`${this.apiUrl}?userId=${userId}&completed=false`)
          .toPromise()
          .then(res => res.json().data as Todo[])
          .catch(this.catchError);
      case TodoFilterType.completed:
        return this.http
          .get(`${this.apiUrl}?userId=${userId}&completed=true`)
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

`/src/app/todo/todo-routing.module.ts`

```ts
import { AuthGuard } from './../core/auth.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TodoComponent } from './todo.component';

const routes: Routes = [
  {
    path: 'todo',
    component: TodoComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TodoRoutingModule { }


```

测试一下.