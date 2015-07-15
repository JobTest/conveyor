package com.pb.biser.conveyor.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by diver on 5/19/15.
 */
@Component
@PropertySource("classpath:debug.properties")
public class DebugProperties {

    @Value("${debug.secret_key}")
    private String secretKey;

    @Autowired
    private Environment environment;

    public String getSecretKey() {
        return secretKey;
    }

    public String getProperty(String node) {
        return environment.getProperty(node.toLowerCase());
    }
}
