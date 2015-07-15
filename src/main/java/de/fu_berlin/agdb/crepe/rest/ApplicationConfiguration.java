package de.fu_berlin.agdb.crepe.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.core.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Spring configuration for the REST server.
 *
 * @author Simon Kalt
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"de.fu_berlin.agdb.crepe.*", "de.fu_berlin.agdb.crepe.rest.*"})
public class ApplicationConfiguration {

    private static final Configuration configuration = new Configuration();
    private static final Logger logger = LogManager.getLogger("RestEndpoint");

    static {
        configuration.loadAndCreate();
    }

    @Bean
    public Configuration configuration() {
        return configuration;
    }

    @Bean
    public Logger logger() {
        return logger;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("de.fu_berlin.agdb.crepe.data");
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return new Jackson2ObjectMapperBuilder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return jacksonBuilder().build();
    }
}
