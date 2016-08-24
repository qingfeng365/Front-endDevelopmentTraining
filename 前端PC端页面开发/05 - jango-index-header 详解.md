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



