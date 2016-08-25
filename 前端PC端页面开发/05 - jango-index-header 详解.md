# 05 - jango-index-header 详解

## 滚动页面`.c-layout-header`的效果说明

### 刚发生滚动 `.c-layout-header` 可以透出轮播图背景的手法

- `.c-layout-header` 的背景设为透明
- 起始状态 `.c-layout-header` 的背景为 body 的背景
- 因此刚发生滚动时,`.c-layout-header` 可以透出轮播图背景
- 滚动事件对`body`增加`.c-page-on-scroll`后
- 会重置`.c-layout-header`为白色(与`.c-layout-header-4`相关)
- `.c-layout-header-(x)`说明 见 [31 -  jango-header.md]


`components.css`

:3504

```css
  .c-layout-header.c-layout-header-4 {
    background: none;
    border-bottom: 0; }
```

:3545

```css
  .c-page-on-scroll .c-layout-header.c-layout-header-4 {
    background: #ffffff;
    border-bottom: 1px solid #e6eff3;
    box-shadow: none; }
```

## .c-layout-header 的布局结构

### 扩展定义

`.c-layout-header-4` 指定大屏的特殊样式类型

`.c-layout-header-default-mobile` 指定小屏的样式类型

```jade
	header.c-layout-header.c-layout-header-4.c-layout-header-default-mobile(data-minimize-offset="80")
```

即: 当使用 `.c-layout-header` 时,还需要指定大屏和小屏分别使用哪种样式

`.c-layout-header-(x)` 的说明见 [31 -  jango-header.md]

### .c-layout-header内部结构

```jade
	header.c-layout-header
	  .c-topbar
	  .c-navbar
```

`.c-navbar` 是主导航栏,是必有的 
`.c-topbar` 是主导航栏上方的附加的工具条,是可选的.(index.html 没有使用)


> 注意:
> 当使用 `.c-topbar` 时, 在body还需要使用 `c-layout-header-(x)-topbar`
> 主要是因为 `.c-topbar` 会使`.c-layout-header`需要使用不同的高度

### .c-navbar的布局结构

```jade
.c-navbar
	.container
    .c-navbar-wrapper.clearfix
      .c-brand.c-pull-left
      form.c-quick-search(action="#")
      nav.c-mega-menu.c-pull-right.c-mega-menu-dark.c-mega-menu-dark-mobile.c-fonts-bold
    .c-cart-menu
```

#### `.container` 还可以选择 `.container-fluid`

`.container` 特性: 定宽水平居中

- 主要用于控制一级内容的垂直基线
- 即保证在一级内容的容器节点分别使用`.container`,内容的垂直基线是一致的
- 不要设置背景,背景应由 `.container` 上级容器处理
- 水平居中特性是用 `margin:auto` 实现,
- 因此上级容器宽度大于`.container` 才会生效
- 不应该在宽度小于`.container`的容器内使用

`.container-fluid` 特性: 非定宽充满

- 虽然设置了`margin:auto`,但因为没有定宽,因此不会有居中效果
- 只是因为充满,通过设置`padding`,形成居中效果

`bootstrap.css`

```css
@media (min-width: 992px)
.container {
    width: 970px;
}
@media (min-width: 768px)
.container {
    width: 750px;
}
.container {
    margin-right: auto;
    margin-left: auto;
    padding-left: 15px;
    padding-right: 15px;
}

.container-fluid {
    margin-right: auto;
    margin-left: auto;
    padding-left: 15px;
    padding-right: 15px;
}

```

`components.css`

```

@media (min-width: 992px)
.c-layout-header .c-navbar > .container {
    position: relative;
}
@media (min-width: 1200px)
.container {
    width: 1170px;
}
@media (min-width: 992px)
.container {
    width: 970px;
}
@media (min-width: 768px)
.container {
    width: 750px;
}

.container {
    margin-right: auto;
    margin-left: auto;
    padding-left: 15px;
    padding-right: 15px;
}

@media (min-width: 992px)
.c-layout-header .c-navbar > .container-fluid {
    position: relative;
    padding: 0 100px;
}
@media (min-width: 992px)
.container-fluid {
    padding: 0 100px;
}
.container-fluid {
    margin-right: auto;
    margin-left: auto;
    padding-left: 15px;
    padding-right: 15px;
}

```

`.container` 在header的用途

- 下拉菜单的定位依据
- 大屏模式下 `.c-brand` 的位置

#### 结构

`.c-navbar-wrapper` : 主导航栏的包裹层
  
    `.c-brand` : 品牌区,在小屏模式下只显示品牌区

    `form.c-quick-search` : 搜索表单,初始不显示

    `nav.c-mega-menu` : 导航主菜单,仅在大屏模式下显示

`.c-cart-menu` : 快捷购物车弹出层(初始不显示)

