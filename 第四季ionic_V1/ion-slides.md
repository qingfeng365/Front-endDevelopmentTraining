
ion-slides 是利用swiper插件,因此部分设置还需要看swiper api

http://idangero.us/swiper/api/#.WGnhtJKdrCg

 @ngdoc directive
 @name ionSlides
 @module ionic
 @restrict E
 @description
 The Slides component is a powerful multi-page container where each page can be swiped or dragged between.
 
 Note: this is a new version of the Ionic Slide Box based on the [Swiper](http://www.idangero.us/swiper/#.Vmc1J-ODFBc) widget from
 [idangerous](http://www.idangero.us/).
 
 ![SlideBox](http://ionicframework.com.s3.amazonaws.com/docs/controllers/slideBox.gif)
 
 @usage
 ```html
 <ion-content scroll="false">
   <ion-slides  options="options" slider="data.slider">
     <ion-slide-page>
       <div class="box blue"><h1>BLUE</h1></div>
     </ion-slide-page>
     <ion-slide-page>
       <div class="box yellow"><h1>YELLOW</h1></div>
     </ion-slide-page>
     <ion-slide-page>
       <div class="box pink"><h1>PINK</h1></div>
     </ion-slide-page>
   </ion-slides>
 </ion-content>
 ```
 
 ```js
 $scope.options = {
   loop: false,
   effect: 'fade',
   speed: 500,
 }
 
 $scope.$on("$ionicSlides.sliderInitialized", function(event, data){
   // data.slider is the instance of Swiper
   $scope.slider = data.slider;
 });
 
 $scope.$on("$ionicSlides.slideChangeStart", function(event, data){
   console.log('Slide change is beginning');
 });
 
 $scope.$on("$ionicSlides.slideChangeEnd", function(event, data){
   // note: the indexes are 0-based
   $scope.activeIndex = data.slider.activeIndex;
   $scope.previousIndex = data.slider.previousIndex;
 });
 
 ```
 
 ## Slide Events
 
 The slides component dispatches events when the active slide changes
 
 <table class="table">
   <tr>
     <td><code>$ionicSlides.slideChangeStart</code></td>
     <td>This event is emitted when a slide change begins</td>
   </tr>
   <tr>
     <td><code>$ionicSlides.slideChangeEnd</code></td>
     <td>This event is emitted when a slide change completes</td>
   </tr>
   <tr>
     <td><code>$ionicSlides.sliderInitialized</code></td>
     <td>This event is emitted when the slider is initialized. It provides access to an instance of the slider.</td>
   </tr>
 </table>
 
 
 ## Updating Slides Dynamically
 When applying data to the slider at runtime, typically everything will work as expected.
 
 In the event that the slides are looped, use the `updateLoop` method on the slider to ensure the slides update correctly.
 
 ```js
 $scope.$on("$ionicSlides.sliderInitialized", function(event, data){
   // grab an instance of the slider
   $scope.slider = data.slider;
 });
 
 function dataChangeHandler(){
   // call this function when data changes, such as an HTTP request, etc
   if ( $scope.slider ){
     $scope.slider.updateLoop();
   }
 }
 ```
 
