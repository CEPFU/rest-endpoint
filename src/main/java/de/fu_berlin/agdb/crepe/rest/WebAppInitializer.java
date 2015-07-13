package de.fu_berlin.agdb.crepe.rest;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

@Component
public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        // your context configuration like in Spring.io tutorial

        // Register filter to allow cross-domain requests
        registerServletFilter(servletContext, new SimpleCORSFilter());
    }


    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        String filterName = Conventions.getVariableName(filter);
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
        registration.setAsyncSupported(true);
        registration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        return registration;
    }
}
