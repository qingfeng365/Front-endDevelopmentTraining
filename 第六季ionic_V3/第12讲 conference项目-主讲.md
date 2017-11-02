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