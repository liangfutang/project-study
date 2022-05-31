
# 一. [OAuth2.0](oauth2/src/main/java/com/zjut/study/auth/oauth2)
在spring中测试OAuth2.0等第三方平台网页授权，当前已经做了的网页授权有:   

## 1.1 微信授权:
微信的OAuth2.0网页授权经过如下几步:
+ step.1 公众号后台配置-  
[公众号后台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)  
![微信公众号后台配置-修改获取用户信息的域名](./pic/微信公众号后台配置-修改获取用户信息的域名.png)   
![微信公众号后台配置-修改获取用户信息的域名配置](./pic/微信公众号后台配置-修改获取用户信息的域名1.png)

+ step.2 配置微信授权平台和服务器间的签名校验   
`/oauth/2.0/signature/check`接口中做的授权平台和服务器间的签名校验   
![微信授权平台和服务器间的签名校验配置](./pic/微信授权平台和服务器间的签名校验配置.png)   

