# 2-todolist示例第09讲 登录与注册

## 重构登录组件

`/src/app/app.component.css`

```css
.content {
  padding: 0px;
  margin: 0px;
  height: 100%;
}
```

`/src/app/login/login.component.jade`

```jade
div.mdl-grid.mdl-grid--no-spacing.login-container(
  [style.background-image]="'url(' + photo + ')'")
  mdl-layout-spacer.mdl-cell.mdl-cell--8-col.mdl-cell--4-col-tablet.mdl-cell--hide-phone
  form.mdl-cell.mdl-cell--3-col.mdl-cell--3-col-tablet.mdl-cell--4-col-phone.login-form(
    #formCtrl="ngForm",(ngSubmit)="onLogin(formCtrl.valid)")
    mdl-textfield(type="text",label="用户帐号",name="username",
      floating-label="",required,minlength="2",[(ngModel)]="username")
    br
    mdl-textfield(type="password",label="密码",name="password",
      floating-label="",required,[(ngModel)]="password")
    br
    .mdl-grid
      mdl-layout-spacer
      button(mdl-button="", mdl-button-type="raised", mdl-colored="primary",
        mdl-ripple="",type="submit") 登录
      mdl-layout-spacer
      button(mdl-button="", mdl-button-type="raised",mdl-colored="accent",(click)="onRegister($event)",
      type="button") 注册
      mdl-layout-spacer
    p(*ngIf="auth?.hasError") {{auth?.errMsg}}
    p(*ngIf="formCtrl.controls['username']?.touched && formCtrl.controls['username']?.errors?.minlength") 用户帐号长度不合法.
  mdl-layout-spacer

```

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
  photo = '/assets/login_default_bg.jpg';
  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {

      this.authService
        .validLogin(this.username, this.password)
        .then(auth => {
          this.auth = Object.assign({}, auth);
          if (!auth.hasError) {
            this.authService.notityLoginState(true);
          }
        });
    } else {
      console.log('检验不通过');
    }
  }
}

```

## 使用对话框来处理注册表单

`ng g c registerDialog`

使用响应式表单

在根模块 增加 `ReactiveFormsModule`

建立表单模型

`/src/app/register-dialog/register-dialog.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  Validators,
  FormControl,
  FormBuilder
} from '@angular/forms';
@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css']
})
export class RegisterDialogComponent implements OnInit {
  formModel: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.createForm();
  }
  createForm() {
    this.formModel = this.fb.group(
      {
        username: ['', [Validators.required]],
        passwords:
        this.fb.group({
          password: ['', [Validators.required]],
          repeatPassword: ['', [Validators.required]]
        },
          { validator: this.passwordMatchValidator })
      }
    );
  }
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password').value;
    const repeat = group.get('repeatPassword').value;

    if (password.pristine || repeat.pristine) {
      return null;
    }
    if (password === repeat) {
      return null;
    }
    return { 'mismatch': true };
  }
}

```

`/src/app/register-dialog/register-dialog.component.jade`

```jade
form([formGroup]="formModel")
  h3.mdl-dialog__title 注册
  .mdl-dialog__content
    mdl-textfield(type="text",
      label="Username",
      formControlName="username",
      floating-label="")
    br
    div(formGroupName="passwords")
      mdl-textfield(type="password",
        label="密码",
        formControlName="password",
        floating-label="")
      br
      mdl-textfield(type="password",
      label="确认密码",
      formControlName="repeatPassword",
      floating-label="")
  .status-bar
  p.mdl-color-text--primary {{statusMessage}}
  .mdl-dialog__actions
    button(type="button",
      mdl-button="",
      (click)="register()",
      [disabled]="!formModel.valid",
      mdl-button-type="raised",
      mdl-colored="primary",
      mdl-ripple=""
    ) 注册

```

设置调用

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { AuthService } from '../core/auth.service';
import { Router } from '@angular/router';
import { Auth } from '../core/model/auth';
import { MdlDialogService, MdlDialogReference } from '@angular-mdl/core';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  auth: Auth;
  photo = '/assets/login_default_bg.jpg';
  constructor(private authService: AuthService,
    private router: Router,
    private dialogService: MdlDialogService) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {

      this.authService
        .validLogin(this.username, this.password)
        .then(auth => {
          this.auth = Object.assign({}, auth);
          if (!auth.hasError) {
            this.authService.notityLoginState(true);
          }
        });
    } else {
      console.log('检验不通过');
    }
  }
  onRegister(event) {
    const pDialog = this.dialogService.showCustomDialog({
      component: RegisterDialogComponent,
      isModal: true,
      styles: { 'width': '350px' },
      clickOutsideToClose: true,
      enterTransitionDuration: 400,
      leaveTransitionDuration: 400
    });
    pDialog.subscribe(
      dialogRef => {
        console.log(dialogRef);
      }
    );
  }
}


```

测试提示要提供宿主

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
  <dialog-outlet></dialog-outlet>
</body>

</html>
```

测试提示没有入口组件, 原因是 `RegisterDialogComponent`

是要动态加载的

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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from './core/core.module';
import { MdlModule } from '@angular-mdl/core';
import { RegisterDialogComponent } from './register-dialog/register-dialog.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterDialogComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    MdlModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    CoreModule,
    TodoModule
  ],
  entryComponents: [RegisterDialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

```

测试对话框可以正常弹出

## 重构服务

在完善注册机制前, 先重构 UserService AuthService

在重构前, 可以安装或打开 `Git Lens` 插件

### UserService 与 AuthService

`/src/app/core/user.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { User } from './model/user';
import { Observable } from 'rxjs/Rx';


@Injectable()
export class UserService {
  private apiUrl = 'api/users';

  constructor(private http: Http) { }
  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  findUser(username: string): Observable<User> {
    const url = `${this.apiUrl}?username=${username}`;
    return this.http.get(url)
      .map(res => {
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

`/src/app/core/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';
import { Auth } from './model/auth';

import { Observable, Subject, BehaviorSubject } from 'rxjs/Rx';
import { User } from './model/user';
import { Router } from '@angular/router';

import 'rxjs/add/operator/withLatestFrom';
// import 'rxjs/Rx';

@Injectable()
export class AuthService {

  private authSubject: BehaviorSubject<Auth> = new BehaviorSubject({});
  private redirectUrlSubject: BehaviorSubject<string> =
  new BehaviorSubject('/');
  private loginSucceedSubject: BehaviorSubject<boolean> =
  new BehaviorSubject(null);

  constructor(private userService: UserService,
    private router: Router) {
    this.emptyAuth();
    this.procLoginSucceed();
  }
  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  validLogin(username: string, pw: string): Observable<Auth> {
    return this.userService
      .findUser(username)
      .map(user => {
        const auth = new Auth();

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
    };
    this.authSubject.next(auth);
  }

  notityUnActivateRoute(url: string): void {
    this.redirectUrlSubject.next(url);
    this.router.navigate(['/login']);
  }

  notityLoginState(isLogin: boolean): void {
    if (!isLogin) {
      this.emptyAuth();
    }
    this.loginSucceedSubject.next(isLogin);
  }

  private procLoginSucceed(): void {
    this.loginSucceedSubject.asObservable()
      .withLatestFrom(
      this.authSubject.asObservable(),
      this.redirectUrlSubject.asObservable()
      )
      .subscribe(v => {
        const isLoginSucceed = v[0];
        const redirectUrl = v[2];
        if (isLoginSucceed !== null) {
          if (isLoginSucceed) {
            this.router.navigateByUrl(redirectUrl);
          } else {
            // 如果是登出, 则可跳转的 url 要重置
            this.redirectUrlSubject.next('/');
            this.router.navigateByUrl('/login');
          }
        }
      });
  }
}

```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { AuthService } from '../core/auth.service';
import { Router } from '@angular/router';
import { Auth } from '../core/model/auth';
import { MdlDialogService, MdlDialogReference } from '@angular-mdl/core';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  auth: Auth;
  photo = '/assets/login_default_bg.jpg';
  constructor(private authService: AuthService,
    private router: Router,
    private dialogService: MdlDialogService) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {

      this.authService
        .validLogin(this.username, this.password)
        .subscribe(auth => {
          this.auth = Object.assign({}, auth);
          if (!auth.hasError) {
            this.authService.notityLoginState(true);
          }
        });
    } else {
      console.log('检验不通过');
    }
  }
  onRegister(event) {
    const pDialog = this.dialogService.showCustomDialog({
      component: RegisterDialogComponent,
      isModal: true,
      styles: { 'width': '350px' },
      clickOutsideToClose: true,
      enterTransitionDuration: 400,
      leaveTransitionDuration: 400
    });
    pDialog.subscribe(
      dialogRef => {
        console.log(dialogRef);
      }
    );
  }
}


```

测试功能正常

## 完善注册功能

`/src/app/core/user.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { User } from './model/user';
import { Observable } from 'rxjs/Rx';


@Injectable()
export class UserService {
  private apiUrl = 'api/users';

  constructor(private http: Http) { }
  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  findUser(username: string): Observable<User> {
    const url = `${this.apiUrl}?username=${username}`;
    return this.http.get(url)
      .map(res => {
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
  addUser(user): Observable<User> {
    return this.http
      .post(this.apiUrl, user)
      .map(res => res.json().data as User)
      .do(v => console.log(v))
      .catch(this.catchError);
  }
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
import { Router } from '@angular/router';

import 'rxjs/add/operator/withLatestFrom';
import 'rxjs/add/operator/switchMap';
// import 'rxjs/Rx';

@Injectable()
export class AuthService {

  private authSubject: BehaviorSubject<Auth> = new BehaviorSubject({});
  private redirectUrlSubject: BehaviorSubject<string> =
  new BehaviorSubject('/');
  private loginSucceedSubject: BehaviorSubject<boolean> =
  new BehaviorSubject(null);

  constructor(private userService: UserService,
    private router: Router) {
    this.emptyAuth();
    this.procLoginSucceed();
  }
  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  validLogin(username: string, pw: string): Observable<Auth> {
    return this.userService
      .findUser(username)
      .map(user => {
        const auth = new Auth();

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

  validRegister(username: string, password: string): Observable<Auth> {
    const toAddUser = {
      username: username,
      password: password
    };

    return this.userService
      .findUser(username)
      .switchMap(user => {
        if (user) {
          return Observable.of(<Auth>{
            user: null,
            hasError: true,
            errMsg: '用户已存在, 不允许注册...'
          });
        } else {
          return this.userService
          .addUser(toAddUser)
          .map(newUser => {
            return <Auth>{
              user: Object.assign({}, newUser),
              hasError: false,
              errMsg: ''
            };
          })
          .do(auth => this.authSubject.next(Object.assign({}, auth)));
        }
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
    };
    this.authSubject.next(auth);
  }

  notityUnActivateRoute(url: string): void {
    this.redirectUrlSubject.next(url);
    this.router.navigate(['/login']);
  }

  notityLoginState(isLogin: boolean): void {
    if (!isLogin) {
      this.emptyAuth();
    }
    this.loginSucceedSubject.next(isLogin);
  }

  private procLoginSucceed(): void {
    this.loginSucceedSubject.asObservable()
      .withLatestFrom(
      this.authSubject.asObservable(),
      this.redirectUrlSubject.asObservable()
      )
      .subscribe(v => {
        const isLoginSucceed = v[0];
        const redirectUrl = v[2];
        if (isLoginSucceed !== null) {
          if (isLoginSucceed) {
            this.router.navigateByUrl(redirectUrl);
          } else {
            // 如果是登出, 则可跳转的 url 要重置
            this.redirectUrlSubject.next('/');
            this.router.navigateByUrl('/login');
          }
        }
      });
  }
}


```

`/src/app/register-dialog/register-dialog.component.ts`

```ts
import { MdlDialogReference } from '@angular-mdl/core';
import { AuthService } from './../core/auth.service';
import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  Validators,
  FormControl,
  FormBuilder
} from '@angular/forms';
import { Subscription } from 'rxjs/Rx';
@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css']
})
export class RegisterDialogComponent implements OnInit {
  formModel: FormGroup;
  public isRuning = false;
  public statusMessage = '';
  private subscription: Subscription;
  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private dialog: MdlDialogReference) { }

  ngOnInit() {
    this.createForm();
    this.formModel.statusChanges
      .subscribe(
      status => {
        console.log('statusChanges: ' + status);
      }
      );
  }
  createForm() {
    this.formModel = this.fb.group(
      {
        username: ['', [Validators.required]],
        passwords:
        this.fb.group({
          password: ['', [Validators.required]],
          repeatPassword: ['', [Validators.required]]
        },
          { validator: this.passwordMatchValidator })
      }
    );
  }
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password').value;
    const repeat = group.get('repeatPassword').value;

    if (password.pristine || repeat.pristine) {
      return null;
    }
    if (password === repeat) {
      return null;
    }
    return { 'mismatch': true };
  }

  register() {
    this.isRuning = true;
    this.statusMessage = '注册信息正在处理中...';
    const username = this.formModel.get('username').value;
    const password = this.formModel.get('passwords.password').value;
    this.subscription =
      this.authService
        .validRegister(username, password)
        .subscribe(auth => {
          if (auth.user && !auth.hasError) {
            this.isRuning = false;
            this.statusMessage = '注册成功...';
            setTimeout(() => {
              this.dialog.hide(auth);
            }, 500);
          } else {
            this.isRuning = false;
            this.statusMessage = auth.errMsg;
          }
        },
        err => {
          this.isRuning = false;
          this.statusMessage = err.message;
        },
        () => {
          this.subscription.unsubscribe();
        });
  }
}

```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { AuthService } from '../core/auth.service';
import { Router } from '@angular/router';
import { Auth } from '../core/model/auth';
import { MdlDialogService, MdlDialogReference } from '@angular-mdl/core';
import { RegisterDialogComponent } from '../register-dialog/register-dialog.component';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  auth: Auth;
  photo = '/assets/login_default_bg.jpg';
  constructor(private authService: AuthService,
    private router: Router,
    private dialogService: MdlDialogService) { }

  ngOnInit() {
  }

  onLogin(isValid: boolean) {
    if (isValid) {

      this.authService
        .validLogin(this.username, this.password)
        .subscribe(auth => {
          this.auth = Object.assign({}, auth);
          if (!auth.hasError) {
            this.authService.notityLoginState(true);
          }
        });
    } else {
      console.log('检验不通过');
    }
  }
  onRegister(event) {
    const dialog = this.dialogService.showCustomDialog({
      component: RegisterDialogComponent,
      isModal: true,
      styles: { 'width': '350px' },
      clickOutsideToClose: true,
      enterTransitionDuration: 400,
      leaveTransitionDuration: 400
    });
    dialog.subscribe(
      dialogRef => {
        console.log(dialogRef);
        dialogRef.onHide()
          .subscribe((auth: Auth) => {
            this.auth = Object.assign({}, auth);
            if (auth && auth.user && !auth.hasError) {
              this.authService.notityLoginState(true);
            }
          });
      }
    );
  }
}


```
测试功能正常

## 增加对话框ESC键的处理


