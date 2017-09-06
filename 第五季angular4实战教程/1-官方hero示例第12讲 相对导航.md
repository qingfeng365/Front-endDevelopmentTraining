# 1-官方hero示例第12讲 相对导航

## 创建危机中心特性模块

```
ng g m crisisCenter

ng g c crisisCenter

ng g c crisisCenter/crisisList

ng g c crisisCenter/crisisDetail

ng g m crisisCenter/crisisCenterRouting --flat

ng g s crisisCenter/crisis

```

> 说明:
> 
> crisisCenter 是特性模块的主组件 (创建时不用指定目录)
> 
> crisisList 是 crisisCenter 的子组件
> 
> crisisDetail 是 crisisList 的子组件
> 
> 父子关系是 通过路由实现, 不是通过视图
> 
> --flat : 表示不用自动创建目录

## 定义路由

`/src/app/crisis-center/crisis-center.component.jade`

```jade
h2 危机中心
router-outlet
```

`/src/app/crisis-center/crisis-list/crisis-list.component.jade`

```jade
h4 危机列表
router-outlet
```

`/src/app/crisis-center/crisis-center-routing.module.ts`

```ts
import { CrisisDetailComponent } from './crisis-detail/crisis-detail.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CrisisCenterComponent } from './crisis-center.component';
import { CrisisListComponent } from './crisis-list/crisis-list.component';

const routes: Routes = [
  {
    path: 'crisis-center',
    component: CrisisCenterComponent,
    children: [
      {
        path: '',
        component: CrisisListComponent,
        children: [
          {
            path: ':id',
            component: CrisisDetailComponent
          }
        ]
      }
    ]

  }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class CrisisCenterRoutingModule { }

```

> 注意:
>
> 当使用空路径时 '' , 表示使用父路径来匹配自身
> 
> CrisisListComponent 的实际路径可以理解为
> 
> 'crisis-center' + '' 
> 
> 也可以理解为覆盖了父路径的设置
> 



`/src/app/crisis-center/crisis-center.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CrisisCenterComponent } from './crisis-center.component';
import { CrisisListComponent } from './crisis-list/crisis-list.component';
import { CrisisDetailComponent } from './crisis-detail/crisis-detail.component';
import { CrisisCenterRoutingModule } from './crisis-center-routing.module';

@NgModule({
  imports: [
    CommonModule,
    CrisisCenterRoutingModule
  ],
  declarations: [
    CrisisCenterComponent,
    CrisisListComponent,
    CrisisDetailComponent]
})
export class CrisisCenterModule { }

```

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroesModule } from './heroes/heroes.module';
import { CrisisCenterModule } from './crisis-center/crisis-center.module';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    HeroesModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 }),
    CrisisCenterModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

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
  | &nbsp;
  a(routerLink="/crisis-center",routerLinkActive="active") 危机中心
router-outlet

```

测试路由正常

## 准备危机中心数据

`/src/app/crisis-center/crisis.service.ts`

```ts
import { Injectable } from '@angular/core';

@Injectable()
export class CrisisService {
  crisesPromise = Promise.resolve(CRISES);
  constructor() { }
  getCrises() { return this.crisesPromise; }

  getCrisisById(id: number | string) {
    return this.crisesPromise
      .then(crises => crises.find(crisis => crisis.id === +id));
  }
}

export class Crisis {
  constructor(public id: number, public name: string) { }
}

const CRISES = [
  new Crisis(1, '恶龙燃烧城市'),
  new Crisis(2, '天降大白鲨'),
  new Crisis(3, '小行星撞地球'),
  new Crisis(4, '联盟峰会延迟'),
];

```

> 注意
> 
> 路由参数为字符串, 模型 id 为数值的另一种处理方式
> 

`/src/app/crisis-center/crisis-list/crisis-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Crisis, CrisisService } from '../crisis.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-crisis-list',
  templateUrl: './crisis-list.component.html',
  styleUrls: ['./crisis-list.component.css']
})
export class CrisisListComponent implements OnInit {
  crises: Crisis[];
  selectedId: number;
  constructor(
    private service: CrisisService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.service.getCrises()
      .then(crises => this.crises = crises);
  }
  isSelected(crisis: Crisis) {
    return crisis.id === this.selectedId;
  }
  onSelect(crisis: Crisis) {
    this.selectedId = crisis.id;
  }
}

```

`/src/app/crisis-center/crisis-list/crisis-list.component.jade`

```jade
ul.items
  li(*ngFor="let crisis of crises",
    (click)="onSelect(crisis)",
    [class.selected]="isSelected(crisis)")
    span.badge {{ crisis.id }}
    | {{ crisis.name }}
router-outlet

```

`/src/app/crisis-center/crisis-list/crisis-list.component.css`

```css
.items {
  margin: 0 0 2em 0;
  list-style-type: none;
  padding: 0;
  width: 24em;
}

.items li {
  cursor: pointer;
  position: relative;
  left: 0;
  background-color: #EEE;
  margin: .5em;
  padding: .3em 0;
  height: 1.6em;
  border-radius: 4px;
}

.items li:hover {
  color: #607D8B;
  background-color: #DDD;
  left: .1em;
}

.items li.selected {
  background-color: #CFD8DC;
  color: white;
}

.items li.selected:hover {
  background-color: #BBD8DC;
}

.items .text {
  position: relative;
  top: -3px;
}

.items .badge {
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

## 使用相对导航

现在要处理危机详情的路由导航, 如果仍在使用以斜杠开头的绝对路径来导航到危机详情的路由,

路由器会从路由配置的顶层来匹配像这样的绝对路径。

但是那样会把链接钉死在特定的父路由结构上。 

如果我们修改了父路径/crisis-center，那就不得不修改每一个链接参数数组。

通过改成定义相对于当前URL的路径，我们可以把链接从这种依赖中解放出来。 

当我们修改了该特性区的父路由路径时，该特性区内部的导航仍然完好无损。


### 相对导航使用要点


> 在链接参数数组中，路由器支持“目录式”语法来指导我们如何查询路由名：
> 
> ./或无前导斜线形式是相对于当前级别的。
> 
> ../会回到当前路由路径的上一级。
>
> 我们可以把相对导航语法和一个祖先路径组合起来用。 如果不得不导航到一个兄弟路由，我们可以用../<sibling>来回到上一级，然后进入兄弟路由路径中。
> 

#### Router.navigate

用 Router.navigate 方法导航到相对路径时，我们必须提供当前的 ActivatedRoute，来让路由器知道我们现在位于路由树中的什么位置。

在链接参数数组中，添加一个带有 relativeTo 属性的对象，并把它设置为当前的 ActivatedRoute。 这样路由器就会基于当前激活路由的位置来计算出目标URL。

> 注意:
> 当调用路由器的navigateByUrl时，总是要指定完整的绝对路径。

#### [RouterLink]

如果我们用RouterLink来代替Router服务进行导航，就要使用相同的链接参数数组，不过不再需要提供relativeTo属性。 ActivatedRoute已经隐含在了RouterLink指令中。

### 使用相对导航进入详情路由

`/src/app/crisis-center/crisis-list/crisis-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Crisis, CrisisService } from '../crisis.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-crisis-list',
  templateUrl: './crisis-list.component.html',
  styleUrls: ['./crisis-list.component.css']
})
export class CrisisListComponent implements OnInit {
  crises: Crisis[];
  selectedId: number;
  constructor(
    private service: CrisisService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.service.getCrises()
      .then(crises => this.crises = crises);
  }
  isSelected(crisis: Crisis) {
    return crisis.id === this.selectedId;
  }
  onSelect(crisis: Crisis) {
    this.selectedId = crisis.id;

    this.router.navigate([this.selectedId], {relativeTo: this.route});
  }
}

```

### 使用相对导航返回列表路由

`/src/app/crisis-center/crisis-detail/crisis-detail.component.jade`

```jade
div(*ngIf="crisis")
  h3 {{editName}}
  div
    label 序号:
    | {{crisis.id}}
  div
    label 名称:
    input([(ngModel)]="editName", placeholder="请输入名称")
  p
    button((click)="save()") 保存
    button((click)="cancel()") 放弃

```

`/src/app/crisis-center/crisis-detail/crisis-detail.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Crisis, CrisisService } from '../crisis.service';
import { ActivatedRoute, Router, Params } from '@angular/router';

@Component({
  selector: 'app-crisis-detail',
  templateUrl: './crisis-detail.component.html',
  styleUrls: ['./crisis-detail.component.css']
})
export class CrisisDetailComponent implements OnInit {
  crisis: Crisis;
  editName: string;
  constructor(
    private service: CrisisService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.params
      .subscribe(Params => {
        const id: string = Params.id;
        this.service.getCrisisById(id)
          .then(crisis => {
            this.editName = crisis.name;
            this.crisis = crisis;
          });
      })
  }
  cancel() {
    this.gotoCrises();
  }

  save() {
    this.crisis.name = this.editName;
    this.gotoCrises();
  }

  gotoCrises() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
}

```

`/src/app/crisis-center/crisis-detail/crisis-detail.component.css`

```css
input {
  width: 20em
}
```

`/src/app/crisis-center/crisis-center.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrisisCenterComponent } from './crisis-center.component';
import { CrisisListComponent } from './crisis-list/crisis-list.component';
import { CrisisDetailComponent } from './crisis-detail/crisis-detail.component';
import { CrisisCenterRoutingModule } from './crisis-center-routing.module';
import { CrisisService } from './crisis.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    CrisisCenterRoutingModule
  ],
  declarations: [
    CrisisCenterComponent,
    CrisisListComponent,
    CrisisDetailComponent
  ],
  providers: [CrisisService]
})
export class CrisisCenterModule { }

```
