package com.zxj.mall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement    // 开启使用
@MapperScan("com.zxj.mall.product.dao")
public class MyBatisConfig {

    // 引入分页插件 common中引入的mybatis plus版本是3.2，在3.4之后分页插件有所变化
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
