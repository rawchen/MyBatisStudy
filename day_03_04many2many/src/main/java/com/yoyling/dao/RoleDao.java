package com.yoyling.dao;

import com.yoyling.domain.Role;

import java.util.List;

public interface RoleDao {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

}
