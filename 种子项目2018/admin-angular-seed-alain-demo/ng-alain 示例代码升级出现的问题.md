
# ng-alain 示例代码升级出现的问题


## 用户头像丢失的问题

`src/app/layout/default/header/components/user.component.ts`

旧代码:

```ts
export class HeaderUserComponent implements OnInit {
  constructor(
    public settings: SettingsService,
    private router: Router,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
  ) {}

  ngOnInit(): void {
		this.tokenService.change().subscribe((res: any) => {
		  this.settings.setUser(res);
		});
		// mock
		const token = this.tokenService.get() || {
		  token: 'nothing',
		  name: 'Admin',
		  avatar: './assets/logo-color.svg',
		  email: 'cipchk@qq.com',
		};
		this.tokenService.set(token);
  }

  logout() {
    this.tokenService.clear();
    this.router.navigateByUrl(this.tokenService.login_url);
  }
}
```

新代码:

```ts
export class HeaderUserComponent {
  constructor(
    public settings: SettingsService,
    private router: Router,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
  ) {}

  logout() {
    this.tokenService.clear();
    this.router.navigateByUrl(this.tokenService.login_url);
  }
}
```

