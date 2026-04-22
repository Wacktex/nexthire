package com.nexthire.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nexthire")
public class NexthireApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexthireApplication.class, args);
    }
}
