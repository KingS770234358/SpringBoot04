package com.wq.mapper;

import com.wq.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mybatis的mapper类
 * */
// 这个注解表示它是一个Mybatis的mapper类
@Mapper
@Repository
public interface UserMapper {

    // 静态变量 不可改动 (java基础)
    public static final int age = 18;

    //查询所有用户
    List<User> getAllUser();
    // 查询一个用户
    User getUserById(@Param("userId") int id);
    // 增加用户
    int addUser(User user);
    // 删除用户
    int deleteUser(@Param("userId") int id);
    // 修改用户
    int updateUser(User user);
}
