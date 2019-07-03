package me.work.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;


@EnableConfigurationProperties
@SpringBootApplication
@EnableRetry
public class MainApplication {
    public static final String API_ROOT = "/api";
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
