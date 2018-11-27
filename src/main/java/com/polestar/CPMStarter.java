package com.polestar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class CPMStarter {

    public static void main(String[] args) {
        log.info("Service is  Starting !!!");
        SpringApplication.run(CPMStarter.class, args);
        log.info("Service Started !!!");
    }

    @PreDestroy
    private void stop(){
        log.info("Service stopped !");
    }
}
