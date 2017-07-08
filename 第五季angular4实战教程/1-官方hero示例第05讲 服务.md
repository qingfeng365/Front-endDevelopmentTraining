# 1-官方hero示例第05讲 服务

备课项目

`myheroes-test`

github 项目地址
[https://github.com/qingfeng365/myheroes-test.git](https://github.com/qingfeng365/myheroes-test.git)

将现在的模拟数据封装成服务

## 创建HeroService

`ng g s service/hero`

创建后会提示:

```
installing service
  create src/app/service/hero.service.spec.ts
  create src/app/service/hero.service.ts
  WARNING Service is generated but not provided, it must be provided to be used
```

说明:

- 服务默认会在根目录,如果需要目录,需要在创建时指定
- 服务与组件不同,不会自动加入模块中,需要自行决定如何处理

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';

@Injectable()
export class HeroService {

  constructor() { }

}

```

> 说明:
> @Injectable() : 官方推荐服务均要使用 @Injectable() 
> @Injectable()的作用时,表示当前服务需要依赖注入其他服务

### 先写一个stub(桩)

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';

@Injectable()
export class HeroService {

  constructor() { }

  getHeros(): Hero[] {
    return [];
  }
}
```

### 将模拟数据单独放到一个文件

创建目录:

`mock-data`

创建文件:

`/src/app/mock-data/mock-heroes.ts`

```ts
import { Hero } from '../model/hero';

export const HEROES: Hero[] = [
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

```

### 修改服务返回模拟数据

`/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';
import { HEROES } from '../mock-data/mock-heroes';

@Injectable()
export class HeroService {

  constructor() { }

  getHeros(): Hero[] {
    return HEROES;
  }
}
```

## 在组件中使用HeroService

### 修改根组件

- 取消根组件原来的虚拟数据
- 在构造器函数注入HeroService
- 并定义getHeroes()方法

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Hero } from './model/hero';
import { HeroService } from './service/hero.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '英雄之旅';

  heroes: Hero[] = [];

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  constructor(private heroService: HeroService) {

  }

  getHeroes(): void {
    this.heroes = this.heroService.getHeros();
  } 
}
```

> 说明:
> 此时运行,浏览器会报错:Error: No provider for HeroService!
> 原因是 没有找到 HeroService 的提供器
> constructor 会向组件的注入器请求注入, 
> 注入器会向 组件的提供器及父组组件的提供器,依次向上请求 要注入服务的实例
> 直到找到为止

### 在根组件注册HeroService

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { Hero } from './model/hero';
import { HeroService } from './service/hero.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [HeroService]
})
export class AppComponent {
  title = '英雄之旅';

  heroes: Hero[] = [];

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  constructor(private heroService: HeroService) {

  }

  getHeroes(): void {
    this.heroes = this.heroService.getHeros();
  }
}

```
### 实现ngOnInit生命周期钩子

`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from './model/hero';
import { HeroService } from './service/hero.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [HeroService]
})
export class AppComponent implements OnInit {

  title = '英雄之旅';

  heroes: Hero[] = [];

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  constructor(private heroService: HeroService) {

  }

  getHeroes(): void {
    this.heroes = this.heroService.getHeros();
  }

  ngOnInit(): void {
    this.getHeroes();
  }
}

```

### 把服务改成Promise

/src/app/service/hero.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Hero } from '../model/hero';
import { HEROES } from '../mock-data/mock-heroes';

@Injectable()
export class HeroService {

  constructor() { }

  getHeros(): Promise<Hero[]> {
    return Promise.resolve(HEROES);
  }
}


```


`/src/app/app.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { Hero } from './model/hero';
import { HeroService } from './service/hero.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [HeroService]
})
export class AppComponent implements OnInit {

  title = '英雄之旅';

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

### 模拟网络延时的写法

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

}

```

