# 2-todolist示例第08讲 使用Rx重构项目

## 增加顶部菜单链接

`/src/app/app.component.jade`

```jade
mdl-layout.mdl-layout-fixed-header.mdl-layout-header-seamed
  mdl-layout-header
    mdl-layout-header-row
      mdl-layout-title {{title}}
      mdl-layout-spacer
      nav.mdl-navigation
        a.mdl-navigation__link 列表
      nav.mdl-navigation
        a.mdl-navigation__link 个人中心
      nav.mdl-navigation
        a.mdl-navigation__link 登录
      nav.mdl-navigation
        a.mdl-navigation__link 登出
  mdl-layout-drawer
    mdl-layout-title {{title}}
    nav.mdl-navigation
      a.mdl-navigation__link Link
  mdl-layout-content.content
    router-outlet

```

现在问题在于, 如何根据登录状态来更新菜单?

需要获得表示当前权限的 `Auth` 对象

这个对象应从 `AuthService` 获取

从 Rx 的角度来思考, 就是希望可以从 `AuthService` 订阅 `Auth` 对象

## 提供 Auth 对象的方法

思考方法:

- 对外公开 Observable 的接口
- Observable 的值如何创建
- 单个订阅还是多人订阅

结论:

- Auth 对象 是要登录后才能创建, 
- 因此需要登录后, 主动发射值, 且要多人订阅
- 所以需要通过 subject 实现

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject } from 'rxjs/Rx';

@Injectable()
export class AuthService {

  authSubject: Subject<Auth> = new Subject();

  constructor(private userService: UserService) { }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  validLogin(username: string, pw: string): Promise<Auth> {
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

  getAuth(): Observable<Auth> {
    return this.authSubject.asObservable();
  }
}

```

## 在根组件订阅 `Auth` 对象

`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Auth } from './core/model/auth';
import { AuthService } from './core/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = '待办事项管理';
  auth: Auth;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService
      .getAuth()
      .subscribe(auth =>
        this.auth = Object.assign({}, auth));
  }
}

```

`/src/app/app.component.jade`

```jade
mdl-layout.mdl-layout-fixed-header.mdl-layout-header-seamed
  mdl-layout-header
    mdl-layout-header-row
      mdl-layout-title {{title}}
      mdl-layout-spacer
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link 列表
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link 个人中心
      nav.mdl-navigation(*ngIf="auth?.user === null")
        a.mdl-navigation__link 登录
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link 登出
  mdl-layout-drawer
    mdl-layout-title {{title}}
    nav.mdl-navigation
      a.mdl-navigation__link Link
  mdl-layout-content.content
    router-outlet

```

## 先修改缺省路由

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
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

把浏览器地址改为: `http://localhost:4200/`

## 解决发射 `Auth` 对象

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject } from 'rxjs/Rx';

@Injectable()
export class AuthService {

  private authSubject: Subject<Auth> = new Subject();

  constructor(private userService: UserService) { }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  validLogin(username: string, pw: string): Promise<Auth> {
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

        this.authSubject.next(Object.assign({}, auth));
        return auth;
      })
      .catch(this.catchError);
  }

  getAuth(): Observable<Auth> {
    return this.authSubject.asObservable();
  }
}

```

## 缺省路由改回 todo

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

## 完成登出功能

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject } from 'rxjs/Rx';

@Injectable()
export class AuthService {

  private authSubject: Subject<Auth> = new Subject();

  constructor(private userService: UserService) { }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  validLogin(username: string, pw: string): Promise<Auth> {
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

        this.authSubject.next(Object.assign({}, auth));
        return auth;
      })
      .catch(this.catchError);
  }

  getAuth(): Observable<Auth> {
    return this.authSubject.asObservable();
  }

  emptyAuth(): void {
  	localStorage.removeItem('userId');
    const auth: Auth = {
      user: null,
      hasError: true,
      errMsg: '尚未登录...',
      redirectUrl: ''
    };
    this.authSubject.next(auth);
  }
}

```

`/src/app/app.component.jade`

```jade
mdl-layout.mdl-layout-fixed-header.mdl-layout-header-seamed
  mdl-layout-header
    mdl-layout-header-row
      mdl-layout-title {{title}}
      mdl-layout-spacer
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link([routerLink]="['todo']") 列表
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link {{auth?.user?.username}}
      nav.mdl-navigation(*ngIf="auth?.user === null")
        a.mdl-navigation__link([routerLink]="['login']") 登录
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link((click)="logout()") 登出
  mdl-layout-drawer
    mdl-layout-title {{title}}
    nav.mdl-navigation
      a.mdl-navigation__link Link
  mdl-layout-content.content
    router-outlet

```

`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Auth } from './core/model/auth';
import { AuthService } from './core/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = '待办事项管理';
  auth: Auth;

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.authService
      .getAuth()
      .subscribe(auth =>
        this.auth = Object.assign({}, auth));
  }
  logout(): void {
    this.authService.emptyAuth();
    this.auth = null;
    this.router.navigate(['login']);
  }
}

```

`/src/app/app.component.jade`

```jade
mdl-layout.mdl-layout-fixed-header.mdl-layout-header-seamed
  mdl-layout-header
    mdl-layout-header-row
      mdl-layout-title {{title}}
      mdl-layout-spacer
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link([routerLink]="['todo']") 列表
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link {{auth?.user?.username}}
      nav.mdl-navigation(*ngIf="auth?.user === null")
        a.mdl-navigation__link([routerLink]="['login']") 登录
      nav.mdl-navigation(*ngIf="auth?.user !== null")
        a.mdl-navigation__link((click)="logout()") 登出
  mdl-layout-drawer
    mdl-layout-title {{title}}
    nav.mdl-navigation
      a.mdl-navigation__link Link
  mdl-layout-content.content
    router-outlet

```

## 解决路由守卫的问题

### 修改 canActivate 判断是否已登录的形式

`/src/app/core/auth.guard.ts`

```ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './auth.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) { }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const url = state.url;
    return this.authService
      .getAuth()
      .map(auth => auth && !auth.hasError)
      .do(isvalid => {
        if (!isvalid) {
          // 如果没有登录,则先保存当前 url
          localStorage.setItem('redirectUrl', url);
          this.router.navigate(['/login']);
        }
      });
  }
}

```

> canActivate 守卫 要求返回的是 Observable<boolean>
> 
> 所以要用 map 转换为 boolean
> 

测试一下,发现没有生效,原因是什么呢?

`this.authSubject.next` 是在 `validLogin` 方法处理的,

而 `validLogin` 是在登录组件中调用的, 因此还没有机会触发

### 增加 authSubject 的初始值

`/src/app/core/auth.service.ts`

```ts
  constructor(private userService: UserService) {
    this.emptyAuth();
  }
```


再测试一下,发现还是没有生效,原因又是什么呢?

增加一些调试代码观察一下

`/src/app/core/auth.service.ts`

```ts
  emptyAuth(): void {
    localStorage.removeItem('userId');
    const auth: Auth = {
      user: null,
      hasError: true,
      errMsg: '尚未登录...',
      redirectUrl: ''
    };
    console.log('emptyAuth next ...');
    this.authSubject.next(auth);
  }
```

`/src/app/core/auth.guard.ts`

```ts
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const url = state.url;
    console.log('canActivate...');
    return this.authService
      .getAuth()
      .do(v => console.log('canActivate getAuth...'))
      .map(auth => auth && !auth.hasError)
      .do(isvalid => {
        if (!isvalid) {
          // 如果没有登录,则先保存当前 url
          localStorage.setItem('redirectUrl', url);
          this.router.navigate(['/login']);
        }
      });
  }
```

> 注意:
> Subject 要求先订阅, 再发射值
> 
> 如果先发射值, 再订阅, 则之前的值是不会接收的
> 

解决方法:

改用 `BehaviorSubject`

> BehaviorSubject
> 
> Subjects的一个变体是BehaviorSubject,其有"当前值"的概念。
> 
> 它储存着要发射给消费者的最新的值。
> 
> 无论何时一个新的观察者订阅它，
> 
> 都会立即接受到这个来自BehaviorSubject的"当前值"
> 
> 


`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject, BehaviorSubject } from 'rxjs/Rx';
import { User } from './model/user';

@Injectable()
export class AuthService {

  private authSubject: BehaviorSubject<Auth> = new BehaviorSubject({});

  constructor(private userService: UserService) {
    this.emptyAuth();
  }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  validLogin(username: string, pw: string): Promise<Auth> {
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

        this.authSubject.next(Object.assign({}, auth));
        return auth;
      })
      .catch(this.catchError);
  }

  getAuth(): Observable<Auth> {
    return this.authSubject.asObservable();
  }

  emptyAuth(): void {
    localStorage.removeItem('userId');
    const auth: Auth = {
      user: null,
      hasError: true,
      errMsg: '尚未登录...',
      redirectUrl: ''
    };
    this.authSubject.next(auth);
  }
}

```

## 关于是否需要下次免登录功能

一般单页应用, 因页面在使用过程中不会刷新,

简单的做法, 就是首次登录, 但不处理下次免登录,

也就是说如果刷新了页面,就要重新登录. 

这样也比较安全



## 解决 localStorage 存储 userId 的依赖问题

```
validLogin
  // 先清除以前的登录状态
  localStorage.removeItem('userId');

  localStorage.setItem('userId', String(user.id));

```

`/src/app/todo/service/todo.service.ts`

```
 const userId: number = +localStorage.getItem('userId');
```

### 修改 TodoService 获取 userId 的方式

`/src/app/todo/service/todo.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Todo } from '../model/todo';
import 'rxjs/add/operator/toPromise';
import * as UUID from 'node-uuid';
import { AuthService } from '../../core/auth.service';

@Injectable()
export class TodoService {

  private apiUrl = 'api/todos';

  private userId: number;

  constructor(private http: Http,
    private authService: AuthService) {
    this.authService
      .getAuth()
      .map(auth => {
        if (auth && auth.user) {
          return auth.user.id;
        } else {
          return 0;
        }
      })
      .subscribe(id => this.userId = id);
  }

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
    const userId: number = this.userId;
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

  deleteTodoById(id: string): Promise<string> {
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
    const userId: number = this.userId;
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

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject, BehaviorSubject } from 'rxjs/Rx';
import { User } from './model/user';

@Injectable()
export class AuthService {

  private authSubject: BehaviorSubject<Auth> = new BehaviorSubject({});

  constructor(private userService: UserService) {
    this.emptyAuth();
  }
  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err);
  }

  validLogin(username: string, pw: string): Promise<Auth> {
    return this.userService
      .findUser(username)
      .then(user => {
        const auth = new Auth();

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
        }

        this.authSubject.next(Object.assign({}, auth));
        return auth;
      })
      .catch(this.catchError);
  }

  getAuth(): Observable<Auth> {
    return this.authSubject.asObservable();
  }

  emptyAuth(): void {
    const auth: Auth = {
      user: null,
      hasError: true,
      errMsg: '尚未登录...',
      redirectUrl: ''
    };
    this.authSubject.next(auth);
  }
}

```

测试一下功能是否正常

