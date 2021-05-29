package com.wq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/***
 * 测试JDBC的Controller
 * @RestController结果都不会走视图解析器 直接返回字符串
 * 看上面导入的包 @Controller是Spring的
 * @RestController是SpringMVC的
 */
@RestController
public class JDBCController {
    // 1.从Spring容器中自动注入获取JDBCTemplate
    // ctrl点击JdbcTemplate就可以查看它有哪些方法了
    // [execute(Connnection) Query-很多重载 update-还有batchUpdate批处理 call]
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 2.查询数据库的所有信息
    // 2.1 在没有实体类的情况下 数据库中的数据怎么获取? 使用Map存储实体类(对象) Map可以存储对象
    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        // 原生jdbc sql语句
        String sql = "select * from user";
        List<Map<String,Object>>  map_list = jdbcTemplate.queryForList(sql);
        return map_list;
    }

    //3.增加用户
    @GetMapping("/addUser")
    public String addUser(){
        String sql = "insert into user(id, name, pwd) values (13, 'SpringBootJDBC','xxxx')";
        // 增加使用update方法 ===== 自动提交事务
        return String.valueOf(jdbcTemplate.update(sql));
    }

    //4.删除用户 //RestFul风格传参
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        // 可以使用拼接的方式
        // String sql = "delete from user where id="+id;
        // 也可以使用传参的方式
        String sql = "delete from user where id=?";
        // 增加使用update方法 ===== 自动提交事务
        return String.valueOf(jdbcTemplate.update(sql,id));
    }

    //5.修改用户 //RestFul风格传参
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id){
        String sql = "update user set name=?,pwd=? where id="+id;
        // 封装参数
        Object[] param = new Object[2];
        param[0] = "小明";
        param[1] = "xmxixmxisasdsaidhisa";
        // update可以传入两个参数 第一个参数是sql语句 第二个参数就是参数列表
        return String.valueOf(jdbcTemplate.update(sql, param));
    }
}
