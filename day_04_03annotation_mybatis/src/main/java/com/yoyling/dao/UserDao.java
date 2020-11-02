package com.yoyling.dao;

import com.yoyling.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * CRUD四个注解: @Select @Insert @Update @Delete
 */
public interface UserDao {

	/**
	 * 查询所有用户
	 * @return
	 */
	@Select("select * from user")
	List<User> findAll();
}
