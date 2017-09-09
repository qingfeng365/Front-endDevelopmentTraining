# 1-官方hero示例第13讲 路由守卫

路由器支持多种守卫：

    用CanActivate来处理导航到某路由的情况。

    用CanActivateChild处理导航到子路由的情况。

    用CanDeactivate来处理从当前路由离开的情况。

    用Resolve在路由激活之前获取路由数据。

    用CanLoad来处理异步导航到某特性模块的情况。

在分层路由的每个级别上，我们都可以设置多个守卫。 

路由器会先按照从最深的子路由由下往上检查的顺序来检查CanDeactivate()和CanActivateChild()守卫。 

然后它会按照从上到下的顺序检查CanActivate()和CanActivateChild()守卫。 

如果特性模块是异步加载的，在加载它之前还会检查CanLoad()守卫。 

如果任何一个守卫返回false，其它尚未完成的守卫会被取消，这样整个导航就被取消了。


## 创建管理中心特性模块

```
ng g m admin

ng g c admin

ng g c admin/adminDashboard

ng g c admin/ManageCrises

ng g c admin/ManageHeroes

ng g m admin/adminRouting --flat

```

## 设置管理中心路由

`/src/app/admin/admin-routing.module.ts`

```ts
import { ManageCrisesComponent } from './manage-crises/manage-crises.component';
import { AdminComponent } from './admin.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ManageHeroesComponent } from './manage-heroes/manage-heroes.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
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

`/src/app/admin/admin.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ManageCrisesComponent } from './manage-crises/manage-crises.component';
import { ManageHeroesComponent } from './manage-heroes/manage-heroes.component';
import { AdminRoutingModule } from './admin-routing.module';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule
  ],
  declarations: [
    AdminComponent,
    AdminDashboardComponent,
    ManageCrisesComponent,
    ManageHeroesComponent]
})
export class AdminModule { }

```

`/src/app/admin/admin.component.jade`

```jade
h3 管理中心
nav
  a(routerLink="./", routerLinkActive="active",
    [routerLinkActiveOptions]="{ exact: true }") 仪表盘
  | &nbsp;
  a(routerLink="./crises", routerLinkActive="active") 危机管理
  | &nbsp;
  a(routerLink="./heroes", routerLinkActive="active") 英雄管理
router-outlet

```

>　exact:true , 要求精确匹配,  routerLinkActive 默认当前路由包含 routerLink 定义即可

`/src/app/app.module.ts`

```ts
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
import { AdminModule } from './admin/admin.module';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    HeroesModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, { delay: 1000 }),
    CrisisCenterModule,
    AdminModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

测试功能正常

`/src/styles.css`

```css
h1 {
  color: #369;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 250%;
}

h2,
h3 {
  color: #444;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: lighter;
}

body {
  margin: 2em;
}

body,
input[text],
button {
  color: #888;
  font-family: Cambria, Georgia;
}

* {
  font-family: Arial, Helvetica, sans-serif;
}

nav a {
  padding: 5px 10px;
  text-decoration: none;
  margin-top: 10px;
  display: inline-block;
  background-color: #eee;
  border-radius: 4px;
}

nav a:visited,
a:link {
  color: #607D8B;
}

nav a:hover {
  color: #039be5;
  background-color: #CFD8DC;
}

nav a.active {
  color: #039be5;
}
```


##　创建权限与守卫服务

```

ng g s auth

ng g s authGuard

ng g c login

```


`/src/app/auth.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
@Injectable()
export class AuthService {
  isLoggedIn = false;

  redirectUrl: string;

  constructor() { }

  login(): Observable<boolean> {
    return Observable.of(true).delay(1000).do(val => this.isLoggedIn = true);
  }

  logout(): void {
    this.isLoggedIn = false;
  }

}

```

`/src/app/auth-guard.service.ts`

```ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url: string): boolean {
    if (this.authService.isLoggedIn) { return true; }

    this.authService.redirectUrl = url;

    this.router.navigate(['/login']);
    return false;
  }
}

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
import { CrisisCenterModule } from './crisis-center/crisis-center.module';
import { AdminModule } from './admin/admin.module';
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
    AdminModule,
    AppRoutingModule,
  ],
  providers: [AuthService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

```

`/src/app/login/login.component.jade`

```jade
h2 登录
p
  button((click)="login()", *ngIf="!authService.isLoggedIn") 登录
  | &nbsp;
  button((click)="logout()", *ngIf="authService.isLoggedIn") 登出

```

`/src/app/login/login.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public authService: AuthService, public router: Router) { }

  ngOnInit() {
  }
  login() {
    this.authService
      .login()
      .subscribe(isLoggedIn => {
        if (isLoggedIn) {
          const redirect =
            this.authService.redirectUrl
              ? this.authService.redirectUrl
              : '/admin';
          this.router.navigate([redirect]);
        }
      });
  }
  logout() {
    this.authService.logout();
  }
}

```


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
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuardService],
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

`/src/app/app.component.jade`

```jade
h1 {{title}}
nav
  a(routerLink="/dashboard",routerLinkActive="active") 推荐
  | &nbsp;
  a(routerLink="/heroes",routerLinkActive="active") 列表
  | &nbsp;
  a(routerLink="/animate-hero-list",routerLinkActive="active") 动画演示
  | &nbsp;
  a(routerLink="/crisis-center",routerLinkActive="active") 危机中心
  | &nbsp;
  a(routerLink="/admin",routerLinkActive="active") 管理中心
  | &nbsp;
  a(routerLink="/login",routerLinkActive="active") 登录
router-outlet

```

测试守卫功能

## CanAcitvateChild：保护子路由



