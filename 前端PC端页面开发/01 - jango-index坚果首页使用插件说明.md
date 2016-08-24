# jango-index坚果首页

## 重要提示

由于 jquery 最新版本为 3.1.0

如果将 jquery 升级到 3.1.0 时, 要同时升级 bootstrap 到 3.3.7 以上.


## 首页使用的第三方插件

### css

#### socicon.css (可不使用)

提供国外常用的 社交媒体 小图标 

[http://www.socicon.com/chart.php](http://www.socicon.com/chart.php)


#### bootstrap-social.css (可不使用)

提供国外常用的 社交媒体 图标的按钮样式

#### font-awesome.min.css 

常用字体小图标

`/echart-demo/client/stylesheets/font-awesome`

#### simple-line-icons.min.css

常用字体小图标 (简笔画风格)

`bower install simple-line-icons --save`

#### animate.min.css

css3 常见动画样式

[https://github.com/daneden/animate.css](https://github.com/daneden/animate.css)

`bower install animate.css --save`

#### bootstrap.min.css

bootstrap

#### revo-slider

```
revo-slider/css/settings.css
revo-slider/css/layers.css
revo-slider/css/navigation.css
```


轮播图插件 Revolution Slider 

见 `轮播图插件 - Revolution Slider` 小节说明


#### cubeportfolio.min.css

图片墙展示插件-魔方布局插件

见 `Cube Portfolio-魔方布局插件` 小节说明

`/echart-demo/client/libraries/cubeportfolio`

#### owl.carousel

旋转木马轮播插件

```
owl-carousel/owl.carousel.css
owl-carousel/owl.theme.css
owl-carousel/owl.transitions.css
```

见 `owl.carousel-旋转木马轮播插件` 小节说明

安装 1.3.2 版本

`bower install owl-carousel`

安装 2.1.6 版本

`bower install owl.carousel --save`

#### jquery.fancybox.css

弹框效果插件

常用于图片展示(弹出图片大图)

见 `jquery.fancybox` 小节说明


`bower install fancybox --save`

#### slider-for-bootstrap/css/slider.css

滑块输入插件

见 `bootstrap-slider-滑块输入插件` 小节说明

`bower install bootstrap-slider --save`

#### assets/base/css/plugins.css

jango 针对 bootstrap 缺省样式的 强化

`/echart-demo/client/libraries/jango/assets/css/plugins.css`

#### assets/base/css/components.css

jango 设计的一套 前端ui样式和辅助类

`/echart-demo/client/libraries/jango/assets/css/components.css`

#### assets/base/css/themes/default.css

jango 设计的一套默认主题

`/echart-demo/client/libraries/jango/assets/css/themes/default.css`

### js-核心插件

#### jquery.min.js

jango 使用的是 1.11.1 版本

最新版本为 3.1.0

`bower install jquery --save`

#### jquery-migrate.min.js

jQuery 应用迁移辅助插件, 使用1.9以上版本的jquery,需要兼容低版jquery代码时用

`bower install jquery-migrate --save`

#### bootstrap.min.js

jango 使用的是 3.3.5 版本

`bower install bootstrap --save`


#### jquery.easing.min.js

jquery动画增强插件

文档:(很慢)

[http://gsgd.co.uk/sandbox/jquery/easing/](http://gsgd.co.uk/sandbox/jquery/easing/)

github版本:

`bower install jquery.easing`

github另一个较新的版本

仅提供 npm 安装

[https://github.com/gdsmith/jquery.easing](https://github.com/gdsmith/jquery.easing)

`cnpm install jquery.easing --save`

该版本有示例

#### wow.js

页面滚屏触发动画插件

见 `wowjs-页面滚屏触发动画插件` 小节

`bower install wowjs --save`

#### assets/base/js/scripts/reveal-animate/reveal-animate.js

jango 初始化 wowjs 代码, 代码有疑问.


### js-布局插件

#### revo-slider

轮播图插件

相关css

```
revo-slider/css/settings.css
revo-slider/css/layers.css
revo-slider/css/navigation.css
```

相关js

```
revo-slider/js/jquery.themepunch.tools.min.js
revo-slider/js/jquery.themepunch.revolution.min.js
revo-slider/js/extensions/revolution.extension.slideanims.min.js
revo-slider/js/extensions/revolution.extension.layeranimation.min.js
revo-slider/js/extensions/revolution.extension.navigation.min.js
revo-slider/js/extensions/revolution.extension.video.min.js
```

见 `轮播图插件 - Revolution Slider` 小节说明

#### jquery.cubeportfolio.min.js

相关css

```
cubeportfolio.min.css
```

相关js

```
/cubeportfolio/js/jquery.cubeportfolio.min.js
```

见 `Cube Portfolio-魔方布局插件` 小节说明

`/echart-demo/client/libraries/cubeportfolio`

#### owl.carousel.min.js


相关css

```
owl-carousel/owl.carousel.css
owl-carousel/owl.theme.css
owl-carousel/owl.transitions.css
```

相关js

```
/owl-carousel/owl.carousel.min.js
```

见 `owl.carousel-旋转木马轮播插件` 小节说明


安装 1.3.2 版本

`bower install owl-carousel`

安装 2.1.6 版本

`bower install owl.carousel --save`

#### jquery.counterup.min.js

计数动画插件

见 `jquery.counterup-计数动画插件` 小节说明

`bower install jquery.counterup --save`

#### jquery.waypoints.min.js

滚动监听插件

不需要单独安装, jquery.counterup 会依赖安装

见 `jquery.waypoints-滚动监听插件` 小节说明

#### jquery.fancybox.pack.js

弹框效果插件

相关css

```
jquery.fancybox.css
```

相关js

```
jquery.fancybox.pack.js
```

弹框效果插件

见 `jquery.fancybox` 小节说明

`bower install fancybox --save`

#### bootstrap-slider.js

滑块输入插件

相关css

```
slider-for-bootstrap/css/slider.css
```

相关js

```
bootstrap-slider.js
```

见 `bootstrap-slider-滑块输入插件` 小节说明

`bower install bootstrap-slider --save`

#### assets/base/js/components.js

jango 对ui控制的代码

#### assets/base/js/components-shop.js

jango 对商城ui控制的代码

#### assets/base/js/app.js

jango 初始化页面代码


## 插件详解

### 轮播图插件 - Revolution Slider 

即yourkeyWebSite项目的

```
/yourkeyWebSite/client/libraries/slider-revolution-slider
```

但 yourkeyWebSite项目 使用的是 4.6.4 版本

jango 使用的是 5.1.6 版本


4.6.4 版本目录: 
`/echart-demo/client/libraries/slider-revolution-slider`

5.1.6 版本目录:
`/echart-demo/client/libraries/revolution-slider-516`


官网:

[https://www.themepunch.com/portfolio/slider-revolution-jquery-plugin/](https://www.themepunch.com/portfolio/slider-revolution-jquery-plugin/)

帮助文档:

[https://www.themepunch.com/revsliderjquery-doc/slider-revolution-jquery-5-x-documentation/](https://www.themepunch.com/revsliderjquery-doc/slider-revolution-jquery-5-x-documentation/)

api文档:

[https://www.themepunch.com/revsliderjquery-doc/api/](https://www.themepunch.com/revsliderjquery-doc/api/)


angular指令: (没什么用,留做参考)

https://github.com/Karnith/angular-revolution

### Cube Portfolio-魔方布局插件

非免费版本: 图片墙展示插件-魔方布局插件

jango 使用的是 2.3.2 版本 

`/echart-demo/client/libraries/cubeportfolio`

[http://cbpw.scriptpie.com/](http://cbpw.scriptpie.com/)

api文档:

[http://www.denondj.com/assets/premium/cubeportfolio/documentation/index.html#](http://www.denondj.com/assets/premium/cubeportfolio/documentation/index.html#)

### owl-carousel-旋转木马轮播插件

jango 使用的是 1.3.2 版本

最新版本为 2.1.6

api文档

[https://owlcarousel2.github.io/OwlCarousel2/](https://owlcarousel2.github.io/OwlCarousel2/)

安装 2.1.6 版本

`bower install owl.carousel --save`

### jquery.fancybox

`bower install fancybox --save`

官网

[http://www.fancyapps.com/fancybox/](http://www.fancyapps.com/fancybox/)

github

[https://github.com/fancyapps/fancyBox](https://github.com/fancyapps/fancyBox)

jQuery Fancybox插件使用参数详解
[http://my.oschina.net/xinger/blog/223997?p=1](http://my.oschina.net/xinger/blog/223997?p=1)


### bootstrap-slider-滑块输入插件

`bower install bootstrap-slider --save`

api文档

[http://www.eyecon.ro/bootstrap-slider/](http://www.eyecon.ro/bootstrap-slider/)

### wowjs-页面滚屏触发动画插件

`bower install wowjs --save`

官网:

[http://mynameismatthieu.com/WOW/](http://mynameismatthieu.com/WOW/)

github:

[https://github.com/matthieua/WOW](https://github.com/matthieua/WOW)

### jquery.counterup-计数动画插件

`bower install jquery.counterup --save`

演示页面

[http://ciromattia.github.io/jquery.counterup/demo/index.html](http://ciromattia.github.io/jquery.counterup/demo/index.html)

原作者:

[http://bfintal.github.io/Counter-Up/demo/demo.html](http://bfintal.github.io/Counter-Up/demo/demo.html)

github

[https://github.com/ciromattia/jquery.counterup](https://github.com/ciromattia/jquery.counterup)

原作者:

[https://github.com/bfintal/Counter-Up](https://github.com/bfintal/Counter-Up)

### jquery.waypoints-滚动监听插件

`bower install waypoints --save`

如果已安装 `jquery.counterup` ,会同时安装 `waypoints`


官网:

[http://imakewebthings.com/waypoints/](http://imakewebthings.com/waypoints/)

github:

[https://github.com/imakewebthings/waypoints](https://github.com/imakewebthings/waypoints)



