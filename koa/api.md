


## Koa 内置的代理 request 的属性和方法列表

ctx 上的很多属性和方法都被代理到 request 对象上，对于这些属性和方法使用 ctx 和使用 request 去访问它们是等价的，例如 ctx.url === ctx.request.url。

[http://koajs.com/#request-aliases](http://koajs.com/#request-aliases)
Request aliases

The following accessors and alias Request equivalents:

    ctx.header
    ctx.headers
    ctx.method
    ctx.method=
    ctx.url
    ctx.url=
    ctx.originalUrl
    ctx.origin
    ctx.href
    ctx.path
    ctx.path=
    ctx.query
    ctx.query=
    ctx.querystring
    ctx.querystring=
    ctx.host
    ctx.hostname
    ctx.fresh
    ctx.stale
    ctx.socket
    ctx.protocol
    ctx.secure
    ctx.ip
    ctx.ips
    ctx.subdomains
    ctx.is()
    ctx.accepts()
    ctx.acceptsEncodings()
    ctx.acceptsCharsets()
    ctx.acceptsLanguages()
    ctx.get()

Response aliases

The following accessors and alias Response equivalents:

    ctx.body
    ctx.body=
    ctx.status
    ctx.status=
    ctx.message
    ctx.message=
    ctx.length=
    ctx.length
    ctx.type=
    ctx.type
    ctx.headerSent
    ctx.redirect()
    ctx.attachment()
    ctx.set()
    ctx.append()
    ctx.remove()
    ctx.lastModified=
    ctx.etag=

## http://koajs.com/#context