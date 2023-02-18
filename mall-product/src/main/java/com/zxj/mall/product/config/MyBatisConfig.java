package com.zxj.mall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
    @Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，
    这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
 */

/*
    @EnableTransactionManagement是Spring事务的总开关，开启事务支持后，在访问数据库的Service方法上添加注解@Transactional
 */
/**
 * 配置Mybatis-Plus的相关插件：分页插件（基于Mybatis-Plus V3.2）
 */
@Configuration
@EnableTransactionManagement    // 开启使用事务的总开关
@MapperScan("com.zxj.mall.product.dao")
public class MyBatisConfig {

    // 引入分页插件 common模块中引入的mybatis plus版本是3.2，在3.4之后分页插件有所变化
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最后页的操作，true调回到首页，false继续请求，默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认500条，-1不限制
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }

}
