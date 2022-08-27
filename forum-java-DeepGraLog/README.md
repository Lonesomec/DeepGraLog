# Introduction

forum-java is an open source and modern community platform, and it does：
- Content oriented discussion forum;
- Q&A oriented cyber community;
- Fully open source;

- Thanks to the Icebreaking Security Laboratory for helping to do the project security vulnerability scanning, so as to ensure that every user can use the system without fear of vulnerabilities attack;

# Motivation

- Most Internet companies devote themselves to constructing the open platform field, so it is indispensible to develop a developer community website for developers to learn and communicate.(This project refers to the community functional requirements developed by top domestic IT companies, such as wechat open platform, Toutiao developer community, Youzanyun developer community, and Taobao open platform);
- Most of the existing online open source communities are developed by PHP language and Java, Few companies develop with the Spring Boot framework.The internal development language of most Internet companies is Java, and many of them are using Spring Boot/Cloud development framework. For the PHP open source community, the internal system of the company cannot be well accessed and administrated;
- Existing open source community functions are too simple to meet most of the needs of the enterprise;
- Existing open source communities have an old-fashioned interface style, an aesthetic that has not follow the trend, and poor customization;
- Existing open source communities lack the functionality required to actually operate, and the management functionality is too simple and expensive to develop;


# Function list

```$xslt

用户端
    文章分类
        筛选文章
    标签
        查看详情
        筛选文章/问答
    文章
        写文章
        编辑
        删除
        评论
        点赞
        查看详情
    问答
        提问题
        编辑
        删除
        查看详情
        评论
        关注
        设置评论为最佳答案
        筛选已解决问题
        筛选未解决问题
    用户
        查看详情
        编辑个人资料
        更新登录密码
        关注好友
        查看粉丝
    消息
        文章/问答被关注通知
        文章/问答被评论通知
        个人被关注通知
        设置消息为已读
    关注
        关注的用户文章/问答
        关注的问答
        评论的问答
        点赞的文章
        评论的文章
    搜索
        根据文章/问答标题/内容模糊搜索
        
管理端
    用户管理
        禁用/启用
        设置为管理员/取消管理员
    操作日志
        操作类别筛选
    文章管理
        设置为官方
        设置为置顶
        设置为加精
        审核通过（可见）
        审核不过（不可见）
    文章类别管理
        审核通过（可见）
        审核不过（不可见）
        新增分类
    问答管理
        审核通过（可见）
        审核不过（不可见）
    标签管理
        审核通过（可见）
        审核不过（不可见）
        新增标签
```

 
![开发者客栈.png](https://static.developers.pub/8a71564c56c74416bb81ce87f3f2e719?)
 

# 五 特性

##  前端

- 多终端适配（手机端，pc端）
- 自定义主题颜色，方便企业用户自定义主题
- 编辑器支持control + s保存
- 编辑器支持control + v复制图片上传

##  后端

- 日志带有调用链，方便排查问题
- 分布式session，支持集群部署
- 用户角色权限分级，便于用户管理
- 接口权限校验，接口操作更安全

## 可扩展功能接口

- 文章/问答更新时自带审核，可接入审核中心便于运营管理
- 文件存储抽象接口，可支持自定义接入企业内部文件储存服务
- 缓存服务抽象接口，可支持自定义接入企业内部缓存服务
- 搜索服务抽象接口，可支持自定义接入企业内部搜索服务

# 六 技术栈

## 后端

- 数据库：mysql
- 持久层框架：mybatis
- 数据库连接池管理：hikaricp
- 数据库分页插件：github pagehelper
- mvc框架：spring mvc
- 应用层容器：spring boot
- json序列化工具：fastjson
- 邮件发送sdk：javax mail
- 七牛云存储sdk：qiniu java sdk
- 服务端页面渲染：thymeleaf

## 前端

- 前端markdown编辑器：mavon-editor
- 管理后台js框架：vue
- 用户端UI框架：bootstrap
- 管理后台UI框架 iview

# 七 部分页面展示

## 用户页面展示

- 首页

 
![首页]( https://static.developers.pub/81c6695a0e374ea89eb4037ff248259c)
 

 
![image.png](https://static.developers.pub/5092d67341a14cc6b155d21727a79227)
 


- 问答页

 
![问答页]( https://static.developers.pub/bfe0760841cd444a88942b9131355d30)
 

 
![image.png](https://static.developers.pub/ee20c1508a234b229613d681dc3cd913)
 


- 关注页

 
![image.png](https://static.developers.pub/cf523137fa964bb0a60691b7b37a2594)
 

- 消息列表页

 
![image.png](https://static.developers.pub/ff047bbafb6d43b2b497ee7188d5b6c2)
 

- 文章详情页

 
![文章详情页]( https://static.developers.pub/e537e76e4ad34177b2ab3a5b21624f25)
 


 
![image.png](https://static.developers.pub/83e559536c0e48408d276f96de9ed5fc)
 



- 标签详情页

 
![标签详情页]( https://static.developers.pub/57d3af8df85e421fba035dcc688fbf1c)
 

- 搜索页

 
![image.png](https://static.developers.pub/47dfbec4db884c668734df94749d2410)
 


- 用户主页

 
![用户主页](https://static.developers.pub/37da306856a844f5b6e9194f8a3217f2)
 

 
![image.png](https://static.developers.pub/02897af0bc794e3b9b9a4cc8d429cd14)
 


- 写文章页

 
![写文章页]( https://static.developers.pub/359f88bd5c6240b7aceb52cbf4f23ed5)
 

# 八 管理后台页面

 
![image.png](https://static.developers.pub/17475abfff6442fc8cb102301379c0e0)
 

# 九 安装

请参考 [forum-java安装指南](https://www.developers.pub/article/1005736)。

# 使用说明

**社区版只允许个人使用。商业用途请联系作者购买。**
