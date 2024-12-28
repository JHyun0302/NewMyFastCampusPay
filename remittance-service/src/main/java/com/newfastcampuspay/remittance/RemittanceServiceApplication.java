package com.newfastcampuspay.remittance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.newfastcampuspay.remittance", "com.newfastcampuspay.common"})
public class RemittanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemittanceServiceApplication.class, args);
    }

}
