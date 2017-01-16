

 @ngdoc directive
 @name ionSlideBox
 @module ionic
 @codepen AjgEB
 @deprecated will be removed in the next Ionic release in favor of the new ion-slides component.
 Don't depend on the internal behavior of this widget.
 @delegate ionic.service:$ionicSlideBoxDelegate
 @restrict E
 @description
 The Slide Box is a multi-page container where each page can be swiped or dragged between:

 @usage
 ```html
 <ion-slide-box on-slide-changed="slideHasChanged($index)">
   <ion-slide>
     <div class="box blue"><h1>BLUE</h1></div>
   </ion-slide>
   <ion-slide>
     <div class="box yellow"><h1>YELLOW</h1></div>
   </ion-slide>
   <ion-slide>
     <div class="box pink"><h1>PINK</h1></div>
   </ion-slide>
 </ion-slide-box>
 ```

 @param {string=} delegate-handle The handle used to identify this slideBox
 with {@link ionic.service:$ionicSlideBoxDelegate}.
 @param {boolean=} does-continue Whether the slide box should loop.
 @param {boolean=} auto-play Whether the slide box should automatically slide. Default true if does-continue is true.
 @param {number=} slide-interval How many milliseconds to wait to change slides (if does-continue is true). Defaults to 4000.
 @param {boolean=} show-pager Whether a pager should be shown for this slide box. Accepts expressions via `show-pager="{{shouldShow()}}"`. Defaults to true.
 @param {expression=} pager-click Expression to call when a pager is clicked (if show-pager is true). Is passed the 'index' variable.
 @param {expression=} on-slide-changed Expression called whenever the slide is changed.  Is passed an '$index' variable.
 @param {expression=} active-slide Model to bind the current slide index to.

