# 1-官方hero示例第06讲 初始化路由与重构组件

## 将当前根组件的内容转到 列表组件

### 新建列表组件

`ng g c heroes `

列表组件的内容为原来根组件的内容,并在模块级别提供HeroService

`/src/app/heroes/heroes.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit {

  heroes: Hero[] = [];

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  constructor(private heroService: HeroService) {

  }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => this.heroes = heroes
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }

}

```

`/src/app/heroes/heroes.component.jade`

```jade
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    | {{hero.name}}
app-hero-detail([hero]="selectedHero")
```

`/src/app/heroes/heroes.component.css`

```css
.selected {
  background-color: #CFD8DC !important;
  color: white;
}

.heroes {
  margin: 0 0 2em 0;
  list-style-type: none;
  padding: 0;
  width: 15em;
}

.heroes li {
  cursor: pointer;
  position: relative;
  left: 0;
  background-color: #EEE;
  margin: .5em;
  padding: .3em 0;
  height: 1.6em;
  border-radius: 4px;
}

.heroes li.selected:hover {
  background-color: #BBD8DC !important;
  color: white;
}

.heroes li:hover {
  color: #607D8B;
  background-color: #DDD;
  left: .1em;
}

.heroes .text {
  position: relative;
  top: -3px;
}

.heroes .badge {
  display: inline-block;
  font-size: small;
  color: white;
  padding: 0.8em 0.7em 0 0.7em;
  background-color: #607D8B;
  line-height: 1em;
  position: relative;
  left: -1px;
  top: -4px;
  height: 1.8em;
  margin-right: .8em;
  border-radius: 4px 0 0 4px;
}
```

### 原根组件内容调整如下:

`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = '英雄之旅';

  constructor() {
  }

  ngOnInit(): void {
  }
}

```

`/src/app/app.component.jade`

```jade
h1 {{title}}
app-heroes
```


`/src/app/app.component.css`

```css

```

### 在模块级别提供HeroService

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

### 进行测试

跟原来效果一样

## 初始化路由

### 在根模块进行路由设置

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

import { Routes, RouterModule } from '@angular/router'

const routes: Routes = [
  {
    path: 'heroes',
    component: HeroesComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroesComponent
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

### 重构根组件视图

`/src/app/app.component.jade`

```jade
h1 {{title}}
a(routerLink="/heroes") 列表
router-outlet
```

## 增加推荐仪表盘组件

### 创建组件

`ng g c dashboard`

### 配置路由

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  {
    path: 'heroes',
    component: HeroesComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
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

### 重构根组件视图

`/src/app/app.component.jade`

```jade
h1 {{title}}
nav
  a(routerLink="/dashboard") 推荐
  | &nbsp;
  a(routerLink="/heroes") 列表
router-outlet
```