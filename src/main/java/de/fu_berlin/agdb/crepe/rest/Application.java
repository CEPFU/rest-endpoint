package de.fu_berlin.agdb.crepe.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"de.fu_berlin.agdb.crepe.*"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
