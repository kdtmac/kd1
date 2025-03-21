# Demo Spring Boot 项目

## 项目概述
基于Spring Boot 2.7.0的企业级应用脚手架，集成MyBatis和MySQL数据库支持，包含基础安全配置。

## 功能特性
- 用户认证模块：提供注册/登录功能，集成Spring Security实现权限控制
- 数据持久化：通过MyBatis实现MySQL数据库的CRUD操作
- REST API：基于Spring MVC实现标准RESTful接口
- 安全配置：包含CSRF防护和基础请求验证机制
- 容器化支持：提供Docker镜像构建配置

## 技术栈
- **核心框架**: Spring Boot 2.7.0
- **ORM框架**: MyBatis Spring Boot Starter 2.2.2
- **数据库**: MySQL 8.x
- **安全框架**: Spring Security
- **构建工具**: Maven

## 快速启动
```bash
# 克隆项目
git clone https://github.com/your-repo/kd0320.git

# 安装依赖
mvn clean install

# 启动应用
mvn spring-boot:run
```

## 环境配置
1. 创建MySQL数据库：
```sql
CREATE DATABASE demo_db CHARACTER SET utf8mb4;
```
2. 配置环境变量：
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Docker部署
```bash
# 构建镜像
mvn spring-boot:build-image

# 运行容器
docker run -p 8080:8080 -e SPRING_DATASOURCE_PASSWORD=your_password demo:0.0.1-SNAPSHOT
```

## 接口测试
```bash
# 运行单元测试
mvn test

# 集成测试（需启动应用）
curl http://localhost:8080/api/endpoint
```

## 版本更新
- 0.1.0 (2024-03-20) 初始版本，包含核心用户认证模块

## 安全规范

## 文档维护规范
1. 新增功能开发需同步更新对应功能模块文档
2. 版本发布时需在更新日志中添加版本说明
3. 接口变更需同步更新Swagger文档和测试用例
1. 生产环境必须配置HTTPS
2. 数据库密码应通过环境变量注入
3. 定期检查Spring Security的CVE公告

---
> 完整文档请参考项目中的JavaDoc和Swagger UI（访问 /swagger-ui.html）