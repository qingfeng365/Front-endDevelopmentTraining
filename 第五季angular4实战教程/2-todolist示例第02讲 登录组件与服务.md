# 2-todolist示例第02讲 登录组件与服务

## 创建登录组件和权限服务

`ng g c login`

`ng g s service/auth`

## 设置路由

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

## 设置权限服务

`/src/app/service/auth.service.ts`

```ts
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {

  constructor() { }

  validLogin(userName: string, password: string): boolean {
    if (userName && userName !== '' && userName !== 'no') {
      return true;
    } else {
      return false;
    }
  }

}

```

## 设置登录表单

### 引入 表单 模块 和 AuthService

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './service/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }

```

### 处理 LoginComponent

`/src/app/login/login.component.jade`

```jade
div
  div 用户:
    input(type="text",[(ngModel)]="username")
  div 密码:
    input(type="password",[(ngModel)]="password")
  div
    button((click)="onLogin()") 登录

```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogin() {
    const isCanLogin = this.authService.validLogin(this.username, this.password);

    console.log('登录检查:' + isCanLogin);

  }
}

```

> 说明:
> 对 input 使用 [(ngModel)] 双向绑定要注意以下几点:
> 
> input 不是 组件, 因此如果象组件一样, 对 value 进行双向绑定: [(value)] 是没有作用的  
> 
> angular 是对 input 对应创建了 `FormConstol` 实例 
> 
> 使用 [(ngModel)] 这个指令, 通过 `ngModel` 对象 将 input.value 映射到 `FormConstol` 实例 的 value 属性
> 
> 同时, [(ngModel)] 这个指令, 还需要引入 FormsModule 才能生效
> 

### 增加校验

以下是测试用代码:

`/src/app/login/login.component.jade`

```jade
div
  div 用户:
    input(type="text",[(ngModel)]="username",required,minlength="3",#usernameCtrl="ngModel")
  div 密码:
    input(type="password",[(ngModel)]="password",required,#passwordCtrl="ngModel")
  div
    button((click)="onLogin(usernameCtrl,passwordCtrl)") 登录
```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogin(userCtrl, pwCtrl) {
    console.dir(userCtrl);
    console.dir(pwCtrl);
    const isCanLogin = this.authService.validLogin(this.username, this.password);

    console.log('登录检查:' + isCanLogin);

  }
}

```

因为要获得校验状态,是需要获得 `input` 对应的 `ngModel` 对象, 或者说要得到 `FormConstol` 实例,

`FormConstol` 实例可以通过 `ngModel.constol` 得到.

因此,如果这样: `<input #usernameCtrl>` , 则模板本地变量 `usernameCtrl` 指向是 `input`自身 (html element),

所以要写成: `<input #usernameCtrl="ngModel">`, 要求模板本地变量要指向 `input` 对应的 `ngModel` 对象, 


增加校验后的正式代码:

`/src/app/login/login.component.jade`

```jade
div
  div 用户:
    input(type="text",[(ngModel)]="username",required,minlength="3",#usernameCtrl="ngModel")
    p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.required") 请输入用户帐号.
    p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.minlength") 用户帐号长度不合法.
  div 密码:
    input(type="password",[(ngModel)]="password",required,#passwordCtrl="ngModel")
    p(*ngIf="passwordCtrl.touched && passwordCtrl.errors?.required") 请输入密码.
  div
    button((click)="onLogin((usernameCtrl.valid && passwordCtrl.valid))") 登录

```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {
      const isCanLogin = this.authService.validLogin(this.username, this.password);
      console.log('登录检查:' + isCanLogin);
    } else {
      console.log('检验不通过');
    }
  }
}

```

### 使用 `form` 标签 (模板式表单)


`/src/app/login/login.component.jade`

```jade
div
  form
    div 用户:
      input(type="text",[(ngModel)]="username",required,minlength="3",#usernameCtrl="ngModel")
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.required") 请输入用户帐号.
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.minlength") 用户帐号长度不合法.
    div 密码:
      input(type="password",[(ngModel)]="password",required,#passwordCtrl="ngModel")
      p(*ngIf="passwordCtrl.touched && passwordCtrl.errors?.required") 请输入密码.
    div
      button((click)="onLogin((usernameCtrl.valid && passwordCtrl.valid))") 登录

```

此时控制台会报错:

```
If ngModel is used within a form tag, either the name attribute must be set or the form control must be defined as 'standalone' in ngModelOptions.
```

原因是:

angular 创建<form>表单的时候，系统默认会创建一个”FormGroup"的对象。

使用带有 `ngModel` 的 `<input>` 时，系统会自动把 input 的 `FormControl`，添加到 `FormGroup` 中。添加时必须用 `<input>` 标签上的 `name` 属性来做标识.

因此,要设置 `input` 的 `name` 属性.

或者用另外一种方法: 在 `<input>` 中设置 `ngModelOptions` 的 `standalone` 为 `true`, 
这样则不会将 `FormControl` 添加到 `FormGroup` 中, 但是这样的话, 通过 `form` 是无法访问 `FormControl`的

#### 解决方法一: ngModelOptions

`/src/app/login/login.component.jade`

```jade
div
  form
    div 用户:
      input(type="text",[(ngModel)]="username",required,minlength="3",#usernameCtrl="ngModel",[ngModelOptions]="{standalone: true}")
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.required") 请输入用户帐号.
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.minlength") 用户帐号长度不合法.
    div 密码:
      input(type="password",[(ngModel)]="password",required,#passwordCtrl="ngModel",[ngModelOptions]="{standalone: true}")
      p(*ngIf="passwordCtrl.touched && passwordCtrl.errors?.required") 请输入密码.
    div
      button((click)="onLogin((usernameCtrl.valid && passwordCtrl.valid))") 登录

```

#### 解决方法二:

`/src/app/login/login.component.jade`

```jade
div
  form
    div 用户:
      input(type="text",[(ngModel)]="username",required,minlength="3",#usernameCtrl="ngModel",name="username")
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.required") 请输入用户帐号.
      p(*ngIf="usernameCtrl.touched && usernameCtrl.errors?.minlength") 用户帐号长度不合法.
    div 密码:
      input(type="password",[(ngModel)]="password",required,#passwordCtrl="ngModel",name="password")
      p(*ngIf="passwordCtrl.touched && passwordCtrl.errors?.required") 请输入密码.
    div
      button((click)="onLogin((usernameCtrl.valid && passwordCtrl.valid))") 登录

```

当使用这种方式时,就可以通过 from 的 模板本地变量处理

正式模板如下:

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
```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {
      const isCanLogin = this.authService.validLogin(this.username, this.password);
      console.log('登录检查:' + isCanLogin);
    } else {
      console.log('检验不通过');
    }
  }
}

```

说明:

- `#formCtrl="ngForm"` , 将变量绑定到 `ngForm` 
- `ngForm.controls` 指向的是 `FormControl`
- 注意, 要使用 `formCtrl.controls['username']?` 安全导航符,
- 因为刚开始时, 还没有添加到 `controls`
- 将按钮设置 `type="submit"` , 就可以通过 `form` 的 `(ngSubmit)` 事件处理



### 使用模板式表单的原则

适用:

- 如果只有一两个编辑框
- 且不需要使用自定义校验
- 同时也不需要复杂的错误处理

> 如果是上述情况,可同时不使用 form 标签, 处理起来更简单

不适用:

- 编辑框超过三个
- 需要自定义校验
- 错误信息比较复杂

> 此时要使用 `响应式表单`
