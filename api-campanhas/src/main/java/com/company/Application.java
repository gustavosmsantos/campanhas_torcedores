package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.company")
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }

}
