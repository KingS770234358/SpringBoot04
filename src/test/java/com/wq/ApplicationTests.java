package com.wq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootTest
class ApplicationTests {

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        // 1.查看下默认的数据源 class com.zaxxer.hikari.HikariDataSource
        System.out.println("数据源名称:"+dataSource.getClass());
        // 2.获得连接
        Connection connection = dataSource.getConnection();
        System.out.println("数据库连接:"+connection);
        // 3.使用jdbcTemplate进行操作
        connection.close();
    }

}
