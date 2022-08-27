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


# Function List

```$xslt

User
    Article Classification
        Article Filter
    Label
        Detail View
        Article/Q&A Filter
    Article
        Writing
        Edit
        Deletion
        Remark
        Like
        Detail View
    Q&A
        Ask Question
        Edit
        Deletion
        Detail View
        Remark
        Follow
        Set the comment as the best answer
        Filter resolved questions
        Filter unresolved questions
    User
        Detail View
        Profile Edit
        Password Update
        Concerned friends
        Fans View
    Notification
        Article/Q&A is followed
        Article/Q&A is remarked
        User is followed
        Set the notification as read
    Follow
        Concerned friends' article/Q&A View
        Concerned Q&A
        Q&A remarked by user
        Article liked by user
        Article remarked by user
    Search
        By article/Q&A title or content
        
Administrator
    User Management
        Enable/Disable
        Administrator set/cancel
    Operation Log
        Operation Category Filter
    Article Management
        Set to Official
        Set it to the top
        set to Excellent
        Approved(visible)
        Unapproved(invisible)
    Article Category Management
        Approved(visible)
        Unapproved(invisible)
        Classification Addition
    Q&A Management
        Approved(visible)
        Unapproved(invisible)
    Label Management
        Approved(visible)
        Unapproved(invisible)
        Label Addition
```

 
![开发者客栈.png](https://static.developers.pub/8a71564c56c74416bb81ce87f3f2e719?)
 

# Feature

##  Front-End

- Multi-terminal adaptation(IOS&Android, PC)
- Customize theme colors for enterprise users to customize themes
- The editor supports "Control + S" operation to save
- The editor supports "Control + V" operation to paste image

##  Back-End

- Logs with call chains for troubleshooting
- Distributed session to support cluster deployment
- User role rights are classified to facilitate user management
- Interface permission verification to facilitate safer interface operation

## Extensible functional interface

- Article/Q&A update comes with its own audit, can be accessed to the audit center for operation and management
- File storage abstract interface, can be used to customize the internal file storage service for enterprise
- Cache service abstract interface, can be used to customize the internal cache service for enterprise
- Search service abstract interface, can be used to customize the internal search service for enterprise

# Technology Stack

## Back-End

- Database: mysql
- Persistence Layer Framework: mybatis
- Database Connection Pool Management: hikaricp
- Database Paging Plug-in: github pagehelper
- MVC Framework: spring mvc
- Application Layer Container: spring boot
- Json Serialization Tool: fastjson
- Email Sending SDK: javax mail
- Qiniu Cloud Storage SDK: qiniu java sdk
- Server-side Page Rendering: thymeleaf

## Front-End

- Front-End Markdown Editor：mavon-editor
- Back-End JS Framework：vue
- User Side UI Framework：bootstrap
- Administrator side UI Framework: iview

