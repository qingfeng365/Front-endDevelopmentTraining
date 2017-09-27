# 第6讲 super项目

## 创建项目


```bash
ionic start mysuper blank --skip-deps --skip-link 

cd mysuper

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

npm install  gulp gulp-jade --save-dev --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```


`/gulpfile.js`

```js
'use strict';

var gulp = require('gulp');
var jade = require('gulp-jade');

gulp.task('watch', function() {
  gulp.watch('**/*.jade', ['jade']);
});

gulp.task('jade', function() {
  gulp.src('src/**/*.jade', { base: '.' })
    .pipe(jade({
      pretty: true
    }))
    .pipe(gulp.dest('.'));
});
gulp.task('default', ['watch', 'jade']);
```

## 创建引导页

`ionic g page tutorial --no-module`

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    TutorialPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```

`/src/pages/tutorial/tutorial.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

export interface Slide {
  title: string;
  desc: string;
  image: string;
}

@Component({
  selector: 'page-tutorial',
  templateUrl: 'tutorial.html',
})
export class TutorialPage implements OnInit {

  slides: Slide[];
  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.slides = [
      {
        title: '欢迎来到 Ionic Super 启动项目',
        desc: '<b>Ionic Super 启动项目</b> 是一个功能齐全的启动项目, 有很多预制页面和最佳实践. ',
        image: 'assets/img/ica-slidebox-img-1.png'
      },
      {
        title: '如何使用 Ionic Super 启动项目',
        desc: "删除你不想要的页面. 我们提供了许多常见的移动应用程序页面,如登录,注册,导航及教学页面.",
        image: 'assets/img/ica-slidebox-img-2.png'
      },
      {
        title: '新手入门',
        desc: 'Need help? Check out the Super Starter README for a full tutorial',
        image: 'assets/img/ica-slidebox-img-3.png'
      },
      {
        title: '准备好开始了吗?',
        desc: '',
        image: 'assets/img/ica-slidebox-img-4.png'
      }
    ]
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad TutorialPage');
  }

}

```

`/src/pages/tutorial/tutorial.jade`

```jade
ion-header
  ion-navbar
ion-content
  ion-slides
    ion-slide(*ngFor="let slide of slides")
      img.slide-image([src]="slide.image")
      h2.slide-title([innerHTML]="slide.title")
      p([innerHTML]="slide.desc")

```

测试一下

需要进一步调整样式

`/src/pages/tutorial/tutorial.scss`

```css
page-tutorial {
  .toolbar-background {
    background: transparent;
    border-color: transparent;
  }
  .slide-zoom {
    height: 100%;
  }
  .slide-title {
    margin-top: 2.8rem;
  }
  .slide-image {
    max-height: 40%;
    max-width: 60%;
    margin: 36px 0;
  }
  b {
    font-weight: 500;
  }
  p {
    padding: 0 40px;
    font-size: 14px;
    line-height: 1.5;
    color: #60646B;
    b {
      color: #000000;
    }
  }
}
```

`/src/pages/tutorial/tutorial.jade`

```jade
ion-header(no-shadow="")
  ion-navbar
ion-content(no-bounce="")
  ion-slides(pager="true")
    ion-slide(*ngFor="let slide of slides")
      img.slide-image([src]="slide.image")
      h2.slide-title([innerHTML]="slide.title")
      p([innerHTML]="slide.desc")

```

> no-shadow : 没找到 css 有相关设置, 
> 本意应为取消 安卓 系统下的 ion-header 的下阴影 
> 

> no-bounce: 通过 css 实现, 取消 ios 系统下的回弹效果
> 

> 这两个设置,没有文档说明. 只在示例中有相关代码
> 

> 关于样式, ionic 没有使用 angular4 默认启用的组件样式策略,
> 
> 因此注意要将样式放到 组件标签内
> 


### 增加 跳过 按钮 及 进入 按钮

`/src/pages/tutorial/tutorial.jade`

```jade
ion-header(no-shadow="")
  ion-navbar
    ion-buttons(end="", *ngIf="showSkip")
      button(ion-button="",(click)="startApp()", color="primary") 跳过
ion-content(no-bounce="")
  ion-slides(pager="true",(ionSlideWillChange)="onSlideChangeStart($event)")
    ion-slide(*ngFor="let slide of slides; let i = index;")
      img.slide-image([src]="slide.image")
      h2.slide-title([innerHTML]="slide.title")
      p([innerHTML]="slide.desc")
      button(ion-button="",
        icon-end="",
        large="",clear="",
        (click)="startApp()",
        *ngIf="i === slides.length - 1")
        | 继续
        ion-icon(name="arrow-forward")

```

`/src/pages/tutorial/tutorial.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { HomePage } from '../home/home';

export interface Slide {
  title: string;
  desc: string;
  image: string;
}

@Component({
  selector: 'page-tutorial',
  templateUrl: 'tutorial.html',
})
export class TutorialPage implements OnInit {
  slides: Slide[];
  showSkip = true;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.slides = [
      {
        title: '欢迎来到 Ionic Super 启动项目',
        desc: '<b>Ionic Super 启动项目</b> 是一个功能齐全的启动项目, 有很多预制页面和最佳实践. ',
        image: 'assets/img/ica-slidebox-img-1.png'
      },
      {
        title: '如何使用 Ionic Super 启动项目',
        desc: "删除你不想要的页面. 我们提供了许多常见的移动应用程序页面,如登录,注册,导航及教学页面.",
        image: 'assets/img/ica-slidebox-img-2.png'
      },
      {
        title: '新手入门',
        desc: 'Need help? Check out the Super Starter README for a full tutorial',
        image: 'assets/img/ica-slidebox-img-3.png'
      },
      {
        title: '准备好开始了吗?',
        desc: '',
        image: 'assets/img/ica-slidebox-img-4.png'
      }
    ]
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad TutorialPage');
  }
  startApp() {
    this.navCtrl.setRoot(HomePage, {}, {
      animate: true,
      direction: 'forward'
    });
  }
  onSlideChangeStart(slider) {
    console.log(slider);
    this.showSkip = !slider.isEnd();
  }
}

```


##  Slides   Slide 

Slides

[https://ionicframework.com/docs/api/components/slides/Slides/](https://ionicframework.com/docs/api/components/slides/Slides/) 

ion-slide

[https://ionicframework.com/docs/api/components/slides/Slide/](https://ionicframework.com/docs/api/components/slides/Slide/)

## 改造主页为登录注册导航页

### 先创建登录与注册页

```

ionic g page login --no-module

ionic g page signup --no-module

```

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { LoginPage } from '../pages/login/login';
import { SignupPage } from '../pages/signup/signup';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    LoginPage,
    SignupPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    TutorialPage,
    LoginPage,
    SignupPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```

### 设置欢迎页


`/src/pages/home/home.jade`

```jade
ion-content(scroll="false")
  .splash-bg
  .splash-info
    .splash-logo
    .splash-intro 欢迎进入 great Ionic app
  div(padding="")
    button.signup(ion-button="", block="",
      (click)="signup()") 注册
    button.login(ion-button="", block="",
      (click)="login()") 登录

```

`/src/pages/home/home.scss`

```css
page-home {
  .splash-bg {
    position: relative;
    background: url('../assets/img/splashbg.png') no-repeat transparent;
    background-size: cover;
    height: 45%;
    z-index: 1;
    background-repeat: repeat-x;
    animation: animatedBackground 40s linear infinite;
  }
  @keyframes animatedBackground {
    from {
      background-position: 0 0;
    }
    to {
      background-position: 100% 0;
    }
  }
  .splash-info {
    position: relative;
    z-index: 2;
    margin-top: -64px;
    text-align: center;
  }
  .splash-logo {
    margin: auto;
    background: url('../assets/img/appicon.png') repeat transparent;
    background-size: 100%;
    width: 128px;
    height: 128px;
  }
  .splash-intro {
    font-size: 18px;
    font-weight: bold;
    max-width: 80%;
    margin: auto;
  }
  button.login {
    margin-top: 25px;
    background-color: white;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
    color: #333;
    &.activated {
      background-color: rgb(220, 220, 220);
    }
  }
}
```

`/src/pages/home/home.ts`

```ts
import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { LoginPage } from '../login/login';
import { SignupPage } from '../signup/signup';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  constructor(public navCtrl: NavController) {

  }
  login() {
    this.navCtrl.push(LoginPage);
  }

  signup() {
    this.navCtrl.push(SignupPage);
  }
}

```

## 先生成 tabs 页面和相关子页面

```

ionic g page tabs --no-module

ionic g page list --no-module

ionic g page search --no-module

ionic g page settings --no-module

ionic g page cards --no-module

```


## 处理用户登录与注册

### 使用 json-server 模拟后台服务

全局安装 json-server

`npm install json-server -g`


创建目录:

`/src/mock-data`

`/src/mock-data/db.json`

```json
{
  "users": [{
    "id": 1,
    "username": "1111",
    "email": "1111@example.com",
    "password": "1111"
  }, {
    "id": 2,
    "username": "2222",
    "email": "1111@example.com",
    "password": "2222"
  }]
}
```

`/src/mock-data/routes.json`

```json
{
  "/api/*": "/$1"
}
```

`/package.json`

```json
  "scripts": {
    "clean": "ionic-app-scripts clean",
    "build": "ionic-app-scripts build",
    "lint": "ionic-app-scripts lint",
    "ionic:build": "ionic-app-scripts build",
    "ionic:serve": "ionic-app-scripts serve",
    "json": "json-server -w src/mock-data/db.json -r src/mock-data/routes.json"
  },
```

在新的命令行窗口运行命令:

`npm run json`

### 使用第三方工具测试

Postman

RESTClient


### 创建服务

`/src/providers/api.ts`

```ts
import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';

/**
 * Api is a generic REST Api handler. Set your API url first.
 */
@Injectable()
export class Api {
  url: string = 'http://localhost:3000/api';

  constructor(public http: Http) {
  }

  get(endpoint: string, params?: any, options?: RequestOptions) {
    if (!options) {
      options = new RequestOptions();
    }

    // Support easy query params for GET requests
    if (params) {
      let p = new URLSearchParams();
      for (let k in params) {
        p.set(k, params[k]);
      }
      // Set the search field if we have params and don't already have
      // a search field set in options.
      options.search = !options.search && p || options.search;
    }

    return this.http.get(this.url + '/' + endpoint, options);
  }

  post(endpoint: string, body: any, options?: RequestOptions) {
    return this.http.post(this.url + '/' + endpoint, body, options);
  }

  put(endpoint: string, body: any, options?: RequestOptions) {
    return this.http.put(this.url + '/' + endpoint, body, options);
  }

  delete(endpoint: string, options?: RequestOptions) {
    return this.http.delete(this.url + '/' + endpoint, options);
  }

  patch(endpoint: string, body: any, options?: RequestOptions) {
    return this.http.put(this.url + '/' + endpoint, body, options);
  }
}

```

`/src/providers/user.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Api } from './api';
import { User } from '../models/user';


@Injectable()
export class UserService {
  private apiUrl = 'users';

  constructor(public api: Api) { }
  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  findUser(username: string): Observable<User> {
    const url = `${this.apiUrl}?username=${username}`;
    return this.api.get(url)
      .map(res => {
        const users = res.json() as User[];
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
    return this.api
      .post(this.apiUrl, user)
      .map(res => res.json() as User)
      .do(v => console.log(v))
      .catch(this.catchError);
  }
}

```

`/src/providers/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/toPromise';
import { UserService } from './user.service';

import { Observable } from 'rxjs/Rx';

// import 'rxjs/Rx';
import { Auth } from '../models/auth';

@Injectable()
export class AuthService {

  constructor(private userService: UserService) {
    this.emptyAuth();
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
        return auth;
      })
      .catch(this.catchError);
  }

  validRegister(username: string, email: string, password: string): Observable<Auth> {
    const toAddUser = {
      username: username,
      email: email,
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
          });
        }
      })
      .catch(this.catchError);
  }


  emptyAuth(): void {
    const auth: Auth = {
      user: null,
      hasError: true,
      errMsg: '尚未登录...',
    };
  }

}


```

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { LoginPage } from '../pages/login/login';
import { SignupPage } from '../pages/signup/signup';
import { HttpModule } from '@angular/http';
import { Api } from '../providers/api';
import { TabsPage } from '../pages/tabs/tabs';
import { ListPage } from '../pages/list/list';
import { SearchPage } from '../pages/search/search';
import { SettingsPage } from '../pages/settings/settings';
import { CardsPage } from '../pages/cards/cards';
import { UserService } from '../providers/user.service';
import { AuthService } from '../providers/auth.service';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    LoginPage,
    SignupPage,
    TabsPage,
    ListPage,
    SearchPage,
    SettingsPage,
    CardsPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp,{
      backButtonText: '返回',
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    TutorialPage,
    LoginPage,
    SignupPage,
    TabsPage,
    ListPage,
    SearchPage,
    SettingsPage,
    CardsPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    Api,
    UserService,
    AuthService

  ]
})
export class AppModule {}

```

###　处理登录

`/src/pages/login/login.jade`

```jade
ion-header
  ion-navbar
    ion-title 登录
ion-content
  form((submit)="onLogin()")
    ion-list
      ion-item
        ion-label(fixed="") 帐号:
        ion-input(type="text", [(ngModel)]="account.username",
          name="username")
      ion-item
        ion-label(fixed="") 密码:
        ion-input(type="password", [(ngModel)]="account.password",
          name="password")
      div(padding="")
        button(ion-button="",color="primary",block="") 登录

```

`/src/pages/login/login.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { Auth } from '../../models/auth';
import { AuthService } from '../../providers/auth.service';
import { TabsPage } from '../tabs/tabs';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  account: { username: string, password: string } = {
    username: '',
    password: ''
  };

  auth: Auth;
  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    private authService: AuthService) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }
  onLogin() {
    this.authService
      .validLogin(this.account.username, this.account.password)
      .subscribe(auth => {
        this.auth = Object.assign({}, auth);
        if (!auth.hasError) {
          this.navCtrl.push(TabsPage);
        }
      });
  }
}

```

测试登录

### 处理错误提示

Toast 是 Android 中用来显示显示信息的一种机制,和 Dialog 不一样的是, Toast 是没有焦点的,而且 Toast 显示的时间有限,过一定的时间就会自动消失

ToastController 

[https://ionicframework.com/docs/api/components/toast/ToastController/](https://ionicframework.com/docs/api/components/toast/ToastController/)

Label 

[https://ionicframework.com/docs/api/components/label/Label/](https://ionicframework.com/docs/api/components/label/Label/)

> 关于 ion-label 的使用场景
> 
> ion-label 的父级容器为 ion-item, 
> 配合 ion-input, ion-toggle, ion-checkbox 一起使用
> 

`/src/pages/login/login.ts`


```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ToastController } from 'ionic-angular';
import { Auth } from '../../models/auth';
import { AuthService } from '../../providers/auth.service';
import { TabsPage } from '../tabs/tabs';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  account: { username: string, password: string } = {
    username: '',
    password: ''
  };

  auth: Auth;
  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    private authService: AuthService,
    public toastCtrl: ToastController,) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }
  onLogin() {
    this.authService
      .validLogin(this.account.username, this.account.password)
      .subscribe(auth => {
        this.auth = Object.assign({}, auth);
        if (!auth.hasError) {
          this.navCtrl.push(TabsPage);
        } else {
          const toast = this.toastCtrl.create({
            message: this.auth.errMsg,
            duration: 3000,
            position: 'top'
          });
          toast.present();
        }
      });
  }
}

```

### 处理用户注册

`/src/pages/signup/signup.jade`

```jade
ion-header
  ion-navbar
    ion-title 注册
ion-content
  form((submit)="onSignup()")
    ion-list
      ion-item
        ion-label(fixed="") 帐号:
        ion-input(type="text", [(ngModel)]="account.username",
          name="username")
      ion-item
        ion-label(fixed="") 邮箱:
        ion-input(type="email", [(ngModel)]="account.email",
          name="email")
      ion-item
        ion-label(fixed="") 密码:
        ion-input(type="password", [(ngModel)]="account.password",
          name="password")
      div(padding="")
        button(ion-button="",color="primary",block="") 注册

```

`/src/pages/signup/signup.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ToastController } from 'ionic-angular';
import { AuthService } from '../../providers/auth.service';
import { Auth } from '../../models/auth';
import { TabsPage } from '../tabs/tabs';


@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html',
})
export class SignupPage {

  account: { username: string, email: string, password: string } = {
    username: '',
    email: '',
    password: ''
  };

  auth: Auth;
  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    private authService: AuthService,
    public toastCtrl: ToastController, ) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SignupPage');
  }
  onSignup() {
    this.authService
      .validRegister(
      this.account.username,
      this.account.email,
      this.account.password)
      .subscribe(auth => {
        this.auth = Object.assign({}, auth);
        if (!auth.hasError) {
          this.navCtrl.push(TabsPage);
        } else {
          const toast = this.toastCtrl.create({
            message: this.auth.errMsg,
            duration: 3000,
            position: 'top'
          });
          toast.present();
        }
      });
  }
}

```

