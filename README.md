# *Sa*aS *Ap*p *Hi*gh-*Sp*eed *De*velopment *Pl*atform
-------

#### 一. 项目说明

    
基于Spring Cloud Hoxton.RELEASE 的快速开发平台, 集成了Nacos作为注册和配置中心、oauth2鉴权、Seata分布式事务、基于RBAC的操作权限以及数据权限、通知权限(阿里短信、极光APP推送、邮件通知)、Excel导入导出封装等
    

|类别                | 框架          
--------------     |----------- 
| 核心框架             |  springcloud Hoxton.RELEASE |
| 安全框架             |   Spring Security、 Spring Cloud Oauth2     |
| 持久层框架           | MyBatis-Plus        |
| 数据库连接池         | Alibaba Druid       |
| 消息队列             | RabbitMQ     |
| 缓存中间件             | Redis (Redisson)     |
| 分布式任务调度        | elastic-job       |
| 分布式事务        | Seata       |
    

---
#### 二. 技术栈及项目结构说明
```
cloudPlatform
├── core -- 基础核心模块集合
     ├── auth -- 认证服务
     ├── common -- 基础公共模块
          ├── basal -- 最基础模块，包含通用工具类、Redis、用户权限Entity和feign接口
          ├── upms -- UserPermissionsManagement 用户租户角色的curd [9009]
          └── security -- 权限和oauth2权限配置
     ├── eureke -- 注册中心 [已删除换用nacos]
     ├── gateway -- 网关 [8888]
     └── config -- 配置中心 [已删除换用nacos]
├── admin -- 后台管理模块
├── order -- 订单服务 TODO
├── stock -- 库存服务 TODO
├── finance -- 财务服务 TODO
├── app -- app基础接口 TODO
├── mes -- 对接mes TODO
└── visual  -- 图形化模块 TODO
     ├── monitor -- 微服务监控
     └── codegen -- 图形化代码生成
	 
```

---
#### 三. 开发要点

    0. 所有类注释上 用处、创建人、时间， 后增的方法上也要标注。 modify的代码段需写上原因、修改人、时间
    
    1. 所有接口必须保证幂等，特别是微服务之间调用修改的接口（Feign超时自动重发请求、消息队列重复处理）
    
    2. gateway使用了路由重试，所以接口必须符合RESTful，尤其是GET只能用来查询！
    
    3. 数据表结构必须有update_time 字段， 更新数据必须更新这个字段。方便数据增量备份和向es增量同步数据
    
    4. 前端form表单提交的button必须加loading， 前端进行一遍防重复提交的check


---


---
#### 四. 快速开始

    0. 准备环境： jdk1.8 、 nacos 、 RabbitMq 、 mysql 、 redis ...
    
    1. 导入sql文件 : doc/sql ; 导入nacos配置文件 : 公司gitlab
    
    2. 本地启动： 按顺序启动 ， 最后启动 gateway
    
    3. 项目打包：  mvn clean install package -P {dev|test|prod}
    
    4. 部署发布 ： 


---
