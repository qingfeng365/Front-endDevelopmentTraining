# 第9讲 Angularjs输入相关知识

## 表单知识

### CSS - form

- ng-valid is set if the form is valid.
- ng-invalid is set if the form is invalid.   `非法`
- ng-pristine is set if the form is pristine. `干净`
- ng-dirty is set if the form is dirty.
- ng-submitted is set if the form was submitted.

Keep in mind that ngAnimate can detect each of these classes when added and removed.


### form html标签

```html
<form name="myForm" ng-controller="FormController" 
  >
```

- name
- ng-controller
  如果上级已有controller,可以不创建

### input html标签

```html
<input
  ng-model="string"
  [name="string"]
  [required="string"]
  [ng-required="boolean"]
  [ng-minlength="number"]
  [ng-maxlength="number"]
  [ng-pattern="string"]
  [ng-change="string"]
  [ng-trim="boolean"]>
...
</input>
```
注意:这里没有列全,只是部分通用属性

常规

- ng-model 必有
- name     必有

检查

- required 需要必填时,优先使用
- ng-required 必填需要按条件判断时,使用
- ng-maxlength 应优先使用maxlength
- ng-minlength 最小长度
- ng-pattern   应优先使用pattern

事件

- ng-change 修改时触发事件

附加行为

- ng-trim 自动压缩空格(default: true)

If set to false Angular will not automatically trim the input. This parameter is ignored for input[type=password] controls, which will never trim the input.

(default: true)

#### css - input

The following CSS classes are added and removed on the associated input/select/textarea element depending on the validity of the model.

    ng-valid: the model is valid
    ng-invalid: the model is invalid
    ng-valid-[key]: for each valid key added by $setValidity
    ng-invalid-[key]: for each invalid key added by $setValidity
    ng-pristine: the control hasn't been interacted with yet
    ng-dirty: the control has been interacted with
    ng-touched: the control has been blurred
    ng-untouched: the control hasn't been blurred
    ng-pending: any $asyncValidators are unfulfilled

Keep in mind that ngAnimate can detect each of these classes when added and removed.

#### ngModel

The ngModel directive binds an input,select, textarea (or custom form control) to a property on the scope using NgModelController, which is created and exposed by this directive.

ngModel is responsible for:

- Binding the view into the model, which other directives such as input, textarea or select require.
- Providing validation behavior (i.e. required, number, email, url).
- Keeping the state of the control (valid/invalid, dirty/pristine, touched/untouched, validation errors).
- Setting related css classes on the element (ng-valid, ng-invalid, ng-dirty, ng-pristine, ng-touched, ng-untouched) including animations.
- Registering the control with its parent form.

Note: ngModel will try to bind to the property given by evaluating the expression on the current scope. If the property doesn't already exist on this scope, it will be created implicitly and added to the scope.

For best practices on using ngModel, see:

[Understanding Scopes](https://github.com/angular/angular.js/wiki/Understanding-Scopes)

#### ngModelController

每个ngModel都会对应一个ngModelController

如何访问ngModelController

`$scope.<from name>.<input name>`

#### HTML5 <input> 标签

<style type="text/css">
	table.dataintable td.html5_new {
    background: #efefef url("http://www.w3chtml.com/img/site_table_bg.gif") no-repeat scroll right top;
}
</style>
<table class="dataintable">
    <tbody>
        <tr>
            <th>属性</th>
            <th style="width: 15%">值</th>
            <th style="width: 56%">描述</th>
        </tr>
        <tr>
            <td>accept</td>
            <td><i>list_of_mime_types</i></td>
            <td>
            <p>规定可通过文件上传控件提交的文件类型。</p>
            <p>（仅适用于 type=&quot;file&quot;）</p>
            </td>
        </tr>
        <tr>
            <td>alt</td>
            <td><i>text</i></td>
            <td>
            <p>规定图像输入控件的替代文本。</p>
            <p>（仅适用于 type=&quot;image&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">autocomplete</td>
            <td>
            <ul>
                <li>on</li>
                <li>off</li>
            </ul>
            </td>
            <td>规定是否使用输入字段的自动完成功能。</td>
        </tr>
        <tr>
            <td class="html5_new">autofocus</td>
            <td>autofocus</td>
            <td>
            <p>规定输入字段在页面加载时是否获得焦点。</p>
            <p>（不适用于 type=&quot;hidden&quot;）</p>
            </td>
        </tr>
        <tr>
            <td>checked</td>
            <td>checked</td>
            <td>
            <p>规定当页面加载时是否预先选择该 input 元素。</p>
            <p>（适用于 type=&quot;checkbox&quot; 或 type=&quot;radio&quot;）</p>
            </td>
        </tr>
        <tr>
            <td>disabled</td>
            <td>disabled</td>
            <td>
            <p>规定当页面加载时是否禁用该 input 元素。</p>
            <p>（不适用于 type=&quot;hidden&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">form</td>
            <td><i>formname</i></td>
            <td>规定输入字段所属的一个或多个表单。</td>
        </tr>
        <tr>
            <td class="html5_new">formaction</td>
            <td><i>URL</i></td>
            <td>
            <p>覆盖表单的 action 属性。</p>
            <p>（适用于 type=&quot;submit&quot; 和 type=&quot;image&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">formenctype</td>
            <td>见注释</td>
            <td>
            <p>覆盖表单的 enctype 属性。</p>
            <p>（适用于 type=&quot;submit&quot; 和 type=&quot;image&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">formmethod</td>
            <td>
            <ul>
                <li>get</li>
                <li>post</li>
            </ul>
            </td>
            <td>
            <p>覆盖表单的 method 属性。</p>
            <p>（适用于 type=&quot;submit&quot; 和 type=&quot;image&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">formnovalidate</td>
            <td>formnovalidate</td>
            <td>
            <p>覆盖表单的 novalidate 属性。</p>
            <p>如果使用该属性，则提交表单时不进行验证。</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">formtarget</td>
            <td>
            <ul>
                <li>_blank</li>
                <li>_self</li>
                <li>_parent</li>
                <li>_top</li>
                <li><i>framename</i></li>
            </ul>
            </td>
            <td>
            <p>覆盖表单的 target 属性。</p>
            <p>（适用于 type=&quot;submit&quot; 和 type=&quot;image&quot;）</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">height</td>
            <td>
            <ul>
                <li><i>pixels</i></li>
                <li><i>%</i></li>
            </ul>
            </td>
            <td>定义 input 字段的高度。（适用于 type=&quot;image&quot;）</td>
        </tr>
        <tr>
            <td class="html5_new">list</td>
            <td><i>datalist-id</i></td>
            <td>引用包含输入字段的预定义选项的 datalist 。</td>
        </tr>
        <tr>
            <td class="html5_new">max</td>
            <td>
            <ul>
                <li><i>number</i></li>
                <li><i>date</i></li>
            </ul>
            </td>
            <td>
            <p>规定输入字段的最大值。</p>
            <p>请与 &quot;min&quot; 属性配合使用，来创建合法值的范围。</p>
            </td>
        </tr>
        <tr>
            <td>maxlength</td>
            <td><i>number</i></td>
            <td>规定文本字段中允许的最大字符数。</td>
        </tr>
        <tr>
            <td class="html5_new">min</td>
            <td>
            <ul>
                <li><i>number</i></li>
                <li><i>date</i></li>
            </ul>
            </td>
            <td>
            <p>规定输入字段的最小值。</p>
            <p>请与 &quot;max&quot; 属性配合使用，来创建合法值的范围。</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">multiple</td>
            <td>multiple</td>
            <td>如果使用该属性，则允许一个以上的值。</td>
        </tr>
        <tr>
            <td>name</td>
            <td><i>field_name</i></td>
            <td>
            <p>规定 input 元素的名称。</p>
            <p>name 属性用于在提交表单时搜集字段的值。</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">pattern</td>
            <td><i>regexp_pattern</i></td>
            <td>
            <p>规定输入字段的值的模式或格式。</p>
            <p>例如 pattern=&quot;[0-9]&quot; 表示输入值必须是 0 与 9 之间的数字。</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">placeholder</td>
            <td><i>text</i></td>
            <td>规定帮助用户填写输入字段的提示。</td>
        </tr>
        <tr>
            <td>readonly</td>
            <td>readonly</td>
            <td>指示字段的值无法修改。</td>
        </tr>
        <tr>
            <td class="html5_new">required</td>
            <td>required</td>
            <td>指示输入字段的值是必需的。</td>
        </tr>
        <tr>
            <td>size</td>
            <td><i>number_of_char</i></td>
            <td>规定输入字段中的可见字符数。</td>
        </tr>
        <tr>
            <td>src</td>
            <td><i>URL</i></td>
            <td>规定图像的 URL。（适用于 type=&quot;image&quot;）</td>
        </tr>
        <tr>
            <td class="html5_new">step</td>
            <td><i>number</i></td>
            <td>规定输入字的的合法数字间隔。</td>
        </tr>
        <tr>
            <td>type</td>
            <td>
            <ul>
                <li>button</li>
                <li>checkbox</li>
                <li>date</li>
                <li>datetime</li>
                <li>datetime-local</li>
                <li>email</li>
                <li>file</li>
                <li>hidden</li>
                <li>image</li>
                <li>month</li>
                <li>number</li>
                <li>password</li>
                <li>radio</li>
                <li>range</li>
                <li>reset</li>
                <li>submit</li>
                <li>text</li>
                <li>time</li>
                <li>url</li>
                <li>week</li>
            </ul>
            </td>
            <td>规定 input 元素的类型。</td>
        </tr>
        <tr>
            <td>value</td>
            <td><i>value</i></td>
            <td>
            <p>对于按钮：规定按钮上的文本</p>
            <p>对于图像按钮：传递到脚本的字段的符号结果</p>
            <p>对于复选框和单选按钮：定义 input 元素被点击时的结果。</p>
            <p>对于隐藏、密码和文本字段：规定元素的默认值。</p>
            <p>注释：不能与 type=&quot;file&quot; 一同使用。</p>
            <p>注释：对于 type=&quot;checkbox&quot; 以及 type=&quot;radio&quot;，是必需的。</p>
            </td>
        </tr>
        <tr>
            <td class="html5_new">width</td>
            <td>
            <ul>
                <li><i>pixels</i></li>
                <li><i>%</i></li>
            </ul>
            </td>
            <td>定义 input 字段的宽度。（适用于 type=&quot;image&quot;）</td>
        </tr>
    </tbody>
</table>

### input[text]

```
<input type="text"
       ng-model="string"
       [name="string"]
       [required="string"]
       [ng-required="string"]
       [ng-minlength="number"]
       [ng-maxlength="number"]
       [pattern="string"]
       [ng-pattern="string"]
       [ng-change="string"]
       [ng-trim="boolean"]>
```

### input[checkbox]

```html
<input type="checkbox"
       ng-model="string"
       [name="string"]
       [ng-true-value="expression"]
       [ng-false-value="expression"]
       [ng-change="string"]>
```
仅在ng-model的值不是布尔型时使用

- ng-true-value:The value to which the expression should be set when selected.
- ng-false-value: The value to which the expression should be set when not selected.

### input[radio]

```html
<input type="radio"
       ng-model="string"
       value="string"
       [name="string"]
       [ng-change="string"]
       ng-value="string">
```
-ng-value Angular expression which sets the value to which the expression should be set when selected.
  当值不是简单的字符串时,可使用.指向$scope的变量

## api

### FormController

FormController keeps track of all its controls and nested forms as well as the state of them, such as being valid/invalid or dirty/pristine.

Each form directive creates an instance of FormController.
Methods

- $rollbackViewValue();

    Rollback all form controls pending updates to the $modelValue.

    Updates may be pending by a debounced event or because the input is waiting for a some future event defined in ng-model-options. This method is typically needed by the reset button of a form that uses ng-model-options to pend updates.

- $commitViewValue();

    Commit all form controls pending updates to the $modelValue.

    Updates may be pending by a debounced event or because the input is waiting for a some future event defined in ng-model-options. This method is rarely needed as NgModelController usually handles calling this in response to input events.

- $addControl();

    Register a control with the form.

    Input elements using ngModelController do this automatically when they are linked.

- $removeControl();

    Deregister a control from the form.

    Input elements using ngModelController do this automatically when they are destroyed.

- $setValidity();

    Sets the validity of a form control.

    This method will also propagate to parent forms.

- $setDirty();

    Sets the form to a dirty state.

    This method can be called to add the 'ng-dirty' class and set the form to a dirty state (ng-dirty class). This method will also propagate to parent forms.

- $setPristine();

    Sets the form to its pristine state.

    This method can be called to remove the 'ng-dirty' class and set the form to its pristine state (ng-pristine class). This method will also propagate to all the controls contained in this form.

    Setting a form back to a pristine state is often useful when we want to 'reuse' a form after saving or resetting it.

- $setUntouched();

    Sets the form to its untouched state.

    This method can be called to remove the 'ng-touched' class and set the form controls to their untouched state (ng-untouched class).

    Setting a form controls back to their untouched state is often useful when setting the form back to its pristine state.

- $setSubmitted();

    Sets the form to its submitted state.

#### Properties

- $pristine
    boolean 	

    True if user has not interacted with the form yet.

- $dirty
    boolean 	

    True if user has already interacted with the form.

- $valid
    boolean 	

    True if all of the containing forms and controls are valid.

- $invalid
    boolean 	

    True if at least one containing control or form is invalid.

- $submitted
    boolean 	

    True if user has submitted the form even if its invalid.

- $error
    Object 	

    Is an object hash, containing references to controls or forms with failing validators, where:

        keys are validation tokens (error names),

        values are arrays of controls or forms that have a failing validator for given error name.

        Built-in validation tokens:

```
        email
        max
        maxlength
        min
        minlength
        number
        pattern
        required
        url
        date
        datetimelocal
        time
        week
        month
```

#### Example

`index.html`

```html
<script>
  angular.module('formExample', [])
    .controller('FormController', ['$scope', function($scope) {
      $scope.userType = 'guest';
    }]);
</script>
<style>
 .my-form {
   -webkit-transition:all linear 0.5s;
   transition:all linear 0.5s;
   background: transparent;
 }
 .my-form.ng-invalid {
   background: red;
 }
</style>
<form name="myForm" ng-controller="FormController" class="my-form">
  userType: <input name="input" ng-model="userType" required>
  <span class="error" ng-show="myForm.input.$error.required">Required!</span><br>
  <code>userType = {{userType}}</code><br>
  <code>myForm.input.$valid = {{myForm.input.$valid}}</code><br>
  <code>myForm.input.$error = {{myForm.input.$error}}</code><br>
  <code>myForm.$valid = {{myForm.$valid}}</code><br>
  <code>myForm.$error.required = {{!!myForm.$error.required}}</code><br>
 </form>

```

### NgModelController

<div class="grid-right">
          <div ng-show="loading" id="loading" class="ng-hide">Loading...</div>
          <!-- ngInclude: partialPath --><div autoscroll="" ng-include="partialPath" ng-hide="loading" class="ng-scope" style=""><a class="improve-docs btn btn-primary ng-scope" href="https://github.com/angular/angular.js/edit/v1.4.x/src/ng/directive/ngModel.js?message=docs(ngModel.NgModelController)%3A%20describe%20your%20change...#L22"><i class="glyphicon glyphicon-edit">&nbsp;</i>Improve this Doc</a>



<a class="view-source pull-right btn btn-primary ng-scope" href="https://github.com/angular/angular.js/tree/v1.4.0-rc.2/src/ng/directive/ngModel.js#L22">
  <i class="glyphicon glyphicon-zoom-in">&nbsp;</i>View Source
</a>


<header class="api-profile-header ng-scope">
  <h1 class="api-profile-header-heading">ngModel.NgModelController</h1>
  <ol class="api-profile-header-structure naked-list step-list">
    
    <li>
      - type in module <a href="api/ng">ng</a>
    </li>
  </ol>
</header>



<div class="api-profile-description ng-scope">
  <p><code><span class="typ">NgModelController</span></code> provides API for the <a href="api/ng/directive/ngModel"><code><span class="pln">ngModel</span></code></a> directive.
The controller contains services for data-binding, validation, CSS updates, and value formatting
and parsing. It purposefully does not contain any logic which deals with DOM rendering or
listening to DOM events.
Such DOM related logic should be provided by other directives which make use of
<code><span class="typ">NgModelController</span></code> for data-binding to control elements.
Angular provides this DOM logic for most <a href="api/ng/directive/input"><code><span class="pln">input</span></code></a> elements.
At the end of this page you can find a <a href="api/ng/type/ngModel.NgModelController#custom-control-example">custom control example</a> that uses <code><span class="pln">ngModelController</span></code> to bind to <code><span class="pln">contenteditable</span></code> elements.</p>

</div>




<div class="ng-scope">
  

    

  

  
<h2>Methods</h2>
<ul class="methods">
  <li id="$render">
    <h3><p><code><span class="pln">$render</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Called when the view needs to be updated. It is expected that the user of the ng-model
directive will implement this method.</p>
<p>The <code><span class="pln">$render</span><span class="pun">()</span></code> method is invoked in the following situations:</p>
<ul>
<li><code><span class="pln">$rollbackViewValue</span><span class="pun">()</span></code> is called.  If we are rolling back the view value to the last
committed value then <code><span class="pln">$render</span><span class="pun">()</span></code> is called to update the input control.</li>
<li>The value referenced by <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span></code> is changed programmatically and both the <code><span class="pln">$modelValue</span></code> and
the <code><span class="pln">$viewValue</span></code> are different from last time.</li>
</ul>
<p>Since <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span></code> does not do a deep watch, <code><span class="pln">$render</span><span class="pun">()</span></code> is only invoked if the values of
<code><span class="pln">$modelValue</span></code> and <code><span class="pln">$viewValue</span></code> are actually different from their previous value. If <code><span class="pln">$modelValue</span></code>
or <code><span class="pln">$viewValue</span></code> are objects (rather than a string or number) then <code><span class="pln">$render</span><span class="pun">()</span></code> will not be
invoked if you only change a property on the objects.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$isEmpty">
    <h3><p><code><span class="pln">$isEmpty</span><span class="pun">(</span><span class="pln">value</span><span class="pun">);</span></code></p>

</h3>
    <div><p>This is called when we need to determine if the value of an input is empty.</p>
<p>For instance, the required directive does this to work out if the input has data or not.</p>
<p>The default <code><span class="pln">$isEmpty</span></code> function checks whether the value is <code><span class="kwd">undefined</span></code>, <code><span class="str">''</span></code>, <code><span class="kwd">null</span></code> or <code><span class="kwd">NaN</span></code>.</p>
<p>You can override this for input directives whose concept of being empty is different from the
default. The <code><span class="pln">checkboxInputType</span></code> directive does this because in its case a value of <code><span class="kwd">false</span></code>
implies empty.</p>
</div>

    
    <h4>Parameters</h4>
    
<table class="variables-matrix input-arguments">
  <thead>
    <tr>
      <th>Param</th>
      <th>Type</th>
      <th>Details</th>
    </tr>
  </thead>
  <tbody>
    
    <tr>
      <td>
        value
        
        
      </td>
      <td>
        <a class="label type-hint type-hint-object" href="">*</a>
      </td>
      <td>
        <p>The value of the input to check for emptiness.</p>

        
      </td>
    </tr>
    
  </tbody>
</table>

    

    
    
    
    <h4>Returns</h4>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if <code><span class="pln">value</span></code> is "empty".</p>
</td>
  </tr>
</tbody></table>
    

  </li>
  
  <li id="$setValidity">
    <h3><p><code><span class="pln">$setValidity</span><span class="pun">(</span><span class="pln">validationErrorKey</span><span class="pun">,</span><span class="pln"> isValid</span><span class="pun">);</span></code></p>

</h3>
    <div><p>Change the validity state, and notify the form.</p>
<p>This method can be called within $parsers/$formatters or a custom validation implementation.
However, in most cases it should be sufficient to use the <code><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$validators</span></code> and
<code><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$asyncValidators</span></code> collections which will call <code><span class="pln">$setValidity</span></code> automatically.</p>
</div>

    
    <h4>Parameters</h4>
    
<table class="variables-matrix input-arguments">
  <thead>
    <tr>
      <th>Param</th>
      <th>Type</th>
      <th>Details</th>
    </tr>
  </thead>
  <tbody>
    
    <tr>
      <td>
        validationErrorKey
        
        
      </td>
      <td>
        <a class="label type-hint type-hint-string" href="">string</a>
      </td>
      <td>
        <p>Name of the validator. The <code><span class="pln">validationErrorKey</span></code> will be assigned
       to either <code><span class="pln">$error</span><span class="pun">[</span><span class="pln">validationErrorKey</span><span class="pun">]</span></code> or <code><span class="pln">$pending</span><span class="pun">[</span><span class="pln">validationErrorKey</span><span class="pun">]</span></code>
       (for unfulfilled <code><span class="pln">$asyncValidators</span></code>), so that it is available for data-binding.
       The <code><span class="pln">validationErrorKey</span></code> should be in camelCase and will get converted into dash-case
       for class name. Example: <code><span class="pln">myError</span></code> will result in <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">valid</span><span class="pun">-</span><span class="kwd">my</span><span class="pun">-</span><span class="pln">error</span></code> and <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">invalid</span><span class="pun">-</span><span class="kwd">my</span><span class="pun">-</span><span class="pln">error</span></code>
       class and can be bound to as  <code><span class="pun">{{</span><span class="pln">someForm</span><span class="pun">.</span><span class="pln">someControl</span><span class="pun">.</span><span class="pln">$error</span><span class="pun">.</span><span class="pln">myError</span><span class="pun">}}</span></code> .</p>

        
      </td>
    </tr>
    
    <tr>
      <td>
        isValid
        
        
      </td>
      <td>
        <a class="label type-hint type-hint-boolean" href="">boolean</a>
      </td>
      <td>
        <p>Whether the current state is valid (true), invalid (false), pending (undefined),
                         or skipped (null). Pending is used for unfulfilled <code><span class="pln">$asyncValidators</span></code>.
                         Skipped is used by Angular when validators do not run because of parse errors and
                         when <code><span class="pln">$asyncValidators</span></code> do not run because any of the <code><span class="pln">$validators</span></code> failed.</p>

        
      </td>
    </tr>
    
  </tbody>
</table>

    

    
    
    

  </li>
  
  <li id="$setPristine">
    <h3><p><code><span class="pln">$setPristine</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Sets the control to its pristine state.</p>
<p>This method can be called to remove the <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">dirty</span></code> class and set the control to its pristine
state (<code><span class="pln">ng</span><span class="pun">-</span><span class="pln">pristine</span></code> class). A model is considered to be pristine when the control
has not been changed from when first compiled.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$setDirty">
    <h3><p><code><span class="pln">$setDirty</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Sets the control to its dirty state.</p>
<p>This method can be called to remove the <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">pristine</span></code> class and set the control to its dirty
state (<code><span class="pln">ng</span><span class="pun">-</span><span class="pln">dirty</span></code> class). A model is considered to be dirty when the control has been changed
from when first compiled.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$setUntouched">
    <h3><p><code><span class="pln">$setUntouched</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Sets the control to its untouched state.</p>
<p>This method can be called to remove the <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">touched</span></code> class and set the control to its
untouched state (<code><span class="pln">ng</span><span class="pun">-</span><span class="pln">untouched</span></code> class). Upon compilation, a model is set as untouched
by default, however this function can be used to restore that state if the model has
already been touched by the user.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$setTouched">
    <h3><p><code><span class="pln">$setTouched</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Sets the control to its touched state.</p>
<p>This method can be called to remove the <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">untouched</span></code> class and set the control to its
touched state (<code><span class="pln">ng</span><span class="pun">-</span><span class="pln">touched</span></code> class). A model is considered to be touched when the user has
first focused the control element and then shifted focus away from the control (blur event).</p>
</div>

    

    
    
    

  </li>
  
  <li id="$rollbackViewValue">
    <h3><p><code><span class="pln">$rollbackViewValue</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Cancel an update and reset the input element's value to prevent an update to the <code><span class="pln">$modelValue</span></code>,
which may be caused by a pending debounced event or because the input is waiting for a some
future event.</p>
<p>If you have an input that uses <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span><span class="pun">-</span><span class="pln">options</span></code> to set up debounced events or events such
as blur you can have a situation where there is a period when the <code><span class="pln">$viewValue</span></code>
is out of synch with the ngModel's <code><span class="pln">$modelValue</span></code>.</p>
<p>In this case, you can run into difficulties if you try to update the ngModel's <code><span class="pln">$modelValue</span></code>
programmatically before these debounced/future events have resolved/occurred, because Angular's
dirty checking mechanism is not able to tell whether the model has actually changed or not.</p>
<p>The <code><span class="pln">$rollbackViewValue</span><span class="pun">()</span></code> method should be called before programmatically changing the model of an
input which may have such events pending. This is important in order to make sure that the
input field will be updated with the new model value and any pending operations are cancelled.</p>
<p>

</p><div>
  <a class="btn pull-right" ng-click="openPlunkr('examples/example-ng-model-cancel-update', $event)">
    <i class="glyphicon glyphicon-edit">&nbsp;</i>
    Edit in Plunker</a>

  <div module="cancel-update-example" name="ng-model-cancel-update" path="examples/example-ng-model-cancel-update" class="runnable-example ng-scope"><!-- ngIf: tabs --><nav ng-if="tabs" class="runnable-example-tabs ng-scope">  <!-- ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope active" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    app.js  </a><!-- end ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    index.html  </a><!-- end ngRepeat: tab in tabs track by $index --></nav><!-- end ngIf: tabs -->

  
    <div type="js" language="js" name="app.js" class="runnable-example-file" style="display: block;">
      <pre><code><span class="pln">angular</span><span class="pun">.</span><span class="kwd">module</span><span class="pun">(</span><span class="str">'cancel-update-example'</span><span class="pun">,</span><span class="pln"> </span><span class="pun">[])</span><span class="pln">

</span><span class="pun">.</span><span class="pln">controller</span><span class="pun">(</span><span class="str">'CancelUpdateController'</span><span class="pun">,</span><span class="pln"> </span><span class="pun">[</span><span class="str">'$scope'</span><span class="pun">,</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">$scope</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  $scope</span><span class="pun">.</span><span class="pln">resetWithCancel </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">e</span><span class="pun">.</span><span class="pln">keyCode </span><span class="pun">==</span><span class="pln"> </span><span class="lit">27</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
      $scope</span><span class="pun">.</span><span class="pln">myForm</span><span class="pun">.</span><span class="pln">myInput1</span><span class="pun">.</span><span class="pln">$rollbackViewValue</span><span class="pun">();</span><span class="pln">
      $scope</span><span class="pun">.</span><span class="pln">myValue </span><span class="pun">=</span><span class="pln"> </span><span class="str">''</span><span class="pun">;</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
  </span><span class="pun">};</span><span class="pln">
  $scope</span><span class="pun">.</span><span class="pln">resetWithoutCancel </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">e</span><span class="pun">.</span><span class="pln">keyCode </span><span class="pun">==</span><span class="pln"> </span><span class="lit">27</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
      $scope</span><span class="pun">.</span><span class="pln">myValue </span><span class="pun">=</span><span class="pln"> </span><span class="str">''</span><span class="pun">;</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
  </span><span class="pun">};</span><span class="pln">
</span><span class="pun">}]);</span></code></pre>
    </div>
  
    <div type="html" language="html" name="index.html" class="runnable-example-file" style="display: none;">
      <pre><code><span class="tag">&lt;div</span><span class="pln"> </span><span class="atn">ng-controller</span><span class="pun">=</span><span class="atv">"CancelUpdateController"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;p&gt;</span><span class="pln">Try typing something in each input.  See that the model only updates when you
     blur off the input.
   </span><span class="tag">&lt;/p&gt;</span><span class="pln">
   </span><span class="tag">&lt;p&gt;</span><span class="pln">Now see what happens if you start typing then press the Escape key</span><span class="tag">&lt;/p&gt;</span><span class="pln">

  </span><span class="tag">&lt;form</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"myForm"</span><span class="pln"> </span><span class="atn">ng-model-options</span><span class="pun">=</span><span class="atv">"{ updateOn: 'blur' }"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;p</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"inputDescription1"</span><span class="tag">&gt;</span><span class="pln">With $rollbackViewValue()</span><span class="tag">&lt;/p&gt;</span><span class="pln">
    </span><span class="tag">&lt;input</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"myInput1"</span><span class="pln"> </span><span class="atn">aria-describedby</span><span class="pun">=</span><span class="atv">"inputDescription1"</span><span class="pln"> </span><span class="atn">ng-model</span><span class="pun">=</span><span class="atv">"myValue"</span><span class="pln">
           </span><span class="atn">ng-keydown</span><span class="pun">=</span><span class="atv">"resetWithCancel($event)"</span><span class="tag">&gt;&lt;br/&gt;</span><span class="pln">
    myValue: "{{ myValue }}"

    </span><span class="tag">&lt;p</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"inputDescription2"</span><span class="tag">&gt;</span><span class="pln">Without $rollbackViewValue()</span><span class="tag">&lt;/p&gt;</span><span class="pln">
    </span><span class="tag">&lt;input</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"myInput2"</span><span class="pln"> </span><span class="atn">aria-describedby</span><span class="pun">=</span><span class="atv">"inputDescription2"</span><span class="pln"> </span><span class="atn">ng-model</span><span class="pun">=</span><span class="atv">"myValue"</span><span class="pln">
           </span><span class="atn">ng-keydown</span><span class="pun">=</span><span class="atv">"resetWithoutCancel($event)"</span><span class="tag">&gt;&lt;br/&gt;</span><span class="pln">
    myValue: "{{ myValue }}"
  </span><span class="tag">&lt;/form&gt;</span><span class="pln">
</span><span class="tag">&lt;/div&gt;</span></code></pre>
    </div>
  

    <iframe name="example-ng-model-cancel-update" src="examples/example-ng-model-cancel-update/index.html" class="runnable-example-frame"></iframe>
  </div>
</div>


<p></p>
</div>

    

    
    
    

  </li>
  
  <li id="$validate">
    <h3><p><code><span class="pln">$validate</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Runs each of the registered validators (first synchronous validators and then
asynchronous validators).
If the validity changes to invalid, the model will be set to <code><span class="kwd">undefined</span></code>,
unless <a href="api/ng/directive/ngModelOptions"><code><span class="pln">ngModelOptions</span><span class="pun">.</span><span class="pln">allowInvalid</span></code></a> is <code><span class="kwd">true</span></code>.
If the validity changes to valid, it will set the model to the last available valid
<code><span class="pln">$modelValue</span></code>, i.e. either the last parsed value or the last value set from the scope.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$commitViewValue">
    <h3><p><code><span class="pln">$commitViewValue</span><span class="pun">();</span></code></p>

</h3>
    <div><p>Commit a pending update to the <code><span class="pln">$modelValue</span></code>.</p>
<p>Updates may be pending by a debounced event or because the input is waiting for a some future
event defined in <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span><span class="pun">-</span><span class="pln">options</span></code>. this method is rarely needed as <code><span class="typ">NgModelController</span></code>
usually handles calling this in response to input events.</p>
</div>

    

    
    
    

  </li>
  
  <li id="$setViewValue">
    <h3><p><code><span class="pln">$setViewValue</span><span class="pun">(</span><span class="pln">value</span><span class="pun">,</span><span class="pln"> trigger</span><span class="pun">);</span></code></p>

</h3>
    <div><p>Update the view value.</p>
<p>This method should be called when an input directive want to change the view value; typically,
this is done from within a DOM event handler.</p>
<p>For example <a href="api/ng/directive/input">input</a> calls it when the value of the input changes and
<a href="api/ng/directive/select">select</a> calls it when an option is selected.</p>
<p>If the new <code><span class="pln">value</span></code> is an object (rather than a string or a number), we should make a copy of the
object before passing it to <code><span class="pln">$setViewValue</span></code>.  This is because <code><span class="pln">ngModel</span></code> does not perform a deep
watch of objects, it only looks for a change of identity. If you only change the property of
the object then ngModel will not realise that the object has changed and will not invoke the
<code><span class="pln">$parsers</span></code> and <code><span class="pln">$validators</span></code> pipelines.</p>
<p>For this reason, you should not change properties of the copy once it has been passed to
<code><span class="pln">$setViewValue</span></code>. Otherwise you may cause the model value on the scope to change incorrectly.</p>
<p>When this method is called, the new <code><span class="pln">value</span></code> will be staged for committing through the <code><span class="pln">$parsers</span></code>
and <code><span class="pln">$validators</span></code> pipelines. If there are no special <a href="api/ng/directive/ngModelOptions"><code><span class="pln">ngModelOptions</span></code></a> specified then the staged
value sent directly for processing, finally to be applied to <code><span class="pln">$modelValue</span></code> and then the
<strong>expression</strong> specified in the <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span></code> attribute.</p>
<p>Lastly, all the registered change listeners, in the <code><span class="pln">$viewChangeListeners</span></code> list, are called.</p>
<p>In case the <a href="api/ng/directive/ngModelOptions">ngModelOptions</a> directive is used with <code><span class="pln">updateOn</span></code>
and the <code><span class="kwd">default</span></code> trigger is not listed, all those actions will remain pending until one of the
<code><span class="pln">updateOn</span></code> events is triggered on the DOM element.
All these actions will be debounced if the <a href="api/ng/directive/ngModelOptions">ngModelOptions</a>
directive is used with a custom debounce for this particular event.</p>
<p>Note that calling this function does not trigger a <code><span class="pln">$digest</span></code>.</p>
</div>

    
    <h4>Parameters</h4>
    
<table class="variables-matrix input-arguments">
  <thead>
    <tr>
      <th>Param</th>
      <th>Type</th>
      <th>Details</th>
    </tr>
  </thead>
  <tbody>
    
    <tr>
      <td>
        value
        
        
      </td>
      <td>
        <a class="label type-hint type-hint-string" href="">string</a>
      </td>
      <td>
        <p>Value from the view.</p>

        
      </td>
    </tr>
    
    <tr>
      <td>
        trigger
        
        
      </td>
      <td>
        <a class="label type-hint type-hint-string" href="">string</a>
      </td>
      <td>
        <p>Event that triggered the update.</p>

        
      </td>
    </tr>
    
  </tbody>
</table>

    

    
    
    

  </li>
  </ul>
  
  
<h2>Properties</h2>
<ul class="properties">
  <li id="$viewValue">
    <h3><code><span class="pln">$viewValue</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-string" href="">string</a></td>
    <td><p>Actual string value in the view.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$modelValue">
    <h3><code><span class="pln">$modelValue</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-object" href="">*</a></td>
    <td><p>The value in the model that the control is bound to.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$parsers">
    <h3><code><span class="pln">$parsers</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-array" href="">Array.&lt;Function&gt;</a></td>
    <td><p>Array of functions to execute, as a pipeline, whenever
       the control reads value from the DOM. The functions are called in array order, each passing
       its return value through to the next. The last return value is forwarded to the
       <a href="api/ng/type/ngModel.NgModelController#$validators"><code><span class="pln">$validators</span></code></a> collection.</p>
<p>Parsers are used to sanitize / convert the <a href="api/ng/type/ngModel.NgModelController#$viewValue"><code><span class="pln">$viewValue</span></code></a>.</p>
<p>Returning <code><span class="kwd">undefined</span></code> from a parser means a parse error occurred. In that case,
no <a href="api/ng/type/ngModel.NgModelController#$validators"><code><span class="pln">$validators</span></code></a> will run and the <code><span class="pln">ngModel</span></code>
will be set to <code><span class="kwd">undefined</span></code> unless <a href="api/ng/directive/ngModelOptions"><code><span class="pln">ngModelOptions</span><span class="pun">.</span><span class="pln">allowInvalid</span></code></a>
is set to <code><span class="kwd">true</span></code>. The parse error is stored in <code><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$error</span><span class="pun">.</span><span class="pln">parse</span></code>.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$formatters">
    <h3><code><span class="pln">$formatters</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-array" href="">Array.&lt;Function&gt;</a></td>
    <td><p>Array of functions to execute, as a pipeline, whenever
       the model value changes. The functions are called in reverse array order, each passing the value through to the
       next. The last return value is used as the actual DOM value.
       Used to format / convert values for display in the control.</p>
<pre><code class="lang-js"><span class="kwd">function</span><span class="pln"> formatter</span><span class="pun">(</span><span class="pln">value</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">value</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">return</span><span class="pln"> value</span><span class="pun">.</span><span class="pln">toUpperCase</span><span class="pun">();</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span><span class="pln">
ngModel</span><span class="pun">.</span><span class="pln">$formatters</span><span class="pun">.</span><span class="pln">push</span><span class="pun">(</span><span class="pln">formatter</span><span class="pun">);</span></code></pre>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$validators">
    <h3><code><span class="pln">$validators</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-object" href="">Object.&lt;string, function&gt;</a></td>
    <td><p>A collection of validators that are applied
     whenever the model value changes. The key value within the object refers to the name of the
     validator while the function refers to the validation operation. The validation operation is
     provided with the model value as an argument and must return a true or false value depending
     on the response of that validation.</p>
<pre><code class="lang-js"><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$validators</span><span class="pun">.</span><span class="pln">validCharacters </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">modelValue</span><span class="pun">,</span><span class="pln"> viewValue</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">var</span><span class="pln"> value </span><span class="pun">=</span><span class="pln"> modelValue </span><span class="pun">||</span><span class="pln"> viewValue</span><span class="pun">;</span><span class="pln">
  </span><span class="kwd">return</span><span class="pln"> </span><span class="str">/[0-9]+/</span><span class="pun">.</span><span class="pln">test</span><span class="pun">(</span><span class="pln">value</span><span class="pun">)</span><span class="pln"> </span><span class="pun">&amp;&amp;</span><span class="pln">
         </span><span class="str">/[a-z]+/</span><span class="pun">.</span><span class="pln">test</span><span class="pun">(</span><span class="pln">value</span><span class="pun">)</span><span class="pln"> </span><span class="pun">&amp;&amp;</span><span class="pln">
         </span><span class="str">/[A-Z]+/</span><span class="pun">.</span><span class="pln">test</span><span class="pun">(</span><span class="pln">value</span><span class="pun">)</span><span class="pln"> </span><span class="pun">&amp;&amp;</span><span class="pln">
         </span><span class="str">/\W+/</span><span class="pun">.</span><span class="pln">test</span><span class="pun">(</span><span class="pln">value</span><span class="pun">);</span><span class="pln">
</span><span class="pun">};</span></code></pre>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$asyncValidators">
    <h3><code><span class="pln">$asyncValidators</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-object" href="">Object.&lt;string, function&gt;</a></td>
    <td><p>A collection of validations that are expected to
     perform an asynchronous validation (e.g. a HTTP request). The validation function that is provided
     is expected to return a promise when it is run during the model validation process. Once the promise
     is delivered then the validation status will be set to true when fulfilled and false when rejected.
     When the asynchronous validators are triggered, each of the validators will run in parallel and the model
     value will only be updated once all validators have been fulfilled. As long as an asynchronous validator
     is unfulfilled, its key will be added to the controllers <code><span class="pln">$pending</span></code> property. Also, all asynchronous validators
     will only run once all synchronous validators have passed.</p>
<p>Please note that if $http is used then it is important that the server returns a success HTTP response code
in order to fulfill the validation and a status level of <code><span class="lit">4xx</span></code> in order to reject the validation.</p>
<pre><code class="lang-js"><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$asyncValidators</span><span class="pun">.</span><span class="pln">uniqueUsername </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">modelValue</span><span class="pun">,</span><span class="pln"> viewValue</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">var</span><span class="pln"> value </span><span class="pun">=</span><span class="pln"> modelValue </span><span class="pun">||</span><span class="pln"> viewValue</span><span class="pun">;</span><span class="pln">

  </span><span class="com">// Lookup user by username</span><span class="pln">
  </span><span class="kwd">return</span><span class="pln"> $http</span><span class="pun">.</span><span class="kwd">get</span><span class="pun">(</span><span class="str">'/api/users/'</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> value</span><span class="pun">).</span><span class="pln">
     then</span><span class="pun">(</span><span class="kwd">function</span><span class="pln"> resolved</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
       </span><span class="com">//username exists, this means validation fails</span><span class="pln">
       </span><span class="kwd">return</span><span class="pln"> $q</span><span class="pun">.</span><span class="pln">reject</span><span class="pun">(</span><span class="str">'exists'</span><span class="pun">);</span><span class="pln">
     </span><span class="pun">},</span><span class="pln"> </span><span class="kwd">function</span><span class="pln"> rejected</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
       </span><span class="com">//username does not exist, therefore this validation passes</span><span class="pln">
       </span><span class="kwd">return</span><span class="pln"> </span><span class="kwd">true</span><span class="pun">;</span><span class="pln">
     </span><span class="pun">});</span><span class="pln">
</span><span class="pun">};</span></code></pre>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$viewChangeListeners">
    <h3><code><span class="pln">$viewChangeListeners</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-array" href="">Array.&lt;Function&gt;</a></td>
    <td><p>Array of functions to execute whenever the
    view value has changed. It is called with no arguments, and its return value is ignored.
    This can be used in place of additional $watches against the model value.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$error">
    <h3><code><span class="pln">$error</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-object" href="">Object</a></td>
    <td><p>An object hash with all failing validator ids as keys.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$pending">
    <h3><code><span class="pln">$pending</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-object" href="">Object</a></td>
    <td><p>An object hash with all pending validator ids as keys.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$untouched">
    <h3><code><span class="pln">$untouched</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if control has not lost focus yet.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$touched">
    <h3><code><span class="pln">$touched</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if control has lost focus.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$pristine">
    <h3><code><span class="pln">$pristine</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if user has not interacted with the control yet.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$dirty">
    <h3><code><span class="pln">$dirty</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if user has already interacted with the control.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$valid">
    <h3><code><span class="pln">$valid</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if there is no error.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$invalid">
    <h3><code><span class="pln">$invalid</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-boolean" href="">boolean</a></td>
    <td><p>True if at least one error on the control.</p>
</td>
  </tr>
</tbody></table>
  </li>
  
  <li id="$name">
    <h3><code><span class="pln">$name</span></code></h3>
    <table class="variables-matrix return-arguments">
  <tbody><tr>
    <td><a class="label type-hint type-hint-string" href="">string</a></td>
    <td><p>The name attribute of the control.</p>
</td>
  </tr>
</tbody></table>
  </li>
  </ul>



  
  <h2 id="example">Example</h2><h3 id="custom-control-example">Custom Control Example</h3>
<p>This example shows how to use <code><span class="typ">NgModelController</span></code> with a custom control to achieve
data-binding. Notice how different directives (<code><span class="pln">contenteditable</span></code>, <code><span class="pln">ng</span><span class="pun">-</span><span class="pln">model</span></code>, and <code><span class="pln">required</span></code>)
collaborate together to achieve the desired result.</p>
<p><code><span class="pln">contenteditable</span></code> is an HTML5 attribute, which tells the browser to let the element
contents be edited in place by the user.</p>
<p>We are using the <a href="api/ng/service/$sce">$sce</a> service here and include the <a href="api/ngSanitize">$sanitize</a>
module to automatically remove "bad" content like inline event listener (e.g. <code><span class="tag">&lt;span</span><span class="pln"> </span><span class="atn">onclick</span><span class="pun">=</span><span class="atv">"</span><span class="pun">...</span><span class="atv">"</span><span class="tag">&gt;</span></code>).
However, as we are using <code><span class="pln">$sce</span></code> the model can still decide to provide unsafe content if it marks
that content using the <code><span class="pln">$sce</span></code> service.</p>
<p>

</p><div>
  <a class="btn pull-right" ng-click="openPlunkr('examples/example-NgModelController', $event)">
    <i class="glyphicon glyphicon-edit">&nbsp;</i>
    Edit in Plunker</a>

  <div deps="angular-sanitize.js" module="customControl" name="NgModelController" path="examples/example-NgModelController" class="runnable-example ng-scope"><!-- ngIf: tabs --><nav ng-if="tabs" class="runnable-example-tabs ng-scope">  <!-- ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope active" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    style.css  </a><!-- end ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    script.js  </a><!-- end ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    index.html  </a><!-- end ngRepeat: tab in tabs track by $index --><a ng-click="setTab($index)" class="btn ng-binding ng-scope" href="" ng-repeat="tab in tabs track by $index" ng-class="{active:$index==activeTabIndex}">    protractor.js  </a><!-- end ngRepeat: tab in tabs track by $index --></nav><!-- end ngIf: tabs -->

  
    <div type="css" language="css" name="style.css" class="runnable-example-file" style="display: block;">
      <pre><code><span class="pun">[</span><span class="pln">contenteditable</span><span class="pun">]</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  border</span><span class="pun">:</span><span class="pln"> </span><span class="lit">1px</span><span class="pln"> solid black</span><span class="pun">;</span><span class="pln">
  background</span><span class="pun">-</span><span class="pln">color</span><span class="pun">:</span><span class="pln"> white</span><span class="pun">;</span><span class="pln">
  min</span><span class="pun">-</span><span class="pln">height</span><span class="pun">:</span><span class="pln"> </span><span class="lit">20px</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span><span class="pln">

</span><span class="pun">.</span><span class="pln">ng</span><span class="pun">-</span><span class="pln">invalid </span><span class="pun">{</span><span class="pln">
  border</span><span class="pun">:</span><span class="pln"> </span><span class="lit">1px</span><span class="pln"> solid red</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span></code></pre>
    </div>
  
    <div type="js" language="js" name="script.js" class="runnable-example-file" style="display: none;">
      <pre><code><span class="pln">angular</span><span class="pun">.</span><span class="kwd">module</span><span class="pun">(</span><span class="str">'customControl'</span><span class="pun">,</span><span class="pln"> </span><span class="pun">[</span><span class="str">'ngSanitize'</span><span class="pun">]).</span><span class="pln">
directive</span><span class="pun">(</span><span class="str">'contenteditable'</span><span class="pun">,</span><span class="pln"> </span><span class="pun">[</span><span class="str">'$sce'</span><span class="pun">,</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">$sce</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">return</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    restrict</span><span class="pun">:</span><span class="pln"> </span><span class="str">'A'</span><span class="pun">,</span><span class="pln"> </span><span class="com">// only activate on element attribute</span><span class="pln">
    </span><span class="kwd">require</span><span class="pun">:</span><span class="pln"> </span><span class="str">'?ngModel'</span><span class="pun">,</span><span class="pln"> </span><span class="com">// get a hold of NgModelController</span><span class="pln">
    link</span><span class="pun">:</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">scope</span><span class="pun">,</span><span class="pln"> element</span><span class="pun">,</span><span class="pln"> attrs</span><span class="pun">,</span><span class="pln"> ngModel</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
      </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(!</span><span class="pln">ngModel</span><span class="pun">)</span><span class="pln"> </span><span class="kwd">return</span><span class="pun">;</span><span class="pln"> </span><span class="com">// do nothing if no ng-model</span><span class="pln">

      </span><span class="com">// Specify how UI should be updated</span><span class="pln">
      ngModel</span><span class="pun">.</span><span class="pln">$render </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        element</span><span class="pun">.</span><span class="pln">html</span><span class="pun">(</span><span class="pln">$sce</span><span class="pun">.</span><span class="pln">getTrustedHtml</span><span class="pun">(</span><span class="pln">ngModel</span><span class="pun">.</span><span class="pln">$viewValue </span><span class="pun">||</span><span class="pln"> </span><span class="str">''</span><span class="pun">));</span><span class="pln">
      </span><span class="pun">};</span><span class="pln">

      </span><span class="com">// Listen for change events to enable binding</span><span class="pln">
      element</span><span class="pun">.</span><span class="pln">on</span><span class="pun">(</span><span class="str">'blur keyup change'</span><span class="pun">,</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        scope</span><span class="pun">.</span><span class="pln">$evalAsync</span><span class="pun">(</span><span class="pln">read</span><span class="pun">);</span><span class="pln">
      </span><span class="pun">});</span><span class="pln">
      read</span><span class="pun">();</span><span class="pln"> </span><span class="com">// initialize</span><span class="pln">

      </span><span class="com">// Write data to the model</span><span class="pln">
      </span><span class="kwd">function</span><span class="pln"> read</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        </span><span class="kwd">var</span><span class="pln"> html </span><span class="pun">=</span><span class="pln"> element</span><span class="pun">.</span><span class="pln">html</span><span class="pun">();</span><span class="pln">
        </span><span class="com">// When we clear the content editable the browser leaves a &lt;br&gt; behind</span><span class="pln">
        </span><span class="com">// If strip-br attribute is provided then we strip this out</span><span class="pln">
        </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln"> attrs</span><span class="pun">.</span><span class="pln">stripBr </span><span class="pun">&amp;&amp;</span><span class="pln"> html </span><span class="pun">==</span><span class="pln"> </span><span class="str">'&lt;br&gt;'</span><span class="pln"> </span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
          html </span><span class="pun">=</span><span class="pln"> </span><span class="str">''</span><span class="pun">;</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
        ngModel</span><span class="pun">.</span><span class="pln">$setViewValue</span><span class="pun">(</span><span class="pln">html</span><span class="pun">);</span><span class="pln">
      </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
  </span><span class="pun">};</span><span class="pln">
</span><span class="pun">}]);</span></code></pre>
    </div>
  
    <div type="html" language="html" name="index.html" class="runnable-example-file" style="display: none;">
      <pre><code><span class="tag">&lt;form</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"myForm"</span><span class="tag">&gt;</span><span class="pln">
 </span><span class="tag">&lt;div</span><span class="pln"> </span><span class="atn">contenteditable</span><span class="pln">
      </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"myWidget"</span><span class="pln"> </span><span class="atn">ng-model</span><span class="pun">=</span><span class="atv">"userContent"</span><span class="pln">
      </span><span class="atn">strip-br</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
      </span><span class="atn">required</span><span class="tag">&gt;</span><span class="pln">Change me!</span><span class="tag">&lt;/div&gt;</span><span class="pln">
  </span><span class="tag">&lt;span</span><span class="pln"> </span><span class="atn">ng-show</span><span class="pun">=</span><span class="atv">"myForm.myWidget.$error.required"</span><span class="tag">&gt;</span><span class="pln">Required!</span><span class="tag">&lt;/span&gt;</span><span class="pln">
 </span><span class="tag">&lt;hr&gt;</span><span class="pln">
 </span><span class="tag">&lt;textarea</span><span class="pln"> </span><span class="atn">ng-model</span><span class="pun">=</span><span class="atv">"userContent"</span><span class="pln"> </span><span class="atn">aria-label</span><span class="pun">=</span><span class="atv">"Dynamic textarea"</span><span class="tag">&gt;&lt;/textarea&gt;</span><span class="pln">
</span><span class="tag">&lt;/form&gt;</span></code></pre>
    </div>
  
    <div language="js" type="protractor" name="protractor.js" class="runnable-example-file" style="display: none;">
      <pre><code><span class="pln">it</span><span class="pun">(</span><span class="str">'should data-bind and become invalid'</span><span class="pun">,</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">browser</span><span class="pun">.</span><span class="kwd">params</span><span class="pun">.</span><span class="pln">browser </span><span class="pun">==</span><span class="pln"> </span><span class="str">'safari'</span><span class="pln"> </span><span class="pun">||</span><span class="pln"> browser</span><span class="pun">.</span><span class="kwd">params</span><span class="pun">.</span><span class="pln">browser </span><span class="pun">==</span><span class="pln"> </span><span class="str">'firefox'</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="com">// SafariDriver can't handle contenteditable</span><span class="pln">
    </span><span class="com">// and Firefox driver can't clear contenteditables very well</span><span class="pln">
    </span><span class="kwd">return</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">var</span><span class="pln"> contentEditable </span><span class="pun">=</span><span class="pln"> element</span><span class="pun">(</span><span class="kwd">by</span><span class="pun">.</span><span class="pln">css</span><span class="pun">(</span><span class="str">'[contenteditable]'</span><span class="pun">));</span><span class="pln">
  </span><span class="kwd">var</span><span class="pln"> content </span><span class="pun">=</span><span class="pln"> </span><span class="str">'Change me!'</span><span class="pun">;</span><span class="pln">

  expect</span><span class="pun">(</span><span class="pln">contentEditable</span><span class="pun">.</span><span class="pln">getText</span><span class="pun">()).</span><span class="pln">toEqual</span><span class="pun">(</span><span class="pln">content</span><span class="pun">);</span><span class="pln">

  contentEditable</span><span class="pun">.</span><span class="pln">clear</span><span class="pun">();</span><span class="pln">
  contentEditable</span><span class="pun">.</span><span class="pln">sendKeys</span><span class="pun">(</span><span class="pln">protractor</span><span class="pun">.</span><span class="typ">Key</span><span class="pun">.</span><span class="pln">BACK_SPACE</span><span class="pun">);</span><span class="pln">
  expect</span><span class="pun">(</span><span class="pln">contentEditable</span><span class="pun">.</span><span class="pln">getText</span><span class="pun">()).</span><span class="pln">toEqual</span><span class="pun">(</span><span class="str">''</span><span class="pun">);</span><span class="pln">
  expect</span><span class="pun">(</span><span class="pln">contentEditable</span><span class="pun">.</span><span class="pln">getAttribute</span><span class="pun">(</span><span class="str">'class'</span><span class="pun">)).</span><span class="pln">toMatch</span><span class="pun">(</span><span class="str">/ng-invalid-required/</span><span class="pun">);</span><span class="pln">
</span><span class="pun">});</span></code></pre>
    </div>
  

    <iframe name="example-NgModelController" src="examples/example-NgModelController/index.html" class="runnable-example-frame"></iframe>
  </div>
</div>


<p></p>

</div>


</div>
        </div>




