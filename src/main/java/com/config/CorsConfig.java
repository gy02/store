package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {//Cross-Origin Resource Sharing (CORS)
    @Bean
    public CorsFilter corsFilter(){
        //添加CORS配置信息
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //允许的域，这里不要写*，否则cookie就无法使用了
        corsConfiguration.addAllowedOrigin("http://localhost:8888");//这里填写请求的前端服务器
        //允许的请求方式
        corsConfiguration.addAllowedMethod("*");
        //是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
        //允许的头信息
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}
