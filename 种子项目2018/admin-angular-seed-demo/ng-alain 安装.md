# ng-alain 安装

npm install --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass


```
npm install -g @angular-devkit/core @angular-devkit/schematics @schematics/schematics rxjs --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```

```
npm install -g @angular/cli --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```

```
npm install -g @delon/cli@next --registry=https://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist --sass-binary-site=http://npm.taobao.org/mirrors/node-sass

```



## 复制文件

```
# linux
cd /usr/local/lib/node_modules/@angular/cli/node_modules
mkdir @delon
cp -R /usr/local/lib/node_modules/@delon/* @delon/

# window（提醒：注意 `asdf` 替换成你的用户名）
cd C:\Users\asdf\AppData\Roaming\npm\node_modules
xcopy "@delon" "@angular\cli\node_modules\@delon" /s /e /y
```

## 创建项目

