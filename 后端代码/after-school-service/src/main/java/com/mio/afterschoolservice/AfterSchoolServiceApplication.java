package com.mio.afterschoolservice;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mio.afterschoolservice.mapper")
public class AfterSchoolServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfterSchoolServiceApplication.class, args);
    }

}
