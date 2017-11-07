# 第11讲 conference项目-日程其它功能

## 处理主题选择页

```bash
ionic g page ScheduleFilter
```

### `/src/app/app.module.ts`

```ts
import { TabsPage } from './../pages/tabs/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
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
import { ConferenceService } from './service/conference.service';
import { UserService } from './service/user.service';
import { ScheduleFilterPage } from '../pages/schedule-filter/schedule-filter';
@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage,
    ScheduleFilterPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
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
    AboutPage,
    ScheduleFilterPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    ConferenceService,
    UserService
  ]
})
export class AppModule { }

```

### `/src/pages/schedule-filter/schedule-filter.jade`

```jade
ion-header
  ion-toolbar
    ion-buttons(start="")
      button(ion-button="",(click)="dismiss()") 取消
    ion-title 过滤课程
    ion-buttons(end="")
      button(ion-button="", (click)="applyFilters()",
        strong="") 确定
ion-content.outer-content
  ion-list
    ion-list-header 主题
    ion-item(*ngFor="let track of tracks",
      [attr.track]="track.name | lowercase")
      span.dot(item-start="")
      ion-label {{track.name}}
      ion-toggle([(ngModel)]="track.isChecked",
        color="secondary")
  ion-list
    button.reset-filters(ion-item="",
      (click)="resetFilters()",
      detail-none="",
      ) 重置主题

```

> detail-none : 取消 ios 的 ion-item 默认右侧的小箭头


`/src/pages/schedule-filter/schedule-filter.ts`

```ts
import { Component } from '@angular/core';
import { IonicPage, NavParams, ViewController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';

@IonicPage()
@Component({
  selector: 'page-schedule-filter',
  templateUrl: 'schedule-filter.html',
})
export class ScheduleFilterPage {
  tracks: Array<{ name: string, isChecked: boolean }> = [];

  constructor(
    public conferenceService: ConferenceService,
    public navParams: NavParams,
    public viewCtrl: ViewController) {
    let excludedTrackNames = this.navParams.data;
    console.log('excludedTrackNames:',excludedTrackNames);
    this.conferenceService
      .getTracks()
      .subscribe((trackNames: string[]) => {
        console.log('getTracks:', trackNames);
        trackNames.forEach(trackName => {
          this.tracks.push({
            name: trackName,
            isChecked: (excludedTrackNames.indexOf(trackName) === -1)
          });
        });
      });
  }

  ionViewDidLoad() {
  }
  resetFilters() {
    this.tracks.forEach(track => {
      track.isChecked = true;
    });
  }
  applyFilters() {
    let excludedTrackNames = this.tracks.filter(c => !c.isChecked).map(c => c.name);
    this.dismiss(excludedTrackNames);
  }
  dismiss(data?: any) {
    this.viewCtrl.dismiss(data);
  }
}

```

### `/src/pages/schedule/schedule.ts`

处理调用

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  // @ViewChild('scheduleList', { read: List }) 
  // scheduleList: List;
  @ViewChild(List) 
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() { 
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(sessionData: any) { }
  addFavorite(slidingItem: ItemSliding, sessionData: any) { }
  removeFavorite(slidingItem: ItemSliding, sessionData: any) { }
}

```

测试功能是否正常

### 增加样式

`/src/pages/schedule-filter/schedule-filter.scss`

```css
page-schedule-filter {
  .reset-filters {
    color: color($colors, danger);
  }
  @each $track,
  $value in auxiliary-categories() {
    ion-item[track=#{$track}] .dot {
      height: 10px;
      display: inline-block;
      width: 10px;
      background-color: $value;
      border-radius: 5px;
      margin-right: 10px;
    }
  }
}
```

## 增加关注

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController, AlertController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { UserService } from '../../app/service/user.service';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  // @ViewChild('scheduleList', { read: List }) 
  // scheduleList: List;
  @ViewChild(List)
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
    public user: UserService,
    public alertCtrl: AlertController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() {
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(sessionData: any) { }

  addFavorite(slidingItem: ItemSliding, sessionData: any) {
    this.user.addFavorite(sessionData.name);
    let alert = this.alertCtrl.create({
      title: '已新增关注',
      buttons: [{
        text: '确定',
        handler: () => {
          slidingItem.close();
        }
      }]
    });
    alert.present();
  }
  removeFavorite(slidingItem: ItemSliding, sessionData: any) { }
}

```

## 取消关注

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController, AlertController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { UserService } from '../../app/service/user.service';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  // @ViewChild('scheduleList', { read: List }) 
  // scheduleList: List;
  @ViewChild(List)
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
    public user: UserService,
    public alertCtrl: AlertController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() {
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(sessionData: any) { }

  addFavorite(slidingItem: ItemSliding, sessionData: any) {
    this.user.addFavorite(sessionData.name);
    let alert = this.alertCtrl.create({
      title: '已新增关注',
      buttons: [{
        text: '确定',
        handler: () => {
          slidingItem.close();
        }
      }]
    });
    alert.present();
  }
  removeFavorite(slidingItem: ItemSliding, sessionData: any) {
    let alert = this.alertCtrl.create({
      title: '取消关注',
      message: '确认取消关注吗?',
      buttons: [
        {
          text: '取消',
          handler: () => {
            slidingItem.close();
          }
        },
        {
          text: '确定',
          handler: () => {
            this.user.removeFavorite(sessionData.name);
            this.updateSchedule();
            slidingItem.close();
          }
        }
      ]
    });
    alert.present();
   }
}

```

## 已增加关注再次关注的处理

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController, AlertController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { UserService } from '../../app/service/user.service';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  // @ViewChild('scheduleList', { read: List }) 
  // scheduleList: List;
  @ViewChild(List)
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
    public user: UserService,
    public alertCtrl: AlertController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() {
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(sessionData: any) { }

  addFavorite(slidingItem: ItemSliding, sessionData: any) {
    if (this.user.hasFavorite(sessionData.name)) {
      this.removeFavorite(slidingItem, sessionData, '该会议已关注');      
    } else {
      this.user.addFavorite(sessionData.name);
      let alert = this.alertCtrl.create({
        title: '已新增关注',
        buttons: [{
          text: '确定',
          handler: () => {
            slidingItem.close();
          }
        }]
      });

      alert.present();
    }
  }
  removeFavorite(slidingItem: ItemSliding, sessionData: any,
    title?: string) {
    let alert = this.alertCtrl.create({
      title: title || '取消关注',
      message: '确认取消关注吗?',
      buttons: [
        {
          text: '取消',
          handler: () => {
            slidingItem.close();
          }
        },
        {
          text: '确定',
          handler: () => {
            this.user.removeFavorite(sessionData.name);
            this.updateSchedule();
            slidingItem.close();
          }
        }
      ]
    });
    alert.present();
  }
}

```

## 增加下拉刷新功能

### `/src/pages/schedule/schedule.jade`

```jade
ion-header(no-border="")
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-segment([(ngModel)]="segment",
      (ionChange)="updateSchedule()")
      ion-segment-button(value="all") 全部
      ion-segment-button(value="favorites") 关注
    ion-buttons(end="")
      button(ion-button="",
        icon-only="",
        (click)="presentFilter()")
        ion-icon(ios="ios-options-outline",md="md-options")
  ion-toolbar
    ion-searchbar(color="primary",
      [(ngModel)]="queryText",
      (ionInput)="updateSchedule()",
      placeholder="搜索...")
ion-content
  ion-refresher((ionRefresh)="doRefresh($event)")
    ion-refresher-content
  ion-list(#scheduleList="",[hidden]="shownSessions === 0")
    ion-item-group(*ngFor="let group of groups",
      [hidden]="group.hide")
      ion-item-divider(sticky="")
        ion-label {{group.time}}
      ion-item-sliding(*ngFor="let session of group.sessions",
        [hidden]="session.hide",
        #slidingItem="",
        [attr.track]="session.tracks[0] | lowercase")
        button(ion-item="",(click)="goToSessionDetail(session)")
          h3 {{session.name}}
          p
            | {{session.timeStart}} &mdash;
            | {{session.timeEnd}}:
            | {{session.location}}
        ion-item-options
          button(ion-button="",color="favorite",
            (click)="addFavorite(slidingItem, session)",
            *ngIf="segment === 'all'") 关注
          button(ion-button="",color="danger",
            (click)="removeFavorite(slidingItem, session)",
            *ngIf="segment === 'favorites'") 取消关注
  ion-list-header([hidden]="shownSessions > 0")
    | 没有找到会议信息...
  ion-fab(bottom="", right="", #fab="")
    button(ion-fab="")
      ion-icon(name="share")
    ion-fab-list(side="top")
      button(ion-fab="", color="vimeo")
        ion-icon(name="logo-vimeo")
      button(ion-fab="", color="google")
        ion-icon(name="logo-googleplus")
      button(ion-fab="", color="twitter")
        ion-icon(name="logo-twitter")
      button(ion-fab="", color="facebook")
        ion-icon(name="logo-facebook")

```
## 处理分享按钮

### `/src/pages/schedule/schedule.jade`

```jade
ion-header(no-border="")
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-segment([(ngModel)]="segment",
      (ionChange)="updateSchedule()")
      ion-segment-button(value="all") 全部
      ion-segment-button(value="favorites") 关注
    ion-buttons(end="")
      button(ion-button="",
        icon-only="",
        (click)="presentFilter()")
        ion-icon(ios="ios-options-outline",md="md-options")
  ion-toolbar
    ion-searchbar(color="primary",
      [(ngModel)]="queryText",
      (ionInput)="updateSchedule()",
      placeholder="搜索...")
ion-content
  ion-refresher((ionRefresh)="doRefresh($event)")
    ion-refresher-content
  ion-list(#scheduleList="",[hidden]="shownSessions === 0")
    ion-item-group(*ngFor="let group of groups",
      [hidden]="group.hide")
      ion-item-divider(sticky="")
        ion-label {{group.time}}
      ion-item-sliding(*ngFor="let session of group.sessions",
        [hidden]="session.hide",
        #slidingItem="",
        [attr.track]="session.tracks[0] | lowercase")
        button(ion-item="",(click)="goToSessionDetail(session)")
          h3 {{session.name}}
          p
            | {{session.timeStart}} &mdash;
            | {{session.timeEnd}}:
            | {{session.location}}
        ion-item-options
          button(ion-button="",color="favorite",
            (click)="addFavorite(slidingItem, session)",
            *ngIf="segment === 'all'") 关注
          button(ion-button="",color="danger",
            (click)="removeFavorite(slidingItem, session)",
            *ngIf="segment === 'favorites'") 取消关注
  ion-list-header([hidden]="shownSessions > 0")
    | 没有找到会议信息...
  ion-fab(bottom="", right="", #fab="")
    button(ion-fab="")
      ion-icon(name="share")
    ion-fab-list(side="top")
      button(ion-fab="", color="vimeo",(click)="openSocial('vimeo',fab)")
        ion-icon(name="logo-vimeo")
      button(ion-fab="", color="google",(click)="openSocial('google',fab)")
        ion-icon(name="logo-googleplus")
      button(ion-fab="", color="twitter",(click)="openSocial('twitter',fab)")
        ion-icon(name="logo-twitter")
      button(ion-fab="", color="facebook",(click)="openSocial('facebook',fab)")
        ion-icon(name="logo-facebook")

```

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController, AlertController, Refresher, ToastController, FabContainer, LoadingController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { UserService } from '../../app/service/user.service';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  // @ViewChild('scheduleList', { read: List }) 
  // scheduleList: List;
  @ViewChild(List)
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
    public user: UserService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController,
    public loadingCtrl: LoadingController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() {
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(sessionData: any) { }

  addFavorite(slidingItem: ItemSliding, sessionData: any) {
    if (this.user.hasFavorite(sessionData.name)) {
      this.removeFavorite(slidingItem, sessionData, '该会议已关注');
    } else {
      this.user.addFavorite(sessionData.name);
      let alert = this.alertCtrl.create({
        title: '已新增关注',
        buttons: [{
          text: '确定',
          handler: () => {
            slidingItem.close();
          }
        }]
      });

      alert.present();
    }
  }
  removeFavorite(slidingItem: ItemSliding, sessionData: any,
    title?: string) {
    let alert = this.alertCtrl.create({
      title: title || '取消关注',
      message: '确认取消关注吗?',
      buttons: [
        {
          text: '取消',
          handler: () => {
            slidingItem.close();
          }
        },
        {
          text: '确定',
          handler: () => {
            this.user.removeFavorite(sessionData.name);
            this.updateSchedule();
            slidingItem.close();
          }
        }
      ]
    });
    alert.present();
  }
  doRefresh(refresher: Refresher) {
    console.log(refresher);
    this.confData
      .getTimeline(
      this.dayIndex,
      this.queryText,
      this.excludeTracks,
      this.segment)
      .subscribe((data: any) => {
        this.shownSessions = data.shownSessions;
        this.groups = data.groups;

        setTimeout(() => {
          refresher.complete();
          const toast = this.toastCtrl.create({
            message: '数据已更新.',
            duration: 3000
          });
          toast.present();
        }, 1000);
      });
  }
  openSocial(network: string, fab: FabContainer) {
    let loading = this.loadingCtrl.create({
      content: `分享到 ${network}`,
      duration: (Math.random() * 1000) + 500
    });
    loading.onWillDismiss(() => {
      fab.close();
    });
    loading.present();
  }
}

```

## 处理详情页面

`ionic g page SessionDetail --no-module`

### `/src/app/app.module.ts`

```ts
import { TabsPage } from './../pages/tabs/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
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
import { ConferenceService } from './service/conference.service';
import { UserService } from './service/user.service';
import { ScheduleFilterPage } from '../pages/schedule-filter/schedule-filter';
import { SessionDetailPage } from '../pages/session-detail/session-detail';
@NgModule({
  declarations: [
    MyApp,
    HomePage,
    TutorialPage,
    TabsPage,
    SchedulePage,
    SpeakerListPage,
    MapPage,
    AboutPage,
    ScheduleFilterPage,
    SessionDetailPage
  ],
  imports: [
    BrowserModule,
    HttpModule,
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
    AboutPage,
    ScheduleFilterPage,
    SessionDetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    ConferenceService,
    UserService
  ]
})
export class AppModule { }

```

### `/src/pages/session-detail/session-detail.jade`

```jade
ion-header
  ion-navbar
    ion-title(*ngIf="session") {{session.name}}
ion-content(padding="")
  div(*ngIf="session")
    h1 {{session.name}}
    h4(*ngFor="let speaker of session?.speakers")
      | {{speaker.name}}
    p {{session.timeStart}} - {{session.timeEnd}}
    p {{session.location}}
    p {{session.description}}

```

### `/src/pages/session-detail/session-detail.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-session-detail',
  templateUrl: 'session-detail.html',
})
export class SessionDetailPage {
  session: any;
  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams) {
      this.session = navParams.get('session');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SessionDetailPage');
  }

}

```

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List, ModalController, AlertController, Refresher, ToastController, FabContainer, LoadingController } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { ScheduleFilterPage } from '../schedule-filter/schedule-filter';
import { UserService } from '../../app/service/user.service';
import { SessionDetailPage } from '../session-detail/session-detail';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  @ViewChild(List)
  scheduleList: List;

  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';
  dayIndex = 0;
  excludeTracks: any = [];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public confData: ConferenceService,
    public modalCtrl: ModalController,
    public user: UserService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController,
    public loadingCtrl: LoadingController,
  ) {
  }
  ngOnInit(): void {
  }
  ionViewDidLoad() {
    this.updateSchedule();
  }
  updateSchedule() {
    // 如果处于已滑出按钮状态,要先关闭
    this.scheduleList && this.scheduleList.closeSlidingItems();

    this.confData.getTimeline(this.dayIndex, this.queryText, this.excludeTracks, this.segment).subscribe((data: any) => {
      this.shownSessions = data.shownSessions;
      this.groups = data.groups;
    });
  }
  presentFilter() {
    let modal = this.modalCtrl.create(ScheduleFilterPage, this.excludeTracks);
    modal.present();

    modal.onWillDismiss((data: any[]) => {
      if (data) {
        this.excludeTracks = data;
        this.updateSchedule();
      }
    });
  }
  goToSessionDetail(session: any) {
    this.navCtrl.push(SessionDetailPage,
      { session: session });
  }

  addFavorite(slidingItem: ItemSliding, sessionData: any) {
    if (this.user.hasFavorite(sessionData.name)) {
      this.removeFavorite(slidingItem, sessionData, '该会议已关注');
    } else {
      this.user.addFavorite(sessionData.name);
      let alert = this.alertCtrl.create({
        title: '已新增关注',
        buttons: [{
          text: '确定',
          handler: () => {
            slidingItem.close();
          }
        }]
      });

      alert.present();
    }
  }
  removeFavorite(slidingItem: ItemSliding, sessionData: any,
    title?: string) {
    let alert = this.alertCtrl.create({
      title: title || '取消关注',
      message: '确认取消关注吗?',
      buttons: [
        {
          text: '取消',
          handler: () => {
            slidingItem.close();
          }
        },
        {
          text: '确定',
          handler: () => {
            this.user.removeFavorite(sessionData.name);
            this.updateSchedule();
            slidingItem.close();
          }
        }
      ]
    });
    alert.present();
  }
  doRefresh(refresher: Refresher) {
    console.log(refresher);
    this.confData
      .getTimeline(
      this.dayIndex,
      this.queryText,
      this.excludeTracks,
      this.segment)
      .subscribe((data: any) => {
        this.shownSessions = data.shownSessions;
        this.groups = data.groups;

        setTimeout(() => {
          refresher.complete();
          const toast = this.toastCtrl.create({
            message: '数据已更新.',
            duration: 3000
          });
          toast.present();
        }, 1000);
      });
  }
  openSocial(network: string, fab: FabContainer) {
    let loading = this.loadingCtrl.create({
      content: `分享到 ${network}`,
      duration: (Math.random() * 1000) + 500
    });
    loading.onWillDismiss(() => {
      fab.close();
    });
    loading.present();
  }
}

```

