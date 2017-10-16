# 3-nicefish示例第03讲 文章

先在 src 目录复制素材

`/src/mock-data` 

`/.angular-cli.json`

```json
    "assets": [
      "assets",
      "mock-data",
      "favicon.ico"
    ],
```

## 创建组件、模型与服务

```
ng g c post/postList

ng g c post/postDetailMain

ng g c post/postDetail

ng g s post/service/postList

ng g s post/service/postDetail

```


`/src/app/post/model/post-model.ts`

```ts
export class Post {
  id: number;
  title: string;
  text: string;
  author: string;
  postTime: Date;
  readTimes: number;
  commentTimes: number;
}

```

## 设置路由

`/src/app/post/post-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostDetailMainComponent } from './post-detail-main/post-detail-main.component';
import { PostListComponent } from './post-list/post-list.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'page/1',
    pathMatch: 'full'
  },
  {
    path: 'page/:page',
    component: PostListComponent
  },
  {
    path: 'postdetail/:postId',
    component: PostDetailMainComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostRoutingModule { }


```

`/src/app/post/post.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostListComponent } from './post-list/post-list.component';
import { PostDetailMainComponent } from './post-detail-main/post-detail-main.component';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { PostRoutingModule } from './post-routing.module';
import { PostListService } from './service/post-list.service';
import { PostDetailService } from './service/post-detail.service';

@NgModule({
  imports: [
    CommonModule,
    PostRoutingModule
  ],
  declarations: [
    PostListComponent,
    PostDetailMainComponent,
    PostDetailComponent],
    providers: [
      PostListService,
      PostDetailService,
  ]
})
export class PostModule { }

```

`/src/app/home/home-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [{
      path: '',
      loadChildren: '../post/post.module#PostModule'
    }]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule { }


```

`/src/app/app-routing.module.ts`

```ts
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'posts',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomeModule'
  },
  {
    path: 'posts',
    loadChildren: './home/home.module#HomeModule'
  },
  {
    path: '**',
    loadChildren: './home/home.module#HomeModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

```

## 处理模块引用

`/src/app/app.module.ts`

```ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

```

`/src/app/shared/shared.module.ts`

```ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [],
})
export class SharedModule { }

```

`/src/app/post/post.module.ts`

```ts
import { SharedModule } from './../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostListComponent } from './post-list/post-list.component';
import { PostDetailMainComponent } from './post-detail-main/post-detail-main.component';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { PostRoutingModule } from './post-routing.module';
import { PostListService } from './service/post-list.service';
import { PostDetailService } from './service/post-detail.service';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    PostRoutingModule
  ],
  declarations: [
    PostListComponent,
    PostDetailMainComponent,
    PostDetailComponent],
    providers: [
      PostListService,
      PostDetailService,
  ]
})
export class PostModule { }

```

##　文章列表

`/src/app/post/service/post-list.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable, Subject } from 'rxjs/Rx';
import { Post } from '../model/post-model';

export interface ReturnData<T> {
  allRecordCount: number;
  datas: T[];
}

export interface PageInfo {
  pageIndex: number;
  pageSize: number;
}

@Injectable()
export class PostListService {
  private apiUrl = 'mock-data/postlist-mock.json';

  private requireDataSubject: Subject<PageInfo>
  = new Subject();

  private returnDataSubject: Subject<
  ReturnData<Post>> = new Subject();

  private requireAllCountSubject: Subject<string> = new Subject();

  constructor(private http: Http) {
    this.procRequireData();
  }

  notityRequireData(pageIndex: number, pageSize: number):
    Observable<ReturnData<Post>> {
    this.requireAllCountSubject.next('');
    this.requireDataSubject.next({ pageIndex, pageSize });
    return this.returnDataSubject;
  }

  private procRequireData() {
    this.requireDataSubject
      .switchMap(pageInfo => {
        const start = pageInfo.pageIndex * pageInfo.pageSize;
        return this.getItems(start, pageInfo.pageSize);
      })
      .withLatestFrom(
      this.requireAllCountSubject
        .distinctUntilChanged()
        .switchMap(v => {
          return this.getItemsTotal();
        }),
      (datas, count) => {
        return {
          allRecordCount: count,
          datas: datas
        };
      }
      )
      .subscribe(v => {
        this.returnDataSubject.next(v);
      });
  }

  private catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  private getItems(start: number, size: number): Observable<Post[]> {
    return this.http.get(this.apiUrl)
      .map(res => {
        let result = [];
        const items: any[] = res.json().items;
        if (items && items.length > start) {
          result = items.slice(start, start + size);
        }
        return result;
      })
      .catch(this.catchError);
  }
  private getItemsTotal(): Observable<number> {
    return this.http.get(this.apiUrl)
      .map(res => res.json().items.length)
      .catch(this.catchError);
  }
}

```

`/src/app/post/post-list/post-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { PostListService } from '../service/post-list.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from '../model/post-model';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {
  postList: Post[];

  currPageIndex = 0;
  pageSize = 5;
  allRecordCount = 0;

  get pageCount(): number {
    return Math.ceil(this.allRecordCount / this.pageSize);
  }

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    private postListService: PostListService
  ) { }

  ngOnInit() {
    this.initPage();
    this.activeRoute.params
      .pluck('page')
      .subscribe(page => {
        if (page) {
          this.currPageIndex = (+page) - 1;
          if (this.currPageIndex < 0) {
            this.currPageIndex = 0;
          }
        }
        this.loadData();
      });
  }

  initPage() {
    this.currPageIndex = 0;
    this.pageSize = 5;
    this.allRecordCount = 0;
  }

  loadData() {
    this.postListService
      .notityRequireData(this.currPageIndex, this.pageSize)
      .subscribe(v => {
        this.postList = [...v.datas];
        this.allRecordCount = v.allRecordCount;
      });
  }

  pageChanged(event: { page: number, first: number, rows: number, pageCount: number }) {
    this.router.navigate(['/posts/page', (+event.page) + 1]);
  }
}

```

`/src/app/post/post-list/post-list.component.jade`

```jade
.post-list-container
  .row
    .col-sm-12
      .post-item-container(*ngFor="let post of postList")
        h5
          a([routerLink]="['/post/postdetail',post.postId]")
            | {{post.title}}
        p
          i.fa.fa-thumbs-up
          | &nbsp;{{post.likedTimes}} &nbsp;&nbsp;&nbsp;&nbsp;
          i.fa.fa-comment
          | &nbsp;{{post.commentTimes}} &nbsp;&nbsp;&nbsp;&nbsp;
          i.fa.fa-eye
          | &nbsp;{{post.readTimes}} &nbsp;&nbsp;&nbsp;&nbsp;{{post.userName }} &nbsp;&nbsp;&nbsp;&nbsp;{{post.postTime}}
  .row
    .col-sm-12
      p-paginator(
       [rows]="pageSize",
       [totalRecords]="allRecordCount",
       (onPageChange)="pageChanged($event)"
       )

```

`/src/app/post/post-list/post-list.component.scss`

```css
.post-list-container {
  .post-item-container {
    padding-bottom: 10px;
    margin-bottom: 20px;
    border-bottom: 1px solid #d9d9d9;
  }
}
```

## 增加搜索功能

`/src/app/post/service/post-list.service.ts`

```ts
import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable, Subject } from 'rxjs/Rx';
import { Post } from '../model/post-model';

export interface ReturnData<T> {
  allRecordCount: number;
  datas: T[];
}

export interface PageInfo {
  pageIndex: number;
  pageSize: number;
  searchText?: string;
}

@Injectable()
export class PostListService {
  private apiUrl = 'mock-data/postlist-mock.json';

  private requireDataSubject: Subject<PageInfo>
  = new Subject();

  private returnDataSubject: Subject<
  ReturnData<Post>> = new Subject();

  private requireAllCountSubject: Subject<string> = new Subject();

  constructor(private http: Http) {
    this.procRequireData();
  }

  notityRequireData(pageIndex: number, pageSize: number,
    searchText?: string):
    Observable<ReturnData<Post>> {
    this.requireAllCountSubject.next((searchText) ? searchText : '');
    this.requireDataSubject.next({
      pageIndex,
      pageSize,
      searchText: (searchText) ? searchText : null
    });
    return this.returnDataSubject;
  }

  private procRequireData() {
    this.requireDataSubject
      .switchMap(pageInfo => {
        const start = pageInfo.pageIndex * pageInfo.pageSize;
        return this.getItems(start, pageInfo.pageSize, pageInfo.searchText);
      })
      .withLatestFrom(
      this.requireAllCountSubject
        .distinctUntilChanged()
        .switchMap(v => {
          return this.getItemsTotal(v);
        }),
      (datas, count) => {
        return {
          allRecordCount: count,
          datas: datas
        };
      }
      )
      .subscribe(v => {
        this.returnDataSubject.next(v);
      });
  }

  private catchError(err) {
    console.log(err);
    return Observable.throw(err.message || err);
  }

  private getItems(start: number, size: number,
    searchText?: string): Observable<Post[]> {
    return this.http.get(this.apiUrl)
      .map(res => {
        let result = [];
        let items: any[] = res.json().items;
        if (searchText && searchText !== '') {
          items = items.filter(v => {
            const reg = new RegExp(searchText, 'gi');
            return v.title.search(reg) >= 0;
          });
        }
        if (items && items.length > start) {
          result = items.slice(start, start + size);
        }
        return result;
      })
      .catch(this.catchError);
  }
  private getItemsTotal(searchText?: string): Observable<number> {
    return this.http.get(this.apiUrl)
      .map(res => {
        let items: any[] = res.json().items;
        if (searchText && searchText !== '') {
          items = items.filter(v => {
            const reg = new RegExp(searchText, 'gi');
            return v.title.search(reg) >= 0;
          });
        }
        return items.length;
      })
      .catch(this.catchError);
  }
}

```

`/src/app/post/post-list/post-list.component.jade`

```jade
.post-list-container
  .row.my-3.p-3
    .col
      .input-group
        input.form-control(type="text",
          placeholder="搜索",[(ngModel)]="searchText",
          (input)="search()",
          name="searchText")
        span.input-group-btn
          button.btn.btn-default(type="button",
            (click)="searchText='';search()")
            i.fa.fa-times
  .row
    .col-sm-12
      .post-item-container(*ngFor="let post of postList")
        h5
          a([routerLink]="['/post/postdetail',post.postId]")
            | {{post.title}}
        p
          i.fa.fa-thumbs-up
          | &nbsp;{{post.likedTimes}} &nbsp;&nbsp;&nbsp;&nbsp;
          i.fa.fa-comment
          | &nbsp;{{post.commentTimes}} &nbsp;&nbsp;&nbsp;&nbsp;
          i.fa.fa-eye
          | &nbsp;{{post.readTimes}} &nbsp;&nbsp;&nbsp;&nbsp;{{post.userName }} &nbsp;&nbsp;&nbsp;&nbsp;{{post.postTime}}
  .row
    .col-sm-12
      p-paginator(
       [rows]="pageSize",
       [totalRecords]="allRecordCount",
       (onPageChange)="pageChanged($event)"
       )

```

`/src/app/post/post-list/post-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { PostListService } from '../service/post-list.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from '../model/post-model';
import { Observable, Subject } from 'rxjs/Rx';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {
  postList: Post[];

  currPageIndex = 0;
  pageSize = 5;
  allRecordCount = 0;

  searchText = '';
  searchSubject: Subject<string> = new Subject();

  get pageCount(): number {
    return Math.ceil(this.allRecordCount / this.pageSize);
  }

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    private postListService: PostListService
  ) {
    this.procSearch();
  }

  ngOnInit() {
    this.initPage();
    this.activeRoute.params
      .subscribe(params => {
        console.log(params);
        if (!params.page) {
          this.currPageIndex = (+params.page) - 1;
          if (this.currPageIndex < 0) {
            this.currPageIndex = 0;
          }
        }
        if (params.searchText) {
          this.searchText = params.searchText;
        }
        this.loadData();
      });
  }

  initPage() {
    this.currPageIndex = 0;
    this.pageSize = 5;
    this.allRecordCount = 0;
  }

  loadData() {
    this.postListService
      .notityRequireData(
      this.currPageIndex,
      this.pageSize,
      (this.searchText) ? this.searchText : null)
      .subscribe(v => {
        this.postList = [...v.datas];
        this.allRecordCount = v.allRecordCount;
      });
  }

  pageChanged(event: { page: number, first: number, rows: number, pageCount: number }) {
    if (this.searchText) {
      this.router.navigate(['/posts/page', (+event.page) + 1, { searchText: this.searchText }]);
    } else {
      this.router.navigate(['/posts/page', (+event.page) + 1]);
    }
  }

  search() {
    this.searchSubject.next(this.searchText);
  }
  procSearch() {
    this.searchSubject
      .debounceTime(500)
      .distinctUntilChanged()
      .subscribe(v => {
        if (v) {
          this.router.navigate(['/posts/page/1',
            { searchText: v }]);
        } else {
          this.router.navigate(['/posts/page/1']);
        }
      });
  }
}

```



