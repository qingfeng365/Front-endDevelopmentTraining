# mac 常用 linux 命令

## vi 使用方法

### 进入和退出

`i` : 进入编辑状态
`ESC` : 退出编辑状态
`:wq` : 保存并退出


## shell 配置文件

缺省 bash 的配置文件: .bashprofile

```bash
$ vi ~/.bashprofile
```

zsh 的配置文件: .zshrc

```bash
$ vi ~/.zshrc
```


## Mac显示隐藏系统文件

Mac显示隐藏系统文件

方法一：（快捷键）

      打开Finder，同时按下三个组合键：Shift + Command + . 

方法二：（终端操作，要重启Finder，没方法一快捷）

      显示：defaults write com.apple.finder AppleShowAllFiles -bool true 

      隐藏：defaults write com.apple.finder AppleShowAllFiles -bool false