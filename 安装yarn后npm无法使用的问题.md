# 安装yarn后npm无法使用的问题.md

要重装 node , 主要原因是 yarn 本身会安装 node

版本不一样时, 有冲突 


## 清除 使用官网安装包 安装过 node

```
sudo rm -rf /usr/local/{bin/{node,npm},lib/node_modules/npm,lib/node} 
```


## 安装 yarn 

```
brew update
brew install yarn
```

安装 yarn 会安装最新的 node

如果 使用官网安装包 安装过 node , 实际上存在两个版本的 node

因此, 清除 node 后, 再安装 yarn , 就不要 再安装 node 了

只需要 link 

```
brew link --overwrite node
brew postinstall node
```



## 使用 brew 安装 node

不使用官网安装包

```
sudo brew uninstall node
brew update
brew upgrade
brew cleanup
brew install node
sudo chown -R $(whoami) /usr/local
brew link --overwrite node
brew postinstall node

```