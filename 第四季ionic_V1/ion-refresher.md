

```js
/**
 * @ngdoc directive
 * @name ionRefresher
 * @module ionic
 * @restrict E
 * @parent ionic.directive:ionContent, ionic.directive:ionScroll
 * @description
 * Allows you to add pull-to-refresh to a scrollView.
 *
 * Place it as the first child of your {@link ionic.directive:ionContent} or
 * {@link ionic.directive:ionScroll} element.
 *
 * When refreshing is complete, $broadcast the 'scroll.refreshComplete' event
 * from your controller.
 *
 * @usage
 *
 * ```html
 * <ion-content ng-controller="MyController">
 *   <ion-refresher
 *     pulling-text="Pull to refresh..."
 *     on-refresh="doRefresh()">
 *   </ion-refresher>
 *   <ion-list>
 *     <ion-item ng-repeat="item in items"></ion-item>
 *   </ion-list>
 * </ion-content>
 * ```
 * ```js
 * angular.module('testApp', ['ionic'])
 * .controller('MyController', function($scope, $http) {
 *   $scope.items = [1,2,3];
 *   $scope.doRefresh = function() {
 *     $http.get('/new-items')
 *      .success(function(newItems) {
 *        $scope.items = newItems;
 *      })
 *      .finally(function() {
 *        // Stop the ion-refresher from spinning
 *        $scope.$broadcast('scroll.refreshComplete');
 *      });
 *   };
 * });
 * ```
 *
 * @param {expression=} on-refresh Called when the user pulls down enough and lets go
 * of the refresher.
 * @param {expression=} on-pulling Called when the user starts to pull down
 * on the refresher.
 * @param {string=} pulling-text The text to display while the user is pulling down.
 * @param {string=} pulling-icon The icon to display while the user is pulling down.
 * Default: 'ion-android-arrow-down'.
 * @param {string=} spinner The {@link ionic.directive:ionSpinner} icon to display
 * after user lets go of the refresher. The SVG {@link ionic.directive:ionSpinner}
 * is now the default, replacing rotating font icons. Set to `none` to disable both the
 * spinner and the icon.
 * @param {string=} refreshing-icon The font icon to display after user lets go of the
 * refresher. This is deprecated in favor of the SVG {@link ionic.directive:ionSpinner}.
 * @param {boolean=} disable-pulling-rotation Disables the rotation animation of the pulling
 * icon when it reaches its activated threshold. To be used with a custom `pulling-icon`.
 *
 */
```



 ion-refresher
Child of ionContent or ionScroll

Allows you to add pull-to-refresh to a scrollView.

Place it as the first child of your ionContent or ionScroll element.

When refreshing is complete, $broadcast the ‘scroll.refreshComplete’ event from your controller.
Usage

<ion-content ng-controller="MyController">
  <ion-refresher
    pulling-text="Pull to refresh..."
    on-refresh="doRefresh()">
  </ion-refresher>
  <ion-list>
    <ion-item ng-repeat="item in items"></ion-item>
  </ion-list>
</ion-content>

angular.module('testApp', ['ionic'])
.controller('MyController', function($scope, $http) {
  $scope.items = [1,2,3];
  $scope.doRefresh = function() {
    $http.get('/new-items')
     .success(function(newItems) {
       $scope.items = newItems;
     })
     .finally(function() {
       // Stop the ion-refresher from spinning
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
});

API
Attr 	Type 	Details
on-refresh
(optional)
	expression 	

Called when the user pulls down enough and lets go of the refresher.
on-pulling
(optional)
	expression 	

Called when the user starts to pull down on the refresher.
pulling-text
(optional)
	string 	

The text to display while the user is pulling down.
pulling-icon
(optional)
	string 	

The icon to display while the user is pulling down. Default: 'ion-android-arrow-down'.
spinner
(optional)
	string 	

The ionSpinner icon to display after user lets go of the refresher. The SVG ionSpinner is now the default, replacing rotating font icons. Set to none to disable both the spinner and the icon.
refreshing-icon
(optional)
	string 	

The font icon to display after user lets go of the refresher. This is deprecated in favor of the SVG ionSpinner.
disable-pulling-rotation
(optional)
	boolean 	

Disables the rotation animation of the pulling icon when it reaches its activated threshold. To be used with a custom pulling-icon.