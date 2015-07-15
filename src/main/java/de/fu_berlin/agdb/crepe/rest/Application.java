package de.fu_berlin.agdb.crepe.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main entry point for the REST server.
 * @author Simon Kalt
 */
@EnableAutoConfiguration(exclude = { JpaBaseConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
