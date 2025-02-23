package com.hsiangyu.demo;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hsiangyu.demo.service.ICathaybkService;

/* Unit Test 程式碼 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CathaybkExam01ApplicationTests {

    @Autowired
    private ICathaybkService cathaybkServiceImpl;

    @Test
    @Order(0)
    public void contextLoads() {
        System.out.println("#### SpringBootTest-00 ####");
        java.util.Date now = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String outputDate = dateFormat.format(now);
        System.out.printf("$$ SpringBootTest %s%n", outputDate);
        System.out.println("$$$$ SpringBootTest-00 end $$$$");
    }

    @Test
    @Order(1)
    public void secondContext() {
        System.out.println("#### SpringBootTest-01 ####");

        System.out.println("Unit Test calculateNumberSum01");
        int sum1 = cathaybkServiceImpl.calculateNumberSum01(1, 10);
        System.out.printf("service answer: 1 + 2 + 3 + ... + 10 = %d%n", sum1);
        System.out.printf("expected answer: 1 + 2 + 3 + ... + 10 = %d%n", 55);
        Assert.assertEquals(55, sum1);
        System.out.println();

        System.out.println("Unit Test calculateNumberSum02");
        int sum2 = cathaybkServiceImpl.calculateNumberSum02(1, 10);
        System.out.printf("service answer: 1 + 2 + 3 + ... + 10 = %d%n", sum2);
        System.out.printf("expected answer: 1 + 2 + 3 + ... + 10 = %d%n", 55);
        Assert.assertEquals(55, sum2);
        System.out.println();

        System.out.println("$$$$ SpringBootTest-01 end $$$$");
    }

}
