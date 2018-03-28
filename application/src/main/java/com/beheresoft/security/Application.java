package com.beheresoft.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aladi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.beheresoft.security.repository", "com.beheresoft.security.service",
        "com.beheresoft.security.realm", "com.beheresoft.security.controller"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
