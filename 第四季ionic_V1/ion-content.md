
https://github.com/driftyco/ionic/blob/1.x/js/angular/directive/content.js#L1

```js
/**
 * @ngdoc directive
 * @name ionContent
 * @module ionic
 * @delegate ionic.service:$ionicScrollDelegate
 * @restrict E
 *
 * @description
 * The ionContent directive provides an easy to use content area that can be configured
 * to use Ionic's custom Scroll View, or the built in overflow scrolling of the browser.
 *
 * While we recommend using the custom Scroll features in Ionic in most cases, sometimes
 * (for performance reasons) only the browser's native overflow scrolling will suffice,
 * and so we've made it easy to toggle between the Ionic scroll implementation and
 * overflow scrolling.
 *
 * You can implement pull-to-refresh with the {@link ionic.directive:ionRefresher}
 * directive, and infinite scrolling with the {@link ionic.directive:ionInfiniteScroll}
 * directive.
 *
 * If there is any dynamic content inside the ion-content, be sure to call `.resize()` with {@link ionic.service:$ionicScrollDelegate}
 * after the content has been added.
 *
 * Be aware that this directive gets its own child scope. If you do not understand why this
 * is important, you can read [https://docs.angularjs.org/guide/scope](https://docs.angularjs.org/guide/scope).
 *
 * @param {string=} delegate-handle The handle used to identify this scrollView
 * with {@link ionic.service:$ionicScrollDelegate}.
 * @param {string=} direction Which way to scroll. 'x' or 'y' or 'xy'. Default 'y'.
 * @param {boolean=} locking Whether to lock scrolling in one direction at a time. Useful to set to false when zoomed in or scrolling in two directions. Default true.
 * @param {boolean=} padding Whether to add padding to the content.
 * Defaults to true on iOS, false on Android.
 * @param {boolean=} scroll Whether to allow scrolling of content.  Defaults to true.
 * @param {boolean=} overflow-scroll Whether to use overflow-scrolling instead of
 * Ionic scroll. See {@link ionic.provider:$ionicConfigProvider} to set this as the global default.
 * @param {boolean=} scrollbar-x Whether to show the horizontal scrollbar. Default true.
 * @param {boolean=} scrollbar-y Whether to show the vertical scrollbar. Default true.
 * @param {string=} start-x Initial horizontal scroll position. Default 0.
 * @param {string=} start-y Initial vertical scroll position. Default 0.
 * @param {expression=} on-scroll Expression to evaluate when the content is scrolled.
 * @param {expression=} on-scroll-complete Expression to evaluate when a scroll action completes. Has access to 'scrollLeft' and 'scrollTop' locals.
 * @param {boolean=} has-bouncing Whether to allow scrolling to bounce past the edges
 * of the content.  Defaults to true on iOS, false on Android.
 * @param {number=} scroll-event-interval Number of milliseconds between each firing of the 'on-scroll' expression. Default 10.
 */
```

http://ionicframework.com/docs/api/directive/ionContent/

 ion-content
Delegate: $ionicScrollDelegate

The ionContent directive provides an easy to use content area that can be configured to use Ionic’s custom Scroll View, or the built in overflow scrolling of the browser.

While we recommend using the custom Scroll features in Ionic in most cases, sometimes (for performance reasons) only the browser’s native overflow scrolling will suffice, and so we’ve made it easy to toggle between the Ionic scroll implementation and overflow scrolling.

You can implement pull-to-refresh with the ionRefresher directive, and infinite scrolling with the ionInfiniteScroll directive.

If there is any dynamic content inside the ion-content, be sure to call .resize() with $ionicScrollDelegate after the content has been added.

Be aware that this directive gets its own child scope. If you do not understand why this is important, you can read https://docs.angularjs.org/guide/scope.
Usage

<ion-content
[delegate-handle=""]
[direction=""]
[locking=""]
[padding=""]
[scroll=""]
[overflow-scroll=""]
[scrollbar-x=""]
[scrollbar-y=""]
[start-x=""]
[start-y=""]
[on-scroll=""]
[on-scroll-complete=""]
[has-bouncing=""]
[scroll-event-interval=""]>
...
</ion-content>

API
Attr 	Type 	Details
delegate-handle
(optional)
	string 	

The handle used to identify this scrollView with $ionicScrollDelegate.
direction
(optional)
	string 	

Which way to scroll. 'x' or 'y' or 'xy'. Default 'y'.
locking
(optional)
	boolean 	

Whether to lock scrolling in one direction at a time. Useful to set to false when zoomed in or scrolling in two directions. Default true.
padding
(optional)
	boolean 	

Whether to add padding to the content. Defaults to true on iOS, false on Android.
scroll
(optional)
	boolean 	

Whether to allow scrolling of content. Defaults to true.
overflow-scroll
(optional)
	boolean 	

Whether to use overflow-scrolling instead of Ionic scroll. See $ionicConfigProvider to set this as the global default.
scrollbar-x
(optional)
	boolean 	

Whether to show the horizontal scrollbar. Default true.
scrollbar-y
(optional)
	boolean 	

Whether to show the vertical scrollbar. Default true.
start-x
(optional)
	string 	

Initial horizontal scroll position. Default 0.
start-y
(optional)
	string 	

Initial vertical scroll position. Default 0.
on-scroll
(optional)
	expression 	

Expression to evaluate when the content is scrolled.
on-scroll-complete
(optional)
	expression 	

Expression to evaluate when a scroll action completes. Has access to 'scrollLeft' and 'scrollTop' locals.
has-bouncing
(optional)
	boolean 	

Whether to allow scrolling to bounce past the edges of the content. Defaults to true on iOS, false on Android.
scroll-event-interval
(optional)
	number 	

Number of milliseconds between each firing of the 'on-scroll' expression. Default 10.
