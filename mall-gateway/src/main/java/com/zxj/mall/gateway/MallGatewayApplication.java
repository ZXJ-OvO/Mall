package com.zxj.mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1、开启服务注册发现
 *  (配置nacos的注册中心地址)
 * 2、编写网关配置文件
 */
// 排除数据源的相关配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class MallGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallGatewayApplication.class, args);
	}

}
