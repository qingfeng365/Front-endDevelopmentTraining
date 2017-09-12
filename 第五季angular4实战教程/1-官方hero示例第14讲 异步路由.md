# 1-官方hero示例第14讲 异步路由

## 惰性加载路由配置

将 `AdminModule` 改为惰性加载

取消 `AdminRoutingModule` 的主路径

因为 主路径 要提到在根路由设置


`/src/app/admin/admin-routing.module.ts`

```ts
import { ManageCrisesComponent } from './manage-crises/manage-crises.component';
import { AdminComponent } from './admin.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ManageHeroesComponent } from './manage-heroes/manage-heroes.component';
import { AuthGuardService } from '../auth-guard.service';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: '',
        canActivateChild: [AuthGuardService],
        children: [
          {
            path: 'crises',
            component: ManageCrisesComponent
          },
          {
            path: 'heroes',
            component: ManageHeroesComponent
          },
          {
            path: '',
            component: AdminDashboardComponent
          }
        ]
      }
    ],

  }]

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule { }

```

在根路由设置 惰性加载

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './heroes/hero-list/hero-list.component';
import { DashboardComponent } from './heroes/dashboard/dashboard.component';
import { HeroDetailComponent } from './heroes/hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './heroes/animate-hero-list/animate-hero-list.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    loadChildren: 'app/admin/admin.module.ts#AdminModule'
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

> 给它一个 loadChildren 属性（注意不是 children 属性），
> 把它设置为 AdminModule 的地址。
> 
> 该地址是 AdminModule 的文件路径（相对于 app 目录的），加上一个#分隔符，
> 再加上导出模块的类名 AdminModule。

> 当路由器导航到这个路由时，它会用 loadChildren 字符串来动态加载 AdminModule ，
> 然后把 AdminModule 添加到当前的路由配置中， 
> 最后，它把所请求的路由加载到目标 admin 组件中。

> 惰性加载和重新配置工作只会发生一次，也就是在该路由首次被请求时。
> 在后续的请求中，该模块和路由都是立即可用的。
> 


最后一步是把管理特性区从主应用中完全分离开。 
根模块 AppModule 既不能加载也不能引用 AdminModule 及其文件。

`/src/app/app.module.ts`

```ts
import { AuthGuardService } from './auth-guard.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroesModule } from './heroes/heroes.module';
import { CrisisCenterModule } from './crisis-center/crisis-center.module';
import { LoginComponent } from './login/login.component';
import { AuthService } from './auth.service';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    HeroesModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 }),
    CrisisCenterModule,
    AppRoutingModule,
  ],
  providers: [AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```


## CanLoad守卫：保护对特性模块的未授权加载

我们已经使用 CanAcitvate 保护 AdminModule 了，它会阻止未授权用户访问管理特性区。
如果用户未登录，它就会跳转到登录页。

但是路由器仍然会加载 AdminModule —— 即使用户无法访问它的任何一个组件。 理想的方式是，只有在用户已登录的情况下我们才加载 AdminModule 。

添加一个 CanLoad 守卫，它只在用户已登录并且尝试访问管理特性区的时候，
才加载 AdminModule 一次。

`/src/app/auth-guard.service.ts`

```ts
import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot, RouterStateSnapshot,
  CanActivateChild,
  NavigationExtras,
  CanLoad,
  Route
} from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs/Rx';



@Injectable()
export class AuthGuardService implements CanActivate, CanActivateChild, CanLoad {



  constructor(private authService: AuthService, private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('canActivate...');
    const url: string = state.url;
    return this.checkLogin(url);
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
    console.log('canActivateChild...');
    const url: string = state.url;
    return this.checkLogin(url);
  }

  canLoad(route: Route): boolean | Observable<boolean> | Promise<boolean> {
    console.log('canLoad... ', route.path);
    const url: string = route.path;
    return this.checkLogin(url);
  }
  checkLogin(url: string): boolean {
    if (this.authService.isLoggedIn) { return true; }

    this.authService.redirectUrl = url;
    const sessionId = 123456789;

    const navigationExtras: NavigationExtras = {
      queryParams: { 'session_id': sessionId },
      fragment: 'anchor'
    };

    this.router.navigate(['/login'], navigationExtras);
    return false;
  }
}

```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './heroes/hero-list/hero-list.component';
import { DashboardComponent } from './heroes/dashboard/dashboard.component';
import { HeroDetailComponent } from './heroes/hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './heroes/animate-hero-list/animate-hero-list.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './auth-guard.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    loadChildren: 'app/admin/admin.module.ts#AdminModule',
    canLoad: [AuthGuardService]
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```


## 预加载：特性区的后台加载

AppModule 在应用启动时就被加载了，它是立即加载的。 

而 AdminModule 只有当用户点击某个链接时才会加载，它是惰性加载的。

预加载是介于两者之间的一种方式。 我们来看看危机中心。 

用户第一眼不会看到它。 默认情况下，英雄管理才是第一视图。 

为了获得尽可能小的初始加载体积和最快的加载速度，
我们应该对 AppModule 和 HeroesModule 进行立即加载。


我们可以惰性加载危机中心。 

但是，我们几乎可以肯定用户会在启动应用之后的几分钟内访问危机中心。 

理想情况下，应用启动时应该只加载 AppModule 和 HeroesModule ，然后几乎立即开始后台加载 CrisisCenterModule 。 

在用户浏览到危机中心之前，该模块应该已经加载完毕，可供访问了。


### 预加载的工作原理

在每次成功的导航后，路由器会在自己的配置中查找尚未加载并且可以预加载的模块。 是否加载某个模块，以及要加载哪些模块，取决于预加载策略。

Router内置了两种预加载策略：

    完全不预加载，这是默认值。惰性加载的特性区仍然会按需加载。

    预加载所有惰性加载的特性区。

默认情况下，路由器或者完全不预加载或者预加载每个惰性加载模块。 路由器还支持自定义预加载策略，以便完全控制要预加载哪些模块以及何时加载。

### PreloadAllModules

先将 危机中心 改为惰性加载


`/src/app/crisis-center/crisis-center-routing.module.ts`

```ts
import { CrisisDetailComponent } from './crisis-detail/crisis-detail.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CrisisCenterComponent } from './crisis-center.component';
import { CrisisListComponent } from './crisis-list/crisis-list.component';

const routes: Routes = [
  {
    path: '',
    component: CrisisCenterComponent,
    children: [
      {
        path: '',
        component: CrisisListComponent,
        children: [
          {
            path: ':id',
            component: CrisisDetailComponent
          }
        ]
      }
    ]

  }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class CrisisCenterRoutingModule { }

```


`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './heroes/hero-list/hero-list.component';
import { DashboardComponent } from './heroes/dashboard/dashboard.component';
import { HeroDetailComponent } from './heroes/hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './heroes/animate-hero-list/animate-hero-list.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './auth-guard.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    loadChildren: 'app/admin/admin.module.ts#AdminModule',
    canLoad: [AuthGuardService]
  },
  {
    path: 'crisis-center',
    loadChildren: 'app/crisis-center/crisis-center.module.ts#CrisisCenterModule',
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

`/src/app/app.module.ts`

```ts
import { AuthGuardService } from './auth-guard.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api'
import { InMemoryDataService } from './mock-data/in-memory-data.service';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroesModule } from './heroes/heroes.module';
import { LoginComponent } from './login/login.component';
import { AuthService } from './auth.service';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    HeroesModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 }),
    AppRoutingModule,
  ],
  providers: [AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

### 启用预加载

导入 PreloadAllModules

RouterModule.forRoot 方法的第二个参数接受一个附加配置选项对象。 

preloadingStrategy 就是其中之一。 

把 PreloadAllModules 添加到 forRoot 调用


`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HeroListComponent } from './heroes/hero-list/hero-list.component';
import { DashboardComponent } from './heroes/dashboard/dashboard.component';
import { HeroDetailComponent } from './heroes/hero-detail/hero-detail.component';
import { AnimateHeroListComponent } from './heroes/animate-hero-list/animate-hero-list.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './auth-guard.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'admin',
    loadChildren: 'app/admin/admin.module.ts#AdminModule',
    canLoad: [AuthGuardService]
  },
  {
    path: 'crisis-center',
    loadChildren: 'app/crisis-center/crisis-center.module.ts#CrisisCenterModule',
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

路由器在加载了 HeroesModule 之后立即开始加载 CrisisCenterModule。

但是，AdminModule 没有预加载

这是因为 CanLoad 会阻塞预加载

PreloadAllModules 策略不会加载被 CanLoad 守卫所保护的特性区。这是刻意设计的。

CanLoad守卫的优先级高于预加载策略。

如果我们要加载一个模块并且保护它防止未授权访问，请移除 canLoad 守卫，只单独依赖 CanActivate 守卫。

## 自定义预加载策略

要自行实现 PreloadingStrategy 接口, 略