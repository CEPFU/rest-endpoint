package de.fu_berlin.agdb.crepe.rest;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.Profile;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.crepe.core.Configuration;
import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;
import de.fu_berlin.agdb.crepe.json.serialize.AttributeDeserializer;
import de.fu_berlin.agdb.crepe.json.serialize.EqualDeserializer;
import de.fu_berlin.agdb.crepe.json.serialize.ProfileDeserializer;
import de.fu_berlin.agdb.crepe.outputadapters.JSONOutputAdapter;
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
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        //region Register custom deserializers
        builder.deserializerByType(Profile.class, new ProfileDeserializer());
        builder.deserializerByType(Equal.class, new EqualDeserializer());
        builder.deserializerByType(Attribute.class, new AttributeDeserializer());
        //endregion

        builder.modulesToInstall(MyModule.class);

        return builder;
    }


    @Bean
    public Module defaultModule() {
        return new MyModule();
    }

    public static class MyModule extends SimpleModule {
        public MyModule() {
            super("MyModule", new Version(1, 0, 0, null, null, null));
        }

        @Override
        public void setupModule(SetupContext context) {
            super.setupModule(context);
            context.registerSubtypes(Event.class);
            context.registerSubtypes(Equal.class);
            context.registerSubtypes(Attribute.class);
            context.setMixInAnnotations(Operator.class, JSONOutputAdapter.TypeInfoMixIn.class);
            context.setMixInAnnotations(IEvent.class, JSONOutputAdapter.TypeInfoMixIn.class);
            context.setMixInAnnotations(IAttribute.class, JSONOutputAdapter.TypeInfoMixIn.class);
        }
    }
}
