# netty-demo
## 项目介绍
   本项目包含**三种IO模型的使用**以及**对于Netty的基本应用**，适合Netty初学者学习。

## 项目结构
### 1. **io-model** （三种IO模型下的socket）
 - bio （传统阻塞io模型）
 - biowithexecutor （阻塞io加上线程池）
 - nio （同步非阻塞io模型）
 - aio （异步非阻塞io模型）

### 2. **netty-basic** （netty简单使用）
- helloworld （netty入门示例）
- separate,separate2 （消息分隔，解决粘包）
- serial （序列化）
- udp （udp 协议应用）
- http （http/https 协议应用）
- websocket （websocket 协议应用）
    

### 3. **netty-pro** （进阶使用）
- heartbeat （心跳，客户端定时将本机基本信息发送至服务器）
- filedownload （基于http的文件下载服务器）

