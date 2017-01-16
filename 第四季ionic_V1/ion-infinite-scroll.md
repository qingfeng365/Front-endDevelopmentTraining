


```js
/**
 * @ngdoc directive
 * @name ionInfiniteScroll
 * @module ionic
 * @parent ionic.directive:ionContent, ionic.directive:ionScroll
 * @restrict E
 *
 * @description
 * The ionInfiniteScroll directive allows you to call a function whenever
 * the user gets to the bottom of the page or near the bottom of the page.
 *
 * The expression you pass in for `on-infinite` is called when the user scrolls
 * greater than `distance` away from the bottom of the content.  Once `on-infinite`
 * is done loading new data, it should broadcast the `scroll.infiniteScrollComplete`
 * event from your controller (see below example).
 *
 * @param {expression} on-infinite What to call when the scroller reaches the
 * bottom.
 * @param {string=} distance The distance from the bottom that the scroll must
 * reach to trigger the on-infinite expression. Default: 1%.
 * @param {string=} spinner The {@link ionic.directive:ionSpinner} to show while loading. The SVG
 * {@link ionic.directive:ionSpinner} is now the default, replacing rotating font icons.
 * @param {string=} icon The icon to show while loading. Default: 'ion-load-d'.  This is depreciated
 * in favor of the SVG {@link ionic.directive:ionSpinner}.
 * @param {boolean=} immediate-check Whether to check the infinite scroll bounds immediately on load.
 *
 * @usage
 * ```html
 * <ion-content ng-controller="MyController">
 *   <ion-list>
 *   ....
 *   ....
 *   </ion-list>
 *
 *   <ion-infinite-scroll
 *     on-infinite="loadMore()"
 *     distance="1%">
 *   </ion-infinite-scroll>
 * </ion-content>
 * ```
 * ```js
 * function MyController($scope, $http) {
 *   $scope.items = [];
 *   $scope.loadMore = function() {
 *     $http.get('/more-items').success(function(items) {
 *       useItems(items);
 *       $scope.$broadcast('scroll.infiniteScrollComplete');
 *     });
 *   };
 *
 *   $scope.$on('$stateChangeSuccess', function() {
 *     $scope.loadMore();
 *   });
 * }
 * ```
 *
 * An easy to way to stop infinite scroll once there is no more data to load
 * is to use angular's `ng-if` directive:
 *
 * ```html
 * <ion-infinite-scroll
 *   ng-if="moreDataCanBeLoaded()"
 *   icon="ion-loading-c"
 *   on-infinite="loadMoreData()">
 * </ion-infinite-scroll>
 * ```
 */
```

 ion-infinite-scroll
Child of ionContent or ionScroll

The ionInfiniteScroll directive allows you to call a function whenever the user gets to the bottom of the page or near the bottom of the page.

The expression you pass in for on-infinite is called when the user scrolls greater than distance away from the bottom of the content. Once on-infinite is done loading new data, it should broadcast the scroll.infiniteScrollComplete event from your controller (see below example).
Usage

<ion-content ng-controller="MyController">
  <ion-list>
  ....
  ....
  </ion-list>

  <ion-infinite-scroll
    on-infinite="loadMore()"
    distance="1%">
  </ion-infinite-scroll>
</ion-content>

function MyController($scope, $http) {
  $scope.items = [];
  $scope.loadMore = function() {
    $http.get('/more-items').success(function(items) {
      useItems(items);
      $scope.$broadcast('scroll.infiniteScrollComplete');
    });
  };

  $scope.$on('$stateChangeSuccess', function() {
    $scope.loadMore();
  });
}

An easy to way to stop infinite scroll once there is no more data to load is to use angular’s ng-if directive:

<ion-infinite-scroll
  ng-if="moreDataCanBeLoaded()"
  icon="ion-loading-c"
  on-infinite="loadMoreData()">
</ion-infinite-scroll>

API
Attr 	Type 	Details
on-infinite 	expression 	

What to call when the scroller reaches the bottom.
distance
(optional)
	string 	

The distance from the bottom that the scroll must reach to trigger the on-infinite expression. Default: 1%.
spinner
(optional)
	string 	

The ionSpinner to show while loading. The SVG ionSpinner is now the default, replacing rotating font icons.
icon
(optional)
	string 	

The icon to show while loading. Default: 'ion-load-d'. This is depreicated in favor of the SVG ionSpinner.
immediate-check
(optional)
	boolean 	

Whether to check the infinite scroll bounds immediately on load.
