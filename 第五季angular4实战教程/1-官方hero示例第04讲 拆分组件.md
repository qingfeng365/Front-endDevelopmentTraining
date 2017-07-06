# 1-官方hero示例第04讲 拆分组件

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
div(*ngIf='selectedHero')
  h2 {{hero.name}} 详情
  div
    label 编号:
    | {{hero.id}}
  div
    label 名称:
    input([(ngModel)]='hero.name',placeholder='名称')
```


