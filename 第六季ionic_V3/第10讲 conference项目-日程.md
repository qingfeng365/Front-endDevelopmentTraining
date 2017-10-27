# 第10讲 conference项目-日程

## 日程视图

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
  ion-list(#scheduleList="",[hidden]="shownSessions === 0")
    ion-item-group(*ngFor="let group of groups",
      [hidden]="group.hide")
      ion-item-divider(sticky="")
        ion-label {{group.time}}
      ion-item-sliding(*ngFor="let session of group.sessions",
        [hidden]="session.hide",
        #slidingItem="")
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
    | 没有找到课程信息...
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

### `/src/pages/schedule/schedule.ts`

```ts
import { Component } from '@angular/core';
import { NavController, NavParams, ItemSliding } from 'ionic-angular';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage {
  groups: any = [];
  shownSessions = 0;
  queryText = '';
  segment = 'all';

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SchedulePage');
  }
  updateSchedule() { }
  presentFilter() { }
  goToSessionDetail(sessionData: any) { }
  addFavorite(slidingItem: ItemSliding, sessionData: any) { }
  removeFavorite(slidingItem: ItemSliding, sessionData: any) { }
}

```

### `/src/theme/variables.scss`

```css
$font-path: "../assets/fonts";
$app-direction: ltr;
@import "ionic.globals";
$headings-font-weight: 300;
$colors: ( primary: #488aff, secondary: #32db64, danger: #f53d3d, light: #f4f4f4, dark: #222, favorite: #69BB7B, twitter: #1da1f4, google: #dc4a38, vimeo: #23b6ea, facebook: #3b5998);
$toolbar-md-background: color($colors, primary);
$toolbar-md-active-color: #fff;
@import "ionic.theme.default";
@import "ionic.ionicons";
@import "roboto";
@import "noto-sans";
```

测试视图是否正常


## 准备数据

`ng g s service/conference`

`ng g s service/user`

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
    AboutPage
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

### `/src/app/service/user.service.ts`

```
import { Injectable } from '@angular/core';
import { Events } from 'ionic-angular';
import { Storage } from '@ionic/storage';
@Injectable()
export class UserService {


  _favorites: string[] = [];
  HAS_LOGGED_IN = 'hasLoggedIn';
  HAS_SEEN_TUTORIAL = 'hasSeenTutorial';

  constructor(
    public events: Events,
    public storage: Storage
  ) {}

  hasFavorite(sessionName: string): boolean {
    return (this._favorites.indexOf(sessionName) > -1);
  };

  addFavorite(sessionName: string): void {
    this._favorites.push(sessionName);
  };

  removeFavorite(sessionName: string): void {
    let index = this._favorites.indexOf(sessionName);
    if (index > -1) {
      this._favorites.splice(index, 1);
    }
  };

  login(username: string): void {
    this.storage.set(this.HAS_LOGGED_IN, true);
    this.setUsername(username);
    this.events.publish('user:login');
  };

  signup(username: string): void {
    this.storage.set(this.HAS_LOGGED_IN, true);
    this.setUsername(username);
    this.events.publish('user:signup');
  };

  logout(): void {
    this.storage.remove(this.HAS_LOGGED_IN);
    this.storage.remove('username');
    this.events.publish('user:logout');
  };

  setUsername(username: string): void {
    this.storage.set('username', username);
  };

  getUsername(): Promise<string> {
    return this.storage.get('username').then((value) => {
      return value;
    });
  };

  hasLoggedIn(): Promise<boolean> {
    return this.storage.get(this.HAS_LOGGED_IN).then((value) => {
      return value === true;
    });
  };

  checkHasSeenTutorial(): Promise<string> {
    return this.storage.get(this.HAS_SEEN_TUTORIAL).then((value) => {
      return value;
    });
  };

}

```

### `/src/app/service/conference.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { UserService } from './user.service';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class ConferenceService {
  data: any;
  constructor(public http: Http, public user: UserService) { }
  load(): any {
    if (this.data) {
      return Observable.of(this.data);
    } else {
      return this.http.get('assets/data/data.json')
        .map(this.processData, this);
    }
  }

  private processData(data: any) {
    // data 实际为 http.get 的 res 对象
    this.data = data.json();

    this.data.tracks = [];

    this.data.schedule.forEach((day: any) => {
      day.groups.forEach((group: any) => {

        // 将原始数据的 sessions.speakerNames (名称数组)
        //   转换成 session.speakers (主讲对象数组)
        // 并将原始数据 speakers 对应的主讲 增加 speaker.sessions (主讲的课程对象数组)
        group.sessions.forEach((session: any) => {
          session.speakers = [];
          if (session.speakerNames) {
            session.speakerNames.forEach((speakerName: any) => {
              let speaker = this.data.speakers.find((s: any) => s.name === speakerName);
              if (speaker) {
                session.speakers.push(speaker);
                speaker.sessions = speaker.sessions || [];
                speaker.sessions.push(session);
              }
            });
          }

          // 原始数据没有 主题(tracks) 数据,
          // 从 每个课程的 session.tracks, 提取出 track
          // 构造单独的 主题(tracks) 数组
          if (session.tracks) {
            session.tracks.forEach((track: any) => {
              if (this.data.tracks.indexOf(track) < 0) {
                this.data.tracks.push(track);
              }
            });
          }
        });
      });
    });

    console.log(this.data);
    return this.data;
  }

  /**
   * 
   * @param {number} dayIndex  第几天
   * @param {string} [queryText=''] 搜索框文本
   * @param {any[]} [excludeTracks=[]] 要排除的主题
   * @param {string} [segment='all'] 分段(全部 , 关注)
   * @returns 
   *    {
   *      date:,
   *      shownSessions:,  //有多少个课程有效
   *      groups:{
   *        hide:,     
   *        sessions:[{
   *          hide:,
   *        }]
   *      }
   *    }
   * @memberof ConferenceService
   */
  getTimeline(dayIndex: number, queryText = '', excludeTracks: any[] = [], segment = 'all') {
    return this.load().map((data: any) => {
      let day = data.schedule[dayIndex];
      day.shownSessions = 0;

      // 折解成 搜索词数组
      // 将 , . - 转换成 空格
      queryText = queryText.toLowerCase().replace(/,|\.|-/g, ' ');
      let queryWords = queryText.split(' ').filter(w => !!w.trim().length);

      day.groups.forEach((group: any) => {
        // 
        group.hide = true;

        group.sessions.forEach((session: any) => {
          
          // 检查是否要隐藏课程
          this.filterSession(session, queryWords, excludeTracks, segment);

          if (!session.hide) {
            // 有课程可以显示, 则该组才可以显示
            group.hide = false;
            day.shownSessions++;
          }
        });

      });

      return day;
    });
  }

  private  filterSession(session: any, queryWords: string[], excludeTracks: any[], segment: string) {

    let matchesQueryText = false;
    if (queryWords.length) {
      // 每个搜索词都要检查
      queryWords.forEach((queryWord: string) => {
        // 只按课程名检查匹配
        if (session.name.toLowerCase().indexOf(queryWord) > -1) {
          matchesQueryText = true;
        }
      });
    } else {
      // 没有搜索则为真
      matchesQueryText = true;
    }

    // 检查 主题 是否匹配
    let matchesTracks = false;
    session.tracks.forEach((trackName: string) => {
      if (excludeTracks.indexOf(trackName) === -1) {
        matchesTracks = true;
      }
    });

    // 当 分段为 关注时, 检查 是否为关注的课程
    let matchesSegment = false;
    if (segment === 'favorites') {
      if (this.user.hasFavorite(session.name)) {
        matchesSegment = true;
      }
    } else {
      matchesSegment = true;
    }

    // 任意一项不符,则隐藏
    session.hide = !(matchesQueryText && matchesTracks && matchesSegment);
  }

  getSpeakers() {
    return this.load().map((data: any) => {
      return data.speakers.sort((a: any, b: any) => {
        let aName = a.name.split(' ').pop();
        let bName = b.name.split(' ').pop();
        return aName.localeCompare(bName);
      });
    });
  }

  getTracks() {
    return this.load().map((data: any) => {
      return data.tracks.sort();
    });
  }

  getMap() {
    return this.load().map((data: any) => {
      return data.map;
    });
  }
}

```

### `/src/pages/schedule/schedule.ts`

```ts
import { Component, ViewChild, OnInit } from '@angular/core';
import { NavController, NavParams, ItemSliding, List } from 'ionic-angular';
import { ConferenceService } from '../../app/service/conference.service';

@Component({
  selector: 'page-schedule',
  templateUrl: 'schedule.html',
})
export class SchedulePage implements OnInit {

  @ViewChild('scheduleList', { read: List }) 
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
  presentFilter() { }
  goToSessionDetail(sessionData: any) { }
  addFavorite(slidingItem: ItemSliding, sessionData: any) { }
  removeFavorite(slidingItem: ItemSliding, sessionData: any) { }
}

```

> 注意:
>   @ViewChild('scheduleList', { read: List }) 
  scheduleList: List;
> 使用 模板本地变量, 要获取标签对应组件对象的写法, 否则获取的是 html 标签对象
> 
> 还可以这样写:
> 
>   @ViewChild(List) 
  scheduleList: List;
> 
> 这样要注意 List 类型有多个时, 找到的是 第一个

## 增加主题颜色标志

### `/src/pages/schedule/schedule.scss`

```css
$categories: ( ionic: color($colors, primary), angular: #AC282B, communication: #8E8D93, tooling: #FE4C52, services: #FD8B2D, design: #FED035, workshop: #69BB7B, food: #3BC7C4, documentation: #B16BE3, navigation: #6600CC, );
@function auxiliary-categories() {
  @return map-remove($categories);
}

page-schedule {
  @each $track,
  $value in auxiliary-categories() {
    ion-item-sliding[track=#{$track}] ion-label {
      border-left: 2px solid $value;
      padding-left: 10px;
    }
  }
}
```

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
    | 没有找到课程信息...
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

ionic g page ScheduleFilter