# ionic3 安卓打包和上架

## 要用到的环境

- java sdk 1.8 (注意不能超过 1.8, 不能使用 1.9 以上)
- Android studio 3 
- Gradle (必须)

[https://gradle.org/install/](https://gradle.org/install/)

## 打包步骤

## 确定 ionic 和 cordova 的全局安装是最新的

## 确定已在项目添加安卓平台配置

## 尝试打包

```

ionic cordova build android

```

如果成功运行,则会生成测试包

`platforms/android/app/build/outputs/apk/debug/app-debug.apk`


## 生成签名文件

```

keytool -genkey -v -keystore 你起的名字.keystore -alias 你起的别名 -keyalg RSA -validity 36500

```


- genkey	 执行生成数字证书操作
- v			   显示生成证书的详细信息
- keystore 签名文件名称
- alias    签名别名 (别名与文件名,可一致也可不一致)
- keyalg   加密算法
- validity 有效期天数

```

keytool -genkey -v -keystore ykmobilebi.keystore -alias ykmobilebi -keyalg RSA -validity 36500
```

密钥库口令: yk38259343
名字和姓氏: piaoyanguohai
组织单位名称: yourkey
组织名称: yourkey
城市: gz
省: gd
国家代码: zh

## 生成正式未签名的 apk

```
ionic cordova build android --prod --release
```

如果成功运行,则会生成正式未签名包

`platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk`

## 对 apk 进行签名

```
jarsigner -verbose -keystore 你起的名字.keystore -signedjar 输出文件路径和文件名.apk 源文件路径和文件名.apk 证书的别名
```

- verbose: 显示详细信息
- keystore 签名文件名称

```
jarsigner -verbose -keystore ykmobilebi.keystore -signedjar platforms/android/app/build/outputs/apk/release/ykmobilebi-release.apk platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk ykmobilebi

```

按提示输入口令


## 压缩

如果文件过大,考虑压缩,略
