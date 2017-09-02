# 1-官方hero示例第08讲 http

## 引入 http 模块

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
import { HttpModule } from '@angular/http';


@NgModule({
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroesComponent,
    DashboardComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

## 使用 [内存 Web API] 模拟服务

### 安装 angular-in-memory-web-api

`npm install angular-in-memory-web-api --save`

### 创建内存数据服务

`ng g s mock-data/inMemoryData`

`/src/app/mock-data/in-memory-data.service.ts`

```ts
import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from '../model/hero';

@Injectable()
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const heroes: Hero[] = [
      { id: 11, name: '美国队长' },
      { id: 12, name: '钢铁侠' },
      { id: 13, name: '雷神' },
      { id: 14, name: '绿巨人' },
      { id: 15, name: '黑寡妇' },
      { id: 16, name: '鹰眼' },
      { id: 17, name: '金刚狼' },
      { id: 18, name: '万磁王' },
      { id: 19, name: '死侍' },
      { id: 20, name: '月光骑士' }
    ];
    return { heroes: heroes };
  }

  constructor() { }

}

```

## 在根模块导入 InMemoryWebApiModule

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
    FormsModule,
    AppRoutingModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService)
  ],
  providers: [HeroService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

> 注意:
> InMemoryWebApiModule 应在 HttpModule 后面引入
> InMemoryWebApiModule将Http客户端默认的后端服务 — 这是一个辅助服务，负责与远程服务器对话 — 替换成了内存 Web API服务
> 
> forRoot()方法的名字告诉我们，应该只在设置根模块AppModule时调用InMemoryWebApiModule一次。不要再次调用它。
> 

## 修改 HeroService 使用 http

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class HeroService {

  private heroesUrl = 'api/heroes';

  constructor(private http: Http) { }

  getHeros(): Promise<Hero[]> {
    return this.http.get(this.heroesUrl)
      .toPromise()
      .then(res => res.json().data as Hero[])
      .catch(err => {
        console.log(err);
        return Promise.reject(err.message || err)
      })
  }

  getHero(id: number): Promise<Hero> {
    return this.getHeros()
      .then(heroes => heroes.find(hero => hero.id === id));
  }

}

```

> 说明
> angular-in-memory-web-api 会拦截 使用 angular 的 http 服务发出的请求
> 浏览器 或 第三方的 请求是不会拦截的,
> 并对所发出的请求采用以下形式解析
> `:base/:collectionName/:id?` 
> 因为只要 collectionName 对应即可使用
> :base 的内容是随意指定的
> :id? 表示可以没有,如果有的话, 则数据中,应用 id 字段(key) 
> 
> 如果需要模拟延时, 在根模块导入时设置:
> 
> InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 })
> 
> res.json() 是将 返回的 body 所包含的字符串, 按 json 解析
> 
> angular-in-memory-web-api 返回的数据格式: 
> 
> {data: [] | {}}
> 
> 

## 修改 getHero 方法

并同时,提取错误处理方法

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class HeroService {

  private heroesUrl = 'api/heroes';

  constructor(private http: Http) { }

  getHeros(): Promise<Hero[]> {
    return this.http.get(this.heroesUrl)
      .toPromise()
      .then(res => res.json().data as Hero[])
      .catch(this.catchError);
  }

  getHero(id: number): Promise<Hero> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(res => res.json().data as Hero)
      .catch(this.catchError);

  }

  catchError(err) {
    console.log(err);
    return Promise.reject(err.message || err)
  }

}

```

## 增加对英雄的 增删改 功能

### 更新英雄详情

`/src/app/service/hero.service.ts`

```ts
  update(hero: Hero): Promise<Hero> {
    const url = `${this.heroesUrl}/${hero.id}`;
    return this.http.put(url, hero)
      .toPromise()
      .then((res) => hero)
      .catch(this.catchError);
  }
```

> angular-in-memory-web-api 在 put 成功后, 是不返回数据的, res.body: null
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
  | &nbsp;
  button((click)="save()") 保存
```

`/src/app/hero-detail/hero-detail.component.ts`

```ts
  save() {
    this.heroService.update(this.hero)
      .then(() => this.goBack());
  }
```

### 添加英雄


`/src/app/service/hero.service.ts`

```ts
  create(name: string): Promise<Hero> {
    const body = {name: name};
    return this.http.post(this.heroesUrl, body)
      .toPromise()
      .then(res => res.json().data as Hero )
      .catch(this.catchError);
  }
```


`/src/app/heroes/heroes.component.jade`

```jade
h2 英雄列表
div
  label 英雄名称：
  input(#heroName='')
  button((click)="add(heroName.value); heroName.value=''") 新增
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    | {{hero.name}}
div(*ngIf="selectedHero")
  h2 {{selectedHero.name}}
  button((click)="gotoDetail()") 查看详情
```

>
> `#heroName` 通过对 input 设置模板本地变量, 可以在模板表达式获得 input 的 dom 元素对象
> 
> heroName.value 是 input 的 dom 元素, 本身的属性
> 
> 这种方式可以避免要用 双向绑定, 在组件内部创建对应的成员
> 
> 事件表达式,可以写多条语句
>

`/src/app/heroes/heroes.component.ts`

```ts
  add(name: string) {
    name = name.trim();
    if (name === '') { return; }

    this.heroService.create(name)
      .then(hero => {
        this.heroes.push(hero);
        this.selectedHero = null;
      })

  }
```

### 删除英雄


`/src/app/service/hero.service.ts`

```ts
  delete(id: number): Promise<void> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.delete(url)
      .toPromise()
      .then(res => null)
      .catch(this.catchError);
  }
```

为列表增加删除按钮和样式

`/src/app/heroes/heroes.component.jade`

```jade
h2 英雄列表
div
  label 英雄名称：
  input(#heroName='')
  button((click)="add(heroName.value); heroName.value=''") 新增
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    span {{hero.name}}
    | &nbsp;
    button.delete((click)="delete(hero); $event.stopPropagation()") x
div(*ngIf="selectedHero")
  h2 {{selectedHero.name}}
  button((click)="gotoDetail()") 查看详情
```

> 注意,删除按钮 button 的 父级 li 本身也有绑定 (click) , 因此要 禁止事件冒泡
> 

`/src/app/heroes/heroes.component.css`

```css
button.delete {
  float: right;
  margin-top: 2px;
  margin-right: .8em;
  background-color: gray !important;
  color: white;
  border-style: none;
}
```

`/src/app/heroes/heroes.component.ts`

```ts
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
```