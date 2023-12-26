package com.leechee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@EnableCaching
public class SimpleDouyinJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDouyinJavaApplication.class, args);
		log.info("server started...");
	}

}
