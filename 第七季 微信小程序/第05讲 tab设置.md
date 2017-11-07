# 第05讲 tab设置

要使用 tab 必须至少有两个页面

## 新建 movies 页面

新建目录: `pages/movies`

##　设置启用 tab

### `app.json`

```json
{
  "pages": [
    "pages/welcome/welcome",
    "pages/news/news",
    "pages/news/news-detail/news-detail",
    "pages/movies/movies"
  ],
  "window": {
    "navigationBarBackgroundColor": "#405f80"
  },
  "tabBar": {
    "list": [
      {
        "pagePath": "pages/news/news",
        "text": "新闻",
        "iconPath": "images/tab/yuedu.png",
        "selectedIconPath": "images/tab/yuedu_hl.png"
      },
      {
        "pagePath": "pages/movies/movies",
        "text": "电影",
        "iconPath": "images/tab/dianying.png",
        "selectedIconPath": "images/tab/dianying_hl.png"
      }
    ]
  }
}
```

### `welcome.js`

```js
  onOpenHome: (event) =>{
    console.log(event);
    wx.switchTab({
      url: '../news/news',
    })
  },
```

> 要导航到 启用 tabBar 的页面,必须使用 switchTab

