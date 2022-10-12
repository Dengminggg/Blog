package com.dm.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.dm.blog.mapper")
@EnableSwagger2
public class AdminBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBlogApplication.class, args);
    }
}
