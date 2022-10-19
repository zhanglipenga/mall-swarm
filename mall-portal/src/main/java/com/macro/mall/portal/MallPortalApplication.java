package com.macro.mall.portal;

import com.macro.mall.portal.config.LoadBalancerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.macro.mall")
@LoadBalancerClient(name = "mall-portal", configuration = LoadBalancerConfiguration.class)
public class MallPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPortalApplication.class, args);
    }

}
