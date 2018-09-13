# 后端api规范

## 返回错误的规定

### 错误对象格式


示例:

```
{
    "code": "invalid_param",
    "message": "参数不正确",
    "errors": [
        {
            "message": "id 值不能为空",
            "field": "id",
            "code": "missing_field"
        }
    ]
}
```

#### 规则

- code: 错误类型编码（可选）, 自定义的编码, 全部小写,用 `_` 分隔单词
- message: 友好的错误信息(必填), 一般建议中文
- errors: 具体的错误信息数组(可选),用于前端开发人员定位错误

- errors[].message : 某项错误的信息(必填)
- errors[].field : 某项错误的key(可选), key 名: `field` 为示例, 可根据错误形式定义不同的名称
- errors[].code : 某项错误的类型编码（可选）, 自定义的编码, 全部小写,用 `_` 分隔单词

## 常见的错误处理规范

### 参数不符合语法

当 参数 为 query 或 body 时, 对参数有基本的语法检查,

如必填, 数值, 布尔 , 等等

如示例:

状态码: 422

```
{
    "code": "invalid_param",
    "message": "参数不正确",
    "errors": [
        {
            "message": "id 值不能为空",
            "field": "id",
            "code": "missing_field"
        }
    ]
}
```

如果参数检查是由 `ctx.validate` 自动处理返回, 则 message 是英文, 一般不需要特别处理

### 登录检查失败

用户不存在或者密码错误

如示例:

状态码: 401

```
{
    "code": "unauthorized",
    "message": "身份验证错误",
    "errors": [
        {
            "message": "用户不存在",
            "field": "user",
            "code": "user_not_found"
        }
    ]
}
```


状态码: 401

```
{
    "code": "unauthorized",
    "message": "身份验证错误",
    "errors": [
        {
            "message": "密码不正确",
            "field": "password",
            "code": "invalid_password"
        }
    ]
}
```

### token 检查失败

- 没有 token 

状态码: 401

```
{
    "code": "unauthorized",
    "message": "身份验证错误",
    "errors": [
        {
            "message": "没有提供 token ",
            "field": "token",
            "code": "missing_token"
        }
    ]
}
```


### 错误状态码的选择

- 如果服务端可以正常处理请求,但因请求自身的原因产生的错误,状态码为 4xx
- 如果服务端代码出现异常,状态码为 5xx
- 在数据库处理过程中,非法数据检查, 状态码为 4xx
- 能通过检查, 在执行语句中出现异常, 状态码为 5xx


## 错误状态码参考

|Status Code|Constructor Name             | desc                        |
|-----------|-----------------------------|-----------------------------| 
|400        |BadRequest                   | 错误请求:服务器不理解请求的语法  |
|401        |Unauthorized                 | 身份验证错误                  |
|402        |PaymentRequired              | 该状态码是为了将来可能的需求而预留的|
|403        |Forbidden                    | 禁止:  服务器拒绝请求|
|404        |NotFound                     | 未找到: 服务器找不到请求的资源|
|405        |MethodNotAllowed             | 方法禁用: 禁用请求中指定的方法|
|406        |NotAcceptable                | 不接受: 无法使用请求的内容|
|407        |ProxyAuthenticationRequired  | 需要代理授权:与 401 类似|
|408        |RequestTimeout               | 请求超时|
|409        |Conflict                     | 冲突: 服务器在完成请求时发生冲突|
|410        |Gone                         | 已删除:请求的资源永久删除后|
|411        |LengthRequired               | 需要有效长度|
|412        |PreconditionFailed           | 未满足前提条件:未满足请求中设置的前提条件|
|413        |PayloadTooLarge              | 请求实体过大|
|414        |URITooLong                   | 请求的 URI 过长|
|415        |UnsupportedMediaType         | 不支持的媒体类型|
|416        |RangeNotSatisfiable          | 请求范围不符合要求|
|417        |ExpectationFailed            | 未满足期望值|
|418        |ImATeapot                    | 
|421        |MisdirectedRequest           | 连接数超过了服务器许可的最大范围
|422        |UnprocessableEntity          | 请求格式正确，但含有语义错误
|423        |Locked                       | 当前资源被锁定
|424        |FailedDependency             | 由于之前的某个请求发生的错误，导致当前请求失败
|425        |UnorderedCollection          |
|426        |UpgradeRequired              |
|428        |PreconditionRequired         |
|429        |TooManyRequests              |
|431        |RequestHeaderFieldsTooLarge  |
|451        |UnavailableForLegalReasons   | 该请求因法律原因不可用
|500        |InternalServerError          | 服务器内部错误
|501        |NotImplemented               | 尚未实施
|502        |BadGateway                   | 错误网关
|503        |ServiceUnavailable           | 服务不可用
|504        |GatewayTimeout               | 网关超时
|505        |HTTPVersionNotSupported      | HTTP 版本不受支持
|506        |VariantAlsoNegotiates        |
|507        |InsufficientStorage          |
|508        |LoopDetected                 |
|509        |BandwidthLimitExceeded       |
|510        |NotExtended                  |
|511        |NetworkAuthenticationRequired|

## 中间件参考 

HTTP-friendly error objects 

[https://github.com/hapijs/boom](https://github.com/hapijs/boom)


Create HTTP errors for Express, Koa, Connect

[https://github.com/jshttp/http-errors](https://github.com/jshttp/http-errors)


如何选择正确的HTTP状态码

[http://www.infoq.com/cn/news/2015/12/how-to-choose-http-status-code](http://www.infoq.com/cn/news/2015/12/how-to-choose-http-status-code)