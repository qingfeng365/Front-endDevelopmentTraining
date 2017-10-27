# 第8讲 super项目-其它页面

## 搜索页

`/src/pages/search/search.jade`

```jade
ion-header
  ion-navbar
    ion-title 搜索
ion-content
  ion-searchbar(placeholder="搜索列表内容...",
    [(ngModel)]="searchText",(ionInput)="onSearch()",
    debounce="500",
    mode="md")
  ion-list
    button(ion-item="",
      *ngFor="let item of items",
      (click)="gotoItemDetail(item)")
      ion-avatar(item-start="")
        img([src]="item.profilePic")
      h2 {{item.name}}
      p {{item.about}}
      ion-note(item-end="",*ngIf="item.about") {{item.about}}

```

`/src/pages/search/search.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { CommonItem } from '../../models/commonItem';
import { ItemsProvider } from '../../providers/items/items';
import { ItemDetailPage } from '../item-detail/item-detail';


@Component({
  selector: 'page-search',
  templateUrl: 'search.html',
})
export class SearchPage {

  items: CommonItem[];
  searchText: string;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider) {
  }

  ionViewDidLoad() {
  }

  onSearch() {
    if(!this.searchText || !this.searchText.trim()){
      this.items = [];
      return;
    }
    this.itemsProvider.getItems({'name_like':this.searchText})
      .subscribe(items => this.items = [...items]);
  }

  gotoItemDetail(item){
    this.navCtrl.push(ItemDetailPage, {
      item: item
    });
  }
}

```

ion-searchbar

[https://ionicframework.com/docs/api/components/searchbar/Searchbar/](https://ionicframework.com/docs/api/components/searchbar/Searchbar/)

## 设置页

Storage 

[https://ionicframework.com/docs/storage/](https://ionicframework.com/docs/storage/)

### 安装 SQLite 插件

```
ionic cordova plugin add cordova-sqlite-storage

npm install --save @ionic/storage --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist

```

引入模块

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
import { ItemsProvider } from '../providers/items/items';
import { ItemCreatePage } from '../pages/item-create/item-create';
import { Camera } from '@ionic-native/camera';
import { ItemDetailPage } from '../pages/item-detail/item-detail';
import { IonicStorageModule } from '@ionic/storage';

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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp, {
      backButtonText: '返回',
    }),
    IonicStorageModule.forRoot()
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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    Api,
    UserService,
    AuthService,
    ItemsProvider,
    Camera
  ]
})
export class AppModule { }

```

设置注入

```ts
import { Storage } from '@ionic/storage';

...
	constructor(private storage: Storage) { }
```

> 注意, 系统本身也有 Storage , 不会有智能提示引入, 要手工处理


### 定义配置服务

`/src/providers/Settings.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';

@Injectable()
export class SettingsService {
  private SETTINGS_KEY: string = '_settings';

  settings: any;

  constructor(public storage: Storage) { }

  setValue(key: string, value: any) {
    this.settings[key] = value;
    return this.storage.set(this.SETTINGS_KEY, this.settings);
  }
  getValue(key: string) {
    if (this.settings) {
      return Promise.resolve(this.settings[key]);
    } else {
      return this.storage.get(this.SETTINGS_KEY)
        .then(settings => {
          this.settings = settings;
          return settings[key];
        });
    }
  }
  getAll() {
    if (this.settings) {
      return Promise.resolve(this.settings);
    } else {
      return this.storage
        .get(this.SETTINGS_KEY)
        .then(settings => {
          this.settings = settings;
          return settings;
        });
    }
  }
  setAll(value: any) {
    this.settings = value;
    return this.storage.set(this.SETTINGS_KEY, value);
  }

  clear(){
    return this.storage.remove(this.SETTINGS_KEY);
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
import { ItemsProvider } from '../providers/items/items';
import { ItemCreatePage } from '../pages/item-create/item-create';
import { Camera } from '@ionic-native/camera';
import { ItemDetailPage } from '../pages/item-detail/item-detail';
import { IonicStorageModule } from '@ionic/storage';
import { SettingsService } from '../providers/Settings.service';

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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp, {
      backButtonText: '返回',
    }),
    IonicStorageModule.forRoot()
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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    Api,
    UserService,
    AuthService,
    ItemsProvider,
    Camera,
    SettingsService
  ]
})
export class AppModule { }

```

### 处理配置页

`/src/pages/settings/settings.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SettingsService } from '../../providers/Settings.service';
import { FormGroup, FormBuilder } from '@angular/forms';


@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html',
})
export class SettingsPage implements OnInit {


  formModel: FormGroup;
  options: any;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public fb: FormBuilder,
    public settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getAll()
      .then(sets => {
        this.options = sets;
        this.buildForm();
      });
  }

  buildForm() {
    this.formModel =  this.fb.group({
      option1: [this.options.option1],
      option2: [this.options.option2],
      option3: [this.options.option3]
    });
  }

  ionViewDidLoad() {
  }

  clear(){
    this.settingsService.clear()
      .then(v=>console.log('配置已清除...'));
  }
}

```

`/src/pages/settings/settings.jade`

```jade
ion-header
  ion-navbar
    ion-title 设置
ion-content
  form([formGroup]="formModel", *ngIf="formModel")
    ion-list
      ion-item
        ion-label 配置1
        ion-toggle(formControlName="option1")
      ion-item
        ion-label 配置2
        ion-input(formControlName="option2")
      ion-item
        ion-label 配置3
        ion-select(formControlName="option3",
          okText="确定",cancelText="取消")
          ion-option(value="1") 1
          ion-option(value="2") 2
          ion-option(value="3") 3
  p
    button(ion-button="", block="", (click)="clear()") 删除配置

```

如果是首次运行会报错...

### 处理默认值

`/src/pages/settings/settings.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SettingsService } from '../../providers/Settings.service';
import { FormGroup, FormBuilder } from '@angular/forms';


@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html',
})
export class SettingsPage implements OnInit {


  formModel: FormGroup;
  options: any;

  _defaultOp = {
    option1: false,
    option2: '',
    option3: 1
  };

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public fb: FormBuilder,
    public settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getAll()
      .then(sets => {
        if(sets){
          this.options = sets;
        } else {
          this.options = this._defaultOp;
          this.settingsService.setAll(this.options);
        }
        this.buildForm();
      });
  }

  buildForm() {
    this.formModel =  this.fb.group({
      option1: [this.options.option1],
      option2: [this.options.option2],
      option3: [this.options.option3]
    });
  }

  ionViewDidLoad() {
  }

  clear(){
    this.settingsService.clear()
      .then(v=>console.log('配置已清除...'));
  }
}

```

### 保存配置

`/src/pages/settings/settings.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SettingsService } from '../../providers/Settings.service';
import { FormGroup, FormBuilder } from '@angular/forms';


@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html',
})
export class SettingsPage implements OnInit {


  formModel: FormGroup;
  options: any;

  _defaultOp = {
    option1: false,
    option2: '',
    option3: 1
  };

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public fb: FormBuilder,
    public settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getAll()
      .then(sets => {
        if (sets) {
          this.options = sets;
        } else {
          this.options = this._defaultOp;
          this.settingsService.setAll(this.options);
        }
        this.buildForm();
      });
  }

  buildForm() {
    this.formModel = this.fb.group({
      option1: [this.options.option1],
      option2: [this.options.option2],
      option3: [this.options.option3]
    });
    this.formModel.valueChanges
      .debounceTime(500)
      .switchMap(v => this.settingsService.setAll(v))
      .subscribe(v => {
        console.log('配置已保存.');
      });
  }

  ionViewDidLoad() {
  }

  clear() {
    this.settingsService.clear()
      .then(v => console.log('配置已清除...'));
  }
}

```

### 处理缺省配置

`/src/providers/appDefaultSettings.ts`

```ts
import { InjectionToken } from '@angular/core';

export const AppDefaultSettings: any = {
  option1: false,
  option2: '缺省配置',
  option3: 1
}

export const APP_DEFAULT_SETTINGS = new InjectionToken<any>('AppDefaultSettings');

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
import { ItemsProvider } from '../providers/items/items';
import { ItemCreatePage } from '../pages/item-create/item-create';
import { Camera } from '@ionic-native/camera';
import { ItemDetailPage } from '../pages/item-detail/item-detail';
import { IonicStorageModule } from '@ionic/storage';
import { SettingsService } from '../providers/Settings.service';
import { AppDefaultSettings, APP_DEFAULT_SETTINGS } from '../providers/appDefaultSettings';

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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp, {
      backButtonText: '返回',
    }),
    IonicStorageModule.forRoot()
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
    CardsPage,
    ItemCreatePage,
    ItemDetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    Api,
    UserService,
    AuthService,
    ItemsProvider,
    Camera,
    { provide: APP_DEFAULT_SETTINGS, useValue: AppDefaultSettings },
    SettingsService
  ]
})
export class AppModule { }

```

`/src/providers/Settings.service.ts`

```ts
import { Injectable, Inject } from '@angular/core';
import { Storage } from '@ionic/storage';
import { AppDefaultSettings, APP_DEFAULT_SETTINGS } from './appDefaultSettings';

@Injectable()
export class SettingsService {
  private SETTINGS_KEY: string = '_settings';


  settings: any;

  constructor(
    public storage: Storage,
    @Inject(APP_DEFAULT_SETTINGS)
    public appDefaultSettings: any) {
      this.settings = Object.assign({}, appDefaultSettings);
      this.storage.get(this.SETTINGS_KEY)
      .then(settings => {
        if(settings){
          this.settings = Object.assign({}, appDefaultSettings, settings);
        }
      });
  }

  setValue(key: string, value: any) {
    this.settings[key] = value;
    return this.storage.set(this.SETTINGS_KEY, this.settings);
  }
  getValue(key: string) {
    if (this.settings) {
      return Promise.resolve(this.settings[key]);
    } else {
      return this.storage.get(this.SETTINGS_KEY)
        .then(settings => {
          this.settings = settings;
          return settings[key];
        });
    }
  }
  getAll() {
    if (this.settings) {
      return Promise.resolve(this.settings);
    } else {
      return this.storage
        .get(this.SETTINGS_KEY)
        .then(settings => {
          this.settings = settings;
          return settings;
        });
    }
  }
  setAll(value: any) {
    this.settings = value;
    return this.storage.set(this.SETTINGS_KEY, value);
  }
  clear() {
    return this.storage.remove(this.SETTINGS_KEY);
  }
}

```

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { TutorialPage } from '../pages/tutorial/tutorial';
import { SettingsService } from '../providers/Settings.service';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage: any = TutorialPage;

  constructor(
    platform: Platform,
    statusBar: StatusBar,
    splashScreen: SplashScreen,
    SettingsService: SettingsService) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();
    });
  }
}


```

`/src/pages/settings/settings.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { SettingsService } from '../../providers/Settings.service';
import { FormGroup, FormBuilder } from '@angular/forms';


@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html',
})
export class SettingsPage implements OnInit {


  formModel: FormGroup;
  options: any;

  _defaultOp = {
    option1: false,
    option2: '',
    option3: 1
  };

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public fb: FormBuilder,
    public settingsService: SettingsService) {
  }

  ngOnInit(): void {
    this.settingsService.getAll()
      .then(sets => {
        if (sets) {
          this.options = sets;
        } else {
          this.options = this._defaultOp;
          this.settingsService.setAll(this.options);
        }
        this.buildForm();
      });
  }

  buildForm() {
    this.formModel = this.fb.group({
      option1: [this.options.option1],
      option2: [this.options.option2],
      option3: [this.options.option3]
    });
    this.formModel.valueChanges
      .debounceTime(500)
      .switchMap(v => this.settingsService.setAll(v))
      .subscribe(v => {
        console.log('配置已保存.');
      });
  }

  ionViewDidLoad() {
  }

  clear() {
    this.settingsService.clear()
      .then(v => console.log('配置已清除...'));
  }
}

```

## 卡片页

`/src/pages/cards/cards.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-cards',
  templateUrl: 'cards.html',
})
export class CardsPage implements OnInit {

  cardItems: any[];
  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }
  ngOnInit(): void {
    this.cardItems = [
      {
        user: {
          avatar: 'assets/img/marty-avatar.png',
          name: 'Marty McFly'
        },
        date: 'November 5, 1955',
        image: 'assets/img/advance-card-bttf.png',
        content: 'Wait a minute. Wait a minute, Doc. Uhhh... Are you telling me that you built a time machine... out of a DeLorean?! Whoa. This is heavy.',
      },
      {
        user: {
          avatar: 'assets/img/sarah-avatar.png.jpeg',
          name: 'Sarah Connor'
        },
        date: 'May 12, 1984',
        image: 'assets/img/advance-card-tmntr.jpg',
        content: 'I face the unknown future, with a sense of hope. Because if a machine, a Terminator, can learn the value of human life, maybe we can too.'
      },
      {
        user: {
          avatar: 'assets/img/ian-avatar.png',
          name: 'Dr. Ian Malcolm'
        },
        date: 'June 28, 1990',
        image: 'assets/img/advance-card-jp.jpg',
        content: 'Your scientists were so preoccupied with whether or not they could, that they didn\'t stop to think if they should.'
      }
    ];
  }

  ionViewDidLoad() {
  }

}

```

`/src/pages/cards/cards.jade`

```jade
ion-header
  ion-navbar
    ion-title 卡片
ion-content
  ion-card(*ngFor="let item of cardItems")
    ion-item
      ion-avatar(item-start="")
        img([src]="item.user.avatar")
      h2 {{item.user.name}}
      p {{item.date}}
    img([src]="item.image")
    ion-card-content
      p {{item.content}}
    ion-row
      ion-col
        button(ion-button="",
          color="primary",
          clear="",
          small="",
          icon-start="")
          ion-icon(name='thumbs-up')
          | 12 Likes
      ion-col
        button(ion-button="",
          color="primary",
          clear="",
          small="",
          icon-start="")
          ion-icon(name='text')
          | 4 Comments
      ion-col(align-self-center="", text-center="")
        ion-note 11h ago

```