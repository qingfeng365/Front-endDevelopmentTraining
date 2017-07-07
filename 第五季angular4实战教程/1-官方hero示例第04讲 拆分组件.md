# 1-官方hero示例第04讲 拆分组件

备课项目

`myheroes-test`

github 项目地址
[https://github.com/qingfeng365/myheroes-test.git](https://github.com/qingfeng365/myheroes-test.git)

分支 E1-04

将根组件 拆分成 详情组件 列表组件

## 将`Hero`数据模型独立出来

新建目录:

`/src/app/model`

新建文件

`/src/app/model/hero.ts`

```ts
export class Hero {
  id: number;
  name: string;
}

```

## 创建详情组件

`ng g c heroDetail`

`/src/app/hero-detail/hero-detail.component.jade`

> 注意将 `selectedHero` 改为 `hero`

```jade
div(*ngIf='hero')
  h2 {{hero.name}} 详情
  div
    label 编号:
    | {{hero.id}}
  div
    label 名称:
    input([(ngModel)]='hero.name',placeholder='名称')
```

## 详情组件定义输入属性

`/src/app/hero-detail/hero-detail.component.ts`

```ts
import { Component, OnInit, Input } from '@angular/core';
import { Hero } from '../model/hero';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

  @Input()
  hero: Hero;

  constructor() { }

  ngOnInit() {
  }

}

```

## 在根组件视图中引用子组件

`/src/app/app.component.jade`

```jade
h1 {{title}}
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    | {{hero.name}}
app-hero-detail([hero]="selectedHero")
```
