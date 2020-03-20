package com.tabuyos.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Tabuyos
 * @Time 3/16/20 1:34 AM
 * @Site www.tabuyos.com
 * @Email tabuyos@outlook.com
 * @Description
 */
@SpringBootApplication(scanBasePackages = {"com.tabuyos.microservice"})
public class Power1Application {
    public static void main(String[] args) {
        SpringApplication.run(Power1Application.class);
    }
}
