# 04 - jango 基本布局结构

## 布局形式

基本为 上(固定高)下(充满-自适应)布局

## 页面整体结构

```jade

	body.c-layout-header-fixed.c-layout-header-mobile-fixed
		header.c-layout-header.c-layout-header-4.c-layout-header-default-mobile(		data-minimize-offset="80")
		.c-layout-page
		.c-layout-footer.c-layout-footer-6.c-bg-grey-1
```

## body

body 主要控制 header 与 .c-layout-page 的关系

`.c-layout-header-fixed` 控制大屏模式下 `.c-layout-header` 使用固定定位

`c-layout-header-mobile-fixed` 控制小屏模式下 `.c-layout-header` 使用固定定位

如果使用了固定定位,则需要对 `.c-layout-page` 设置 margin-top = header的高度

同时还要考虑 `.c-layout-header` 是否有 `.c-topbar` 的情况,

因此`.c-layout-header` 的高度有两种情况

主要大多数说明均以大屏模式为准:

```css
@media (min-width: 992px) {}
```

```css
  .c-layout-header-fullscreen.c-layout-header-static .c-layout-header,
  .c-layout-header-fixed .c-layout-header {
    top: 0;
    position: fixed;
    z-index: 9995;
    width: 100%; }
  .c-layout-header-fixed .c-layout-page {
    margin-top: 100px; }
  .c-layout-header-fixed.c-layout-header-topbar .c-layout-page {
    margin-top: 145px; }
```

## 有关产生滚动后效果的说明

### .c-page-on-scroll

当页面产生一定量的滚动, `body` 会增加 `.c-page-on-scroll`

滚动量由 `body` 的 `data-minimize-offset="80"` 指定

代码:

`/libraries/jango/assets/js/components.js`

:63

```js
// BEGIN: Layout Header
var LayoutHeader = function () {
	var offset = parseInt($('.c-layout-header').attr('data-minimize-offset') > 0 ? parseInt($('.c-layout-header').attr('data-minimize-offset')) : 0);
	var _handleHeaderOnScroll = function () {
		if ($(window).scrollTop() > offset) {
			$("body").addClass("c-page-on-scroll");
		} else {
			$("body").removeClass("c-page-on-scroll");
		}
	}

	var _handleTopbarCollapse = function () {
		$('.c-layout-header .c-topbar-toggler').on('click', function (e) {
			$('.c-layout-header-topbar-collapse').toggleClass("c-topbar-expanded");
		});
	}

	return {
		//main function to initiate the module
		init: function () {
			if ($('body').hasClass('c-layout-header-fixed-non-minimized')) {
				return;
			}

			_handleHeaderOnScroll();
			_handleTopbarCollapse();

			$(window).scroll(function () {
				_handleHeaderOnScroll();
			});
		}
	};
}();
// END

```

:685

```js
LayoutHeader.init();
```

### .c-layout-header的表现

- 如果 body 定义 `.c-layout-header-fixed`

	则 `.c-layout-header` 会调整高度,字体样式微调

- 如果 body 定义 `.c-layout-header-static`

	则 `.c-layout-header` 会隐藏

	注意不是改变 `.c-layout-header` 的 `position: fixed;`
	
```css
  .c-page-on-scroll.c-layout-header-static .c-layout-header {
    display: none; }
  .c-page-on-scroll.c-layout-header-fixed .c-layout-header {
    height: 65px;
    line-height: 0px; }
```
