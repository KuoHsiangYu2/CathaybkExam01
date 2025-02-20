package com.hsiangyu.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CathaybkExam01Application {

    private static final Logger logger = LoggerFactory.getLogger(CathaybkExam01Application.class);

    public static void main(String[] args) {
        logger.info("SpringApplication start");
        SpringApplication.run(CathaybkExam01Application.class, args);

        logger.info("SpringApplication end");
    }

}
