package com.thoughtworks.devcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class CodeCheckApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CodeCheckApplication.class).web(true).run(args);
    }

}
