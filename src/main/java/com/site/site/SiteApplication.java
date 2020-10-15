package com.site.site;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.site.site.dao")
public class SiteApplication {

    public static void main(String[] args) {
        int low = 3;
        int height = 8;
        int mid = (low + height) /2;
        System.out.print(mid);
        System.out.print(000000000000);
        SpringApplication.run(SiteApplication.class, args);
    }

}
