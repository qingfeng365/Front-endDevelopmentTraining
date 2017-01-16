# 第6讲 实战app之生活频道首页

## 准备静态演示数据

`/trainApp-V1/www/app/app.service.js`

演示数据见 `/素材/静态演示数据.md`


```js
(function() {
  'use strict';
  var currApp = angular.module('app');
  currApp
    .service('appDataService', ['$q', '$http',
      function($q, $http) {
      	/**
      	 * [getStoreList description]
      	 * @param  {[type]} start [起始记录,从0开始计数]
      	 * @param  {[type]} size  [返回数据条数]
      	 * @return {[type]}       [description]
      	 */
        this.getStoreList = function(start, size) {
          var self = this;
          return $q(function(resolve, reject) {
            //静态演示,制造1秒延时效果
            var count = self.demoDatas.stores.length;
            var end = start + size - 1;
            setTimeout(function() {
            	if (start < count){
            		if(end >= count){
            			end = count - 1;
            		}
            		resolve(self.demoDatas.stores.slice(start, end + 1));
            	} else {
            		// 如果start已到最大,
            		resolve([]);
            	}
            }, 1000);
          });
        };

        this.demoDatas = {};

      }
    ]);
})();

```

##　加载数据

`/trainApp-V1/www/app/life.js`

```js
(function() {
  'use strict';
 	angular.module('life',['app']);
})();
```

`/trainApp-V1/www/app/life.controller.js`

```js
(function() {
  'use strict';
  var currApp = angular.module('life');
  currApp
    .controller('life.mainController', ['$scope', '$state', '$ionicHistory',
      '$window', 'appDataService','$q',
      function($scope, $state, $ionicHistory, $window, appDataService,$q) {
        var vm = this;

        vm.listdata = [];
        vm.isHasMoreData = true;
        /*下次取列表的开始位置*/
        vm.currNextIndex = 0;
        /*每次读取的最大条数*/
        vm.loadSize = 3;

        vm.addToListdata = function(data) {
          if (!vm.listdata) {
            vm.listdata = [];
          }
          vm.listdata = vm.listdata.concat(data);
          vm.isHasMoreData = (data.length !== 0);
        };

        /*因要被引用,写成promise*/
        vm.readListdata = function() {
          return $q(function(resolve, reject) {
            if (vm.isHasMoreData) {
              return appDataService.getStoreList(vm.currNextIndex, vm.loadSize)
                .then(function(data) {
                  console.log(data);
                  vm.currNextIndex = vm.currNextIndex + data.length;
                  return resolve(vm.addToListdata(data));
                })
                .catch(function(err) {
                  console.error(err);
                  return reject(err);
                });
            }else{
              return resolve(null);
            }
          });
        };

        vm.readListdata();

      }
    ]);
})();

```

## 处理视图

```jade
ion-view(title="首页")
	ion-content.life.life-home(padding="true")
		ion-slide-box(auto-play="true",
			does-continue="true",
			slide-interval=3000)
			ion-slide
				img(src="img/广告横幅1.jpg")
			ion-slide
				img(src="img/广告横幅2.jpg")
			ion-slide
				img(src="img/广告横幅1.jpg")
			ion-slide
				img(src="img/广告横幅2.jpg")
		.navbtnbox
			.row
				.col.navbtn(ng-click="gotoUrl('#/cate')")
					img.navbtn-icon(src="img/图标美食.jpg")
					h5.navbtn-title 美食
				.col.navbtn()
					img.navbtn-icon(src="img/图标电影.jpg")
					h5.navbtn-title 电影
				.col.navbtn()
					img.navbtn-icon(src="img/图标酒店.jpg")
					h5.navbtn-title 酒店
				.col.navbtn()
					img.navbtn-icon(src="img/图标KTV.jpg")
					h5.navbtn-title KTV
			.row
				.col.navbtn()
					img.navbtn-icon(src="img/图标外卖.jpg")
					h5.navbtn-title 外卖
				.col.navbtn()
					img.navbtn-icon(src="img/图标娱乐.jpg")
					h5.navbtn-title 娱乐
				.col.navbtn()
					img.navbtn-icon(src="img/图标游戏.jpg")
					h5.navbtn-title 游戏
				.col.navbtn()
					img.navbtn-icon(src="img/图标休闲.jpg")
					h5.navbtn-title 休闲
		ion-list.padding(ng-repeat="store in vm.listdata", 
			ng-show="vm.listdata.length")
			ion-item
				h2
					strong {{store.name}}
				i.icon.ion-android-star(
					ng-repeat="star in store.star track by $index"
					ng-class="{'energized':star===1,'gray':star===0}")
				span.gray.font-size-12.padding-horizontal &nbsp;&nbsp;¥&nbsp;&nbsp;{{store.spend}}/人
				span.gray.font-size-12.padding-horizontal &nbsp;&nbsp;{{store.loc}}
				span.gray.font-size-12.padding-horizontal &nbsp;&nbsp;{{store.distance}}公里
			ion-item.item-thumbnail-left(
				ng-repeat="product in store.products",
				ng-show="store.products.length")
				img(ng-src="{{product.img}}")
				h3 {{product.name}}
				.row.marging-top
					.col
						span.assertive.font-size-24 ¥ {{product.currentPrice}}
						span.gray.font-size-16
							strike {{product.originPrice}}
					.col
						span.gray.font-size-16 已售 {{product.saledCount}}
```

## 处理样式

`/trainApp-V1/www/css/style.css`

```css

/**
 * ============
 * 通用的辅助样式
 * ============
 */

/**
 * 通用的辅助样式 - 颜色
 */

.gray {
    color: #C0C0C0;
}

/**
 * 通用的辅助样式 - 字体
 */

.font-size-12 {
    font-size: 12px;
}
.font-size-14 {
    font-size: 14px;
}
.font-size-16 {
    font-size: 16px;
}
.font-size-18 {
    font-size: 18px;
}
.font-size-20 {
    font-size: 20px;
}
.font-size-24 {
	font-size: 24px;
}


/**
 * 通用的辅助样式 - marging
 */
.marging-top{
	margin-top: 10px;
}


/**
 * ============
 * 可公用的样式
 * ============
 */

/*横幅广告轮播区 图片*/
.slider-slide img{
	width: 100%;
}

/*导航图标按钮*/
.navbtn{
	text-align: center;
}
/*导航图标按钮 图标*/
.navbtn .navbtn-icon{
	width:48px;
	height:48px;
	max-width:100%;
}


/**
 * 生活板块通用样式
 */


/**
 * 生活首页专用样式
 */

```

测试效果

## 实现上拉加载效果

### 视图加入无限流动组件

`/trainApp-V1/www/jade_template/life.template.main.jade`

```jade
		ion-infinite-scroll(ng-if="vm.isHasMoreData",
			on-infinite="vm.loadMoreData()")
```


### loadMoreData

`/trainApp-V1/www/app/life.controller.js`

```js
        // 启用上拉加载机制后,该句要注释掉
        // vm.readListdata();

        vm.loadMoreData = function() {
          if (!vm.isHasMoreData){
            //数据已全部加载
            console.log("数据已全部加载");
            $scope.$broadcast('scroll.infiniteScrollComplete');
          } else {
            return vm.readListdata()
            .then(function(){
              console.log("数据加载中....");
              $scope.$broadcast('scroll.infiniteScrollComplete');
            })
            .catch(function(err){
              // 如果有错,取消刷新状态,恢复原样
              $scope.$broadcast('scroll.infiniteScrollComplete');
            });
          }
        };
```


## 实现下拉刷新效果

`/trainApp-V1/www/jade_template/life.template.main.jade`

```jade
ion-view(title="首页")
	ion-content.life.life-home(padding="true")
		ion-refresher(pulling-text="释放刷新...",
			on-refresh="vm.pullRefresh()")
```


`/trainApp-V1/www/app/life.controller.js`

```js
        // 下拉刷新
        vm.pullRefresh = function() {
          console.log("数据刷新中....");
          vm.listdata = [];
          vm.isHasMoreData = true;
          vm.currNextIndex = 0;
          return vm.readListdata()
            .then(function() {
              $scope.$broadcast('scroll.refreshComplete');
            })
            .catch(function(err) {
              // 如果有错,取消刷新状态,恢复原样
              $scope.$broadcast('scroll.refreshComplete');
            });
        };
```


