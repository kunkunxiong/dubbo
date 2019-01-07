package com.dubbo.service;

import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;

public interface UserService {

    /**
     * 分页的形式查询user表的数据
     * @param page
     */
    Page<UserPojo> query(Page<UserPojo> page);

    /**
     * 添加用户信息
     * @param user
     */
    void add(User user);

    /**
     * 修改用户信息
     * @param user
     */
    void update(User user);

    /**
     * 删除用户信息
     */
    void delete(Integer id);

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    User getById(Integer id);
}
