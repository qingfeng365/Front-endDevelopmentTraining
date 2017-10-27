# flex 值说明

## 父容器属性

父容器上有六个属性

    flex-direction：主轴的方向。
    flex-wrap：超出父容器子容器的排列样式。
    flex-flow：flex-direction 属性和 flex-wrap 属性的简写形式。
    justify-content：子容器在主轴的排列方向。
    align-items：子容器在交叉轴的排列方向。
    align-content：多根轴线的对齐方式。

### flex-direction 属性

flex-direction 属性决定主轴的方向（主轴的方向不一定是水平的，这个属性就是设置主轴的方向，主轴默认是水平方向，从左至右，如果主轴方向设置完毕，那么交叉轴就不需要设置，交叉轴永远是主轴顺时针旋转 90°）。

```css
.ele {
  flex-direction: row;                // 默认值，主轴为水平方向，起点在左端。
  flex-direction: row-reverse;        // 主轴为水平方向，起点在右端。
  flex-direction: column;             // 主轴为垂直方向，起点在上。
  flex-direction: column-reverse;     // 主轴为垂直方向，起点在下。
}
```

### flex-wrap 属性

flex-wrap 属性决定子容器如果在一条轴线排不下时，如何换行。

```css
.ele {
 flex-wrap: nowrap;          // 默认，不换行
 flex-wrap: wrap;            // 换行，第一行在上方。
 flex-wrap: wrap-reverse     // 换行，第一行在下方。
}
```

### justify-content 属性

justify-content 属性定义了子容器在主轴上的对齐方式。

```css
.ele{
    justify-content: flex-start;      // 默认，左对齐
    justify-content: flex-end;        // 右对齐
    justify-content: center;          // 居中
    justify-content: space-between;   // 两端对齐，项目之间的间隔都相等。
    justify-content: space-around;    // 每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。

}
```

### flex-flow 属性

flex-flow 属性是 flex-direction 属性和 flex-wrap 属性的简写形式，默认值为 row nowrap。

```css
.ele {
  flex-flow: <flex-direction> || <flex-wrap>;
}
```

### align-items 属性

align-items属性定义子容器在交叉轴上如何对齐。
具体的对齐方式与交叉轴的方向有关，下面假设交叉轴从上到下。

```css
.ele{
    align-items: flex-start;    // 交叉轴的起点对齐。
    align-items: flex-end;      // 交叉轴的终点对齐。
    align-items: center;        // 交叉轴的中点对齐。
    align-items: baseline;      // 项目的第一行文字的基线对齐。
    align-items: stretch;       // 默认，如果项目未设置高度或设为auto，将占满整个容器的高度。
}
```


### align-content 属性

align-content 属性定义了多根轴线的对齐方式。如果项目只有一根轴线，该属性不起作用。

```css
.ele{
    align-content: flex-start;   // 与交叉轴的起点对齐
    align-content; flex-end;     // 与交叉轴的终点对齐。
    align-content: center;       // 与交叉轴的中点对齐。
    align-content: space-between;// 与交叉轴两端对齐，轴线之间的间隔平均分布。
    align-content: space-around; // 每根轴线两侧的间隔都相等。所以，轴线之间的间隔比轴线与边框的间隔大一倍。
    align-content: stretch;     // 默认 轴线占满整个交叉轴。
}
```


## 子容器属性 

子容器也有 6 个属性：

    order：子容器的排列顺序
    flex-grow：子容器剩余空间的拉伸比例
    flex-shrink：子容器超出空间的压缩比例
    flex-basis：子容器在不伸缩情况下的原始尺寸
    flex：子元素的 flex 属性是 flex-grow,flex-shrink 和 flex-basis 的简写
    align-self

### order 属性

order 属性定义项目的排列顺序。数值越小，排列越靠前，默认为 0。

```css
.ele{
   order: num; 
}
```

### flex-grow 属性

flex-grow 属性定义子容器的扩展比例。按照该比例给子容器分配空间。

```css
.ele{
    flex-grow: <number>; /* default 0 */
}
```

### flex-shrink 属性

flex-shrink 属性定义了子容器收缩的比例。如图，超出的部分按 1:2 的比例从给子容器中减去。此属性要生效，父容器的 flex-wrap 属性要设置为 nowrap

```css
.ele{
    flex-shrink: <number>; /* default 0 */
}
```

### flex-basis 属性

flex-basis 属性定义了子容器在不伸缩情况下的原始尺寸，主轴为横向时代表宽度，主轴为纵向时代表高度。

```css
.ele{
    flex-basis: <length> | auto; /* default auto */
}
```

## flex 的常见值

flex:   none | [ <'flex-grow'> <'flex-shrink'>? || <'flex-basis'> ]

### 初始值 

「flex」的各部件的初始值等同于「flex: 0 1 auto」。 

### flex: none

flex: 0 0 auto

### flex: 0 auto  flex: initial

flex: 0 1 auto

### flex: auto  flex: 1 auto 

flex: 1 1 auto

### flex: <positive-number>

flex: <positive-number> 1 0px

