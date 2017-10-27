# flex 要点

## 学习资源

[https://juejin.im/post/599970f4518825243a78b9d5?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com](https://juejin.im/post/599970f4518825243a78b9d5?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com)

[https://www.w3.org/html/ig/zh/wiki/Css3-flexbox/zh-hans#.E4.BC.B8.E7.BC.A9.E9.A1.B9.E7.9B.AE](https://www.w3.org/html/ig/zh/wiki/Css3-flexbox/zh-hans#.E4.BC.B8.E7.BC.A9.E9.A1.B9.E7.9B.AE)

## 基础知识

- 设为 Flex 布局以后，子元素的 float、clear 和 vertical-align 属性将失效。

## 默认设置

### 如果仅在容器设置 `display: flex`

则相当于有以下默认值:

```css
	.box{
		display: flex;
		flex-direction: row;
		flex-wrap: nowrap;
		  // 或
		flex-flow: row nowrap;
		
		justify-content: normal;  //相当于 flex-start 左对齐
		align-items: normal; // 相当于 stretch, 如果项目未设置高度或设为auto，将占满整个容器的高度
		align-content: normal;
	}

	.item{
		order: 0;
		align-self: normal; //相当于 stretch,如果项目未设置高度或设为auto，将占满整个容器的高度。

		flex-grow: 0; //不自动扩展
		flex-shrink: 1; //收缩比率为1
		flex-basis: auto; //原始宽高

     //或
    flex: 0 1 auto; // 
	}
```

表现行为:

- 容器宽度大于 项目总宽时, 项目左对齐, 右侧留白
- 容器宽度小于 项目总宽时, 项目自动收缩 (因为不换行,且flex-shrink=1)
- 容器高度等于 高度最大的项目
- 项目有设置高度时, 项目为自身高度
- 项目没有设置高度时, 则会充满容器

