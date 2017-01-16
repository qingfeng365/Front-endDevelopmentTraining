



http://ionicframework.com/docs/api/service/$ionicHistory/

 $ionicHistory

$ionicHistory keeps track of views as the user navigates through an app. Similar to the way a browser behaves, an Ionic app is able to keep track of the previous view, the current view, and the forward view (if there is one). However, a typical web browser only keeps track of one history stack in a linear fashion.

Unlike a traditional browser environment, apps and webapps have parallel independent histories, such as with tabs. Should a user navigate few pages deep on one tab, and then switch to a new tab and back, the back button relates not to the previous tab, but to the previous pages visited within that tab.

$ionicHistory facilitates this parallel history architecture.
Methods
viewHistory()

The app’s view history data, such as all the views and histories, along with how they are ordered and linked together within the navigation stack.

    Returns: object Returns an object containing the apps view history data.

currentView()

The app’s current view.

    Returns: object Returns the current view.

currentHistoryId()

The ID of the history stack which is the parent container of the current view.

    Returns: string Returns the current history ID.

currentTitle([val])

Gets and sets the current view’s title.
Param 	Type 	Details
val
(optional)
	string 	

The title to update the current view with.

    Returns: string Returns the current view’s title.

backView()

Returns the view that was before the current view in the history stack. If the user navigated from View A to View B, then View A would be the back view, and View B would be the current view.

    Returns: object Returns the back view.

backTitle()

Gets the back view’s title.

    Returns: string Returns the back view’s title.

forwardView()

Returns the view that was in front of the current view in the history stack. A forward view would exist if the user navigated from View A to View B, then navigated back to View A. At this point then View B would be the forward view, and View A would be the current view.

    Returns: object Returns the forward view.

currentStateName()

Returns the current state name.

    Returns: string

goBack([backCount])

Navigates the app to the back view, if a back view exists.
Param 	Type 	Details
backCount
(optional)
	number 	

Optional negative integer setting how many views to go back. By default it'll go back one view by using the value -1. To go back two views you would use -2. If the number goes farther back than the number of views in the current history's stack then it'll go to the first view in the current history's stack. If the number is zero or greater then it'll do nothing. It also does not cross history stacks, meaning it can only go as far back as the current history.
removeBackView()

Remove the previous view from the history completely, including the cached element and scope (if they exist).
clearHistory()

Clears out the app’s entire history, except for the current view.
clearCache()

Removes all cached views within every ionNavView. This both removes the view element from the DOM, and destroy it’s scope.

    Returns: promise

nextViewOptions()

Sets options for the next view. This method can be useful to override certain view/transition defaults right before a view transition happens. For example, the menuClose directive uses this method internally to ensure an animated view transition does not happen when a side menu is open, and also sets the next view as the root of its history stack. After the transition these options are set back to null.

Available options:

    disableAnimate: Do not animate the next transition.
    disableBack: The next view should forget its back view, and set it to null.
    historyRoot: The next view should become the root view in its history stack.

$ionicHistory.nextViewOptions({
  disableAnimate: true,
  disableBack: true
});

