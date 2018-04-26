# 02 模拟浏览器header

```py
'''
模拟浏览器header
'''

from urllib import request

# 20180331 访问该网址已经没有检查header反爬虫了
url = "http://blog.csdn.net/weiwei_pig/article/details/51178226"

'''
res = request.urlopen(url)

# 获取网页环境信息
print(res.info())

# 状态码
print(res.getcode())

# url
print(res.geturl())
'''

# 获取浏览器 header 方法
'''
在浏览器,打开开发者工具, Network 面板
Headers 页签, Request Headers , User-agent
'''

header = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"

# 先获得 Request 对象
req = request.Request(url)

'''
request.Request 返回 request 对象

request = urllib.request.Request(url, data=None, headers={}, origin_req_host=None, unverifiable=False, method=None)
# url 包含网址的字符串
# data 要发送给服务器的数据对象，对于POST请求，要通过urllib.parse.urlencode() 方法进行编码
# header 头部信息，必须为字典类型
# method 请求方法，如果data为None则为GET，否则为POST

头部信息示例

headers = {
     'User-Agent': r'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                   r'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
     'Referer': r'http://www.lagou.com/zhaopin/Python/?labelWords=label',
     'Connection': 'keep-alive'
  }



# User-Agent ：这个头部可以携带如下几条信息：浏览器名和版本号、操作系统名和版本号、默认语言

# Referer：可以用来防止盗链，有一些网站图片显示来源http://*.com，就是检查Referer来鉴定的

# Connection：表示连接状态，记录Session的状态。

也可以通过返回的request对象的 add_header(key, val) 方法来添加header信息

'''

req.add_header("User-Agent", header)

res = request.urlopen(req)

# print(res.info())

html = res.read()
htmlstr = str(html, encoding='utf-8')

'''
print(htmlstr)

'''

# 超时设置

for i in range(1,100):
  try:
    # 设置成 1 秒, 有可能出现超时异常
    # res = request.urlopen("http://yum.iqianyue.com", timeout=1)
    # 设置成 5 秒
    res = request.urlopen("http://yum.iqianyue.com", timeout=5)
    
    data = res.read()
    print(i,' ',res.getcode())
    # print(len(data))
  except Exception as e:
    print(i," 出现异常:", str(e))
    '''出现异常: <urlopen error timed out>'''
print('end')



```