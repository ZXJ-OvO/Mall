package com.zxj.mall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class MallCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 1. 配置跨域
        corsConfiguration.addAllowedHeader("*");    // 允许任意头跨域
        corsConfiguration.addAllowedMethod("*");    // 允许任意请求方式跨域
        corsConfiguration.addAllowedOrigin("*");    // 允许任意请求来源跨域
        corsConfiguration.setAllowCredentials(true);    // 允许携带cookies跨域


        source.registerCorsConfiguration("/**", corsConfiguration);    // 任意路径都要进行跨域配置
        return new CorsWebFilter(source);
    }
}
