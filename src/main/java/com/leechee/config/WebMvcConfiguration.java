package com.leechee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.leechee.interceptor.JwtTokenInterceptor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport{
    
    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 注册自定义拦截器
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("注册自定义拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/douyin/**")
                .excludePathPatterns("/douyin/feed", "/douyin/user/register", "/douyin/user/login");
    }
}
