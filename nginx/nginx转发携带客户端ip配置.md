# nginx转发携带客户端ip配置

## 在 location 段 增加 以下设置

普通写法:

```
location xxx {
	proxy_set_header Host $host;
	proxy_set_header X-Forwarded-Host $server_name;
	proxy_set_header X-Real-IP $remote_addr;
	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

}
```


最完整写法, 适应最多的情况

```

location xxx {
	proxy_set_header Host $host;
	proxy_set_header X-Forwarded-Host $server_name;
	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	set $Real $http_x_forwarded_for;
	if ( $Real ~ (\d+)\.(\d+)\.(\d+)\.(\d+),(.*) ){
    set $Real $1.$2.$3.$4;
	}
	proxy_set_header X-Real-IP $Real; 
}

```




$proxy_add_x_forwarded_for : 是一串ip地址, 每经过一层转发, 都会增加一个上游ip
$remote_addr: 是 上一层的ip 地址 , 不一定是真实客户端IP


## node 相关代码

如果用 ipware 的话, ipware 默认是 用 X-Forwarded-For 的 最左 ip 判断

用原生代码, 可直接使用 X-Real-IP,  http_x_real_ip 判断


## 另一种设置

```
nginx配置

强烈建议使用nginx托管前端静态资源，并反向代理后端服务，配置如下

server {
  listen 80;
  server_name example.com www.example.com;
  set $node_port 7002;

  root /path/to/www;

  location  /api/ {
    proxy_http_version 1.1;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Host $http_host;
    proxy_set_header X-NginX-Proxy true;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
    proxy_pass http://127.0.0.1:$node_port$request_uri;
    proxy_redirect off;
  }

}
```