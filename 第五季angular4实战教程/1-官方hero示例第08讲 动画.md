# 1-官方hero示例第08讲 动画

## 检查是否已安装包

`/package.json`

`@angular/animations`

## 启用动画

### 引入动画模块

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroService } from './service/hero.service';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';

@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroesComponent,
    DashboardComponent,
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

### 在组件装饰器使用 animations

`/src/app/dashboard/dashboard.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import {
  trigger, state, style,
  animate, transition
} from '@angular/animations';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#607D8B',
        transform: 'scale(1)',
        color: '#eee'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
        color: '#607d8b'
      })),
      transition('inactive => active', animate('200ms ease-in')),
      transition('active => inactive', animate('200ms ease-out'))
    ])
  ]
})
```

### 在视图中绑定动画触发器

`/src/app/dashboard/dashboard.component.jade`

```jade
h3 最强英雄
.grid.grid-pad
  a.col-1-4(*ngFor="let hero of heroes", [routerLink]="['/detail', hero.id]")
    .module([@heroState]="getState(hero)")
      h4 {{hero.name}}
```

### 在组件中处理状态维护

`/src/app/dashboard/dashboard.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import {
  trigger, state, style,
  animate, transition
} from '@angular/animations';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#607D8B',
        transform: 'scale(1)',
        color: '#eee'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
        color: '#607d8b'
      })),
      transition('inactive => active', animate('200ms ease-in')),
      transition('active => inactive', animate('200ms ease-out'))
    ])
  ]
})
export class DashboardComponent implements OnInit {

  heroes: Hero[] = [];
  activeHero: Hero;

  constructor(private heroService: HeroService) { }

  ngOnInit() {
    this.heroService.getHeros()
      .then(heroes => this.heroes = heroes.slice(1, 5));
  }

  changeActiveHero(hero: Hero) {
    this.activeHero = hero;
  }
  isActiveHero(hero: Hero): boolean {
    return this.activeHero === hero;
  }
  getState(hero) {
    return this.isActiveHero(hero) ? 'active' : 'inactive';
  }
}

```

### 在视图绑定事件

`/src/app/dashboard/dashboard.component.jade`

```jade
h3 最强英雄
.grid.grid-pad
  a.col-1-4(*ngFor="let hero of heroes", [routerLink]="['/detail', hero.id]",
    (mouseenter)="changeActiveHero(hero)",
    (mouseleave)="changeActiveHero(null)")
    .module([@heroState]="getState(hero)")
      h4 {{hero.name}}
```

测试效果

###　在设置样式里要注意

如果在　css 中, 设置了 `:hover`, 注意是否要完整覆盖

动画样式是写在元素内联样式,优先级高于 css

`inactive` `active` 中的样式一般要对应设置,不然会产生残留影象

## 状态与转场

Angular动画是由状态和状态之间的转场效果所定义的

### 状态

- state具体定义了每个状态的最终样式。
- 一旦元素转场到那个状态，该样式就会被应用到此元素上
- 当它留在此状态时，这些样式也会一直保持着

### 转场

- 定义完状态，就能定义在状态之间的各种转场了。
- 每个转场都会控制一条在一组样式和下一组样式之间切换的时间线

```ts
transition('inactive => active', animate('100ms ease-in')),
transition('active => inactive', animate('100ms ease-out'))
```

如果多个转场都有同样的时间线配置，就可以把它们合并进同一个transition定义中

```ts
transition('inactive => active, active => inactive',
 animate('100ms ease-out'))
```

如果要对同一个转场的两个方向都使用相同的时间线（就像前面的例子中那样），就可以使用<=>这种简写语法

```ts
transition('inactive <=> active', animate('100ms ease-out'))
```

## 只在动画期间生效的设置方法

有时希望一些样式只在动画期间生效，但在结束后并不保留它们。这时可以把这些样式内联在transition中进行定义。 在这个例子中，该元素会立刻获得一组样式，然后动态转场到下一个状态。当转场结束时，这些样式并不会被保留，因为它们并没有被定义在state中

```ts
transition('inactive => active', [
  style({
    backgroundColor: '#cfd8dc',
    transform: 'scale(1.3)'
  }),
  animate('80ms ease-in', style({
    backgroundColor: '#eee',
    transform: 'scale(1)'
  }))
]),
```

## * (通配符)状态

*(通配符)状态匹配任何动画状态。

当定义那些不需要管当前处于什么状态的样式及转场时，这很有用。

- 当该元素的状态从active变成任何其它状态时，active => * 转场都会生效
- 当在任意两个状态之间切换时，* => * 转场都会生效

## void 状态

有一种叫做void的特殊状态，它可以应用在任何动画中。它表示元素没有被附加到视图。这种情况可能是由于它尚未被添加进来或者已经被移除了。 void状态在定义“进场”和“离场”的动画时会非常有用

比如当一个元素离开视图时，* => void转场就会生效，而不管它在离场以前是什么状态



