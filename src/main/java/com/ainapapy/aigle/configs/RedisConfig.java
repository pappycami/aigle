package com.ainapapy.aigle.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {
    
    // Tu peux y ajouter une config custom ici plus tard si besoin
    
}
