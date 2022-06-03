package com.hodor.dota2partner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Dota2PartnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dota2PartnerApplication.class, args);
    }

}
