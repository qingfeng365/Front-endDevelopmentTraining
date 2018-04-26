# 03 模拟GET与POST请求


```py
'''
模拟GET与POST请求
'''

from urllib import request
from urllib import parse

# 普通GET

keyword = 'python'
url = "http://www.baidu.com/s?wd=" + keyword

req = request.Request(url)

data = request.urlopen(req).read()
'''
注意, 这样得到的只是一段脚本, 百度有反爬处理
'''

file = open('./baidu-python.html', 'wb')

file.write(data)
file.close()

# 如果查询串有中文, 需要编码

keyword = '机器学习'
url = "http://www.baidu.com/s?wd="

key_code = request.quote(keyword)
url_all = url + key_code

# key_code: %E6%9C%BA%E5%99%A8%E5%AD%A6%E4%B9%A0'''
print('key_code:', key_code)

# url_all: http://www.baidu.com/s?wd=%E6%9C%BA%E5%99%A8%E5%AD%A6%E4%B9%A0
print('url_all:', url_all)

req = request.Request(url_all)

data = request.urlopen(req).read()

file = open('./baidu-quote.html', 'wb')

file.write(data)
file.close()

# POST

url = "http://www.iqianyue.com/mypost/"
postData = parse.urlencode({"name": "python学习", "pass": "123456"})
'''
urlencode
将包含字符串或字节内容的映射对象或二维元组序列转换为ASCII文本字符
urlencode(query, doseq=False, safe='', encoding=None, errors=None, quote_via=quote_plus)

转换结果用于POST操作时，还需将其编码为字节
postData = parse.urlencode({...}).encode('utf-8')

返回结果需解码

response = request.urlopen(url, data=postData).read().decode('utf-8')

'''

# postData: name=python%E5%AD%A6%E4%B9%A0&pass=123456
print('postData:', postData)


# postData: b'name=python%E5%AD%A6%E4%B9%A0&pass=123456'
postData = postData.encode('utf-8')

print('postData:', postData)

req = request.Request(url, postData)

req.add_header(
    'User-Agent',
    'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0'
)

data = request.urlopen(req).read()

file = open('./post.html', 'wb')

file.write(data)
file.close()



```

