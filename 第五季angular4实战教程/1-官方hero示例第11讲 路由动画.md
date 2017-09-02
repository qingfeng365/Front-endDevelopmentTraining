# 1-官方hero示例第11讲 路由动画

## 为详情组件增加进场离场动画

创建独立的动画描述文件

`/src/app/animations.ts`

```ts
import { trigger, state, style, transition, animate} from '@angular/animations';
import { AnimationEntryMetadata } from '@angular/core';

export const slideInDownAnimation: AnimationEntryMetadata =
trigger('routeAnimation', [
  state('*',
    style({
      opacity: 1,
      transform: 'translateX(0)'
    })
  ),
  transition(':enter', [
    style({
      opacity: 1,
      transform: 'translateX(-100%)'
    }),
    animate('3s ease-in')
  ]),
  transition(':leave', [
    animate('2s ease-out', style({
      opacity: 0,
      transform: 'translateY(100%)'
    }))
  ])
]);


```

注意: 进场动画,如果时间太短会显现不出来,

原因, `div(*ngIf='hero')` 引起的,

而获取 `hero`, 有设置延时.

要解决该问题, 需要使用 resolve 路由守卫

## 说明

官方提供的路由动画方案比较麻烦,主要用于每个组件都可以使用不同的动画效果时使用

如果可以统一效果, 可以使用更简单的方案

就是在 根组件 的 `router-outlet` 节点, 封装一个父节点,

在该父节点上定义动画,

要使用这种方案, 需要主动触发, 不能使用进场和离场的别名
