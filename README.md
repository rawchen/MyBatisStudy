# MyBatisStudy

### MyBatis 3 学习记录4阶段

--------------------------------------------------------

**（1）mybatis入门**

1. mybatis的概述
2. mybatis的环境搭建
3. mybatis入门案例
4. 自定义mybatis框架（主要的目的是为了让大家了解mybatis中执行细节） 

---------------------------------------------------------------

**（2）mybatis基本使用**

1. mybatis的单表crud操作
2. mybatis的参数和返回值
3. mybatis的dao编写
4. mybatis配置的细节
5. 几个标签的使用

**（3）mybatis的深入和多表**

1. mybatis的连接池

2. mybatis的事务控制及设计的方法

3. mybatis的多表查询

   一对多-----多对一------多对多

**（4）mybatis的缓存和注解开发**

1. mybatis中的加载时机（查询的时机）

2. mybatis中的一级缓存和二级缓存

3. mybatis的注解开发

   单表CRUD------多表查询

## 什么是框架？

它是我们软件开发中的一套解决方案，不同的框架解决的是不同的问题。

使用框架的好处：框架封装了很多的细节，使开发者可以使用极简的方式实现功能。大大提高开发效率。

## 三层架构

表现层：是用于展示数据的

业务层：是处理业务需求

持久层：是和数据库交互的

## 持久层技术解决方案

1. JDBC技术：**Connection**、**PreparedStatement**、**ResultSet**

2. Spring的**JdbcTemplate**：Spring中对jdbc的简单封装

3. Apache的**DBUtils**：它和Spring的JdbcTemplate很像，也是对Jdbc的简单封装

   以上这些都不是框架，JDBC是规范，Spring的JdbcTemplate和Apache的DBUtils都只是工具类

## mybatis的概述

mybatis是一个持久层框架，用java编写的。

它封装了jdbc操作的很多细节，使开发者只需要关注sql语句本身，而无需关注注册驱动，创建连接等繁杂过程。

它使用了ORM思想实现了结果集的封装。

**ORM：Object Relational Mappging 对象关系映射**

简单的说：就是把数据库表和实体类及实体类的属性对应起来，让我们可以操作实体类就实现操作数据库表。



## day_01_01mybatis

**建库建表：mybatistest.sql**

```SQL
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `birthday` datetime default NULL COMMENT '生日',
  `sex` char(1) default NULL COMMENT '性别',
  `address` varchar(256) default NULL COMMENT '地址',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`username`,`birthday`,`sex`,`address`) 
values 
(41,'老王','2018-02-27 17:47:08','男','北京'),
(42,'小二王','2018-03-02 15:09:37','女','北京金燕龙'),
(43,'小二王','2018-03-04 11:34:34','女','北京金燕龙'),
(45,'传智播客','2018-03-04 12:04:06','男','北京金燕龙'),
(46,'老王','2018-03-07 17:37:26','男','北京'),
(48,'小马宝莉','2018-03-08 11:44:00','女','北京修正');
```

