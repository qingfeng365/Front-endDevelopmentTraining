
# 关于dom

## 查询某元素的dom属性


```
.el(#el="")


  @ViewChild('el') el: ElementRef;

  this.el.nativeElement.getBoundingClientRect()

  window.getComputedStyle(this.el.nativeElement)

	window.getComputedStyle(this.el.nativeElement).getPropertyValue('height');

	window.getComputedStyle(this.el.nativeElement).paddingTop;

```