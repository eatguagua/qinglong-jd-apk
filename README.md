# 介绍
青龙脚本，京东cookie获取上传APP
- 手机使用app
- 登录后，自动将cookie上传到青龙脚本服务器
- 源码全部开放

## 界面
![docs/app.png](docs/app.png)

![docs/web.png](docs/web.png)


# 使用方式
为了安全起见，请fork本项目就修改网址，应用id及密钥
1. fork 本项目
2. 修改代码Config.java中的青龙后台地址，CLIENT_ID,CLIENT_SECRET。 (需在青龙脚本配置一个应用)
3. 等待一段时间就会构建出apk了 ，在右侧Releases菜单下可见


# 笔记
接口参考文档：https://qinglong.ukenn.top

## 签名相关
jks 在docs目录下
密码： 123123
alias(别名): key0 

## github actions
需要生成base64时， 使用 gitbash 打开jks文件所在目录（docs）， 执行
```
openssl base64 < qinglong-client.jks | tr -d '\n' | tee some_signing_key.jks.base64.txt
```



