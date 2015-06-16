package de.fu_berlin.agdb.crepe.rest;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

//        //region Register custom deserializers
//        builder.deserializerByType(Profile.class, new ProfileDeserializer());
//        builder.deserializerByType(Equal.class, new EqualDeserializer());
//        builder.deserializerByType(Attribute.class, new AttributeDeserializer());
//        //endregion

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
//            context.registerSubtypes(Event.class);
//            context.registerSubtypes(Equal.class);
//            context.registerSubtypes(Attribute.class);
//            context.setMixInAnnotations(Operator.class, JSONOutputAdapter.TypeInfoMixIn.class);
//            context.setMixInAnnotations(IEvent.class, JSONOutputAdapter.TypeInfoMixIn.class);
//            context.setMixInAnnotations(IAttribute.class, JSONOutputAdapter.TypeInfoMixIn.class);
        }
    }
}
