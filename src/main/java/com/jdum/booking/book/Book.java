package com.jdum.booking.book;

import com.jdum.booking.book.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.jdum.booking"})
@EnableDiscoveryClient
@EnableFeignClients
@Import(AppConfig.class)
public class Book {

    public static void main(String[] args) {
        SpringApplication.run(Book.class, args);
    }
}
