# 07 读取csdn博客链接

代码要点:

- opener 如何设置 headers
- list 如何去除重复元素

```py
import re
import urllib.request


def getlink(url):
    headers = (
        "User-Agent",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0"
    )
    opener = urllib.request.build_opener()
    opener.addheaders = [headers]
    urllib.request.install_opener(opener)
    res = urllib.request.urlopen(url)

    data = str(res.read())

    # 原来的写法, 有问题, 因为 (\w|/) 遇到问号 ? 就会结束
    # pattern = r'(https?://[^\s)";]+\.(\w|/)*)'

    # 正确的写法, 遇到 空白字符 " 就会结束
    pattern = r'(https?://[^\s)";]+)'

    link = re.compile(pattern).findall(data)
    # 把 list 转成集合, 再转回 list , 去掉重复的元素
    link = list(set(link))
    return link


url = "http://blog.csdn.net/"
linklist = getlink(url)
for link in linklist:
    print(link)

```