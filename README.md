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

```java
public interface UserDao {
    @Select("select * from user")
    List<User> findAll();
}
```

```xml
<!-- SqlMapConfig.xml中的<configuration>标签中的<mappers>需要改动 -->
<mappers>
   <mapper class="com.yoyling.dao.UserDao"/>
</mappers>
```

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

UserDao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
		PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yoyling.dao.UserDao">

	<!-- 配置查询结果的列名和实体类的属性名的对应关系 -->
	<resultMap id="userMap" type="user">
		<!-- 主键字段对应 -->
		<id property="userId" column="id"/>
		<!-- 非主键字段对应 -->
		<result property="userName" column="username"/>
		<result property="userAddress" column="address"/>
		<result property="userSex" column="sex"/>
		<result property="userBirthday" column="birthday"/>
	</resultMap>

	<!-- 查询所有 -->
	<select id="findAll" resultMap="userMap">
		<!--select id as userId,username as userName,address as userAddress,sex as userSex,birthday as userBirthday from user;-->
		select * from user;
	</select>

	<!-- 保存用户 -->
	<insert id="saveUser" parameterType="user">
		<!-- 配置插入操作后，获取插入数据的id -->
		<selectKey keyProperty="userId" keyColumn="id" resultType="int" order="AFTER">
			select last_insert_id();
		</selectKey>
		insert into user(username,address,sex,birthday)values(#{userName},#{userAddress},#{userSex},#{userBirthday});
	</insert>

	<!-- 更新用户 -->
	<update id="updateUser" parameterType="user">
		update user set username=#{userName},address=#{userAddress},sex=#{userSex},birthday=#{userBirthday} where id=#{userId};
	</update>

	<!-- 删除用户 -->
	<delete id="deleteUser" parameterType="int">
		delete from user where id = #{uid}
	</delete>

	<!-- 根据id查询用户 -->
	<select id="findById" parameterType="int" resultMap="userMap">
		select * from user where id = #{uid}
	</select>

	<!-- 根据名称模糊查询 -->
	<select id="findByName" parameterType="String" resultMap="userMap">
 		select * from user where username like #{username}
		<!-- select * from user where username like '%${value}%' -->
	</select>

	<!-- 查询总用户数 -->
	<select id="findTotal" resultType="int">
		select count(id) from user;
	</select>

	<!-- 根据queryVo的条件查询用户 -->
	<select id="findUserByVo" parameterType="com.yoyling.domain.QueryVo" resultMap="userMap">
		select * from user where username like #{user.userName}
	</select>
</mapper>
```



MybatisTest.java

```java
package com.yoyling.test;

/**
 * 测试mybatis的crud操作
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before     //用于在测试方法执行之前执行
    public void init() throws Exception {
        in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After     //用于在测试方法执行之后执行
    public void destroy() throws Exception {
        //提交事务
        sqlSession.commit();

        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUserName("yoyling 最后插入");
        user.setUserAddress("福建省厦门市");
        user.setUserSex("男");
        user.setUserBirthday(new Date());

        System.out.println("保存操作前：" + user);
        userDao.saveUser(user);
        System.out.println("保存操作后：" + user);
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setUserId(50);
        user.setUserName("mybatis");
        user.setUserAddress("福建省漳州市");
        user.setUserSex("女");
        user.setUserBirthday(new Date());

        userDao.updateUser(user);
    }

    /**
     * 测试删除操作
     */
    @Test
    public void testDelete() {
        userDao.deleteUser(50);
    }

    /**
     * 测试查询一个操作
     */
    @Test
    public void testFindOne() {
        User user = userDao.findById(48);
        System.out.println(user);
    }

    /**
     * 测试模糊查询操作
     */
    @Test
    public void testFindByName() {
        List<User> users = userDao.findByName("%王%");
//        List<User> users = userDao.findByName("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试查询总记录条数操作
     */
    @Test
    public void testFindTotal() {
        int count = userDao.findTotal();
        System.out.println(count);
    }

    /**
     * 测试使用QueryVo作为查询条件
     */
    @Test
    public void testFindByVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUserName("%王%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }
}
```

**resultMap**

配置**查询结果**的列名和实体类的属性名的对应关系

```xml
<resultMap id="userMap" type="user">
   <!-- 主键字段对应 -->
   <id property="userId" column="id"/>
   <!-- 非主键字段对应 -->
   <result property="userName" column="username"/>
   <result property="userAddress" column="address"/>
   <result property="userSex" column="sex"/>
   <result property="userBirthday" column="birthday"/>
</resultMap>
```

```xml
<!-- 查询所有 -->
<select id="findAll" resultMap="userMap">
```



**SqlMapConfig.xml中的<typeAliases>**

**typeAlias**用于配置别名。**type**属性指定的是实体类全限定类名。**alias**属性指定别名，当指定了别名就不再区分大小写。

```xml
<typeAliases>
   <typeAlias type="com.yoyling.domain.User" alias="user"/>
</typeAliases>
```

用于指定要配置别名的包，当指定之后该包下的实体类都会注册别名，并且类名就是别名，不再区分大小写。

```xml
<typeAliases>
   <package name="com.yoyling.domain"/>
</typeAliases>
```

```xml
<!-- 保存用户中的parameterType则可以直接写不区分大小写的类名 -->
<insert id="saveUser" parameterType="user">
```



**SqlMapConfig.xml中把jdbc连接信息提取到外面的properties文件**

```xml
<properties resource="jdbcConfig.properties"/>
<environments default="mysql">
	<environment id="mysql">
		<transactionManager type="JDBC"/>
		<dataSource type="POOLED">
			<property name="driver" value="${jdbc.driver}"/>
			<property name="url" value="${jdbc.url}"/>
			<property name="username" value="${jdbc.username}"/>
			<property name="password" value="${jdbc.password}"/>
		</dataSource>
	</environment>
</environments>
```

**jdbcConfig.properties**

```properties
jdbc.driver = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/mybatistest
jdbc.username = root
jdbc.password = root
```

## 02_02mybatisDAO

mybatis也可以自己编写Dao实现或者使用代理Dao实现。

**UserDaoImpl.java**

```java
package com.yoyling.dao.impl;

public class UserDaoImpl implements UserDao {

    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        //1.根据factory获取SqlSession对象
        SqlSession session = factory.openSession();
        //2.调用SqlSession中的方法，实现查询列表
        List<User> users = session.selectList("com.yoyling.dao.UserDao.findAll");//参数就是能获取配置信息的key
        //3.释放资源
        session.close();
        return users;
    }

    @Override
    public void saveUser(User user) {
        //1.根据factory获取SqlSession对象
        SqlSession session = factory.openSession();
        //2.调用方法实现保存
        session.insert("com.yoyling.dao.UserDao.saveUser",user);
        //3.提交事务
        session.commit();
        //4.释放资源
        session.close();
    }

    @Override
    public void updateUser(User user) {
        //1.根据factory获取SqlSession对象
        SqlSession session = factory.openSession();
        //2.调用方法实现更新
        session.update("com.yoyling.dao.UserDao.updateUser",user);
        //3.提交事务
        session.commit();
        //4.释放资源
        session.close();
    }
}
```

**MybatisTest.java**

```java
package com.yoyling.test;

/**
 * 测试mybatis的crud操作
 */
public class MybatisTest {

    private InputStream in;
    private UserDao userDao;

    @Before     //用于在测试方法执行之前执行
    public void init() throws Exception {
        in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        userDao = new UserDaoImpl(factory);
    }

    @After     //用于在测试方法执行之后执行
    public void destroy() throws Exception {
        in.close();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("yoyling 最后插入");
        user.setAddress("福建省厦门市");
        user.setSex("男");
        user.setBirthday(new Date());

        System.out.println("保存操作前：" + user);
        userDao.saveUser(user);
        System.out.println("保存操作后：" + user);
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(49);
        user.setUsername("mybatis");
        user.setAddress("福建省漳州市");
        user.setSex("女");
        user.setBirthday(new Date());

        userDao.updateUser(user);
    }
}
```

## 03_01datasourceAndTx

**连接池：**

* 我们在实际开发中都会使用连接池。
* 因为它可以减少我们获取连接所消耗的时间。

**mybatis中的连接池：**

主配置文件SqlMapConfig.xml中的dataSource标签，type属性就是表示采用何种连接池方式。



**type属性的取值：**

**POOLED ** 采用传统的javax.sql.DataSource规范中的连接池，mybatis中有针对规范的实现。

**UNPOOLED**  采用传统的获取连接的方式，虽然也实现Javax.sql.DataSource接口，但是并没有使用池的思想。

**JNDI ** 采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样。



**mybatis中的事务**

什么是事务、事务的四大特性ACID、不考虑隔离性会产生的3个问题、解决办法：四种隔离级别

它是通过sqlsession对象的commit方法和rollback方法实现事务的提交和回滚

## 03_02dynamicSQL

MyBatis动态SQL（select查询）常用标签： <where><if><foreach>

```xml
<!-- 了解的内容:抽取重复的sql语句 -->
<sql id="defaultUser">
   select * from user
</sql>

<!-- 查询所有 -->
<select id="findAll" resultMap="userMap">
   <include refid="defaultUser"/>
</select>

<!-- 根据条件查询
<select id="findUserByCondition" resultMap="userMap" parameterType="user">
   select * from user where 1 = 1
   <if test="userName != null">
      and username = #{userName}
   </if>
   <if test="userSex != null">
      and sex = #{userSex}
   </if>
</select> -->
<select id="findUserByCondition" resultMap="userMap" parameterType="user">
   <include refid="defaultUser"/>
   <where>
      <if test="userName != null">
         and username = #{userName}
      </if>
      <if test="userSex != null">
         and sex = #{userSex}
      </if>
   </where>
</select>

<!-- 根据queryvo中提供的id集合查询用户列表 -->
<select id="findUserInIds" resultMap="userMap" parameterType="queryvo">
   <include refid="defaultUser"/>
   <where>
      <if test="ids != null and ids.size()>0">
         <foreach collection="ids" open="and id in (" close=")" item="id" separator=",">
            #{id}
         </foreach>
      </if>
   </where>
</select>
```

多表关系操作，添加账户表：

```SQL
DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `ID` int(11) NOT NULL COMMENT '编号',
  `UID` int(11) default NULL COMMENT '用户编号',
  `MONEY` double default NULL COMMENT '金额',
  PRIMARY KEY  (`ID`),
  KEY `FK_Reference_8` (`UID`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `account`(`ID`,`UID`,`MONEY`) values (1,46,1000),(2,45,1000),(3,46,2000);
```
## 03_03one2many

一对一和一对多
