# 第4讲 tutorial项目内容

## 本项目学习的内容


>　注意:
>　
>　ionic 生成文件命名与 angular CLI 的规范不太一致,注意源文件描述
>　


- 侧滑菜单
- 菜单控制器
- 导航容器
- 导航控制器

## 先创建列表页面

`ionic g page list --no-module`

选择不自动生成模块, 然后需要手动声明到 `AppModule`

ionic 默认会为每一页面,创建自己的模块,在这里不需要这样做

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ListPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ListPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```

>　注意：要同时声明 `declarations` `entryComponents`

## 准备菜单数据

`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';
@Component({
  templateUrl: 'app.html'
})
export class MyApp implements OnInit {

  rootPage: any = HomePage;
  pages: [{ title: string, component: any }];

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
  ngOnInit(): void {
    this.pages = [
      { title: '首页', component: HomePage },
      { title: '列表', component: ListPage }
    ]
  }
}

```


`/src/app/app.jade`

```jade
ion-menu([content]="content")
  ion-header
    ion-toolbar
      ion-title
  ion-content
    ion-list
      button.ion-item(*ngFor="let page of pages",
        (click)="openPage(page)")
        | {{page.title}}
ion-nav([root]="rootPage", #content="")

```

菜单已经生成到页面,但还显示不出来


## 改造 HomePage 显示出导航菜单


`/src/pages/home/home.jade`

```
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 首页
ion-content(padding="")
  h3 欢迎来到 ionic App !
  p 这是一个功能丰富的 App .
  p
    button(ion-button="",(click)="toggleMenu()",color="primary") 菜单
```

`/src/pages/home/home.ts`

```ts
import { Component } from '@angular/core';
import { NavController, MenuController } from 'ionic-angular';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  constructor(public navCtrl: NavController,
    private menuCtrl: MenuController) {

  }
  toggleMenu(){
    this.menuCtrl.toggle();
  }
}

```

测试菜单已经可以显示

## 内容容器的选择

- 页面内容应用 `ion-content` 包裹
- `ion-content` 会占满整个屏幕,并处理滚动
- 当使用了 `ion-content` 后, 如果还要在 `ion-content` 之外的区域显示内容,
- 则要选择 `ion-header` `ion-footer`
- `ion-content` `ion-header` `ion-footer` 属于根级内容容器


## ion-navbar VS ion-toolbar

- 都是二级内容容器, 必须在外面包裹根级内容容器
- ion-navbar 会附加动态内容, 如显示返回按钮, 并会计算菜单按钮的是否显示
- ion-toolbar 只有静态内容
- ion-toolbar 可以做为 ion-navbar 的兄弟节点, 即附加 sub header

## ion-title

是 `ion-navbar` `ion-toolbar` 的专用子组件

用于在 `ion-navbar` `ion-toolbar` 显示标题,

标题的位置会根据操作系统规范调整


## ion-menu

[https://ionicframework.com/docs/api/components/menu/Menu/](https://ionicframework.com/docs/api/components/menu/Menu/)

- content: any, 侧滑参照元素, 一般应为主 nav 导航容器

- enabled: boolean, 缺省: true

- id: string, 有多个菜单时, id标识

- persistent: boolean, 子页面导航样也可见菜单按钮, 缺省:false

- side:  string, 位置, 可选值: left, right , 缺省:left

- swipeEnabled:  boolean, 允许用手势滑出菜单, 缺省: true  

- type: string, 菜单类型,可选值: overlay, reveal,  push , 可选,不设置时,系统自动判断操作系统

## MenuController 

[https://ionicframework.com/docs/api/components/app/MenuController/](https://ionicframework.com/docs/api/components/app/MenuController/)

要控制 `ion-menu` 组件, 就要注入 `MenuController`

```ts
import { NavController, MenuController } from 'ionic-angular';


  constructor(public navCtrl: NavController,
    private menuCtrl: MenuController) {

  }
```

## ion-button

[https://ionicframework.com/docs/api/components/button/Button/](https://ionicframework.com/docs/api/components/button/Button/)

## ion-icon

图标列表:

[https://ionicframework.com/docs/ionicons/](https://ionicframework.com/docs/ionicons/)

api:

[https://ionicframework.com/docs/api/components/icon/Icon/](https://ionicframework.com/docs/api/components/icon/Icon/)


输入属性:

- ios:string,  Specifies which icon to use on ios mode.
      
- isActive: boolean, If true, the icon is styled with an "active" appearance.An active icon is filled in, and an inactive icon is the outline of the icon.The isActive property is largely used by the tabbar. Only affects ios icons.
      
- md:string, Specifies which icon to use on md mode.
      
- name:string, Specifies which icon to use. The appropriate icon will be used based on the mode.For more information, see Ionicons.

```html
<!-- automatically uses the correct "star" icon depending on the mode -->
<ion-icon name="star"></ion-icon>
<!-- explicity set the icon for each mode -->
<ion-icon ios="ios-home" md="md-home"></ion-icon>
<!-- always use the same icon, no matter what the mode -->
<ion-icon name="ios-clock"></ion-icon>
<ion-icon name="logo-twitter"></ion-icon>
```

##　实现菜单打开页面功能

要引用 `NavController` , 但是在根组件, 不能注入 `NavController`,

原因是 NavController 实际对应的 nav 组件, 是根组件的子组件, 

在 根组件 构造器调用时, nav 组件 实际上还不存在.

`/src/app/app.component.ts`

```ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { Platform, Nav } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';
@Component({
  templateUrl: 'app.html'
})
export class MyApp implements OnInit {

  @ViewChild(Nav)
  nav: Nav;

  rootPage: any = HomePage;
  pages: [{ title: string, component: any }];

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
  ngOnInit(): void {
    this.pages = [
      { title: '首页', component: HomePage },
      { title: '列表', component: ListPage }
    ]
  }

  openPage(page:any){
    this.nav.setRoot(page.component);
  }
}


```

测试一下, 还要处理关闭菜单


`/src/app/app.component.ts`

```ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { Platform, Nav, MenuController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';

@Component({
  templateUrl: 'app.html'
})
export class MyApp implements OnInit {

  @ViewChild(Nav)
  nav: Nav;

  rootPage: any = HomePage;
  pages: [{ title: string, component: any }];

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen,
  private menuCtrl: MenuController) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
  ngOnInit(): void {
    this.pages = [
      { title: '首页', component: HomePage },
      { title: '列表', component: ListPage }
    ]
  }

  openPage(page:any){
    this.menuCtrl.close();
    this.nav.setRoot(page.component);
  }
}


```


## 处理列表页面

`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="", menuToggle="")
      ion-icon(name="menu")
    ion-title 列表
ion-content
  ion-list
    button(ion-item="",
      *ngFor="let item of items$ | async")
      ion-icon([name]="item.icon", item-left="")
      | {{item.title}}
      .item-note(item-right="") {{item.note}}

```

`/src/pages/list/list.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { Observable } from 'rxjs/Rx';


@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage implements OnInit {

  items$: Observable<Item[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.createItems$();
  }
  ionViewDidLoad() {
    console.log('ionViewDidLoad ListPage');
  }

  createItems$(){
    const icons = ['flask', 'wifi',
    'beer', 'football',
    'basketball', 'paper-plane',
    'american-football', 'boat',
    'bluetooth', 'build'];
  this.items$ = Observable
    .zip(
    Observable.from(icons),
    Observable.range(1, icons.length),
    (icon, i) =>
      (
        {
          title: '项目 ' + i,
          note: '这是项目 #' + i,
          icon: icon
        })
    )
    .scan((acc, curr) => [...acc, curr], [])
    .do(v => console.log(v));
  }
}

export class Item {
  title: string;
  note: string;
  icon: string;
}

```


##  Item : ion-list-header,ion-item,[ion-item],ion-item-divider

[https://ionicframework.com/docs/api/components/item/Item/](https://ionicframework.com/docs/api/components/item/Item/)

## 页面生命周期

Page Event  Returns   Description

- ionViewDidLoad  void

>
  + Runs when the page has loaded. 
  + This event only happens once per page being created. 
  + If a page leaves but is cached, then this event will not fire again on a subsequent viewing. 
  + The ionViewDidLoad event is good place to put your setup code for the page.
> 页面加载完毕触发。该事件发生在页面被创建成 DOM 的时候，且仅仅执行一次。如果页面被缓存（Ionic默认是缓存的）就不会再次触发该事件。该事件中可以放置初始化页面的一些事件

- ionViewWillEnter  void 

>
  + Runs when the page is about to enter and become the active page.
> 即将进入一个页面变成当前激活页面的时候执行的事件

- ionViewDidEnter   void  

>
  + Runs when the page has fully entered and is now the active page. 
  + This event will fire, whether it was the first load or a cached page.
> 进入了一个页面且变成了当前的激活页面，该事件不管是第一次进入还是缓存后进入都将执行

- ionViewWillLeave  void  

>
  + Runs when the page is about to leave and no longer be the active page.
> 将要离开了该页面之后变成了不是当前激活页面的时候执行的事件

- ionViewDidLeave   void  

>
  + Runs when the page has finished leaving and is no longer the active page.
> 在页面完成了离开该页面并变成了不是当前激活页面的时候执行的事件

- ionViewWillUnload   void  

>
  + Runs when the page is about to be destroyed and have its elements removed.
> 在页面销毁和页面中有元素移除之前执行的事件

- ionViewCanEnter   boolean/Promise<void>   

>
  + Runs before the view can enter. 
  + This can be used as a sort of "guard" in authenticated views where you need to check permissions before the view can enter

- ionViewCanLeave   boolean/Promise<void>   

>
  + Runs before the view can leave. 
  + This can be used as a sort of "guard" in authenticated views where you need to check permissions before the view can leave

## 创建详情页面

`ionic g page listDetail --no-module`

声明到根模块

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';
import { ListDetailPage } from '../pages/list-detail/list-detail';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ListPage,
    ListDetailPage,
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ListPage,
    ListDetailPage,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```


`/src/pages/list-detail/list-detail.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="", menuToggle="")
      ion-icon(name="menu")
    ion-title 详情
ion-content
  div(*ngIf="item")
    h3(text-center="")
      | {{item.title}}
      ion-icon([name]="item.icon")
    h4(text-center="") {{item.note}}
```

`/src/pages/list-detail/list-detail.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-list-detail',
  templateUrl: 'list-detail.html',
})
export class ListDetailPage {

  item: any;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    this.item = this.navParams.get('item');
  }

}

```

`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="", menuToggle="")
      ion-icon(name="menu")
    ion-title 列表
ion-content
  ion-list
    button(ion-item="",
      *ngFor="let item of items$ | async",
      (click)="tappedItem(item)")
      ion-icon([name]="item.icon", item-left="")
      | {{item.title}}
      .item-note(item-right="") {{item.note}}

```

`/src/pages/list/list.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { Observable } from 'rxjs/Rx';
import { ListDetailPage } from '../list-detail/list-detail';


@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage implements OnInit {

  items$: Observable<Item[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.createItems$();
  }
  ionViewDidLoad() {
    console.log('ionViewDidLoad ListPage');
  }

  createItems$() {
    const icons = ['flask', 'wifi',
      'beer', 'football',
      'basketball', 'paper-plane',
      'american-football', 'boat',
      'bluetooth', 'build'];
    this.items$ = Observable
      .zip(
      Observable.from(icons),
      Observable.range(1, icons.length),
      (icon, i) =>
        (
          {
            title: '项目 ' + i,
            note: '这是项目 #' + i,
            icon: icon
          })
      )
      .scan((acc, curr) => [...acc, curr], []);
  }

  tappedItem(item: Item) {
    this.navCtrl.push(ListDetailPage, { item });
  }
}

export class Item {
  title: string;
  note: string;
  icon: string;
}
```

> 导航栏的菜单按钮是会动态计算显示的
> 
> 如果不是 root 页面, 则会显示 返回 按钮, 不显示 菜单 按钮
> 
> 返回按钮会自动调用 navCtrl.pop()

## 生命周期测试

`/src/pages/list/list.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { Observable } from 'rxjs/Rx';
import { ListDetailPage } from '../list-detail/list-detail';


@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage implements OnInit {

  items$: Observable<Item[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.createItems$();
  }
  ionViewDidLoad() {
    console.log('ionViewDidLoad ListPage...');
  }

  createItems$() {
    const icons = ['flask', 'wifi',
      'beer', 'football',
      'basketball', 'paper-plane',
      'american-football', 'boat',
      'bluetooth', 'build'];
    this.items$ = Observable
      .zip(
      Observable.from(icons),
      Observable.range(1, icons.length),
      (icon, i) =>
        (
          {
            title: '项目 ' + i,
            note: '这是项目 #' + i,
            icon: icon
          })
      )
      .scan((acc, curr) => [...acc, curr], []);
  }

  tappedItem(item: Item) {
    this.navCtrl.push(ListDetailPage, { item });
  }
  ionViewWillEnter() {
    console.log('ionViewWillEnter ListPage...');
  }
  ionViewDidEnter() {
    console.log('ionViewDidEnter ListPage...');
  }
  ionViewWillLeave() {
    console.log('ionViewWillLeave ListPage...');
  }
  ionViewDidLeave() {
    console.log('ionViewDidLeave ListPage...');
  }
  ionViewWillUnload() {
    console.log('ionViewWillUnload ListPage...');
  }
}

export class Item {
  title: string;
  note: string;
  icon: string;
}

```

`/src/pages/list-detail/list-detail.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-list-detail',
  templateUrl: 'list-detail.html',
})
export class ListDetailPage {

  item: any;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ListDetailPage...');
    this.item = this.navParams.get('item');
  }
  ionViewWillEnter() {
    console.log('ionViewWillEnter ListDetailPage...');
  }
  ionViewDidEnter() {
    console.log('ionViewDidEnter ListDetailPage...');
  }
  ionViewWillLeave() {
    console.log('ionViewWillLeave ListDetailPage...');
  }
  ionViewDidLeave() {
    console.log('ionViewDidLeave ListDetailPage...');
  }
  ionViewWillUnload() {
    console.log('ionViewWillUnload ListDetailPage...');
  }
}

```

## 设置返回按钮文字

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ListPage } from '../pages/list/list';
import { ListDetailPage } from '../pages/list-detail/list-detail';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    ListPage,
    ListDetailPage,
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp,{
      backButtonText: '返回',
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    ListPage,
    ListDetailPage,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}

```

>  Config api
[https://ionicframework.com/docs/api/config/Config/](https://ionicframework.com/docs/api/config/Config/)

