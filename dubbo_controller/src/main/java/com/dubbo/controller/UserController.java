package com.dubbo.controller;

import com.dubbo.entity.Page;
import com.dubbo.entity.User;
import com.dubbo.entity.UserPojo;
import com.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @describe:  �����controller��ͨ����dubboע����ʵ�ֵģ��������controller��web�൱��1����Ŀ��ֻ�ǻ�������
 * @auther: xiongdingkun
 * @date: 2018/12/20 16:59
 **/
@Controller
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserService userService;

    /**
     * �����⵽form���ύ����id,ֱ�ӽ�ֵ����request��
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getAccount(@RequestParam(value="id",required=false) Integer id,Map<String, Object> map){
        if(id != null && id != 0){
            User user = userService.getById(id);
            map.put("user", user);
        }
    }

    /**
     * ��ҳ����ʽ��ѯuser������ݣ������¼һ�����⣬�������������ֵΪnull,������쳣���ᵼ�������������Ϊnull
     * @return List<User>
     */
    @RequestMapping(value="/users")
    @ResponseBody
    public Page<UserPojo> query(UserPojo userPojo, Page<UserPojo> page){
        if(userPojo.getUser().getBirthday() == null){//���������dateֵ����null
            java.util.Date utilDate = new java.util.Date();
            userPojo.getUser().setBirthday(new Date(utilDate.getTime()));
        }
        page.setT(userPojo);
        page = userService.query(page);
        return page;
    }

    /**
     * ��ת���ҳ��
     * @return String
     */
    @RequestMapping(value="/user")
    public String jumpAdd(Map<String, Object> map){
        map.put("user", new User());//����һ���յ�user����
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "��");
        map1.put("2", "Ů");
        map1.put("3", "����");
        map.put("sexs", map1);
        return "userAU";
    }

    /**
     * �������
     * @return String
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public String saveAdd(User user){
        userService.add(user);
        return "redirect:/user";
    }

    /**
     * ��ת�޸�
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map, UserPojo userPojo, Page<UserPojo> page){
        page.setT(userPojo);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "��");
        map1.put("2", "Ů");
        map1.put("3", "����");
        map.put("sexs", map1);
        map.put("page", page);
        map.put("user", userService.getById(id));//����id��ȡ�������request��
        return "userAU";
    }

    /**
     * �����޸�
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.PUT)
    public String saveUpdate(User user, UserPojo userPojo, Page<UserPojo> page, Map<String, Object> map){
        page.setT(userPojo);
        map.put("page", page);
        userService.update(user);
        return "userList";
    }

    /**
     * ɾ���û�
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}