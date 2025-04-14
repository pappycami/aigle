package com.ainapapy.aigle.configs;

import com.ainapapy.aigle.utils.redis.RedisSettings;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@PropertySource("classpath:redis.properties")
@EnableConfigurationProperties(RedisSettings.class)
public class RedisConfig {
    
    @Autowired
    RedisSettings redisSettings;
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        System.out.println(">>>>> Redis Connection ENTER: ");
        return new LettuceConnectionFactory();
    } 
    
    @PostConstruct
    public void showCustomValues() {
        System.out.println(">>>>> Redis Namespace: " + redisSettings.getNamespace());
        System.out.println(">>>>> JWT Prefix: " + redisSettings.getTokenPrefix());
    }
   
}

