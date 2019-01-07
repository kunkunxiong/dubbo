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
     * ��ҳ����ʽ��ѯuser�������
     * @param page
     */
    public Page<UserPojo> query(Page<UserPojo> page){
        page.setTotalCount(userDao.getCount(page));//��ѯ����������page��
        List<UserPojo> list = userDao.query(page);//��ҳ��ѯ������
        page.setDataList(list);
        return page;
    }

    /**
     * ����û���Ϣ
     * @param user
     */
    public void add(User user){
        userDao.add(user);
    }

    /**
     * �޸��û���Ϣ
     * @param user
     */
    public void update(User user){
        userDao.update(user);
    }

    /**
     * ɾ���û���Ϣ
     * @param id
     */
    public void delete(Integer id){
        userDao.delete(id);
    }

    /**
     * ����id��ѯ�û�����
     * @param id
     * @return User
     */
    public User getById(Integer id){
        return userDao.getById(id);
    }
}
