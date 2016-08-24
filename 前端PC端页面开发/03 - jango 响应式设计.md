# 03 - jango 响应式设计

## 响应式设计


## jango

### min-width

@media (min-width: 992px)

@media (min-width: 1170px)


### max-width

@media (max-width: 480px) (iphon4)

@media (max-width: 767px) 

@media (max-width: 768px) (ipad 竖) ()

@media (max-width: 991px)

@media (max-width: 1199px) (手提电脑 1920×1080)

@media (max-width: 1390px)

### 写法规范

- 宽度判断 是包含当前, 即 >=  <= 
- 当符合多个时,后面的条件优先
- min-width 要从小写到大
- max-width 要从大写到小

### 注意与bootstrap的定义的兼容性


```css

/* 超小屏幕（手机，小于 768px） */
/* 没有任何媒体查询相关的代码，因为这在 Bootstrap 中是默认的（还记得 Bootstrap 是移动设备优先的吗？） */

/* 小屏幕（平板，大于等于 768px） */
@media (min-width: @screen-sm-min) { ... }

/* 中等屏幕（桌面显示器，大于等于 992px） */
@media (min-width: @screen-md-min) { ... }

/* 大屏幕（大桌面显示器，大于等于 1200px） */
@media (min-width: @screen-lg-min) { ... }

```

### bootstrap的定义

- `.col-xs-`: 超小屏幕 手机 (<768px) 

- `.col-sm-`: 小屏幕 平板 (≥768px)

- `.col-md-`: 中等屏幕 桌面显示器 (≥992px)

- `.col-lg-`: 大屏幕 大桌面显示器 (≥1200px)





