# 1-官方hero示例第02讲 英雄编辑器

## 设置根组件

- 创建英雄模型类
- 创建首个英雄
- 显示英雄详情

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
}

export class Hero {
  id: number;
  name: string;
}

```

> 注意,直接实例化Hero的方法

`/src/app/app.component.jade`

```jade
h1 {{title}}
h2 {{hero.name}} 详情
div
  label id:
  | {{hero.id}}
div
  label name:
  | {{hero.name}}
```

## 改用编辑框处理英雄名称

### 模板改用input

`/src/app/app.component.jade`

```jade
h1 {{title}}
h2 {{hero.name}} 详情
div
  label id:
  | {{hero.id}}
div
  label name:
  input([(ngModel)]='hero.name',placeholder='名称')
```

> 注意:
这个时候会报错,原因是 `[(ngModel)]` 双向绑定需要引用 `FormsModule` 模块

### 引用 `FormsModule` 模块

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```
