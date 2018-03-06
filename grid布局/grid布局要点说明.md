# grid布局要点说明.md

## 阅读

英文完整说明

[https://www.w3.org/TR/css-grid-1/](https://www.w3.org/TR/css-grid-1/)

部分中文翻译

[https://www.w3cplus.com/css3/css3-grid-layout.html](https://www.w3cplus.com/css3/css3-grid-layout.html)

[https://juejin.im/entry/59af77ac51882538cb1ec542?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com](https://juejin.im/entry/59af77ac51882538cb1ec542?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com)

[https://juejin.im/post/599970f4518825243a78b9d5?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com](https://juejin.im/post/599970f4518825243a78b9d5?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com)

非常好的介绍

[http://chris.house/blog/a-complete-guide-css-grid-layout/?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com](http://chris.house/blog/a-complete-guide-css-grid-layout/?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com)

属性值列表

[http://vanseodesign.com/css/grid-layout-module/](http://vanseodesign.com/css/grid-layout-module/)
## 使用指南

### 启用grid布局

父容器:

```
 display: grid;
```

### 父容器可用属性

### 子项可用属性

合并单元格
```
  grid-row: span 2;
  grid-column: span 2;
```

#### 定义列与行

grid-template-columns
grid-template-rows

```css
.container{
  grid-template-columns: <track-size> ... | <line-name> <track-size> ...;
  grid-template-rows: <track-size> ... | <line-name> <track-size> ...;
}
```

示例:

```css
.container{
  grid-template-columns: 40px 50px auto 50px 40px;
  grid-template-rows: 25% 100px auto;
}
```

示例: 给每条线都取了别名

```css
.container{
  grid-template-columns: [first] 40px [line2] 50px [line3] auto [col4-start] 50px [five] 40px [end];
  grid-template-rows: [row1-start] 25% [row1-end] 100px [third-line] auto [last-line];
}
```

```
.container{
  grid-template-rows: [row1-start] 25% [row1-end row2-start] 25% [row2-end];
}
```


### 定义网格

#### 只指定列

如果没有指定行, 则子元素超过列数时, 会指定增加一行,继续处理

父容器:

```
 grid-template-columns: 1fr 1fr 1fr 1fr; 
```

效果为四列平均分布


fr 分配剩余空间的比率单位

> grid-template-columns : 可理解为列数,有4列, 
> 但最好理解为有5根纵向的网线,组成4条纵向的网轨
> 


```
 grid-template-columns: auto auto auto auto; 
```

> 使用 auto ,表示初始空间为内容宽度, 但由于最后还有剩余空间,因此会伸展
> 最后结果仍为四列平均分布

```
 grid-template-columns: auto auto auto 1fr; 
```

> 前3列为内容宽度, 第4列为占据所有剩余空间


```
grid-template-columns: 2fr 4fr 3fr 1fr;
```

> 按比率占据空间, 跟flex 的伸展属性类似

```
  grid-template-columns: repeat(auto-fill, minmax(200px,1fr));
```

> 自动创建能建立的最大网轨数



### 可用函数

#### minmax

minmax() 函数来创建行或列的最小或最大尺寸，第一个参数定义网格轨道的最小值，第二个参数定义网格轨道的最大值。可以接受任何长度值，也接受 auto 值。auto 值允许网格轨道基于内容的尺寸拉伸或挤压。 


#### repeat

repeat() 属性可以创建重复的网格轨道。这个适用于创建相等尺寸的网格项目和多个网格项目。

repeat() 也接受两个参数：第一个参数定义网格轨道应该重复的次数，第二个参数定义每个轨道的尺寸。

### 定义网格间距


父容器:

```
  grid-gap: 1rem;
```

## 一些特殊的案例

### 将任意子元素显示到指定的网格

```html
<ul>
  <li>One</li>
  <li>Two</li>
  <li>Three</li>
  <li>Four</li>
  <li>Five</li>
  <li>Six</li>
  <li>Seven</li>
  <li>Eight</li>
  <li>Nine</li>
  <li>Ten</li>
  <li>Eleven</li>
  <li>Twelve</li>
  <li>Thirteen</li>
</ul>
```

```css
// Now let's think about the items.
// Write code to target items. Place the black boxes in the first column, and place the blue boxes  in the second column. Do so in such a way that we can see all of the boxes (none are hidden behind another).

ul {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px,1fr));
  grid-gap: 1rem;
}

li:nth-child(2) {
  grid-row: 1 / 2;
  grid-column: 1 / 2;
}
li:nth-child(6) {
  grid-row: 2 / 3;
  grid-column: 1 / 2;
}
li:nth-child(8) {
  grid-row: 1 / 2;
  grid-column: 2 / 3;
}
li:nth-child(10) {
  grid-row: 2 / 3;
  grid-column: 2 / 3;
}




//--------------------------------------
// Additional Visual Styling
body {
  background: #F6F3EA
}
ul {
  margin: 0;
  padding: 0;
  list-style: none;
  color: white;
  font-family: Avenir, Heveltica, Arial, san-serif;
  font-weight: bold;
  font-size: 1.5rem;
}
li {
  padding: 1em;
  background: #F9423A;
}
li:nth-child(2),
li:nth-child(6){
  background: #444;
}
li:nth-child(8),
li:nth-child(10){
  background: #4794B8;
}
```

### 定义单元区域重叠

```html
<ul>
  <li><img src="http://labs.jensimmons.com/2017/media/01-001/brooklynmuseum-o1085i000-52.166.5.jpg" alt=""></li>
  <li><img src="http://labs.jensimmons.com/2017/media/01-001/380485.jpg" alt=""></li>
  <li><img src="http://labs.jensimmons.com/2017/media/01-001/brooklynmuseum-o4266i000-86.226.18_SL1.jpg" alt=""></li>
  <li><img src="http://labs.jensimmons.com/2017/media/01-001/brooklynmuseum-o44489i000-35.867_reference_SL1.jpg" alt=""></li>
  <li><img src="http://labs.jensimmons.com/2017/media/01-001/436667.jpg" alt=""></li>
</ul>
```

```css
// Now make many of these paintings overlap. Make them span multiple cells.
// If you'd like, make more columns. Feel free to place things in column 6 or 7 or more, even without defining more columns explictly. See what happens? The browser makes more grid for you.

ul {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-auto-rows: 1fr;
  grid-gap: 1rem;
}
li:nth-child(1) {
  grid-row: 1 / 3;
  grid-column: 1 / 3;
}
li:nth-child(2) {
  grid-row: 2 / 5;
  grid-column: 2 / 5;
}
li:nth-child(3) {
  grid-row: 1 / 3;
  grid-column: 4 / 6;
}
li:nth-child(4) {
  grid-row: 3 / 5;
  grid-column: 1 / 3;
}
li:nth-child(5) {
  grid-row: 4 / 7;
  grid-column: 3 / 6;
}
li {
 opacity: 0.75;
}



//--------------------------------------
// Additional Visual Styling
* {box-sizing:border-box;}
body {
 background: #f6f3ea;
}
img {
  display: block;
  width: 100%;
}
ul {
  list-style: none;
  margin: 0;
  padding: 2.2rem;
}
```