package com.yoyling.test;

import com.yoyling.dao.RoleDao;
import com.yoyling.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class RoleTest {
    private InputStream in;
    private SqlSession sqlSession;
    private RoleDao roleDao;

    @Before     //用于在测试方法执行之前执行
    public void init() throws Exception {
        in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession(true);
        roleDao = sqlSession.getMapper(RoleDao.class);
    }

    @After     //用于在测试方法执行之后执行
    public void destroy() throws Exception {
        //提交事务
        //sqlSession.commit();

        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<Role> roles = roleDao.findAll();
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}
