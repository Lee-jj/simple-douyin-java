package com.leechee.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "douyin.jwt")
public class JwtProperties {
    
    private String secretKey;   // 密钥名称
    private long ttl;           // 过期时间
    private String tokenName;   // token名称
}
