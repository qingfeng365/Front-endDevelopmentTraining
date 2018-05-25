# 02 page-header

## 使用说明

page-header 页头

[https://ng-alain.com/components/page-header](https://ng-alain.com/components/page-header)


页头用来声明页面的主题，包含了用户所关注的最重要的信息，使用户可以快速理解当前页面是什么以及它的功能。

## API

参数 | 说明 | 类型 | 默认值
----|------|-----|------
title | 标题名 | `string` | -
home | 首页文本，若指定空表示不显示  | `string` | `首页`
home_link | 首页链接  | `string` | `/`
home_i18n | 首页链接国际化参数 | `string` | -
autoBreadcrumb | 自动生成导航，以当前路由从主菜单中定位  | `boolean` | `true`
breadcrumb | 导航区域  | `TemplateRef<any>` | -
logo | LOGO区域  | `TemplateRef<any>` | -
action | 操作区域  | `TemplateRef<any>` | -
content | 内容区域  | `TemplateRef<any>` | -
extra | 额外信息区域  | `TemplateRef<any>` | -
tab | 标签区域  | `TemplateRef<any>` | -

**自动生成导航**

默认情况下会根据菜单数据自动生成导航，有时你可能希望隐藏某个节点菜单数据时，可以指定菜单的 `hideInBreadcrumb: true`。

## 关于 面包屑 用法

### 自动处理

####　方法一: 默认方式

```html
<page-header></page-header>
```

相当于

```html
<page-header [autoBreadcrumb]="true"></page-header>
```

autoBreadcrumb 默认为 true

如果 设置 autoBreadcrumb = false, 则 page-header 会跳过 面包屑 内容的生成


####　方法二: 控制面包屑生成

通过菜单节点的 `hideInBreadcrumb` 控制

面包屑 自动生成的算法:

- 根据 url 查找该 url 对应在菜单的节点
- 根据 菜单节点, 计算 所有父级节点, 直到菜单的一级节点 , 组成节点数组
- 根据节点数据 生成 面包屑 段, 如果 节点中的 `hideInBreadcrumb` 为 true ,则跳过该段
- 最后增加 '/' 的 面包屑 段, 根据 `page-header` 的属性, `home` `home_link` `home_i18n` 生成

### 手动处理

当菜单的层级, 不符合当前页面所需要的 面包屑 形式时, 可完全手动生成 面包屑

通过模板本地变量 `#breadcrumb` 指定 `ng-template` 标签

```html
<page-header>
  <ng-template #breadcrumb>
    <nz-breadcrumb>
      <nz-breadcrumb-item>
        <a [routerLink]="['/']">首页</a>
      </nz-breadcrumb-item>
      <nz-breadcrumb-item>
        <a [routerLink]="['/']">Dashboard</a>
      </nz-breadcrumb-item>
      <nz-breadcrumb-item>工作台</nz-breadcrumb-item>
    </nz-breadcrumb>
  </ng-template>
</page-header>
```

## 应用场景

###　最复杂的应用形式


- 自定义面包屑
- 自定义 logo
- 标题区位置不能更改,只能定义标题,以及其它样式(背景,字体)
- 自定义工具栏,位置为 标题区右侧, 靠右对齐
- 自定义内容区, 位置为 标题区 下一行, 靠左对齐
- 自定义附加内容区, 位置为 内容区右侧, 靠右对齐
- 控制 投影区的 样式: `:host ::ng-deep`

```ts
import { Component } from '@angular/core';

@Component({
    selector: 'components-page-header-structure',
    template: `
<page-header [title]="'title'">
    <ng-template #breadcrumb>面包屑</ng-template>
    <ng-template #logo><div class="logo">logo</div></ng-template>
    <ng-template #action><div class="action">action</div></ng-template>
    <ng-template #content><div class="desc">content</div></ng-template>
    <ng-template #extra><div class="extra">extra</div></ng-template>
    <ng-template #tab>
        <nz-tabset [nzSize]="'default'">
            <nz-tab nzTitle="页签一"></nz-tab>
            <nz-tab nzTitle="页签二"></nz-tab>
            <nz-tab nzTitle="页签三"></nz-tab>
        </nz-tabset>
    </ng-template>
</page-header>
    `,
    styles: [`
    :host ::ng-deep .logo {
        background: #3ba0e9;
        color: #fff;
        height: 100%;
    }
    :host ::ng-deep h1.title {
        background: rgba(16, 142, 233, 1);
        color: #fff;
    }
    :host ::ng-deep .action,
    :host ::ng-deep .desc,
    :host ::ng-deep .extra {
        background: #7dbcea;
        color: #fff;
    }
    `]
})
export class ComponentsPageHeaderStructureComponent {
}
```

`用于显示单据详情的页头示例`

```ts
import { Component } from '@angular/core';

@Component({
    selector: 'components-page-header-standard',
    template: `
<page-header [title]="'单号：234231029431'">
    <ng-template #breadcrumb>
        <nz-breadcrumb>
            <nz-breadcrumb-item><a>一级菜单</a></nz-breadcrumb-item>
            <nz-breadcrumb-item><a>二级菜单</a></nz-breadcrumb-item>
            <nz-breadcrumb-item><a>三级菜单</a></nz-breadcrumb-item>
        </nz-breadcrumb>
    </ng-template>
    <ng-template #logo><img src="https://gw.alipayobjects.com/zos/rmsportal/nxkuOJlFJuAUhzlMTCEe.png"></ng-template>
    <ng-template #action>
        <nz-button-group>
            <button nz-button>操作</button>
            <button nz-button>操作</button>
        </nz-button-group>
        <nz-dropdown class="mx-sm">
            <button nz-button nz-dropdown><i class="anticon anticon-ellipsis"></i></button>
            <ul nz-menu>
                <li nz-menu-item>选项一</li>
                <li nz-menu-item>选项二</li>
                <li nz-menu-item>选项三</li>
            </ul>
        </nz-dropdown>
        <button nz-button [nzType]="'primary'">主操作</button>
    </ng-template>
    <ng-template #content>
        <desc-list size="small" col="2">
            <desc-list-item term="创建人">曲丽丽</desc-list-item>
            <desc-list-item term="订购产品">XX 服务</desc-list-item>
            <desc-list-item term="创建时间">2017-07-07</desc-list-item>
            <desc-list-item term="关联单据"><a (click)="msg.success('yes')">12421</a></desc-list-item>
            <desc-list-item term="生效日期">2017-07-07 ~ 2017-08-08</desc-list-item>
            <desc-list-item term="备注">请于两个工作日内确认</desc-list-item>
        </desc-list>
    </ng-template>
    <ng-template #extra>
        <div nz-row>
            <div nz-col nzXs="24" nzSm="12">
                <p class="text-grey">状态</p>
                <p class="text-lg">待审批</p>
            </div>
            <div nz-col nzXs="24" nzSm="12">
                <p class="text-grey">订单金额</p>
                <p class="text-lg">¥ 568.08</p>
            </div>
        </div>
    </ng-template>
    <ng-template #tab>
        <nz-tabset [nzSize]="'default'">
            <nz-tab nzTitle="详情"></nz-tab>
            <nz-tab nzTitle="规则"></nz-tab>
        </nz-tabset>
    </ng-template>
</page-header>
    `
})
export class ComponentsPageHeaderStandardComponent {
}
```

## page-header 组件本身的模板

`/node_modules/@delon/abc/src/page-header/page-header.component.js`

```html
<ng-container *ngIf="!breadcrumb; else breadcrumb">
  <nz-breadcrumb *ngIf="paths && paths.length > 0">
    <nz-breadcrumb-item *ngFor="let i of paths">
      <ng-container *ngIf="i.link"><a [routerLink]="i.link">{{i.title}}</a></ng-container>
      <ng-container *ngIf="!i.link">{{i.title}}</ng-container>
    </nz-breadcrumb-item>
  </nz-breadcrumb>
</ng-container>
<div class="detail">
  <div *ngIf="logo" class="logo">
    <ng-template [ngTemplateOutlet]="logo"></ng-template>
  </div>
  <div class="main">
    <div class="row">
      <h1 *ngIf="title" class="title">{{title}}</h1>
      <div *ngIf="action" class="action">
        <ng-template [ngTemplateOutlet]="action"></ng-template>
      </div>
    </div>
    <div class="row">
      <div class="desc" (cdkObserveContent)="checkContent()" #conTpl>
        <ng-content></ng-content>
        <ng-template [ngTemplateOutlet]="content"></ng-template>
      </div>
      <div *ngIf="extra" class="extra">
        <ng-template [ngTemplateOutlet]="extra"></ng-template>
      </div>
    </div>
  </div>
</div>
<ng-template [ngTemplateOutlet]="tab"></ng-template>
```

```
  host: {
      '[class.content__title]': 'true',
      '[class.ad-page-header]': 'true',
  },
```



### angular4 新增的 *ngIf 语法

```
<element *ngIf="[condition expression]; else [else template]">
```

注意: else 后面是 模板本地变量, 该变量是设置在 另一段 ng-template 标签中,
表示当表达式为假时, 不生成当前标签, 但用另一段标签(模板本地变量 所在的 ng-template)替换

示例1:

```html
<ng-template #hidden>
 <p>You are not allowed to see our secret</p>
</ng-template>
<p *ngIf="shown; else hidden">
 Our secret is being happy
</p>
```

示例2:
```ts
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
 
@Component({
 selector: 'exe-app',
 template: `
 <ng-template #fetching>
 <p>Fetching...</p>
 </ng-template>
 <p *ngIf="auth | async; else fetching; let user">
 {{user.username }}
 </p>
 `,
})
export class AppComponent implements OnInit {
 auth: Observable<{}>;
 
 ngOnInit() {
 this.auth = Observable
 .of({ username: 'semlinker', password: 'segmentfault' })
 .delay(new Date(Date.now() + 2000));
 }
}
```

示例3

```html
<div *ngIf="buttonClicked">
      <div *ngIf="person$ | async as person; else personLoading">
        Name: {{ person.name }} <br />
        Twitter: {{ person.twitter }}
      </div>
      <ng-template #personLoading>
        Loading person...
      </ng-template>
    </div>
```


Angular4 中常用的指令入门总结

[http://www.jb51.net/article/115968.htm](http://www.jb51.net/article/115968.htm)

- NgNonBindable
- querySelector()
- 使用 [hidden] 属性控制元素可见性存在的问题
- @ViewChild() 设置返回对象的类型


### ng-container ng-template ngTemplateOutlet

ng-content 中隐藏的内容

[https://segmentfault.com/a/1190000010730597](https://segmentfault.com/a/1190000010730597)


## 自动生成 面包屑 的源码


```js
    PageHeaderComponent.prototype.genBreadcrumb = /**
     * @return {?}
     */
    function () {
        var _this = this;
        if (this.breadcrumb || !this.autoBreadcrumb || !this.menuSrv)
            return;
        var /** @type {?} */ menus = this.menuSrv.getPathByUrl(this.route.url);
        if (menus.length <= 0)
            return;
        var /** @type {?} */ paths = [];
        menus.forEach(function (item) {
            if (typeof item.hideInBreadcrumb !== 'undefined' && item.hideInBreadcrumb)
                return;
            var /** @type {?} */ title = item.text;
            if (item.i18n && _this.i18nSrv)
                title = _this.i18nSrv.fanyi(item.i18n);
            paths.push({ title: title, link: item.link && [item.link] });
        });
        // add home
        if (this.home) {
            paths.splice(0, 0, {
                title: (this.home_i18n &&
                    this.i18nSrv &&
                    this.i18nSrv.fanyi(this.home_i18n)) ||
                    this.home,
                link: [this.home_link],
            });
        }
        this.paths = paths;
    };
```
