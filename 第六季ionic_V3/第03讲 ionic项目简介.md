# 第3讲 ionic 项目简介

## ionic 项目 与 angular4 项目的差别

- 不使用 angular4 的路由
- 由 ionic 的导航机制动态加载组件
- 根组件由 ionic 的根组件接管
- 项目的根组件由 ionic 的根组件 动态加载
- 由于是动态加载, 组件还要声明到 `entryComponents`


项目的根组件的作用

- 不直接显示内容
- 指定导航容器
- 具体内容要在页组件中显示
- 而页组件由导航容器动态加载
- 处理与导航相关的其它内容,如侧滑菜单

## ionic 自动生成的初始内容

`/src/index.html`

```
  <!-- Ionic's root component and where the app will load -->
  <ion-app></ion-app>

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

@NgModule({
  declarations: [
    MyApp,
    HomePage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```

> `bootstrap` 已被 `IonicApp` 接管
>
> `MyApp` 被设置为 `IonicModule.forRoot(MyApp)`

`/src/app/app.html`

```
<ion-nav [root]="rootPage"></ion-nav>
```

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage:any = HomePage;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
}


```

## ion-nav

导航容器组件 

[https://ionicframework.com/docs/api/components/nav/Nav/](https://ionicframework.com/docs/api/components/nav/Nav/)

页面由导航容器组件动态加载

ion-nav 的输入属性:

- name: string, a unique name for the nav element
- root: Page, The Page component to load as the root page within this nav
- rootParams: object, Any nav-params to pass to the root page of this nav

每个导航容器组件 都对应一个导航控制器

##  NavController

导航控制器

[https://ionicframework.com/docs/api/navigation/NavController/](https://ionicframework.com/docs/api/navigation/NavController/)

导航控制器 主要用于通过代码完成导航

导航控制器 管理页面的栈

NavController 通过依赖注入获取



