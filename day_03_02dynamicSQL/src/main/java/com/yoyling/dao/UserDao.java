package com.yoyling.dao;

import com.yoyling.domain.QueryVo;
import com.yoyling.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 根据名称模糊查询用户信息
     * @param username
     * @return
     */
    List<User> findByName(String username);

    /**
     * 根据queryVo中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入的参数条件查询
     * @param user 查询条件有可能有用户名，有可能有性别，也有可能有地址，还有可能都有
     * @return
     */
    List<User> findUserByCondition(User user);

    /**
     * 根据queryvo中提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);
}
