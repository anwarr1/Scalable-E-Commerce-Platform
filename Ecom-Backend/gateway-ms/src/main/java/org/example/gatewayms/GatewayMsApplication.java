package org.example.gatewayms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMsApplication.class, args);
    }

}
