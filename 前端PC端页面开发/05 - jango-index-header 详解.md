# 05 - jango-index-header 详解

## 滚动页面`.c-layout-header`的效果说明

### 刚发生滚动 `.c-layout-header` 可以透出轮播图背景的手法

- `.c-layout-header` 的背景设为透明
- 同时注意,由于bootstrap对部分nav元素有背景,也要清除 
- 起始状态 `.c-layout-header` 的背景为 body 的背景
- 因此刚发生滚动时,`.c-layout-header` 可以透出轮播图背景
- 滚动事件对`body`增加`.c-page-on-scroll`后
- 会重置`.c-layout-header`为白色(与`.c-layout-header-4`相关)
- `.c-layout-header-(x)`说明 见 [31 -  jango-header.md]
- 最后注意以上手法,仅对大屏有效

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
      //- 品牌区,在小屏模式下只显示品牌区
      .c-brand.c-pull-left
      //- 搜索表单,初始不显示
      form.c-quick-search(action="#")
      //- 导航主菜单,仅在大屏模式下显示
      nav.c-mega-menu.c-pull-right.c-mega-menu-dark
        .c-mega-menu-dark-mobile.c-fonts-bold
    //-快捷购物车弹出层(初始不显示)
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

components.css

```css

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


#### `.c-navbar-wrapper.clearfix`

对 `.c-navbar-wrapper` 需要使用 `.clearfix`的理由是什么?

思考题: 如果取消了`.clearfix`, 为什么 `.container` 仍有高度


#### .clearfix 的定义

```
.clearfix() {
  &:before,
  &:after {
    content: " ";
    display: table;
  }
  &:after {
    clear: both;
  }
}
```

#### bootstrap对以下类(不完全统计)自带 `.clearfix`

```
.container
.container-fluid
.row
.form-group
.btn-group
.nav
.navbar
.panel-body
.modal-footer
dd
```

#### 有关动画的处理

transition (过渡)

```
transition: <transition-property> || <transition-duration> || <transition-timing-function> || <transition-delay>

transition-property: 过渡属性(默认值为all)
transition-duration: 过渡持续时间(默认值为0s)
transiton-timing-function: 过渡函数(默认值为ease函数)
transition-delay: 过渡延迟时间(默认值为0s)
````

深入理解CSS过渡transition

[http://www.cnblogs.com/xiaohuochai/p/5347930.html](http://www.cnblogs.com/xiaohuochai/p/5347930.html)

```
ease: 开始和结束慢，中间快。相当于cubic-bezier(0.25,0.1,0.25,1)
linear: 匀速。相当于cubic-bezier(0,0,1,1)
ease-in: 开始慢。相当于cubic-bezier(0.42,0,1,1)
ease-out: 结束慢。相当于cubic-bezier(0,0,0.58,1)
ease-in-out: 和ease类似，但比ease幅度大。相当于cubic-bezier(0.42,0,0.58,1)
step-start: 直接位于结束处。相当于steps(1,start)
step-end: 位于开始处经过时间间隔后结束。相当于steps(1,end)
```

#### 动画的具体实例

`.c-brand`

```css
.c-layout-header .c-brand {
    margin: 40px 0 37px;
}
.c-layout-header .c-brand {
    display: inline-block;
    transition: all 0.2s ease 0s;
}

.c-page-on-scroll.c-layout-header-fixed .c-layout-header .c-brand {
    margin: 22px 0 19px;
    transition: margin 0.2s ease 0s;
}
```

菜单项

```css
@media (min-width: 992px)
.c-page-on-scroll.c-layout-header-fixed .c-layout-header .c-mega-menu .nav.navbar-nav > li > .c-link {
    transition: all 0.2s;
    padding: 23px 15px 21px 15px;
    min-height: 65px;
}

@media (min-width: 992px)
.c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li > .c-link {
    letter-spacing: 1px;
    font-style: normal;
    transition: all 0.2s;
    padding: 41px 15px 39px 15px;
    min-height: 100px;
    font-size: 17px;
}

```

### `.c-brand` 的结构

```jade
.c-brand.c-pull-left
  a.c-logo
    img.c-desktop-logo
    img.c-desktop-logo-inverse
    img.c-mobile-logo
  button.c-hor-nav-toggler
  button.c-topbar-toggler
  button.c-cart-toggler
```

- `.c-pull-left` 跟 `pull-left` 的区别

   `.c-pull-left` 本身并没有单独定义样式
   所以不能直接应用,应用前要检查是否有联合定义

   比如: `.c-brand.c-pull-left`

   这样做的目的,在特殊情况下,不一定通过`float:left`实现

:2255
```css
@media (min-width: 992px){}

.c-layout-header .c-brand {
  transition: all 0.2s;
  display: inline-block; }
  .c-layout-header .c-brand.c-pull-left {
    float: left; }
  .c-layout-header .c-brand.c-pull-right {
    float: right; }
```

- `img.c-desktop-logo` 大屏初始状态下所用图片   

- `img.c-desktop-logo-inverse` 大屏发生滚动后所用图片

- `img.c-mobile-logo` 小屏状态下所用图片

- 注意:三种状态下所用图片必须都定义

- 按钮在大屏状态不显示,仅在小屏状态显示

- 特别要注意的地方:

logo 图片与菜单居中,并没有做成自适应,而是通过固定的margin实现的

如 index.html 所使用的 logo-3.png

```css
.c-layout-header .c-brand {
    margin: 40px 0 37px;
}
.c-page-on-scroll.c-layout-header-fixed .c-layout-header .c-brand {
    margin: 22px 0 19px;
    transition: margin 0.2s ease 0s;
}
.c-layout-header .c-brand {
    display: block;
    float: none !important;
    margin: 20px 15px 21px;
}
```

因此当图片大小不同时,要在自定义的样式文件中,要覆盖定义

同时注意,部份 `c-layout-header-(x)` 也会定义,如

```css
@media (min-width: 992px)
.c-layout-header.c-layout-header-5 .c-navbar .c-brand {
    margin: 0;
}
```


### `nav.c-mega-menu`的结构

```jade
nav.c-mega-menu.c-pull-right.c-mega-menu-dark.c-mega-menu-dark-mobile
  ul.nav.navbar-nav.c-theme-nav
    li.c-active
      a.c-link.dropdown-toggle
        | 一级菜单
        //- 该小箭头仅在小屏显示,通过字体图标实现
        span.c-arrow.c-toggler        
      .dropdown-menu.c-menu-type-mega.c-menu-type-fullwidth(style="min-width:auto;")
```

- `nav.c-mega-menu` 相当于 bootstrap 导航条的顶级节点 `nav.navbar` 

- bootstrap 导航条也是自适应的

- 注意与bootstrap 导航条不同的是

    bootstrap 导航条 通过 子节点 `.navbar-header`
    实现小屏状态的菜单按钮,以及logo: `.navbar-brand`
    jango 将这部分职能改到 `.c-brand`处理

- 而导航菜单(即横向菜单)(导航条的下级节点),与bootstrap是一致的

  即: `ul.nav.navbar-nav.c-theme-nav`

- 高亮菜单使用 `.c-active`

- `a.c-link` 对非按钮类型的a标签,建议都使用 `a.c-link`

- `.c-mega-menu` 相当于 导航条,在小屏模式下不显示

- `.c-mega-menu-dark` 大屏状态下的弹出菜单背景

- `.c-mega-menu-dark-mobile` 小屏状态下的弹出菜单背景

- `ul.nav.navbar-nav` 相当于 导航菜单(横向菜单)

- 重温一下 bootstrap 的 `.dropdown-menu`

  + `.dropdown-menu` 的节点的 `data-toggle` 属性指明在哪个节点下弹出菜单,

   比如 `data-toggle='dropdown'`, 则以含有`.dropdown`类名的节点为准

  原因是 bootstrap 在弹出菜单前需要对 `.dropdown` 节点增加 `.open` 开关
  
  同时注意`.dropdown`节点的 `position: relative`


.c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li.c-menu-type-classic {
    position: relative;
}

- jango 对`.dropdown-menu` 不同的处理方式

  + 要对`.dropdown-menu` 附加定义 

    是使用 `.c-menu-type-mega` 模式还是 `.c-menu-type-classic`模式

  + 是通过`:hover`控制菜单弹出

  ```css
  .c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li:hover > .dropdown-menu {
    display: block;
  }
  ```

  + `.c-menu-type-mega` 模式的绝对定位依据不是父级li节点,而是`.container`节点
  
  ```css
    .c-layout-header .c-navbar > .container {
        position: relative;
    }
  ```

  + 使用`.c-menu-type-classic`模式的`.dropdown-menu`,还需要对父级节点也
    应用`.c-menu-type-classic`,目的是获取定位依据

    ```css
    .c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li.c-menu-type-classic {
    position: relative;
    }
    ```
  + 对于a标签不需要定义 `data-toggle`属性,但要应用 `.dropdown-toggle`

    作用是不一样,jango主要用于控制小屏状态下的a标签 显示小箭头.


- `.dropdown-menu.c-menu-type-mega` 表示当前的.dropdown-menu是
   巨型弹出菜单,直接将子级.dropdown-menu显示出来
  
   注意:这种形式只能处理三级菜单(从横向导航菜单起算)

```css
.c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li > .dropdown-menu.c-menu-type-mega .dropdown-menu {
    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
    box-shadow: none !important;
    display: block;
    float: none;
    position: static;
}
```

- `.c-menu-type-fullwidth`

```css
.c-layout-header .c-navbar .c-mega-menu > .nav.navbar-nav > li > .dropdown-menu.c-menu-type-fullwidth {
    left: 0;
    right: 0;
    width: 100%;
}
```

#### `.dropdown-menu.c-menu-type-mega` 扁平式弹出菜单的下级布局
  
  方法一: 通过栅格系统进行分隔,可以自由控制栅格比例

  注意: 扁平式弹出菜单的顶级节点是`div` ,不是`ul`

  ```jdae
  .dropdown-menu.c-menu-type-mega.c-menu-type-fullwidth
    .row
      .col-md-3
        ul.dropdown-menu.c-menu-type-inline
        ...
      .col-md-3
        ul.dropdown-menu.c-menu-type-inline        
  ```

  方法二: 顶级节点为ul,每个下级菜单为li,自适应宽度布局

  注意: 扁平式弹出菜单的顶级节点是`ul`

  ```jade
    ul.dropdown-menu.c-menu-type-mega.c-menu-type-fullwidth
      li
        ul.dropdown-menu.c-menu-type-inline      
          ...
      li
        ul.dropdown-menu.c-menu-type-inline      
  ```

#### 在`nav.c-mega-menu` 下使用传统下拉菜单

- 在横向菜单的 `li` 节点要使用 `.c-menu-type-classic`
- 在弹出菜单的 `ul` 节点要使用 `.dropdown-menu.c-menu-type-classic`
- 在弹出菜单的菜单项又要弹出下一级菜单时, 菜单项`li`节点要使用 `.dropdown-submenu`


```jade
  li.c-menu-type-classic
    a.c-link.dropdown-toggle(href="javascript:;")
      | 传统菜单
      //- 该小箭头仅在小屏显示,通过字体图标实现
      span.c-arrow.c-toggler
    ul.dropdown-menu.c-menu-type-classic.c-pull-left
      li.dropdown-submenu
        a(href="javascript:;")
          | 菜单1
          span.c-arrow.c-toggler
        ul.dropdown-menu.c-pull-right
```

#### 扁平式弹出菜单需要显示二级以上菜单的处理方式(仅大屏需要处理)

扁平式弹出菜单只是一个内容块,内部可任意布局

示例为通过 `使用bootstrap的 导航页签` 增加显示菜单的层级

```jade
  .dropdown-menu.c-menu-type-mega.c-menu-type-fullwidth.c-visible-desktop.c-pull-right
    //- 使用bootstrap的 导航页签
    ul.nav.nav-tabs.c-theme-nav
      li.active
        //- 导航页签触发页签内容显示,是通过href指向id(锚点)
        a(href="#navtab_1", data-toggle="tab") 菜单页1
      li
        a(href="#navtab_2", data-toggle="tab") 菜单页2
    .tab-content
      #navtab_1.tab-pane.active
        .row
          .col-md-4
            ul.dropdown-menu.c-menu-type-inline
              ...
          .col-md-4
            ul.dropdown-menu.c-menu-type-inline
      #navtab_2.tab-pane
        .row
          .col-md-4
            ul.dropdown-menu.c-menu-type-inline
              ...
```

#### 扁平式弹出菜单 根据大屏小屏采用不同的模式

`.c-visible-desktop` : 弹出菜单仅在大屏模式下显示

`.c-visible-mobile` : 弹出菜单仅在小屏模式下显示

#### 购物车显示的技巧讲解


#### 边栏显示的技巧讲解

