package com.wq.controller;

import com.wq.mapper.UserMapper;
import com.wq.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/***
 * 用于测试Mybatis的控制器
 */
@Controller
public class MybatisController {
    // 注入Spring容器中的User类接口(底层调用对应的.xml文件里的Sql语句)
    @Autowired
    private UserMapper userMapper;

    // 让结果不走视图解析器 直接返回看到结果
    @ResponseBody
    @RequestMapping("/getAllUser")
    public User getAllUser(){
        List<User> userList = userMapper.getAllUser();
        // 控制台查看结果
        for(User u:userList){
            System.out.println(u);
        }
        return userList.get(0);
    }

    @ResponseBody
    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") int userId){
        User user = userMapper.getUserById(userId);
        return user;
    }

    @ResponseBody
    @RequestMapping("/addMybaUser")
    public int addMybaUser(){
        int addRes = userMapper.addUser(new User(665,"哈？","zheshimima "));
        return addRes;
    }

    @ResponseBody
    @RequestMapping("/deleteMybaUser/{id}")
    public int deleteMybaUser(@PathVariable("id") int userId){
        int deleteRes = userMapper.deleteUser(userId);
        return deleteRes;
    }

    @ResponseBody
    @RequestMapping("/updateMybaUser")
    public int updateMybaUser(){
        int deleteRes = userMapper.updateUser(new User(665,"呵呵呵","zhebushimima"));
        return deleteRes;
    }
}
