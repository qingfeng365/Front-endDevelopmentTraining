# 第7讲 super项目-tabs页与其它页面

## tabs页

`/src/pages/tabs/tabs.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ListPage } from '../list/list';
import { SearchPage } from '../search/search';
import { SettingsPage } from '../settings/settings';
import { CardsPage } from '../cards/cards';

@Component({
  selector: 'page-tabs',
  templateUrl: 'tabs.html',
})
export class TabsPage {

  tab1Root: any = ListPage;
  tab2Root: any = SearchPage;
  tab3Root: any = SettingsPage;
  tab4Root: any = CardsPage;

  tab1Title = "列表";
  tab2Title = "搜索";
  tab3Title = "设置";
  tab4Title = "卡片";

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }

}

```

`/src/pages/tabs/tabs.jade`

```jade
ion-tabs
  ion-tab([root]="tab1Root", [tabTitle]="tab1Title",
    tabIcon="home")
  ion-tab([root]="tab2Root", [tabTitle]="tab2Title",
    tabIcon="search")
  ion-tab([root]="tab3Root", [tabTitle]="tab3Title",
    tabIcon="cog")
  ion-tab([root]="tab4Root", [tabTitle]="tab4Title",
    tabIcon="photos")
```

## 处理列表页

### 准备数据

`/src/mock-data/db.json`

```json
{
  "users": [{
      "id": 1,
      "username": "1111",
      "email": "1111@example.com",
      "password": "1111"
    },
    {
      "id": 2,
      "username": "2222",
      "email": "1111@example.com",
      "password": "2222"
    }
  ],
  "items": [{
      "id": 1,
      "name": "伯特熊",
      "profilePic": "assets/img/speakers/bear.jpg",
      "about": "伯特是一头熊."
    },
    {
      "id": 2,
      "name": "查利猎豹",
      "profilePic": "assets/img/speakers/cheetah.jpg",
      "about": "查利是一头猎豹."
    },
    {
      "id": 3,
      "name": "唐纳德鸭",
      "profilePic": "assets/img/speakers/duck.jpg",
      "about": "唐纳德是一只鸭."
    },
    {
      "id": 4,
      "name": "伊娃鹰",
      "profilePic": "assets/img/speakers/eagle.jpg",
      "about": "伊娃是一只鹰."
    },
    {
      "id": 5,
      "name": "埃莉象",
      "profilePic": "assets/img/speakers/elephant.jpg",
      "about": "埃莉是一头大象."
    },
    {
      "id": 6,
      "name": "莫莉鼠",
      "profilePic": "assets/img/speakers/mouse.jpg",
      "about": "莫莉是一只老鼠."
    },
    {
      "id": 7,
      "name": "保罗狗",
      "profilePic": "assets/img/speakers/puppy.jpg",
      "about": "保罗是一只狗."
    }
  ]
}
```

### 创建模型与服务


通用 item

`/src/models/commonItem.ts`

```ts
export class CommonItem{
  constructor(private fields: any) {
    for (let f in fields) {
      this[f] = fields[f];
    }
  }
}

```

创建服务:

`ionic g provider items`

> 没有参数可以控制不创建目录
> 


`/src/providers/items/items.ts`

```ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Api } from '../api';
import { CommonItem } from '../../models/commonItem';

@Injectable()
export class ItemsProvider {
  private apiUrl = 'items';

  constructor(public api: Api) { }

  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  getItems(params?: any): Observable<CommonItem[]> {
    return this.api.get(this.apiUrl, params)
      .map(res => res.json())
      .catch(this.catchError);
  }
}


```

### 列表页

`/src/pages/list/list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ItemsProvider } from '../../providers/items/items';
import { Observable } from 'rxjs/Rx';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage {
  items: CommonItem[];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider) {
  }

  ionViewDidLoad() {
    this.itemsProvider.getItems()
      .subscribe(items => this.items = [...items]);
  }

}

```

`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    ion-title 列表
    ion-buttons(end="")
      button(ion-button="", icon-only="")
        ion-icon(name="add")
ion-content
  ion-list
    ion-item-sliding(*ngFor="let item of items")
      button(ion-item="")
        ion-avatar(item-start="")
          img([src]="item.profilePic")
        h2 {{item.name}}
        p {{item.about}}
        ion-note(item-end="",*ngIf="item.note") {{item.note}}
      ion-item-options
        button(ion-button="", color="danger") 删除

```

ion-item-sliding

[https://ionicframework.com/docs/api/components/item/ItemSliding/](https://ionicframework.com/docs/api/components/item/ItemSliding/)

ion-item-options

[https://ionicframework.com/docs/api/components/item/ItemOptions/](https://ionicframework.com/docs/api/components/item/ItemOptions/)


>　ion-item-sliding 使用场景: 需要使用侧滑按钮
>  做为 ion-list 与 ion-item 的中间容器
> 
> ion-item-options 只能为 ion-item-sliding 的子组件
> 

ion-avatar

[https://ionicframework.com/docs/api/components/avatar/Avatar/](https://ionicframework.com/docs/api/components/avatar/Avatar/)

> ion-avatar 的父容器为 ion-item
> 
> 圆形图片
> 

ion-note

[https://ionicframework.com/docs/api/components/note/Note/](https://ionicframework.com/docs/api/components/note/Note/)

> ion-note 的父容器为 ion-item
> 


### 创建新增页面

`ionic g page itemCreate --no-module`

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
    ItemCreatePage
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
    CardsPage,
    ItemCreatePage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    Api,
    UserService,
    AuthService,
    ItemsProvider

  ]
})
export class AppModule {}

```

### 列表新增按钮

`/src/pages/list/list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ModalController } from 'ionic-angular';
import { ItemsProvider } from '../../providers/items/items';
import { Observable } from 'rxjs/Rx';
import { ItemCreatePage } from '../item-create/item-create';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage {
  items: CommonItem[];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider,
    public modalCtrl: ModalController) {
  }

  ionViewDidLoad() {
    this.itemsProvider.getItems()
      .subscribe(items => this.items = [...items]);
  }

  addItem() {
    const addModal = this.modalCtrl.create(ItemCreatePage);
    addModal.present();
  }
}

```

ModalController 

[https://ionicframework.com/docs/api/components/modal/ModalController/](https://ionicframework.com/docs/api/components/modal/ModalController/)


`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    ion-title 列表
    ion-buttons(end="")
      button(ion-button="", icon-only="", (click)="addItem()")
        ion-icon(name="add")
ion-content
  ion-list
    ion-item-sliding(*ngFor="let item of items")
      button(ion-item="")
        ion-avatar(item-start="")
          img([src]="item.profilePic")
        h2 {{item.name}}
        p {{item.about}}
        ion-note(item-end="",*ngIf="item.about") {{item.about}}
      ion-item-options
        button(ion-button="", color="danger") 删除

```

### 处理新增页面的关闭功能

`/src/pages/item-create/item-create.jade`

```jade
ion-header
  ion-navbar
    ion-title 新增
    ion-buttons(start="")
      button(ion-button="",(click)="cancel()",color="primary")
        | 取消
    ion-buttons(end="")
      button(ion-button="",(click)="ok()",color="primary",strong="")
        | 确定
ion-content

```

`/src/pages/item-create/item-create.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';

@Component({
  selector: 'page-item-create',
  templateUrl: 'item-create.html',
})
export class ItemCreatePage {

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController
  ) {
  }

  ionViewDidLoad() {
  }
  cancel() {
    this.viewCtrl.dismiss();
  }

  ok() {
    this.viewCtrl.dismiss();
  }
}

```


ViewController

[https://ionicframework.com/docs/api/navigation/ViewController/](https://ionicframework.com/docs/api/navigation/ViewController/)


### 安装原生插件的步骤

步骤1: 安装　cordova　插件

```
ionic cordova plugin add cordova-plugin-camera
```

>　这一步的目的是写配置文件　`/config.xml`,
>　以及 plugins 目录

步骤2: 安装插件的　ionic　封装文件

```
npm install @ionic-native/camera --save --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
```

步骤3: 设置提供器

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
    ItemCreatePage
  ],
  imports: [
    BrowserModule,
    HttpModule,
    IonicModule.forRoot(MyApp, {
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
    CardsPage,
    ItemCreatePage
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

步骤4: 依赖注入

```ts
 constructor(
    public camera: Camera
  )
```


Camera

[https://ionicframework.com/docs/native/camera/](https://ionicframework.com/docs/native/camera/)

### 实现新增表单

> ReactiveFormsModule 不需要引入
> 
> 因为 IonicModule 已经引入 CommonModule, FormsModule,                    ReactiveFormsModule,
> 

#### 准备模型与视图

`/src/pages/item-create/item-create.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Camera } from '@ionic-native/camera';

@Component({
  selector: 'page-item-create',
  templateUrl: 'item-create.html',
})
export class ItemCreatePage {
  formModel: FormGroup;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    private fb: FormBuilder,
    public camera: Camera
  ) {
    this.formModel = fb.group({
      profilePic: [''],
      name: ['', Validators.required],
      about: ['']
    });
  }

  ionViewDidLoad() {
  }
  cancel() {
    this.viewCtrl.dismiss();
  }

  ok() {
    this.viewCtrl.dismiss();
  }
}

```

`/src/pages/item-create/item-create.jade`

```jade
ion-header
  ion-navbar
    ion-title 新增
    ion-buttons(start="")
      button(ion-button="",(click)="cancel()",color="primary")
        | 取消
    ion-buttons(end="")
      button(ion-button="",(click)="ok()",color="primary",strong="",
      [disabled]="!formModel.valid")
        | 确定
ion-content
  form([formGroup]="formModel")
    input(type="file")
    ion-list
      ion-item
        ion-input(type="text",
          placeholder="名称",
          formControlName="name")
      ion-item
        ion-input(type="text",
          placeholder="描述",
          formControlName="about")

```

### 处理图片样式

`/src/pages/item-create/item-create.jade`

```jade
ion-header
  ion-navbar
    ion-title 新增
    ion-buttons(start="")
      button(ion-button="",(click)="cancel()",color="primary")
        | 取消
    ion-buttons(end="")
      button(ion-button="",(click)="ok()",color="primary",strong="",
      [disabled]="!formModel.valid")
        | 确定
ion-content
  form([formGroup]="formModel")
    input.file-input(type="file",#fileInput="")
    .profile-image-wrapper
      .profile-image-placeholder(
        *ngIf="!formModel.controls.profilePic.value")
        ion-icon(name="add")
        div 新增图片
      .profile-image(
        *ngIf="formModel.controls.profilePic.value"
      )
    ion-list
      ion-item
        ion-input(type="text",
          placeholder="名称",
          formControlName="name")
      ion-item
        ion-input(type="text",
          placeholder="描述",
          formControlName="about")


```

`/src/pages/item-create/item-create.scss`

```scss
page-item-create {
  .profile-image-wrapper {
    text-align: center;
    margin: 20px 0;
    .profile-image-placeholder {
      display: inline-block;
      background-color: #eee;
      width: 96px;
      height: 96px;
      border-radius: 50%;
      font-size: 12px;
      ion-icon {
        font-size: 44px;
        margin-bottom: -10px;
        margin-top: 10px;
      }
    }
    .profile-image {
      width: 96px;
      height: 96px;
      border-radius: 50%;
      display: inline-block;
      background-repeat: no-repeat;
      background-size: cover;
      background-position: center;
    }
  }
  .file-input {
    display: block;
    visibility: hidden;
    height: 0px;
  }
}
```

### 处理获取图片

`/src/pages/item-create/item-create.ts`

```ts
import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Camera } from '@ionic-native/camera';

@Component({
  selector: 'page-item-create',
  templateUrl: 'item-create.html',
})
export class ItemCreatePage {

  @ViewChild('fileInput')
  fileInput;

  formModel: FormGroup;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    private fb: FormBuilder,
    public camera: Camera
  ) {
    this.formModel = fb.group({
      profilePic: [''],
      name: ['', Validators.required],
      about: ['']
    });
  }

  ionViewDidLoad() {
    this.formModel.valueChanges
      .debounceTime(1000)
      .subscribe(
      v => console.log(v)
      );
  }
  cancel() {
    this.viewCtrl.dismiss();
  }

  ok() {
    this.viewCtrl.dismiss();
  }

  getPicture() {
    if (Camera.installed()) {
      this.camera.getPicture({
        destinationType: this.camera.DestinationType.DATA_URL,
        targetWidth: 96,
        targetHeight: 96
      }).then((data) => {
        this.formModel.patchValue({ 'profilePic': 'data:image/jpg;base64,' + data });
      }, (err) => {
        alert('无法拍照.');
      })
    } else {
      this.fileInput.nativeElement.click();
    }
  }

  onImgFileSelect(event) {
    console.log(event);
    let reader = new FileReader();
    reader.onload = (readerEvent) => {
      console.log(readerEvent);
      let imageData = (readerEvent.target as any).result;
      this.formModel.patchValue({ 'profilePic': imageData });
    };
    reader.readAsDataURL(event.target.files[0]);
  }
}

```

FileReader

[https://developer.mozilla.org/zh-CN/docs/Web/API/FileReader](https://developer.mozilla.org/zh-CN/docs/Web/API/FileReader)


`/src/pages/item-create/item-create.jade`

```jade
ion-header
  ion-navbar
    ion-title 新增
    ion-buttons(start="")
      button(ion-button="",(click)="cancel()",color="primary")
        | 取消
    ion-buttons(end="")
      button(ion-button="",(click)="ok()",color="primary",strong="",
      [disabled]="!formModel.valid")
        | 确定
ion-content
  form([formGroup]="formModel")
    input.file-input(type="file",#fileInput="",
      name="fileinput",
      (change)="onImgFileSelect($event)")
    .profile-image-wrapper((click)="getPicture()")
      .profile-image-placeholder(
        *ngIf="!formModel.controls.profilePic.value")
        ion-icon(name="add")
        div 新增图片
      .profile-image(
        *ngIf="formModel.controls.profilePic.value"
      )
    ion-list
      ion-item
        ion-input(type="text",
          placeholder="名称",
          formControlName="name")
      ion-item
        ion-input(type="text",
          placeholder="描述",
          formControlName="about")

```

### 显示图片

`/src/pages/item-create/item-create.jade`

```jade
ion-header
  ion-navbar
    ion-title 新增
    ion-buttons(start="")
      button(ion-button="",(click)="cancel()",color="primary")
        | 取消
    ion-buttons(end="")
      button(ion-button="",(click)="ok()",color="primary",strong="",
      [disabled]="!formModel.valid")
        | 确定
ion-content
  form([formGroup]="formModel")
    input.file-input(type="file",#fileInput="",
      name="fileinput",
      (change)="onImgFileSelect($event)")
    .profile-image-wrapper((click)="getPicture()")
      .profile-image-placeholder(
        *ngIf="!formModel.controls.profilePic.value")
        ion-icon(name="add")
        div 新增图片
      .profile-image(
        *ngIf="formModel.controls.profilePic.value",
        [style.background-image]="'url('+formModel.controls.profilePic.value+')'"
      )
    ion-list
      ion-item
        ion-input(type="text",
          placeholder="名称",
          formControlName="name")
      ion-item
        ion-input(type="text",
          placeholder="描述",
          formControlName="about")

```

###　回传结果

`/src/pages/item-create/item-create.ts`

```ts
import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, ViewController } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Camera } from '@ionic-native/camera';

@Component({
  selector: 'page-item-create',
  templateUrl: 'item-create.html',
})
export class ItemCreatePage {

  @ViewChild('fileInput')
  fileInput;

  formModel: FormGroup;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public viewCtrl: ViewController,
    private fb: FormBuilder,
    public camera: Camera
  ) {
    this.formModel = fb.group({
      profilePic: [''],
      name: ['', Validators.required],
      about: ['']
    });
  }

  ionViewDidLoad() {
    this.formModel.valueChanges
      .debounceTime(1000)
      .subscribe(
      v => console.log(v)
      );
  }
  cancel() {
    this.viewCtrl.dismiss();
  }

  ok() {
    if (this.formModel.valid) {
      this.viewCtrl.dismiss(this.formModel.value);
    }
  }

  getPicture() {
    if (Camera.installed()) {
      this.camera.getPicture({
        destinationType: this.camera.DestinationType.DATA_URL,
        targetWidth: 96,
        targetHeight: 96
      }).then((data) => {
        this.formModel.patchValue({ 'profilePic': 'data:image/jpg;base64,' + data });
      }, (err) => {
        alert('无法拍照.');
      })
    } else {
      this.fileInput.nativeElement.click();
    }
  }

  onImgFileSelect(event) {
    console.log(event);
    let reader = new FileReader();
    reader.onload = (readerEvent) => {
      console.log(readerEvent);
      let imageData = (readerEvent.target as any).result;
      this.formModel.patchValue({ 'profilePic': imageData });
    };
    reader.readAsDataURL(event.target.files[0]);
  }
}

```

### 保存

`/src/providers/items/items.ts`

```ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Api } from '../api';
import { CommonItem } from '../../models/commonItem';

@Injectable()
export class ItemsProvider {
  private apiUrl = 'items';

  constructor(public api: Api) { }

  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  getItems(params?: any): Observable<CommonItem[]> {
    return this.api.get(this.apiUrl, params)
      .map(res => res.json())
      .catch(this.catchError);
  }

  addItem(item: CommonItem): Observable<CommonItem> {
    return this.api
      .post(this.apiUrl, item)
      .map(res => res.json())
      .do(v => console.log(v))
      .catch(this.catchError);
  }

}


```

`/src/pages/list/list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ModalController} from 'ionic-angular';
import { ItemsProvider } from '../../providers/items/items';
import { Observable } from 'rxjs/Rx';
import { ItemCreatePage } from '../item-create/item-create';
import { CommonItem } from '../../models/commonItem';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage {
  items: CommonItem[];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider,
    public modalCtrl: ModalController) {
  }

  ionViewDidLoad() {
    this.itemsProvider.getItems()
      .subscribe(items => this.items = [...items]);
  }

  addItem() {
    const addModal = this.modalCtrl.create(ItemCreatePage);
    addModal.present();
    addModal.onDidDismiss(item => {
      if (item) {
        this.itemsProvider.addItem(item)
          .subscribe(v => {
            this.items.push(item);
          });
      }
    })
  }

}


```

### 处理删除

`/src/providers/items/items.ts`

```ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Api } from '../api';
import { CommonItem } from '../../models/commonItem';

@Injectable()
export class ItemsProvider {
  private apiUrl = 'items';

  constructor(public api: Api) { }

  catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  getItems(params?: any): Observable<CommonItem[]> {
    return this.api.get(this.apiUrl, params)
      .map(res => res.json())
      .catch(this.catchError);
  }

  addItem(item: CommonItem): Observable<CommonItem> {
    return this.api
      .post(this.apiUrl, item)
      .map(res => res.json())
      .do(v => console.log(v))
      .catch(this.catchError);
  }

  deleteItemById(id: number | string): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.api
    .delete(url)
    .map(res => res.json())
    .do(v => console.log(v))
    .catch(this.catchError);
  }
}

```

`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    ion-title 列表
    ion-buttons(end="")
      button(ion-button="", icon-only="", (click)="addItem()")
        ion-icon(name="add")
ion-content
  ion-list
    ion-item-sliding(*ngFor="let item of items")
      button(ion-item="")
        ion-avatar(item-start="")
          img([src]="item.profilePic")
        h2 {{item.name}}
        p {{item.about}}
        ion-note(item-end="",*ngIf="item.about") {{item.about}}
      ion-item-options
        button(ion-button="", color="danger",
          (click)="deleteItem(item)") 删除

```

`/src/pages/list/list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ModalController} from 'ionic-angular';
import { ItemsProvider } from '../../providers/items/items';
import { Observable } from 'rxjs/Rx';
import { ItemCreatePage } from '../item-create/item-create';
import { CommonItem } from '../../models/commonItem';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage {
  items: CommonItem[];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider,
    public modalCtrl: ModalController) {
  }

  ionViewDidLoad() {
    this.itemsProvider.getItems()
      .subscribe(items => this.items = [...items]);
  }

  addItem() {
    const addModal = this.modalCtrl.create(ItemCreatePage);
    addModal.present();
    addModal.onDidDismiss(item => {
      if (item) {
        this.itemsProvider.addItem(item)
          .subscribe(v => {
            this.items.push(item);
          });
      }
    })
  }

  deleteItem(item) {
    this.itemsProvider.deleteItemById(item.id)
      .subscribe(v => {
        this.items.splice(this.items.indexOf(item), 1);
      })
  }
}

```

## 详情页

`ionic g page itemDetail --no-module`

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

导航到详情页

`/src/pages/list/list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ModalController} from 'ionic-angular';
import { ItemsProvider } from '../../providers/items/items';
import { Observable } from 'rxjs/Rx';
import { ItemCreatePage } from '../item-create/item-create';
import { CommonItem } from '../../models/commonItem';
import { ItemDetailPage } from '../item-detail/item-detail';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
})
export class ListPage {
  items: CommonItem[];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public itemsProvider: ItemsProvider,
    public modalCtrl: ModalController) {
  }

  ionViewDidLoad() {
    this.itemsProvider.getItems()
      .subscribe(items => this.items = [...items]);
  }

  addItem() {
    const addModal = this.modalCtrl.create(ItemCreatePage);
    addModal.present();
    addModal.onDidDismiss(item => {
      if (item) {
        this.itemsProvider.addItem(item)
          .subscribe(v => {
            this.items.push(item);
          });
      }
    })
  }

  deleteItem(item) {
    this.itemsProvider.deleteItemById(item.id)
      .subscribe(v => {
        this.items.splice(this.items.indexOf(item), 1);
      })
  }

  gotoItemDetail(item){
    this.navCtrl.push(ItemDetailPage, {
      item: item
    });
  }
}

```

`/src/pages/list/list.jade`

```jade
ion-header
  ion-navbar
    ion-title 列表
    ion-buttons(end="")
      button(ion-button="", icon-only="", (click)="addItem()")
        ion-icon(name="add")
ion-content
  ion-list
    ion-item-sliding(*ngFor="let item of items")
      button(ion-item="",(click)="gotoItemDetail(item)")
        ion-avatar(item-start="")
          img([src]="item.profilePic")
        h2 {{item.name}}
        p {{item.about}}
        ion-note(item-end="",*ngIf="item.about") {{item.about}}
      ion-item-options
        button(ion-button="", color="danger",
          (click)="deleteItem(item)") 删除

```

`/src/pages/item-detail/item-detail.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { CommonItem } from '../../models/commonItem';

@Component({
  selector: 'page-item-detail',
  templateUrl: 'item-detail.html',
})
export class ItemDetailPage implements OnInit {
  item: CommonItem;

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams) {
  }

  ngOnInit(){
    this.item = this.navParams.get('item')
  }

  ionViewDidLoad() {

  }

}
```

>　注意: 初始化数据不能放到 ionViewDidLoad , 因此时页面已经生成

`/src/pages/item-detail/item-detail.jade`

```jade
ion-header
  ion-navbar
    ion-title {{item.name}}
ion-content
  .item-profile(text-center="",
    [style.background-image]="'url(' + item.profilePic + ')'")
  .item-detail(padding="")
    h2 {{item.name}}
    p {{item.about}}
```

> 显示图片用 背景模式, 比较方便,适用性好

`/src/pages/item-detail/item-detail.scss`

```css
page-item-detail {
  .item-profile {
    width: 100%;
    background-position: center center;
    background-size: cover;
    height: 250px;
  }
  .item-detail {
    width: 100%;
    background: white;
    position: absolute;
  }
}
```