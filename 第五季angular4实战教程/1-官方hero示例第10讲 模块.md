# 1-官方hero示例第10讲 模块.md

## 模块

根模块 

核心模块: Core Module, 存放应用级别核心构件的核心模块
共享模块: Shared Module, 封装一些公共构件的共享模块
特性模块: Feature Module, 封装某个完整功能的特性模块

现在将有关英雄功能封装到特性模块

## 重构前的准备

`ng g c NotFound`

`/src/app/not-found/not-found.component.html`

```html
<p>
  页面不存在
</p>
```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeroesComponent } from './heroes/heroes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';
import { NotFoundComponent } from './not-found/not-found.component';

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
  },
  {
    path: 'animate-hero-list',
    component: AnimateHeroListComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```


## 重构特性模块

### 目录与命名

- 如果有对应资源名, 取 资源名 的复数形式做模块目录名和特性模块名
- 如果没有对应资源名, 另取 特性名 做模块目录
- 如果存在特性主组件, 则 特性主组件 的名称 同 目录名

当前选择:

- 有对应资源: Hero ,  目录: heroes
- 目录不存在特性主组件
- 但 列表组件 与 目录同名了

### 新建列表组件

将现有列表组件内容移动新组件

`ng g c heroList`

这两个文件与原来列表组件内容一样

`/src/app/hero-list/hero-list.component.css`

`/src/app/hero-list/hero-list.component.jade`




`/src/app/hero-list/hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hero-list',
  templateUrl: './hero-list.component.html',
  styleUrls: ['./hero-list.component.css']
})
export class HeroListComponent implements OnInit {

  heroes: Hero[] = [];

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  constructor(private heroService: HeroService, private router: Router) {

  }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => this.heroes = heroes
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }

  gotoDetail() {
    this.router.navigate(['/detail', this.selectedHero.id]);
  }

  add(name: string) {
    name = name.trim();
    if (name === '') { return; }

    this.heroService.create(name)
      .then(hero => {
        this.heroes.push(hero);
        this.selectedHero = null;
      })
  }

  delete(hero: Hero) {
    this.heroService
      .delete(hero.id)
      .then(() => {
        this.heroes = this.heroes.filter(item => item !== hero);
        if (this.selectedHero === hero) {
          this.selectedHero = null;
        }
      })
  }
}


```

`/src/app/app-routing.module.ts`

取消旧组件的引用, 增加新组件

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './hero-list/hero-list.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'heroes',
    component: HeroListComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'detail/:id',
    component: HeroDetailComponent,
  },
  {
    path: 'animate-hero-list',
    component: AnimateHeroListComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

测试功能正常

删除 heroes 目录下的文件 , 或删除整个目录

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroService } from './service/hero.service';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './hero-list/hero-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    DashboardComponent,
    AnimateHeroListComponent,
    NotFoundComponent,
    HeroListComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 })
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

### 创建特性模块

`ng g m heroes`

- 将以下组件目录移到 heroes 目录下

animate-hero-list
dashboard
hero-detail
hero-list
model
service

- 在 HeroesModule 声明组件,服务, 以及要引入的模块
- 创建特性路由模块

`/src/app/heroes/heroes-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeroListComponent } from './hero-list/hero-list.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';


const routes: Routes = [
  {
    path: 'heroes',
    component: HeroListComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'detail/:id',
    component: HeroDetailComponent,
  },
  {
    path: 'animate-hero-list',
    component: AnimateHeroListComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class HeroesRoutingModule { }


```

`/src/app/heroes/heroes.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroListComponent } from './hero-list/hero-list.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroService } from './service/hero.service';
import { HeroesRoutingModule } from './heroes-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    FormsModule,
    HeroesRoutingModule
  ],
  declarations: [
    AnimateHeroListComponent,
    DashboardComponent,
    HeroListComponent,
    HeroDetailComponent
  ],
  providers: [HeroService]
})
export class HeroesModule { }


```

修改 引用 `Hero` 模型的 文件

`/src/app/mock-data/mock-heroes.ts`

`/src/app/mock-data/in-memory-data.service.ts`

修改根模块

`/src/app/app.module.ts`

```
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroesModule } from './heroes/heroes.module';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    HeroesModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './heroes/hero-list/hero-list.component';
import { DashboardComponent } from './heroes/dashboard/dashboard.component';
import { HeroDetailComponent } from './heroes/hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './heroes/animate-hero-list/animate-hero-list.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

测试功能正常

