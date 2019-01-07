package com.dubbo.service;

import com.dubbo.dao.UserDao;
import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 分页的形式查询user表的数据
     * @param page
     */
    public Page<UserPojo> query(Page<UserPojo> page){
        page.setTotalCount(userDao.getCount(page));//查询总条数加入page中
        List<UserPojo> list = userDao.query(page);//分页查询的数据
        page.setDataList(list);
        return page;
    }

    /**
     * 添加用户信息
     * @param user
     */
    public void add(User user){
        userDao.add(user);
    }

    /**
     * 修改用户信息
     * @param user
     */
    public void update(User user){
        userDao.update(user);
    }

    /**
     * 删除用户信息
     * @param id
     */
    public void delete(Integer id){
        userDao.delete(id);
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    public User getById(Integer id){
        return userDao.getById(id);
    }
}
