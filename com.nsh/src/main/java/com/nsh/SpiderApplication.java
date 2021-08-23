package com.nsh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
@EnableScheduling
@MapperScan("com.nsh.dao")

public class SpiderApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpiderApplication.class,args);

    }
}
