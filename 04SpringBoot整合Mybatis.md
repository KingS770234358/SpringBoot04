04SpringBoot整合Mybatis
引言
当时Spring整合mybatis的时候,引入mybatis-spring包,springboot和Mybatis的[整合]也需要类似的[整合包]
启动器[mybatis-spring-boot-starter]====github管网
1.在pom.xml文件中引入整合依赖启动器mybatis-spring-boot-starter
dependency
    groupId org.mybatis.spring.boot  groupId 
    artifactId mybatis-spring-boot-starter  artifactId 
    version 2.1.1  version 
dependency 
SpringBoot官方的启动器spring-boot-starter-xxx
非官方的启动器mybatis-spring-boot-starter
## 1插入知识点: 项目管理平台-maven gradle docker
2.全局配置文件application.xml中配置数据库信息
3.pojo mapper等文件夹 定义接口 xxxMapper
· 在对应的xxxMapper[接口]上要使用[①@Mapper注解]来表示它是一个Mybatis的Mapper类
  方式一:// 这个注解表示它是一个Mybatis的mapper类
  @Mapper
  方式二:在主启动类的上方使用@MapperScan("com.wq.mapper")直接以包为单位扫描mapper接口
· 接口上还要使用[②@Repository]Dao层的@Component 把接口注入到Spring容器中
## 2插入知识点: @Component全局组件 @Controller 控制器组件 @Service 服务层组件 @Repository 持久层组件
4.现在统一把接口的实现xxMapper.xml放到resources目录下,这样[无法Mapper.xml和注解同时使用]
  全部用配置文件xxxMapper.xml实现
· resource下建立mybatis文件夹 下面建立mapper文件夹 创建XxxxMapper.xml实现类
· 在[③全局配置文件中整合mybatis],让XxxxMapper.xml可以扫描到resultType的pojo类
  想知道有哪些东西可以配置,直接在全局配置文件中ctrl点击属性跳转至[MybatisProperties.java]中
## 3回顾mybatis XxxMapper.xml中的一些配置 -cache 开启缓存 -在sql语句里使用useCache属性使用特定缓存
5.编写Controller进行测试

总结
MVC框架
M: 数据及业务
C: 交接
V: HTML
[整合Mybatis的步骤]
1.pom.xml中导入依赖包(启动器)
2.全局配置文件application.yaml中配置jdbc数据库(源)
3.全局配置文件application.yaml中配置整合Mybatis
4.编写XxxxMapper.xml(统一写在resources文件夹下)
5.Service层调用Dao层
6.Controller层调用Service层

