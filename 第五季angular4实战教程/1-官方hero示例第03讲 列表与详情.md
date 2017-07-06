# 1-官方hero示例第03讲 列表与详情

## 增加英雄列表显示

### 处理数据模型

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '英雄之旅';
  hero: Hero = {
    id: 1,
    name: '暴风女'
  }

  heroes: Hero[] = [
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
  ]

}

export class Hero {
  id: number;
  name: string;
}


```

### 处理视图

`/src/app/app.component.jade`

```jade
h1 {{title}}
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes')
    span.badge() {{hero.id}}
    | {{hero.name}}
h2 {{hero.name}} 详情
div
  label 编号:
  | {{hero.id}}
div
  label 名称:
  input([(ngModel)]='hero.name',placeholder='名称')
```

### 增加样式

`/src/app/app.component.css`

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

## 列表与详情联动

### 增加事件处理

`/src/app/app.component.ts`

```ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '英雄之旅';

  heroes: Hero[] = [
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
  ]

  selectedHero: Hero;
  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }
}

export class Hero {
  id: number;
  name: string;
}


```


### 修改视图

`/src/app/app.component.jade` 

```jade
h1 {{title}}
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)')
    span.badge() {{hero.id}}
    | {{hero.name}}
h2 {{selectedHero.name}} 详情
div
  label 编号:
  | {{selectedHero.id}}
div
  label 名称:
  input([(ngModel)]='selectedHero.name',placeholder='名称')
```


此时浏览器会报错,因`selectedHero`没有考虑还没有选择时情况

### 增加ngIf处理

`/src/app/app.component.jade` 

```jade
h1 {{title}}
h2 英雄列表
ul.heroes
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)')
    span.badge() {{hero.id}}
    | {{hero.name}}
div(*ngIf='selectedHero')
  h2 {{selectedHero.name}} 详情
  div
    label 编号:
    | {{selectedHero.id}}
  div
    label 名称:
    input([(ngModel)]='selectedHero.name',placeholder='名称')
```

### 处理列表当前选项的样式绑定

`/src/app/app.component.jade`

```jade
  li(*ngFor='let hero of heroes',(click)='onSelect(hero)',
    [class.selected]='hero === selectedHero')
    span.badge() {{hero.id}}
    | {{hero.name}}
```