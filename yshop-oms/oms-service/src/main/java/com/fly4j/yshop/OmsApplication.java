package com.fly4j.yshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-04-13 14:26
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.fly4j.yshop.oms.feign")
@MapperScan("com.fly4j.yshop.oms.mapper")
public class OmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class, args);
    }
}
