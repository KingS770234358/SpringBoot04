Spring Data
使用Spring的思想去做,就是把它以bean的方式注入使用
不论是SQL(关系型数据库)还是NoSQL(菲关系型数据库),SpringBoot底层都是采用SpringData的方式统一处理
Spring Data也是Spring中和SpringBoot SpringCloud齐名的项目
官网:https://spring.io/projects/spring-data
· SpringData JDBC
· SpringData JPA
· SpringData MangoDB
· SpringData Redis
· SpringData Solr
· SpringData ElasticSearch
· SpringData Apache Hadoop
一些可能用到的启动器(thymeleaf mail quartz validate)

1.引入对应的启动器依赖starter
jdbc启动器
dependency
    groupId org.springframework.boot  groupId 
    artifactId spring-boot-starter-jdbc  artifactId 
dependency
数据库连接驱动
dependency
     groupId mysql  groupId 
     artifactId mysql-connector-java  artifactId 
     scope runtime  scope
dependency
[在创建项目 引入web的时候 就可以在左侧的SQL里选择JDBC API和MySQL Driver]

2.在全局配置文件application.yaml中配置数据库
· 配置
spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
    //com.mysql.jdbc.Driver 已经废弃 
    [设置url的时候要设置时区serverTimezone=UTC,SpringBoot默认使用的jar包是8版本的,否则使用的时候报错]
3.配置好数据库之后,在Spring容器中就有DataSource的Bean了,可以直接@Autowired使用(test目录下的ApplicationTests.java)
  可以直接注入使用 可以查看SpringBoot默认数据源的名称(class com.zaxxer.hikari.HikariDataSource比dbcp快)
  -- 获得连接getConnection
  -- 准备语句prepareStatement
  -- 提交事务Commit
  -- 回滚 rollback
  [测试的时候需要抛出Sql异常 throws SQLException]
· 查看源码,在全局配置文件中,ctrl点击username等属性
  就可以跳转至[DataSourceProperties类 prefix = "spring.datasource"]下载源码查看
  有XxxxProperties类就必定有[与之对应的XxxxAutoConfiguration类],
  上面使用@EnableConfigurationProperties(DataSourceProperties.class)标注,因此可以绑定全局配置的设置
· 使用原生的jdbc操作(XxxxTemplate SpringBoot配置好的模板bean 拿来即用 比如 jdbcTemplate RedisTemplate)
  ··左侧找到autoconfigure找到包下的jdbc里面有JdbcTemplateAutoConfiguration和JdbcTemplateConfiguration
  里面就向Spring容器中注入了jdbcTemplate对象
  ··写一个Controller进行测试(详见JDBCController.java)
· 查看DataSourceAutoConfiguration源码
  有静态类PooledDataSourceConfiguration上@Import导入了几个数据源
  下面PooledDataSourceCondition类里可以通过在[全局配置文件中spring.datasource.type来指定数据源]
  Import里面有个DataSourceConfiguration.Hikari类,里面可以查看
4.使用Druid数据源
· 常见的数据源: jdbc c3p0 dbcp druid(天然继承日志监控) hikari(最快) tomcat
· 是阿里巴巴开源平台上一个数据库连接池实现,结合了C3P0 DBCP PROXOOL等DB池的优点,同时加入了日志监控
· 一些基本配置参数 name url username password driverClassName initialSize maxActive maxWait
· 在pom.xml中导入依赖 点进包里看源码结构 pool/DruidDataSource.class里面有所有可以配置的参数
 druid数据源依赖 
 dependency 
     groupId com.alibaba  groupId 
     artifactId druid  artifactId 
     version 1.1.21  version 
  dependency 
· 在全局配置文件中指定数据源的type 在项目的测试模块进行查看
    # 配置指定的数据源 不设置的话默认是Hikari [配置的时候ctrl点击属性名可以进入源码查看]
    
· 配置Druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    #Spring Boot 默认是不注入这些属性值的，需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
    #则导入 log4j 依赖即可，Maven 地址： https://mvnrepository.com/artifact/log4j/log4j
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500

  ====>数据源名称:class com.alibaba.druid.pool.DruidDataSource
  · 上面配置日志使用了log4j 因此要在pom.xml中导入log4j的包 [还是使用1.2.17的]
· 创建Config文件夹 编写Druid的配置类 ==== [@Configuration的配置类相当于以前的.xml配置文件]
  详见config/DruidConfig
  -- 可以正常进入后台系统,但是控制台报错 不合法的Invalid IP Address [localhost]
  -- 进入系统后 SQL监控里 可以看到执行的所有Sql语句
#############################################################################
# 这个DruidConfig 
# 1.配置了Druid后台监控
# 2.配置了Servlet 和 Filter 相当于以前的web.xml文件!!!!
############################################################################# 

