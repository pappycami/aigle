package com.ainapapy.aigle.configs;

import com.ainapapy.aigle.utils.redis.RedisSettings;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

@Configuration
@PropertySource("classpath:redis.properties")
@EnableConfigurationProperties(RedisSettings.class)
public class RedisConfig {
    
    @Autowired
    RedisSettings redisSettings;
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisSettings.getHost());
        config.setPort(redisSettings.getPort());
        config.setDatabase(redisSettings.getDatabase());

        if (redisSettings.getPassword() != null && !redisSettings.getPassword().isBlank()) {
            config.setPassword(redisSettings.getPassword());
        }

        return new LettuceConnectionFactory(config);
    }    
   
}

