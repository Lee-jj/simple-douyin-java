package com.leechee.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leechee.properties.QiniuOssProperties;
import com.leechee.utils.QiniuOssUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OssConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public QiniuOssUtil qiniuOssUtil (QiniuOssProperties qiniuOssProperties) {
        log.info("创建七牛云文件上传工具类对象:{}", qiniuOssProperties);
        return new QiniuOssUtil(qiniuOssProperties.getAccessKey(), 
                qiniuOssProperties.getSecretKey(), 
                qiniuOssProperties.getBucketName(),
                qiniuOssProperties.getDomain());
    }
}
