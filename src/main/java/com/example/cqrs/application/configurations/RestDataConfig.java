package com.example.cqrs.application.configurations;

import com.example.cqrs.adapter.out.entities.PersonJPA;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        cors.addMapping("/**").allowedHeaders("*").allowedOriginPatterns("*").exposedHeaders("*").allowCredentials(true);
        var exposureConfiguration = config.getExposureConfiguration();
        config.exposeIdsFor(PersonJPA.class);
        exposureConfiguration.withItemExposure(((metdata, httpMethods) ->
                httpMethods.disable(HttpMethod.DELETE,HttpMethod.PATCH,HttpMethod.POST,HttpMethod.PUT)));
        config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
        config.setBasePath("rest-data-api");
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        //Disable all get by id for all resources
        config.disableDefaultExposure();
        //Disable links for all resources
        config.useHalAsDefaultJsonMediaType(false);
        config.setExposeRepositoryMethodsByDefault(false);
        config.getMetadataConfiguration().setAlpsEnabled(false);
    }
}
