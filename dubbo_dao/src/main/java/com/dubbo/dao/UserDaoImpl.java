package com.dubbo.dao;

import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;
import com.dubbo.util.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    //如何获取到和当前事务关联的 EntityManager 对象呢 ?通过 @PersistenceContext 注解来标记成员变量!
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询总条数
     * @param page
     * @return Integer
     */
    public Integer getCount(Page<UserPojo> page){//@Param指定的是别名
        String jpql = "select count(*) from User u where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){//判断是否为null和空
            jpql += "and u.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            jpql += "and u.birthday >= :startBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            jpql += "and u.birthday <= :endBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            jpql += "and u.addr like :addr ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){
            query.setParameter("name", "%"+page.getT().getUser().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            query.setParameter("startBirthday", DateUtils.toDate(page.getT().getStartBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            query.setParameter("endBirthday", DateUtils.toDate(page.getT().getEndBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            query.setParameter("addr", "%"+page.getT().getUser().getAddr()+"%");
        }
        Long count = (Long)query.getSingleResult();
        return count.intValue();//将long转int
    }

    /**
     * 分页的形式查询user表的数据
     * @return List<User>
     */
    public List<UserPojo> query(Page<UserPojo> page){
        String jpql = "select u from User u where 1 = 1 ";
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){//判断是否为null和空
            jpql += "and u.name like :name ";
        }
        if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            jpql += "and u.birthday >= :startBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            jpql += "and u.birthday <= :endBirthday ";
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            jpql += "and u.addr like :addr ";
        }
        if(page.getT().getOrder() == 0){
            jpql += "order by u.id asc ";
        }else if(page.getT().getOrder() == 1){
            jpql += "order by u.id desc ";
        }
        Query query = entityManager.createQuery(jpql);
        if(!StringUtils.isEmpty(page.getT().getUser().getName())){
            query.setParameter("name", "%"+page.getT().getUser().getName()+"%");
        }
        if(!StringUtils.isEmpty(page.getT().getStartBirthday())){
            query.setParameter("startBirthday", DateUtils.toDate(page.getT().getStartBirthday()));
        }
        if(!StringUtils.isEmpty(page.getT().getEndBirthday())){
            query.setParameter("endBirthday", DateUtils.toDate(page.getT().getEndBirthday()));//转sql date
        }
        if(!StringUtils.isEmpty(page.getT().getUser().getAddr())){
            query.setParameter("addr", "%"+page.getT().getUser().getAddr()+"%");
        }
        query.setFirstResult(page.getLimitNum()).setMaxResults(page.getPageSize());//查询分页
        List<UserPojo> list = query.getResultList();
        return list;
    }

    /**
     * 添加用户信息
     * @param user
     */
    @Transactional
    public void add(User user){
        String sql = "insert into user (name,sex,addr,birthday,email,state,password)values(?1,?2,?3,?4,?5,?6,?7)";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, user.getName()).setParameter(2, user.getSex()).setParameter(3, user.getAddr())
                .setParameter(4, user.getBirthday()).setParameter(5, user.getEmail()).setParameter(6, user.getState()).setParameter(7, user.getPassword());
        query.executeUpdate();
    }

    /**
     * 修改用户信息
     * @param user
     */
    @Transactional
    public void update(User user){
        String jpql = "update User u set u.name=?1,u.sex=?2,u.addr=?3,u.birthday=?4,u.email=?5,u.state=?6,u.password=?7 where u.id=?8";
        Query query = entityManager.createQuery(jpql).setParameter(1, user.getName()).setParameter(2, user.getSex()).setParameter(3, user.getAddr())
                .setParameter(4, user.getBirthday()).setParameter(5, user.getEmail()).setParameter(6, user.getState()).setParameter(7, user.getPassword()).setParameter(8, user.getId());
        query.executeUpdate();
    }

    /**
     * 删除用户信息
     * @param id
     */
    @Transactional
    public void delete(Integer id){
        String jpql = "delete from User u where u.id = ?1";
        Query query = entityManager.createQuery(jpql).setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * 根据id查询用户数据
     * @param id
     * @return User
     */
    public User getById(Integer id){
        String jpql = "select u from User u where u.id = ?1";
        User user = (User)entityManager.createQuery(jpql).setParameter(1, id).getSingleResult();
        return user;
    }
}
