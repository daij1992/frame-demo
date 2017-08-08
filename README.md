# 1.快速体验

标签（空格分隔）： Frame

---

# 运行环境
* IDE: Eclipse / IntelliJ IDEA
* JDK 6+
* MySQL 5.6
* Tomcat 6+
* Maven 3.3+
* SVN源码:


# 1. 运行SQL脚本
```
CREATE TABLE `frame_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id 主键',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `passwd` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `addr` varchar(255) NOT NULL COMMENT '地址',
  `birth` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

# 2. 检查数据库配置
**修改 src/main/assemlbly 下config.properties 的数据库配置**


# 3. 体验



## 3.1 转发

* 3.1.1 示例(GET):根据传入地址进行站内转发
    * URL:http://localhost:8080/frame-demo/jump/forward
    * PARAMS:
        * relativePath:相对路径
    * demo:http://localhost:8080/frame-demo/jump/forward?relativePath=/pages/forward.html



## 3.2 重定向

* 3.2.1 示例(GET):根据传入地址进行重定向
* URL:http://localhost:8080/frame-demo/jump/redirect
    * PARAMS:
        * absolutePath：绝对路径
    * demo:http://localhost:8080/frame-demo/jump/redirect?absolutePath=http://www.baidu.com

## 3.3 分页+排序

* 3.3.1 示例(GET):根据分页参数和排序参数进行分页排序
    * URL:http://localhost:8080/frame-demo/v1/users
    * PARAMS:
        * pageNum:请求页码
        * pageSize:每页大小
        * sort(非必填):排序列和排序方式
    * demo: http://localhost:8080/frame-demo/v1/users?pageNum=1&pageSize=10&sort=id desc


## 3.4 增/删/查
* 3.4.1 示例(POST): 新增用户(所有参数不能为空,且用户名(username)不能重复)
    * URL:http://localhost:8080/frame-demo/v1/users
    * PARAMS:
        * username:用户名 非空 不能重复
        * addr:地址 非空
        * email:邮箱 非空
        * birth:生日 年-月-日 非空
        * passwd:密码 非空
    * 异常码:
        * E11001=参数不合法:{0}


* 3.4.2 示例(DELETE):删除用户(用户必须存在)
    * URL:http://localhost:8080/frame-demo/v1/users/{id}
    * 异常码:
        * E11003=删除用户失败:{0}
    * demo:http://localhost:8080/frame-demo/v1/users/4

* 3.4.3 示例(GET):通过id查询用户信息
    * URL: http://localhost:8080/frame-demo/v1/users/{id}
    * demo:http://localhost:8080/frame-demo/v1/users/3

## 3.5 异常

* 示例:删除一个不存在的用户-->3.4.2 接口

* 示例:新增用户时用户名重复或者必填参数为空-->3.4.1 接口

## 3.6 Service/Dao 单元测试
**参考src/test下代码**

## 3.6 动态打包
* 测试环境打包: clean install -Ptest -Dmaven.test.skip=true
* 生产环境打包: clean install -Pproduct -Dmaven.test.skip=true

## 3.7 Druid Web 监控

* URL:http://localhost:8080/frame-demo/druid/index.html
* 效果:
    * ![image_1bk1oi5p11uj3965dfn1h1v1oin9.png-150.8kB][1]
    * ![image_1bk1ojhf5rt71rash1v1jiu1ecnm.png-143.6kB][2]


  [1]: http://static.zybuluo.com/daijie1992/t6u5nk6gtvymuu9lhif76fh2/image_1bk1oi5p11uj3965dfn1h1v1oin9.png
  [2]: http://static.zybuluo.com/daijie1992/o154hr0jhx1qna2nbqtu6ibi/image_1bk1ojhf5rt71rash1v1jiu1ecnm.png