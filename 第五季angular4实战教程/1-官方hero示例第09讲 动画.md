# 1-官方hero示例第09讲 动画

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

## 修改 Hero 模型

`/src/app/model/hero.ts`

```ts
export class Hero {
  id: number;
  name: string;
  state?: string;
}
```

## 创建新的列表组件

`ng g c animateHeroList`

### 设置路由

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeroesComponent } from './heroes/heroes.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './animate-hero-list/animate-hero-list.component';

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

`/src/app/app.component.jade`

```jade
h1 {{title}}
nav
  a(routerLink="/dashboard",routerLinkActive="active") 推荐
  | &nbsp;
  a(routerLink="/heroes",routerLinkActive="active") 列表
  | &nbsp;
  a(routerLink="/animate-hero-list",routerLinkActive="active") 动画演示
router-outlet

```

### 获取列表内容

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css']
})
export class AnimateHeroListComponent implements OnInit {
  heroes: Hero[] = [];
  constructor(private heroService: HeroService, private router: Router) { }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => this.heroes =
        [...heroes].map(e => Object.assign({}, e, { state: 'inactive' }))
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }
}

```

`/src/app/animate-hero-list/animate-hero-list.component.jade`

```jade
h2 英雄列表
ul
  li(*ngFor='let hero of heroes')
    | {{hero.name}}
```

`/src/app/animate-hero-list/animate-hero-list.component.css`

```css
ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: block;
  width: 120px;
  line-height: 50px;
  padding: 0 10px;
  box-sizing: border-box;
  background-color: #eee;
  border-radius: 4px;
  margin: 10px;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
}

.active {
  background-color: #cfd8dc;
  transform: scale(1.1);
}

.inactive {
  background-color: #eee;
  transform: scale(1);
}
```

###　设置点击切换状态功能

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css']
})
export class AnimateHeroListComponent implements OnInit {
  heroes: Hero[] = [];
  constructor(private heroService: HeroService, private router: Router) { }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => this.heroes =
        [...heroes].map(e => Object.assign({}, e, { state: 'inactive' }))
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }

  toggleState(hero: Hero): void {
    hero.state = (hero.state === 'inactive') ? 'active' : 'inactive';
  }
}

```

`/src/app/animate-hero-list/animate-hero-list.component.jade`

```jade
h2 英雄列表
ul
  li(*ngFor='let hero of heroes',
    (click)="toggleState(hero)")
    | {{hero.name}}

```

## 在两个状态间转场

### 在组件装饰器使用 animations

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out'))
    ])
  ]
})
export class AnimateHeroListComponent implements OnInit {
  heroes: Hero[] = [];
  constructor(private heroService: HeroService, private router: Router) { }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => this.heroes =
        [...heroes].map(e => Object.assign({}, e, { state: 'inactive' }))
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }

  toggleState(hero: Hero): void {
    hero.state = (hero.state === 'inactive') ? 'active' : 'inactive';
  }
}

```

### 在视图中绑定动画触发器

`/src/app/animate-hero-list/animate-hero-list.component.jade`

```jade
h2 英雄列表
ul
  li(*ngFor='let hero of heroes',
    (click)="toggleState(hero)",
    [@heroState]="hero.state")
    | {{hero.name}}

```

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

### 只在动画期间生效的设置方法

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

### * (通配符)状态

*(通配符)状态匹配任何动画状态。

当定义那些不需要管当前处于什么状态的样式及转场时，这很有用。

- 当该元素的状态从active变成任何其它状态时，active => * 转场都会生效
- 当在任意两个状态之间切换时，* => * 转场都会生效

### void 状态

有一种叫做void的特殊状态，它可以应用在任何动画中。它表示元素没有被附加到视图。这种情况可能是由于它尚未被添加进来或者已经被移除了。 void状态在定义“进场”和“离场”的动画时会非常有用

比如当一个元素离开视图时，* => void转场就会生效，而不管它在离场以前是什么状态



## 进场与离场

### 增加进场与离场的动画

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => *', [
        style({transform: 'translateX(-100%)'}),
        animate(100)
      ]),
      transition('* => void', [
        animate(100, style({transform: 'translateX(100%)'}))
      ])
    ])
  ]
})
```

测试一下效果

### 增加进场与离场的按钮

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';

import { Observable } from 'rxjs/Rx';
// import 'rxjs/Rx';

@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => *', [
        style({transform: 'translateX(-100%)'}),
        animate(100)
      ]),
      transition('* => void', [
        animate(100, style({transform: 'translateX(100%)'}))
      ])
    ])
  ]
})
export class AnimateHeroListComponent implements OnInit {
  heroes: Hero[] = [];
  _heroes: Hero[] = [];

  constructor(private heroService: HeroService, private router: Router) { }

  getHeroes(): void {
    this.heroService.getHeros().then(
      heroes => {
        this._heroes =
          [...heroes].map(e => Object.assign(
            {}, e, { state: 'inactive' }));
      }
    );
  }

  ngOnInit(): void {
    this.getHeroes();
  }

  toggleState(hero: Hero): void {
    hero.state = (hero.state === 'inactive') ? 'active' : 'inactive';
  }
  enter() {
    this.heroes = [];
    Observable.from(this._heroes)
      .zip(
      Observable.interval(500),
      v => v
      )
      .subscribe(hero => this.heroes.push(hero))
  }
  leave() {
    Observable
      .interval(500)
      .take(this.heroes.length)
      .subscribe(
        v => this.heroes.pop()
      );
  }
}

```

`/src/app/animate-hero-list/animate-hero-list.component.jade`

```jade
h2 英雄列表
  span(*ngIf="_heroes.length > 0")
    button(type="button",(click)="enter()") 进场
    | &nbsp;
    button(type="button",(click)="leave()") 离场
ul
  li(*ngFor='let hero of heroes',
    (click)="toggleState(hero)",
    [@heroState]="hero.state")
    | {{hero.name}}

```

##  进场与离场的别名

这两个常见的动画有自己的别名：

```ts
transition(':enter', [ ... ]); // void => *
transition(':leave', [ ... ]); // * => void
```

## 动画时间线

对每一个动画转场效果，有三种时间线属性可以调整：持续时间(duration)、延迟(delay)和缓动(easing)函数。它们被合并到了一个单独的转场时间线字符串

持续时间

持续时间控制动画从开始到结束要花多长时间。可以用三种方式定义持续时间：

    作为一个普通数字，以毫秒为单位，如：100

    作为一个字符串，以毫秒为单位，如：'100ms'

    作为一个字符串，以秒为单位，如：'0.1s'

延迟

延迟控制的是在动画已经触发但尚未真正开始转场之前要等待多久。可以把它添加到字符串中的持续时间后面，它的选项格式也跟持续时间是一样的：

    等待100毫秒，然后运行200毫秒：'0.2s 100ms'。

缓动函数 transition-timing-function

缓动函数用于控制动画在运行期间如何加速和减速。比如：使用ease-in函数意味着动画开始时相对缓慢，然后在进行中逐步加速。可以通过在这个字符串中的持续时间和延迟后面添加第三个值来控制使用哪个缓动函数(如果没有定义延迟就作为第二个值)。

    等待100毫秒，然后运行200毫秒，并且带缓动：'0.2s 100ms ease-out'

    运行200毫秒，并且带缓动：'0.2s ease-in'


> 注意:
> 
> 延迟 必须带时间单位,不然会错
> 
> 缓动函数 只能用基本的, 大部分不能使用
> 
> 

能用的缓动函数

[https://developer.mozilla.org/en-US/docs/Web/API/AnimationEffectTimingProperties/easing](https://developer.mozilla.org/en-US/docs/Web/API/AnimationEffectTimingProperties/easing)

[http://www.zhangxinxu.com/css3/css3-transition-timing-function.php](http://www.zhangxinxu.com/css3/css3-transition-timing-function.php)

linear      线性过渡 
ease        平滑过渡
ease-in     由慢到快
ease-out    由快到慢
ease-in-out 由慢到快再到慢

可以考虑 使用 cubic-bezier , 大部分缓动函数都有对应的 cubic-bezier 


如: 
ease        = cubic-bezier(0.25, 0.1, 0.25, 1)

ease-in     = cubic-bezier(0.42, 0, 1, 1)
ease-out    = cubic-bezier(0, 0, 0.58, 1)
ease-in-out = cubic-bezier(0.42, 0, 0.58, 1)

带回弹效果: cubic-bezier(0.175, 0.885, 0.32, 1.275)

cubic-bezier 在线工具

[http://cubic-bezier.com](http://cubic-bezier.com)

cubic-bezier 模板
[http://web.chacuo.net/css3beziertool](http://web.chacuo.net/css3beziertool)



关于 时间函数的资料

[https://www.w3.org/TR/css-timing-1/#introduction](https://www.w3.org/TR/css-timing-1/#introduction)

### 示例

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)',
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => *', [
        style({
          opacity: 0,
          transform: 'translateX(-100%)'
        }),
        animate('2s cubic-bezier(.91,1.54,.7,.84)')
      ]),
      transition('* => void', [
        animate('2s 500ms ease-out', style({
          opacity: 0,
          transform: 'translateX(100%)'
        }))
      ])
    ])
  ]
})
```



## 从不同的状态下进场和离场

- 非激活英雄进场：void => inactive
- 激活英雄进场：void => active
- 非激活英雄离场：inactive => void
- 激活英雄离场：active => void

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from '../model/hero';
import { HeroService } from '../service/hero.service';
import { Router } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';

import { Observable } from 'rxjs/Rx';
// import 'rxjs/Rx';

@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)'
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => inactive', [
        style({transform: 'translateX(-100%)'}),
        animate(100)
      ]),
      transition('inactive => void', [
        animate(100, style({transform: 'translateX(100%)'}))
      ]),
      transition('void => active', [
        style({transform: 'scale(0)'}),
        animate(200)
      ]),
      transition('active => void', [
        animate(200, style({transform: 'scale(0)'}))
      ])
    ])
  ]
})

```

## 自动属性值计算

有时候，我们想在动画中使用的尺寸类样式，它的值在开始运行之前都是不可知的。比如，元素的宽度和高度往往依赖于它们的内容和屏幕的尺寸。处理这些属性对CSS动画而言通常是相当棘手的

如果用Angular动画，就可以用一个特殊的*属性值来处理这种情况。该属性的值将会在运行期被计算出来，然后插入到这个动画中。

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)',
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('* => void', [
        style({height: '*'}),
        animate(250, style({height: 0}))
      ]),
      transition('void => *', [
        style({height: 0}),
        animate(250, style({height: '*'}))
      ])
    ])
  ]
})
```

## 基于关键帧(Keyframes)的多阶段动画

通过定义动画的关键帧，可以把两组样式之间的简单转场，升级成一种更复杂的动画，它会在转场期间经历一个或多个中间样式

每个关键帧都可以被指定一个偏移量，用来定义该关键帧将被用在动画期间的哪个时间点。偏移量是一个介于0(表示动画起点)和1(表示动画终点)之间的数组

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)',
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => *', [
        animate('1s', keyframes([
          style({opacity: 0, transform: 'translateX(-100%)', offset: 0}),
          style({opacity: 1, transform: 'translateX(15px)',  offset: 0.3}),
          style({opacity: 1, transform: 'translateX(0)',     offset: 1.0})
        ]))
      ]),
      transition('* => void', [
        animate('1s', keyframes([
          style({opacity: 1, transform: 'translateX(0)',     offset: 0}),
          style({opacity: 1, transform: 'translateX(-15px)', offset: 0.7}),
          style({opacity: 0, transform: 'translateX(100%)',  offset: 1.0})
        ]))
      ])
    ])
  ]
})
```

## 并行动画组(Group)

我们已经知道该如何在同一时间段进行多个样式的动画了：只要把它们都放进同一个style()定义中就行了！

但我们也可能会希望为同时发生的几个动画配置不同的时间线。比如，同时对两个CSS属性做动画，但又得为它们定义不同的缓动函数。

这种情况下就可以用动画组来解决了。在这个例子中，我们同时在进场和离场时使用了组，以便能让它们使用两种不同的时间线配置。 它们被同时应用到同一个元素上，但又彼此独立运行：

示例: 其中一个动画组对元素的transform和width做动画，另一个组则对opacity做动画

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
@Component({
  selector: 'app-animate-hero-list',
  templateUrl: './animate-hero-list.component.html',
  styleUrls: ['./animate-hero-list.component.css'],
  animations: [
    trigger('heroState', [
      state('inactive', style({
        backgroundColor: '#eee',
        transform: 'scale(1)',
      })),
      state('active', style({
        backgroundColor: '#cfd8dc',
        transform: 'scale(1.1)',
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out')),
      transition('void => *', [
        style({ width: 10, transform: 'translateX(50px)', opacity: 0 }),
        group([
          animate('1.3s 0.1s ease', style({
            transform: 'translateX(0)',
            width: 120
          })),
          animate('2.3s ease', style({
            opacity: 1
          }))
        ])
      ]),
      transition('* => void', [
        group([
          animate('1.3s ease', style({
            transform: 'translateX(50px)',
            width: 10
          })),
          animate('2.3s 0.2s ease', style({
            opacity: 0
          }))
        ])
      ])
    ])
  ]
})
```

## 动画回调

当动画开始和结束时，会触发一个回调。


动画开始: @triggerName.start
动画结束: @triggerName.done

无论动画是否实际执行过，那些回调都会触发

`/src/app/animate-hero-list/animate-hero-list.component.ts`

```ts
  logit(event: any) {
    console.log(event);
  }
```

`/src/app/animate-hero-list/animate-hero-list.component.jade`

```ts
h2 英雄列表
  span(*ngIf="_heroes.length > 0")
    button(type="button",(click)="enter()") 进场
    | &nbsp;
    button(type="button",(click)="leave()") 离场
ul
  li(*ngFor='let hero of heroes',
    (click)="toggleState(hero)",
    [@heroState]="hero.state",
    (@heroState.start)="logit($event)",
    (@heroState.done)="logit($event)")
    | {{hero.name}}
```

## 常用动画 api 参考 

CSS transform 

- transform 属性允许你修改CSS视觉格式模型的坐标空间
- 使用它，元素可以被平移（translate）、旋转（rotate）、缩放（scale）、倾斜（skew）
- CSS transform 属性 , 只对 block 级元素生效
[https://developer.mozilla.org/zh-CN/docs/Web/CSS/transform#%E5%B9%B3%E7%A7%BB](https://developer.mozilla.org/zh-CN/docs/Web/CSS/transform#%E5%B9%B3%E7%A7%BB)