# 第12讲 conference项目-主讲

## 列表页

### `/src/pages/speaker-list/speaker-list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';

/**
 * Generated class for the SpeakerListPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-speaker-list',
  templateUrl: 'speaker-list.html',
})
export class SpeakerListPage {
  speakers: any[] = [];
  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    public ConferenceService: ConferenceService,) {
  }

  ionViewDidLoad() {
    this.ConferenceService
    .getSpeakers()
    .subscribe((speakers: any[]) => {
      this.speakers = speakers;
    });
  }

}

```

### `/src/pages/speaker-list/speaker-list.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 主讲
ion-content
  ion-list([virtualScroll]="speakers")
    ion-card(*virtualItem="let speaker")
      ion-card-header
        button(ion-item="", detail-none="",
          (click)="goToSpeakerDetail(speaker)")
          ion-avatar(item-start="")
            img([src]="speaker.profilePic" )
          | {{speaker.name}}
      ion-card-content
        ion-list
          button(ion-item="", 
            *ngFor="let session of speaker.sessions",
            (click)="goToSessionDetail(session)")
            h3 {{session.name}}
          button(ion-item="", 
            (click)="goToSpeakerDetail(speaker)")
            h3 About {{speaker.name}}
      ion-row(no-padding="")
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="goToSpeakerTwitter(speaker)")
            ion-icon(name="logo-twitter")
            | Tweet
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="openSpeakerShare(speaker)")
            ion-icon(name="share-alt")
            | Share
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="openContact(speaker)")
            ion-icon(name="chatboxes")
            | Contact

```

> 注意: virtualScroll 的用法
> 


测试,进一步调整样式

##　样式

> 原案例的 方法 仅适用于 IOS
> ion-content class="outer-content speaker-list"

### `/src/pages/speaker-list/speaker-list.jade`

```jade
ion-header
  ion-navbar
    button(ion-button="",menuToggle="")
      ion-icon(name="menu")
    ion-title 主讲
ion-content.speaker-content
  ion-list.speakerlist([virtualScroll]="speakers")
    ion-card(*virtualItem="let speaker")
      ion-card-header
        button(ion-item="", detail-none="",
          (click)="goToSpeakerDetail(speaker)")
          ion-avatar(item-start="")
            img([src]="speaker.profilePic" )
          | {{speaker.name}}
      ion-card-content
        ion-list(mode="ios")
          button(ion-item="", 
            *ngFor="let session of speaker.sessions",
            (click)="goToSessionDetail(session)")
            h3 {{session.name}}
          button(ion-item="", 
            (click)="goToSpeakerDetail(speaker)")
            h3 About {{speaker.name}}
      ion-row(no-padding="")
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="goToSpeakerTwitter(speaker)")
            ion-icon(name="logo-twitter")
            | Tweet
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="openSpeakerShare(speaker)")
            ion-icon(name="share-alt")
            | Share
        ion-col(col-12="",text-center="")
          button(ion-button="",
            clear="",small="",
            color="primary", icon-start="",
            (click)="openContact(speaker)")
            ion-icon(name="chatboxes")
            | Contact

```

### `/src/pages/speaker-list/speaker-list.scss`

```
page-speaker-list {
  .speaker-content .card-content {
    padding: 0;
  }
  .speaker-content {
    background: #efeff4;
  }
  .speakerlist {
    margin-top: 12px;
  }
}
```

## 详情页

`ionic g page speakerDetail --no-module`

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
import { SpeakerDetailPage } from '../pages/speaker-detail/speaker-detail';
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
    SessionDetailPage,
    SpeakerDetailPage
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
    SessionDetailPage,
    SpeakerDetailPage
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

### `/src/pages/speaker-detail/speaker-detail.jade`

```jade
ion-header
  ion-navbar
    ion-title {{speaker?.name}}
ion-content.speaker-detail(padding)
  div(text-center="",*ngIf="speaker")
    img([src]="speaker.profilePic",[alt]="speaker.name")
    br
    button(ion-button="",
      icon-only="", clear="",
      small="", color="twitter")
      ion-icon(name="logo-twitter")
    button(ion-button="",
      icon-only="", clear="",
      small="", color="github")
      ion-icon(name="logo-github")
    button(ion-button="",
      icon-only="", clear="",
      small="", color="instagram")
      ion-icon(name="logo-instagram")
  p {{speaker?.about}}

```

### `/src/pages/speaker-detail/speaker-detail.scss`

```css
page-speaker-detail {
  .speaker-detail img {
    max-width: 140px;
    border-radius: 50%;
  }
  .speaker-detail p {
    color: #60646B;
  }
}
```

### `/src/pages/speaker-detail/speaker-detail.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-speaker-detail',
  templateUrl: 'speaker-detail.html',
})
export class SpeakerDetailPage {
  speaker: any;
  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams) {
    this.speaker = navParams.get('speaker');
  }

  ionViewDidLoad() {
  }

}

```

### `/src/pages/speaker-list/speaker-list.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';
import { SessionDetailPage } from '../session-detail/session-detail';
import { SpeakerDetailPage } from '../speaker-detail/speaker-detail';

/**
 * Generated class for the SpeakerListPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-speaker-list',
  templateUrl: 'speaker-list.html',
})
export class SpeakerListPage {
  speakers: any[] = [];
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public ConferenceService: ConferenceService, ) {
  }

  ionViewDidLoad() {
    this.ConferenceService
      .getSpeakers()
      .subscribe((speakers: any[]) => {
        this.speakers = speakers;
      });
  }
  goToSessionDetail(session: any) {
    this.navCtrl.push(SessionDetailPage, { session });
  }

  goToSpeakerDetail(speaker: any) {
    this.navCtrl.push(SpeakerDetailPage, { speaker });
  }
}

```

