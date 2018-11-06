
# angular6 依赖注入的新特点


## 创建服务

```ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SelectService {

  constructor() { }
}

```

providedIn: 'root' 告诉 Angular在根注入器中注册这个服务.root 还可以是某一个具体的模块名


[https://www.jb51.net/article/142719.htm](https://www.jb51.net/article/142719.htm)