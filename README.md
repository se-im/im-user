# IM关系服务
负责用户的注册登录更新注销
好友请求管理及好友关系的维护
群的管理以及群成员的管理

并提供dubbo服务供chat服务调用


## 运行所需环境

- jdk8+
- mysql5.7+  数据存储
- rocketmq   异步通知chat服务
- redis      缓存，单点登录
- nacos      注册中心

#### 如何运行
推荐在docker里运行
- `mvn package` 打jar包
- 安装docker环境
- `chmod +x start.sh && ./start.sh`
