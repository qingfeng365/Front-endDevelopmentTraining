# 3-nicefish示例第02讲 首页

## 首页主菜单

`/src/app/app.component.jade`

```jade
nav.navbar.navbar-expand-lg.navbar-dark.bg-navbar
  .container
    a.navbar-brand(routerLink="posts") NiceFish
    button.navbar-toggler(type="button",
      data-toggle="collapse",
      data-target="#navbarSupportedContent")
      span.navbar-toggler-icon
    #navbarSupportedContent.collapse.navbar-collapse
      ul.navbar-nav.mr-auto
        li.nav-item(routerLinkActive="active")
          a.nav-link(routerLink="posts") 动态
        li.nav-item(routerLinkActive="active")
          a.nav-link(routerLink="echart") 集成图表
      ul.navbar-nav.ml-auto
        li.nav-item(routerLinkActive="active")
          a.nav-link(routerLink="login") 登录
        li.nav-item(routerLinkActive="active")
          a.nav-link(routerLink="register") 注册
.container
  router-outlet
.footer
  .container
    .row
      .col
        span.text-center Powered by &nbsp;
          a(href="javascript:;") NiceFish

```

`/src/app/app.component.scss`

```css
.bg-navbar {
  // background-color: #563d7c; // bootstrap 官网
  background-color: #6f5499;
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.20), 0 -1px 0 rgba(0, 0, 0, 0.1) inset;
  min-height: 3rem;
}

.navbar-nav {
  .nav-link {
    color: rgba(255, 255, 255, 0.85);
    &:focus,
    &:hover {
      color: rgb(255, 255, 255);
    }
  }
}

.footer {
  text-align: center;
  padding: 1rem 3rem;
  background: #263238;
  color: #fff;
}
```


## 首页模块组件

```
ng g c home

ng g c home/socialChannel

ng g c home/sitestat

ng g c home/onlineContact
```

使用 `Angular v4 TypeScript Snippets` 插件 增加 `HomeRoutingModule`

`/src/app/home/home-routing.module.ts`

先保存文件, 然后输入 `a-module-routing`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule { }

```

`/src/app/home/home.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { SocialChannelComponent } from './social-channel/social-channel.component';
import { SitestatComponent } from './sitestat/sitestat.component';
import { OnlineContactComponent } from './online-contact/online-contact.component';
import { HomeRoutingModule } from './home-routing.module';

@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule
  ],
  declarations: [
    HomeComponent,
    SocialChannelComponent,
    SitestatComponent,
    OnlineContactComponent
  ]
})
export class HomeModule { }

```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomeModule'
  },
  {
    path: '**',
    loadChildren: './home/home.module#HomeModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

## 设置首页内容

`/src/app/home/home.component.jade`

```jade
.container.pt-3
  .row
    .col-sm-9
      router-outlet
    .col-sm-3
      .mb-3
        app-social-channel
      .mb-3
        app-online-contact
      .mb-3
        app-sitestat

```

`/src/app/home/social-channel/social-channel.component.jade`

```jade
.card
  h6.card-header 社交媒体
  .list-group.list-group-flush
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-video-camera
      | &nbsp;优酷视频教程
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-youtube
      | &nbsp;油管视频教程
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-github
      | &nbsp;我的主页

```

`/src/app/home/online-contact/online-contact.component.jade`

```jade
.card
  h6.card-header QQ群
  .list-group.list-group-flush
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 1区:xxxxxxxxx（满）
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 2区:xxxxxxxxx（满）
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 3区:xxxxxxxxx（满）
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 4区:xxxxxxxxx（满）
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 5区:xxxxxxxxx（满）
    a.list-group-item.list-group-item-action(href="javasript:;")
      i.fa.fa-qq
      | &nbsp;Angular 6区:xxxxxxxxx

```

`/src/app/home/sitestat/sitestat.component.jade`

```jade
.card
  h6.card-header 博客统计
  .card-body
    p.card-text 访问总数：xxxxxxxx
    p.card-text 文章总数：xxxx
    p.card-text 评论总数：xxxx
    p.card-text 会员总数：xxxxx
    p.card-text 在线访客：xxx
    p.card-text 在线会员：xx
    p.card-text 在线记录：xxx

```