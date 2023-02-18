# Mall

### 介绍

个人毕业设计，分布式电商项目

### 软件架构

1. renren-fast搭建后台系统
2. renren-generator代码生成器逆向构建项目
3. renren-fast-vue搭建后台前端项目
4. MySQL5.7做数据持久化
5. VirtualBox用Vagrant构建Centos7
6. Docker安装MySQL、Redis
7. Nacos做配置中心、注册中心
8. Gateway做网关

### 技术总结&使用说明

#### 分布式基础概念

- 集群是个物理形态，分布式是个工作方式
- 远程调用：在分布式系统中，各个服务可能处于不同主机，但是服务之间不可避免的相互调用，称为远程调用
- springcloud中使用HTTP+JSON的方式完成远程调用
- 服务注册/发现以及引入配置中心集中统一管理服务的配置信息
- 在微服务架构中，微服务之间通过网络进行通信，存在相互依赖，当其中一个服务不可用时，有可能会造成雪崩效应。要防止这样的情况，必须要有容错机制来保护服务

  - 服务熔断：在微服务架构中，微服务之间通过网络进行通信，存在相互依赖，当其中一个服务不可用时，有可能会造成雪崩效应。要防止这样的情况，必须要有容错机制来保护服务
  - 服务降级：在微服务架构中，微服务之间通过网络进行通信，存在相互依赖，当其中一个服务不可用时，有可能会造成雪崩效应。要防止这样的情况，必须要有容错机制来保护服务
- API网关

  - 客户端发送请求到服务器路途中，设置一个网关，请求都先到达网关，网关对请求进行统一认证（合法非法）和处理等操作。他是安检
  - 在微服务架构中，API gateway作为整体架构的重要组件，它抽象了微服务中都需要的公共功能，同时提供了客户端负载均衡，服务自动熔断，灰度发布，统一认证，限流流控，日志统计等丰富的功能，帮助我们解决很多API管理难题
- 前后分离开发，分为内网部署和外网部署，外网是面向公众访问的，部署前端项目；内网部署的是后端集群，前端在页面上操作发送请求到后端，在这途中会经过Nginx集群，Nginx把请求转交给API网关(springcloud gateway)（网关可以根据当前请求动态地路由到指定的服务），路由过来的请求如果很多，可以负载均衡地调用目标服务所在的服务器中的一台，当商品服务器出现问题也可以在网关层面对服务进行熔断或降级（Alibaba sentinel），网关还有其他的功能如认证授权、限流等
- 到达服务器后进行处理，服务与服务会相互调用（OpenFeign），有些请求可能经过登录才能进行（基于OAuth2.0的认证中心。安全和权限使用springSecurity控制）
- 服务保存了一些数据或者需要使用缓存，使用redis集群（分片+哨兵集群）。持久化使用mysql，读写分离和分库分表。
- 服务和服务之间使用消息队列（RabbitMQ），来完成异步解耦，分布式事务的一致性。有些服务需要全文检索，检索商品信息，使用ElaticSearch。
- 服务需要存取数据，使用阿里云的对象存储服务OSS。
- 项目上线后为了快速定位问题，使用ELK对日志进行处理，使用 LogStash收集业务里的各种日志，把日志存储到ES中，用Kibana可视化页面从ES中检索出相关信息，帮助我们快速定位问题所在。
- 在分布式系统中，由于每个服务都可能部署在很多台机器，服务和服务可能相互调用，就得知道彼此都在哪里，所以需要将所有服务都注册到注册中心。服务从注册中心（Alibaba Nacos）发现其他服务所在位置。每个服务的配置众多，为了实现改一处配置相同配置就同步更改，就需要配置中心（Alibaba Nacos），服务从配置中心中动态读取配置。
- 服务追踪，追踪服务调用哪里出现问题，使用springcloud提供的Sleuth、Zipkin、Metrics，把每个服务的信息交给Prometheus进行聚合分析，再由Grafana进行可视化展示，提供Prometheus提供的AlterManager实时得到服务的告警信息，以短信/邮件的方式告知开发人员。
- 提供持续集成和持续部署。项目发布起来后，因为微服务众多，每一个都打包部署到服务器太麻烦，有了持续集成后开发人员可以将修改后的代码提交到github，运维人员可以通过自动化工具Jenkins Pipeline将github中获取的代码打包成docker镜像，最终是由k8s集成docker服务，将服务以docker容器的方式运行。

#### Linux环境搭建

- 使用Oracle VM VirtualBox虚拟机搭建Linux、设置全局端口转发NAT网络模式
- Vagrant初始化CentOS/7，主机通过SSH连接虚拟机
- CentOS/7配置镜像源、安装NPM、YUM、Docker
- Docker环境下安装配置MySQL、Redis、Nginx

#### 开发环境搭建

- Maven配置镜像，建立本地仓库，设定Maven编译构建环境
- JDK1.8
- IDEA2022（集成Lombok），配置.gitignore文件
- Nacos（Dev期间置于Windows下，上线时归整到Linux下）
- VSCode（插件：Auto Close Tag、Auto Rename Tag、ESlint、HTML CSS Support、HTML Boilerplate、JavaScript ES6、Live Server、Open in browser、Vetur）
- Gitee&Git&GitHub
- Gitee开源项目renren-fast（后台管理）、renren-generator（代码生成器）、renren-fast-vue（项目后台WEB前端）本地初始化
- Navicat连接Linux并建库建表
- Node.js V12安装、配置镜像源、配置Python V3支持
- 配置aliyun OSS
- 配置Windows Terminal终端
- MD文档工具Typora、图床SM.MS
- API测试工具：APIPOST
- RedisGUI管理平台：RedisInsight。连接与配置

#### 逆向工程代码生成

**执行db/mysql.sql**

**renren-generator/../generator.properties**

```
#主目录
mainPath=com.zxj
#包名
package=com.zxj.mall
#模块名
moduleName=product
#作者&邮箱
author=Epiphany
email=zxjOvO@gmail.com
#表前缀
tablePrefix=pms_
```

**renren-generator/../application.yml**

```
url: jdbc:mysql://192.168.56.10:3306/mall-pms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
```

**启动服务并修改配置依次建立所需的所有模块代码**

#### 模块介绍

mall-common：公共服务依赖

mall-coupon：优惠服务

mall-member：会员服务

mall-order：订单服务

mall-product：商品服务

mall-ware：库存服务

mall-third-party：第三方服务

mall-gateway：服务网关路由

renren-fast：人人后端

renren-generator：人人生成器

#### Nacos配置文件

####
