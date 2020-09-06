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

### 什么是框架？

它是我们软件开发中的一套解决方案，不同的框架解决的是不同的问题。

使用框架的好处：框架封装了很多的细节，使开发者可以使用极简的方式实现功能。大大提高开发效率。

### 三层架构

表现层：是用于展示数据的

业务层：是处理业务需求

持久层：是和数据库交互的

### 持久层技术解决方案

1. JDBC技术：**Connection**、**PreparedStatement**、**ResultSet**

2. Spring的**JdbcTemplate**：Spring中对jdbc的简单封装

3. Apache的**DBUtils**：它和Spring的JdbcTemplate很像，也是对Jdbc的简单封装

   以上这些都不是框架，JDBC是规范，Spring的JdbcTemplate和Apache的DBUtils都只是工具类

### mybatis的概述

mybatis是一个持久层框架，用java编写的。

它封装了jdbc操作的很多细节，使开发者只需要关注sql语句本身，而无需关注注册驱动，创建连接等繁杂过程。

它使用了ORM思想实现了结果集的封装。

**ORM：Object Relational Mappging 对象关系映射**

简单的说：就是把数据库表和实体类及实体类的属性对应起来，让我们可以操作实体类就实现操作数据库表。



## 01_01mybatis入门

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
(42,'小二王','2018-03-02 15:09:37','女','福建'),
(43,'小二王','2018-03-04 11:34:34','女','厦门'),
(45,'老六','2018-03-04 12:04:06','男','西藏'),
(46,'老王','2018-03-07 17:37:26','男','新疆'),
(48,'小马宝莉','2018-03-08 11:44:00','女','湖南');
```

### mybatis的环境搭建：

1. 创建maven工程并导入坐标

2. 创建实体类和dao的接口

3. 创建Mybatis的主配置文件

   SqlMapConifg.xml

4. 创建映射配置文件

   UserDao.xml

### 环境搭建的注意事项：

1. 创建UserDao.xml 和 UserDao.java时名称是为了和我们之前的知识保持一致。在Mybatis中它把持久层的操作接口名称和映射文件也叫做：Mapper

   所以：UserDao 和 UserMapper是一样的

2. 在idea中创建目录的时候，它和包是不一样的

   包在创建时：com.yoyling.dao它是三级结构

   目录在创建时：com.yoyling.dao是一级目录

3. mybatis的映射配置文件位置必须和dao接口的包结构相同

4. 映射配置文件的mapper标签namespace属性的取值必须是dao接口的全限定类名

5. 映射配置文件的操作配置（select），id属性的取值必须是dao接口的方法名

   当我们遵从了第三，四，五点之后，我们在开发中就无须再写dao的实现类。

### mybatis的入门案例：

1. 读取配置文件
2. 第二步：创建SqlSessionFactory工厂
3. 创建SqlSession
4. 创建Dao接口的代理对象
5. 执行dao中的方法
6. 释放资源



实体类**User.java**

接口**UserDao**，一个 **List<User> findAll()**

**测试main方法：**

```Java
//1.读取配置文件
InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
//2.创建SqlSessionFactory工厂
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(in);
//3.使用工厂生产SqlSession对象
SqlSession session = factory.openSession();
//4.使用SqlSession创建Dao接口的代理对象
UserDao userDao = session.getMapper(UserDao.class);
//5.使用代理对象执行方法
List<User> users = userDao.findAll();
for (User user : users) {
    System.out.println(user);
}
//6.释放资源
session.close();
in.close();
```

**SqlMapConfig.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
		PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- mybatis的主配置文件 -->
<configuration>
	<!-- 配置环境 -->
	<environments default="mysql">
		<!-- 配置mysql环境 -->
		<environment id="mysql">
			<!-- 配置事务的类型 -->
			<transactionManager type="JDBC"/>
			<!-- 配置数据源（连接池） -->
			<dataSource type="POOLED">
				<!-- 配置连接数据库的4个基本信息的 -->
				<property name="driver" value="com.mysql.jdbc.Driver"/>
				<property name="url" value="jdbc:mysql://localhost:3306/mybatistest"/>
				<property name="username" value="root"/>
				<property name="password" value="root"/>
			</dataSource>
		</environment>
	</environments>

	<!-- 指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件 -->
	<mappers>
		<mapper resource="com/yoyling/dao/UserDao.xml"/>
	</mappers>
</configuration>
```

**UserDao.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
		PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yoyling.dao.UserDao">
	<select id="findAll" resultType="com.yoyling.domain.User">
		select * from user;
	</select>
</mapper>
```



### 注意事项：

不要忘记在映射配置中告知mybatis要封装到哪个实体类中

配置的方式：指定实体类的全限定类名

## 01_02mybatis_annotation

**mybatis基于注解的入门案例：**

把UserDao.xml移除，在dao接口的方法上使用@Select注解，并且指定SQL语句。

同时需要在SqlMapConfig.xml中的mapper配置时，使用class属性指定dao接口的全限定类名。

**明确：**

我们在实际开发中，都是越简便越好，所以都是采用不写dao实现类的方式。

不管使用XML还是注解配置。

但是Mybatis它是支持写dao实现类的。

## 01_03mybatis_dao

Mybatis的dao实现类

## 01_04mybatis_design

**自定义Mybatis的分析：**

mybatis在使用代理dao的方式实现增删改查时做什么事呢？

1. 创建代理对象
2. 在代理对象中调用selectList

**自定义mybatis能通过入门案例看到类：**

- class Resources
- class SqlSessionFactoryBuilder
- interface SqlSessionFactory
- interface SqlSession

## 02_01mybatisCRUD



## 02_02mybatisDAO