package com.leechee.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "douyin.qiniuoss")
@Data
public class QiniuOssProperties {
    
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String domain;
}
