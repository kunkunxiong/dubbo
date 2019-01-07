package com.dubbo.dao;

import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;

import java.util.List;

public interface UserDao{

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    Integer getCount(Page<UserPojo> page);//@Param指定的是别名

    /**
     * 分页的形式查询user表的数据
     * @return List<User>
     */
    List<UserPojo> query(Page<UserPojo> page);

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
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    User getById(Integer id);
}
