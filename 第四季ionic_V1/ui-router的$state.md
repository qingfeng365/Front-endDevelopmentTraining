$state
service in module ui.router.state
Description

$state service is responsible for representing states as well as transitioning between them. It also provides interfaces to ask for current state or even states you're coming from.
Dependencies

    $rootScope
    $q
    $view
    $injector
    $resolve
    $stateParams
    $urlRouter

Methods

    get(stateOrName, context)

    Returns the state configuration object for any specific state or all states.
    Parameters
    Param	Type	Details
    stateOrName
    (optional)
    	
    string
    object
    	

    (absolute or relative) If provided, will only get the config for the requested state. If not provided, returns an array of ALL state configs.
    context
    (optional)
    	
    string
    object
    	

    When stateOrName is a relative state reference, the state will be retrieved relative to context.
    Returns
    Object|Array
    	

    State configuration object or array of all objects.
    go(to, params, options)

    Convenience method for transitioning to a new state. $state.go calls $state.transitionTo internally but automatically sets options to { location: true, inherit: true, relative: $state.$current, notify: true }. This allows you to easily use an absolute or relative to path and specify only the parameters you'd like to update (while letting unspecified parameters inherit from the currently active ancestor states).
    Parameters
    Param	Type	Details
    to	
    string
    	

    Absolute state name or relative state path. Some examples:
        $state.go('contact.detail') - will go to the contact.detail state
        $state.go('^') - will go to a parent state
        $state.go('^.sibling') - will go to a sibling state
        $state.go('.child.grandchild') - will go to grandchild state
    params
    (optional)
    	
    object
    	

    A map of the parameters that will be sent to the state, will populate $stateParams. Any parameters that are not specified will be inherited from currently defined parameters. Only parameters specified in the state definition can be overridden, new parameters will be ignored. This allows, for example, going to a sibling state that shares parameters specified in a parent state. Parameter inheritance only works between common ancestor states, I.e. transitioning to a sibling will get you the parameters for all parents, transitioning to a child will get you all current parameters, etc.
    options
    (optional)
    	
    object
    	

    Options object. The options are:
        location - {boolean=true|string=} - If true will update the url in the location bar, if false will not. If string, must be "replace", which will update url and also replace last history record.
        inherit - {boolean=true}, If true will inherit url parameters from current url.
        relative - {object=$state.$current}, When transitioning with relative path (e.g '^'), defines which state to be relative from.
        notify - {boolean=true}, If true will broadcast $stateChangeStart and $stateChangeSuccess events.
        reload (v0.2.5) - {boolean=false|string|object}, If true will force transition even if no state or params have changed. It will reload the resolves and views of the current state and parent states. If reload is a string (or state object), the state object is fetched (by name, or object reference); and \ the transition reloads the resolves and views for that matched state, and all its children states.
    Returns
    promise
    	

    A promise representing the state of the new transition.

    Possible success values:
        $state.current


    Possible rejection values:
        'transition superseded' - when a newer transition has been started after this one
        'transition prevented' - when event.preventDefault() has been called in a $stateChangeStart listener
        'transition aborted' - when event.preventDefault() has been called in a $stateNotFound listener or when a $stateNotFound event.retry promise errors.
        'transition failed' - when a state has been unsuccessfully found after 2 tries.
        resolve error - when an error has occurred with a resolve
    Example

        var app = angular.module('app', ['ui.router']);
         
        app.controller('ctrl', function ($scope, $state) {
          $scope.changeState = function () {
            $state.go('contact.detail');
          };
        });

    href(stateOrName, params, options)

    A url generation method that returns the compiled url for the given state populated with the given params.
    Parameters
    Param	Type	Details
    stateOrName	
    string
    object
    	

    The state name or state object you'd like to generate a url from.
    params
    (optional)
    	
    object
    	

    An object of parameter values to fill the state's required parameters.
    options
    (optional)
    	
    object
    	

    Options object. The options are:
        lossy - {boolean=true} - If true, and if there is no url associated with the state provided in the first parameter, then the constructed href url will be built from the first navigable ancestor (aka ancestor with a valid url).
        inherit - {boolean=true}, If true will inherit url parameters from current url.
        relative - {object=$state.$current}, When transitioning with relative path (e.g '^'), defines which state to be relative from.
        absolute - {boolean=false}, If true will generate an absolute url, e.g. "http://www.example.com/fullurl".
    Returns
    string
    	

    compiled state url
    Example

        expect($state.href("about.person", { person: "bob" })).toEqual("/about/bob");

    includes(stateOrName, params, options)

    A method to determine if the current active state is equal to or is the child of the state stateName. If any params are passed then they will be tested for a match as well. Not all the parameters need to be passed, just the ones you'd like to test for equality.
    Parameters
    Param	Type	Details
    stateOrName	
    string
    	

    A partial name, relative name, or glob pattern to be searched for within the current state name.
    params
    (optional)
    	
    object
    	

    A param object, e.g. {sectionId: section.id}, that you'd like to test against the current active state.
    options
    (optional)
    	
    object
    	

    An options object. The options are:
        relative - {string|object=} - If stateOrName is a relative state reference and options.relative is set, .includes will test relative to options.relative state (or name).
    Returns
    boolean
    	

    Returns true if it does include the state
    Example

    Partial and relative names

        $state.$current.name = 'contacts.details.item';
         
        // Using partial names
        $state.includes("contacts"); // returns true
        $state.includes("contacts.details"); // returns true
        $state.includes("contacts.details.item"); // returns true
        $state.includes("contacts.list"); // returns false
        $state.includes("about"); // returns false
         
        // Using relative names (. and ^), typically from a template
        // E.g. from the 'contacts.details' template
        <div ng-class="{highlighted: $state.includes('.item')}">Item</div>

    Basic globbing patterns

        $state.$current.name = 'contacts.details.item.url';
         
        $state.includes("*.details.*.*"); // returns true
        $state.includes("*.details.**"); // returns true
        $state.includes("**.item.**"); // returns true
        $state.includes("*.details.item.url"); // returns true
        $state.includes("*.details.*.url"); // returns true
        $state.includes("*.details.*"); // returns false
        $state.includes("item.**"); // returns false

    is(stateOrName, params, options)

    Similar to $state.includes, but only checks for the full state name. If params is supplied then it will be tested for strict equality against the current active params object, so all params must match with none missing and no extras.
    Parameters
    Param	Type	Details
    stateOrName	
    string
    object
    	

    The state name (absolute or relative) or state object you'd like to check.
    params
    (optional)
    	
    object
    	

    A param object, e.g. {sectionId: section.id}, that you'd like to test against the current active state.
    options
    (optional)
    	
    object
    	

    An options object. The options are:
        relative - {string|object} - If stateOrName is a relative state name and options.relative is set, .is will test relative to options.relative state (or name).
    Returns
    boolean
    	

    Returns true if it is the state.
    Example

        $state.$current.name = 'contacts.details.item';
         
        // absolute name
        $state.is('contact.details.item'); // returns true
        $state.is(contactDetailItemStateObject); // returns true
         
        // relative name (. and ^), typically from a template
        // E.g. from the 'contacts.details' template
        <div ng-class="{highlighted: $state.is('.item')}">Item</div>

    reload(state)

    A method that force reloads the current state. All resolves are re-resolved, controllers reinstantiated, and events re-fired.
    Parameters
    Param	Type	Details
    state
    (optional)
    	
    string=
    object
    	
        A state name or a state object, which is the root of the resolves to be re-resolved.
    Returns
    promise
    	

    A promise representing the state of the new transition. See $state.go.
    Example

        //assuming app application consists of 3 states: 'contacts', 'contacts.detail', 'contacts.detail.item' 
        //and current state is 'contacts.detail.item'
        var app angular.module('app', ['ui.router']);
         
        app.controller('ctrl', function ($scope, $state) {
          $scope.reload = function(){
            //will reload 'contact.detail' and 'contact.detail.item' states
            $state.reload('contact.detail');
          }
        });

    reload() is just an alias for:

        $state.transitionTo($state.current, $stateParams, { 
          reload: true, inherit: false, notify: true
        });

    transitionTo(to, toParams, options)

    Low-level method for transitioning to a new state. $state.go uses transitionTo internally. $state.go is recommended in most situations.
    Parameters
    Param	Type	Details
    to	
    string
    	

    State name.
    toParams
    (optional)
    	
    object
    	

    A map of the parameters that will be sent to the state, will populate $stateParams.
    options
    (optional)
    	
    object
    	

    Options object. The options are:
        location - {boolean=true|string=} - If true will update the url in the location bar, if false will not. If string, must be "replace", which will update url and also replace last history record.
        inherit - {boolean=false}, If true will inherit url parameters from current url.
        relative - {object=}, When transitioning with relative path (e.g '^'), defines which state to be relative from.
        notify - {boolean=true}, If true will broadcast $stateChangeStart and $stateChangeSuccess events.
        reload (v0.2.5) - {boolean=false|string=|object=}, If true will force transition even if the state or params have not changed, aka a reload of the same state. It differs from reloadOnSearch because you'd use this when you want to force a reload when everything is the same, including search params. if String, then will reload the state with the name given in reload, and any children. if Object, then a stateObj is expected, will reload the state found in stateObj, and any children.
    Returns
    promise
    	

    A promise representing the state of the new transition. See $state.go.
    Example

        var app = angular.module('app', ['ui.router']);
         
        app.controller('ctrl', function ($scope, $state) {
          $scope.changeState = function () {
            $state.transitionTo('contact.detail');
          };
        });

Properties

    params

    A param object, e.g. {sectionId: section.id)}, that you'd like to test against the current active state.
    current

    A reference to the state's config object. However you passed it in. Useful for accessing custom data.
    transition

    Currently pending transition. A promise that'll resolve or reject.

Events

    $stateChangeError

    Fired when an error occurs during transition. It's important to note that if you have any errors in your resolve functions (javascript errors, non-existent services, etc) they will not throw traditionally. You must listen for this $stateChangeError event to catch ALL errors.
    Type:
    broadcast
    Target:
    root scope
    Parameters
    Param	Type	Details
    event	
    Object
    	

    Event object.
    toState	
    State
    	

    The state being transitioned to.
    toParams	
    Object
    	

    The params supplied to the toState.
    fromState	
    State
    	

    The current state, pre-transition.
    fromParams	
    Object
    	

    The params supplied to the fromState.
    error	
    Error
    	

    The resolve error object.
    $stateChangeStart

    Fired when the state transition begins. You can use event.preventDefault() to prevent the transition from happening and then the transition promise will be rejected with a 'transition prevented' value.
    Type:
    broadcast
    Target:
    root scope
    Parameters
    Param	Type	Details
    event	
    Object
    	

    Event object.
    toState	
    State
    	

    The state being transitioned to.
    toParams	
    Object
    	

    The params supplied to the toState.
    fromState	
    State
    	

    The current state, pre-transition.
    fromParams	
    Object
    	

    The params supplied to the fromState.
    Example

        $rootScope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){
            event.preventDefault();
            // transitionTo() promise will be rejected with
            // a 'transition prevented' error
        })

    $stateChangeSuccess

    Fired once the state transition is complete.
    Type:
    broadcast
    Target:
    root scope
    Parameters
    Param	Type	Details
    event	
    Object
    	

    Event object.
    toState	
    State
    	

    The state being transitioned to.
    toParams	
    Object
    	

    The params supplied to the toState.
    fromState	
    State
    	

    The current state, pre-transition.
    fromParams	
    Object
    	

    The params supplied to the fromState.
    $stateNotFound

    Fired when a requested state cannot be found using the provided state name during transition. The event is broadcast allowing any handlers a single chance to deal with the error (usually by lazy-loading the unfound state). A special unfoundState object is passed to the listener handler, you can see its three properties in the example. You can use event.preventDefault() to abort the transition and the promise returned from go will be rejected with a 'transition aborted' value.
    Type:
    broadcast
    Target:
    root scope
    Parameters
    Param	Type	Details
    event	
    Object
    	

    Event object.
    unfoundState	
    Object
    	

    Unfound State information. Contains: to, toParams, options properties.
    fromState	
    State
    	

    Current state object.
    fromParams	
    Object
    	

    Current state params.
    Example

        // somewhere, assume lazy.state has not been defined
        $state.go("lazy.state", {a:1, b:2}, {inherit:false});
         
        // somewhere else
        $scope.$on('$stateNotFound',
        function(event, unfoundState, fromState, fromParams){
            console.log(unfoundState.to); // "lazy.state"
            console.log(unfoundState.toParams); // {a:1, b:2}
            console.log(unfoundState.options); // {inherit:false} + default options
        })

