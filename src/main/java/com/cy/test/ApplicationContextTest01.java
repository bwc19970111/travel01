package com.cy.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextTest01 {

    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("application.properties");


        System.out.println(context.getDisplayName());
    }

}
