# 第02讲 middleware 中间件

## 中间件

```ts
app.use(async (ctx, next)=>{
  await next();
  ctx.response.type = 'text/html';
  ctx.response.body = '<h1>Hello World</h1>';
});
```

它的作用是：每收到一个 http 请求，Koa 都会调用通过 app.use() 注册的 async 函数，同时为该函数传入 ctx 和 next 两个参数。而这个 async 函数就是我们所说的中间件。

## ctx (Context)

ctx 作为上下文使用，包含了基本的 ctx.request 和 ctx.response。另外，还对 Koa 内部对一些常用的属性或者方法做了代理操作，使得我们可以直接通过 ctx 获取。比如，ctx.request.url 可以写成 ctx.url。


除此之外，Koa 还约定了一个中间件的存储空间 ctx.state。通过 state 可以存储一些数据，比如用户数据，版本信息等。如果你使用 webpack 打包的话，可以使用中间件，将加载资源的方法作为 ctx.state 的属性传入到 view 层，方便获取资源路径。

```ts
declare interface ContextDelegatedRequest {
    /**
     * Return request header.
     */
    header: any;

    /**
     * Return request header, alias as request.header
     */
    headers: any;

    /**
     * Get/Set request URL.
     */
    url: string;

    /**
     * Get origin of URL.
     */
    origin: string;

    /**
     * Get full request URL.
     */
    href: string;

    /**
     * Get/Set request method.
     */
    method: string;

    /**
     * Get request pathname.
     * Set pathname, retaining the query-string when present.
     */
    path: string;

    /**
     * Get parsed query-string.
     * Set query-string as an object.
     */
    query: any;

    /**
     * Get/Set query string.
     */
    querystring: string;

    /**
     * Get the search string. Same as the querystring
     * except it includes the leading ?.
     *
     * Set the search string. Same as
     * response.querystring= but included for ubiquity.
     */
    search: string;

    /**
     * Parse the "Host" header field host
     * and support X-Forwarded-Host when a
     * proxy is enabled.
     */
    host: string;

    /**
     * Parse the "Host" header field hostname
     * and support X-Forwarded-Host when a
     * proxy is enabled.
     */
    hostname: string;

    /**
     * Get WHATWG parsed URL object.
     */
    URL: url.URL;

    /**
     * Check if the request is fresh, aka
     * Last-Modified and/or the ETag
     * still match.
     */
    fresh: boolean;

    /**
     * Check if the request is stale, aka
     * "Last-Modified" and / or the "ETag" for the
     * resource has changed.
     */
    stale: boolean;

    /**
     * Check if the request is idempotent.
     */
    idempotent: boolean;

    /**
     * Return the request socket.
     */
    socket: Socket;

    /**
     * Return the protocol string "http" or "https"
     * when requested with TLS. When the proxy setting
     * is enabled the "X-Forwarded-Proto" header
     * field will be trusted. If you're running behind
     * a reverse proxy that supplies https for you this
     * may be enabled.
     */
    protocol: string;

    /**
     * Short-hand for:
     *
     *    this.protocol == 'https'
     */
    secure: boolean;

    /**
     * Request remote address. Supports X-Forwarded-For when app.proxy is true.
     */
    ip: string;

    /**
     * When `app.proxy` is `true`, parse
     * the "X-Forwarded-For" ip address list.
     *
     * For example if the value were "client, proxy1, proxy2"
     * you would receive the array `["client", "proxy1", "proxy2"]`
     * where "proxy2" is the furthest down-stream.
     */
    ips: string[];

    /**
     * Return subdomains as an array.
     *
     * Subdomains are the dot-separated parts of the host before the main domain
     * of the app. By default, the domain of the app is assumed to be the last two
     * parts of the host. This can be changed by setting `app.subdomainOffset`.
     *
     * For example, if the domain is "tobi.ferrets.example.com":
     * If `app.subdomainOffset` is not set, this.subdomains is
     * `["ferrets", "tobi"]`.
     * If `app.subdomainOffset` is 3, this.subdomains is `["tobi"]`.
     */
    subdomains: string[];

    /**
     * Check if the given `type(s)` is acceptable, returning
     * the best match when true, otherwise `undefined`, in which
     * case you should respond with 406 "Not Acceptable".
     *
     * The `type` value may be a single mime type string
     * such as "application/json", the extension name
     * such as "json" or an array `["json", "html", "text/plain"]`. When a list
     * or array is given the _best_ match, if any is returned.
     *
     * Examples:
     *
     *     // Accept: text/html
     *     this.accepts('html');
     *     // => "html"
     *
     *     // Accept: text/*, application/json
     *     this.accepts('html');
     *     // => "html"
     *     this.accepts('text/html');
     *     // => "text/html"
     *     this.accepts('json', 'text');
     *     // => "json"
     *     this.accepts('application/json');
     *     // => "application/json"
     *
     *     // Accept: text/*, application/json
     *     this.accepts('image/png');
     *     this.accepts('png');
     *     // => undefined
     *
     *     // Accept: text/*;q=.5, application/json
     *     this.accepts(['html', 'json']);
     *     this.accepts('html', 'json');
     *     // => "json"
     */
    accepts(): string[] | boolean;
    accepts(...types: string[]): string | boolean;
    accepts(types: string[]): string | boolean;

    /**
     * Return accepted encodings or best fit based on `encodings`.
     *
     * Given `Accept-Encoding: gzip, deflate`
     * an array sorted by quality is returned:
     *
     *     ['gzip', 'deflate']
     */
    acceptsEncodings(): string[] | boolean;
    acceptsEncodings(...encodings: string[]): string | boolean;
    acceptsEncodings(encodings: string[]): string | boolean;

    /**
     * Return accepted charsets or best fit based on `charsets`.
     *
     * Given `Accept-Charset: utf-8, iso-8859-1;q=0.2, utf-7;q=0.5`
     * an array sorted by quality is returned:
     *
     *     ['utf-8', 'utf-7', 'iso-8859-1']
     */
    acceptsCharsets(): string[] | boolean;
    acceptsCharsets(...charsets: string[]): string | boolean;
    acceptsCharsets(charsets: string[]): string | boolean;

    /**
     * Return accepted languages or best fit based on `langs`.
     *
     * Given `Accept-Language: en;q=0.8, es, pt`
     * an array sorted by quality is returned:
     *
     *     ['es', 'pt', 'en']
     */
    acceptsLanguages(): string[] | boolean;
    acceptsLanguages(...langs: string[]): string | boolean;
    acceptsLanguages(langs: string[]): string | boolean;

    /**
     * Check if the incoming request contains the "Content-Type"
     * header field, and it contains any of the give mime `type`s.
     * If there is no request body, `null` is returned.
     * If there is no content type, `false` is returned.
     * Otherwise, it returns the first `type` that matches.
     *
     * Examples:
     *
     *     // With Content-Type: text/html; charset=utf-8
     *     this.is('html'); // => 'html'
     *     this.is('text/html'); // => 'text/html'
     *     this.is('text/*', 'application/json'); // => 'text/html'
     *
     *     // When Content-Type is application/json
     *     this.is('json', 'urlencoded'); // => 'json'
     *     this.is('application/json'); // => 'application/json'
     *     this.is('html', 'application/*'); // => 'application/json'
     *
     *     this.is('html'); // => false
     */
    // is(): string | boolean;
    is(...types: string[]): string | boolean;
    is(types: string[]): string | boolean;

    /**
     * Return request header.
     *
     * The `Referrer` header field is special-cased,
     * both `Referrer` and `Referer` are interchangeable.
     *
     * Examples:
     *
     *     this.get('Content-Type');
     *     // => "text/plain"
     *
     *     this.get('content-type');
     *     // => "text/plain"
     *
     *     this.get('Something');
     *     // => undefined
     */
    get(field: string): string;
}
```

## next

next 参数的作用是将处理的控制权转交给下一个中间件，而 next() 后面的代码，将会在下一个中间件及后面的中间件（如果有的话）执行结束后再执行。

注意： 中间件的顺序很重要！ 

如果一个中间件没有调用 await next()，会怎样呢？答案是『后面的中间件将不会执行』。

## 实例

```ts
import * as Koa  from "Koa";

const app = new Koa();

app.use(async (ctx, next)=>{
  console.log('时间记录中间件 begin...')
  let stime = new Date().getTime()
  await next()
  let etime = new Date().getTime()
  ctx.response.type = 'text/html'
  ctx.response.body = '<h1>Hello World</h1>'
  console.log(`请求地址: ${ctx.path}，响应时间：${etime - stime}ms`)
  console.log('时间记录中间件 end.')
});

app.use(async (ctx, next) => {
  console.log('中间件1 begin...')
  await next();
  console.log('中间件1 end.')
})

app.use(async (ctx, next) => {
  console.log('中间件2 begin...')
  await next()
  console.log('中间件2 end.')
})

app.use(async (ctx, next) => {
  console.log('中间件3 begin...')
  await next();
  console.log('中间件3 end.')
})

app.listen(3000, () => {
  console.log('server is running at http://localhost:3000')
})

```

输出:

```
时间记录中间件 begin...
中间件1 begin...
中间件2 begin...
中间件3 begin...
中间件3 end.
中间件2 end.
中间件1 end.
请求地址: /，响应时间：0ms
时间记录中间件 end.

```


