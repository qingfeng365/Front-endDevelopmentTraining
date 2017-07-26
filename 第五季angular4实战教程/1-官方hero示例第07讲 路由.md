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

`/src/app/hero-detail/hero-detail.component.css`

```css
    label {
      display: inline-block;
      width: 3em;
      margin: .5em 0;
      color: #607D8B;
      font-weight: bold;
    }
    
    input {
      height: 2em;
      font-size: 1em;
      padding-left: .4em;
    }
    
    button {
      margin-top: 20px;
      font-family: Arial;
      background-color: #eee;
      border: none;
      padding: 5px 10px;
      border-radius: 4px;
      cursor: pointer;
      cursor: hand;
    }
    
    button:hover {
      background-color: #cfd8dc;
    }
    
    button:disabled {
      background-color: #eee;
      color: #ccc;
      cursor: auto;
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

### 修改详情组件获取组件的方式

`/src/app/hero-detail/hero-detail.component.ts`

```ts
import { Component, OnInit, Input } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { ActivatedRoute, Params } from '@angular/router';
import 'rxjs/Rx';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

  @Input()
  hero: Hero;

  constructor(private heroService: HeroService,
    private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    this.routeInfo.params
      .switchMap((params: Params) => this.heroService.getHero(+params.id))
      .subscribe(hero => this.hero = hero);
  }

}


```

在浏览器用  `/detail/11` 测试


```ts
  // 使用 switchMap 等价于 下面这种写法: 即 switchMap = map + switch
  // ngOnInit() {
  //   this.routeInfo.params
  //     .map((params: Params) => Observable.from(
  //       this.heroService.getHero(+params.id)))
  //     .switch()
  //     .subscribe(hero => this.hero = hero);
  // } 
```


> 说明
> .map((params: Params) => Observable.from(this.heroService.getHero(+params.id))) 
> 
> 此时, map 将 值 (11) 转换成一个 observable (可观察对象) 
> 如果此时 直接 subscribe 
> 
> 那么 订阅获得数据其实是一个 observable
> 比如需要以下这样写:
> 如果不使用switch, 则需要再次订阅

```ts
  ngOnInit() {
    this.routeInfo.params
      .map((params: Params) => Observable.from(
        this.heroService.getHero(+params.id)))
      .subscribe(observable => observable.subscribe(hero => this.hero = hero));
  }
```

> switch 操作符的行为是：
> 
> 如果 上一个操作符的数据本身又是 Observable，switch 会将数据流中最新的一个 
> Observable 订阅并将它的值传递给下一个操作符，然后`取消订阅之前的 Observable`。 
> 
> `取消订阅`的意思是说 `不管之前的 Observable` 有没有订阅 或者 还有没有剩余的数据没有订阅完,都结束之前的 Observable
> 
> switchMap 其实是 map and switch
> 
> 注意: switchMap 比 switch 对 判断当前数据是否为 Observable 要宽松一些
> 
> promise 也可视为 observable
> 
> 相当于自动转换为 `RX.observable.fromPromise`
> 
> `fromPromise` : 如果 Promise 是成功状态, 则 Observable 会将成功的值作为 next 发出
> 然后 complete, 如果 Promise 失败, 则 Observable 发出相应的错误
> 
> switch switchMap , 可以这样去理解, 当接收到的 observable 不是普通的 observable, 而是嵌套的 observable , 即 observable 的数据本身又是 observable, 
> 
> 此时需要双重订阅, 才能获得真实的数据, switch 就是自动完成双重订阅, 
> 
> 即将 高阶 observable (两阶observable) 重新降为一阶 observable ,再传为下一个操作符
> 
> 这种降阶操作符还有一些, 但 switch 还有一个作用是 丢弃之前的订阅, 即总是保留最新的数据





注意:

引入RX,可以直接这样写: `import 'rxjs/Rx';` (如果仅使用 angular 自身的 Observable)

但是正规写法, 则是要用到什么, 才引入什么:

```ts
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/switch';
import 'rxjs/add/observable/from';
```

各种写法的完整示例:

```ts
import { Component, OnInit, Input } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { ActivatedRoute, Params } from '@angular/router';
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/switch';
import 'rxjs/add/observable/from';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

  @Input()
  hero: Hero;

  constructor(private heroService: HeroService,
    private routeInfo: ActivatedRoute) { }

  ngOnInit() {
    this.routeInfo.params
      .switchMap((params: Params) => this.heroService.getHero(+params.id))
      .subscribe(hero => this.hero = hero);
  }

  // 使用 switchMap 等价于 下面这种写法: 即 switchMap = map + switch
  // ngOnInit() {
  //   this.routeInfo.params
  //     .map((params: Params) => Observable.from(
  //       this.heroService.getHero(+params.id)))
  //     .switch()
  //     .subscribe(hero => this.hero = hero);
  // }

  // 如果不使用switch, 则需要再次订阅
  // ngOnInit() {
  //   this.routeInfo.params
  //     .map((params: Params) => Observable.from(
  //       this.heroService.getHero(+params.id)))
  //     .subscribe(observable => observable.subscribe(hero => this.hero = hero));
  // }

}

```

> 注意:
> `Router` 管理它提供的`可观察对象`，并使订阅局部化。当组件被销毁时，会清除 订阅，防止内存泄漏，所以我们不需要从路由参数 `Observable` 取消订阅。

### 增加推荐组件导航到详情组件的链接

`/src/app/dashboard/dashboard.component.jade`

```jade
h3 最强英雄
.grid.grid-pad
  a.col-1-4(*ngFor="let hero of heroes", [routerLink]="['/detail', hero.id]")
    .module.hero
      h4 {{hero.name}}
```

### 调整列表组件显示详情方式

取消直接在列表组件显示详情组件

`/src/app/heroes/heroes.component.jade`

```jade
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    | {{hero.name}}
//- app-hero-detail([hero]="selectedHero")
div(*ngIf="selectedHero")
  h2 {{selectedHero.name}}
  button((click)="gotoDetail()") 查看详情
```

`/src/app/heroes/heroes.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';

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
}

```

### 在详情界面增加返回上一界面功能

由于用户有多种方式导航到HeroDetailComponent。

可以利用浏览器的历史堆栈，导航到上一步。

> 注意:
> 
> 由于ts 有同名的 Location, 此处要使用的 angular 提供的 Location, 
> 所以要手工引入:
> 
> import { Location } from '@angular/common';
> 

`/src/app/hero-detail/hero-detail.component.jade`

```jade
div(*ngIf='hero')
  h2 {{hero.name}} 详情
  div
    label 编号:
    | {{hero.id}}
  div
    label 名称:
    input([(ngModel)]='hero.name',placeholder='名称')
  button((click)="goBack()") 返回
```

`/src/app/hero-detail/hero-detail.component.ts`

```ts
import { Component, OnInit, Input } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { ActivatedRoute, Params } from '@angular/router';
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/switch';
import 'rxjs/add/observable/from';

import { Location } from '@angular/common';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

  @Input()
  hero: Hero;

  constructor(private heroService: HeroService,
    private routeInfo: ActivatedRoute,
    private location: Location) { }

  ngOnInit() {
    this.routeInfo.params
      .switchMap((params: Params) => this.heroService.getHero(+params.id))
      .subscribe(hero => this.hero = hero);
  }

  // 使用 switchMap 等价于 下面这种写法: 即 switchMap = map + switch
  // ngOnInit() {
  //   this.routeInfo.params
  //     .map((params: Params) => Observable.from(
  //       this.heroService.getHero(+params.id)))
  //     .switch()
  //     .subscribe(hero => this.hero = hero);
  // }

  // 如果不使用switch, 则需要再次订阅
  // ngOnInit() {
  //   this.routeInfo.params
  //     .map((params: Params) => Observable.from(
  //       this.heroService.getHero(+params.id)))
  //     .subscribe(observable => observable.subscribe(hero => this.hero = hero));
  // }

  goBack() {
    this.location.back();
  }

}

```

### 重构路由为一个路由模块

`ng g m appRouting --flat`

- `--flat`: 参数表示不自动创建目录,生成到根目录

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeroesComponent } from './heroes/heroes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';

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
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```


典型路由模块需要注意的有：

- 将路由抽出到一个变量中。如果你将来要导出这个模块，这种 "路由模块" 的模式也会更加明确。
- 添加 RouterModule.forRoot(routes) 到 imports 。
- 把RouterModule添加到路由模块的 exports 中，以便关联模块（比如 AppModule ）中的组件可以访问路由模块中的声明，比如 RouterLink 和 RouterOutlet。
- 无 declarations ！声明是关联模块的任务。
- 如果有守卫服务，把它们添加到本模块的 providers 中（本例子中没有守卫服务）。

### 修改 AppModule

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AppRoutingModule } from './app-routing.module';



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
    AppRoutingModule
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

### 对活动路由应用active样式

Angular路由器提供了routerLinkActive指令，
我们可以用它来为匹配了活动路由的 HTML 导航元素自动添加一个 CSS 类

`/src/app/app.component.jade`

```jade
h1 {{title}}
nav
  a(routerLink="/dashboard",routerLinkActive="active") 推荐
  | &nbsp;
  a(routerLink="/heroes",routerLinkActive="active") 列表
router-outlet
```

### 设置全局样式

`/src/styles.css`

```css

h1 {
  color: #369;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 250%;
}

h2,
h3 {
  color: #444;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: lighter;
}

body {
  margin: 2em;
}

body,
input[text],
button {
  color: #888;
  font-family: Cambria, Georgia;
}


/* . . . */


/* everywhere else */

* {
  font-family: Arial, Helvetica, sans-serif;
}
```

