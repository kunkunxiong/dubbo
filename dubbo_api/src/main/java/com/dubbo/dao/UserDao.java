package com.dubbo.dao;

import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;

import java.util.List;

public interface UserDao{

    /**
     * ��ѯ������
     * @param page
     * @return Integer
     */
    Integer getCount(Page<UserPojo> page);//@Paramָ�����Ǳ���

    /**
     * ��ҳ����ʽ��ѯuser�������
     * @return List<User>
     */
    List<UserPojo> query(Page<UserPojo> page);

    /**
     * ����û���Ϣ
     * @param user
     */
    void add(User user);

    /**
     * �޸��û���Ϣ
     * @param user
     */
    void update(User user);

    /**
     * ɾ���û���Ϣ
     * @param id
     */
    void delete(Integer id);

    /**
     * ����id��ѯ�û�����
     * @param id
     * @return User
     */
    User getById(Integer id);
}
