package com.wq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/***
 * Druid数据源的配置类 相当于以前的.xml配置文件
 */
@Configuration
public class DruidConfig {
    //3.并注入到容器中
    @Bean
    // 2.绑定配置文件里的设置spring: datasource:
    @ConfigurationProperties(prefix = "spring.datasource")
    // 1.返回一个Druid数据源对象DruidDataSource对象
    public DataSource getDruidDataSource(){
        return new DruidDataSource();
    }

    /***
     * SpringBoot内置了Servlet容器 所以没有web.xml配置文件
     * 替代方法就是把Servlet封装成bean然后注册进去就可以了
     * ServletRegistrationBean xxxxServlet()
     * @return
     */
    // 4.实现后台监控的功能(web.xml)====注册一个ServletBean 把Druid一些注册bean放入
    // Druid提供的后台监控系统(相当于web.xml)
    // 4.3.最后一定要@Bean注入容器才能生效
    @Bean
    public ServletRegistrationBean statViewServlet(){
        // 1.配置进入后台的Servlet路由url映射 浏览器地址栏输入即可进入(要加上配置的项目虚拟路径)
        ServletRegistrationBean<StatViewServlet> bean =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 2.后台登录需要的账号密码 还可以设置一些url映射
        HashMap<String, String> initParams = new HashMap<>(); // 存储初始化参数的map
        // 设置参数 增加配置 //Druid后台key参数名都是固定的
        // 2.1 管理员用户名
        initParams.put("loginUsername","admin");
        // 2.2 登录密码
        initParams.put("loginPassword","123456");
        // 2.3 允许访问的列表 ""代表谁都可以访问 initParams.put("allow",ip);
        initParams.put("allow","localhost");
        // 2.4 进制访问列表 initParams.put(任意key,ip);
        bean.setInitParameters(initParams); //设置初始化参数
        return bean;
    }
    // filter过滤器配置方式同上 (类似在web.xml文件中)
    // 最后一定要注入容器才能生效
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        // 设置过滤器 这是一个阿里巴巴的过滤器
        bean.setFilter(new WebStatFilter());
        // 设置它可以过滤哪些东西 点进去看 需要什么传什么
        Map<String, String> initParameters = new HashMap<>();
        // 不过滤所有*.js *.css的请求 直接放行
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }

}
