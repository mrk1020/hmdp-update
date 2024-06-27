package com.mrk.hmdp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mrk
 * @create 2024-06-27-15:27
 */
@SpringBootApplication
@MapperScan("com.mrk.hmdp.mapper")
public class HmdpApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmdpApplication.class, args);
    }
}
