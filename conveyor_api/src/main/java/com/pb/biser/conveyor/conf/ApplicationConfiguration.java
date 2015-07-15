package com.pb.biser.conveyor.conf;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by diver on 5/11/15.
 */
@EnableWebMvc
@Configuration
@ComponentScan({
        "com.pb.biser.conveyor.controller",
        "com.pb.biser.conveyor.properties",
        "com.pb.biser.conveyor.service"
})
@Import(BiserConfiguration.class)
public class ApplicationConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
