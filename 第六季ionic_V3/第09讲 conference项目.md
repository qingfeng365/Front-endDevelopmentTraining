# 第9讲 conference项目

## 创建项目


```bash
ionic start myconference-test blank --skip-deps --skip-link 

cd myconference

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


## 创建欢迎页

`ionic g page tutorial --no-module`

### `/src/app/app.module.ts`

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

### `/src/app/app.jade`

```jade
ion-nav([root]="rootPage",
  #content="",
  swipeBackEnabled="false",
  main="",
  name="app")
```

### `/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage:any = TutorialPage;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
}

```

###　`/src/pages/tutorial/tutorial.ts`

```ts
import { Component } from '@angular/core';
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
export class TutorialPage {
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
    this.showSkip = !slider.isEnd();
  }
}

```

### `/src/pages/tutorial/tutorial.jade`

```jade
ion-header(no-border="")
  ion-navbar
    ion-buttons(end="", *ngIf="showSkip")
      button(ion-button="",(click)="startApp()", color="primary") 跳过
ion-content(no-bounce="")
  ion-slides(pager="")
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

### `/src/pages/tutorial/tutorial.scss`

```scss
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
    max-height: 50%;
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
## 创建tabs页与其它页面

```

ionic g page tabs --no-module

ionic g page Schedule --no-module

ionic g page SpeakerList --no-module

ionic g page Map --no-module

ionic g page About --no-module

```

### `/src/app/app.module.ts`

```ts
import { TabsPage } from './../pages/tabs/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { SchedulePage } from '../pages/schedule/schedule';
import { SpeakerListPage } from '../pages/speaker-list/speaker-list';
import { MapPage } from '../pages/map/map';
import { AboutPage } from '../pages/about/about';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage
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
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler }
  ]
})
export class AppModule { }

```

## 处理主菜单和跳转首页

### `/src/pages/pages.ts`

准备菜单数据

```ts

export interface PageInterface {
  title: string;
  name?: string;
  component?: any;
  icon?: string;
  logsOut?: boolean;
  index?: number;
  tabName?: string;
  tabComponent?: any;
}

export const AppPages: PageInterface[] = [
  { title: '日程', icon: 'calendar' },
  { title: '主讲', icon: 'contacts' },
  { title: '地图', icon: 'map' },
  { title: '关于', icon: 'information-circle' }
];

export const LoggedInPages: PageInterface[] = [
  { title: '个人中心', icon: 'person' },
  { title: '支持', icon: 'help' },
  { title: '登出', icon: 'log-out' }
];

export const LoggedOutPages: PageInterface[] = [
  { title: '登录', icon: 'log-in' },
  { title: '支持', icon: 'help' },
  { title: '注册', icon: 'person-add' }
];

```

### `/src/app/app.component.ts`

```ts
import { Component} from '@angular/core';
import { Platform} from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { TutorialPage } from '../pages/tutorial/tutorial';
import { AppPages, PageInterface, LoggedInPages, LoggedOutPages } from '../pages/pages';
@Component({
  templateUrl: 'app.html'
})
export class MyApp{

  rootPage: any = TutorialPage;

  appPages: PageInterface[] = AppPages;
  loggedInPages: PageInterface[] = LoggedInPages;
  loggedOutPages: PageInterface[] = LoggedOutPages;

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }

  isActive(page: PageInterface) {
  }
  openPage(page: PageInterface) {
  }
  openTutorial() {
  }
}

```

### `/src/app/app.jade`

```jade
ion-split-pane
  ion-menu#loggedOutMenu([content]="content")
    ion-header
      ion-toolbar
        ion-title 菜单
    ion-content.outer-content
      ion-list
        ion-list-header 导航
        button(ion-item="",menuClose="",
          *ngFor="let page of appPages",
          (click)="openPage(page)")
          ion-icon(item-start="",
            [name]="page.icon",
            [color]="isActive(page)")
          | {{page.title}}
      ion-list
        ion-list-header 帐户
        button(ion-item="",menuClose="",
          *ngFor="let page of loggedOutPages",
          (click)="openPage(page)")
          ion-icon(item-start="",
            [name]="page.icon",
            [color]="isActive(page)")
          | {{page.title}}
      ion-list
        ion-list-header 欢迎
        button(ion-item="",menuClose="",
          (click)="openTutorial()")
          ion-icon(item-start="",
            name="hammer")
          | 显示欢迎页
  ion-menu#loggedInMenu([content]="content")
    ion-header
      ion-toolbar
        ion-title 菜单
    ion-content.outer-content
      ion-list
        ion-list-header 导航
        button(ion-item="",menuClose="",
          *ngFor="let page of appPages",
          (click)="openPage(page)")
          ion-icon(item-start="",
            [name]="page.icon",
            [color]="isActive(page)")
          | {{page.title}}
      ion-list
        ion-list-header 帐户
        button(ion-item="",menuClose="",
          *ngFor="let page of loggedInPages",
          (click)="openPage(page)")
          ion-icon(item-start="",
            [name]="page.icon",
            [color]="isActive(page)")
          | {{page.title}}
      ion-list
        ion-list-header 欢迎
        button(ion-item="",menuClose="",
          (click)="openTutorial()")
          ion-icon(item-start="",
            name="hammer")
          | 显示欢迎页
  ion-nav([root]="rootPage",
    #content="",
    swipeBackEnabled="false",
    main="",
    name="app")

```

### `/src/pages/tutorial/tutorial.ts`

跳转首页

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { HomePage } from '../home/home';
import { TabsPage } from '../tabs/tabs';

export interface Slide {
  title: string;
  desc: string;
  image: string;
}

@Component({
  selector: 'page-tutorial',
  templateUrl: 'tutorial.html',
})
export class TutorialPage {
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
    this.navCtrl.setRoot(TabsPage, {}, {
      animate: true,
      direction: 'forward'
    });
  }
  onSlideChangeStart(slider) {
    this.showSkip = !slider.isEnd();
  }
}

```

### 显示主菜单

`/src/pages/schedule/schedule.jade`

```jade
ion-header
  ion-navbar(no-border-bottom="")
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 日程
ion-content

```

> no-border-bottom 是无效的, 有可能已废弃, 要改为 no-border

`/src/pages/speaker-list/speaker-list.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 主讲
ion-content

```

`/src/pages/map/map.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 地图
ion-content

```

`/src/pages/about/about.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 关于
ion-content

```

## 处理第二次不显示欢迎页

### 引入 IonicStorageModule

`/src/app/app.module.ts`

```ts
import { TabsPage } from './../pages/tabs/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { SchedulePage } from '../pages/schedule/schedule';
import { SpeakerListPage } from '../pages/speaker-list/speaker-list';
import { MapPage } from '../pages/map/map';
import { AboutPage } from '../pages/about/about';
import { IonicStorageModule } from '@ionic/storage';
@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    TutorialPage,
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler }
  ]
})
export class AppModule { }

```



### `/src/pages/tutorial/tutorial.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { HomePage } from '../home/home';
import { TabsPage } from '../tabs/tabs';
import { Storage } from '@ionic/storage';

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
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public storage: Storage) {
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
    this.navCtrl
      .setRoot(TabsPage, {}, {
        animate: true,
        direction: 'forward'
      })
      .then(() => {
        this.storage.set('hasSeenTutorial', true);
      });
  }
  onSlideChangeStart(slider) {
    this.showSkip = !slider.isEnd();
  }
}

```

### `/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { Storage } from '@ionic/storage';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { AppPages, PageInterface, LoggedInPages, LoggedOutPages } from '../pages/pages';
import { TabsPage } from '../pages/tabs/tabs';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {

  rootPage: any;

  appPages: PageInterface[] = AppPages;
  loggedInPages: PageInterface[] = LoggedInPages;
  loggedOutPages: PageInterface[] = LoggedOutPages;

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen,
    public storage: Storage) {

    this.storage
      .get('hasSeenTutorial')
      .then((hasSeenTutorial) => {
        if (hasSeenTutorial) {
          this.rootPage = TabsPage;
        } else {
          this.rootPage = TutorialPage;
        }
        platform.ready().then(() => {
          statusBar.styleDefault();
          splashScreen.hide();
        });
      })
  }

  isActive(page: PageInterface) {
  }
  openPage(page: PageInterface) {
  }
  openTutorial() {
  }
}


```

