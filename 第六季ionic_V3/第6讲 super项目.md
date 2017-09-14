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

