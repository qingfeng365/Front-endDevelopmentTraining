# 1-官方hero示例第07讲 路由

## 处理推荐组件的数据

`/src/app/dashboard/dashboard.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  heroes: Hero[] = [];

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.heroService.getHeros()
      .then(heroes => this.heroes = heroes.slice(1, 5));
  }
}

```

## 处理样式

`/src/app/dashboard/dashboard.component.css`

```css
    [class*='col-'] {
      float: left;
      padding-right: 20px;
      padding-bottom: 20px;
    }
    
    [class*='col-']:last-of-type {
      padding-right: 0;
    }
    
    a {
      text-decoration: none;
    }
    
    *,
    *:after,
    *:before {
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    
    h3 {
      text-align: center;
      margin-bottom: 0;
    }
    
    h4 {
      position: relative;
    }
    
    .grid {
      margin: 0;
    }
    
    .col-1-4 {
      width: 25%;
    }
    
    .module {
      padding: 20px;
      text-align: center;
      color: #eee;
      max-height: 120px;
      min-width: 120px;
      background-color: #607D8B;
      border-radius: 2px;
    }
    
    .module:hover {
      background-color: #EEE;
      cursor: pointer;
      color: #607d8b;
    }
    
    .grid-pad {
      padding: 10px 0;
    }
    
    .grid-pad>[class*='col-']:last-of-type {
      padding-right: 20px;
    }
    
    @media (max-width: 600px) {
      .module {
        font-size: 10px;
        max-height: 75px;
      }
    }
    
    @media (max-width: 1024px) {
      .grid {
        margin: 0;
      }
      .module {
        min-width: 60px;
      }
    }
```

`/src/app/app.component.css`

```css
    h1 {
      font-size: 1.2em;
      color: #999;
      margin-bottom: 0;
    }
    
    h2 {
      font-size: 2em;
      margin-top: 0;
      padding-top: 0;
    }
    
    nav a {
      padding: 5px 10px;
      text-decoration: none;
      margin-top: 10px;
      display: inline-block;
      background-color: #eee;
      border-radius: 4px;
    }
    
    nav a:visited,
    a:link {
      color: #607D8B;
    }
    
    nav a:hover {
      color: #039be5;
      background-color: #CFD8DC;
    }
    
    nav a.active {
      color: #039be5;
    }
```

## 处理通过导航显示详情组件

### 配置详情组件的路由

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'heroes',
    component: HeroesComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'detail/:id',
    component: HeroDetailComponent,
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroesComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

### 数据服务增加获取单个英雄的方法

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';
import { HEROES } from '../mock-data/mock-heroes';

@Injectable()
export class HeroService {

  constructor() { }

  // 普通写法
  // getHeros(): Promise<Hero[]> {
  //   return Promise.resolve(HEROES);
  // }

  // 模拟网络延时的写法
  getHeros(): Promise<Hero[]> {
    return new Promise(
      resolve => setTimeout(
        () => resolve(HEROES), 1000
      ));
  }

  getHero(id: number): Promise<Hero> {
    return this.getHeros()
      .then(heroes => heroes.find(hero => hero.id === id));
  }
}

```

