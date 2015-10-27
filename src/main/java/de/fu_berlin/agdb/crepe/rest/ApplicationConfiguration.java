package de.fu_berlin.agdb.crepe.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.GeometryFactory;
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

    private Configuration configuration;
    private Logger logger;

    @Bean
    public Configuration configuration() {
        if (configuration == null) {
            configuration = new Configuration();
            configuration.loadAndCreate();
        }

        return configuration;
    }

    @Bean
    public Logger logger() {
        if (logger == null) {
            logger = LogManager.getLogger("RestEndpoint");
        }

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

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

    @Bean
    public String ionicPushPrivateApiKey() {
        return "<insert your ionic.io private API key here!>";
    }
}
