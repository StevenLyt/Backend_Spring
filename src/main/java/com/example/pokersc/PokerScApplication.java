package com.example.pokersc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"com.example"})
@ComponentScan(basePackages = {"com.example.pokersc","com.example.pokersc.controller","com.example.pokersc.entity","com.example.pokersc.repository"})
@EntityScan
public class PokerScApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokerScApplication.class, args);
    }

}
