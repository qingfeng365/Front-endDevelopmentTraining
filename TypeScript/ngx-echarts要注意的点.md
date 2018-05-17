# ngx-echarts要注意的点

## 图的高度

如果 图 的 html 元素 本身没有高度, 则 ngx-echarts 会强制改变 
元素 的 height 为 400px

为了 避免 强制指定, 要做以下两点:

- 要让 图 的 html 元素 自身在没有内容时,也有高度
- 如果 采用强性布局, 则在 组件的 ngAfterViewInit 事件时, 元素高度并不是最终高度
	应用 setTimeout 处理, 让 chart 在 setTimeout 中才生成