package com.dm.blog;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com/dm/blog/mapper")
@EnableScheduling
@EnableSwagger2
public class FrontBlogApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FrontBlogApplication.class, args);
    }

}
