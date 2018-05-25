# tslint 部分提示的解决方案

### for ... in ...

提示: for (... in ...) statements must be filtered with an if statement

建议增加 hasOwnProperty 的 if 判断

问题代码示例:

```ts
	for (const key in skuAttr) {
	    goodsSku = goodsSku + ',' + skuAttr[key].id;
	    attrValues =
	      attrValues + (attrValues === '' ? '' : ' ') + skuAttr[key].value;
	}
```

解决方法: 

```ts
  for (const key in skuAttr) {
    if (skuAttr.hasOwnProperty(key)) {
      goodsSku = goodsSku + ',' + skuAttr[key].id;
      attrValues =
        attrValues + (attrValues === '' ? '' : ' ') + skuAttr[key].value;
    }
  }
```

