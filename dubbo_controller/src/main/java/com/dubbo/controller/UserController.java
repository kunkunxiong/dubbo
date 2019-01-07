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
 * @describe:  正规的controller是通过向dubbo注册来实现的，而这里的controller和web相当于1个项目，只是互相引用
 * @auther: xiongdingkun
 * @date: 2018/12/20 16:59
 **/
@Controller
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserService userService;

    /**
     * 如果检测到form表单提交带有id,直接将值存入request中
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
     * 分页的形式查询user表的数据，这里记录一个问题，如果参数对象有值为null,会出现异常，会导致整个请求对象为null
     * @return List<User>
     */
    @RequestMapping(value="/users")
    @ResponseBody
    public Page<UserPojo> query(UserPojo userPojo, Page<UserPojo> page){
        if(userPojo.getUser().getBirthday() == null){//这里避免了date值等于null
            java.util.Date utilDate = new java.util.Date();
            userPojo.getUser().setBirthday(new Date(utilDate.getTime()));
        }
        page.setT(userPojo);
        page = userService.query(page);
        return page;
    }

    /**
     * 跳转添加页面
     * @return String
     */
    @RequestMapping(value="/user")
    public String jumpAdd(Map<String, Object> map){
        map.put("user", new User());//传入一个空的user对象
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "男");
        map1.put("2", "女");
        map1.put("3", "保密");
        map.put("sexs", map1);
        return "userAU";
    }

    /**
     * 保存添加
     * @return String
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public String saveAdd(User user){
        userService.add(user);
        return "redirect:/user";
    }

    /**
     * 跳转修改
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map, UserPojo userPojo, Page<UserPojo> page){
        page.setT(userPojo);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "男");
        map1.put("2", "女");
        map1.put("3", "保密");
        map.put("sexs", map1);
        map.put("page", page);
        map.put("user", userService.getById(id));//根据id获取对象放入request中
        return "userAU";
    }

    /**
     * 保存修改
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
     * 删除用户
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}