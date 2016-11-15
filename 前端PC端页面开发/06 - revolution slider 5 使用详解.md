# 06 - revolution slider 5 使用详解

### 必须用到的文件

```html
<!-- RS5.0 Main Stylesheet -->
<link rel="stylesheet" type="text/css" href="revolution/css/settings.css">
 
<!-- RS5.0 Layers and Navigation Styles -->
<link rel="stylesheet" type="text/css" href="revolution/css/layers.css">
<link rel="stylesheet" type="text/css" href="revolution/css/navigation.css"> 
 
<!-- RS5.0 Core JS Files -->
<script type="text/javascript" src="revolution/js/jquery.themepunch.tools.min.js?rev=5.0"></script>
<script type="text/javascript" src="revolution/js/jquery.themepunch.revolution.min.js?rev=5.0"></script>
```

### 页面的基本结构形式

```html
<!-- START REVOLUTION SLIDER 5.0 -->
<div class="rev_slider_wrapper">
 <div id="slider1" class="rev_slider"  data-version="5.0">
  <ul> 
   <li data-transition="fade"> 
     <!-- MAIN IMAGE -->
     <img src="images/slide1.jpg"  alt=""  width="1920" height="1280"> 
     <!-- LAYER NR. 1 -->
     <div class="tp-caption News-Title" 
        data-x="left" data-hoffset="80" 
        data-y="top" data-voffset="450" 
        data-whitespace="normal"
        data-transform_idle="o:1;" 
        data-transform_in="o:0" 
        data-transform_out="o:0" 
        data-start="500">DISCOVER THE WILD</div>
   </li>
 
   <li data-transition="fade"> 
     <!-- MAIN IMAGE -->
     <img src="images/slide1.jpg"  alt=""  width="1920" height="1280"> 
     <!-- LAYER NR. 1 -->
     <div class="tp-caption News-Title" 
        data-x="left" data-hoffset="80" 
        data-y="top" data-voffset="450" 
        data-whitespace="normal"
        data-transform_idle="o:1;" 
        data-transform_in="o:0" 
        data-transform_out="o:0" 
        data-start="500">DISCOVER THE WILD</div>
   </li>
  </ul> 
 </div><!-- END REVOLUTION SLIDER -->
</div><!-- END OF SLIDER WRAPPER -->
```


- rev_slider_wrapper: 这一层是自定义的,类名可自由确定,可以在这一层控制slider的宽度
- rev_slider: 这一层的类名是规定要使用的. slider的根节点,js初始化是要引用该节点
- ul: slider的容器节点,必须使用ul
- li: slider的叶节点

### 初始化代码

要初始化后,才能使用:

```js
jQuery(document).ready(function() { 
   jQuery("#slider1").revolution({
      sliderType:"standard",
      sliderLayout:"auto",
      delay:9000,
      navigation: {
          arrows:{enable:true} 
      }, 
      gridwidth:1230,
      gridheight:720 
    }); 
}); 
```

或为了保证总是显示:

```js
jQuery("#slider1").show().revolution({})
```


### 对象的层次结构

```
Slides(*)
  Main Images(*)
  Layers
    Text / HTML Markups
    Button
    Shapes
    Videos
    Audio
    Images
Static Layers
  Text / HTML Markups
  Button
  Shapes
  Videos
  Audio
  Images
Navigation  
  Arrows
  Bullets
  Tabs
  Thumbnails
Progress Bar
```

说明:

- Slides 对象必须存在
- Main Images  对象必须存在




