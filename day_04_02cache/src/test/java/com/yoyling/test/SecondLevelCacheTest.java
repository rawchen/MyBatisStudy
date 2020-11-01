package com.yoyling.test;

import com.yoyling.dao.UserDao;
import com.yoyling.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class SecondLevelCacheTest {
    private InputStream in;
    SqlSessionFactory factory;
//    private SqlSession sqlSession;
//    private UserDao userDao;

    @Before     //用于在测试方法执行之前执行
    public void init() throws Exception {
        in = Resources.getResourceAsStream("sqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);


//        sqlSession = factory.openSession(true);
//        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After     //用于在测试方法执行之后执行
    public void destroy() throws Exception {
        //提交事务
        //sqlSession.commit();

//        sqlSession.close();
        in.close();
    }

    /**
	 * 测试一级缓存
     */
    @Test
    public void testFirstLevelCache(){
        SqlSession sqlSession1 = factory.openSession();
        UserDao userDao1 = sqlSession1.getMapper(UserDao.class);
        User user1 = userDao1.findById(41);
        System.out.println(user1);
        sqlSession1.close();//一级缓存消失


        SqlSession sqlSession2 = factory.openSession();
        UserDao userDao2 = sqlSession2.getMapper(UserDao.class);
        User user2 = userDao2.findById(41);
        System.out.println(user2);
        sqlSession2.close();

        System.out.println(user1==user2);
    }
}
