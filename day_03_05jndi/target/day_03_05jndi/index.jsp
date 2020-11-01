<%@ page import="com.yoyling.dao.UserDao" %>
<%@ page import="com.yoyling.domain.User" %>
<%@ page import="org.apache.ibatis.io.Resources" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactoryBuilder" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>
<%
	//1.读取配置文件
	InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
	//2.创建SqlSessionFactory工厂
	SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	SqlSessionFactory factory = builder.build(in);
	//3.使用工厂生产SqlSession对象
	SqlSession sqlSession = factory.openSession();
	//4.使用SqlSession创建Dao接口的代理对象
	UserDao userDao = sqlSession.getMapper(UserDao.class);
	//5.使用代理对象执行方法
	List<User> users = userDao.findAll();
	for (User user : users) {
		System.out.println(user);
	}
	//6.释放资源
	sqlSession.close();
	in.close();
%>
</body>
</html>
